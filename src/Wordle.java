// Basic
import java.util.List;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
// GUI
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Wordle implements ActionListener {
    // ----- What I need -----
    JFrame frame;
    JPanel title; // stores the heading label and instructions pop up window
    JButton help; // Displays image - in pop up window - when clicked
    JButton replay;
    JLabel heading; 
    JPanel grid; // stores the labels that display grey/green/orange ie RESULTS
    static JLabel[] squares = new JLabel[30]; 
    JPanel keyboard; // stores buttons for the user's input
    static JTextField textfield; // stores user input
    JPanel message; // stores Label displaying welcome message/ error message

    // ------- Buttons --------
    JButton deleteButton = new JButton("<"); // Backspace
    JButton enterButton = new JButton("ENTER"); // Enter
    JButton[] letters = new JButton[26]; // Alphabet

    // ------- Additional -------
    static Font hel = new Font("Helvetica", Font.BOLD, 30); // Font used in wordle
    static Font helSmaller = new Font("Helvetica", Font.BOLD, 13);
    static String userInput = "", answer = "";
    static int rowCount = 0;
    static List<String> words = new ArrayList<>();
    static String[] alphabet = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", 
    "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"
    };

    // Color Palette from WORDLE
    static Color dark = new Color(31, 31, 31); // Increased Brightness from dark mode
    static Color yellow = new Color(181, 159, 59);
    static Color lightGrey = new Color(128, 130, 132);
    static Color green = new Color(83, 141, 79);
    static Color darkGrey = new Color(59, 59, 60);
    static Color offWhite = new Color(255,255,242);

    Wordle () {
        // -------- Set Up --------
        frame = new JFrame("WORDLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Required -- can't close window without it
        frame.setFont(hel); // For entire frame
        frame.setSize(600, 800); // Not definite
        frame.setResizable(false); // Remove maximize option - Will always be responsive now :)
        frame.setLayout(null);
        frame.getContentPane().setBackground(dark);

        // ----- Title -----
        title = new JPanel();
        title.setBounds(70,5,460,50);
        title.setLayout(new BorderLayout(75,10));
        title.setBorder( new MatteBorder(0, 0, 2, 0, darkGrey));
        title.setBackground(dark);

        heading = new JLabel(" W O R D L E");
        heading.setFont(hel);
        heading.setForeground(offWhite);

        help = new JButton("?");
        help.setFont(hel);
        help.setForeground(offWhite);
        help.setFocusable(false);
        help.setBackground(dark);
        help.setBorderPainted(false);
        help.addActionListener(this);

        replay = new JButton("\u21BB");
        replay.setFont(hel);
        replay.setForeground(offWhite);
        replay.setFocusable(false);
        replay.setBackground(dark);
        replay.setBorderPainted(false);
        replay.addActionListener(this);

        title.add(replay, BorderLayout.EAST);
        title.add(help, BorderLayout.WEST);
        title.add(heading, BorderLayout.CENTER);

        // ----- 5x6 Table to display results -----
        grid = new JPanel();
        grid.setBounds(143, 100, 300, 350); // 10 px built in panel ?
		grid.setOpaque(true);
		grid.setBackground(dark);
		grid.setLayout(new GridLayout(6,5,3,3));
        for(int i = 0; i < squares.length; i++) {
            squares[i] = new JLabel("",SwingConstants.CENTER);
            squares[i].setFont(hel);
            squares[i].setBackground(dark);
            squares[i].setForeground(offWhite);
            squares[i].setOpaque(true);
            squares[i].setBorder(new LineBorder(darkGrey, 2));

            grid.add(squares[i]);
        }

        // ----- User Input Field -----
        textfield = new JTextField("hi");
        textfield.setBounds(142, 480, 300, 50); // Position x, y, length, height
        textfield.setHorizontalAlignment(JTextField.CENTER); // Center align the input
        textfield.setFont(hel);
        //textfield.setEditable(false); // Doesn't allow input - only from buttons
        textfield.setBorder(new LineBorder(darkGrey, 4));
        textfield.setBackground(dark);
        textfield.setForeground(offWhite);

        // ----- Keyboard -----
        keyboard = new JPanel();
        keyboard.setBounds(70,550, 460, 185);
        keyboard.setOpaque(true);
		keyboard.setBackground(dark);
		keyboard.setLayout(new GridLayout(3,1,4,4));

        // Split panel into 3 seperate panels
        JPanel row1 = new JPanel(), row2 = new JPanel(), row3 = new JPanel();
        kbPanel(row1);
        kbPanel(row2);
        kbPanel(row3);

        // row 1
        for(int i = 0; i < 10; i++) {
            letters[i] = new JButton(alphabet[i]);
            buttonProperties(letters[i]);
            letters[i].addActionListener(this);
            row1.add(letters[i]);
        }
        keyboard.add(row1);

        // row 2
        for(int i = 9; i < 20; i++) {
            if(i == 9) {
                JLabel fillLeft = new JLabel();
                row2.add(fillLeft);
            } else if (i == 19) {
                JLabel fillRight = new JLabel();
                row2.add(fillRight);
            } else {
                letters[i] = new JButton(alphabet[i]);
                buttonProperties(letters[i]);
                letters[i].addActionListener(this);
                row2.add(letters[i]);
            }
        }
        keyboard.add(row2);

        // row 3
        for(int i = 18; i < 27 ; i++) {
            if(i == 18) {
                enterButton = new JButton("\u2713"); // tick 
                buttonProperties(enterButton);
                enterButton.addActionListener(this);
                enterButton.setFont(helSmaller);
                row3.add(enterButton);
            } else if (i == 26) {
                deleteButton = new JButton("\u2190"); // Backspace arrow
                buttonProperties(deleteButton);
                deleteButton.addActionListener(this);
                deleteButton.setFont(helSmaller);
                row3.add(deleteButton);
            } else {
                letters[i] = new JButton(alphabet[i]);
                buttonProperties(letters[i]);
                letters[i].addActionListener(this);
                row3.add(letters[i]);
            }
        }
        keyboard.add(row3);

        // ----- Finalize the JFrame -----
		frame.add(title);
        frame.add(grid);
        frame.getContentPane().add(keyboard);
        frame.add(textfield);
		frame.add(textfield);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true); // Required for window to open

    }
    public static void main(String[] args) throws IOException {
        Path myPath = Paths.get("C:/Users/Tony/wordle-clone-java-swing/src/words.txt"); // Get file with wordle-words in it
        words = Files.readAllLines(myPath, StandardCharsets.UTF_8); // fill array
        newWord();
        new Wordle();
        System.out.print(answer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < letters.length; i++) {
			if(e.getSource() == letters[i] && textfield.getText().length() < 5) {
				textfield.setText(textfield.getText().concat(alphabet[i]));
			}
		}

        if (e.getSource()==deleteButton) {
			userInput = textfield.getText();
            if(userInput.length() > 0)
			    textfield.setText("");
		}

        if(e.getSource()==enterButton) {
            userInput = textfield.getText();
            if (userInput.length() == 5 && words.contains(userInput.toLowerCase())) {
                textfield.setText("");
                checkWord();
            } else if(userInput.length() == 5 && !words.contains(userInput.toLowerCase())) {
                textfield.setText("Not in my list");
            }
		}

        if(e.getSource()==help) {
            HelpWindow helpPage = new HelpWindow();
        }

        if(e.getSource()==replay) {
            resetGame();
        }
    }

    public static void checkWord() {
        for(int i = 0; i < answer.length(); i++){
            squares[i+rowCount].setText(String.valueOf(userInput.charAt(i)));
            if(userInput.charAt(i) == answer.charAt(i)) { // Turn green
                squares[i+rowCount].setBackground(green);
                squares[i+rowCount].setBorder(new LineBorder(green, 2));
            } else if(answer.contains(String.valueOf(userInput.charAt(i)))) { // Turn yellow
                squares[i+rowCount].setBackground(yellow);
                squares[i+rowCount].setBorder(new LineBorder(yellow, 2));
            } else {
                squares[i+rowCount].setBackground(darkGrey);
            }
        }
        rowCount += 5;
        if (rowCount ==24) {
            resetGame();
            textfield.setText("Ans: " + answer);
        }
    }

    public static void newWord() {
        int randomWord = new Random().nextInt(words.size());
        answer = words.get(randomWord).toUpperCase();
        rowCount = 0;
    }

    public static void resetGame() {
        newWord();
        for(JLabel i : squares) {
            i.setBackground(dark);
            i.setText("");
            i.setBorder(new LineBorder(darkGrey, 2));
        }
            
    }

    public static void kbPanel(JPanel row) {
        row.setLayout(new GridLayout(1,10,4,4));
        row.setBackground(dark);
    }

    public static void buttonProperties(JButton b) {
        b.setFocusable(false);
        b.setBackground(lightGrey);
        b.setForeground(offWhite);
        b.setFont(hel);
        b.setOpaque(true);
        b.setBorder(new LineBorder(dark, 2));
    }
}
