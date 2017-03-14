package com.thegoodhen.pipeTest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class rlPlotDrawer extends PlotDrawer {

	//Plot drawnPlot;
	
	boolean rainbow=true;

	
	public rlPlotDrawer(Graphics g) {
		this.g = g;
	}
	
	public void draw(GraphLine gl) {

		PointFloat[] points = gl.getPoints();
	
		if(!rainbow)
		{
			super.draw(gl);
			return;
		}

		for (int i = 1; i < points.length; i++)// TODO: make sure this doesn't
												// crash on
												// ArrayIndexOutOfBounds
		{
			//float h=((float)i/points.length)*0.9F;
			float h=((float)i/points.length)*0.9F;
			Color c=Color.getHSBColor(h, 1.0F, 1F);
			drawLineFromPointToPrevPoint(points,i,5,c);

		}
	}
	
	



	public void draw(rlPlot p)
	{
		
		
		//draw the lines
		super.draw(p);
		
		int xOff=(int)p.getOffset().x;
		int yOff=(int)p.getOffset().y;
		
		
		int x1=(int)((p.getZpCursorPos().x+xOff));
		int y1=(int)((p.getZpCursorPos().y+yOff));
		
		g.setColor(Color.RED);
		g.drawLine(x1-5, y1, x1-10, y1);
		g.drawLine(x1+5, y1, x1+10, y1);
		g.drawLine(x1, y1-5, x1, y1-10);
		g.drawLine(x1, y1+5, x1, y1+10);
		
		
		//g.fillOval((int)((p.getZpCursorPos().x-5+xOff)),(int)((p.getZpCursorPos().y-5+yOff)),10,10);
		//System.out.println((p.getZpCursorPos().x)/p.getScale().x);
		
		//System.out.println(p.getClPoles());
		//System.out.println(p.getClPoles().getPlotLines());
		
		for(GraphLine pL:p.getClPoles().getPlotLines())
		{
			float x=pL.getPoints()[0].x;
			float y=pL.getPoints()[0].y;
			
			g.fillOval(xOff-5+(int)(x*p.getScale().x), yOff-5+(int)(y*p.getScale().y), 10, 10);
			
		}
		
		
		//draw the compensator poles
	
		g.setColor(Color.BLUE);
		
		
		double time = 2 * Math.PI * (System.currentTimeMillis() % 5000)
				/ 5000.;
		double circleD=Math.abs(Math.sin(time*4));
		for(PointFloat pf:p.getCompensatorPoles())
		{
			
		g.fillOval(xOff-5+(int)(pf.x*p.getScale().x), yOff-5+(int)(pf.y*p.getScale().y), 10, 10);
			
		
//System.out.println(pf);
		}
		
		
		for(PointFloat pf:p.getCompensatorZeroes())
		{
		
			g.drawOval(xOff-5+(int)(pf.x*p.getScale().x), yOff-5+(int)(pf.y*p.getScale().y), 10, 10);
		
		}
		PointFloat currentPole=p.getSelectedPole();
		//System.out.println(currentPole);
		//g.setColor(Color.green);
		//g.fillOval(xOff-50+(int)(currentPole.x*p.getScale().x), yOff-50+(int)(currentPole.y*p.getScale().y), 100, 100);

		
		g.setColor(new Color(1.0F, (float)circleD,1.0F));
		
		
		//circleD=5;
		
		g.fillOval((int)(xOff-(5)+(currentPole.x*p.getScale().x)), (int)(yOff-(5)+(currentPole.y*p.getScale().y)), 10,10);
		//System.out.println("drawing, wee!");
	}
}
