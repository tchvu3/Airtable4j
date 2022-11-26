package com.avihu.airtable4j.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AirtableGenericUtils {

    public static boolean validateRegex(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

}
