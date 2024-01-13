import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * BlueAnt is an abstract class that represents a blue ant.
 * Provides the base image and functionality for blue ants.
 */
public abstract class BlueAnt extends Ant {
    private static String imagePath = "images/BlueAnt.png";
    private static ImageIcon antImage = new ImageIcon(BlueAnt.class.getResource(imagePath));
    private static int size = antImage.getIconWidth();

    protected BlueAnt(BlueAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        JLabel antLabel = new JLabel(antImage);
        antLabel.setBounds(0, 0, size, size);
        getPanel().add(antLabel);
    }
}
