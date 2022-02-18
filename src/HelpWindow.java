import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HelpWindow {

    JFrame frame = new JFrame();

    

    HelpWindow () throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/imgs/help.png"));
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(2,2,480,530);
        frame.setBackground(Color.black);

        frame.add(label);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500,550);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    
}
