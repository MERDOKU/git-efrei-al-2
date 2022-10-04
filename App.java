
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class App {	 
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Pacman 2 Le Retour");
		frame.setBounds(50,50,400,450);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		Grille uneGrille = new Grille(0,0);
		ArrayList<FantomeContext> contextList = new ArrayList<FantomeContext>();
		Pacman pman = new Pacman(6*24,8*24, uneGrille);						
		frame.add(pman);
		frame.setVisible(true);	
		for(int i = 0; i < 4; i++) {
			Fantome f = new Fantome(i*24,(i+5)*24, uneGrille);
			frame.add(f);
			frame.setVisible(true);
			FantomeContext fContext = new FantomeContext(f);
			fContext.setState(new FantomeNormalState());
			contextList.add(fContext);			
		} 	
		JLabel afficheScore = new JLabel("Score : ");		
		frame.add(afficheScore);
		afficheScore.setBounds(10, 350, 300, 100);			
		frame.add(uneGrille);
		frame.setVisible(true);				
			
		PacmanContext pContext = new PacmanContext(pman);
		pContext.setState(new NormalState()); 
		GameObserver game = new GameObserver(pman,pContext, uneGrille,afficheScore,contextList);
		pman.register(game);			
	} 	
}