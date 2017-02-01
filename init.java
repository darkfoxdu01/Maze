import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class init extends JFrame implements ActionListener, KeyListener {

    JButton boutonOk;
    JButton boutonAnnuler;
    JButton boutonPacman;
    List listeTaille;
    Maze maze;
    Pacman pac;
    JPanel launcher;
    JPanel mainContainer;
    JPanel fin;
    CardLayout cl;

    public static void main(String[] str) {
        new init();
    }

    public init() {
        // Initialisation de la fenêtre
        setSize(500,500);
        setTitle("Maze & Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialisation des composants
        boutonOk = new JButton("OK");
        boutonOk.addActionListener(this);
        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.addActionListener(this);
        boutonPacman = new JButton("Pacman");
        boutonPacman.addActionListener(this);
        listeTaille = new List(10, false);
        for(int i = 10; i <= 100; i+=10) {
            listeTaille.add(i + "");
        }

        // Initialisation des panneaux et des organisation (layouts)
        launcher = new JPanel();
        launcher.add(boutonPacman);
        launcher.add(listeTaille);
        launcher.add(boutonAnnuler);
        launcher.add(boutonOk);

        mainContainer = new JPanel();
        cl = new CardLayout();
        mainContainer.setLayout(cl);
        setContentPane(mainContainer);
        mainContainer.add("Launcher", launcher);
        cl.first(mainContainer);
        JButton ok = new JButton("Continuer");
        setVisible(true);
    }

    public void fini(int coup) {
        Victoire vic = new Victoire(coup);
        JButton ok = new JButton("Continuer");
        ok.addActionListener(this);
        vic.setBoutonOK(ok);
        mainContainer.add("Victoire", vic);
        cl.next(mainContainer);
    }

    public class Victoire extends JPanel {
        private JButton okay;
        private JButton quitter;
        private JLabel mess;

        public Victoire(int c) {
            mess = new JLabel();
            mess.setText("Bravo ! Vous avez réussi en " + c + " coups !");
            quitter = new JButton("Quitter");
            quitter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }

        public void setBoutonOK(JButton ok) {
            JButton okay = ok;
            setLayout(new BorderLayout());
            add(okay, BorderLayout.EAST);
            add(quitter, BorderLayout.WEST);
            add(mess, BorderLayout.CENTER);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Pacman")) {
            // Vous codez dans cette condition :
            pac = new Pacman();
            pac.addKeyListener(this);
            pac.setParent(this);
            mainContainer.add("Pacman", pac);
            cl.next(mainContainer);
        }

        if(e.getActionCommand().equals("OK")) {
            try {
                maze = new Maze(this, Integer.parseInt(listeTaille.getSelectedItem()));
            } catch(Exception except) {
                maze = new Maze(this);
            }
            maze.addKeyListener(this);
            mainContainer.add("Maze", maze);
            cl.next(mainContainer);
        }
        if(e.getActionCommand().equals("Annuler")) {
            System.exit(0);
        }

        if(e.getActionCommand().equals("Continuer")) {
            mainContainer.removeAll();
            cl = new CardLayout();
            mainContainer.setLayout(cl);
            setContentPane(mainContainer);
            cl.first(mainContainer);
            mainContainer.add("Launcher", launcher);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(pac != null) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                pac.move(Pacman.NORD);
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                pac.move(Pacman.SUD);
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                pac.move(Pacman.OUEST);
            }
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                pac.move(Pacman.EST);
            }
        }

        else if(maze != null) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                maze.move(Maze.NORD);
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                maze.move(Maze.SUD);
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                maze.move(Maze.OUEST);
            }
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                maze.move(Maze.EST);
            }
            else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                maze.generationPathRobot();
            }
        }

        else if(e.getKeyChar() == 'q') {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
