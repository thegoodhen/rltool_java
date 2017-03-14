package com.thegoodhen.pipeTest;

import java.util.ArrayList;

public class rlPlot extends Plot {// TODO: make a common interface for all plots
									// yet to come, implement it and Drawable
	private int poleCount;
	private int sampleCount;
	private GraphLine[] pp;
	private float xScale = 50;// TODO: What the hay am I doing here?
	private float yScale = 100;
	private int xOffset = 350;// TODO: AND WHAT AM I DOING HERE?!
	private int yOffset = 450;
	private rlPlotDrawer gd;
	private ArrayList<PointFloat> listOfZP;
	private PointFloat zpCursorPos = new PointFloat(0, 0);

	public rlPlot() {
		super();
		listOfZP=new ArrayList<PointFloat>();
		this.getData().setRequestString("P\n");
	}

	public PointFloat getZpCursorPos() {
		return zpCursorPos;
	}

	public void setZpCursorPos(PointFloat zpCursorPos) {
		this.zpCursorPos = zpCursorPos;
	}

	public void setZpCursorPos(float x, float y) {
		this.zpCursorPos.x = x;
		this.zpCursorPos.y = y;
	}

	private int selectedPoleIndex = 0;
	private PlotData clPoles;

	public PlotData getClPoles() {
		return clPoles;
	}

	public void setClPoles(PlotData clPoles) {
		this.clPoles = clPoles;
	}

	private float gain;

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		this.gain = gain;
	}

	/*
	 * public void setPolePaths(GraphLine[] pp) { this.pp = pp; }
	 * 
	 * @Override public GraphLine[] getPlotLines() { return this.pp; }
	 */
	public ArrayList<PointFloat> getCompensatorPoles() {
		ArrayList<PointFloat> returnList=new ArrayList<>();
		for(PointFloat pf:listOfZP)
		{
			if(pf instanceof Pole)
			{
				returnList.add(pf);
			}
		}
		return returnList;
	}

	public ArrayList<PointFloat> getCompensatorZeroes() {
		ArrayList<PointFloat> returnList=new ArrayList<>();
		for(PointFloat pf:listOfZP)
		{
			if(pf instanceof Zero)
			{
				returnList.add(pf);
			}
		}
		return returnList;
	}

	public void setCompensatorPoles(ArrayList<Pole> cPoles) {
		this.listOfZP.addAll(cPoles);
	}

	public void setCompensatorZeroes(ArrayList<Zero> cZeroes) {
		this.listOfZP.addAll(cZeroes);
	}

	public PointFloat getSelectedPole() {
		return listOfZP.get(selectedPoleIndex);
	}

	public void setSelectedPole(PointFloat pf) {
		this.getData().deprecate();
		listOfZP.set(selectedPoleIndex, pf);
	}

	// TODO: Add zeroes to the cycling

	public void cyclePoles(boolean up) {
		int dir = up ? 1 : -1;
		selectedPoleIndex += dir;
		if (selectedPoleIndex >= listOfZP.size()) {
			selectedPoleIndex = 0;
		}
		if (selectedPoleIndex < 0) {
			selectedPoleIndex = listOfZP.size() - 1;
		}
	}

	public void setDrawer(rlPlotDrawer gd) {
		this.gd = gd;
	}

	public void draw() {
		gd.draw(this);
	}

	// TODO: separate the scale, etc. from the actual data

	public void setScale(float xScale, float yScale) {
		this.xScale = xScale;
		this.yScale = yScale;
	}

	@Override
	public PointFloat getScale() {
		return new PointFloat(xScale, yScale);
	}

	@Override
	public PointFloat getOffset() {
		return new PointFloat(xOffset, yOffset);
	}

	public String getPolePathsAsString() {
		StringBuilder sb = new StringBuilder();
		for (GraphLine p : getData().pp) {
			for (PointFloat pf : p.getPoints()) {
				sb.append(pf.toString()).append('\n');
			}
			sb.append("------------------------------------\n");
		}
		return sb.toString();
	}

	public void addPole() {
		Pole newPole = new Pole(getZpCursorPos().x / getScale().x,
				getZpCursorPos().y / getScale().y);
		listOfZP.add(newPole);
		
	}
	
	
	public void addZero() {
		Zero newPole = new Zero(getZpCursorPos().x / getScale().x,
				getZpCursorPos().y / getScale().y);
		listOfZP.add(newPole);
		
	}

	public void removePole() {
		listOfZP.remove(selectedPoleIndex);
		if(selectedPoleIndex>=listOfZP.size())
		{
			selectedPoleIndex=listOfZP.size()-1;
		}
	}

}
