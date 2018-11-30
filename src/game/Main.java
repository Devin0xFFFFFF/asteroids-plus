package game;

import java.applet.Applet;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Main 
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
	    frame.setSize(1200, 720);

	    final Applet applet = new Window();

	    frame.getContentPane().add(applet);
	    frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	            applet.stop();
	            applet.destroy();
	            System.exit(0);
	        }
	    });

	    frame.setVisible(true);
	    applet.init();
	    applet.start();
	}
}
