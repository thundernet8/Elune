/**
 * Elune - Lightweight Forum Powered by Razor.
 * Copyright (C) 2017, Touchumind<chinash2010@gmail.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.elune.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EncryptUtil {

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String md5(String data) {

        return md5(data.getBytes());
    }

    public static String md5(String data, String salt) {

        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    public static String md5(byte[] data) {

        return bytes2HexString(encryptMD5(data));
    }

    public static byte[] encryptMD5(byte[] data) {

        return hashTemplate(data, "MD5");
    }

    /**
     * byte数组转16进制字符串
     *
     * @param bytes 字节数组
     * @return 16进制小写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret).toLowerCase();
    }

    private static byte[] hashTemplate(byte[] data, String algorithm) {

        if (data == null || data.length <= 0) return null;
        try {

            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return null;
        }
    }
}
