import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Stone extends Vertex {
    private static String imagePath = "images/Stone.png";
    private static int size = 150; // just for visuals
    private static ImageIcon vertexImage = Utils.getScaledImage(Stone.class.getResource(imagePath), size);

    Stone(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
        setDifficulty(5);
    }

    protected void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius - 20, point.y - radius - 20, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
        super.draw();
    }
}
