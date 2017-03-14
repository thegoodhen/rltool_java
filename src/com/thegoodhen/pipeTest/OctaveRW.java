package com.thegoodhen.pipeTest;

import java.io.IOException;
import java.util.ArrayList;

public class OctaveRW {
OctaveReader o_r;
OctaveWriter o_w;
	/**
	 * @param args
	 */
public OctaveRW(String readerFile, String writerFile)
{
	o_r=new OctaveReader(readerFile);
	o_w=new OctaveWriter(writerFile);
}

public PlotData requestGainChange(float gain) throws IOException
{
	//System.out.println("gain "+gain);
	o_w.sendStringRequest("G\n");
	o_r.eatReply();
	String params=Float.toString(gain);
	o_w.sendStringRequest(params);
	//o_w.requestRLPlot(precision);
	return o_r.readRlPlot();
	//return requestRLPlot(gain, gain+1, 2);//TODO: figure out why this doesn't work with (gain, gain, 1) and fix
}

public PlotData requestRLPlot(String rS,float startGain, float endGain, int stepCount) throws IOException
{
	o_w.sendStringRequest(rS);//TODO: just a test
	o_r.eatReply();
	String params=Float.toString(startGain)+" "+Float.toString(endGain)+" "+Integer.toString(stepCount);
	o_w.sendStringRequest(params);
	//o_w.requestRLPlot(precision);
	return o_r.readRlPlot();
}




public void requestZPChange(boolean zero,ArrayList<PointFloat> newPoles) throws IOException
{
	if(zero)
	{
		o_w.sendStringRequest("ZE\n");
	}
	else
	{
	o_w.sendStringRequest("PO\n");
	}
	o_r.eatReply();
	StringBuilder sb=new StringBuilder();
	for(PointFloat pole:newPoles)
	{
		sb.append(pole.toString());
		sb.append(" ");
	}
	sb.append('\n');
	System.out.print(sb.toString());
	o_w.sendStringRequest(sb.toString());
	o_r.eatReply();
}




}
