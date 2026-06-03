import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{};:,.<>?";

    private final SecureRandom secureRandom;

    public PasswordGenerator() {
        secureRandom = new SecureRandom();
    }

    public String generatePassword(int length, boolean useUppercase, boolean useLowercase,
                                   boolean useNumbers, boolean useSpecialCharacters) {
        StringBuilder allowedCharacters = new StringBuilder();
        List<Character> passwordCharacters = new ArrayList<>();

        // Add one character from each selected category.
        if (useUppercase) {
            allowedCharacters.append(UPPERCASE_LETTERS);
            passwordCharacters.add(getRandomCharacter(UPPERCASE_LETTERS));
        }

        if (useLowercase) {
            allowedCharacters.append(LOWERCASE_LETTERS);
            passwordCharacters.add(getRandomCharacter(LOWERCASE_LETTERS));
        }

        if (useNumbers) {
            allowedCharacters.append(NUMBERS);
            passwordCharacters.add(getRandomCharacter(NUMBERS));
        }

        if (useSpecialCharacters) {
            allowedCharacters.append(SPECIAL_CHARACTERS);
            passwordCharacters.add(getRandomCharacter(SPECIAL_CHARACTERS));
        }

        // Fill the remaining length using all selected characters.
        while (passwordCharacters.size() < length) {
            passwordCharacters.add(getRandomCharacter(allowedCharacters.toString()));
        }

        // Shuffle so the required characters are not always at the start.
        Collections.shuffle(passwordCharacters, secureRandom);

        StringBuilder finalPassword = new StringBuilder();
        for (char character : passwordCharacters) {
            finalPassword.append(character);
        }

        return finalPassword.toString();
    }

    private char getRandomCharacter(String characters) {
        int randomIndex = secureRandom.nextInt(characters.length());
        return characters.charAt(randomIndex);
    }
}
