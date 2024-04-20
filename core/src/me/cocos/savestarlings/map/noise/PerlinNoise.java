package me.cocos.savestarlings.map.noise;

import com.badlogic.gdx.math.MathUtils;

public class PerlinNoise {
    private int[] permutation;

    public PerlinNoise() {
        permutation = new int[256];
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        for (int i = 0; i < 256; i++) {
            int j = MathUtils.random(255);
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }
        permutation = concat(permutation, permutation);
    }

    private int[] concat(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    private float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    private float grad(int hash, float x, float y, float z) {
        int h = hash & 15;
        float u = (h < 8) ? x : y;
        float v = (h < 4) ? y : (h == 12 || h == 14) ? x : z;
        return (((h & 1) == 0) ? u : -u) + (((h & 2) == 0) ? v : -v);
    }

    public float noise(float x, float y, float z) {
        int X = MathUtils.floor(x) & 255;
        int Y = MathUtils.floor(y) & 255;
        int Z = MathUtils.floor(z) & 255;

        x -= MathUtils.floor(x);
        y -= MathUtils.floor(y);
        z -= MathUtils.floor(z);

        float u = fade(x);
        float v = fade(y);
        float w = fade(z);

        int A = permutation[X] + Y;
        int AA = permutation[A] + Z;
        int AB = permutation[A + 1] + Z;
        int B = permutation[X + 1] + Y;
        int BA = permutation[B] + Z;
        int BB = permutation[B + 1] + Z;

        float lerpResult1 = lerp(
                lerp(grad(permutation[AA], x, y, z), grad(permutation[BA], x - 1, y, z), u),
                lerp(grad(permutation[AB], x, y - 1, z), grad(permutation[BB], x - 1, y - 1, z), u),
                v
        );

        float lerpResult2 = lerp(
                lerp(grad(permutation[AA + 1], x, y, z - 1), grad(permutation[BA + 1], x - 1, y, z - 1), u),
                lerp(grad(permutation[AB + 1], x, y - 1, z - 1), grad(permutation[BB + 1], x - 1, y - 1, z - 1), u),
                v
        );

        return (lerp(lerpResult1, lerpResult2, w) + 1) / 2;
    }
}


