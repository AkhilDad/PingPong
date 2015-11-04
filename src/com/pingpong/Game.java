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


    public Game(Player player1, Player player2) {
        mFirstPlayer = player1;
        mSecondPlayer = player2;
    }

    public Player startPlay() {
//        while(isGameOver()) {
            handleOneChance(mFirstPlayer, mSecondPlayer);
//        }
        return isPlayerWon(mFirstPlayer) ? mFirstPlayer : mSecondPlayer;
    }

    private boolean isPlayerWon(Player player) {
        return player.getScore() == MAX_SCORE;
    }

    private void handleOneChance(Player firstPlayer, Player secondPlayer) {
    	mFirstPlayer = firstPlayer;
    	mSecondPlayer = secondPlayer;
        firstPlayer.notify();
    }

    public boolean isGameOver() {
        return mFirstPlayer.getScore() == MAX_SCORE || mSecondPlayer.getScore() == MAX_SCORE;
    }
    
    public synchronized void setRandom(int random) {
            try {
                mFirstPlayer.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        mRandom = random;
        mSecondPlayer.notify();
        System.out.println(random);
        notify();
    }

    public synchronized void setDefenceSet(List<Integer> defenceSet) {
    	try {
    		mSecondPlayer.wait();
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	if (defenceSet.contains(mRandom)) {
    		mFirstPlayer.increaseScore();
    		mFirstPlayer.setIsFistPlayer(true);
    		mSecondPlayer.setIsFistPlayer(false);
    	} else {
    		mSecondPlayer.increaseScore();
    		mFirstPlayer.setIsFistPlayer(false);
    		mSecondPlayer.setIsFistPlayer(true);
    	}
    	//notify referee that this game is complete
    }

    

	private Player checkForWin() {
		if (isGameOver()) {
			return isPlayerWon(mFirstPlayer) ? mFirstPlayer : mSecondPlayer;
		} else {
			return null;
		}
		
	}
}
