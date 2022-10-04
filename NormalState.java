import java.awt.Color;

public class NormalState extends PacmanState {
	public void action(Pacman p) {
		p.setColorPacman(Color.cyan);
		p.process();		
	}
}
