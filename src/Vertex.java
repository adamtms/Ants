import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Vertex {
    private static String imagePath = "images/TransparentCircle.png";
    protected static int size = 120;
    protected static int radius = size / 2;
    private static ImageIcon vertexImage = Utils.getScaledImage(imagePath, size + 20);
    private Point point;
    private double difficulty;
    private ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
    private ArrayList<Larvae> larvaes = new ArrayList<Larvae>();
    private Object larvaeLock = new Object();
    private ArrayList<Ant> ants = new ArrayList<Ant>();
    private JLayeredPane layeredPane;

    protected Vertex(Point point, JLayeredPane layeredPane) {
        this.point = point;
        this.layeredPane = layeredPane;
        this.difficulty = Math.random() * 2 + 1;
    }

    protected void addLarvae() {
        synchronized(larvaeLock) {
            larvaes.add(new Larvae(randomPointInVertex(), layeredPane));
        }
    }

    protected void putLarvae(Larvae larvae){
        synchronized(larvaeLock) {
            larvaes.add(larvae);
        }
        Point point = randomPointInVertex();
        larvae.getLabel().setBounds(point.x - size / 2 , point.y - size / 2, size, size);
        layeredPane.add(larvae.getLabel(), Integer.valueOf(1));
    }

    protected void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        vertexLabel.setBounds(point.x - radius - 10, point.y - radius - 10, size + 10, size + 10);
        layeredPane.add(vertexLabel, Integer.valueOf(0));
    }

    protected double distance(Vertex other) {
        int xDiff = this.point.x - other.point.x;
        int yDiff = this.point.y - other.point.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    protected Point randomPointInVertex() {
        double r = (radius - 20) * Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;
        return new Point((int) (r * Math.cos(theta)) + point.x, 
                        (int) (r * Math.sin(theta)) + point.y);
    }

    protected JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    protected Point getPoint() {
        return point;
    }

    protected double getDifficulty() {
        return difficulty;
    }

    protected void setDifficulty(int difficulty) {
        if (difficulty < 1 || difficulty > 10) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 10");
        }
        this.difficulty = difficulty;
    }

    protected void addNeighbour(Vertex other) {
        neighbours.add(other);
    }

    protected ArrayList<Vertex> getNeighbours() {
        return neighbours;
    }

    protected ArrayList<Ant> getAnts() {
        return ants;
    }

    protected void addAnt(Ant ant) {
        synchronized(ants) {
            ants.add(ant);
        }
    }

    protected void removeAnt(Ant ant) {
        synchronized(ants) {
            ants.remove(ant);
        }
    }

    protected Vertex getRandomNeighbour() {
        return neighbours.get(Utils.random.nextInt(neighbours.size()));
    }

    protected Object getLarvaeLock() {
        return larvaeLock;
    }

    protected Larvae takeLarvae() {
        if (larvaes.isEmpty()) {
            return null;
        }
        Larvae larvae = larvaes.remove(larvaes.size() - 1);
        layeredPane.remove(larvae.getLabel());
        return larvae;
    }
}
