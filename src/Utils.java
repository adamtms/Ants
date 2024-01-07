import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Random;

public class Utils {
    protected static Random random = new Random();

    protected static ImageIcon getScaledImage(String imagePath, int size) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }
}
