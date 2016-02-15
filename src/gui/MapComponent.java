package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import backend.Map;

public class MapComponent extends JComponent {
	private Map stateMap;
	
	public MapComponent(){
		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//boxes
		Graphics2D g2 = (Graphics2D) g;
		Rectangle map= new Rectangle(50,50,650,650);//x1,y1,x1->x2,y1->y2
		Rectangle list= new Rectangle(750,50,200,650);//x1,y1,x2,y2
		Rectangle from= new Rectangle(50,750,250,50);
		Rectangle to= new Rectangle(50,850,250,50);
		Rectangle findRoute= new Rectangle(500, 750, 200, 50);
		Rectangle reset= new Rectangle(500, 850, 200, 50);
//
//		JTextComponent txt= new JTextField("asdfasdf");
//		txt.setLocation(200, 200);;
//		txt.setBounds(map);
//		map.a;
		
		g2.draw(list);
		g2.draw(from);
		g2.draw(map);
		g2.draw(to);
		g2.draw(findRoute);
		g2.draw(reset);
	
		
		
		//circles
		Ellipse2D.Double time = new Ellipse2D.Double(330, 750, 30, 30);
		Ellipse2D.Double noToll = new Ellipse2D.Double(330, 800, 30, 30);
		Ellipse2D.Double rating = new Ellipse2D.Double(330, 850, 30, 30);
		Ellipse2D.Double distance = new Ellipse2D.Double(330, 900, 30, 30);
		g2.draw(time);
		g2.draw(noToll);
		g2.draw(rating);
		g2.draw(distance);
		
		//textures
		Font font = new Font("Arial", Font.BOLD, 20);
		g2.setFont(font);
		g2.drawString("Time", 370, 770);
		g2.drawString("No Toll", 370, 820);
		g2.drawString("Rating", 370, 870);
		g2.drawString("Distance", 370, 920);
		
	}
	

}
//Rectangle box = new Rectangle(10,20,30,40);
//g2.draw(box);
//g2.setColor(new Color(255,128,0, 1));
//g2.fill(box);
//
//
//
//
//for (int size = 10; size< this.getWidth(); size+=10){
//	box = new Rectangle(0, 0, size, size);
//	g2.setColor(Color.black);
//	g2.draw(box);
//
//	
//}
//Ellipse2D.Double circle = new Ellipse2D.Double(50, 400, 100, 100);
//g2.draw(circle);
//
//Font font = new Font("Arial", Font.ITALIC, 200);
//g2.setFont(font);
//
//g2.drawString("Hello", 50, 200);
//g2.drawString("Birthday", 10, 300);
//
//box = new Rectangle (0, 0, this.getWidth(), this.getHeight());
//g2.setColor(new Color(255,128,255,64));
//g2.fill(box);