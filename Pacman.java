import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Pacman extends JPanel {
	int nombregum=248;
	int pacmanX = 1;
	int pacmanY = 1;
	int ghostX = 18;
	int ghostY = 29;
    public static final int NORD = 0b1;
    public static final int EST = 0b10;
    public static final int SUD = 0b100;
    public static final int OUEST = 0b1000;
    private int[] path = new int[2];
    int directionPacman;
    int GhostDirection;
    
	int[] [] tableau = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
			{1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
			{1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1},
			{1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1},
			{1,0,0,0,0,1,0,1,1,1,1,1,1,0,1,0,0,0,0,1},
			{1,1,1,1,0,1,0,0,0,1,1,0,0,0,1,0,1,1,1,1},
			{1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1},
			{1,1,1,1,0,1,0,0,0,0,0,0,0,0,1,0,1,1,1,1},
			{1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1},
			{1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1},
			{0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0},
			{1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1},
			{1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1},
			{1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,1,1},
			{1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
			{1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1},
			{1,0,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,1,0,1},
			{1,0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,1},
			{1,0,0,0,0,1,1,1,0,1,1,0,1,1,1,0,0,0,0,1},
			{1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
			{1,0,0,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,0,1},
			{1,1,0,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,1,1},
			{1,0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    public Pacman() {
    	}
    public void ghostpath() {
            path[0] = ghostX & ghostY;
            int GhostDirection = EST;
            while(ghostX != pacmanX && ghostY != pacmanY) {
                if(tableau[ghostX][ghostY] / (GhostDirection*2%15) % 2 == 0) {
                	GhostDirection = GhostDirection*2%15;
                }
                else if(tableau[ghostX][ghostY] / GhostDirection % 2 == 0) {
                }
                else if(tableau[ghostX][ghostY] / (GhostDirection*8%15) % 2 == 0) {
                    GhostDirection = GhostDirection * 8 % 15;
                }
                else if(tableau[ghostX][ghostY] / (GhostDirection*4%15) % 2 == 0) {
                    GhostDirection = GhostDirection * 4 % 15;
                }
                else {
                    System.out.println("Probleme de direction");
                }
            }
            repaint();
            }

    public void ghostmove(int GhostDirection) {
        if((tableau[ghostX][ghostY] & GhostDirection) == 0) {
            if(GhostDirection == NORD) {
                ghostY = ghostY-1;
            }
            else if(GhostDirection == SUD) {
            	ghostY = ghostY+1;
            }
            else if(GhostDirection == EST) {
            	ghostX = ghostX+1;
            }
            else if(GhostDirection == OUEST) {
            	ghostX = ghostX-1;
            }
            repaint();
        }
        }
    public void paintComponent(Graphics g) {
        // sert de background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

      for(int i=0;i<31;i++){
    	  for(int j=0;j<20;j++){
    		  if(tableau[i][j] == 1) {
        		  g.setColor(Color.BLACK);
        		  g.fillRect(getWidth()/20*j, getHeight()/31*i , getWidth()/20, getHeight()/31);	  
    		  }
    		  if(j == pacmanX && i == pacmanY) {
    			  g.setColor(Color.YELLOW);
        		  g.fillOval(getWidth()/20*j, getHeight()/31*i , getWidth()/20, getHeight()/31);
    		  }
    		  if(j == ghostX && i == ghostY){
    			  g.setColor(Color.RED);
    			  g.fillOval(getWidth()/20*j, getHeight()/31*i, getWidth()/20, getHeight()/31);
    		  }
    	  }
      }
    }
    
}
