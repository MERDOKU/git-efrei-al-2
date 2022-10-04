import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Pacman extends JComponent implements KeyListener {
	private static final long serialVersionUID = 1L;
	protected int x, y, dx, dy;
	protected Direction newDir, dirRequest;;
	private Grille grid;
	protected int vie, cpt;
	protected Color colorPacman;
	private ArrayList<ObjObserver> observers;
	 	
	public Pacman(int x0, int y0, Grille g0) {
		x = x0;
		y = y0;
		grid= g0;
		newDir = Direction.left;
		cpt = 0;
		vie = 3;
		colorPacman = Color.cyan;
		observers = new ArrayList<ObjObserver>();
		grid.setStatutPacman(EnumPacman.NORMAL);
		addKeyListener(this);
	}
	
	public void register(ObjObserver o) {
		observers.add(o);
	}

	public void unregister(ObjObserver o) {
		observers.remove(o);
	}

	void notifyObservers() {
		for (ObjObserver obj : observers) {
			obj.update();
		}
	}

	public void murQuiGene() {
		int uneCase = grid.getBLOCK_SIZE(); //nombre de pixels pour une case
		int positionPacman = 0;
		int codeMur = 0;			
		
		if(x%uneCase == 0 && y%uneCase == 0) { //Pour verifier que le pacman est entierement dans une case avant de changer la direction
			
			positionPacman = grid.getTabPos(x,y); //position du pacman dans le tableau de la grille
			grid.setPosPacman(positionPacman); // pour que le fantome y accède			
			codeMur = grid.contexte(positionPacman);		
			switch(codeMur) {
				case 1:
					if(dy < 0) newDir = Direction.still;
					else if(dirRequest == Direction.left || dirRequest == Direction.right || dirRequest == Direction.down)
						newDir = dirRequest;
					break;
				case 2:           //mur a gauche
					if(dx < 0) newDir = Direction.still;
					else if(dirRequest == Direction.right || dirRequest == Direction.down || dirRequest == Direction.up )
						newDir = dirRequest;
					break;
				case 3:
					if(dx < 0 || dy < 0)
						newDir = Direction.still;
					else if(dirRequest == Direction.right || dirRequest == Direction.down)
						newDir = dirRequest;
					break;
				case 4:
					if(dy > 0) newDir = Direction.still;
					else if(dirRequest == Direction.left || dirRequest == Direction.right || dirRequest == Direction.up)
						newDir = dirRequest;
					break;
				case 5:
					newDir = Direction.still;
					if(dirRequest == Direction.left || dirRequest == Direction.right)
						newDir = dirRequest;
					break;
					
				case 6:
					if(dx < 0 || dy > 0)
						newDir = Direction.still;
					else if(dirRequest == Direction.right || dirRequest == Direction.up)
						newDir = dirRequest;
					break;
				case 7:
					if(dx < 0 )
						if(positionPacman == 16) {
							grid.getSortiePacman(grid.getTabPos(x,y));
							x = 360;
							y = 312;
						}
						else newDir = Direction.still;
					if(dirRequest == Direction.right)
						newDir = dirRequest;
					break;
				case 8:           
					if(dx > 0) newDir = Direction.still;
					else if(dirRequest == Direction.left || dirRequest == Direction.down || dirRequest == Direction.up )
						newDir = dirRequest;
					break;
				case 9:
					if(dx > 0 || dy < 0)
						newDir = Direction.still;
					if(dirRequest == Direction.left || dirRequest == Direction.down)
						newDir = dirRequest;
					break;
				case 10:
					newDir = Direction.still;
					if(dirRequest == Direction.up || dirRequest == Direction.down)
						newDir = dirRequest;
					break;
				case 11:
					newDir = Direction.still;
					if(dirRequest == Direction.down)
						newDir = dirRequest;
					break;
					
				case 12:
					if(dx > 0 || dy > 0)
						newDir = Direction.still;
					else if(dirRequest == Direction.left || dirRequest == Direction.up)
						newDir = dirRequest;
					break;
				case 13:
					if(dx > 0 )
						if(positionPacman == 223) {
							grid.getSortiePacman(grid.getTabPos(x,y));
							x = 0;
							y = 24;
						}
						else newDir = Direction.still;
					if(dirRequest == Direction.left)
						newDir = dirRequest;
					break;
				case 14:
					newDir = Direction.still;
					if(dirRequest == Direction.up)
						newDir = dirRequest;
					break;
				default:
					newDir = dirRequest;					
			}
		}	
	}
		
	
	public boolean isFocusable() { 
	    return true;
	}
	
	public void deplacePacman() {
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
		if(newDir == Direction.still) {
			dx = 0;
			dy = 0;
		}				
		this.x += dx;
		this.y += dy;
	}
	
	public Color getColorPacman() {
		return this.colorPacman;
	}
	
	public void setColorPacman(Color pacmanColor) {
		this.colorPacman = pacmanColor;
	}
	
	public void process() {
		boolean estMort;
		
		estMort = grid.isMortPacman();
		if(estMort) {  
			if(vie >= 1 && cpt == 0) {
				vie -= 1;
				x = 6 * grid.getBLOCK_SIZE(); 
				y = 6 * grid.getBLOCK_SIZE();	
				newDir = Direction.still;	
				grid.setPosPacman(grid.getTabPos(x,y));
			}	
			cpt++;
			if(cpt > 24) {
				grid.setMortPacman(false);
				estMort = false;
				cpt = 0;
			}
		} else {
			murQuiGene();
			deplacePacman();
			repaint(); 
		}
		notifyObservers();
	}
	
	public void paint(Graphics g) {

    	int BLOCK_SIZE = grid.getBLOCK_SIZE(); 
    	
    	if(vie > 0) {
    		g.setColor(colorPacman);
    		if(cpt % 3 == 0)
    			g.fillOval(x+4 , y+2 , 22, 22);
    		else 
    			g.fillOval(x+8 , y+2 , 22, 22);
    		g.setColor(Color.white);
    		if (dx == -1) {							//vers gauche
    			g.fillRect(x+4, y+10, 12, 8);         	
    		} else if (dx == 1) {					//vers droite
    			g.fillRect(x+BLOCK_SIZE-10, y+10, 12, 8);       	
    		} else if (dy == -1) {        			//vers haut
    			g.fillRect(x+12, y+4, 8, 12); 
    		} else if(dy == 1){						//vers bas
    			g.fillRect(x+12, y+12,8, 12);
    		} else if(dx == 0 && dy == 0) {
    			g.fillRect(x+10,y+12, 12, 4);
    		}

    	}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();
		switch(keyCode) {
		case 37:				//keyCode correspondant a la Fleche gauche du clavier
			dirRequest = Direction.left;
			break;
		case 38:
			dirRequest = Direction.up;
			break;
		case 39:
			dirRequest = Direction.right;
			break;
		case 40:
			dirRequest = Direction.down;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

}