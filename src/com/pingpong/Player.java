package com.pingpong;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by akhil on 31/10/15.
 */
public class Player implements Runnable {

    private int mPlayerNumber;
    private int mPlayerName;
    private int mDefenceSetLength;
    private int mScore;
    private Game mGame;
    private Random mRandom;
    private boolean mIsFistPlayer;
    
    public Player(int mPlayerNumber, int mPlayerName, int mDefenceSetLength) {
		super();
		this.mPlayerNumber = mPlayerNumber;
		this.mPlayerName = mPlayerName;
		this.mDefenceSetLength = mDefenceSetLength;
	}

	public boolean isFistPlayer() {
		return mIsFistPlayer;
	}

	public void setIsFistPlayer(boolean isFistPlayer) {
		this.mIsFistPlayer = isFistPlayer;
	}

	public int getPlayerNumber() {
        return mPlayerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        mPlayerNumber = playerNumber;
    }

    public int getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(int playerName) {
        mPlayerName = playerName;
    }

    public int getDefenceSetLength() {
        return mDefenceSetLength;
    }

    public void setDefenceSetLength(int defenceSetLength) {
        mDefenceSetLength = defenceSetLength;
    }

    @Override
    public void run() {
        synchronized (mGame) {
            while (mGame.isGameOver()) {
            	if (mIsFistPlayer) {
            		mGame.setRandom(makeMove());
            	} else {
            		//generateArray
            		mGame.setDefenceSet(getDefenceSet());
            	}
            	
            }
        }

    }

    public int makeMove() {
        return mRandom.nextInt(10) + 1;
    }

    public List<Integer> getDefenceSet() {
        List<Integer> defenceSetList = new ArrayList<>(mDefenceSetLength);
        for (int i = 0; i < mDefenceSetLength; i++) {
            defenceSetList.add(mRandom.nextInt(10) + 1);
        }

        return defenceSetList;
    }

    public void increaseScore() {
        mScore++;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public Player(Game game) {
        mRandom = new Random();
    }

    public void startPlaying(Game game) {
    	mGame = game;
    	new Thread(this).start();
    }
	

}
