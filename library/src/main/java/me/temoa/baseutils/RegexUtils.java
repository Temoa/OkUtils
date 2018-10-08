package me.temoa.baseutils;

import static java.util.regex.Pattern.matches;

/**
 * Created by lai
 * on 2018/3/5.
 */

@SuppressWarnings({"WeakerAccess", "unused"}) // public api
public class RegexUtils {

    // 手机号码
    private static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    // 手机号码(带 +86 或 86)
    private static final String REGEX_MOBILE_86 = "^((\\+86)|(86))?((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    // 邮箱
    private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    // URL
    private static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    // 用户名
    private static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}";
    // 电话
    private static final String REGEX_TEL = "(^0\\d{2,3}[- ]?)?\\d{7,8}";

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isMobileNumber(CharSequence input) {
        return isMatch(REGEX_MOBILE_SIMPLE, input);
    }

    public static boolean is86MobileNumber(CharSequence input) {
        return isMatch(REGEX_MOBILE_86, input);
    }

    public static boolean isEmail(CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }

    public static boolean isURL(CharSequence input) {
        return isMatch(REGEX_URL, input);
    }

    public static boolean isUsername(CharSequence input) {
        return isMatch(REGEX_USERNAME, input);
    }

    public static boolean isTel(CharSequence input) {
        return isMatch(REGEX_TEL, input);
    }

    private static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && matches(regex, input);
    }
}
