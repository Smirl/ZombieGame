package zombie;
import java.io.IOException;

import javax.swing.*;

public class zombieTest extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5626433763512016034L;
	board C;

	public zombieTest() throws IOException{

		C = new board();
		add(C);

		setTitle("Zombie Test Platform");
		setVisible(true);
		setSize(800,600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		this.addKeyListener(C);
		this.addMouseMotionListener(C);


	}

	public static void main(String[] argv) throws IOException{
		new zombieTest();
	}
}