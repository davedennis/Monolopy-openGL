package Screens;

import org.newdawn.slick.Color;

import Graphics.Drawable;
import Graphics.SpriteBatcher;
import Resources.Helper;
import Resources.Settings;
import UI.UIUtility;

public class MainMenuScreen extends Screen
{
	Drawable background;
	
	public MainMenuScreen()
	{
		background = new Drawable(0, 0, Settings.getRES_X(), Settings.getRES_Y(), "res/images/colors/white.png");
		
		UIUtility.createTextButton(720, 350, new String[] {"Play"}, "play_game", "res/fonts/calibrib.ttf", 24, Color.white);
		UIUtility.createText(700, 600, "SHIT-OPOLY", "res/fonts/calibrib.ttf", 64, Color.white, "name");
		UIUtility.createText(100, 100, Helper.getWinner(), "res/fonts/calibrib.ttf", 24, Color.white, "winnerString");
		UIUtility.createText(720, 400, "Number of Players:", "res/fonts/calibrib.ttf", 24, Color.white, "numplayers");

		UIUtility.createDropDown(900, 500, new String[] {"2", "3", "4"}, "menu_num_players"); 
	}
	
	@Override
	public void run()
	{	
		SpriteBatcher.begin();
		SpriteBatcher.drawDune(background);
		SpriteBatcher.endDune();
		
		UIUtility.executeUI();
	}
}
