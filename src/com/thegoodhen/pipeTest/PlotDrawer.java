package com.thegoodhen.pipeTest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PlotDrawer implements IDrawer {// TODO: assure consistency
												// (graph/plot)
	Plot drawnPlot;
	Graphics g;
	int maxStepPixels = 30;// maximum step between 2 marks on an axis
	int tickSize=10;//size of each mark on the axis

	public PlotDrawer() {

	}

	public PlotDrawer(Graphics g) {
		this.g = g;
	}

	public void setGraphics(Graphics g) {
		this.g = g;
	}

	public void setDrawnPlot(Plot p) {
		this.drawnPlot = p;
	}
	


	@Override
	public void draw() {
		Plot p = drawnPlot;
		for (GraphLine gl : p.getData().getPlotLines()) {
			draw(gl);
		}
		drawAxis(true);
		drawAxis(false);
	}

	public void draw(Plot p) {
		this.drawnPlot = p;// will be used for determining the color, etc.
		draw();
	}

	public void drawAxis(boolean x) {
		
		// TODO: Only repeat the calculations once the actual plot gets updated

		int stepPixels;
		float scale;
		if(x)
		{
			scale=this.drawnPlot.getScale().x;
			g.setColor(Color.green);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g.drawLine((int)drawnPlot.getOffset().x-maxStepPixels*10, (int)drawnPlot.getOffset().y,(int)drawnPlot.getOffset().x+maxStepPixels*10,(int) drawnPlot.getOffset().y);
		}
		else
		{
			g.setColor(Color.green);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g.drawLine((int)drawnPlot.getOffset().x, (int)drawnPlot.getOffset().y-maxStepPixels*10,(int)drawnPlot.getOffset().x,(int) drawnPlot.getOffset().y+maxStepPixels*10);

			scale=this.drawnPlot.getScale().y;
		}
		
		float actualStepSize = maxStepPixels /scale;//calc how much is the maximum step size in pixels in actual units
		int scaleOrder=(int)Math.floor(Math.log10(actualStepSize));//see the order of magnitude the actual step size is in; no loss from casting to int with floor
		float divisor=(float) Math.pow(10, scaleOrder);//convert the order of magnitude to the lowest number of that order (2-->100, 1-->10...)
		double correctedStepSize=actualStepSize-(actualStepSize%divisor);//round the actual step size down based on its order of magnitude (0.253-->0.2, 101-->100, 1523-->1000)
		stepPixels=(int)(correctedStepSize*scale);
		//System.out.println("stepPixels: "+stepPixels);
		
		
		for(int i=-10;i<10;i++)
		{
			int xCommon=(int) (stepPixels *i  + drawnPlot
					.getOffset().x);
			int x1 = xCommon;
			int y1 = (int) (drawnPlot
					.getOffset().y);
			int x2 = xCommon;
			
			int y2 = (int) ( drawnPlot
					.getOffset().y+tickSize);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(1));
			
			String markTxt=Double.toString(correctedStepSize*i);
			markTxt=markTxt.substring(0, Math.min(markTxt.length(), 5));
			markTxt=markTxt.replaceAll("(.*?\\..*?)0+$", "$1");
			char[] markTxtCa=markTxt.toCharArray();
			//System.out.println("test: "+"30.0200".replaceAll("(.*?\\..*?)0+$", "$1"));
			
			if(x)
			{
					g.drawLine(x1, y1, x2, y2);
					g.drawChars(markTxtCa, 0, markTxtCa.length, xCommon, (int) (drawnPlot
					.getOffset().y+tickSize*2));
			}
			else
			{
				g.drawLine((int)drawnPlot
						.getOffset().x, stepPixels*-i+(int)(int)drawnPlot
						.getOffset().y, (int)drawnPlot
						.getOffset().x+tickSize, (int)drawnPlot
						.getOffset().y+stepPixels*-i);
				g.drawChars(markTxtCa, 0, markTxtCa.length, 
						(int)drawnPlot
						.getOffset().x+tickSize*2, stepPixels*-i+(int)(int)drawnPlot
						.getOffset().y
						
						);
			}
			
			//System.out.println(markTxt);
			
		}
		
		
		
		
		
		
		
		
		
		
		
	}

	public void draw(GraphLine gl) {

		PointFloat[] points = gl.getPoints();

		for (int i = 1; i < points.length; i++)// TODO: make sure this doesn't
												// crash on
												// ArrayIndexOutOfBounds
		{
			//float h=((float)i/points.length)*0.9F;
			drawLineFromPointToPrevPoint(points,i,5,Color.yellow);

		}
	}

	protected void drawLineFromPointToPrevPoint(PointFloat[] points, int index, int thickness, Color c)
	{
		//PointFloat[] points = gl.getPoints();
		//float h=((float)i/points.length)*0.9F;
		//	g.setColor(Color.getHSBColor(h, 1.0F, 1F));
			int x1 = (int) (points[index].x * drawnPlot.getScale().x + drawnPlot
					.getOffset().x);
			int y1 = (int) (-points[index].y * drawnPlot.getScale().y + drawnPlot
					.getOffset().y);
			int x2 = (int) (points[index - 1].x * drawnPlot.getScale().x + drawnPlot
					.getOffset().x);
			int y2 = (int) (-points[index - 1].y * drawnPlot.getScale().y + drawnPlot
					.getOffset().y);
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(c);
			g2.setStroke(new BasicStroke(thickness));
			g.drawLine(x1, y1, x2, y2);
	}
	
}
