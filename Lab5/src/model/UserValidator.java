package model;

import java.util.regex.Pattern;

/**
 * MODEL: the business rules for what counts as valid input.
 * Each method returns an error message, or null when the value is valid.
 * Keeping the rules here (not in the controller) means they can be
 * reused or tested without any user interface.
 */
public class UserValidator {

    // Letters (any language), spaces, apostrophes, hyphens and dots, e.g. "Mary-Jane O'Neil Jr."
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}][\\p{L} .'-]*$");

    // something@domain.tld, no spaces, at least one dot in the domain, 2+ letter ending
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    public static final int MIN_AGE = 1;
    public static final int MAX_AGE = 120;

    public static String validateName(String name) {
        if (name == null || name.isBlank()) {
            return "Name is required.";
        }
        String trimmed = name.trim();
        if (trimmed.length() < 2) {
            return "Name must be at least 2 characters.";
        }
        if (trimmed.length() > 50) {
            return "Name must be at most 50 characters.";
        }
        if (!NAME_PATTERN.matcher(trimmed).matches()) {
            return "Name can only contain letters, spaces, apostrophes, hyphens and dots.";
        }
        return null;
    }

    public static String validateAge(String ageText) {
        if (ageText == null || ageText.isBlank()) {
            return "Age is required.";
        }
        String trimmed = ageText.trim();
        if (!trimmed.matches("\\d+")) {
            return "Age must be a whole number (digits only).";
        }
        int age;
        try {
            age = Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            return "Age is too large.";
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            return "Age must be between " + MIN_AGE + " and " + MAX_AGE + ".";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            return "Email is required.";
        }
        String trimmed = email.trim();
        if (trimmed.length() > 254) {
            return "Email is too long.";
        }
        if (trimmed.contains("..")) {
            return "Email cannot contain two dots in a row.";
        }
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            return "Enter a valid email, e.g. name@example.com.";
        }
        return null;
    }
}
