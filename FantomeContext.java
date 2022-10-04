

public class FantomeContext { 
	private FantomeState state = null;
	private Fantome fantome;
	
	public FantomeContext(Fantome f) {
		fantome = f;
	}
	
	public void setState(FantomeState state) {
		this.state = state;		
	}

	public void action() {
		state.action(fantome);
	}

}
