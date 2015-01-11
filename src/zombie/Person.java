package zombie;
import items.Item;
import items.ProjectileWeapon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Person extends Entity{

	private double damageMultipler = 1.0;

	//Pixels around edge of screen to scroll BG not player. 
	private int margin = 399;
	
	private ArrayList<Item> inventory = new ArrayList<Item>();
	
	private Item currentItem =null;


	public Person() {
		super();
	}
	public Person(Point p, Tile c, String image){
		super(p,c, image);
	}

	/***************************************************************************/
	// Getters and Setters
	/***************************************************************************/

	public void setDamageMultipler(double damageMultipler) {
		this.damageMultipler = damageMultipler;
	}


	public double getDamageMultipler() {
		return damageMultipler;
	}
	public void setInventory(ArrayList<Item> newList){
		inventory=newList;
	}
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	public Item getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItemNumber(int currentItemNumber) {
		this.currentItem = inventory.get(currentItemNumber);
	}
	
	/***************************************************************************/
	// Moving
	/***************************************************************************/

	
	/**
	 * Moves the player in the direction determined by the controls boolean[] if not colliding with currentTiles boundaries.
	 * Player is moved in the global co-ords regardless but if near edge of screen of size, d, then offset is changed. 
	 */
	public Point updatePlayer(boolean[] controls, Dimension d, Point o){

		
		Point offset = new Point(o);
		
		//If inside the margin
		boolean[] edgeOfScreen = {(getPosition().getMinY()-offset.getY())<margin,
				(getPosition().getMaxX()-offset.getX())>d.getWidth()-margin,
				(getPosition().getMaxY()-offset.getY())>d.getHeight()-margin,
				(getPosition().getMinX()-offset.getX())<margin};

		
		boolean[] collide = collidesWithWall(currentTile.getBounds());
		
		
		if(controls[0] && !collide[0]){
			if(edgeOfScreen[0]) offset.setLocation(offset.getX(), offset.getY()-getSpeed());
				setLocation(getPosition().getX(), getPosition().getY()-getSpeed());
		}

		if(controls[1] && !collide[1]){
			if(edgeOfScreen[1]) offset.setLocation(offset.getX()+getSpeed(), offset.getY());
			setLocation(getPosition().getX()+getSpeed(), getPosition().getY());
		}

		if(controls[2] && !collide[2]){
			if(edgeOfScreen[2]) offset.setLocation(offset.getX(), offset.getY()+getSpeed());
			setLocation(getPosition().getX(), getPosition().getY()+getSpeed());
		}

		if(controls[3] && !collide[3]){
			if(edgeOfScreen[3]) offset.setLocation(offset.getX()-getSpeed(), offset.getY());
			setLocation(getPosition().getX()-getSpeed(), getPosition().getY());
		}

		return offset;
	}


	/***************************************************************************/
	// Others
	/***************************************************************************/
	
	/**
	 * Does damage to entity
	 * @param dam Amount of damage to try to apply
	 */
	@Override
	public void doDamage(double dam){
		setHealth(health-getDamageMultipler()*dam);
	}
	
	
	@Override
	public void draw(Graphics g, Point offset) {
		//g.setColor(Color.RED);
		//g.drawRect((int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY(), 10, 10);
		
		sprite.draw(g, (int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY());
	}
	
	public void addItem(Item newItem) {
		inventory.add(newItem);
		currentItem=newItem;
	}

}
