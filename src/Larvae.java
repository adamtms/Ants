import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Larvae {
    private static String imagePath = "images/Grub.png";
    private static ImageIcon larveaImage = Utils.getScaledImage(imagePath, 20);
    private static int size = larveaImage.getIconWidth();
    private JLabel larveaLabel;

    protected Larvae(Point point, JLayeredPane layeredPane) {
        larveaLabel = new JLabel(larveaImage);
        larveaLabel.setBounds(point.x - size / 2, point.y - size / 2, size, size);
        layeredPane.add(larveaLabel, Integer.valueOf(1));
    }

    protected JLabel recreateLabel(Point point, JLayeredPane layeredPane) {
        larveaLabel.setBounds(0, 0, 0, 0);
        larveaLabel = new JLabel(larveaImage);
        larveaLabel.setBounds(point.x - size / 2, point.y - size / 2, size, size);
        layeredPane.add(larveaLabel, Integer.valueOf(1));
        return larveaLabel;
    }

    protected JLabel getLabel() {
        return larveaLabel;
    }
}
