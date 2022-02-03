import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

import javax.swing.*;

public class Wordle implements ActionListener 
{
    Path myPath = Paths.get("TESTINGGROUND/src/words.txt");
    static List<String> words = new ArrayList<>();                                        
	words = Files.readAllLines(myPath, StandardCharsets.UTF_8);

    JFrame frame;
    JPanel title;           // stores the title label and instructions pop up window button
    JPanel grid;            // stores the labels that display grey/green/orange ie RESULTS
    JPanel keyboard;        // stores buttons for the user's input
    JTextField textfield;   // stores user input
    JPanel message;         // stores Label displaying welcome message/ error message

    Font hel = new Font("Helvetica", Font.BOLD, 30);      // Font used in wordle
    Random ran = new Random();
    //String userInput = ""; answer = words[ran()];

    // Color Palette
    Color dark = new Color(31, 31, 31);
    Color yellow = new Color(181, 159, 59, 255);
    Color lightGrey = new Color(128, 130, 132, 255);
    Color green = new Color(83, 141, 79, 255);
    Color darkGrey = new Color(59, 59, 60, 255);

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
