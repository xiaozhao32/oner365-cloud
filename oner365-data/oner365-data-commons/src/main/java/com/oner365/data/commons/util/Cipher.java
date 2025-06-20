package com.oner365.data.commons.util;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 加密/解密工具类
 *
 * @author liutao
 */
public final class Cipher {

    public static final int ENCRYPT = 1;

    public static final int DECRYPT = 0;

    public static final int ROUND = 32;

    public static final int BLOCK = 16;

    public static final int FOUR = 4;

    private static final String PARAM_ERROR = "内容和密钥至少16个字符.";

    private Cipher() {

    }

    private static final byte[] SBOX = { (byte) 0xd6, (byte) 0x90, (byte) 0xe9, (byte) 0xfe, (byte) 0xcc, (byte) 0xe1,
            0x3d, (byte) 0xb7, 0x16, (byte) 0xb6, 0x14, (byte) 0xc2, 0x28, (byte) 0xfb, 0x2c, 0x05, 0x2b, 0x67,
            (byte) 0x9a, 0x76, 0x2a, (byte) 0xbe, 0x04, (byte) 0xc3, (byte) 0xaa, 0x44, 0x13, 0x26, 0x49, (byte) 0x86,
            0x06, (byte) 0x99, (byte) 0x9c, 0x42, 0x50, (byte) 0xf4, (byte) 0x91, (byte) 0xef, (byte) 0x98, 0x7a, 0x33,
            0x54, 0x0b, 0x43, (byte) 0xed, (byte) 0xcf, (byte) 0xac, 0x62, (byte) 0xe4, (byte) 0xb3, 0x1c, (byte) 0xa9,
            (byte) 0xc9, 0x08, (byte) 0xe8, (byte) 0x95, (byte) 0x80, (byte) 0xdf, (byte) 0x94, (byte) 0xfa, 0x75,
            (byte) 0x8f, 0x3f, (byte) 0xa6, 0x47, 0x07, (byte) 0xa7, (byte) 0xfc, (byte) 0xf3, 0x73, 0x17, (byte) 0xba,
            (byte) 0x83, 0x59, 0x3c, 0x19, (byte) 0xe6, (byte) 0x85, 0x4f, (byte) 0xa8, 0x68, 0x6b, (byte) 0x81,
            (byte) 0xb2, 0x71, 0x64, (byte) 0xda, (byte) 0x8b, (byte) 0xf8, (byte) 0xeb, 0x0f, 0x4b, 0x70, 0x56,
            (byte) 0x9d, 0x35, 0x1e, 0x24, 0x0e, 0x5e, 0x63, 0x58, (byte) 0xd1, (byte) 0xa2, 0x25, 0x22, 0x7c, 0x3b,
            0x01, 0x21, 0x78, (byte) 0x87, (byte) 0xd4, 0x00, 0x46, 0x57, (byte) 0x9f, (byte) 0xd3, 0x27, 0x52, 0x4c,
            0x36, 0x02, (byte) 0xe7, (byte) 0xa0, (byte) 0xc4, (byte) 0xc8, (byte) 0x9e, (byte) 0xea, (byte) 0xbf,
            (byte) 0x8a, (byte) 0xd2, 0x40, (byte) 0xc7, 0x38, (byte) 0xb5, (byte) 0xa3, (byte) 0xf7, (byte) 0xf2,
            (byte) 0xce, (byte) 0xf9, 0x61, 0x15, (byte) 0xa1, (byte) 0xe0, (byte) 0xae, 0x5d, (byte) 0xa4, (byte) 0x9b,
            0x34, 0x1a, 0x55, (byte) 0xad, (byte) 0x93, 0x32, 0x30, (byte) 0xf5, (byte) 0x8c, (byte) 0xb1, (byte) 0xe3,
            0x1d, (byte) 0xf6, (byte) 0xe2, 0x2e, (byte) 0x82, 0x66, (byte) 0xca, 0x60, (byte) 0xc0, 0x29, 0x23,
            (byte) 0xab, 0x0d, 0x53, 0x4e, 0x6f, (byte) 0xd5, (byte) 0xdb, 0x37, 0x45, (byte) 0xde, (byte) 0xfd,
            (byte) 0x8e, 0x2f, 0x03, (byte) 0xff, 0x6a, 0x72, 0x6d, 0x6c, 0x5b, 0x51, (byte) 0x8d, 0x1b, (byte) 0xaf,
            (byte) 0x92, (byte) 0xbb, (byte) 0xdd, (byte) 0xbc, 0x7f, 0x11, (byte) 0xd9, 0x5c, 0x41, 0x1f, 0x10, 0x5a,
            (byte) 0xd8, 0x0a, (byte) 0xc1, 0x31, (byte) 0x88, (byte) 0xa5, (byte) 0xcd, 0x7b, (byte) 0xbd, 0x2d, 0x74,
            (byte) 0xd0, 0x12, (byte) 0xb8, (byte) 0xe5, (byte) 0xb4, (byte) 0xb0, (byte) 0x89, 0x69, (byte) 0x97, 0x4a,
            0x0c, (byte) 0x96, 0x77, 0x7e, 0x65, (byte) 0xb9, (byte) 0xf1, 0x09, (byte) 0xc5, 0x6e, (byte) 0xc6,
            (byte) 0x84, 0x18, (byte) 0xf0, 0x7d, (byte) 0xec, 0x3a, (byte) 0xdc, 0x4d, 0x20, 0x79, (byte) 0xee, 0x5f,
            0x3e, (byte) 0xd7, (byte) 0xcb, 0x39, 0x48 };

