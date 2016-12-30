import java.util.Random;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Maze extends JPanel {

    // Les directions
    public static final int NORD = 0b1;
    public static final int EST = 0b10;
    public static final int SUD = 0b100;
    public static final int OUEST = 0b1000;

    // Les variables tableau
    private int taille;
    private int[][] tableau;
    private int caseDepart;
    private int caseActu;
    private int caseFin;
    private Random rand;
    private int[] path = new int[2];
    private int coup;
    private init parent;
    private Boolean robot = false;

    public Maze(init p) {
        taille = 50;
        parent = p;
        path[0] = -1;
        caseDepart = -1;
        caseActu = -1;
        caseFin = -1;
        coup = 0;
        rand = new Random();
        initTableau();
        generation();
        setFocusable(true);
    }

    public Maze(init p, int x) {
        taille = x;
        parent = p;
        path[0]=-1;
        caseDepart = -1;
        caseActu = -1;
        caseFin = -1;
        coup = 0;
        rand = new Random();
        initTableau();
        generation();
        setFocusable(true);
    }

    public void move(int direction) {
        if((tableau[caseActu%taille][caseActu/taille] & direction) == 0) {
            if(direction == NORD) {
                caseActu -= taille;
            }
            else if(direction == SUD) {
                caseActu += taille;
            }
            else if(direction == EST) {
                caseActu += 1;
            }
            else if(direction == OUEST) {
                caseActu -= 1;
            }
            coup++;
            repaint();
            if(caseActu == caseFin) {
                parent.fini(coup);
            }
        }
    }

    public void initTableau() {
        tableau = new int[taille][taille];
        for(int i = 0; i < taille*taille; i++) {
            tableau[i%taille][i/taille] = NORD | EST | SUD | OUEST;
        }
    }

    public void generation() {
        // initialisation des variables locales
        int[][] tmp = new int[taille][taille];
        for(int i = 0 ; i < taille * taille ; i++) {
            tmp[i%taille][i/taille] = i;
        }
        Boolean different = true; // Va servir de boucleur
        int value;
        int colonise;
        int possibilite;
        while(different) {
            caseActu = rand.nextInt(taille*taille);
            possibilite = 0;
            value = tmp[caseActu%taille][caseActu/taille];
            if(caseActu%taille != 0 && value != tmp[caseActu%taille-1][caseActu/taille]) {
                possibilite++;
            }
            if(caseActu%taille != taille-1 && value != tmp[caseActu%taille+1][caseActu/taille]) {
                possibilite++;
            }
            if(caseActu/taille != 0 && value != tmp[caseActu%taille][caseActu/taille-1]) {
                possibilite++;
            }
            if(caseActu / taille != taille-1 && value != tmp[caseActu%taille][caseActu/taille+1]) {
                possibilite++;
            }
            if(possibilite != 0) {
                possibilite = rand.nextInt(possibilite) + 1;

                if(caseActu%taille != 0 && value != tmp[caseActu%taille-1][caseActu/taille]) {
                    possibilite--;
                    if(possibilite == 0) {
                        colonise = tmp[caseActu%taille-1][caseActu/taille];
                        tableau[caseActu%taille][caseActu/taille] ^= OUEST;
                        tableau[caseActu%taille-1][caseActu/taille] ^= EST;
                        for(int i = 0; i < taille*taille ; i++) {
                            if(tmp[i%taille][i/taille] == colonise) {
                                tmp[i%taille][i/taille] = value;
                            }
                        }
                    }
                }
                if(caseActu%taille != taille-1 && value != tmp[caseActu%taille+1][caseActu/taille]) {
                    possibilite--;
                    if(possibilite == 0) {
                        colonise = tmp[caseActu%taille+1][caseActu/taille];
                        tableau[caseActu%taille][caseActu/taille] ^= EST;
                        tableau[caseActu%taille+1][caseActu/taille] ^= OUEST;
                        for(int i = 0; i < taille*taille ; i++) {
                            if(tmp[i%taille][i/taille] == colonise) {
                                tmp[i%taille][i/taille] = value;
                            }
                        }
                    }
                }
                if(caseActu/taille != 0 && value != tmp[caseActu%taille][caseActu/taille-1]) {
                    possibilite--;
                    if(possibilite == 0) {
                        colonise = tmp[caseActu%taille][caseActu/taille-1];
                        tableau[caseActu%taille][caseActu/taille] ^= NORD;
                        tableau[caseActu%taille][caseActu/taille-1] ^= SUD;
                        for(int i = 0; i < taille*taille ; i++) {
                            if(tmp[i%taille][i/taille] == colonise) {
                                tmp[i%taille][i/taille] = value;
                            }
                        }
                    }
                }
                if(caseActu/taille != taille-1 && value != tmp[caseActu%taille][caseActu/taille+1]) {
                    possibilite--;
                    if(possibilite == 0) {
                        colonise = tmp[caseActu%taille][caseActu/taille+1];
                        tableau[caseActu%taille][caseActu/taille] ^= SUD;
                        tableau[caseActu%taille][caseActu/taille+1] ^= NORD;
                        for(int i = 0; i < taille*taille ; i++) {
                            if(tmp[i%taille][i/taille] == colonise) {
                                tmp[i%taille][i/taille] = value;
                            }
                        }
                    }
                }
            }
            // On voit s'il faut encore boucler
            different = false;
            for(int i = 0; i < taille * taille-1; i++) {
                if(tmp[i%taille][i/taille] != tmp[(i+1)%taille][(i+1)/taille]) {
                    different = true;
                }
            }
        }
        for(int i = taille*(taille-1) ; i < taille*taille && caseDepart == -1; i++) {
            if((tableau[i%taille][i/taille] ^ NORD) == (NORD | SUD | OUEST | EST)) {
                caseDepart = i;
            }
            else if ((tableau[i%taille][i/taille] ^ SUD) == (NORD | SUD | OUEST | EST)) {
                caseDepart = i;
            }
            else if ((tableau[i%taille][i/taille] ^ OUEST) == (NORD | SUD | OUEST | EST)) {
                caseDepart = i;
            }
            else if ((tableau[i%taille][i/taille] ^ EST) == (NORD | SUD | OUEST | EST)) {
                caseDepart = i;
            }
        }
        if(caseDepart == -1) {
            generation();
        }
        else {
            caseActu = caseDepart;
        }
        for(int i = taille-1 ; i >= 0 && caseFin == -1; i--) {
            if((tableau[i%taille][i/taille] ^ NORD) == (NORD | SUD | OUEST | EST)) {
                caseFin = i;
            }
            else if ((tableau[i%taille][i/taille] ^ SUD) == (NORD | SUD | OUEST | EST)) {
                caseFin = i;
            }
            else if ((tableau[i%taille][i/taille] ^ OUEST) == (NORD | SUD | OUEST | EST)) {
                caseFin = i;
            }
            else if ((tableau[i%taille][i/taille] ^ EST) == (NORD | SUD | OUEST | EST)) {
                caseFin = i;
            }
        }
        if(caseFin == -1) {
            caseDepart = -1;
            caseActu = -1;
            generation();
        }
    }

    public void generationPathRobot() {
        // Init
        path = new int[taille*taille*4];
        for(int i = 0 ; i < taille*taille*4; i++) {
            path[i] = -1;
        }
        if(robot == false) {
            int tmp = caseActu;
            for(int i = 0 ; i < taille*taille*4; i++) {
                path[i] = -1;
            }
            // Première case
            path[0] = caseActu;
            int direction = EST;
            while(caseActu != caseFin) {
                if(tableau[caseActu%taille][caseActu/taille] / (direction*2%15) % 2 == 0) {
                    // On peut tourner à DROITE
                    direction = direction*2%15;
                }
                else if(tableau[caseActu%taille][caseActu/taille] / direction % 2 == 0) {

                }
                else if(tableau[caseActu%taille][caseActu/taille] / (direction*8%15) % 2 == 0) {
                    direction = direction * 8 % 15;
                }
                else if(tableau[caseActu%taille][caseActu/taille] / (direction*4%15) % 2 == 0) {
                    direction = direction * 4 % 15;
                }
                else {
                    System.out.println("Probleme de direction");
                }
                goTo(direction);
            }
            caseActu = tmp;
            robot = true;
        }
        else {
            robot = false;
        }
        repaint();
    }

    public void addPath(int x) {
        int i = 0;
        Boolean sup = false;
        while(path[i] != -1) {
            if(sup) {
                path[i] = -1;
            }
            if(path[i] == x) {
                sup = true;
            }
            i++;
        }
        if(!sup) {
            path[i] = x;
        }
    }

    public int getPath() {
        int i = 0;
        while(path[i] != -1) {
            i++;
        }
        return path[i-1];
    }

    public void goTo(int dir) {
        if(dir == NORD) {
            caseActu -= taille;
        }
        else if(dir == SUD) {
            caseActu += taille;
        }
        else if(dir == OUEST) {
            caseActu -= 1;
        }
        else if(dir == EST) {
            caseActu += 1;
        }
        else {
            System.out.println("DIRECTION INCONNUE !");
        }
        addPath(caseActu);
    }



    @Override
    public void paintComponent(Graphics g) {
        requestFocus();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.ORANGE);
        for(int i = 0; path[i] != -1; i++) {
            g.fillRect(getWidth()/(taille+2)*(path[i]%taille+1), getHeight()/(taille+2)*(path[i]/taille+1), getWidth()/(taille+2), getHeight()/(taille+2));
        }
        g.setColor(Color.BLACK);
        for(int i = 0; i < taille * taille ; i++) {
            if(i == caseDepart) {
                g.setColor(Color.RED);
                g.fillRect(getWidth()/(taille+2) * (i%taille+1), getHeight()/(taille+2)*(i/taille+1), getWidth()/(taille+2), getHeight()/(taille+2));
                g.setColor(Color.BLACK);
            }
            if(i == caseFin) {
                g.setColor(Color.GREEN);
                g.fillRect(getWidth()/(taille+2) * (i%taille+1), getHeight()/(taille+2)*(i/taille+1), getWidth()/(taille+2), getHeight()/(taille+2));
                g.setColor(Color.BLACK);
            }
            if(i == caseActu) {
                g.setColor(Color.BLUE);
                g.fillOval(getWidth()/(taille+2)*(i%taille+1), getHeight()/(taille+2)*(i/taille+1), getWidth()/(taille+2), getHeight()/(taille+2));
                g.setColor(Color.BLACK);
            }
            if((tableau[i%taille][i/taille] & NORD) == NORD) {
                g.fillRect(getWidth()/(taille+2)*(i%taille+1), getHeight()/(taille+2)*(i/taille+1), getWidth()/(taille+2), 2);
            }
            if((tableau[i%taille][i/taille] & SUD) == SUD) {
                g.fillRect(getWidth()/(taille+2)*(i%taille+1), getHeight()/(taille+2)*(i/taille+2), getWidth()/(taille+2), 2);
            }
            if((tableau[i%taille][i/taille] & EST) == EST) {
                g.fillRect(getWidth()/(taille+2)*(i%taille+2), getHeight()/(taille+2)*(i/taille+1), 2, getHeight()/(taille+2));
            }
            if((tableau[i%taille][i/taille] & OUEST) == OUEST) {
                g.fillRect(getWidth()/(taille+2)*(i%taille+1), getHeight()/(taille+2)*(i/taille+1), 2, getHeight()/(taille+2));
            }
        }
    }
}
