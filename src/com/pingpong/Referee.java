package com.pingpong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 01/11/15.
 */
public class Referee implements Runnable{

	List<Game> mGames;
	private boolean mIsAllGamesOver;
	private boolean mIsLeaugeComplete;
	List<Player> mPlayers;
	public Referee(List<Player> players) throws Exception {
		if ((players.size()/2) % 2 != 0) {
			throw new Exception("Players should be in even number groups");
		}
		mPlayers = players;    	 
	}

	private List<Game> prepareGamesList(List<Player> players) {
		List<Game> games = new ArrayList<Game>(players.size()/2);
		for (int i = 0; i < players.size(); i+=2) {
			games.add(new Game(players.get(i), players.get(i+1)));
		}
		return games;
	}

	public Thread startLeauge() {
		Thread thread = new Thread(this);
		thread.start();
		return thread;
	}

	@Override
	public void run() {
		while (!mIsLeaugeComplete){
			mGames = startGames(prepareGamesList(mPlayers));
			mIsAllGamesOver = false;
			while (!mIsAllGamesOver) {
				mIsAllGamesOver = true;
				for (Game game : mGames) {
					if (!game.isGameOver()) {
						mIsAllGamesOver = false;
						break;
					}
				}
				if (mIsAllGamesOver){
					break;
				}	
			}
			mIsLeaugeComplete = checkIfIsLeagueComplete(mGames);
			if (!mIsLeaugeComplete) {
				 mPlayers = new ArrayList<Player>(mGames.size()/2);
				for (Game game : mGames) {
					mPlayers.add(game.getWinner());
				}
			}
		}	
		System.out.println("*******-Winner---*****"+mGames.get(0).getWinner().getPlayerName());
	}

	private boolean checkIfIsLeagueComplete(List<Game> games) {
		if(games.size() == 1) {
			return true;
		}
		return false;
	}

	private List<Game> startGames(List<Game> games) {
		for (Game game : games) {
			game.startPlay();
		}
		return games;
	}
}
