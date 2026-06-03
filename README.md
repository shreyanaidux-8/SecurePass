# SecurePass

SecurePass is a Java Swing password generator application created as a beginner-friendly but professional college AAT project for a 2nd-year AIML student.

The project uses only Java Swing and Core Java. It does not use a database, external API, or framework, so it can run locally in VS Code, IntelliJ IDEA, Eclipse, or any Java-supported editor.

## Project Features

- Java Swing graphical user interface
- Core Java backend logic
- Password length input with minimum 4 and maximum 64
- Checkboxes for uppercase letters, lowercase letters, numbers, and special characters
- Generate Password button
- Copy Password button
- Clear button
- Exit button
- Generated password display area
- Password strength display: Weak, Medium, Strong, Very Strong
- Color-coded strength progress bar
- Cleaner professional Swing layout
- Inline validation status messages
- Input validation for:
  - Empty length input
  - Non-number length input
  - Invalid length
  - No checkbox selected

## Project Structure

```text
SecurePass/
├── README.md
├── RunSecurePass.command
└── src/
    ├── Main.java
    ├── PasswordGenerator.java
    └── PasswordStrengthChecker.java
```

## Technologies Used

- Java
- Java Swing
- Core Java
- SecureRandom

## Requirements

- JDK 8 or above
- VS Code, IntelliJ IDEA, Eclipse, or terminal

## How to Run in Terminal

Open the project folder in terminal and run:

```bash
cd "/Users/shreyanaidu/Documents/Codex/2026-06-03/create-a-complete-java-swing-project/outputs/SecurePass"
javac -d out src/*.java
java -cp out Main
```

On this Mac, you can also double-click:

```text
RunSecurePass.command
```

This file automatically finds the installed JDK, compiles the project, and opens the app.

## How to Run in VS Code

1. Open the SecurePass project folder in VS Code.
2. Install the Java Extension Pack if it is not already installed.
3. Open `src/Main.java`.
4. Click the Run button above the `main` method.

## How to Run in IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select Open and choose the SecurePass project folder.
3. Mark the `src` folder as Sources Root if needed.
4. Open `Main.java`.
5. Right-click inside the file and select Run `Main.main()`.

## File Explanation

### Main.java

`Main.java` contains the Swing frontend. It creates the application window, labels, text fields, checkboxes, buttons, and password display area.

Important responsibilities:

- Accept password length from the user
- Read selected character type checkboxes
- Validate user input
- Call the password generator backend
- Call the strength checker backend
- Display the generated password and strength
- Copy the password to the clipboard
- Clear fields and close the application

### PasswordGenerator.java

`PasswordGenerator.java` contains the backend password generation logic.

Important responsibilities:

- Uses `SecureRandom` instead of `Random`
- Stores character sets for uppercase, lowercase, numbers, and special characters
- Adds at least one character from every selected category
- Fills the remaining password length using all selected categories
- Shuffles the final password so characters are not in a predictable order
- Returns the final generated password

### PasswordStrengthChecker.java

`PasswordStrengthChecker.java` calculates password strength using:

- Password length
- Number of selected character categories

The strength is shown as:

- Weak
- Medium
- Strong
- Very Strong

## Backend Logic

The backend is divided into two classes:

1. `PasswordGenerator`
2. `PasswordStrengthChecker`

This separation keeps the project clean and easy to explain. The GUI code is in `Main.java`, while the main logic is placed in separate backend classes.

## Password Generation Algorithm

1. Read selected character categories.
2. Add all selected category characters to one allowed character set.
3. Pick one random character from each selected category.
4. Fill the remaining password length with random characters from the allowed set.
5. Shuffle all characters using `Collections.shuffle`.
6. Convert the character list into a string.
7. Display the final password in the GUI.

## Password Strength Algorithm

The strength checker gives points based on:

- Length greater than or equal to 8
- Length greater than or equal to 12
- Length greater than or equal to 16
- At least 2 selected character types
- At least 3 selected character types
- All 4 selected character types

Based on the total score:

- 0 to 2: Weak
- 3 to 4: Medium
- 5: Strong
- 6: Very Strong

## Why SecureRandom Is Used

`SecureRandom` is designed for security-sensitive random values. It is better than `Random` for password generation because it produces less predictable results.

## Validation Rules

The application checks:

- If the length field is empty
- If the length is not a number
- If the length is less than 4
- If the length is greater than 64
- If no character type checkbox is selected

If any validation fails, the application shows an inline status message and does not generate a password.

## Viva Questions and Answers

### 1. What is SecurePass?

SecurePass is a Java Swing desktop application that generates secure random passwords based on user-selected options.

### 2. Why did you use Java Swing?

Java Swing is part of Java and is useful for creating desktop GUI applications without external frameworks.

### 3. What is the backend in this project?

The backend is the Core Java logic inside `PasswordGenerator.java` and `PasswordStrengthChecker.java`.

### 4. Why did you use SecureRandom?

`SecureRandom` gives more secure and less predictable random values than `Random`, so it is better for password generation.

### 5. How do you ensure every selected category appears in the password?

The program first adds one random character from every selected category before filling the remaining password length.

### 6. Why is the password shuffled?

The password is shuffled so the required category characters do not always appear at the beginning in the same order.

### 7. What validations are included?

The application validates empty input, non-number input, password length outside 4 to 64, and no checkbox selected.

### 8. How is password strength calculated?

Password strength is calculated using password length and the number of selected character categories.

### 9. Does this project use a database?

No. The project does not store passwords or user data, so no database is required.

### 10. Does this project use any external API?

No. The project runs completely offline using only Java.

### 11. What is the use of SwingUtilities.invokeLater?

It starts the Swing GUI safely on the Event Dispatch Thread, which is the correct thread for Swing components.

### 12. What is the role of Main.java?

`Main.java` creates the GUI, handles button clicks, validates input, and displays results.

### 13. What is the role of PasswordGenerator.java?

It generates the password using selected character categories and `SecureRandom`.

### 14. What is the role of PasswordStrengthChecker.java?

It checks the strength of the generated password based on length and selected character types.

### 15. What are future improvements for this project?

Future improvements can include a password history panel, dark mode, password export option, and advanced strength analysis.

## Future Improvements

- Add dark mode
- Add password history for the current session
- Add option to exclude similar characters like `O`, `0`, `l`, and `1`
- Add option to avoid repeated characters
- Add more advanced strength calculation
- Add unit tests for backend classes
- Add a menu bar with Help and About sections

## Conclusion

SecurePass is a simple, secure, and easy-to-understand Java Swing application. It is suitable for a college AAT project because it includes frontend design, backend logic, validation, security-related random generation, and clear code separation.