    private static final int[] CK = { 0x00070e15, 0x1c232a31, 0x383f464d, 0x545b6269, 0x70777e85, 0x8c939aa1,
            0xa8afb6bd, 0xc4cbd2d9, 0xe0e7eef5, 0xfc030a11, 0x181f262d, 0x343b4249, 0x50575e65, 0x6c737a81, 0x888f969d,
            0xa4abb2b9, 0xc0c7ced5, 0xdce3eaf1, 0xf8ff060d, 0x141b2229, 0x30373e45, 0x4c535a61, 0x686f767d, 0x848b9299,
            0xa0a7aeb5, 0xbcc3cad1, 0xd8dfe6ed, 0xf4fb0209, 0x10171e25, 0x2c333a41, 0x484f565d, 0x646b7279 };

    private static int rotl(int x, int y) {
        return x << y | x >>> (32 - y);
    }

    private static int byteSub(int a) {
        return (SBOX[a >>> 24 & 0xFF] & 0xFF) << 24 | (SBOX[a >>> 16 & 0xFF] & 0xFF) << 16
                | (SBOX[a >>> 8 & 0xFF] & 0xFF) << 8 | (SBOX[a & 0xFF] & 0xFF);
    }

    private static int l1(int b) {
        return b ^ rotl(b, 2) ^ rotl(b, 10) ^ rotl(b, 18) ^ rotl(b, 24);
    }

    private static int l2(int b) {
        return b ^ rotl(b, 13) ^ rotl(b, 23);
    }

    static void sms4Crypt(byte[] input, byte[] output, int[] rk) {
        int r;
        int mid;
        int[] x = new int[FOUR];
        int[] tmp = new int[FOUR];
        for (int i = 0; i < FOUR; i++) {
            tmp[0] = input[0 + FOUR * i] & 0xff;
            tmp[1] = input[1 + FOUR * i] & 0xff;
            tmp[2] = input[2 + FOUR * i] & 0xff;
            tmp[3] = input[3 + FOUR * i] & 0xff;
            x[i] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }
        for (r = 0; r < ROUND; r += FOUR) {
            mid = x[1] ^ x[2] ^ x[3] ^ rk[r + 0];
            mid = byteSub(mid);
            x[0] = x[0] ^ l1(mid);

            mid = x[2] ^ x[3] ^ x[0] ^ rk[r + 1];
            mid = byteSub(mid);
            x[1] = x[1] ^ l1(mid);

            mid = x[3] ^ x[0] ^ x[1] ^ rk[r + 2];
            mid = byteSub(mid);
            x[2] = x[2] ^ l1(mid);

            mid = x[0] ^ x[1] ^ x[2] ^ rk[r + 3];
            mid = byteSub(mid);
            x[3] = x[3] ^ l1(mid);
        }

        // Reverse
        for (int j = 0; j < BLOCK; j += FOUR) {
            output[j] = (byte) (x[3 - j / FOUR] >>> 24 & 0xFF);
            output[j + 1] = (byte) (x[3 - j / FOUR] >>> 16 & 0xFF);
            output[j + 2] = (byte) (x[3 - j / FOUR] >>> 8 & 0xFF);
            output[j + 3] = (byte) (x[3 - j / FOUR] & 0xFF);
        }
    }

    private static void sms4KeyExt(byte[] key, int[] rk, int cryptFlag) {
        int r;
        int mid;
        int[] x = new int[FOUR];
        int[] tmp = new int[FOUR];
        for (int i = 0; i < FOUR; i++) {
            tmp[0] = key[0 + FOUR * i] & 0xFF;
            tmp[1] = key[1 + FOUR * i] & 0xff;
            tmp[2] = key[2 + FOUR * i] & 0xff;
            tmp[3] = key[3 + FOUR * i] & 0xff;
            x[i] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }
        x[0] ^= 0xa3b1bac6;
        x[1] ^= 0x56aa3350;
        x[2] ^= 0x677d9197;
        x[3] ^= 0xb27022dc;
        for (r = 0; r < ROUND; r += FOUR) {
            mid = x[1] ^ x[2] ^ x[3] ^ CK[r + 0];
            mid = byteSub(mid);
            // rk0=K4
            rk[r + 0] = x[0] ^= l2(mid);

            mid = x[2] ^ x[3] ^ x[0] ^ CK[r + 1];
            mid = byteSub(mid);
            // rk1=K5
            rk[r + 1] = x[1] ^= l2(mid);

            mid = x[3] ^ x[0] ^ x[1] ^ CK[r + 2];
            mid = byteSub(mid);
            // rk2=K6
            rk[r + 2] = x[2] ^= l2(mid);

            mid = x[0] ^ x[1] ^ x[2] ^ CK[r + 3];
            mid = byteSub(mid);
            // rk3=K7
            rk[r + 3] = x[3] ^= l2(mid);
        }

        // 解密时轮密钥使用顺序：rk31,rk30,...,rk0
        if (cryptFlag == DECRYPT) {
            for (r = 0; r < BLOCK; r++) {
                mid = rk[r];
                rk[r] = rk[31 - r];
                rk[31 - r] = mid;
            }
        }
    }

