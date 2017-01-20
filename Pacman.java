import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Pacman extends JPanel {
    public Pacman() {

    }

    public void paintComponent(Graphics g) {
        // sert de background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Sert de test pour voir si ça fonctionne
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth()/2, getHeight()/2);
    }
}
