package com.pingpong;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by akhil on 31/10/15.
 */
public class Player implements Runnable {

	private int mPlayerNumber;
	private String mPlayerName;
	private int mDefenceSetLength;
	private int mScore;
	private Game mGame;
	private Random mRandom;
	private boolean mIsFistPlayer;

	public Player(int mPlayerNumber, String mPlayerName, int mDefenceSetLength) {
		super();
		this.mPlayerNumber = mPlayerNumber;
		this.mPlayerName = mPlayerName;
		this.mDefenceSetLength = mDefenceSetLength;
		mRandom = new Random();
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

	public String getPlayerName() {
		return mPlayerName;
	}

	public void setPlayerName(String playerName) {
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
			while (!mGame.isGameOver()) {
				System.out.println(mGame.getCurrentPlayer().getPlayerName()+"Inside while---"+mPlayerName);
				if (mGame.getCurrentPlayer() != this) {
					try {
						System.out.println(mGame.getCurrentPlayer().getPlayerName()+"Inside Wait---"+mPlayerName);
						mGame.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (mIsFistPlayer) {
					mGame.setRandom(makeMove());
				} else {
					mGame.setDefenceSet(getDefenceSet());
				}
			}
		}
		System.out.print("Exiting--->"+mPlayerName);
	}

	public int makeMove() {
		int random = mRandom.nextInt(10) + 1;
		return random;
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

	public void startPlaying(Game game) {
		mGame = game;
		new Thread(this).start();
	}


}
