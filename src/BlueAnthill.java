import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.Point;


/**
 * Represents a blue anthill.
 */
public class BlueAnthill extends Anthill {
    private static String imagePath = "images/BlueAnthill.png";
    private static ImageIcon vertexImage = Utils.getScaledImage(BlueAnthill.class.getResource(imagePath), size);

    protected BlueAnthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
    }

    protected void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius, point.y - radius, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
    }
}
