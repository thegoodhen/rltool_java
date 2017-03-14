package com.thegoodhen.pipeTest;

public class PlotData extends AbstractUpdatable{
GraphLine[] pp;
private String requestString="P\n";
private PlotDataUpdator updator=new PlotDataUpdator();

public PlotData()
{
	this.updator=new PlotDataUpdator();
}

	public void setPolePaths(GraphLine[] pp) {
		this.pp = pp;
	}
	
	
	public GraphLine[] getPlotLines()
	{
		return this.pp;
	}


	@Override
	protected void update() {
		updator.update(this);
	}

	public void setRequestString(String requestString)
	{
		this.requestString=requestString;
	}
	
@Override
	public String getRequestString() {
	return this.requestString;
		
	}





	
}
