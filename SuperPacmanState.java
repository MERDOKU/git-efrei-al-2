import java.awt.Color;

public class SuperPacmanState extends PacmanState{
	public void action(Pacman p) {
		p.setColorPacman(Color.orange);
		p.murQuiGene();
		p.deplacePacman();
		p.repaint();
		p.notifyObservers();
	}
}
