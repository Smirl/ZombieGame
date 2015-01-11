package zombie;
import items.ExplosiveWeapon;
import items.ProjectileWeapon;
import items.Shotgun;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;


import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Game extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private Viewer v = new Viewer(this);
	private User u = new User(this);

	private Thread background = null;

	private ArrayList<Tile> theTiles = new ArrayList<Tile>();
	private Person man;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	private ArrayList<Splatter> effects = new ArrayList<Splatter>();
	
	private boolean[] controls = new boolean[4];
	private boolean firing = false;
	private Point mouse = new Point();
	private int currentItem =0;
	
	private Point offset = new Point();
	private long lastFire =0;


	/****************************************************************************/
	// Contructors
	/****************************************************************************/
	public Game() {
		super("Tile Test");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		v.addMouseListener(this);
		v.addMouseMotionListener(this);
		v.addMouseWheelListener(this);

		theTiles.clear();
		makeTiles(new Point(14*Tile.size,14*Tile.size));

		initEntities();

		addKeyListener(this);

		getContentPane().add(v,BorderLayout.CENTER);
		getContentPane().add(u,BorderLayout.NORTH);
		pack();

		setVisible(true);
	}

	/****************************************************************************/
	// Thread methods
	/****************************************************************************/

	/**
	 * See if the simulation is running
	 * @return true if is running
	 */
	public boolean running(){
		if(background == null){
			return false;
		}else{
			return true;
		}
	}

	/****************************************************************************/
	// Getters
	/****************************************************************************/

	public ArrayList<Tile> getCurrentMap(){
		synchronized (this) {
			return theTiles;
		}
	}
	public ArrayList<Entity> getCurrentEntities(){
		synchronized (this) {

			return entities;
		}
	}
	public ArrayList<Entity> getEntitiesToRemove(){
		synchronized (this) {

			return removeList;
		}
	}
	public ArrayList<Splatter> getCurrentSplatters(){
		synchronized (this) {

			return effects;
		}
	}
	public void addEntity(Entity e){
		entities.add(e);
	}
	public void removeEntity(Entity e){
		removeList.add(e);
	}
	public void addSplatter(Splatter s){
		effects.add(s);
	}

	/****************************************************************************/
	// KEY LISTENERS
	/****************************************************************************/
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==87) controls[0]=true; //up
		if(e.getKeyCode()==68) controls[1]=true; //right
		if(e.getKeyCode()==83) controls[2]=true; //down
		if(e.getKeyCode()==65) controls[3]=true; //left
		
		if(e.getKeyCode()==KeyEvent.VK_0) man.setCurrentItemNumber(9);
		if(e.getKeyCode()==KeyEvent.VK_1) man.setCurrentItemNumber(0);
		if(e.getKeyCode()==KeyEvent.VK_2) man.setCurrentItemNumber(1);
		if(e.getKeyCode()==KeyEvent.VK_3) man.setCurrentItemNumber(2);
		if(e.getKeyCode()==KeyEvent.VK_4) man.setCurrentItemNumber(3);
		if(e.getKeyCode()==KeyEvent.VK_5) man.setCurrentItemNumber(4);
		if(e.getKeyCode()==KeyEvent.VK_6) man.setCurrentItemNumber(5);
		if(e.getKeyCode()==KeyEvent.VK_7) man.setCurrentItemNumber(6);
		if(e.getKeyCode()==KeyEvent.VK_8) man.setCurrentItemNumber(7);
		if(e.getKeyCode()==KeyEvent.VK_9) man.setCurrentItemNumber(8);
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==87) controls[0]=false; //up
		if(e.getKeyCode()==68) controls[1]=false; //right
		if(e.getKeyCode()==83) controls[2]=false; //down
		if(e.getKeyCode()==65) controls[3]=false; //left
	}

	/****************************************************************************/
	// Tile update and creation methods
	/****************************************************************************/

	/**
	 * Updates all entities to the correct tile
	 */
	public void getCurrentTile() {

		for (int i = 0; i < entities.size(); i++) {
			if(!entities.get(i).getCurrentTile().getPosition().contains(entities.get(i).getPosition())) {
				for (int j = 0; j < theTiles.size(); j++) {
					if(theTiles.get(j).getPosition().contains(entities.get(i).getPosition())) {
						entities.get(i).setCurrentTile(theTiles.get(j));
						if(i==0) {
							makeTiles(man.getCurrentTile().getPosition().getLocation());
						}
						continue;
					}
				}
			}
		}
	}

	/**
	 * Trys to create 8 tiles in a square pattern around the tile at point p
	 * @param p The center tiles position
	 */
	public void makeTiles(Point p){
		//Make int 0= route false, 1=route true, 2=null route 
		int[] adjRoutes = new int[4];

		//inputTile(x-width,y-height)
		Point offsetTile = new Point((int)p.getX()-14*Tile.size,(int)p.getY()-14*Tile.size);

		//For each tile trying to be created
		for(int tx=0;tx<3;tx++){
			for (int ty = 0; ty < 3; ty++) {

				Point currentTile= new Point((int)offsetTile.getX()+ty*14*Tile.size,(int)offsetTile.getY()+tx*14*Tile.size);

				if(hasTile(currentTile)) continue;

				//For each adjacent tile look if there is a tile, then return the Routes Boolean
				for (int i = 0; i < adjRoutes.length; i++) {

					//The position of the adjacent tile to be looked at
					Point neighbour = new Point(Tile.tilePosition(currentTile, i));

					//if the neighbour tile is found or not
					boolean found = false;
					//Look for the neighbour tile
					for (int j = 0; j < theTiles.size(); j++) {
						//If it exists
						if(theTiles.get(j).getPosition().getX()==neighbour.getX() && theTiles.get(j).getPosition().getY()==neighbour.getY()){
							//set the adjacent in to either true (1) or false (0)
							if(theTiles.get(j).getRoutes()[(i+6)%4]) adjRoutes[i]=1;
							else adjRoutes[i]=0;
							found=true;
						}
					}
					//If the neighbour does NOT exist then set to null (2)
					if(!found) adjRoutes[i]=2;
				}
				//Create the tile from the neighbour info and the position
				theTiles.add(new Tile(currentTile,adjRoutes));
			}
		}
	}

	public boolean hasTile(Point p){
		for (int j = 0; j < theTiles.size(); j++) {
			if(theTiles.get(j).getPosition().getX()==p.getX() && theTiles.get(j).getPosition().getY()==p.getY()){
				return true;
			}
		}
		return false;
	}

	public void initEntities(){
		entities.clear();
		effects.clear();
				
		man = new Person(new Point(361,361),theTiles.get(5), "res/person.gif");
		//Game g, String l, double dam, double range, long rate, int ammo, int speed
		man.addItem(new ProjectileWeapon(this, "Magnum", 15, 2, 50, 100, 6));
		man.addItem(new Shotgun(this, "Shotgun", 50, 15, 400, 10, 10));
		man.addItem(new Shotgun(this, "Ashleigh Gun", 100, 2, 500, 1000, 6));
		man.addItem(new ExplosiveWeapon(this, "Rocket Launcher", 100, 2, 1000, 100, 5));
		entities.add(man);

		entities.add(new Zombie(new Point((int)theTiles.get(0).getPosition().getCenterX()-40, (int)theTiles.get(0).getPosition().getCenterY()+40), theTiles.get(0),"res/zombie.gif"));
		entities.add(new Zombie(new Point((int)theTiles.get(2).getPosition().getCenterX()-40, (int)theTiles.get(2).getPosition().getCenterY()+40), theTiles.get(2),"res/zombie.gif"));
		entities.add(new Zombie(new Point((int)theTiles.get(5).getPosition().getCenterX()-40, (int)theTiles.get(5).getPosition().getCenterY()+40), theTiles.get(5),"res/zombie.gif"));
		entities.add(new Zombie(new Point((int)theTiles.get(8).getPosition().getCenterX()-40, (int)theTiles.get(8).getPosition().getCenterY()+40), theTiles.get(8),"res/zombie.gif"));

	}
	
	public void tryAction(Point mouse){
		if(System.currentTimeMillis()-lastFire>man.getCurrentItem().getRateOfFire()){
			double angle=0;
			
			Point manPoint = entities.get(0).getPosition().getLocation();
			Point off = v.getOffset();
			
			angle = Math.atan2(mouse.getY()+off.getY()-manPoint.getY(), mouse.getX()+off.getX()-manPoint.getX());
			man.getCurrentItem().tryToAction(angle);
			if(man.getCurrentItem().getName().equals("Ashleigh Gun")){
				for (int i = 1; i < 4; i++) {
					man.getCurrentItem().tryToAction(angle+i*Math.toRadians(90));					
				}
			}
			lastFire=System.currentTimeMillis();
		}
	}

	/****************************************************************************/
	// Main game loops
	/****************************************************************************/

	/**
	 * Starts the simulation in the background thread if not alreading running. Sets up new instance of the SIRSModel with currentGrid and informs the 
	 * view and UserControls that the simulation is now running. 
	 */
	public void startSimulation() {

		if(background != null) return;
		// Set up a new simulation with the desired initial condition (since this may be different from the state in which the last simulation ended)
		theTiles.clear();
		makeTiles(new Point(14*Tile.size,14*Tile.size));

		initEntities();

		v.setOffset(new Point());
		// Run it in a new background thread
		background = new Thread() {
			@Override
			public void run() {
				// Communicate with the view objects on the Swing thread
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						// Inform the view objects that the simulation has started
						v.simulationStarted();
						u.simulationStarted();
					}
				});
				/****************************************************************************/
				// MAIN GAME LOOP 
				//Repeatedly iterate the equations of motion until the thread is interrupted
				/****************************************************************************/
				while(!isInterrupted()){

					//Put Entities on the correct tile
					getCurrentTile();
					
					if(firing) {
						//Point temp = new Point(MouseInfo.getPointerInfo().getLocation());
						
						//mouse=temp.translate(dx, dy);
						tryAction(mouse);
					}

					//Move all of the zombies
					for (int i = 0; i < entities.size(); i++) {
						if(entities.get(i) instanceof Zombie){
							((Zombie) entities.get(i)).updatePlayer(man.getPosition().getLocation(), entities, i);
						}

						if(entities.get(i) instanceof Person){
							if(entities.get(i).getHealth()<=0) background.interrupt();
							v.setOffset(((Person) entities.get(i)).updatePlayer(controls, new Dimension(v.getWidth(),v.getHeight()), v.getOffset()));
						}
						
						if(entities.get(i) instanceof Projectile){
							((Projectile) entities.get(i)).update();
							entities.get(i).doDamage(1*man.getCurrentItem().getRange());
						}
						
						if(entities.get(i).getHealth()<=0) {
							if(entities.get(i) instanceof Zombie) addSplatter(new Splatter(entities.get(i).getPosition().getLocation()));
							if(entities.get(i) instanceof ExplosiveProjectile) ((ExplosiveProjectile) entities.get(i)).explode();
							removeList.add(entities.get(i));
						}
						
					}
					long modTime = System.currentTimeMillis()%4000;
					if(modTime<=20 && modTime>=0){
						entities.add(new Zombie(new Point(151+(int)modTime,151), theTiles.get(5), "res/zombie.gif"));
					}
					
					entities.removeAll(removeList);
					
					//Wait a little while
					try {
						Thread.sleep(30);
					} catch (Exception e) {
						background.interrupt();
					}
				}
				// Communicate with the view objects on the Swing thread
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						background = null;
						// Inform the view objects that the simulation has finished
						v.simulationStopped();
						u.simulationStopped();
					}
				});
			}
		}; //End of init background thread.
		background.start();
	}

	/**
	 * Interupts the thread and stops the times for running. 
	 */
	public void stopSimulation() {
		// Check the a background thread is actually running
		if(background == null) return;

		// Send the interrupt message to the background thread to stop it at a convenient point
		repaint();
		background.interrupt();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Game();

			}
		});

	}

	public void mouseClicked(MouseEvent e) {
		tryAction(mouse);
	}

	//@Override
	public void mousePressed(MouseEvent e) {
		firing=true;
		mouse=e.getPoint();
	}

	//@Override
	public void mouseReleased(MouseEvent e) {
		firing=false;	
	}

	//@Override
	public void mouseEntered(MouseEvent e) {}

	//@Override
	public void mouseExited(MouseEvent e) {}

	//@Override
	public void mouseDragged(MouseEvent e) {
		firing=true;
		mouse=e.getPoint();	
	}

	//@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	//@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		currentItem += e.getWheelRotation();
		if(currentItem>=man.getInventory().size()) currentItem = man.getInventory().size()-1;
		if(currentItem<0) currentItem=0;
		man.setCurrentItemNumber(currentItem);
	}

}
