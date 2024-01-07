import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Leaf extends Vertex{
    static String imagePath = "images/leaf.png";
    static int size = 150; //just for visuals
    static ImageIcon vertexImage = Utils.getScaledImage(imagePath, size);

    Leaf(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
    }

    public void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius, point.y - radius - 20, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
        super.draw();
    }
}
