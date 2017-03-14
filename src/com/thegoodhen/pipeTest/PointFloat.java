package com.thegoodhen.pipeTest;

public class PointFloat {
public float x;
public float y;


public PointFloat(float x, float y) {
	this.x=x;
	this.y=y;
}


public float distanceTo(PointFloat p2)
{
	return (float) Math.sqrt((x-p2.x)*(x-p2.x)+(y-p2.y)*(y-p2.y));//the 100 is just some test
}



@Override
public String toString()
{
	return x+"+"+y+"i";
}

}
