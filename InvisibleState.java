import java.awt.Color;

public class InvisibleState extends PacmanState {	
	public void action(Pacman p) {
		p.setColorPacman(Color.yellow);
		p.murQuiGene();
		p.deplacePacman();
		p.repaint();
		p.notifyObservers();
	}	
}
