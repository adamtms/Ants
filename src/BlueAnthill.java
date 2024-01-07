import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.Point;

public class BlueAnthill extends Anthill{
    static String imagePath = "images/BlueAnthill.png";
    static ImageIcon vertexImage = Utils.getScaledImage(imagePath, size);

    BlueAnthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
    }
    
    public void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius, point.y - radius, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
    }
}
