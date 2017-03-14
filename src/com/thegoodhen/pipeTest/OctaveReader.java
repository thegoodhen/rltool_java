package com.thegoodhen.pipeTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OctaveReader {
String fileName;
public OctaveReader(String fileName)
{
	this.fileName=fileName;
}
	
public void eatReply()
{
	BufferedReader reader = null;
	try {
		System.out.println("kokoko");
		
	reader = new BufferedReader(new FileReader(fileName));
		System.out.println("Reader hotovej");
		while (!reader.ready())
			;
		
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("Kokon kokodak");
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Koroptevvlak");
		while ((reader.readLine()) != null);
		
		System.out.println("Kvak kvak");
	}
	catch(IOException e)
	{
		
	}
	finally {
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

	public PlotData readRlPlot() {
		String s;
		int a;
		int lineNum = 0;
		int samplesCount = 0;
		int polesCount = 0;
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fileName));

			while ((a = reader.read()) == -1)
				;
			/*try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			PlotData rlpd = new PlotData();
			GraphLine[] pp = null;// =new polePath[3];
			while ((s = reader.readLine()) != null) {
				switch (lineNum) {
				case 0:// pole positions (samples) count
					if(s.startsWith(" "))
					{
						s=s.replaceFirst("\\s+", "");//getting rid of the leading whitespace
					}
						
					String samplesCountStrings[] = s.split("\\s+");
					samplesCount = (int) Float.parseFloat(samplesCountStrings[0]);
					break;
				case 1:// individual poles count (smaller number)
					
					if(s.startsWith(" "))
					{
						s=s.replaceFirst("\\s+", "");//getting rid of the leading whitespace
					}
						
					String polesCountStrings[] = s.split("\\s+");
					polesCount = (int) Float.parseFloat(polesCountStrings[0]);
					pp = new GraphLine[polesCount];
					for (int i = 0; i < polesCount; i++) {
						pp[i] = new GraphLine();
						PointFloat thePoints[] = new PointFloat[samplesCount];
						for (int j = 0; j < samplesCount; j++) {
							thePoints[j] = new PointFloat(0,0);
						}
						pp[i].setPoints(thePoints);
					}
					break;
				default:
					if(s.startsWith(" "))
					{
						s=s.replaceFirst("\\s+", "");
					}
					String poleStrings[] = s.split("\\s+");//getting rid of the leading whitespace
			//		float poleFloats[] = new float[poleStrings.length];
					float currentPoleLoc;
					if (poleStrings.length != polesCount) {
						// TODO: handle it here
					}
					for (int i = 0; i < poleStrings.length; i++) {
						currentPoleLoc = Float.parseFloat(poleStrings[i]);

						if (lineNum < 2 + samplesCount) {
							//System.out.println(i);
							//System.out.println(lineNum);
							pp[i].getPoints()[lineNum - 2].x = currentPoleLoc;
						} else {
							pp[i].getPoints()[lineNum - samplesCount - 2].y = currentPoleLoc;
						}
					}

					break;
				}
				// char c = (char)a;
				// prints character
				//System.out.println(s);
				lineNum++;
			}
			rlpd = new PlotData();
			
			int ptsCount=pp[0].getSampleCount();
			
			for(int i=1;i<ptsCount;i++)
			{
				for (GraphLine gl:pp)
				{
					GraphLine plToSwapWith=null;
					float distToPrev=Float.MAX_VALUE;//gl.getPoints()[i].distanceTo(gl.getPoints()[i-1]);//distance to the previous point in the graph line
					
					for(GraphLine gl2:pp)
					{
						float distCurr=gl2.getPoints()[i].distanceTo(gl.getPoints()[i-1]);
						if(distCurr<distToPrev)//if distance of one of the poles to the previous of one of the other pole paths (GraphLines) is smaller than to the previous one of the current graphLine 
						{
							//we swap it around! 
							plToSwapWith=gl2;//.getPoints()[i];
							/*PointFloat tmp=gl2.getPoints()[i];
							gl2.getPoints()[i]=gl.getPoints()[i];
							gl.getPoints()[i]=tmp;*/
							distToPrev=distCurr;
						}
					}
					
					if(plToSwapWith!=null)
					{
						PointFloat tmp=plToSwapWith.getPoints()[i];
						plToSwapWith.getPoints()[i]=gl.getPoints()[i];
						gl.getPoints()[i]=tmp;
					}
					
					//PointFloat[] pts=gl.getPoints();
					
					
				}
				
				
		//		pts=gl.getPoints()[i];
			}
			
			
			
			
			rlpd.setPolePaths(pp);
			
			//System.out.println(rlp.getPolePathsAsString());
			return rlpd;

		} catch (IOException e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
