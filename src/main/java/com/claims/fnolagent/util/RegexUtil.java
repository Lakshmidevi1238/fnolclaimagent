package com.claims.fnolagent.util;

import java.util.regex.*;

public class RegexUtil {

    public static String find(String pattern, String text) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1).trim() : null;
    }
}

