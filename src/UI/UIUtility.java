package UI;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import Resources.GameState;
import Resources.Helper;
import Resources.Settings;


public class UIUtility
{
	private static ArrayList<UIButton> allButtons = new ArrayList<UIButton>();
	private static ArrayList<UITextButton> allTextButtons = new ArrayList<UITextButton>();
	private static ArrayList<UIDropDown> allDropDowns = new ArrayList<UIDropDown>();
	private static ArrayList<UIText> allText = new ArrayList<UIText>();
	
	@SuppressWarnings("unused")
	private static int lastClick;
	@SuppressWarnings("unused")
	private static int clickCooldown = 20;
	
	private static boolean firstClick = true;
	
	public static void executeUI()
	{	
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		for (UIButton button : allButtons)
			button.draw();
		for (UITextButton textButton : allTextButtons)
			textButton.draw();
		for (UIDropDown dropDown : allDropDowns)
			dropDown.draw();
		for (UIText text : allText)
			text.draw();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GameState gameStateChange = null;
		
		if(!(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)))
			firstClick = true;
		
		if ((Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) && firstClick)
		{
			firstClick = false;
			for (UIDropDown dropDown: allDropDowns)
			{
				if (dropDown.inBounds() && !dropDown.isDropped())
				{
					dropDown.setDropped(true);
					lastClick = Helper.getTick();
					return;
				}
				else if (dropDown.inBounds() && dropDown.isDropped())
				{
					dropDown.changeSelection();
					gameStateChange = checkButtonPress(dropDown);
					lastClick = Helper.getTick();
				}
				else
					dropDown.setDropped(false);
			}
			for (UIButton button : allButtons)
			{
				if (button.inBounds())
				{
					gameStateChange = checkButtonPress(button);
					lastClick = Helper.getTick();
				}
			}
			for (UITextButton textButton: allTextButtons)
			{
				if (textButton.inBounds() && textButton.isActive())
				{
					if (Mouse.isButtonDown(0))
						textButton.changeValueForward();
					else if (Mouse.isButtonDown(1))
						textButton.changeValueBackward();
					gameStateChange = checkButtonPress(textButton);
					lastClick = Helper.getTick();
				}
			}
		}
		
		if (gameStateChange != null)
		{
			UIConsole.print("Setting gamestate to " + gameStateChange.toString());
			Settings.setGameState(gameStateChange);
		}		
	}
	
	public static void createDropDown(float x, float y, String[] text, String function)
	{
		allDropDowns.add(new UIDropDown(x, y, text, function));
	}
	
	public static void createButton(float x, float y, float width, float height, String textureName, String function)
	{
		allButtons.add(new UIButton(x, y, width, height, textureName, function));
	}
	
	public static void createTextButton(float x, float y, String[] text, String function, String fontName, float fontSize, Color fontColor)
	{
		allTextButtons.add(new UITextButton(x, y, text, function, fontName, fontSize, fontColor));
	}
	
	public static void createText(float x, float y, String text, String fontName, float size, Color color, String identifier)
	{
		allText.add(new UIText(x, y, text, fontName, size, color, identifier));
	}
	
	public static void clear()
	{
		allButtons.clear();
		allTextButtons.clear();
		allDropDowns.clear();
		allText.clear();
	}
	
	private static GameState checkButtonPress(UITextButton textButton)
	{
		GameState theGameState = null;
		String function = textButton.getFunction();
		
		if (Settings.getGameState() == GameState.MAIN_MENU)
		{
			if (function.equals("play_game"))
			{
				theGameState = GameState.GAME;
			}
		}
		else if (Settings.getGameState() == GameState.GAME)
		{
			if (function.equals("quit_game"))
			{
				theGameState = GameState.MAIN_MENU;
			}
			
		}
		
		return theGameState;
	}
	
	private static GameState checkButtonPress(UIDropDown dropDown)
	{
		GameState theGameState = null;
		String function = dropDown.getFunction();
		
		if (Settings.getGameState() == GameState.MAIN_MENU)
		{
			if (function.equals("menu_num_players"))
			{
				if (dropDown.getSelection().getText().equals("3"))
					Settings.setNumPlayers(3);
				else if (dropDown.getSelection().getText().equals("4"))
					Settings.setNumPlayers(4);
				else
					Settings.setNumPlayers(2);
			}
		}
		return theGameState;
	}
	
	
	
	private static GameState checkButtonPress(UIButton button)
	{
		GameState theGameState = null;
		//String function = button.getFunction();
		return theGameState;
	}
	
	public static int numTextButtons()
	{
		return allTextButtons.size();
	}
	
	public static int numButtons()
	{
		return allButtons.size();
	}
	
	public static int numText()
	{
		return allText.size();
	}
	
	public static int numDropDowns()
	{
		return allDropDowns.size();
	}
	
	public static ArrayList<UIText> getTextList()
	{
		return allText;
	}
	
//	public static void UpdateText(int index, String text)
//	{
//		allText.get(index).setText(text);
//	}
	
	public static void UpdateText(String identifier, String text)
	{
		
		for (UIText uiText : allText)
		{
			if (uiText.getIdentifier().equals(identifier))
				uiText.setText(text);
		}
	}
}
