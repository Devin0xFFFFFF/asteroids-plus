package game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class GameImage 
{
	public BufferedImage image, currentImage, editImage;
	public String path;
	public float alpha;
	
	public GameImage(String path)
	{
		this.path = path;
		if(!setImage())
		{
			this.path = "default_image.png";
			setImage();
		}
		currentImage = image;
		alpha = 1.0f;
	}
	
	public GameImage(BufferedImage img)
	{
		path = "Locally Created Image";
		image = img;
		currentImage = image;
		alpha = 1.0f;
	}
	
	public boolean setImage()
	{
		try
		{
			image = ImageIO.read(getClass().getResource(path));
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Image not Found: '" + path + "'");
			return false;
		}
	}
	
	public String getPath()
	{
		return path;
	}
	
	public BufferedImage getImage()
	{
		return currentImage;
	}
	
	public void scaleSource(int x, int y)
	{
		resize(x,y);
		image = currentImage;
	}
	
	public void rotateSource(int angle)
	{
		rotate(angle);
		image = currentImage;
	}
	
	public void resize(int x, int y)
	{
		editImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);  
	    Graphics2D g = editImage.createGraphics();  
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	    g.drawImage(currentImage, 0, 0, x, y, 0, 0, currentImage.getWidth(), currentImage.getHeight(), null);  
	    g.dispose();
	    currentImage = editImage;
	}
	
	public void flipHorizontal()
	{
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-currentImage.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		currentImage = op.filter(currentImage, null);
	}
	
	public void flipVertical()
	{
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -currentImage.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		currentImage = op.filter(currentImage, null);
	}
	
	public void setTransparency(float alpha, int angle)
	{
		this.alpha = alpha;
		rotate(angle);
	}
	
	public void rotate(int angle)
	{
		//*
		
		double radians = Math.toRadians(angle);
		 
	    int srcWidth = image.getWidth();
	    int srcHeight = image.getHeight();
	 
	    double sin = Math.abs(Math.sin(radians));
	    double cos = Math.abs(Math.cos(radians));
	    int newWidth = (int) Math.floor(srcWidth * cos + srcHeight * sin);
	    int newHeight = (int) Math.floor(srcHeight * cos + srcWidth * sin);
	 
	    editImage = new BufferedImage(newWidth, newHeight,image.getType());
	    Graphics2D g = editImage.createGraphics();
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	    g.translate((newWidth - srcWidth) / 2, (newHeight - srcHeight) / 2);
	    g.rotate(radians, srcWidth / 2, srcHeight / 2);
	    g.drawRenderedImage(image, null);
	    
	    currentImage = editImage;
	    
	    /*/
		
		/*
		AffineTransform xform = new AffineTransform();

		if (image.getWidth() > image.getHeight())
		{
		xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getWidth());
		xform.rotate(Math.toRadians(angle));

		int diff = image.getWidth() - image.getHeight();

		switch (angle)
		{
		case 90:
		xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
		break;
		case 180:
		xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
		break;
		default:
		xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth());
		break;
		}
		}
		else if (image.getHeight() > image.getWidth())
		{
		xform.setToTranslation(0.5 * image.getHeight(), 0.5 * image.getHeight());
		xform.rotate(Math.toRadians(angle));

		int diff = image.getHeight() - image.getWidth();

		switch (angle)
		{
		case 180:
		xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
		break;
		case 270:
		xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
		break;
		default:
		xform.translate(-0.5 * image.getHeight(), -0.5 * image.getHeight());
		break;
		}
		}
		else
		{
		xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getHeight());
		xform.rotate(Math.toRadians(angle));
		xform.translate(-0.5 * image.getHeight(), -0.5 * image.getWidth());
		}

		AffineTransformOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

		currentImage =  op.filter(image, null);
		
		*/
	}
}
