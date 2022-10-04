import java.awt.Color;

public class FantomeNormalState extends FantomeState {	
		public void action(Fantome f) {
			f.setColorFantome(Color.lightGray);
			f.normalSpeed();
			f.action();		
		}
}
