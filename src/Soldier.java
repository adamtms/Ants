import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Soldier extends RedAnt implements Attacking{
    private static String swordPath = "images/Sword.png";
    private static ImageIcon swordImage = Utils.flipImageIcon(Utils.getScaledImage(swordPath, 18));
    private static String shieldPath = "images/Shield.png";
    private static ImageIcon shieldImage = Utils.flipImageIcon(Utils.getScaledImage(shieldPath, 35));

    Soldier(RedAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        JLabel swordLabel = new JLabel(swordImage);
        swordLabel.setBounds(10, 30, swordImage.getIconWidth(), swordImage.getIconHeight());
        getPanel().add(swordLabel);
        getPanel().setComponentZOrder(swordLabel, 0);
        JLabel shieldLabel = new JLabel(shieldImage);
        shieldLabel.setBounds(8, 10, shieldImage.getIconWidth(), shieldImage.getIconHeight());
        getPanel().add(shieldLabel);
        getPanel().setComponentZOrder(shieldLabel, 2);
        setStrength(getStrength() + 2);
    }

    protected void doAction(){
        boolean attacked = false;
        synchronized (currentVertex().getAnts()){
            for (Ant ant : currentVertex().getAnts()){
                if (ant instanceof BlueAnt){
                    attack(ant);
                    attacked = true;
                    break;
                }
            }
        }
        if (attacked) {
            sleep(130);
            return;
        }
        super.doAction();
    }

    private void attack(Ant ant) {
        ant.receiveDamage(getStrength());
    }
}
