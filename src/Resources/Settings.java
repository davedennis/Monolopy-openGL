package Resources;

import UI.UIConsole;

public class Settings
{
	private static GameState gameState = GameState.MAIN_MENU;
	private static GameState newGameState = null;
	private static int numPlayers = 2;
	private static final String WINDOW_TITLE = "Monopoly v.069";
	private static final int SPLASH_TIME = 300;
	private static final int RES_X = 1600;
	private static final int RES_Y = 1100;
	private static final boolean FULLSCREEN = false;
	private static final boolean V_SYNC = true;
	
	private static boolean DEBUG = false;
	private static boolean dSHOWFRAMETIMES = true;
	
	public static void runDebug()
	{
		if (DEBUG)
			if (dSHOWFRAMETIMES)
				UIConsole.print("Frame Time: " + Helper.getDelta() + " ms");
	}
	public static boolean checkGameStateUpdate()
	{
		if (newGameState != null) 
		{
			gameState = newGameState;
			newGameState = null;
			return true;
		}
		return false;
	}
	
	public static GameState getGameState() {
		return gameState;
	}
	public static void setGameState(GameState gameState) {
		newGameState = gameState;
	}
	public static int getNumPlayers() {
		return numPlayers;
	}
	public static void setNumPlayers(int number) {
		numPlayers = number;
	}
	public static String getWINDOW_TITLE() {
		return WINDOW_TITLE;
	}
	public static int getSPLASH_TIME() {
		return SPLASH_TIME;
	}
	public static int getRES_X() {
		return RES_X;
	}
	public static int getRES_Y() {
		return RES_Y;
	}
	public static boolean isFULLSCREEN() {
		return FULLSCREEN;
	}
	public static boolean isV_SYNC() {
		return V_SYNC;
	}
	public static boolean isDEBUG() {
		return DEBUG;
	}
	public static void setDEBUG(boolean dEBUG) {
		DEBUG = dEBUG;
	}
}
