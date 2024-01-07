import java.awt.Point;

import javax.swing.JLayeredPane;

public abstract class Anthill extends Vertex{
    protected Anthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
        setDifficulty(1);
    }

}
