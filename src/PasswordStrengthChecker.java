public class PasswordStrengthChecker {

    public String checkStrength(int length, boolean hasUppercase, boolean hasLowercase,
                                boolean hasNumbers, boolean hasSpecialCharacters) {
        int selectedTypeCount = 0;

        if (hasUppercase) {
            selectedTypeCount++;
        }
        if (hasLowercase) {
            selectedTypeCount++;
        }
        if (hasNumbers) {
            selectedTypeCount++;
        }
        if (hasSpecialCharacters) {
            selectedTypeCount++;
        }

        int score = 0;

        if (length >= 8) {
            score++;
        }
        if (length >= 12) {
            score++;
        }
        if (length >= 16) {
            score++;
        }
        if (selectedTypeCount >= 2) {
            score++;
        }
        if (selectedTypeCount >= 3) {
            score++;
        }
        if (selectedTypeCount == 4) {
            score++;
        }

        if (score <= 2) {
            return "Weak";
        } else if (score <= 4) {
            return "Medium";
        } else if (score == 5) {
            return "Strong";
        } else {
            return "Very Strong";
        }
    }
}
