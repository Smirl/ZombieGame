package zombie;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JPanel;


public class User extends JPanel {
	
	private Game controller;
	
	public static Color redRidingHood = new Color(209,72,54);
	
	private JButton start = new JButton("START");
	
	public User(Game attachedTo) {
	controller = attachedTo;
	setOpaque(true);
	setBackground(Color.WHITE);
	//Size of the left hand user panel
	setPreferredSize(new Dimension(800,50));
	
	start.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			if (controller.running()) {
				controller.stopSimulation();
			}else{
				controller.startSimulation();
			}
		}
	});
	
	//Start-stop button style including dimensions and colours
	start.setPreferredSize(new Dimension(130, 35));
	start.setFocusable(false);
	
	add(start);
	
	}
	
	/**
	 * Called when simulation started to change most elements to non editable. Changes start-stop text
	 * and gridsize in case user has not pressed enter on gridsize (to avoid confusion)
	 */
	public void simulationStarted(){
		start.setText("STOP");
	}

	/**
	 * Called when simulation is stopped to change most elements to editable. Changes start-stop text
	 * 
	 */
	public void simulationStopped(){
		start.setText("START");
	}
	
}
