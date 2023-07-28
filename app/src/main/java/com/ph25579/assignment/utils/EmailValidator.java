package com.ph25579.assignment.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String GMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@gmail.com$";

    public static boolean isValidEmail(String email) {
        // Biểu thức chính quy để kiểm tra địa chỉ email
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }
    public static boolean isValidGmail(String email) {
        Pattern pattern = Pattern.compile(GMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
