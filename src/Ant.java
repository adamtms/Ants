import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Ant extends SwingWorker<Void, Void> {
    private static String imagePath = "images/RedAnt.png";
    private static ImageIcon antImage = new ImageIcon(imagePath);;
    private static int size = antImage.getIconWidth();
    private JPanel antPanel;
    private ArrayList<Vertex> path;

    public Ant(Anthill anthill, JLayeredPane layeredPane) {
        this.antPanel = new JPanel();
        this.antPanel.setSize(size, size);
        this.antPanel.setOpaque(false);
        JLabel antLabel = new JLabel(antImage);
        antLabel.setBounds(0, 0, size, size);
        this.antPanel.add(antLabel);
        this.path = new ArrayList<Vertex>();
        this.path.add(anthill);
        layeredPane.add(antPanel, Integer.valueOf(2));
        
    }

    @Override
    protected Void doInBackground() throws Exception {
        Point currentCoordinate = antPanel.getLocation();

        while (!isCancelled()) {
            Vertex nextVertex = path.getLast().getRandomNeighbour();
            Point nextCoordinate = nextVertex.randomPointInVertex();
            nextCoordinate.x -= size / 2;
            nextCoordinate.y -= size / 2;
            smoothMove(currentCoordinate, nextCoordinate);
            currentCoordinate = nextCoordinate;
            path.add(nextVertex);
        }
        return null;
    }

    private void smoothMove(Point start, Point end) {
        int steps = 10;
        int delay = 50; // Delay between each step in milliseconds

        int dx = (end.x - start.x) / steps;
        int dy = (end.y - start.y) / steps;

        for (int i = 0; i < steps; i++) {
            int x = start.x + dx * i;
            int y = start.y + dy * i;
            antPanel.setLocation(x, y);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
