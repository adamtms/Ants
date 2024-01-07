import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Ant extends Thread {
    private static String imagePath = "images/RedAnt.png";
    private static ImageIcon antImage = new ImageIcon(imagePath);;
    private static int size = antImage.getIconWidth();
    private JPanel antPanel;
    private ArrayList<Vertex> path;
    private int strength;
    private int health;
    private boolean alive = true;

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
    }

    protected void die() {
        alive = false;
        antPanel.setBounds(0,0,0,0);
    }

    protected boolean receiveDamage(int damage) {
        boolean died = false;
        synchronized (this) {
            health -= damage;
            if (health <= 0) {
                die();
                died = true;
            }
        }
        return died;
    }

    protected int getStrength() {
        return strength;
    }

    protected JPanel getPanel() {
        return antPanel;
    }

    private Vertex currentVertex() {
        return path.getLast();
    }

    protected void sleep(int duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            synchronized (this) {
                if (!alive) {
                    break;
                }
            }
            Vertex currentVertex = currentVertex();
            Vertex nextVertex = currentVertex.getRandomNeighbour();
            smoothMove(nextVertex);
            path.add(nextVertex);
        }
    }

    private void smoothMove(Vertex nextVertex) {
        int steps = (int) (10 * nextVertex.getDifficulty() * currentVertex().distance(nextVertex) / Vertex.size);
        int delay = 40; // Delay between each step in milliseconds

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
    }
}
