package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;



public class GameSound 
{
	public AudioClip sound;
	public String path;
	
	public GameSound(String path)
	{
		this.path = path;
		if(!setSound())
		{
			this.path = "default_sound.wav";
			setSound();
		}
	}
	
	public boolean setSound()
	{
		try
		{
			URL url = getClass().getResource(path);
			sound = Applet.newAudioClip(url);
		    return true;
		}
		catch(Exception e)
		{
			System.out.println("Sound Not Found: '" + path + "'");
			return false;
		}
	}
	
	public void play()
	{
		sound.play();
	}
	
	public void loop()
	{
		sound.loop();
	}
	
	public void stop()
	{
		sound.stop();
	}
	
	public String getPath()
	{
		return path;
	}
}
