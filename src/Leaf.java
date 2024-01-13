import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.util.ArrayList;

public class Leaf extends Vertex {
    private static String imagePath = "images/leaf.png";
    private static int size = 150; // just for visuals
    private static ImageIcon vertexImage = Utils.getScaledImage(Leaf.class.getResource(imagePath), size);
    private static ArrayList<Ant> emptyAnts = new ArrayList<Ant>();

    Leaf(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
    }

    protected void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius, point.y - radius - 20, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
        super.draw();
    }

    protected ArrayList<Ant> getAnts() {
        return emptyAnts;
    }
}
