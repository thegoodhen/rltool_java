package com.thegoodhen.pipeTest;

import java.io.IOException;

public class PlotDataUpdator implements IUpdator<PlotData> {

	@Override
	public void update(PlotData u) {//TODO: ADD UPDATEFAILUREEXCEPTION
		String rS=u.getRequestString();
		OctaveRW orw=new OctaveRW("OOJI","JOOI");//TODO: WOAH!! REFACTOR
		try {
			PlotData newData=orw.requestRLPlot(rS, 0, 20, 20);
			u.setPolePaths(newData.getPlotLines());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
