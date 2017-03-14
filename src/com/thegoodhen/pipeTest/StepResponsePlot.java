package com.thegoodhen.pipeTest;

public class StepResponsePlot extends Plot {
	private srPlotDrawer gd;
	public StepResponsePlot()
	{
		setData(new StepResponseData());
		this.getData().setRequestString("S\n");
		setScale(40F,400F);
		this.setOffset(1050, 450);
	}
	

	

}
