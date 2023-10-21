package moe.icegame.coreutils;

import java.util.UUID;

public class CommonUtil {
    public static void Log(Object msg) {
        System.out.println(msg);
    }

    public static String PCRE2RegexToMySql(String pcre2Regex) {
        // Escape special characters used in MySQL regex
        String mysqlRegex = pcre2Regex
                .replace("\\", "\\\\")
                .replace(".", "\\.")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("?", "\\?")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("|", "\\|")
                .replace("/", "\\/");

        // Convert PCRE2-specific patterns to their MySQL equivalents
        mysqlRegex = mysqlRegex
                .replace("\\d", "[0-9]")
                .replace("\\D", "[^0-9]")
                .replace("\\w", "[a-zA-Z0-9_]")
                .replace("\\W", "[^a-zA-Z0-9_]")
                .replace("\\s", "[\\s\\t\\r\\n]")
                .replace("\\S", "[^\\s\\t\\r\\n]");

        // Add any additional PCRE2-specific pattern replacements here

        return mysqlRegex;
    }

}
