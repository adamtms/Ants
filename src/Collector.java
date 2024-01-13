import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;

public class Collector extends RedAnt implements TakingLarvae {
    private static String topHatPath = "images/TopHat.png";
    private static ImageIcon topHatImage = Utils.getScaledImage(Collector.class.getResource(topHatPath), 15);
    private ArrayList<Larvae> larvaes = new ArrayList<Larvae>();
    private static String stonePath = "images/RealityStone.png";
    private static ImageIcon stoneImage = Utils.getScaledImage(Collector.class.getResource(stonePath), 20);

    Collector(RedAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        JLabel topHatLabel = new JLabel(topHatImage);
        topHatLabel.setBounds(12, 14, topHatImage.getIconWidth(), topHatImage.getIconHeight());
        getPanel().add(topHatLabel);
        getPanel().setComponentZOrder(topHatLabel, 0);
        JLabel stoneLabel = new JLabel(stoneImage);
        stoneLabel.setBounds(5, 25, stoneImage.getIconWidth(), stoneImage.getIconHeight());
        getPanel().add(stoneLabel);
        getPanel().setComponentZOrder(stoneLabel, 0);
        setStrength(getStrength() + 1); // Collectors are stronger than normal ants because they have money to go to the gym
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
            sleep(90);
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

    protected boolean hasLarvae() {
        return larvaes.size() > 0;
    }

    protected void depositLarvae() {
        Larvae larvae = larvaes.remove(larvaes.size() - 1);
        getPanel().remove(larvae.getLabel());
        currentVertex().putLarvae(larvae);
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
        getPanel().setComponentZOrder(larvaeLabel, 1);
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
