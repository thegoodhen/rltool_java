package com.thegoodhen.pipeTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Mane2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		OctaveReader or = new OctaveReader("OOJI");
		OctaveWriter ow = new OctaveWriter("JOOI");
		try {

			ow.setFileName("JOOI");
			while (true) {
				ow.requestRLPlot(20);
				or.readRlPlot();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

}
