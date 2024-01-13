import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents a Drone that does nothing but move, it has a dunce hat.
 */
public class Drone extends BlueAnt {
    static String duncePath = "images/Dunce.png";
    static ImageIcon DunceImage = Utils.getScaledImage(Drone.class.getResource(duncePath), 25);

    Drone(BlueAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        receiveDamage(5);

        JLabel dummyHatLabel = new JLabel(DunceImage);
        dummyHatLabel.setBounds(30, 0, DunceImage.getIconWidth(), DunceImage.getIconHeight());
        getPanel().add(dummyHatLabel);
        getPanel().setComponentZOrder(dummyHatLabel, 0);
    }
}
