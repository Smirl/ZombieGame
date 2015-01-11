package zombie;
import items.ProjectileWeapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Projectile extends Entity {

	private double damage;

	private double angle;

	public Projectile(){
		super();
		damage=0;
		angle=0;
	}
	public Projectile(double a, ProjectileWeapon weapon){
		super();
		setWidth(1);
		damage=weapon.getDamage();
		setSpeed(weapon.getSpeed());
		angle=a;
	}
	public Projectile(Point p, Tile c, double a, ProjectileWeapon weapon){
		super(p, c, "res/proj.gif");
		setWidth(1);
		damage=weapon.getDamage();
		setSpeed(weapon.getSpeed());
		angle=a;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void update(){
		boolean collide = false;
		
		for (int i = 0; i < getCurrentTile().getBounds().size(); i++) {
			if(getCurrentTile().getBounds().get(i).contains(getPosition()) || getCurrentTile().getBounds().get(i).intersects(getPosition())){
				collide=true;
				break;
			}
		}

		if(collide){
			doDamage(100);
		}
		setLocation(getPosition().getX()+getSpeed()*Math.cos(angle), getPosition().getY()+getSpeed()*Math.sin(angle));

	}


	@Override
	public void draw(Graphics g, Point offset) {
		//g.setColor(Color.CYAN);
		//g.fillRect((int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY(), 3, 3);
		
		sprite.draw(g, (int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY());
	}


}
