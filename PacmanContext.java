
public class PacmanContext { 
	private PacmanState state = null;
	private Pacman pacman;
	
	public PacmanContext(Pacman p) {
		pacman = p;
	}

	public void setState(PacmanState state) {
		this.state = state;		
	}
	
	public void action() {
		state.action(pacman);
	}
}
