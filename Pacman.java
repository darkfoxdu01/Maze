import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;

public class Pacman extends JPanel {
	init parent;
	int nombregum = 0;// Sera aggrandit
	int pacmanX = 1;
	int pacmanY = 1;
    public static final int NORD = 0b1;
    public static final int EST = 0b10;
    public static final int SUD = 0b100;
    public static final int OUEST = 0b1000;
    int direction;

	int[] [] tableau = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,1,0,1,1,1,1,1,1,0,1,0,0,0,0,0,1},
			{1,1,1,1,1,0,1,0,0,0,1,1,0,0,0,1,0,1,1,1,1,1},
			{1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1,1},
			{1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,1,0,1,1,1,1,1},
			{1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
			{1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
			{1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
			{1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
			{1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,1,0,1},
			{1,0,0,0,0,0,1,1,1,0,1,1,0,1,1,1,0,0,0,0,0,1},
			{1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1},
			{1,0,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,0,1},
			{1,1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1,1},
			{1,0,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	int[][] gum = new int[31][22];

    public Pacman() {
		for(int i = 0; i< 31; i++) {
			for(int j = 0; j < 22 ; j++) {
				if(tableau[i][j] == 1) {
					gum[i][j] = 0;
				}
				else {
					gum[i][j] = 1;
				}
				if(j == 0 || j == 21) {
					gum[i][j] = 0;
				}
				if(j == 1 && i == 1) {
					gum[i][j] = 0;
				}
				if(gum[i][j] == 1) {
					nombregum++;
				}
			}
		}
		System.out.println("Je suis dans le constructeur");
		setFocusable(true);
    }

	public void move(int dir) {
		System.out.println("Coucou");
		if(dir == NORD && tableau[pacmanY-1][pacmanX] == 0) {
			pacmanY -= 1;
		}
		else if(dir == SUD && tableau[pacmanY+1][pacmanX] == 0) {
			pacmanY += 1;
		}
		else if(dir == EST && tableau[pacmanY][pacmanX+1] == 0) {
			pacmanX += 1;
		}
		else if(dir == OUEST && tableau[pacmanY][pacmanX-1] == 0) {
			pacmanX -= 1;
		}

		if(pacmanX == 0 && dir == OUEST) {
			pacmanX = 21;
		}
		else if(pacmanX == 21 && dir == EST) {
			pacmanX = 0;
		}

		if(gum[pacmanY][pacmanX] == 1) {
			gum[pacmanY][pacmanX] = 0;
			nombregum--;
			if(nombregum == 0) {
				parent.fini(10);
			}
		}
		this.repaint();
	}

	public void setParent(init x) {
		parent = x;
	}

    public void paintComponent(Graphics g) {
        // sert de background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

      for(int i=0;i<31;i++){
    	  for(int j=0;j<22;j++){
    		  if(tableau[i][j] == 1) {
        		  g.setColor(Color.BLUE);
        		  g.fillRect(getWidth()/22*j, getHeight()/31*i , getWidth()/22, getHeight()/31);
    		  }
			  if(gum[i][j] == 1) {
				  g.setColor(Color.BLACK);
				  g.fillOval(getWidth()/22*j+getWidth()/22/4, getHeight()/31*i+getHeight()/31/4 , getWidth()/44, getHeight()/62);
			  }
    		  if(j == pacmanX && i == pacmanY) {
    			  g.setColor(Color.YELLOW);
        		  g.fillOval(getWidth()/22*j, getHeight()/31*i , getWidth()/22, getHeight()/31);
    		  }
	     }
   	}
    }
}