    public static int sms4(byte[] in, int inLen, byte[] key, byte[] out, int cryptFlag) {
        int point = 0;
        int[] roundKey = new int[ROUND];

        sms4KeyExt(key, roundKey, cryptFlag);
        byte[] input;
        byte[] output = new byte[BLOCK];

        while (inLen >= BLOCK) {
            input = Arrays.copyOfRange(in, point, point + BLOCK);
            sms4Crypt(input, output, roundKey);
            System.arraycopy(output, 0, out, point, BLOCK);
            inLen -= BLOCK;
            point += BLOCK;
        }

        return 0;
    }

    public static byte[] encodeSms4(String plaintext, byte[] key) {
        if (plaintext.length() < BLOCK || key.length < BLOCK) {
            throw new IllegalArgumentException(PARAM_ERROR);
        }
        StringBuilder plain = new StringBuilder(plaintext);
        for (int i = plaintext.getBytes().length % BLOCK; i < BLOCK; i++) {
            plain.append('\0');
        }

        return encodeSms4(plain.toString().getBytes(), key);
    }

    /**
     * 不限明文长度的SMS4加密
     * @param plaintext 加密串
     * @param key 密钥
     * @return byte[]
     */
    public static byte[] encodeSms4(byte[] plaintext, byte[] key) {
        if (plaintext.length < BLOCK || key.length < BLOCK) {
            throw new IllegalArgumentException(PARAM_ERROR);
        }
        byte[] ciphertext = new byte[plaintext.length];

        int k = 0;
        int plainLen = plaintext.length;
        while (k + BLOCK <= plainLen) {
            byte[] cellPlain = new byte[BLOCK];
            System.arraycopy(plaintext, k + 0, cellPlain, 0, BLOCK);
            byte[] cellCipher = encode16(cellPlain, key);
            System.arraycopy(cellCipher, 0, ciphertext, k + 0, cellCipher.length);

            k += 16;
        }

        return ciphertext;
    }

    /**
     * 不限明文长度的SMS4解密
     * @param ciphertext 加密串
     * @param key 密钥
     * @return byte[]
     */
    public static byte[] decodeSms4(byte[] ciphertext, byte[] key) {
        if (ciphertext.length < BLOCK || key.length < BLOCK) {
            throw new IllegalArgumentException(PARAM_ERROR);
        }
        byte[] plaintext = new byte[ciphertext.length];

        int k = 0;
        int cipherLen = ciphertext.length;
        while (k + BLOCK <= cipherLen) {
            byte[] cellCipher = new byte[BLOCK];
            System.arraycopy(ciphertext, k + 0, cellCipher, 0, BLOCK);
            byte[] cellPlain = decode16(cellCipher, key);
            System.arraycopy(cellPlain, 0, plaintext, k + 0, cellPlain.length);

            k += 16;
        }

        return plaintext;
    }

    /**
     * 解密，获得明文字符串
     * @param ciphertext 加密串
     * @param key 密钥
     * @return String
     */
    public static String decodeSms4toString(byte[] ciphertext, byte[] key) {
        if (ciphertext.length < BLOCK || key.length < BLOCK) {
            throw new IllegalArgumentException(PARAM_ERROR);
        }
        byte[] plaintext = decodeSms4(ciphertext, key);
        return new String(plaintext, Charset.defaultCharset());
    }

    /**
     * 只加密16位明文
     * @param plaintext 加密串
     * @param key 密钥
     * @return byte[]
     */
    private static byte[] encode16(byte[] plaintext, byte[] key) {
        byte[] cipher = new byte[16];
        sms4(plaintext, 16, key, cipher, ENCRYPT);

        return cipher;
    }

    /**
     * 只解密16位密文
     * @param ciphertext 加密串
     * @param key 密钥
     * @return byte[]
     */
    private static byte[] decode16(byte[] ciphertext, byte[] key) {
        byte[] plain = new byte[16];
        sms4(ciphertext, 16, key, plain, DECRYPT);

        return plain;
    }

}
