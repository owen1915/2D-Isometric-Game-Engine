import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private int width = 1280;
    private int height = 720;

    public GamePanel() {
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }
}
