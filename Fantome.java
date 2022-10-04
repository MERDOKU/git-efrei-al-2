import javax.swing.JComponent;
import java.lang.Math;
import java.awt.*;

public class Fantome extends JComponent { 
	private static final long serialVersionUID = 1L;
	private int x, y, dx, dy, speed;
	private Grille grid;
	private Direction newDir;
	private Color colorFantome;
	private int positionFantome;
	private boolean mortFantome; 
	private int cpt;
	
	public Fantome(int x, int y, Grille gr) {
		this.x = x;
		this.y = y;
		this.cpt = 0;
		this.grid = gr;
		newDir = Direction.left;
		mortFantome = false;
	}
	
	public void normalSpeed() {
		speed = 2;
	}
	
	public void lowSpeed() {
		speed = 1;
	}

	public void murQuiGene() {
		int uneCase = grid.getBLOCK_SIZE(); //nombre de pixels pour une case
		int codeMur = 0;
		double randx, randy;
		Direction choiceX, choiceY;
		
		if(x%uneCase == 0 && y%uneCase == 0) { //Pour verifier que le fantome est entierement dans une case avant de cchanger la direction
			//position du fantome dans le tableau de la grille
			positionFantome = grid.getTabPos(x,y);
			codeMur = grid.contexte(positionFantome);
			randx = Math.random();
			if (randx > 0.5)        //tirage pour une direction horizontale
				choiceX = Direction.left;
			else choiceX = Direction.right;
			randy =  Math.random();
			if (randy > 0.5)       //tirage pour une direction verticale
				choiceY = Direction.up;
			else choiceY = Direction.down;			
			
			switch(codeMur) {
			
				case 1:						//mur en haut
					if (dy < 0) {
						if(randx < randy)
							newDir = Direction.down;
						else newDir = choiceX;
					} 
					break;					
				case 2:           
					if (dx < 0) {
						if (randx > randy)
							newDir = Direction.right;
						else newDir = choiceY;
					} 
					break;
				case 3:
					if(dx < 0 || dy < 0) {
						if(randx < randy)
							newDir = Direction.right;
						else newDir = Direction.down;
					}
					break;
				case 4:
					if (dy > 0) {
						if(randx < randy)
							newDir = Direction.up;
						else newDir = choiceX;
					} 
					break;
				case 6:
					if(dx < 0 || dy > 0) {
						if(randx < randy)
							newDir = Direction.right;		
						else newDir = Direction.up;
					}
					break;
				case 7:
					newDir = Direction.right;
					break;
				case 8:
					if (dx > 0) {
						if (randx > randy)
							newDir = Direction.left;
						else newDir = choiceY;
					} 
					break;					
				case 9:
					if(dx > 0 || dy < 0) {
						if(randx > randy)
							newDir = Direction.left;
						else newDir = Direction.down;
					}
					break;
				case 11:
					newDir = Direction.down;
					break;
				case 12:
					if(dx > 0 || dy > 0) {
						if(randx < randy)
							newDir = Direction.left;
						else newDir = Direction.up;
					}
					break;
				case 13:
					newDir = Direction.left;
					break;
				case 14:
					newDir = Direction.up;						
			}
		}
	}

	public void action() {
		if(grid.getPosPacman() == positionFantome) {
			if(grid.getStatutPacman() == EnumPacman.NORMAL)
				grid.setMortPacman(true);
			else if(grid.getStatutPacman() == EnumPacman.SUPERPACMAN)
				mortFantome = true;
			else grid.setMortPacman(false);			//Cas du pacman invisible pour les fanomes
		}
		if(mortFantome) {
			x = 8 * grid.getBLOCK_SIZE();
			y = 8 * grid.getBLOCK_SIZE();	
			cpt++;
			if(cpt > 24) {
				mortFantome = false;
				positionFantome = grid.getTabPos(x, y);
				cpt = 0;
			}			
		}	
		else {
			murQuiGene();
			deplaceFantome();
			repaint();
		}
	}
	
	public void deplaceFantome() {
		if(newDir == Direction.left) {
			dx = -1;
			dy = 0;
		}
		if(newDir == Direction.right) {
			dx = 1;
			dy = 0;
		}
		if(newDir == Direction.up) {
			dx = 0;
			dy = -1; 
		}
		if(newDir == Direction.down) {
			dx = 0;
			dy = 1;
		}						
		this.x += dx * speed;
		this.y += dy * speed;
	}
	
	public void setColorFantome(Color color) {
		this.colorFantome = color;		
	}
	
	public void paint(Graphics g) {					
			g.setColor(colorFantome);
			if((cpt % 3) == 0) 
				g.fillRect(x+4,y+2,16,20);
			else
				g.fillRect(x+8,y+2,16,20);
			if (grid.getStatutPacman() == EnumPacman.SUPERPACMAN)
				g.setColor(Color.white);
			else g.setColor(Color.black);
			g.fillOval(x + 12, y + 4, 5, 5);
			g.fillOval(x + 5, y + 4, 5, 5);		
	}
}