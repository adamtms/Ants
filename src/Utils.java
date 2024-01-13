import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Graphics2D;

/**
 * The Utils abstact class provides utility methods for image manipulation and generating random names.
 */
public abstract class Utils {
    protected static Random random = new Random();

    protected static ImageIcon getScaledImage(URL imagePath, int size) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }

    protected static ImageIcon flipImageIcon(ImageIcon imageIcon) {
        Image image = imageIcon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.translate(width, 0);
        transform.scale(-1, 1);

        g2d.drawImage(image, transform, null);
        g2d.dispose();

        return new ImageIcon(bufferedImage);
    }

    private static String[] titles = {
            "Sir",
            "Lady",
            "Knight",
            "Dame",
            "Baron",
            "Baroness",
            "Count",
            "Countess",
            "Duke",
            "Duchess",
            "Prince",
            "Princess"
    };

    private static String[] names = {
            "Arthur",
            "Lancelot",
            "Galahad",
            "Gawain",
            "Geraint",
            "Percival",
            "Tristan",
            "Bedivere",
            "Kay",
            "Gareth",
            "Bors",
            "Lamorak",
            "Lionel",
            "Lucan",
            "Palamedes",
            "Safir",
            "Ector",
            "Dagonet",
            "Degore",
            "Brunor",
            "Leodegrance",
            "Yvain",
            "Gaheris",
            "Agravain",
    };

    private static String[] suffix = {
            "the Brave",
            "the Bold",
            "the Valiant",
            "the Wise",
            "the Foolish",
            "the Strong",
            "the Weak",
            "the Kind",
            "the Just",
            "the Unjust"
    };

    protected static String randomName() {
        String title = titles[random.nextInt(titles.length)];
        String name = names[random.nextInt(names.length)];
        String nameSuffix = suffix[random.nextInt(suffix.length)];
        return title + " " + name + " " + nameSuffix;
    }
}
