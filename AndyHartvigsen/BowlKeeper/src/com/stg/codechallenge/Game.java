package com.stg.codechallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
	private List<Frame> frames;
	private Frame currentFrame;
	private List<Integer> bonusBalls;
	private final int TWO = 2;
	private boolean gameOver = false;
	
	public Game() {
		frames = Arrays.asList(new Frame[10]);
		for(int i = 0; i < 10; i++) {
			frames.set(i, new Frame(i+1));
		}
		
		currentFrame = frames.get(0);
		 bonusBalls = new ArrayList<>();
	}
	
	public void playBall(int pins) throws BowlingException {
		if(gameOver) {
			throw new BowlingException("Game Over! Add more scores.");
		}
		currentFrame.addBall(pins);
		if(bonusBalls.size() > 0) {
			for(int i = 0; i < bonusBalls.size(); i++) {
				Integer bonusBall = bonusBalls.get(i);
				if(bonusBall > 0) {
					bonusBalls.set(i, bonusBall -1);
					frames.get(i).addBonus(pins);
				}
			}
		}
		if((currentFrame.ballsBowled() == TWO || currentFrame.isStrike()) && currentFrame.getFrameNum() != currentFrame.TEN) {
			if(currentFrame.isSpare()) {
				bonusBalls.add(1);
			} else if(currentFrame.isStrike()) {
				bonusBalls.add(2);
			}
			else {
				bonusBalls.add(0);
			}
			currentFrame = frames.get(currentFrame.getFrameNum());
		}
		if(currentFrame.getFrameNum() == currentFrame.TEN) {
			if(((currentFrame.isStrike() || currentFrame.isSpare()) && currentFrame.ballsBowled() == 3) 
					|| ((!currentFrame.isStrike() && !currentFrame.isSpare()) && currentFrame.ballsBowled() == 2)) {
				gameOver = true;
			}
		}
		printGame();
	}
	
	public int getTotalScore() {
		int totalScore = 0;
		for(Frame frame : frames) {
			totalScore += frame.getScore();
		}
		return totalScore;
	}
	
	public void printGame() {
		for(Frame frame : frames) {
			System.out.println(frame);
		}
	}
}
