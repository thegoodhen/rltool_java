package com.thegoodhen.pipeTest;
 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Random;
 
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class GfxTest extends JFrame {
	Random rand = new Random();

	
 
    public GfxTest() {
        super("Lines Drawing Demo");
 
        setSize(480, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
 
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int  n = rand.nextInt(500) + 1;
        g2d.drawLine(n, n, 360, 50);
 
        g2d.draw(new Line2D.Double(59.2d, 99.8d, 419.1d, 99.8d));
 
        g2d.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
 
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
        repaint();
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GfxTest().setVisible(true);
            }
        });
    }
}