package zombie;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;


public abstract class Entity {

	/** Bounding box of entity*/
	protected Rectangle position = new Rectangle();
	/** */
	protected double health = 100.0;
	/** */
	//private double damageMultipler = 1.0;
	/** */
	protected Tile currentTile; 
	/** */
	protected int width = 10;

	protected double speed = 2.0;
	
	protected Sprite sprite;
	
	public Entity() {
		setPosition(new Point(),new Dimension(width, width));
		currentTile = new Tile();
		sprite = SpriteStore.get().getSprite("");
	}
	public Entity(Point p, Tile c, String spriteImage){
		setPosition(new Point(p),new Dimension(width, width));
		currentTile=new Tile(c);
		sprite = SpriteStore.get().getSprite(spriteImage);
	}
	
	/***************************************************************************/
	// Getters and Setters
	/***************************************************************************/
	public void setHealth(double health) {
		this.health = health;
	}


	public double getHealth() {
		return health;
	}

	public void setPosition(Point position, Dimension d) {
		this.position.setLocation(position);
		this.position.setSize(d);
	}

	public void translate(int dx, int dy){
		position.translate(dx, dy);
	}
	public void setLocation(double x, double y){
		Point temp = new Point();
		temp.setLocation(x, y);
		position.setLocation(temp);
	}

	public Rectangle getPosition() {
		return position;
	}

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public double getSpeed(){
		return speed;
	}

	public void setSpeed(double s){
		this.speed = s;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	public int getWidth() {
		return width;
	}
	/***************************************************************************/
	// Common Methods
	/***************************************************************************/
	/**
	 * Return boolean array. True if hitting something in Ith direction. 
	 * @param bounds The bounding rectangles of the current tile.
	 * @return boolean array of collisions in ith direction
	 */
	public boolean[] collidesWithWall(ArrayList<Rectangle> bounds){
		//True if hitting something in ith direction
		boolean[] collide = {false,false,false,false};
		
		for (int i = 0; i < bounds.size(); i++) {
			//min and max of x and y of rectangle
			double[] pos = {bounds.get(i).getMinX(),
					bounds.get(i).getMaxX(),
					bounds.get(i).getMinY(),
					bounds.get(i).getMaxY()};

			if(getPosition().intersectsLine(pos[0]+1, pos[2], pos[1]-1, pos[2]))collide[2] = true;
			if(getPosition().intersectsLine(pos[1], pos[2]+1, pos[1], pos[3]-1))collide[3] = true;
			if(getPosition().intersectsLine(pos[1]-1, pos[3], pos[0]+1, pos[3]))collide[0] = true;
			if(getPosition().intersectsLine(pos[0], pos[3]-1, pos[0], pos[2]+1))collide[1] = true;

		}
		
		return collide;
	}
	
	public abstract void draw(Graphics g, Point offset);
	
	/**
	 * Does damage to entity
	 * @param dam Amount of damage to try to apply
	 */
	public void doDamage(double dam){
		setHealth(health-dam);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+getPosition();
	}

	
}
