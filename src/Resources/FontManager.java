package Resources;

import java.io.InputStream;
import java.util.ArrayList;
import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class FontManager
{
	private static class IndexedAWTFont
	{
		private String name;
		private Font awtFont;
		
		private IndexedAWTFont(String name, Font awtFont)
		{
			this.name = name;
			this.awtFont = awtFont;
		}
		
		@Override
		public boolean equals(Object other) // Overrides default .equals() method to be used in indexing fonts by name in the ArrayList
		{
			if (!(other instanceof IndexedAWTFont))				
				return false;							
			if (((IndexedAWTFont) other).name.equals(name))				
				return true;				
			return false;
		}
	}
	
	private static class IndexedTrueTypeFont
	{
		private String name;
		private float size;
		private TrueTypeFont trueTypeFont;
		
		private IndexedTrueTypeFont(String name, float size, TrueTypeFont trueTypeFont)
		{
			this.name = name;
			this.size = size;
			this.trueTypeFont = trueTypeFont;
		}
		
		@Override
		public boolean equals(Object other) // Overrides default .equals() method to be used in indexing fonts by name in the ArrayList
		{
			if (!(other instanceof IndexedTrueTypeFont))				
				return false;
			IndexedTrueTypeFont otherFont = (IndexedTrueTypeFont) other;
			if (otherFont.name.equals(name) && otherFont.size == size)				
				return true;				
			return false;
		}
	}
	
	private static ArrayList<IndexedAWTFont> awtFonts = new ArrayList<IndexedAWTFont>();
	private static ArrayList<IndexedTrueTypeFont> trueTypeFonts = new ArrayList<IndexedTrueTypeFont>();
	
	/**
	 * 
	 * @param fontName
	 * @param fontSize
	 * @return
	 * 
	 * Load a TrueTypeFont from memory or load it into memory if it is not there. This should be the only way a font is ever loaded.
	 */
	public static TrueTypeFont loadFont(String fontName, float fontSize)
	{
		IndexedTrueTypeFont comparator = new IndexedTrueTypeFont(fontName, fontSize, null);
		if (trueTypeFonts.contains(comparator))
		{			
			return trueTypeFonts.get(trueTypeFonts.indexOf(comparator)).trueTypeFont;
		}
		else
		{
			Font awtFont;

			if (!awtFonts.contains(new IndexedAWTFont(fontName, null)))
			{
				awtFonts.add(new IndexedAWTFont(fontName, loadAWTFont(fontName)));
			}
			
			awtFont = awtFonts.get(awtFonts.indexOf(new IndexedAWTFont(fontName, null))).awtFont;
			
			awtFont = awtFont.deriveFont(fontSize);
			TrueTypeFont trueTypeFont = new TrueTypeFont(awtFont, true);
			trueTypeFonts.add(new IndexedTrueTypeFont(fontName, fontSize, trueTypeFont));
			return trueTypeFont;			
		}
	}
	
	public static Font loadAWTFont(String fileName)
	{
		try
		{
			InputStream inputStream	= ResourceLoader.getResourceAsStream(fileName);
			Font awtfont = Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
						
			System.out.println("UI: AWTFont " + fileName + " loaded.");
			return awtfont;
		}
		catch (Exception e)
		{
			System.out.println("UI ERROR: AWTFont " + fileName + " could not be loaded.");
			System.exit(-1);
			return null;
		}
	}
	
	public static void clear()
	{
		awtFonts.clear();
		trueTypeFonts.clear();
	}
}
