package com.pingpong;

import java.util.List;

/**
 * Created by akhil on 01/11/15.
 */
public class Game {

    private static final int MAX_SCORE = 5;
    private Player mFirstPlayer;
    private Player mSecondPlayer;
	private int mRandom;
	private Player mCurrentPlayer;


    public Game(Player player1, Player player2) {
        mFirstPlayer = player1;
        mSecondPlayer = player2;
        mCurrentPlayer = player1;
    }

    public void startPlay() {
    	mFirstPlayer.startPlaying(this);
    	mSecondPlayer.startPlaying(this);
    }

    private boolean isPlayerWon(Player player) {
        return player.getScore() == MAX_SCORE;
    }


    public boolean isGameOver() {
        return isPlayerWon(mFirstPlayer) || isPlayerWon(mSecondPlayer) ;
    }
    
    public void setRandom(int random) {
    	synchronized (this) {
        mRandom = random;
        System.out.println("setRandom"+mCurrentPlayer.getPlayerName()+"--->"+random);
        mCurrentPlayer = (mSecondPlayer.isFistPlayer() ? mFirstPlayer : mSecondPlayer);
        this.notifyAll();
    	}
    }

    public void setDefenceSet(List<Integer> defenceSet) {
    	synchronized (this) {
    		System.out.println(mCurrentPlayer.getPlayerName()+"--->"+defenceSet.toString());
    	if (defenceSet.contains(mRandom)) {
    		mFirstPlayer.increaseScore();
    		mFirstPlayer.setIsFistPlayer(true);
    		mSecondPlayer.setIsFistPlayer(false);
    		mCurrentPlayer = mFirstPlayer;
    	} else {
    		mSecondPlayer.increaseScore();
    		mFirstPlayer.setIsFistPlayer(false);
    		mSecondPlayer.setIsFistPlayer(true);
    		mCurrentPlayer = mSecondPlayer;
    	}
    	System.out.println(mFirstPlayer.getPlayerName()+"----Scores --->"+mFirstPlayer.getScore());
    	System.out.println(mSecondPlayer.getPlayerName()+"----Scores --->"+mSecondPlayer.getScore());
    	System.out.println("First Player"+mCurrentPlayer.getPlayerName());
    	this.notifyAll();
    	}
    }

	public Player getCurrentPlayer() {
		return mCurrentPlayer;
	}

	public Player getWinner() {
		return isPlayerWon(mFirstPlayer) ? mFirstPlayer : mSecondPlayer;
	}
}
