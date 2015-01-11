package zombie;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class Zombie extends Entity {

	public Zombie() {
		super();
		setSpeed(2);
	}

	public Zombie(Point p, Tile c, String image){
		super(p,c, image);
		setSpeed(2);
	}


	public void updatePlayer(Point man, ArrayList<Entity> entities, int me){

		boolean[] collide = collidesWithWall(currentTile.getBounds());

		for (int i = 0; i < entities.size(); i++) {
			if(i!=me){
				//min and max of x and y of rectangle
				double[] pos = {entities.get(i).getPosition().getMinX(),
						entities.get(i).getPosition().getMaxX(),
						entities.get(i).getPosition().getMinY(),
						entities.get(i).getPosition().getMaxY()};

				if(getPosition().intersectsLine(pos[0]+1, pos[2], pos[1]-1, pos[2]))collide[2] = true;
				if(getPosition().intersectsLine(pos[1], pos[2]+1, pos[1], pos[3]-1))collide[3] = true;
				if(getPosition().intersectsLine(pos[1]-1, pos[3], pos[0]+1, pos[3]))collide[0] = true;
				if(getPosition().intersectsLine(pos[0], pos[3]-1, pos[0], pos[2]+1))collide[1] = true;

			}
			if(entities.get(i) instanceof Projectile){
				if(getPosition().contains(entities.get(i).getPosition()) || getPosition().intersects(entities.get(i).getPosition())){
					doDamage(((Projectile) entities.get(i)).getDamage());
					entities.get(i).doDamage(100.0);
					System.out.println("Ouch");
				}
			}

			if(entities.get(i) instanceof Person){
				if(getPosition().contains(entities.get(i).getPosition()) || getPosition().intersects(entities.get(i).getPosition())){
					entities.get(i).doDamage(Math.random());
				}
			}
		}

		double rand = 0.8;
		boolean randBool = Math.random()<rand;
		boolean[] controls = {randBool && man.getY()<getPosition().getY(),randBool && man.getX()>getPosition().getX(),
				randBool && man.getY()>=getPosition().getY(),randBool && man.getX()<=getPosition().getX()};

		if(controls[0] && !collide[0]) setLocation(getPosition().getX(), getPosition().getY()-getSpeed());
		if(controls[1] && !collide[1]) setLocation(getPosition().getX()+getSpeed(), getPosition().getY());
		if(controls[2] && !collide[2]) setLocation(getPosition().getX(), getPosition().getY()+getSpeed());
		if(controls[3] && !collide[3]) setLocation(getPosition().getX()-getSpeed(), getPosition().getY());

	}

	@Override
	public void draw(Graphics g, Point offset) {
		//g.setColor(Color.GREEN);
		//g.drawRect((int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY(), 10, 10);		
		
		sprite.draw(g, (int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY());
	}

}
