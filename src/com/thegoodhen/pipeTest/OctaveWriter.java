package com.thegoodhen.pipeTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OctaveWriter {
	
private String fileName;
public OctaveWriter(String fileName) {
this.fileName=fileName;
}
public void setFileName(String fileName)
{
	this.fileName=fileName;
}
private BufferedWriter writer;


public void sendStringRequest(String req) throws IOException
{
	writer = new BufferedWriter(new FileWriter(fileName));
	writer.write(req);
	//writer.flush();
	writer.close();
}

	public void requestRLPlot(int precision) throws IOException
	{
	

		writer = new BufferedWriter(new FileWriter(fileName));
		writer.write("P\n");
		//writer.flush();
		writer.close();
		
		
	}
	public void requestPoleChange(int[] poles) throws IOException
	{
		writer = new BufferedWriter(new FileWriter(fileName));
		writer.write("PO\n");
		//writer.flush();
		writer.close();
	}
	
	
	
	
}
