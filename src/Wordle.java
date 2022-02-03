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
import javax.swing.border.LineBorder;

public class Wordle implements ActionListener {
    // ----- What I need -----
    JFrame frame;
    JPanel title; // stores the title label and instructions pop up window button
    JPanel grid; // stores the labels that display grey/green/orange ie RESULTS
    JLabel[] squares = new JLabel[30]; 
    JPanel keyboard; // stores buttons for the user's input
    JTextField textfield; // stores user input
    JPanel message; // stores Label displaying welcome message/ error message

    // ------- Buttons --------
    JButton deleteButton = new JButton("<"); // Backspace
    JButton enterButton = new JButton("<"); // Enter
    JButton[] letterButtons = new JButton[26]; // Alphabet

    // ------- Additional -------
    Font hel = new Font("Helvetica", Font.BOLD, 30); // Font used in wordle
    static String userInput = "", answer = "";
    static List<String> words = new ArrayList<>();

    // Color Palette from WORDLE
    Color dark = new Color(31, 31, 31); // Increased Brightness from dark mode
    Color yellow = new Color(181, 159, 59);
    Color lightGrey = new Color(128, 130, 132);
    Color green = new Color(83, 141, 79);
    Color darkGrey = new Color(59, 59, 60);
    Color offWhite = new Color(255,255,242);

    Wordle () {
        // -------- Set Up --------
        frame = new JFrame("WORDLE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Required -- can't close window without it
        frame.setFont(hel); // For entire frame
        frame.setSize(600, 800); // Not definite
        frame.setResizable(false); // Remove maximize - Will always be responsive now :)
        frame.setLayout(null);
        frame.getContentPane().setBackground(dark);

        // ----- User Input Field -----
        textfield = new JTextField();
        textfield.setBounds(150, 300, 50, 300); // Position x, y, length, width
        textfield.setHorizontalAlignment(JTextField.CENTER); // Center align the input
        textfield.setFont(hel);
        //textfield.setEditable(false); // Doesn't allow input - only from buttons
        textfield.setBorder(new LineBorder(Color.lightGray, 4));
        textfield.setBackground(dark);
        textfield.setForeground(offWhite);

        // ---- 5x6 Table to display results ----
        grid = new JPanel();
        squares[0] = new JLabel("Label");
        grid.setBounds(100, 75, 350, 400);
		grid.setOpaque(true);
		grid.setBackground(offWhite);
		grid.setLayout(new GridLayout(4,4,10,10));
        grid.add(squares[0]);


        // ----- Finalize the JFrame -----
		frame.add(grid);
		frame.add(deleteButton);
		frame.add(enterButton);
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

    }

    public static void checkWord() {

    }

    public static void newWord() {
        int randomWord = new Random().nextInt(words.size());
        answer = words.get(randomWord);
    }
}
