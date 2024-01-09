import java.awt.Point;

import javax.swing.JLayeredPane;

public abstract class Anthill extends Vertex{
    private int countLarvae = 0;

    protected Anthill(Point point, JLayeredPane layeredPane) {
        super(point, layeredPane);
        setDifficulty(1);
    }

    protected void putLarvae(Larvae larvae){
        synchronized(getLarvaeLock()) {
            countLarvae += 1;
        }
    }
    
    protected int getCountLarvae() {
        return countLarvae;
    }
}
