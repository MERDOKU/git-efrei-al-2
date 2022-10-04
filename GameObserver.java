import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameObserver implements ObjObserver, ActionListener{
	    private Pacman pacman;
	    private int positionPacman = 0;
	    private Grille grid;
	    private JLabel disp;
	    boolean extraLife;
	    boolean mortPacman = false;
	    private int score;
	    private PacmanContext pacmanContext;
	    private ArrayList <FantomeContext> ghostContextList;
	    private Timer timer = new Timer(50,this);

		public GameObserver(Pacman p,PacmanContext pContext, Grille g, JLabel aLabel, ArrayList <FantomeContext> liste) {
			grid = g;
			pacman = p;
			extraLife = false;
			score = 0;
			disp = aLabel;
			pacmanContext = pContext;
			ghostContextList = liste;
			timer.addActionListener(this);
			timer.start();			
		}

		public void actionPerformed(ActionEvent arg0) {
			pacmanContext.action();	
			for (FantomeContext f:ghostContextList)
				f.action();
		}	
		
		public int getPacgommeScore(int gommeValue) {
			
			switch(gommeValue) {
				case 16:
					return 100;
				case 17:		
					grid.setStatutPacman(EnumPacman.INVISIBLE);
					pacmanContext.setState(new InvisibleState());
					for (FantomeContext f : ghostContextList) {
						f.setState(new FantomeNormalState());
						}					
					return 300;
				case 18:
					grid.setStatutPacman(EnumPacman.SUPERPACMAN);
					pacmanContext.setState(new SuperPacmanState());
					for (FantomeContext f : ghostContextList) {
						f.setState(new FantomeWeakState());
						}
					return 500;
				case 19:
					grid.changeStructGrille();				
					return 1000;
				default:
					return 0;
			}
		}
		
		public void update() {
			int uneCase = grid.getBLOCK_SIZE(); 
			
			if (pacman.x % uneCase == 0 && pacman.y % uneCase == 0) { 
				disp.setText("Score: " + score + "     Vie(s): " + pacman.vie);
				disp.repaint();
				positionPacman = grid.getTabPos(pacman.x,pacman.y);
				if(grid.gettabValue(positionPacman) > 15 && grid.gettabValue(positionPacman) < 20 ) { //si le pacman est sur une case contenant un pacgomme il mange le pacgomme
					score += getPacgommeScore(grid.gettabValue(positionPacman));
					if(score >= 5000 && !extraLife) {
						pacman.vie += 1;
						extraLife = true;
					}
					disp.setText("Score: " + score + "     Vie(s): " +pacman.vie);
					disp.repaint();
					grid.settab(positionPacman, 55); 
				}
				if(grid.zeroPacgomme()) {
					disp.setText("YOU WON !!!" + "           Score: " + score);
					disp.repaint();
				}
				if(pacman.vie == 0) {
					disp.setText("GAME OVER !!! " +	" 	Score: " + score);
				}
			}
		}		
		
	}

