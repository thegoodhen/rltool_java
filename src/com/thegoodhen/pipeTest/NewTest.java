package com.thegoodhen.pipeTest;
/**
 * This is the actual main class. Run this as a starting point. BUT FIRST!
 * 
 * Navigate to /home/thegoodhen/Documents/Octave/my_rltool/
 * Run Octave
 * inside Octave, run "source octave-commsTest.m"
 * inside Octave, run "commsTest"
 * inside Eclipse, run this file.
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class NewTest extends JPanel implements MouseListener, ActionListener,
		ComponentListener, Runnable {

	JFrame f;
	Insets insets;
	private Timer t = new Timer(10, this);
	BufferedImage buffer1;
	boolean repaintBuffer1 = true;
	int initWidth = 640;
	int initHeight = 480;
	Rectangle rect;
	boolean activeRedraw = true;

	rlPlotDrawer gd;
	PlotDrawer gd2;
	private boolean updateFlag = false;
	private boolean changeLocked;
	private static Pole[] currentPolesTemp={new Pole(0.5F,0), new Pole(0.8F,0)};
	private static ArrayList<Pole> currentPoles=new ArrayList<Pole>(Arrays.asList(currentPolesTemp));
	
	private static Zero[] currentZeroesTemp={new Zero(2F,0), new Zero(1,0)};
	private static ArrayList<Zero> currentZeroes=new ArrayList<Zero>(Arrays.asList(currentZeroesTemp));
	
	static OctaveRW o_rw = new OctaveRW("OOJI", "JOOI");
	//static OctaveRW o_rw2 = new OctaveRW("OOJI", "JOOI");
	//static OctaveReader o_r = new OctaveReader("OOJI");
	//static OctaveWriter ow = new OctaveWriter("JOOI");
	static rlPlot thePlot;
	static StepResponsePlot srPlot;
ArrayList<StateRestrictedAction> sraList=new ArrayList<>();
private String currentState;
	public static void main(String[] args) {

		try {
			thePlot = new rlPlot();
			srPlot=new StepResponsePlot();
			thePlot.setCompensatorPoles(currentPoles);
			thePlot.setCompensatorZeroes(currentZeroes);
			//thePlot.getData().update();
			thePlot.setData(o_rw.requestRLPlot("P\n",0,10,20));
			srPlot.getData().update();
			//srPlot.setData(o_rw.requestRLPlot("S\n",0,10,20));
			thePlot.setClPoles(o_rw.requestGainChange(5));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EventQueue.invokeLater(new NewTest());
	}

	@Override
	public void run() {
		f = new JFrame("NewTest");
		f.addComponentListener(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		createBuffers();
		insets = f.getInsets();

		t.start();
	}

	public NewTest() {
		super(true);
		this.setPreferredSize(new Dimension(initWidth, initHeight));
		this.setLayout(null);
		this.addMouseListener(this);
		addPoleSwitchAction("J",false);
		addPoleSwitchAction("K", true);
		addMoveAction("H", 0.10F);
		addMoveAction("L", -0.10F);
		addChangeGainAction("F",0.10F);
		addChangeGainAction("B",-0.10F);
		addStateChangeAction("S","S");
		addStateChangeAction("A","PA");
		addStateResetAction("Q");
		addChangeScaleAction("H",1.0F,0);
		addChangeScaleAction("L",-1.0F,0);
		addChangeScaleAction("J",0,1.0F);
		addChangeScaleAction("K",0,-1.0F);
		addZPCursorAction("H",-1.0F,0);
		addZPCursorAction("L",1.0F,0);
		addZPCursorAction("J",0,1.0F);
		addZPCursorAction("K",0,-1.0F);
		addAddPoleAction("P");
		addAddZeroAction("Z");
		addRemovePoleAction("X");//TODO: Add confirmation
		changeState("N");
	}

	void createBuffers() {
		int width = this.getWidth();
		int height = this.getHeight();

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gs.getDefaultConfiguration();
		buffer1 = new BufferedImage(width, height, IndexColorModel.TRANSLUCENT);
		// buffer1 = gc.createCompatibleImage(width, height,
		// Transparency.OPAQUE);

		repaintBuffer1 = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();

		if (true || activeRedraw) {
			if (repaintBuffer1) {
				Graphics g1 = buffer1.getGraphics();
				g1.clearRect(0, 0, width, height);
				g1.setColor(Color.green);
				g1.drawRect(0, 0, width - 1, height - 1);
				g.drawImage(buffer1, 0, 0, null);
				//repaintBuffer1 = false;
			}

			double time = 2 * Math.PI * (System.currentTimeMillis() % 5000)
					/ 5000.;
			g.setColor(Color.RED);
			if (rect != null) {
				g.drawImage(buffer1, rect.x, rect.y, rect.x + rect.width,
						rect.y + rect.height, rect.x, rect.y, rect.x
								+ rect.width, rect.y + rect.height, this);
			}
			rect = new Rectangle(
					(int) (Math.sin(time) * width / 3 + width / 2 - 20),
					(int) (Math.cos(time) * height / 3 + height / 2) - 20, 40,
					40);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			gd = new rlPlotDrawer(g);
			gd2=new PlotDrawer(g);

			if (updateFlag && !changeLocked) {//TODO: refactor the whole stuff using Updatable interface and update() call
				changeLocked=true;
				try {
					//thePlot.setCompensatorPoles(currentPoles);
					o_rw.requestZPChange(false, thePlot.getCompensatorPoles());//TODO: massive refactor (probably using the Updatable interface)
					o_rw.requestZPChange(true, thePlot.getCompensatorZeroes());//TODO: massive refactor
					thePlot.getData().requestUpdate();
					srPlot.getData().requestUpdate();
					//thePlot.setData(o_rw.requestRLPlot("P\n",0,20,20));
					//thePlot.setData(o_rw.requestRLPlot(0,10, 20));
					//o_rw.requestPolesLoc(5);
					thePlot.setClPoles(o_rw.requestGainChange(thePlot.getGain()));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateFlag = false;
				changeLocked=false;
			}
			thePlot.setDrawer(gd);
			thePlot.draw();
			srPlot.setDrawer(gd2);
			StepResponseData srd=(StepResponseData)(srPlot.getData());
			System.out.println(srd.getPercentOS());
			srPlot.draw();
			//System.out.println(thePlot);
			activeRedraw = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		activeRedraw = true;
		this.repaint();
		Toolkit.getDefaultToolkit().sync();
		// System.out.println("rep");
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		int width = e.getComponent().getWidth() - (insets.left + insets.right);
		int height = e.getComponent().getHeight()
				- (insets.top + insets.bottom);
		this.setSize(width, height);
		createBuffers();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/*
		 * JTextField field = new JTextField("test"); field.setBounds(new
		 * Rectangle(e.getX(), e.getY(), 100, 20)); this.add(field);
		 */
		repaintBuffer1 = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void moveZP(float delta) {
		//System.out.println(delta);// TODO
		//currentPoles[0]+=delta;//MAJOR refactoring needs to go here
		PointFloat tempPf=thePlot.getSelectedPole();
		tempPf.x+=delta;
		thePlot.setSelectedPole(tempPf);//TODO: fix so that this doesn't need to be called explicitly
		srPlot.getData().deprecate();//TODO: all those calls to deprecate() should be together! (or not?)
		updateFlag = true;
	}

	
