import java.awt.Color;

public class FantomeWeakState extends FantomeState {
	public void action(Fantome f) {
		f.setColorFantome(Color.blue);
		f.lowSpeed();
		f.action();		
	}
}
