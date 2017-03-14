package com.thegoodhen.pipeTest;


public class GraphLine {
private int sampleCount;
PointFloat thePoints[];

PointFloat[] getPoints()
{
	return thePoints;
}

public int getSampleCount()
{
	return thePoints.length;
}

void setPoints(PointFloat[] thePoints)
{
	this.thePoints=thePoints;
}
}
