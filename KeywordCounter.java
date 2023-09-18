import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordCounter {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java KeywordCounter <Java-source-file>");
            return;
        }

        String fileName = args[0];

        Set<String> keywords = new HashSet<>();
        // Add Java keywords to the set
        keywords.add("abstract");
        keywords.add("assert");
        keywords.add("boolean");
        // Add more keywords as needed...

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sourceCode = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sourceCode.append(line).append('\n');
            }

            String code = sourceCode.toString();
            int keywordCount = countKeywords(keywords, code);

            System.out.println("Total keywords found: " + keywordCount);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    private static int countKeywords(Set<String> keywords, String code) {
        String pattern = "\\b(" + String.join("|", keywords) + ")\\b";
        Pattern keywordPattern = Pattern.compile(pattern);
        Matcher matcher = keywordPattern.matcher(code);

        int count = 0;
        while (matcher.find()) {
            int start = matcher.start();
            matcher.end();
            if (!isInStringLiteral(code, start) && !isInComment(code, start)) {
                count++;
            }
        }

        return count;
    }

    private static boolean isInStringLiteral(String code, int position) {
        // Check if the position is within a string literal
        // You can enhance this method for more accurate detection
        // For simplicity, we assume string literals are properly formatted
        return code.substring(0, position).replaceAll("\"(\\\\\\\\|\\\\\"|[^\"])*\"", "").contains("\"");
    }

    private static boolean isInComment(String code, int position) {
        // Check if the position is within a comment
        // You can enhance this method for more accurate detection
        return code.substring(0, position).replaceAll("//[^\n]*|/\\*(.|\\R)*?\\*/", "").contains("/*");
    }
}
