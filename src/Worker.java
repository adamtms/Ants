import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Worker extends BlueAnt implements Attacking, TakingLarvae{
    private static String sackPath = "images/Sack.png";
    private static ImageIcon sackImage = Utils.getScaledImage(sackPath, 20);
    private static String pickaxePath = "images/Pickaxe.png";
    private static ImageIcon pickaxeImage = Utils.getScaledImage(pickaxePath, 20);
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
    }

    protected void doAction(){
        boolean tookLarvae = false;
        if (larvaes.size() < getStrength()) {
            tookLarvae = tryTakeLarvae();
        }
        if (tookLarvae) {
            sleep(100);
            return;
        }
        boolean attacked = false;
        synchronized (currentVertex().getAnts()){
            for (Ant ant : currentVertex().getAnts()){
                if (ant instanceof RedAnt){
                    attack(ant);
                    attacked = true;
                    break;
                }
            }
        }
        if (attacked) {
            sleep(100);
            return;
        }
        super.doAction();
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
        larvaeLabel.setBounds(20 + Utils.random.nextInt(-3, 4), 20 + Utils.random.nextInt(-3, 4),  larvaeLabel.getWidth(), larvaeLabel.getHeight());
        getPanel().add(larvaeLabel);
        getPanel().setComponentZOrder(larvaeLabel, 0);
        return true;
    }
}
