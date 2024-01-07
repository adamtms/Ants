import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Vertex {
    private static String imagePath = "images/vertex.png";
    protected static int size = 120;
    protected static int radius = size / 2;
    private static ImageIcon vertexImage = Utils.getScaledImage(imagePath, size);
    private Point point;
    private int difficulty;
    private ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
    private ArrayList<Larvea> larveas = new ArrayList<Larvea>();
    private JLayeredPane layeredPane;

    protected Vertex(Point point, JLayeredPane layeredPane) {
        this.point = point;
        this.layeredPane = layeredPane;
        this.difficulty = Utils.random.nextInt(5) + 1;
    }

    protected void addLarvea() {
        synchronized(larveas) {
            larveas.add(new Larvea(randomPointInVertex(), layeredPane));
        }
    }

    protected void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        vertexLabel.setBounds(point.x - radius, point.y - radius, size, size);
        layeredPane.add(vertexLabel, Integer.valueOf(0));
    }

    protected double distance(Vertex other) {
        int xDiff = this.point.x - other.point.x;
        int yDiff = this.point.y - other.point.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    protected Point randomPointInVertex() {
        double r = (radius - 10) * Math.sqrt(Math.random());
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

    protected int getDifficulty() {
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

    protected Vertex getRandomNeighbour() {
        return neighbours.get(Utils.random.nextInt(neighbours.size()));
    }
}
