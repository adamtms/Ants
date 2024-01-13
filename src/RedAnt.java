import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * RedAnt is an abstract class that represents a red ant.
 * Provides the base image and functionality for red ants.
 */
public abstract class RedAnt extends Ant {
    private static String imagePath = "images/RedAnt.png";
    private static ImageIcon antImage = new ImageIcon(RedAnt.class.getResource(imagePath));
    private static int size = antImage.getIconWidth();

    protected RedAnt(RedAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        JLabel antLabel = new JLabel(antImage);
        antLabel.setBounds(0, 0, size, size);
        getPanel().add(antLabel);
    }
}
