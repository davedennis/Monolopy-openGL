package UI;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import Resources.FontManager;
import Resources.Helper;
import Resources.Settings;

public class UITextButton 
{
	private float x, font_x;
	private float y, font_y;
	private float width;
	private float height;
	
	private String[] text;
	private int textIndex = 0;
	private String function;
	
	private int fontIndex;
	private Font awtFont;
	private TrueTypeFont font;
	private String fontName;
	private float fontSize;
	private Color fontColor;
	
	private boolean isActive = true;
	
	
	public UITextButton(float x, float y, String[] text, String function, String fontName, float fontSize, Color fontColor)
	{
		this.x = x;
		this.font_x = x;
		this.y = y;
		this.font_y = y;
		this.width = 0;
		this.height = 0;
		
		this.text = text;
		this.function = function;
		
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		font = FontManager.loadFont(fontName, fontSize);
		
		this.font_y = Settings.getRES_Y() - y - font.getHeight();
		width = font.getWidth(text[textIndex]);
		height = font.getHeight();
	}
	

	
	public void draw()
	{
		if (isActive)
		{
			Helper.setTextProjection();
			font.drawString(font_x, font_y, text[textIndex], fontColor);
			Helper.setGameProjection();
		}
	}
	
	public boolean inBounds()
	{	
		if ((Mouse.getX() > x) && (Mouse.getX() < (x + width)) && (Mouse.getY() > y) && (Mouse.getY() < (y + height)))
			return true;
		else 
			return false;
	}
	
	public boolean inBounds(int MouseX, int MouseY)
	{	
		if ((MouseX > x) && (MouseX < (x + width)) && (MouseY > y) && (MouseY < (y + height)))
			return true;
		else 
			return false;
	}
	
	/**
	 * Change the current button value to the next element in the text array.
	 */
	public void changeValueForward()
	{
		textIndex++;
		if (textIndex >= text.length)
			textIndex = 0;
	}
	
	/**
	 * Change the current button value to the previous element in the text array.
	 */
	public void changeValueBackward()
	{
		textIndex--;
		if (textIndex < 0)
			textIndex = text.length - 1;
	}



	public float getX() {
		return x;
	}



	public void setX(float x) {
		this.x = x;
	}



	public float getFont_x() {
		return font_x;
	}



	public void setFont_x(float font_x) {
		this.font_x = font_x;
	}



	public float getY() {
		return y;
	}



	public void setY(float y) {
		this.y = y;
	}



	public float getFont_y() {
		return font_y;
	}



	public void setFont_y(float y) {
		this.font_y = Settings.getRES_Y() - y - font.getHeight();
	}



	public float getWidth() {
		return width;
	}



	public void setWidth(float width) {
		this.width = width;
	}



	public float getHeight() {
		return height;
	}



	public void setHeight(float height) {
		this.height = height;
	}



	public String[] getText() {
		return text;
	}



	public void setText(String[] text) {
		this.text = text;
		width = font.getWidth(text[textIndex]);
	}



	public int getTextIndex() {
		return textIndex;
	}



	public void setTextIndex(int textIndex) {
		this.textIndex = textIndex;
	}



	public String getFunction() {
		return function;
	}



	public void setFunction(String function) {
		this.function = function;
	}



	public int getFontIndex() {
		return fontIndex;
	}



	public void setFontIndex(int fontIndex) {
		this.fontIndex = fontIndex;
	}



	public Font getAwtFont() {
		return awtFont;
	}



	public void setAwtFont(Font awtFont) {
		this.awtFont = awtFont;
	}



	public TrueTypeFont getFont() {
		return font;
	}



	public void setFont(TrueTypeFont font) {
		this.font = font;
	}



	public String getFontName() {
		return fontName;
	}



	public void setFontName(String fontName) {
		this.fontName = fontName;
	}



	public float getFontSize() {
		return fontSize;
	}



	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}



	public Color getFontColor() {
		return fontColor;
	}



	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	
	public void setActive(boolean value)
	{
		isActive = value;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
}
