package com.thegoodhen.pipeTest;

import java.awt.Graphics;

public class Plot {
PlotDrawer gd;
GraphLine[] plotLines=null;
private float  xScale, yScale;
private float xOffset, yOffset;
private boolean needsUpdate;
private PlotData data;


public Plot()
{
	setData(new PlotData());
	this.xScale=1.0F;
	this.yScale=1.0F;
	this.xOffset=0;
	this.yOffset=0;
}



public void setDrawer(PlotDrawer gd)
{
	this.gd=gd;
}

	public void draw()
	{
		gd.draw(this);
	}
	
	public GraphLine[] getPlotLines()
	{
		return this.plotLines;
	}
	
	public PointFloat getScale()
	{
		return new PointFloat(xScale, yScale);
	}
	
	public void setScale(float xScale, float yScale)
	{
		this.xScale=xScale;
		this.yScale=yScale;
	}
	
	public PointFloat getOffset()
	{
		return new PointFloat(xOffset, yOffset);
	}
	
	public void setOffset(float xOffset, float yOffset)
	{
		this.xOffset=xOffset;
		this.yOffset=yOffset;
	}
	
	public boolean needsUpdate()
	{
		return this.needsUpdate;
	}
	
	
	public void invalidate()
	{
		this.needsUpdate=true;
	}

	public PlotData getData() {
		// TODO Auto-generated method stub
		return this.data;
	}
	
	public void setData(PlotData pd)
	{
		this.data=pd;
	}

}
