package no.ntnu.progark.towerdefense.main;

import android.content.SharedPreferences;

public class GameHandler {
	
	private static int score;
	private static int money;
	private static int lives;
	private static boolean gameActive;

	/**
	 * Resets game values to default
	 * Should be called when starting a new game
	 */
	public static void resetGameValues() {
		lives = Globals.LIVES;
		score = 0;
		money = Globals.STARTING_MONEY;
		gameActive = true;
	}
	
	public static boolean isGameActive() {
		return gameActive;
	}



	public static void setGameActive(boolean gameActive) {
		GameHandler.gameActive = gameActive;
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int s) {
		score = s;
	}
	
	public static void increaseScore(int increase) {
		score += increase;
	}
	
	/**
	 * Saves score to phone memory
	 * @param prefs - the preferences for the game
	 * @param username - the name entered by the user
	 */
	public static void saveScore(SharedPreferences prefs, String username) {
		prefs.edit().putString("score", username+":  "+score).commit();
		Utils.log("Saving score for user "+username+" with score" +score);
	}
	
	public static String getHighscoreText(SharedPreferences prefs) {
		return prefs.getString("score", "");
	}
	
	public static int getMoney() {
		return money;
	}

	public static String getMoneyText(){
		return Integer.toString(money);
	}
	public static void spendMoney(int value) {
		money -= value;
	}
	
	public static void increaseMoney(int value) {
		money += value;
		Utils.log("increasing money to "+money);
	}
	
	public static void removeLife() {
		Utils.log("an enemy reached the goal. removing a life");
		lives--;
	}
	
	public static int getLives() {
		return lives;
	}
	
	public static boolean isDead() {
		if(lives<=0) {
			return true;
		}else {
			return false;
		}
	}
}
