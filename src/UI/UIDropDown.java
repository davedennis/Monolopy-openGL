package UI;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import Graphics.Drawable;
import Graphics.SpriteBatcher;
import Resources.FontManager;


class UIDropDown
{
	private Drawable divider;
	private Drawable small_background, small_left, small_right;
	private Drawable background, left, right, up, down;
	private float x;
	private float y;
	private float width;
	private float height;
	
	private float spacer_x = 10;
	private float spacer_main = 0;
	private float side_width = 5;
	private float center_width = 5;
	private float font_offset;
	private String border_color = "res/images/colors/white.png";
	private String main_color = "res/images/colors/grey.png";
	
	private UIText selection;
	private UIText[] buttons;
	private String function;
	
	private TrueTypeFont trueTypeFont;
	
	private boolean isDropped = false;
	
	
	/**
	 * 
	 * Currently contains hardcoded font and fontSizes. Is a total mess. TODO: Clean This Mess Up...
	 */
	UIDropDown(float x, float y, String[] text, String function)
	{
		trueTypeFont =  FontManager.loadFont("res/fonts/calibrib.ttf", 18f);
		
		this.x = x;
		this.y = y;
		this.function = function;
		
		buttons = new UIText[text.length];
		float textHeight = new UIText(x, y, "", trueTypeFont, Color.black).getHeight();
		
		for (int i = 0; i < text.length; i++)
		{
			buttons[i] = new UIText(x + spacer_x, y - textHeight * (i + 1), text[i], trueTypeFont, Color.black);
			if (buttons[i].getWidth() > width)
				width = buttons[i].getWidth();
		}
		spacer_main = buttons[0].getHeight();
		this.height = spacer_main * (text.length + 1);
		
		selection = new UIText(x + spacer_x, y, text[0], trueTypeFont, Color.white);

		width += spacer_x + 10;
		font_offset = buttons[0].getHeight() - 2;
		
		divider = new Drawable(x, y - spacer_main + font_offset, width, center_width, border_color);
		
		small_background = new Drawable(x, y - spacer_main + font_offset, width, spacer_main, main_color);
		small_left = new Drawable(x, y - spacer_main + font_offset, side_width, spacer_main, border_color);
		small_right = new Drawable(x + width - side_width, y - spacer_main + font_offset, side_width, spacer_main, border_color);
		
		background = new Drawable(x, y + font_offset, width, -height, main_color);
		left = new Drawable(x, y + font_offset - height, side_width, height, border_color);
		right = new Drawable(x + width - side_width, y + font_offset- height, side_width, height, border_color);
		up = new Drawable(x, y + font_offset, width, side_width, border_color);
		down = new Drawable(x, y - height + font_offset, width, side_width, border_color);
	}
	
	boolean isDropped() {
		return isDropped;
	}

	void setDropped(boolean isDropped) {
		this.isDropped = isDropped;
	}
	
	void draw()
	{
		SpriteBatcher.begin();
		if (isDropped)
		{
			background.draw();
			left.draw();
			right.draw();
			up.draw();
			down.draw();
			divider.draw();
		}
		else
		{
			small_background.draw();
			small_left.draw();
			small_right.draw();
			up.draw();
			divider.draw();
		}
		
		selection.draw();
		if (isDropped)
		{
			for (int i = 0; i < buttons.length; i++)
			{
				buttons[i].draw();
			}
		}
		SpriteBatcher.end();
	}
	
	boolean inBounds()
	{
		if (isDropped)
		{
			if ((Mouse.getX() > x) && (Mouse.getX() < (x + width)) && (Mouse.getY() > y + spacer_main - height) && (Mouse.getY() < (y + spacer_main)))
				return true;
		}
		else
		{
			if ((Mouse.getX() > x) && (Mouse.getX() < (x + width)) && (Mouse.getY() > y) && (Mouse.getY() < (y + spacer_main)))
				return true;
		}
		
		return false;
	}
	
	void changeSelection()
	{
		int selectionNumber = (int) ((y - Mouse.getY()) / spacer_main);
		selection.setText(buttons[selectionNumber].getText());
		isDropped = false;
	}
	
	String getFunction()
	{
		return function;
	}
	
	UIText getSelection() {
		return selection;
	}

	void setSelection(UIText selection) {
		this.selection = selection;
	}
}
