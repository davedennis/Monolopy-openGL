package Graphics;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureManager
{
	private static class IndexedTexture
	{
		private Texture texture;
		private String name;
		
		private IndexedTexture(String name, Texture texture)
		{
			this.name = name;
			this.texture = texture;
		}
	    
		public boolean equals(Object other) // Overrides default equals to be used in indexing textures by name in Texture Handler
		{
			if (! (other instanceof IndexedTexture))	
				return false;
			if (((IndexedTexture) other).name.equals(name))				
				return true;
			return false;
		}
	}
	
	private static ArrayList<IndexedTexture> allTextures = new ArrayList<IndexedTexture>();
	
	public static Texture loadTexture(String textureName)
	{
		IndexedTexture comparator = new IndexedTexture(textureName, null);
		if (allTextures.contains(comparator))
		{
			return allTextures.get(allTextures.indexOf(comparator)).texture;
		}
		else
		{
			try
			{
				allTextures.add(new IndexedTexture(textureName, loadTextureData(textureName)));
				return allTextures.get(allTextures.indexOf(comparator)).texture;
			}
			catch (Exception e)
			{
				System.err.println("Internal texture loading error");
				return null;
			}
		}
	}
	
	public static int loadTextureIndex(String textureName)
	{
		IndexedTexture comparator = new IndexedTexture(textureName, null);
		if (allTextures.contains(comparator))
		{
			return allTextures.indexOf(comparator);
		}
		else
		{
			try
			{
				allTextures.add(new IndexedTexture(textureName, loadTextureData(textureName)));
				return allTextures.indexOf(comparator);
			}
			catch (Exception e)
			{
				System.err.println("Internal texture loading error");
				System.exit(-1);
				return -1;
			}
		}
	}
	
	public static Texture loadTextureData(String fileName) throws IOException
	{
		try
		{
			Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(fileName));
			System.out.println("GRAPHICS: Texture " + fileName + " loaded.");
			return texture;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("GRAPHICS: Texture " + fileName + " could not be loaded. Replacing with missingTexture.png");
			return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/missingTexture.png"));
		}
	}
	
	public static int getNumTextures()
	{
		return allTextures.size();
	}
	
	public static Texture getTexture(int index)
	{
		return allTextures.get(index).texture;
	}
	
	public static void clear()
	{
		allTextures.clear();
	}
	
	public static String getTextureName(int index)
	{
		return allTextures.get(index).name;
	}
}
