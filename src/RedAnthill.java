import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class RedAnthill extends Anthill{
    static String imagePath = "images/RedAnthill.png";
    static ImageIcon vertexImage = Utils.getScaledImage(imagePath, size);
    RedAnthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
    }
    
    public void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius, point.y - radius, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
    }
}
