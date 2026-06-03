import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Main extends JFrame {
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 64;

    private static final Color BACKGROUND_COLOR = new Color(242, 246, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color PRIMARY_COLOR = new Color(31, 92, 155);
    private static final Color SUCCESS_COLOR = new Color(34, 139, 88);
    private static final Color WARNING_COLOR = new Color(230, 140, 0);
    private static final Color DANGER_COLOR = new Color(190, 55, 65);
    private static final Color TEXT_COLOR = new Color(35, 45, 60);
    private static final Color MUTED_TEXT_COLOR = new Color(95, 105, 120);

    private JTextField lengthTextField;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox lowercaseCheckBox;
    private JCheckBox numbersCheckBox;
    private JCheckBox specialCharactersCheckBox;
    private JTextArea passwordTextArea;
    private JLabel strengthValueLabel;
    private JLabel statusLabel;
    private JProgressBar strengthProgressBar;

    private final PasswordGenerator passwordGenerator;
    private final PasswordStrengthChecker strengthChecker;

    public Main() {
        passwordGenerator = new PasswordGenerator();
        strengthChecker = new PasswordStrengthChecker();

        setTitle("SecurePass - Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(760, 560));

        createUserInterface();

        pack();
        setLocationRelativeTo(null);
    }

    private void createUserInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(18, 18));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(22, 24, 22, 24));

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(8, 4));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("SecurePass");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel subtitleLabel = new JLabel("Generate strong local passwords using Core Java and Swing");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(MUTED_TEXT_COLOR);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 18, 0));
        contentPanel.setOpaque(false);

        contentPanel.add(createSettingsPanel());
        contentPanel.add(createResultPanel());

        return contentPanel;
    }

    private JPanel createSettingsPanel() {
        JPanel settingsPanel = createCardPanel("Password Settings");
        settingsPanel.setLayout(new GridBagLayout());

        lengthTextField = new JTextField("12", 8);
        lengthTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        lengthTextField.setHorizontalAlignment(SwingConstants.CENTER);
        lengthTextField.addActionListener(event -> generatePassword());

        uppercaseCheckBox = createCheckBox("Uppercase letters (A-Z)", true);
        lowercaseCheckBox = createCheckBox("Lowercase letters (a-z)", true);
        numbersCheckBox = createCheckBox("Numbers (0-9)", true);
        specialCharactersCheckBox = createCheckBox("Special characters (!@#$)", true);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 8, 10, 8);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        settingsPanel.add(createFieldLabel("Password length"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        settingsPanel.add(lengthTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 0;
        settingsPanel.add(createHintLabel("Allowed range: 4 to 64 characters"), constraints);

        constraints.gridy = 2;
        settingsPanel.add(uppercaseCheckBox, constraints);

        constraints.gridy = 3;
        settingsPanel.add(lowercaseCheckBox, constraints);

        constraints.gridy = 4;
        settingsPanel.add(numbersCheckBox, constraints);

        constraints.gridy = 5;
        settingsPanel.add(specialCharactersCheckBox, constraints);

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statusLabel.setForeground(SUCCESS_COLOR);

        constraints.gridy = 6;
        constraints.insets = new Insets(18, 8, 6, 8);
        settingsPanel.add(statusLabel, constraints);

        return settingsPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = createCardPanel("Generated Password");
        resultPanel.setLayout(new GridBagLayout());

        passwordTextArea = new JTextArea(5, 24);
        passwordTextArea.setEditable(false);
        passwordTextArea.setLineWrap(true);
        passwordTextArea.setWrapStyleWord(true);
        passwordTextArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        passwordTextArea.setForeground(TEXT_COLOR);
        passwordTextArea.setBackground(new Color(247, 250, 252));
        passwordTextArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 220, 230), 1, true),
                new EmptyBorder(14, 14, 14, 14)));

        strengthValueLabel = new JLabel("Not generated");
        strengthValueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        strengthValueLabel.setForeground(MUTED_TEXT_COLOR);

        strengthProgressBar = new JProgressBar(0, 100);
        strengthProgressBar.setValue(0);
        strengthProgressBar.setStringPainted(false);
        strengthProgressBar.setPreferredSize(new Dimension(260, 16));
        strengthProgressBar.setForeground(MUTED_TEXT_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 8, 10, 8);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = 0;
        resultPanel.add(createHintLabel("Your password will appear below"), constraints);

        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        resultPanel.add(passwordTextArea, constraints);

        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0;
        resultPanel.add(createFieldLabel("Strength"), constraints);

        constraints.gridy = 3;
        resultPanel.add(strengthValueLabel, constraints);

        constraints.gridy = 4;
        resultPanel.add(strengthProgressBar, constraints);

        return resultPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton generateButton = createButton("Generate Password", PRIMARY_COLOR, Color.WHITE);
        JButton copyButton = createButton("Copy Password", SUCCESS_COLOR, Color.WHITE);
        JButton clearButton = createButton("Clear", Color.WHITE, TEXT_COLOR);
        JButton exitButton = createButton("Exit", DANGER_COLOR, Color.WHITE);

        clearButton.setBorder(new LineBorder(new Color(190, 200, 210), 1, true));

        generateButton.addActionListener(event -> generatePassword());
        copyButton.addActionListener(event -> copyPassword());
        clearButton.addActionListener(event -> clearFields());
        exitButton.addActionListener(event -> dispose());

        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    private JPanel createCardPanel(String title) {
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(218, 226, 235), 1, true),
                new EmptyBorder(22, 22, 22, 22)));
        cardPanel.setBorder(BorderFactory.createTitledBorder(cardPanel.getBorder(), title));
        return cardPanel;
    }

    private JCheckBox createCheckBox(String text, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text, selected);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 15));
        checkBox.setForeground(TEXT_COLOR);
        checkBox.setBackground(CARD_COLOR);
        checkBox.setFocusPainted(false);
        return checkBox;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JLabel createHintLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(MUTED_TEXT_COLOR);
        return label;
    }

    private JButton createButton(String text, Color backgroundColor, Color foregroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(backgroundColor.darker(), 1, true),
                new EmptyBorder(10, 16, 10, 16)));
        return button;
    }

    private void generatePassword() {
        String lengthInput = lengthTextField.getText().trim();

        if (lengthInput.isEmpty()) {
            showStatus("Enter a password length first.", DANGER_COLOR);
            return;
        }

        int passwordLength;
        try {
            passwordLength = Integer.parseInt(lengthInput);
        } catch (NumberFormatException exception) {
            showStatus("Length must be a number only.", DANGER_COLOR);
            return;
        }

        if (passwordLength < MIN_PASSWORD_LENGTH || passwordLength > MAX_PASSWORD_LENGTH) {
            showStatus("Length must be between 4 and 64.", DANGER_COLOR);
            return;
        }

        boolean useUppercase = uppercaseCheckBox.isSelected();
        boolean useLowercase = lowercaseCheckBox.isSelected();
        boolean useNumbers = numbersCheckBox.isSelected();
        boolean useSpecialCharacters = specialCharactersCheckBox.isSelected();

        if (!useUppercase && !useLowercase && !useNumbers && !useSpecialCharacters) {
            showStatus("Select at least one character type.", DANGER_COLOR);
            return;
        }

        String password = passwordGenerator.generatePassword(passwordLength, useUppercase,
                useLowercase, useNumbers, useSpecialCharacters);
        String strength = strengthChecker.checkStrength(passwordLength, useUppercase,
                useLowercase, useNumbers, useSpecialCharacters);

        passwordTextArea.setText(password);
        strengthValueLabel.setText(strength);
        updateStrengthDisplay(strength);
        showStatus("Password generated successfully.", SUCCESS_COLOR);
    }

    private void copyPassword() {
        String password = passwordTextArea.getText();

        if (password.isEmpty()) {
            showStatus("Generate a password before copying.", DANGER_COLOR);
            return;
        }

        StringSelection stringSelection = new StringSelection(password);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        showStatus("Password copied to clipboard.", SUCCESS_COLOR);
    }

    private void clearFields() {
        lengthTextField.setText("12");
        passwordTextArea.setText("");
        strengthValueLabel.setText("Not generated");
        strengthValueLabel.setForeground(MUTED_TEXT_COLOR);
        strengthProgressBar.setValue(0);
        strengthProgressBar.setForeground(MUTED_TEXT_COLOR);

        uppercaseCheckBox.setSelected(true);
        lowercaseCheckBox.setSelected(true);
        numbersCheckBox.setSelected(true);
        specialCharactersCheckBox.setSelected(true);

        showStatus("Ready", SUCCESS_COLOR);
    }

    private void updateStrengthDisplay(String strength) {
        if (strength.equals("Weak")) {
            strengthValueLabel.setForeground(DANGER_COLOR);
            strengthProgressBar.setValue(25);
            strengthProgressBar.setForeground(DANGER_COLOR);
        } else if (strength.equals("Medium")) {
            strengthValueLabel.setForeground(WARNING_COLOR);
            strengthProgressBar.setValue(50);
            strengthProgressBar.setForeground(WARNING_COLOR);
        } else if (strength.equals("Strong")) {
            strengthValueLabel.setForeground(PRIMARY_COLOR);
            strengthProgressBar.setValue(75);
            strengthProgressBar.setForeground(PRIMARY_COLOR);
        } else {
            strengthValueLabel.setForeground(SUCCESS_COLOR);
            strengthProgressBar.setValue(100);
            strengthProgressBar.setForeground(SUCCESS_COLOR);
        }
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception exception) {
            // If Nimbus is unavailable, Java will use the default Swing look.
        }

        SwingUtilities.invokeLater(() -> {
            Main application = new Main();
            application.setVisible(true);
        });
    }
}
