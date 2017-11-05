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

import com.elune.model.TagCreationModel;
import com.hankcs.hanlp.HanLP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public final class StringUtil {

    private static Pattern chinesePattern = Pattern.compile("[\\u4E00-\\u9FBF]+");

    public static boolean isEmail(String email) {

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String genRandString(int length, String characters) {

        char[] text = new char[length];
        for (int i = 0; i < length; i++) {

            text[i] = characters.charAt(new Random().nextInt(characters.length()));
        }

        return new String(text);
    }

    public static String genRandString(int length) {

        return genRandString(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    public static String genRandNumbericString(int length) {

        return genRandString(length, "0123456789");
    }

    public static String genRandLatinString(int length) {

        return genRandString(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    public static boolean hasChineseByReg(String str) {

        return str != null && chinesePattern.matcher(str).find();
    }

    public static List<TagCreationModel> getTagsFromContent(String content, int count) {

        List<String> tagTitles = HanLP.extractKeyword(content, count).stream().distinct().collect(Collectors.toList());
        List<TagCreationModel> tagCreationModels = new ArrayList<>(tagTitles.size());
        tagTitles.forEach(title -> {
            TagCreationModel model = new TagCreationModel();
            model.title = title;
            model.slug = hasChineseByReg(title) ? HanLP.convertToPinyinString(title, "", false) : title;
            tagCreationModels.add(model);
        });

        return tagCreationModels;
    }

    public static boolean isNumberic(String str) {

        return str != null && !"".equals(str.trim()) && str.matches("^[0-9]*$");
    }
}
