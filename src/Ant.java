import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Ant extends Thread {
    private static String imagePath = "images/RedAnt.png";
    private static ImageIcon antImage = new ImageIcon(imagePath);
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
                createKillFrame();
            }
        });
        synchronized (anthill.getAnts()) {
            anthill.addAnt(this);
        }
    }

    private void createKillFrame(){
        JFrame killFrame = new JFrame();
        killFrame.setLayout(null);
        killFrame.setSize(300, 100);
        killFrame.setTitle(name);
        killFrame.setLocationRelativeTo(null);
        killFrame.setVisible(true);
        killFrame.setResizable(false);
        JLabel killLabel = new JLabel("Kill or spare?");
        killLabel.setBounds(110,0,300,20);
        killFrame.add(killLabel);
        JButton killButton = new PrettyButton("Kill", Color.RED.darker());
        killButton.setBounds(0,23,150,50);
        killFrame.add(killButton);
        killButton.addActionListener(e -> {
            synchronized (this) {
                if (alive) {
                    die();
                    createYouAreAMonsterFrame();
                }
            }
            killFrame.dispose();
        });
        JButton cancelButton = new PrettyButton("Spare", Color.YELLOW.darker());
        cancelButton.setBounds(150,23,150,50);
        killFrame.add(cancelButton);
        cancelButton.addActionListener(e -> {//JOptionPane.showMessageDialog(null,
                //"You are a good person. This ant will remember your kindness.");
            killFrame.dispose();});
    }

    private void createYouAreAMonsterFrame(){
        JFrame monsterFrame = new JFrame();
        monsterFrame.setLayout(null);
        monsterFrame.setSize(300, 100);
        monsterFrame.setTitle(name);
        monsterFrame.setLocationRelativeTo(null);
        monsterFrame.setVisible(true);
        monsterFrame.setResizable(false);
        TextArea monsterLabel = new TextArea("You are a monster! \nHow could you kill an innocent creature\nand act as if nothing has happened?");
        monsterLabel.setEnabled(false);

        monsterLabel.setBounds(0, 0, 300, 100);
        monsterFrame.add(monsterLabel);
    }

    protected String getAntName() {
        return name;
    }

    protected void die() {
        alive = false;
        antPanel.setBounds(0,0,0,0);
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

    protected Vertex getAnthill(){
        return path.get(0);
    }

    protected ArrayList<Vertex> getPath() {
        return path;
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
            doAction();
        }
        synchronized(currentVertex().getAnts()) {
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

        path.add(nextVertex);
        synchronized (nextVertex.getAnts()) {
            nextVertex.addAnt(this);
        }
    }

    protected String getInfo(){
        synchronized (this) {
            if (alive) {
                return name + "\nStrength: " + strength + "\nHealth: " + health;
            }
        }
        return null;
    }
}
