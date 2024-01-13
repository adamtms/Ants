import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The World class represents the simulation world for the Ants simulation.
 * It contains the graphical user interface (GUI) components, such as the frame,
 * layered pane, buttons, and text area. It also manages the initialization and
 * updating of the ants, vertices, and anthills in the simulation.
 */
public class World {
    private static String imagePath = "images/grass.jpeg";
    private JFrame frame;
    private JLayeredPane layeredPane;
    static int width = 1200;
    static int height = 801;
    static int numColumns = 15;
    static int numRows = 12;
    static int sidePanelWidth = 250;
    static int addAntsPanelHeight = 100;
    static int addLarvaePanelHeight = 70;
    private BlueAnthill blueAnthill;
    private RedAnthill redAnthill;
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private ArrayList<Ant> ants = new ArrayList<Ant>();
    private JTextArea textArea;

    /**
     * Represents the world in the Ants simulation.
     * Initializes the GUI components and anthills.
     */
    public World() {
        frame = new JFrame("Ants simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width + sidePanelWidth, height);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width + sidePanelWidth, height));

        ImageIcon backgroundImage = new ImageIcon(World.class.getResource(imagePath));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, width, height);
        layeredPane.add(backgroundLabel, Integer.valueOf(-1));

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEnabled(false);
        scrollPane.setBounds(width, addAntsPanelHeight, sidePanelWidth,
                height - addAntsPanelHeight - addLarvaePanelHeight);
        layeredPane.add(scrollPane, Integer.valueOf(0));

        createAntButtons();
        createLarvaeButtons();

        initializeAnthills();
        frame.add(layeredPane);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createAntButtons() {
        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setBounds(width, 0, sidePanelWidth, addAntsPanelHeight);
        JButton addWorkerButton = new PrettyButton("Worker", Color.BLUE);
        addWorkerButton.addActionListener(e -> addWorker());
        JButton addDroneButton = new PrettyButton("Drone", Color.BLUE);
        addDroneButton.addActionListener(e -> addDrone());
        JButton addCollectorButton = new PrettyButton("Collector", Color.RED);
        addCollectorButton.addActionListener(e -> addCollector());
        JButton addSoldierButton = new PrettyButton("Soldier", Color.RED);
        addSoldierButton.addActionListener(e -> addSoldier());
        JButton addBlundererButton = new PrettyButton("Blunderer", Color.RED);
        addBlundererButton.addActionListener(e -> addBlunderer());
        addWorkerButton.setBounds(0, 0, sidePanelWidth / 2, addAntsPanelHeight / 2);
        addDroneButton.setBounds(sidePanelWidth / 2, 0, sidePanelWidth / 2, addAntsPanelHeight / 2);
        addCollectorButton.setBounds(0, addAntsPanelHeight / 2, sidePanelWidth / 3, addAntsPanelHeight / 2);
        addSoldierButton.setBounds(sidePanelWidth / 3, addAntsPanelHeight / 2, sidePanelWidth / 3,
                addAntsPanelHeight / 2);
        addBlundererButton.setBounds(sidePanelWidth * 2 / 3, addAntsPanelHeight / 2, sidePanelWidth / 3,
                addAntsPanelHeight / 2);

        buttonPanel.add(addWorkerButton);
        buttonPanel.add(addDroneButton);
        buttonPanel.add(addCollectorButton);
        buttonPanel.add(addSoldierButton);
        buttonPanel.add(addBlundererButton);

        layeredPane.add(buttonPanel, Integer.valueOf(0));
    }

    private void createLarvaeButtons() {
        Color color = Color.GREEN.darker();
        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setBounds(width, height - addLarvaePanelHeight, sidePanelWidth, addLarvaePanelHeight);
        JButton add1LarvaeButton = new PrettyButton("1 Larvae", color);
        add1LarvaeButton.addActionListener(e -> initializeLarvaes(1));
        add1LarvaeButton.setBounds(0, 0, sidePanelWidth / 3, addLarvaePanelHeight - 28);
        buttonPanel.add(add1LarvaeButton);
        JButton add5LarvaeButton = new PrettyButton("5 Larvaes", color);
        add5LarvaeButton.addActionListener(e -> initializeLarvaes(5));
        add5LarvaeButton.setBounds(sidePanelWidth / 3, 0, sidePanelWidth / 3, addLarvaePanelHeight - 28);
        buttonPanel.add(add5LarvaeButton);
        JButton add10LarvaeButton = new PrettyButton("10 Larvaes", color);
        add10LarvaeButton.addActionListener(e -> initializeLarvaes(10));
        add10LarvaeButton.setBounds(sidePanelWidth * 2 / 3, 0, sidePanelWidth / 3, addLarvaePanelHeight - 28);
        buttonPanel.add(add10LarvaeButton);
        layeredPane.add(buttonPanel, Integer.valueOf(0));
    }

    private void initializeAnthills() {
        int x = Utils.random.nextInt(Anthill.radius, width / 3 - Anthill.radius);
        int y = Utils.random.nextInt(Anthill.radius, height / 3 - Anthill.radius);
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

    /**
     * Initializes the vertices in the world. Maximum number of vertices is 32 (including Anthills).
     * It will not initialize vertices if the number of vertices is already greater than 32.
     * It cannot decrease the number of vertices.
     * 
     * @param numVertices the number of vertices the world should have initialized
     */
    public void initializeVertices(int numVertices) {
        boolean intersects = false;
        Vertex vertex;
        double distance;
        for (int i = vertices.size(); i <= numVertices; i++) {
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
            } else {
                vertices.add(vertex);
                vertex.draw();
                for (Vertex neighbour : vertex.getNeighbours()) {
                    neighbour.addNeighbour(vertex);
                }
            }
        }
    }

    /**
     * Initializes the specified number of larvaes in the world.
     *
     * @param numLarvaes the number of larvaes to initialize
     */
    public void initializeLarvaes(int numLarvaes) {
        for (int i = 0; i < numLarvaes; i++) {
            Vertex vertex = vertices.get(Utils.random.nextInt(2, vertices.size()));
            vertex.addLarvae();
        }
    }

    private void addWorker() {
        synchronized (ants) {
            Ant ant = new Worker(blueAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    private void addDrone() {
        synchronized (ants) {
            Ant ant = new Drone(blueAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    /**
     * Initializes a specified number of blue ants in the world.
     * 
     * @param numAnts the number of blue ants to initialize
     */
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
        synchronized (ants) {
            Ant ant = new Collector(redAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    private void addSoldier() {
        synchronized (ants) {
            Ant ant = new Soldier(redAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    private void addBlunderer() {
        synchronized (ants) {
            Ant ant = new Blunderer(redAnthill, layeredPane);
            ants.add(ant);
            ant.start();
        }
    }

    /**
     * Initializes a specified number of red ants in the world.
     * 
     * @param numAnts the number of red ants to initialize
     */
    public void initializeRedAnts(int numAnts) {
        if (redAnthill == null) {
            System.out.println("Red anthill not initialized");
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

    /**
     * Initializes the ants in the world.
     * It will initialize the same number of blue and red ants.
     * 
     * @param numAnts the number of ants to initialize in each anthill
     */
    public void initializeAnts(int numAnts) {
        initializeRedAnts(numAnts);
        initializeBlueAnts(numAnts);
    }

    /**
     * Runs the simulation of the world.
     * This method continuously adds larvaes to the world and updates the ants' information. 
     * It runs indefinitely until interrupted.
     */
    public void run() {
        Vertex vertex;
        int withoutAdding = 0;
        while (true) {
            vertex = vertices.get(Utils.random.nextInt(2, vertices.size()));
            updateAnts();
            if (withoutAdding == 100) {
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
        synchronized (ants) {
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
}