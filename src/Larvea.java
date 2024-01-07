import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Larvea {
    static String imagePath = "images/Grub.png";
    static ImageIcon larveaImage = new ImageIcon(imagePath);
    static int size = larveaImage.getIconWidth();
    private JLabel larveaLabel;

    public Larvea(Point point, JLayeredPane layeredPane) {
        larveaLabel = new JLabel(larveaImage);
        larveaLabel.setBounds(point.x - size / 2, point.y - size / 2, size, size);
        layeredPane.add(larveaLabel, Integer.valueOf(1));
    }
}
