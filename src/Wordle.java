// Basic
import java.util.List;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
// GUI
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class Wordle implements ActionListener {
    // ----- What I need -----
    JFrame frame;

    JPanel title; // stores the heading label and instructions pop up window
    JButton help; // Displays image - in pop up window - when clicked
    static JButton replay;
    JLabel heading; 

    JPanel grid; // stores the labels that display grey/green/orange ie RESULTS
    static JLabel[] squares = new JLabel[30]; 

    JPanel keyboard; // stores buttons for the user's input
    static JTextField textfield; // stores user input
    static JTextField suggestionField; // stores user input


    // ------- Buttons --------
    JButton deleteButton = new JButton("<"); // Backspace
    static JButton enterButton = new JButton("ENTER"); // Enter
    JButton[] letters = new JButton[26]; // Alphabet

    // ------- Additional -------
    static int chances = 0;
    static Font hel = new Font("Helvetica", Font.BOLD, 30); // Font used in wordle
    static Font helSmaller = new Font("Helvetica", Font.BOLD, 13);
    static String userInput = "", answer = "";
    static int rowCount = 0;
    static List<String> words = new ArrayList<>();
    static List<String> allowable = new ArrayList<>();
    static ArrayList<String> suggestions = new ArrayList<>();
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

    Wordle () throws IOException {
        // -------- Set Up --------
        frame = new JFrame("WORDLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Required -- can't close window without it
        frame.setFont(hel); // For entire frame
        frame.setSize(600, 900); // Not definite
        frame.setResizable(false); // Remove maximize option - Will always be responsive now :)
        frame.setLayout(null);
        frame.getContentPane().setBackground(dark);
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/imgs/logo.png"));
        frame.setIconImage(image);
        frame.setLocationRelativeTo(null);

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
        textfield = new JTextField("HI!");
        textfield.setBounds(142, 480, 300, 50); // Position x, y, length, height
        textfield.setHorizontalAlignment(JTextField.CENTER); // Center align the input
        textfield.setFont(hel);
        textfield.setEditable(false); // Doesn't allow input - only from buttons
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

        // ----- Suggestion Box -----
        suggestionField = new JTextField();
        suggestionField.setBounds(70, 760, 460, 50); // Position x, y, length, height
        suggestionField.setHorizontalAlignment(JTextField.CENTER); // Center align the input
        suggestionField.setFont(hel);
        suggestionField.setEditable(false); // Doesn't allow input - only from buttons
        suggestionField.setBorder(new LineBorder(darkGrey, 4));
        suggestionField.setBackground(dark);
        suggestionField.setForeground(offWhite);

        // ----- Finalize the JFrame -----
		frame.add(title);
        frame.add(grid);
        frame.getContentPane().add(keyboard);
        frame.add(textfield);
		frame.add(suggestionField);
		frame.setVisible(true); // Required for window to open

    }
    public static void main(String[] args) throws IOException {
        Path wordsPath = Paths.get("C:/Users/Tony/wordle-clone-java-swing/src/words.txt"); // Get file with wordle-words in it
        Path allowPath = Paths.get("C:/Users/Tony/wordle-clone-java-swing/src/allowable.txt");
        words = Files.readAllLines(wordsPath, StandardCharsets.UTF_8); // fill array
        allowable = Files.readAllLines(allowPath, StandardCharsets.UTF_8);
        newWord();
        new Wordle();
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

            if(userInput.equals("XXXXX"))
                letsCheckAlgo();

            if(chances < 6) {
                if (userInput.length() == 5 && allowable.contains(userInput.toLowerCase())) {
                textfield.setText("");
                chances++;
                checkWord(); 

                } else if(userInput.length() == 5 && !allowable.contains(userInput.toLowerCase())) {
                    textfield.setText("INVALID WORD");
                }
            } 
		}

        if(e.getSource()==help) {
                try {
                    HelpWindow helpPage = new HelpWindow();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        }

        if(e.getSource()==replay) {
            textfield.setText("");
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
        if (answer.equals(userInput)) {
            textfield.setText("WELL DONE");
            suggestionField.setText("\u21BB PLAY AGAIN \u21BB");
            chances = 6; // game over
        } else if (chances == 6) {
            textfield.setText("Ans: " + answer);
            suggestionField.setText("\u21BB TRY AGAIN \u21BB");
            // game over
        }

        if (chances < 6) {
            findSuggestions();
        }
    }

    public static void newWord() {
        int randomWord = new Random().nextInt(words.size());
        answer = words.get(randomWord).toUpperCase();
        rowCount = 0;
        suggestionReset();
    }

    public static void suggestionReset() {
        for(int i =0; i < words.size(); i++) {
            suggestions.add(words.get(i));
        }
    }


    public static void resetGame() {
        newWord();
        chances = 0;
        for(JLabel i : squares) {
            i.setBackground(dark);
            i.setText("");
            i.setBorder(new LineBorder(darkGrey, 2));
        }
        suggestionField.setText("");
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

    public static void findSuggestions () {

        for(int i = 0; i < userInput.length(); i ++) {
            for(int j = 0; j < suggestions.size(); j ++) {
                String check = suggestions.get(j).toUpperCase();
                if(greenOrangeOrGrey(i) == 0 ) {
                    if(check.contains(String.valueOf(userInput.charAt(i)))) {
                        suggestions.remove(j);
                        if(j!=0)
                            j=j-1;
                    }
                        
                } else if(greenOrangeOrGrey(i) == 1) {
                    if (!check.contains(String.valueOf(userInput.charAt(i)))) {
                        suggestions.remove(j);
                        if(j!=0)
                            j=j-1;
                    }

                    if (userInput.charAt(i) == check.charAt(i)) {
                        suggestions.remove(j);
                        if(j!=0)
                            j=j-1;
                    }

                } else if(greenOrangeOrGrey(i) == 2) {
                    if (userInput.charAt(i) != check.charAt(i)) {
                        suggestions.remove(j);
                        if(j!=0)
                            j=j-1;
                    }
                }
            }
        }
        printSuggestions();
    }

    public static void printSuggestions () {
        String s1="",s2="",s3="",printFinal;
        if(0 < suggestions.size())
            s1 = suggestions.get(0).toUpperCase();
        if(1 < suggestions.size())
            s2 = suggestions.get(1).toUpperCase();
        if(2 < suggestions.size())        
            s3 = suggestions.get(2).toUpperCase();

        if(!s3.equals(""))
            printFinal = s1 + "   |   " + s2 + "   |   " + s3;
        else if (!s2.equals(""))
            printFinal = s1 + "   |   " + s2;
        else if (!s1.equals(""))
            printFinal = s1;
        else
            printFinal = "??????";

        suggestionField.setText(printFinal);
    }

    public static int greenOrangeOrGrey(int index) {
        if(userInput.charAt(index) == answer.charAt(index))
            return 2; // green
        else if (answer.contains(String.valueOf(userInput.charAt(index))))
            return 1; // orange
        else 
            return 0; // grey
    }

    public static void letsCheckAlgo() {          
        if(!instructions("ALIEN"))
            if(!instructions("TOURS"))
                if(!instructions(suggestions.get(0).toUpperCase()))
                    if(!instructions(suggestions.get(0).toUpperCase()))
                        if(!instructions(suggestions.get(0).toUpperCase()))
                            if(!instructions(suggestions.get(0).toUpperCase())) {

                            }
                   
    }

    public static boolean instructions (String s1) {
        textfield.setText(s1);
        userInput = s1;
        enterButton.doClick(100);
        if(textfield.getText().equals("WELL DONE")) {
            System.out.println("Game finished");
            return true;
        } else 
            return false;
    }
}

