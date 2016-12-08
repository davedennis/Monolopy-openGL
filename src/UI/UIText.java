package UI;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import Resources.FontManager;
import Resources.Helper;
import Resources.Settings;

public class UIText
{
	private float x;
	private float y;
	
	private String text;
	private TrueTypeFont font;
	private Color fontColor;
	private String identifier;
	
	public UIText(float x, float y, String text, String fontName, float fontSize, Color fontColor, String identifier)
	{
		this.x = x;
		this.y = y;

		this.text = text;
		
		this.fontColor = fontColor;
		this.identifier = identifier;
		
		font = FontManager.loadFont(fontName, fontSize);
		
		this.y = Settings.getRES_Y() - y - font.getHeight();
	}
	
	public UIText(float x, float y, String text, TrueTypeFont font, Color fontColor)
	{
		this.x = x;
		this.y = Settings.getRES_Y() - y - font.getHeight();
		this.text = text;
		this.font = font;
		this.fontColor = fontColor;
	}
 
	public void draw()
	{
		Helper.setTextProjection();
		font.drawString(x, y, text, fontColor);
		Helper.setGameProjection();
	}
	
	public float getWidth()
	{
		return font.getWidth(text);
	}
	
	public float getHeight()
	{
		return font.getLineHeight();
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public String getIdentifier() {
		return identifier;
	}
}

