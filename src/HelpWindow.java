import java.awt.Color;

import javax.swing.*;

public class HelpWindow {

    JFrame frame = new JFrame();
    static JLabel label = new JLabel(new ImageIcon("C:/Users/Tony/wordle-clone-java-swing/src/help.png"));

    HelpWindow () {

        label.setBounds(2,2,480,530);
        frame.setBackground(Color.black);

        frame.add(label);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500,550);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
}
