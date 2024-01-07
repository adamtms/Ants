import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

public abstract class Anthill extends Vertex{
    static String imagePath;
    static ImageIcon vertexImage;
    Anthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
        setDifficulty(1);
    }

}
