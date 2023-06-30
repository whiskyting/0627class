package tw.org.iii.classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.DebugGraphics;
import javax.swing.JPanel;

public class MyDrawerV2 extends JPanel{
	private LinkedList< LinkedList< HashMap<String, Integer>>> lines,garbag;
	
	
	public MyDrawerV2() {
		lines = new LinkedList<>();
		garbag = new LinkedList<>();
		setBackground(Color.green);
		MyListener myListener = new MyListener();
		addMouseListener(myListener);
		addMouseMotionListener(myListener);
	}
	
	private class MyListener extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX(),y = e.getY();
			HashMap<String, Integer> point =new HashMap<>();
			point.put("x",x);point.put("y", y);
			
			LinkedList< HashMap<String, Integer>>line = new LinkedList<>();
			line.add(point);
			
			lines.add(line);
			
			garbag.clear();
//			repaint();
		}
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			
//		}
		@Override
		public void mouseDragged(MouseEvent e) {
			int x = e.getX(),y = e.getY();
			HashMap<String, Integer> point =new HashMap<>();
			point.put("x",x);point.put("y", y);
			
			lines.getLast().add(point);
			
			repaint();
		}
	}
	
	
	protected void paintComponent(Graphics g) { //畫的原件
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(15));
		
		for(LinkedList< HashMap<String, Integer>> line:lines) {
			for (int i=1;i<line.size();i++) {
				HashMap<String, Integer> p0 = line.get(i-1);
				HashMap<String, Integer> p1 = line.get(i);
				g2d.drawLine(p0.get("x"), p0.get("y"),p1.get("x"),p1.get("y"));
			}
		}
	}
		public void clear() {
			lines.clear();
			repaint();
		}
		
		public void undo() {
			lines.removeLast();
			repaint();;
		}
		
		public void saveJPEG() {
			BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			paint(g);
			try {
				ImageIO.write(img,"jpg",new File("dir1/brad.jpg"));
			} catch (IOException e) {
				System.out.println(e);
			}
		}

		public void saveLines() throws Exception{
			try(ObjectOutputStream oout = 
					new ObjectOutputStream(new FileOutputStream("dir1/brad.sign"))){
					oout.writeObject(oout);
					oout.flush();				
			}
			
		}
		
		public void loadLines() throws Exception{
			ObjectInputStream oin = 
					new ObjectInputStream(new FileInputStream("dir1/brad.sign"));
					Object obj = oin.readObject();
					lines = (LinkedList< LinkedList< HashMap<String, Integer>>>)obj;
					oin.close();
					
					repaint();
							
					
		}
		
	}

class Line{
	private LinkedList< HashMap<String, Integer>> points;
	private Color color;
	private float width;
	
	public Line(Color color,float width) {
		points = new LinkedList<>();
		this.color = color;
		this.width = width;
		
	}
	
	public void addPoint(int x,int y) {
		HashMap<String, Integer>point = new HashMap<>();
		point.put("x", x);point.put("y",y);
		points.add(point);
		
	}
}
