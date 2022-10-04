import javax.swing.JComponent;
import java.awt.*;

public class Grille extends JComponent{

	private static final long serialVersionUID = 1L;
    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 16;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE ;
    private int posPacman = 1;
    boolean mortPacman = false;
    private EnumPacman statutPacman; 
	private int[] tab ={
			0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
		   16, 16, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16, 16, 16, 16,  0,
			0,  0, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16, 19, 16, 16,  0,
			0, 16, 16, 16, 16, 16, 16, 16,  0, 16, 16,  0, 16, 16, 16,  0,
            0,  0, 16, 16,  0,  0, 16, 16, 55, 16, 17,  0, 16, 16, 16,  0,
            0, 16, 16, 16, 16,  0, 16, 16, 16, 16,  0,  0, 16, 16, 16,  0,
            0, 16, 16, 16, 16,  0, 55, 16, 55, 16, 16, 16, 16, 16,  0,  0,
            0, 16, 16, 16, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16, 16,  0,
            0, 16, 16, 16, 16, 16, 55, 16, 16, 16,  0,  0,  0,  0, 16,  0,
            0, 16, 18, 16,  0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,  0,
            0, 16, 16, 16,  0, 16, 16, 17, 16, 16,  0,  0, 16, 16, 16,  0,
            0, 16, 16,  0,  0,  0,  0,  0, 16, 16, 16,  0, 16, 16, 16,  0,
            0, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16,  0,  0,  0, 16,  0,
            0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
            0, 16, 16,  0,  0, 16, 16, 16, 18, 16, 16, 16, 16, 16, 16,  0,
            0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
	
	// 0 = mur | 16 = pacgomme bleu | 17 = pacgomme violet | 18 = pacgomme orange | 19 = pacgomme vert
	

	public int getTabPos(int pX, int pY){
		int uneCase = getBLOCK_SIZE(); //nombre de pixels pour une case
		int nbCase = getN_BLOCKS(); //nombre de cases dans une ligne
		int position = -1;
		int cx = 0;
		int cy = 0;
		if(pX%uneCase == 0 && pY%uneCase == 0) { //Pour verifier que le pacman est entierement dans une case avant de changer la direction
			cx = pX/uneCase; //position du pacman en nombre de cases sur l'axe x
			cy = pY/uneCase; //nombre de lignes (position du pacman sur l'axe y)
			position = cy * nbCase + cx; //position du pacman dans le tableau de la grille
		}
		return position;
	}
	
	public int getPosPacman() {
		return posPacman;
	}
	
	public void setPosPacman(int posPacman) {
		this.posPacman = posPacman;
	}
	
	public boolean isMortPacman() {
		return mortPacman;
	}
	
	public void setMortPacman(boolean mortPacman) {
		this.mortPacman = mortPacman;
	}
	
	public EnumPacman getStatutPacman() {
		return statutPacman;
	}

	public void setStatutPacman(EnumPacman statutPacman) {
		this.statutPacman = statutPacman;
	}
	
	public int contexte(int pos) {
		int code = 0;	
		int gridTab[] = gettab();
		int offset = getN_BLOCKS();  //pour ligne precedente ou ligne suivante
		
		if (gridTab[pos - offset] == 0) //mur en haut
			code = code | 1;
		if (gridTab[pos + offset] == 0) //mur en bas
			code = code | 4;
		if (gridTab[pos - 1] == 0)	  //mur a gauche
			code = code | 2;
		if (gridTab[pos + 1] == 0)	  //mur a droite
			code = code | 8;
		return code;
	}	
	
	
	public Grille(int x, int y) { 
	}
	public void paintComponent(Graphics g2) {
		//dessin de la grille
		int current_x;
		int current_y;

		for (int i=0;i<N_BLOCKS*N_BLOCKS;i++) {
			current_x = (i%N_BLOCKS)*BLOCK_SIZE;
			current_y = (i/N_BLOCKS)*BLOCK_SIZE;
			if (tab[i] == 0) {						//mur
				g2.setColor(Color.darkGray);
				g2.fillRect(current_x,current_y,BLOCK_SIZE ,BLOCK_SIZE);
			}
			if (tab[i] == 16){ 						//pacgomme bleu
                g2.setColor(Color.blue);
                g2.fillOval(current_x + 10, current_y + 10, 6, 6);
            }
			if (tab[i] == 17){ 
                g2.setColor(Color.magenta);			//pacgomme violet
                g2.fillOval(current_x + 10, current_y + 10, 12, 12);
           }
		   if (tab[i] == 18){ 
                g2.setColor(Color.orange);			//pacgomme orange
                g2.fillOval(current_x + 10, current_y + 10, 12, 12);
           }
		   if (tab[i] == 19){ 
               g2.setColor(Color.green);			//pacgomme vert
               g2.fillOval(current_x + 10, current_y + 10, 12, 12);
          }
		}
	}
	
	public int getBLOCK_SIZE() {
		return BLOCK_SIZE;
	}

	public int getN_BLOCKS() {
		return N_BLOCKS;
	}

	public int getSCREEN_SIZE() {
		return SCREEN_SIZE;
	}

	public int[] gettab() {
		return tab;
	}
	public void settab(int pos,int value) {
		tab[pos] = value;
	}
	public int gettabValue(int pos) {
		return tab[pos];
	}
	
	public void setNewTab(int[] newTab) {
		tab = newTab;
	}
	
	public boolean zeroPacgomme() {
		for(int i = 0; i < 255; i++){
			if(tab[i] != 55 && tab[i] != 0)
				return false;
		}
		return true;
	}
	
	public void changeStructGrille() {
	
		int[] tabTmp = {
				0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
			   16, 16, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16, 16, 16, 16,  0,
				0,  0, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16, 19, 16, 16,  0,
				0, 16, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16,  0, 16, 16,  0,
		        0,  0,  0, 16,  0,  0,  0, 16, 16, 16, 17, 16,  0, 16, 16,  0,
		        0,  0,  0, 16, 16, 16, 16, 16, 16, 16,  0,  0,  0, 16, 16,  0,
		        0, 16, 16, 16, 16,  0, 16, 16, 16, 16,  0, 16, 16, 16, 16,  0,
		        0, 16,  0, 16, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16, 16,  0,
		        0,  0, 16, 16, 16, 16, 16, 16, 16,  0,  0,  0,  0,  0, 16,  0,
		        0, 16, 18, 16, 16, 16,  0, 16, 16, 16, 16, 16, 16,  0, 16,  0,
		        0, 16,  0, 16, 16, 16,  0, 17, 16, 16,  0,  0, 16, 16, 16,  0,
		        0, 16, 16, 16,  0,  0,  0,  0, 16, 16, 16,  0, 16,  0, 16,  0,
		        0, 16, 16, 16, 16, 16, 16,  0, 16, 16, 16,  0,  0, 16, 16,  0,
		        0, 16, 16, 16,  0, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
		        0, 16, 16,  0,  0, 16, 16, 16, 18, 16, 16,  0, 16, 16, 16,  0,
		        0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		
		/*     GRILLE POUR TESTER LA victoire du Pacman (quand il a mange toutes les pacgommes) //
		 
		int[] tabTmp = {
				0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
			   55, 55, 55, 55, 55, 55, 55, 55,  0, 55, 55, 55, 55, 55, 55,  0,
				0,  0, 55, 55, 55, 55, 55, 55,  0, 55, 55, 55, 55, 55, 55,  0,
				0, 55, 55, 55, 55, 55, 55, 55,  0, 55, 55, 55,  0, 55, 55,  0,
		        0,  0,  0, 55,  0,  0,  0, 55, 55, 55, 55, 55,  0, 55, 55,  0,
		        0,  0,  0, 55, 55, 55, 55, 55, 55, 55,  0,  0,  0, 55, 55,  0,
		        0, 55, 55, 55, 55,  0, 55, 55, 55, 55,  0, 55, 55, 55, 55,  0,
		        0, 55,  0, 55, 55, 55, 55, 55, 55, 55,  0, 55, 55, 55, 55,  0,
		        0,  0, 55, 55, 55, 55, 55, 55, 55,  0,  0,  0,  0,  0, 55,  0,
		        0, 55, 55, 55, 55, 55,  0, 55, 55, 55, 55, 55, 55,  0, 55,  0,
		        0, 55,  0, 55, 55, 55,  0, 55, 55, 55,  0,  0, 55, 55, 55, 55,
		        0, 55, 55, 55,  0,  0,  0,  0, 55, 55, 55,  0, 55,  0, 55,  0,
		        0, 55, 55, 55, 55, 55, 55,  0, 55, 55, 55,  0,  0, 55, 55,  0,
		        0, 55, 55, 55,  0, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55,
		        0, 55, 55,  0,  0, 55, 55, 55, 55, 55, 55,  0, 55, 55, 55,  0,
		        0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
		*/
		for(int i = 0; i < 255; i++){
			if(tab[i] == 55 && tabTmp[i] != 0) 
				tabTmp[i] = 55;	
		}
		setNewTab(tabTmp);		
	}
	
	public void getSortiePacman(int posPacman) {
		if(posPacman == 223) {
			setPosPacman(16);
		}else if(posPacman == 16) {
			setPosPacman(223);
		}
	}
}