package com.thegoodhen.pipeTest;

public class StepResponseData extends PlotData {
private float finalValue;
public float getFinalValue() {
	return finalValue;
}

public float getPeak() {
	return peak;
}

public float getPercentOS() {
	return percentOS;
}

private float peak;
private float percentOS;
	public void update()
	{
		super.update();
		updateStepInfo();
	}
	
	private void updateStepInfo()//TODO: Handle negative step response
	{
		updateFinalValue();
		updatePeak();
		updatePercentOS();
	}

	private void updateFinalValue()
	{
		PointFloat[] thePoints=this.getPlotLines()[0].getPoints();
		finalValue=thePoints[thePoints.length-1].y;
	}
	
	private void updatePeak()
	{
		PointFloat[] thePoints=this.getPlotLines()[0].getPoints();
		peak=0;
		for(PointFloat p:thePoints)
		{
			if(p.y>peak)
			{
				peak=p.y;
			}
		}
	}
	
	private void updatePercentOS()
	{
		percentOS=((peak-finalValue)/finalValue)*100;
	}
	
	//TODO: implement custom rise times, settling times, etc...
	
}
