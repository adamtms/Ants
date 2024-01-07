import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Vertex {
    static String imagePath = "images/vertex.png";
    static int size = 120;
    static int radius = size / 2;
    static ImageIcon vertexImage = Utils.getScaledImage(imagePath, size);
    private Point point;
    private int difficulty;
    private ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
    private ArrayList<Larvea> larveas = new ArrayList<Larvea>();
    private JLayeredPane layeredPane;

    public Vertex(Point point, JLayeredPane layeredPane) {
        this.point = point;
        this.layeredPane = layeredPane;
        this.difficulty = Utils.random.nextInt(5) + 1;
    }

    public void addLarvea() {
        synchronized(larveas) {
            larveas.add(new Larvea(randomPointInVertex(), layeredPane));
        }
    }

    public void draw() {
        JLabel vertexLabel = new JLabel(vertexImage);
        vertexLabel.setBounds(point.x - radius, point.y - radius, size, size);
        layeredPane.add(vertexLabel, Integer.valueOf(0));
    }

    public double distance(Vertex other) {
        int xDiff = this.point.x - other.point.x;
        int yDiff = this.point.y - other.point.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public Point randomPointInVertex() {
        double r = (radius - 10) * Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;
        return new Point((int) (r * Math.cos(theta)) + point.x, 
                        (int) (r * Math.sin(theta)) + point.y);
    }

    protected JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    public Point getPoint() {
        return point;
    }

    public int getDifficulty() {
        return difficulty;
    }

    protected void setDifficulty(int difficulty) {
        if (difficulty < 1 || difficulty > 10) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 10");
        }
        this.difficulty = difficulty;
    }

    public void addNeighbour(Vertex other) {
        neighbours.add(other);
    }

    public ArrayList<Vertex> getNeighbours() {
        return neighbours;
    }

    public Vertex getRandomNeighbour() {
        return neighbours.get(Utils.random.nextInt(neighbours.size()));
    }
}
