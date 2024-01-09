import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Blunderer extends Collector{
    private static String duncePath = "images/Dunce.png";
    private static ImageIcon dunceImage = Utils.getScaledImage(duncePath, 30);


    Blunderer(RedAnthill anthill, JLayeredPane layeredPane) {
        super(anthill, layeredPane);
        JLabel dunceLabel = new JLabel(dunceImage);
        dunceLabel.setBounds(30, 10, dunceImage.getIconWidth(), dunceImage.getIconHeight());
        getPanel().add(dunceLabel);
        getPanel().setComponentZOrder(dunceLabel, 0);
    }

    protected void move(Vertex nextVertex){
        if (hasLarvae()  && Math.random() < 0.1){
            synchronized(this) { // we do not want it depositing larvae while dying
                synchronized(currentVertex().getLarvaeLock()) {
                    depositLarvae();
                }
            }
        }
        super.move(nextVertex);
    }
}
