import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class World {
    private static String imagePath = "images/grass.jpeg";
    private JFrame frame;
    private JLayeredPane layeredPane;
    static int width = 1200;
    static int height = 801;
    static int numColumns = 15;
    static int numRows = 12;
    static int sidePanelWidth = 250;
    private BlueAnthill blueAnthill;
    private RedAnthill redAnthill;
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private ArrayList<Ant> ants = new ArrayList<Ant>();
    private JTextArea textArea;
    
    public World() {
        frame = new JFrame("Ants simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width + sidePanelWidth, height);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width + sidePanelWidth, height));

        // Set background image
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon backgroundImage = new ImageIcon(imagePath);
            JLabel backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setBounds(0, 0, width, height);
            layeredPane.add(backgroundLabel, Integer.valueOf(-1));
        } else {
            System.out.println("Image file not found: " + imagePath);
        }

        // Set side panel
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        scrollPane.setBounds(width, 0, sidePanelWidth, height);
        layeredPane.add(scrollPane, Integer.valueOf(0));



        frame.add(layeredPane);
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
    public void initializeLarvaes(int numLarvaes) {
        for (int i = 0; i < numLarvaes; i++) {
            Vertex vertex = vertices.get(Utils.random.nextInt(2, vertices.size()));
            vertex.addLarvae();
        }
    }

    private void addWorker() {
        synchronized(ants){
            Ant ant = new Worker(blueAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    private void addDrone() {
        synchronized(ants){
            Ant ant = new Drone(blueAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    public void initializeBlueAnts(int numAnts) {
        if (blueAnthill == null) {
            System.out.println("Blue anthill not initialized");
            return;
        }
        double randomValue;
        for (int i = 0; i < numAnts; i++) {
            randomValue = Math.random();
            if (randomValue < 0.6) {
                addWorker();
            } else {
                addDrone();
            }
        }
    }

    private void addCollector() {
        synchronized(ants){
            Ant ant = new Collector(redAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    private void addSoldier() {
        synchronized(ants){
            Ant ant = new Soldier(redAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    private void addBlunderer() {
        synchronized(ants){
            Ant ant = new Blunderer(redAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    public void initializeRedAnts(int numAnts) {
        if (redAnthill == null) {
            System.out.println("Blue anthill not initialized");
            return;
        }
        double randomValue;
        for (int i = 0; i < numAnts; i++) {
            randomValue = Math.random();
            if (randomValue <= 0.3) {
                addSoldier();
            } else if (randomValue <= 0.6) {
                addCollector();
            } else {
                addBlunderer();
            }
        }
    }

    public void initializeAnts(int numAnts) {
        initializeRedAnts(numAnts);
        initializeBlueAnts(numAnts);
    }

    public void run(){
        Vertex vertex;
        int withoutAdding = 0;
        while(true){
            vertex = vertices.get(Utils.random.nextInt(2, vertices.size()));
            updateAnts();
            if (withoutAdding == 100){
                withoutAdding = 0;
                vertex.addLarvae();
            }
            withoutAdding++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void updateAnts() {
        String text = "";
        String tempText;
        ArrayList<Ant> antsToRemove = new ArrayList<>();
        for (Ant ant : ants) {
            tempText = ant.getInfo();
            if (tempText == null) {
                antsToRemove.add(ant);
                continue;
            }
            text += tempText;
            text += "\n------------------------\n"; // Pretty divider
        }
        textArea.setText(text);
        for (Ant ant : antsToRemove) {
            ants.remove(ant);
        }
    }
}