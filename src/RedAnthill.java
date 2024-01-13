import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class RedAnthill extends Anthill {
    private static String imagePath = "images/RedAnthill.png";
    private static ImageIcon vertexImage = Utils.getScaledImage(RedAnthill.class.getResource(imagePath), size);

    protected RedAnthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
    }

    protected void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius, point.y - radius, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
    }
}
