package Main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import Graphics.ShaderUtility;
import Resources.GameState;
import Resources.Helper;
import Resources.Settings;
import Screens.MainMenuScreen;
import Screens.GameScreen;
import Screens.Screen;
import UI.UIUtility;

public class Main 
{
	private static Screen activeScreen;
	
	public static void main(String[] args)
	{
		Helper.initializeDisplay();
		Helper.setGameProjection();
		ShaderUtility.createDefaultShaders();
		GameState gameState; 
		Helper.updateDelta(); // flush once before loop to set first frame time. Call once per frame and save value
	
		while (!Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
			gameState = Settings.getGameState();
			Helper.updateDelta();
			Helper.updateTick();
			
			
			if (gameState == GameState.GAME)
			{
				if (activeScreen == null)
					activeScreen = new GameScreen(Settings.getNumPlayers());
			
				activeScreen.run();
			}
			else if (gameState == GameState.MAIN_MENU)
			{
				if (activeScreen == null)
					activeScreen = new MainMenuScreen();
				
				activeScreen.run();
			}
			
			if (Settings.checkGameStateUpdate()) {
				activeScreen = null;
				UIUtility.clear();
			}
			Settings.runDebug();
			
			if (Settings.isV_SYNC())
				Display.sync(60);
			Display.update();
		}
	}
}