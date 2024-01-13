import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The Ant class represents an abstract ant in the ant simulation.
 * It extends the Thread class and provides common functionality and attributes for all ant types.
 */
public abstract class Ant extends Thread {
    private static String imagePath = "images/RedAnt.png";
    private static ImageIcon antImage = new ImageIcon(Ant.class.getResource(imagePath));
    private static int size = antImage.getIconWidth();
    private JPanel antPanel;
    private ArrayList<Vertex> path;
    private int strength;
    private int health;
    private boolean alive = true;
    private String name;

    protected Ant(Anthill anthill, JLayeredPane layeredPane) {
        this.antPanel = new JPanel();
        this.antPanel.setSize(size, size);
        this.antPanel.setOpaque(false);
        this.path = new ArrayList<Vertex>();
        this.path.add(anthill);
        layeredPane.add(antPanel, Integer.valueOf(2));

        Point randomPoint = anthill.randomPointInVertex();
        randomPoint.x -= size / 2;
        randomPoint.y -= size / 2;
        this.antPanel.setLocation(randomPoint);
        this.strength = Utils.random.nextInt(1, 5);
        this.health = Utils.random.nextInt(10, 15);
        this.name = Utils.randomName();

        antPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                receiveDamage(9999);
            }
        });
        synchronized (anthill.getAnts()) {
            anthill.addAnt(this);
        }
    }

    protected String getAntName() {
        return name;
    }

    protected void die() {
        alive = false;
        antPanel.setBounds(0, 0, 0, 0);
        System.out.println(name + " has died");
    }

    protected boolean receiveDamage(int damage) {
        boolean died = false;
        synchronized (this) {
            System.out.println(name + " received " + damage + " damage");
            health -= damage;
            if (health <= 0) {
                die();
                died = true;
                currentVertex().removeAnt(this);
            }
        }
        return died;
    }

    protected int getStrength() {
        return strength;
    }

    protected int setStrength(int strength) {
        return this.strength = strength;
    }

    protected JPanel getPanel() {
        return antPanel;
    }

    protected Vertex currentVertex() {
        return path.getLast();
    }

    protected Vertex getAnthill() {
        return path.get(0);
    }

    protected ArrayList<Vertex> getPath() {
        return path;
    }

    protected void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the ant's behavior in a continuous loop until it is no longer alive.
     * The ant performs its action and then checks if it is still alive.
     * If the ant is no longer alive, the loop breaks.
     */
    public void run() {
        while (true) {
            synchronized (this) {
                if (!alive) {
                    break;
                }
            }
            doAction();
        }
        synchronized (currentVertex().getAnts()) {
            currentVertex().removeAnt(this);
        }
    }

    protected void doAction() {
        randomMove();
        sleep(300);
        return;
    }

    private void randomMove() {
        Vertex nextVertex = currentVertex().getRandomNeighbour();
        move(nextVertex);
    }

    protected void move(Vertex nextVertex) {
        synchronized (currentVertex().getAnts()) {
            currentVertex().removeAnt(this);
        }

        int steps = (int) (10 * nextVertex.getDifficulty() * currentVertex().distance(nextVertex) / Vertex.size);
        int delay = 40;

        Point start = antPanel.getLocation();
        Point end = nextVertex.randomPointInVertex();

        end.x -= size / 2;
        end.y -= size / 2;

        int dx = (end.x - start.x) / steps;
        int dy = (end.y - start.y) / steps;

        for (int i = 0; i < steps; i++) {
            int x = start.x + dx * i;
            int y = start.y + dy * i;
            antPanel.setLocation(x, y);
            sleep(delay);
        }

        path.add(nextVertex);
        synchronized (nextVertex.getAnts()) {
            nextVertex.addAnt(this);
        }
    }

    protected String getInfo() {
        synchronized (this) {
            if (alive) {
                return name + "\nStrength: " + strength + "\nHealth: " + health;
            }
        }
        return null;
    }
}