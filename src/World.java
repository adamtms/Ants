import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.awt.Point;

public class World {
    private JFrame frame;
    private JLayeredPane layeredPane;
    static int width = 1200;
    static int height = 801;
    static int numColumns = 15;
    static int numRows = 12;
    private BlueAnthill blueAnthill;
    private RedAnthill redAnthill;
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    
    public World() {
        frame = new JFrame("Ants simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));

        // Set background image
        String imagePath = "images/grass.jpeg";
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon backgroundImage = new ImageIcon(imagePath);
            JLabel backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setBounds(0, 0, width, height);
            layeredPane.add(backgroundLabel, Integer.valueOf(-1));
        } else {
            System.out.println("Image file not found: " + imagePath);
        }

        frame.add(layeredPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void initializeAnthills(){
        int x = Utils.random.nextInt(Anthill.radius, width / 3 - Anthill.radius);
        int y = Utils.random.nextInt(Anthill.radius, height  / 3 - Anthill.radius);
        BlueAnthill blueAnthill = new BlueAnthill(new Point(x, y), layeredPane);
        RedAnthill redAnthill = new RedAnthill(new Point(width - x, height - y), layeredPane);
        this.blueAnthill = blueAnthill;
        this.redAnthill = redAnthill;
        vertices.add(blueAnthill);
        vertices.add(redAnthill);
        blueAnthill.draw();
        redAnthill.draw();
    }

    private Vertex newVertex(Point point, JLayeredPane layeredPane) {
        double randomValue = Math.random();
        Vertex vertex;
        if (randomValue < 0.15) {
            vertex = new Stone(point, layeredPane);
        } else if (randomValue < 0.3) {
            vertex = new Leaf(point, layeredPane);
        } else {
            vertex = new Vertex(point, layeredPane);
        }
        
        return vertex;
    }

    public void initializeVertices(int numVertices) {
        boolean intersects = false;
        Vertex vertex;
        double distance;
        for (int i = 0; i < numVertices; i++) {
            int x = Utils.random.nextInt(Vertex.radius, width - Vertex.radius);
            int y = Utils.random.nextInt(Vertex.radius, height - Vertex.radius);
            vertex = newVertex(new Point(x, y), layeredPane);
            intersects = false;
            for (Vertex other : vertices) {
                distance = vertex.distance(other);
                if (distance < Vertex.radius * 2) {
                    intersects = true;
                    break;
                }
                if (distance < Vertex.radius * 4) {
                    vertex.addNeighbour(other);
                }
            }
            if (intersects) {
                i--;
            }
            else {
                vertices.add(vertex);
                vertex.draw();
                for (Vertex neighbour : vertex.getNeighbours()) {
                    neighbour.addNeighbour(vertex);
                }
            }
        }
    }
    public void initializeLarveas(int numLarveas) {
        for (int i = 0; i < numLarveas; i++) {
            Vertex vertex = vertices.get(Utils.random.nextInt(2, vertices.size()));
            vertex.addLarvea();
        }
    }

    public void initializeAnts(int numAnts) {
        for (int i = 0; i < numAnts; i++) {
            Anthill anthill = Utils.random.nextInt(0, 2) == 0 ? blueAnthill : redAnthill;
            new Ant(anthill, layeredPane).execute();
        }
    }

    public void run(){
        Vertex vertex;
        while(true){
            vertex = vertices.get(Utils.random.nextInt(2, vertices.size()));
            vertex.addLarvea();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}