public void registerStateRestrictedAction(StateRestrictedAction sra)
{
	sraList.add(sra);
}

public void changeState(String newValue)
{
	currentState=newValue;
	for(StateRestrictedAction sra:sraList)//first remove the obsolete bindings
	{
		if(!sra.getApplicableState().equals(currentState))
		{
			String theKey=(String) sra.getValue(Action.NAME);
			this.getActionMap().put(theKey, null);
		}
	}
	
	for(StateRestrictedAction sra:sraList)//then add the new ones
	{

			if(sra.getApplicableState().equals(currentState))
			{
				//String theKey=(String) sra.getValue(Action.NAME);
				this.getActionMap().put(sra.getValue(Action.NAME), sra);
			}
		
	}
	
	
		
			
		
	}



public StateChangeAction addStateChangeAction(String name, String state) {
	StateChangeAction action = new StateChangeAction(name, state);
	KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
	InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	inputMap.put(pressedKeyStroke, name);
	this.getActionMap().put(name, action);

	return action;
}

//TODO: refactor the common code
	public StateResetAction addStateResetAction(String name)//TODO: REFACTOR !!!!
	{
		StateResetAction action=new StateResetAction(name);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
		
	}

	
	public ChangeScaleAction addChangeScaleAction(String name, float deltaX, float deltaY) {
		ChangeScaleAction action = new ChangeScaleAction(name, deltaX, deltaY);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}
	
	
	public moveZPCursorAction addZPCursorAction(String name, float deltaX, float deltaY) {
		moveZPCursorAction action = new moveZPCursorAction(name, deltaX, deltaY);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}
	
	
	public ZPMoveAction addMoveAction(String name, float delta) {
		ZPMoveAction action = new ZPMoveAction(name, delta);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}

	public poleSwitchAction addPoleSwitchAction(String name, boolean up) {
		poleSwitchAction action = new poleSwitchAction(name, up);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}
	
	
	
	public ChangeGainAction addChangeGainAction(String name, float delta) {
		ChangeGainAction action = new ChangeGainAction(name, delta);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}
	
	
	
	public AddPoleAction addAddPoleAction(String name) {
		AddPoleAction action = new AddPoleAction(name);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}
	
	
	public AddZeroAction addAddZeroAction(String name) {
		AddZeroAction action = new AddZeroAction(name);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}
	

	public RemovePoleAction addRemovePoleAction(String name) {
		RemovePoleAction action = new RemovePoleAction(name);
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
		InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, name);
		this.getActionMap().put(name, action);

		return action;
	}
	
	
	
	
	
	private abstract class StateRestrictedAction extends AbstractAction implements ActionListener
	{
		String applicableState="";//TODO: enum the states
		
		public StateRestrictedAction(String name)
		{
			this(name,"N");
		}
		
		public StateRestrictedAction(String name,String as) {
			super(name);
			registerStateRestrictedAction(this);
			this.applicableState=as;
		}
		public void setApplicableState(String s)
		{
			this.applicableState=s;
		}
		public String getApplicableState()
		{
			return this.applicableState;
		}
	}
	
	
	private class StateChangeAction extends StateRestrictedAction
	{
		private String newState;
		public StateChangeAction(String name, String newState)
		{
			super(name);
			this.newState=newState;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			changeState(newState);
			
		}
		
	}
	
	
	private class StateResetAction extends AbstractAction implements ActionListener
	{

		public StateResetAction(String name) {
			// TODO Auto-generated constructor stub
			super(name);//not sure why this is needed
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("KOkokoko");
			changeState("N");
			
		}
		
	}
	
	
	//TODO: the actions should be assigned to the plot itself
	
	
	private class poleSwitchAction extends StateRestrictedAction
	{
		boolean up;
		public poleSwitchAction(String name, boolean up)
		{
			super(name,"N");
			this.up=up;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
		 thePlot.cyclePoles(up);
			
		}
		
	}
	
	private class ZPMoveAction extends StateRestrictedAction {
		private float delta;

		public ZPMoveAction(String name, float delta2) {
			super(name);

			this.delta = delta2;
		}

		public void actionPerformed(ActionEvent e) {
			moveZP(delta);
		}
	}

	
	private class ChangeGainAction extends StateRestrictedAction {
		private float delta;

		public ChangeGainAction(String name, float delta) {
			super(name);
			this.delta=delta;
			
		}

		public void actionPerformed(ActionEvent e) {
			thePlot.setGain(thePlot.getGain()+delta);
			srPlot.getData().deprecate();
			updateFlag = true;//this needs to be more elaborate
		}
	}
	
	
	
	private class ChangeScaleAction extends StateRestrictedAction {
		private float deltaX, deltaY;

		public ChangeScaleAction(String name, float deltaX,float deltaY) {
			super(name,"S");
			this.deltaX=deltaX;
			this.deltaY=deltaY;
			
		}

		public void actionPerformed(ActionEvent e) {
			thePlot.setScale(thePlot.getScale().x+deltaX,thePlot.getScale().y+deltaY);
			updateFlag = true;//this needs to be more elaborate
		}
	}
	
	
	private class moveZPCursorAction extends StateRestrictedAction {
		private float deltaX, deltaY;

		public moveZPCursorAction(String name, float deltaX,float deltaY) {
			super(name,"PA");
			this.deltaX=deltaX;
			this.deltaY=deltaY;
			
		}

		public void actionPerformed(ActionEvent e) {
			thePlot.setZpCursorPos(thePlot.getZpCursorPos().x+deltaX,thePlot.getZpCursorPos().y+deltaY);
			updateFlag = true;//this needs to be more elaborate
		}
	}
	
	
	private class AddPoleAction extends StateRestrictedAction {

		public AddPoleAction(String name) {
			super(name,"PA");
			
		}

		public void actionPerformed(ActionEvent e) {
			thePlot.addPole();
			updateFlag = true;//this needs to be more elaborate
		}
	}
	
	
	private class AddZeroAction extends StateRestrictedAction {

		public AddZeroAction(String name) {
			super(name,"PA");
			
		}

		public void actionPerformed(ActionEvent e) {
			thePlot.addZero();
			updateFlag = true;//this needs to be more elaborate
		}
	}
	
	
	
	
	private class RemovePoleAction extends StateRestrictedAction {

		public RemovePoleAction(String name) {
			super(name,"N");
			
		}

		public void actionPerformed(ActionEvent e) {
			thePlot.removePole();
			updateFlag = true;//this needs to be more elaborate
		}
	}
	
	
	
}