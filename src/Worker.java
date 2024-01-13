import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * Represents a Worker ant that can attack red ants and gather larvae.
 * A Worker ant has a sack and a pickaxe.
 */
public class Worker extends BlueAnt implements Attacking, TakingLarvae {
    private static String sackPath = "images/Sack.png";
    private static ImageIcon sackImage = Utils.getScaledImage(Worker.class.getResource(sackPath), 20);
    private static String pickaxePath = "images/Pickaxe.png";
    private static ImageIcon pickaxeImage = Utils.getScaledImage(Worker.class.getResource(pickaxePath), 20);
    private ArrayList<Larvae> larvaes = new ArrayList<Larvae>();

    Worker(BlueAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        JLabel sackLabel = new JLabel(sackImage);
        sackLabel.setBounds(18, 18, sackImage.getIconWidth(), sackImage.getIconHeight());
        getPanel().add(sackLabel);
        getPanel().setComponentZOrder(sackLabel, 0);
        JLabel pickaxeLabel = new JLabel(pickaxeImage);
        pickaxeLabel.setBounds(32, 24, pickaxeImage.getIconWidth(), pickaxeImage.getIconHeight());
        getPanel().add(pickaxeLabel);
        getPanel().setComponentZOrder(pickaxeLabel, 0);
        receiveDamage(-3); // workers should be more tanky
    }

    protected void doAction() {
        if (currentVertex() == getAnthill() && larvaes.size() > 0) {
            depositLarvae();
            sleep(200);
            return;
        }
        if (larvaes.size() == getStrength()) {
            moveToHome();
            sleep(250);
            return;
        }
        boolean tookLarvae = tryTakeLarvae();
        if (tookLarvae) {
            sleep(100);
            return;
        }
        boolean attacked = false;
        synchronized (currentVertex().getAnts()) {
            for (Ant ant : currentVertex().getAnts()) {
                if (ant instanceof RedAnt) {
                    attack(ant);
                    attacked = true;
                    break;
                }
            }
        }
        if (attacked) {
            sleep(140);
            return;
        }
        super.doAction();
    }

    private void moveToHome() {
        if (currentVertex().getNeighbours().contains(getAnthill())) {
            move(getAnthill());
        } else {
            move(getPath().get(getPath().size() - 2));
            getPath().removeLast();
            getPath().removeLast();
        }
    }

    private void depositLarvae() {
        Larvae larvae = larvaes.remove(larvaes.size() - 1);
        getPanel().remove(larvae.getLabel());
        getAnthill().putLarvae(larvae);
    }

    private void attack(Ant ant) {
        ant.receiveDamage(getStrength());
    }

    private boolean tryTakeLarvae() {
        Larvae larvae;
        synchronized (currentVertex().getLarvaeLock()) {
            larvae = currentVertex().takeLarvae();
            if (larvae == null) {
                return false;
            }
        }
        larvaes.add(larvae);
        JLabel larvaeLabel = larvae.getLabel();
        larvaeLabel.setBounds(20 + Utils.random.nextInt(-3, 4), 20 + Utils.random.nextInt(-3, 4),
                larvaeLabel.getWidth(), larvaeLabel.getHeight());
        getPanel().add(larvaeLabel);
        getPanel().setComponentZOrder(larvaeLabel, 0);
        return true;
    }

    @Override
    protected void die() {
        synchronized (currentVertex().getLarvaeLock()) {
            for (Larvae larvae : larvaes) {
                getPanel().remove(larvae.getLabel());
                currentVertex().putLarvae(larvae);
            }
        }
        super.die();
    }
}
