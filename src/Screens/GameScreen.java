package Screens;

import org.lwjgl.input.Keyboard;

import Graphics.Drawable;
import Graphics.SpriteBatcher;
import Main.GameBoard;
import UI.UIConsole;
import UI.UIUtility;

public class GameScreen extends Screen 
{
	private GameBoard gameBoard;
	private Drawable background;
	
	public GameScreen(int numPlayers)
	{	
		gameBoard = new GameBoard(numPlayers);
		background = new Drawable(0, 0, 1100, 1100, "res/images/colors/white.png");

		//UIUtility.UpdateText(1, gameBoard.);
	}
	
	@Override
	public void run()
	{				
		SpriteBatcher.begin();
		background.draw();
		SpriteBatcher.end();
		
		gameBoard.draw();
		gameBoard.run();
		UIUtility.executeUI();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			UIConsole.scrollUp();
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			UIConsole.scrollDown();
		
		UIConsole.display();
	}
}
