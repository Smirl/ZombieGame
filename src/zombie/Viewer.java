package zombie;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Viewer extends JComponent{

	private Game controller;

	private Point offset = new Point();
	private Point mouse = new Point();
	private Point inv;

	private Timer timer = new Timer(30, new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			repaint();	
		}
	});

	/**
	 * Create new panel to paint main simulation
	 * @param attachedTo the controller
	 */
	public Viewer(Game attachedTo) {
		controller = attachedTo;
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(800,800));
		inv = new Point(400-225, 740);
	}

	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponents(g);

		if(isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		//Paint the tiles
		ArrayList<Tile> theTiles = controller.getCurrentMap();
		for (int i = 0; i < theTiles.size(); i++) {
			theTiles.get(i).paint(g, offset);			
		}


		ArrayList<Splatter> effects = controller.getCurrentSplatters();
		for (int i = 0; i < effects.size(); i++) {
			effects.get(i).draw(g, offset);
		}


		ArrayList<Entity> entities = controller.getCurrentEntities();
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g, offset);
		}

		for (int i = 0; i < 9; i++) {
			g.setColor(new Color(100, 150, 50));

			try {
				if(((Person) entities.get(0)).getCurrentItem().equals(((Person) entities.get(0)).getInventory().get(i))) g.setColor(Color.BLUE);
			} catch (Exception e) {}

			g.fill3DRect((int)inv.getX()+50*i,(int) inv.getY(), 50, 50, true);
		}
		
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.DARK_GRAY);
		g.drawString(String.format("%.2f", entities.get(0).getHealth()), 30, 30);



		//g.drawLine((int)man.getX()-(int)offset.getX()+5, (int)man.getY()-(int)offset.getY()+5, (int)mouse.getX()-(int)offset.getX(), (int)mouse.getY()-(int)offset.getY());
	}
	/**
	 * Called when simulation is started to starts painting the grid at 50 millisecond intervals
	 */
	public void simulationStarted() {

		// Ensure display is up-to-date
		repaint();
		// Send repaint messages at regular intervals using the autoUpdater Timer object
		timer.restart();

	}

	/**
	 * Called when simulation is stopped, Stops timer and paints final state.
	 */
	public void simulationStopped() {

		// Stop sending regular repaint messages
		timer.stop();
		// Ensure display is up-to-date
		repaint();

	}

	public void setOffset(Point p){
		this.offset = new Point(p);
	}
	public Point getOffset(){
		return offset;
	}


}
