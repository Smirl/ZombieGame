package zombie;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Tile {

	public static int size = 21;

	private Rectangle position = new Rectangle(new Point(), new Dimension(14*size,14*size));
	private ArrayList<Rectangle> bounds = new ArrayList<Rectangle>();
	private boolean[] routes = {false,false,false,false,true};
	
	private Sprite road = SpriteStore.get().getSprite("res/road.gif");
	private Sprite hori = SpriteStore.get().getSprite("res/hori.gif");
	private Sprite vert = SpriteStore.get().getSprite("res/vert.gif");
	private Sprite round = SpriteStore.get().getSprite("res/round.gif");

	private static final Rectangle[] posibleBounds = {new Rectangle(3*size, 11*size, 8*size , 3*size), new Rectangle(11*size, 3*size, 3*size, 8*size),
		new Rectangle(3*size, 0*size, 8*size, 3*size), new Rectangle(0*size, 3*size, 3*size, 8*size), 
		new Rectangle(6*size, 6*size, 2*size, 2*size)};

	public Tile() {
		position.setLocation(0,0);
		for (int i = 0; i < routes.length; i++) {
			routes[i]=false;
		}
		addCorners();
		addBoundingBoxes();
		offsetBounds();
	}

	public Tile(Tile t) {
		position.setLocation((int)t.getPosition().getLocation().getX(),(int)t.getPosition().getLocation().getY());

		boolean[] r = t.getRoutes();
		for (int i = 0; i < routes.length; i++) {
			routes[i]=r[i];
		}

		ArrayList<Rectangle> b = t.getBounds();
		for (int i = 0; i < b.size(); i++) {
			bounds.add(new Rectangle(b.get(i)));
		}
	}

	public Tile(Point p, int[] adj){
		//number of routes and blocked routes
		int numberOfRoutes =0;
		int numberOfBlocked =0;
		
		for (int i = 0; i < 4; i++) {
			//if tile in i direction has a wall then set that direction as a route
			//Match the adjacent route boolean
			if(adj[i]==1){
				routes[i]=true;
				numberOfRoutes++;
			}else if(adj[i]==0){
				routes[i]=false;
				numberOfBlocked++;
			}else if(adj[i]==2){
				//50% chance of making a route
				if(Math.random()<=0.5){
					routes[i]=true;
					numberOfRoutes++;
				}
			}

		}
		//get rid of dead ends if block doesn't have to be
		while(numberOfRoutes==1 && numberOfBlocked!=4) {
			for (int i = 0; i < adj.length; i++) {
				if(adj[i]==2 && Math.random()<=0.5){
					routes[i]=true;
					numberOfRoutes++;
				}
			}
		}
		//randomly fill the middle if a cross road
		if(numberOfRoutes==4 && Math.random()<=0.7) routes[4]=false;

		setLocation(p);

		addCorners();
		addBoundingBoxes();
		offsetBounds();

	//	System.out.println(this);
		//System.out.println();

	}

	public Tile(int x, int y){
		position.setLocation(x, y);
		for (int i = 0; i < routes.length; i++) {
			routes[i]=true;
		}
		addCorners();
		offsetBounds();
	}

	/***************************************************************************/
	// Getters and Setters
	/***************************************************************************/

	public Rectangle getPosition(){return position;}
	public ArrayList<Rectangle> getBounds() {return bounds;}
	public boolean[] getRoutes() {return routes;}

	public void setX(int x){
		position.setLocation(x, (int)getPosition().getLocation().getY());
	}
	public void setY(int y){
		position.setLocation((int)getPosition().getLocation().getX(),y);
	}
	public void setLocation(Point p){
		position.setLocation(p);
	}
	public void setRoute(int direction,boolean a){
		routes[direction] = a;
	}
	public void setRoute(boolean[] b){
		for (int i = 0; i < b.length; i++) {
			routes[i]=b[i];
		}
	}
	public void clearBounds(){
		bounds.clear();
	}

	/***************************************************************************/
	// Others
	/***************************************************************************/

	public void addCorners(){
		bounds.add(new Rectangle(0*size, 0*size, 3*size , 3*size));
		bounds.add(new Rectangle(11*size, 0*size, 3*size, 3*size));
		bounds.add(new Rectangle(0*size, 11*size, 3*size, 3*size));
		bounds.add(new Rectangle(11*size, 11*size, 3*size, 3*size));
	}

	public void addBoundingBoxes(){
		if(!routes[0]) bounds.add(new Rectangle(posibleBounds[2]));
		if(!routes[1]) bounds.add(new Rectangle(posibleBounds[1]));
		if(!routes[2]) bounds.add(new Rectangle(posibleBounds[0]));
		if(!routes[3]) bounds.add(new Rectangle(posibleBounds[3]));
		if(!routes[4]) bounds.add(new Rectangle(posibleBounds[4]));
	}

	public void offsetBounds(){
		for (int i = 0; i < bounds.size(); i++) {
			Rectangle temp = bounds.get(i);
			temp.translate((int)getPosition().getX(), (int)getPosition().getY());
			bounds.set(i, temp);
		}
	}

	public void randomChooseRoutes(int path){
		int numberOfRoutes = (int)(Math.random()*3.0+1.0);
		while(numberOfRoutes!=0){
			int rand = (int)(Math.random()*5.0-1.0);
			if(rand!=path) routes[rand]=true;

		}

	}

	public boolean hasBoundingBox(Tile b, Rectangle r){

		for (int i = 0; i < b.getBounds().size(); i++) {
			if(b.getBounds().get(i).getX()==r.getX()+b.getPosition().getX() && b.getBounds().get(i).getY()==r.getY()+b.getPosition().getY()) return true;
		}
		return false;
	}

	public static Point tilePosition(Point p, int d){
		Point out = new Point();
		switch (d) {
		case 0: out.setLocation(p.getX(), p.getY()-(double)14*size); break;
		case 1: out.setLocation(p.getX()+(double)14*size, p.getY());break;
		case 2: out.setLocation(p.getX(), p.getY()+(double)14*size); break;
		case 3: out.setLocation(p.getX()-(double)14*size, p.getY()); break;
		default:System.out.println("Incorrect Direction"); break;
		}

		return out;
	}
	
	public void paint(Graphics g, Point offset){
		//The inner part of the tile is white
		road.draw(g, (int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY());
		
		if(!routes[0]) hori.draw(g, (int)(getPosition().getX()+63-offset.getX()), (int)(getPosition().getY()-63-76-offset.getY()));
		if(!routes[3]) vert.draw(g, (int)(getPosition().getX()-63-76-offset.getX()), (int)(getPosition().getY()+63-offset.getY()));
		
		if(!routes[4]) round.draw(g, (int)(getPosition().getX()+126-offset.getX()), (int)(getPosition().getY()+126-offset.getY()));
		
		//g.setColor(Color.black);
		
		//Tile boundary for debugging
		//g.drawRect((int)getPosition().getX()-(int)offset.getX(), (int)getPosition().getY()-(int)offset.getY(), 14*size, 14*size);
		
		//Draw the walls
		//for (int j = 0; j < bounds.size(); j++) {
		//	g.fillRect((int)bounds.get(j).getX()-(int)offset.getX(), (int)bounds.get(j).getY()-(int)offset.getY(), (int)bounds.get(j).getWidth(), (int)bounds.get(j).getHeight());
		//}
	}
	
	@Override
	public String toString() {
		return getPosition().getX()+" "+getPosition().getY()+" "+getRoutes()[0]+" "+getRoutes()[1]+" "+getRoutes()[2]+" "+getRoutes()[3]+" "+getRoutes()[4];
	}

}
