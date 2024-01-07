import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Stone extends Vertex{
    static String imagePath = "images/Stone.png";
    static int size = 150; //just for visuals
    static ImageIcon vertexImage = Utils.getScaledImage(imagePath, size);
    Stone(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
        setDifficulty(10);
    }

    public void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        Point point = getPoint();
        vertexLabel.setBounds(point.x - radius - 20, point.y - radius - 20, size, size);
        this.getLayeredPane().add(vertexLabel, Integer.valueOf(0));
        super.draw();
    }
}
