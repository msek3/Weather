import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    public ImagePanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            image = null;
        }
    }
    public JLabel createImage(){
        return new JLabel(new ImageIcon(image));
    }
}
