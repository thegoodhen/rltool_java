package com.thegoodhen.pipeTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Mane {

	public static void main(String[] args) {
		System.out.println("Hello World!"); // Display the string.

		BufferedReader br;
		BufferedWriter writer;
		try {
			//
			br = new BufferedReader(new FileReader("OOJI"));
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("prdel_hovna.txt"), "utf-8"));
			writer.write("Something");
			writer.flush();
			writer.close();
			/*try {
				String x;

				while (true) {
				
					while ((x = br.readLine()) != null) {
						// printing out each line in the file
						System.out.println(x);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}*/
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}
}
