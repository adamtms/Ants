import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public abstract class BlueAnt extends Ant{
    private static String imagePath = "images/BlueAnt.png";
    private static ImageIcon antImage = new ImageIcon(imagePath);;
    private static int size = antImage.getIconWidth();

    protected BlueAnt(BlueAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        JLabel antLabel = new JLabel(antImage);
        antLabel.setBounds(0, 0, size, size);
        getPanel().add(antLabel);
    }
}
