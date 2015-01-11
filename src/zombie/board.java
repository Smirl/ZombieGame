package zombie;
/* This script is to find the daily chargeable balance for an offset mortgage given a comma separated variable file containing an unknown number of accounts and days in the statement.*/


import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
//import java.awt.geom.*;
import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;


public class board extends JPanel implements KeyListener, MouseInputListener {

	javax.swing.Timer timer;
	MouseMotionListener mouseListener;
	double width = 800;
	double height = 600;
	double margin = 150;
	double speed = 5;

	boolean dir[] = new boolean[5];;
	private double heroX = 200, heroY =200;
	private double bgX = 0, bgY =0;
	private double winX = 0, winY =0;
	private AffineTransform at = new AffineTransform();

	private BufferedImage BG;
	private BufferedImage hero1;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5084398283666566099L;

	public board() throws IOException{

		makeTimer();
		ImagePanel();

	}

	public void move() throws IOException {

		boolean inside[] = {false,heroY>margin,heroX<width-margin,heroY<height-margin,heroX>margin};

		if(dir[1] && !dir[4] && !dir[2]){
			if(inside[1]){
				at.setToTranslation(0, -speed);
				heroY-=speed;
			}else{
				bgY+=speed;
			}
		}
		if(dir[2] && !dir[1] && !dir[3]){
			if(inside[2]){
				at.setToTranslation(speed, 0);
				heroX += speed;
			}else{
				bgX-=speed;
			}
		}
		if(dir[3] && !dir[4] && !dir[2]){
			if(inside[3]){
				at.setToTranslation(0, speed);
				heroY += speed;
			}else{
				bgY-=speed;
			}
		}
		if(dir[4] && !dir[1] && !dir[3]){
			if(inside[4]){
				at.setToTranslation(-speed,0);
				heroY -=speed;
			}else{
				bgX+=speed;
			}
		}

		if(dir[1] && dir[2] && inside[1] && inside[2]){
			heroY-=0.707*speed;
			heroX+=0.707*speed;
			at.translate(0, -speed*0.707);
		}
		if(dir[1] && dir[2] && inside[1] && !inside[2]){
			heroY-=0.707*speed;
			bgX-=0.707*speed;
		}
		if(dir[1] && dir[2] && !inside[1] && inside[2]){
			bgY+=0.707*speed;
			heroX+=0.707*speed;
		}
		if(dir[1] && dir[2] && !inside[1] && !inside[2]){
			bgY+=0.707*speed;
			bgX-=0.707*speed;
		}
		
		if(dir[3] && dir[2] && inside[3] && inside[2]){
			heroY+=0.707*speed;
			heroX+=0.707*speed;
		}
		if(dir[3] && dir[2] && inside[3] && !inside[2]){
			heroY+=0.707*speed;
			bgX-=0.707*speed;
		}
		if(dir[3] && dir[2] && !inside[3] && inside[2]){
			bgY-=0.707*speed;
			heroX+=0.707*speed;
		}
		if(dir[3] && dir[2] && !inside[3] && !inside[2]){
			bgY-=0.707*speed;
			bgX-=0.707*speed;
		}
		
		if(dir[3] && dir[4] && inside[3] && inside[4]){
			heroY+=0.707*speed;
			heroX-=0.707*speed;
		}
		if(dir[3] && dir[4] && inside[3] && !inside[4]){
			heroY+=0.707*speed;
			bgX+=0.707*speed;
		}
		if(dir[3] && dir[4] && !inside[3] && inside[4]){
			bgY-=0.707*speed;
			heroX-=0.707*speed;
		}
		if(dir[3] && dir[4] && !inside[3] && !inside[4]){
			bgY-=0.707*speed;
			bgX+=0.707*speed;
		}
		
		if(dir[1] && dir[4] && inside[1] && inside[4]){
			heroY-=0.707*speed;
			heroX-=0.707*speed;
		}
		if(dir[1] && dir[4] && inside[1] && !inside[4]){
			heroY-=0.707*speed;
			bgX+=0.707*speed;
		}
		if(dir[1] && dir[4] && !inside[1] && inside[4]){
			bgY+=0.707*speed;
			heroX-=0.707*speed;
		}
		if(dir[1] && dir[4] && !inside[1] && !inside[4]){
			bgY+=0.707*speed;
			bgX+=0.707*speed;
		}


	}
//collapsed

	//************************************************************
	//Timer Classes
	//************************************************************
	public void makeTimer(){

		timer = new javax.swing.Timer(20,new ActionListener(){

			public void actionPerformed(ActionEvent e){
				try {
					move();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}


		});

		timer.start();
	}



	//************************************************************
	//Paint Class
	//************************************************************
	public void paint(Graphics g){

		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		RenderingHints rh =new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2.setRenderingHints(rh);

		draw(g2);


	}

	public void draw(Graphics2D g2){
		AffineTransform a1 = new AffineTransform();
		a1.translate(bgX, bgY-800);
		g2.drawRenderedImage((RenderedImage)BG, a1);


		AffineTransform a2 = new AffineTransform();
		double dx = winX - heroX ;
		double dy = winY - heroY ;


		//at.translate(dx, dy);
		//at.setToRotation(Math.atan2(dy, dx)-Math.PI/2);
		//g2.setTransform(at);
		//g2.drawRenderedImage((RenderedImage)hero1, at);
		
		g2.fillRect((int)heroX, (int)heroY, 10, 10);
		

	}

	public Shape triangle(double xx, double yy, double ww, double hh) {
		int x=(int)xx,y=(int)yy,w=(int)ww,h=(int)hh;
		int xs[] = {x,x+(w/2),x+w};
		int ys[] = {y-h,y,y-h};
		return new Polygon(xs,ys,3);


	}

	public void ImagePanel() {
		try {                
			BG = ImageIO.read(new File("BG.jpg"));
			hero1 = ImageIO.read(new File("hero1.gif"));
		} catch (IOException ex) {
			// handle exception...
		}
	}


	//****************************************************************************************************************
	//KeyListeners and ActionListeners
	//****************************************************************************************************************	
	public void keyTyped(KeyEvent e) { 
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==87){//up
			dir[1]=true;
		}
		if(e.getKeyCode()==68){//right
			dir[2]=true;
		}
		if(e.getKeyCode()==83){//down
			dir[3]=true;
		}
		if(e.getKeyCode()==65){//left
			dir[4]=true;
		}



	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==87){//up
			dir[1]=false;
		}
		if(e.getKeyCode()==68){//right
			dir[2]=false;
		}
		if(e.getKeyCode()==83){//down
			dir[3]=false;
		}
		if(e.getKeyCode()==65){//left
			dir[4]=false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		winX = e.getX();
		winY = e.getY();

	}

	public void mouseMoved(MouseEvent e) {
		winX = e.getX();
		winY = e.getY();
	}


}