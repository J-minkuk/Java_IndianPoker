package net.skhu.IndianPoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ai{
	private int coin;
	List<Integer> aiPokerDeck = new ArrayList<Integer>();
	
	static public List<Integer> aiRemoveCard = new ArrayList<Integer>();	// ai 꺼낸 카드 저장 리스트
	
	public Ai() {
		int[] deck = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5,
				  	  6, 6, 7, 7, 8, 8, 9, 9, 10, 10 };
	
		for(int i=0; i < deck.length; ++i)
			aiPokerDeck.add(deck[i]);
		Collections.shuffle(aiPokerDeck);
		// System.out.println("aiPorkerDeck: "+aiPokerDeck);
	}
		
	public int getCard(int index) {
		return aiPokerDeck.get(index);
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	
	public void addAiRemoveCard(int value) {
		aiRemoveCard.add(value);
		// System.out.println(aiRemoveCard.toString());
	}
	
	// 세부적인 확률 계산을 위한 method (value = nowUser)
	public int detailPer(int value) {
		int percent = 0;
		Collections.sort(Ai.aiRemoveCard);
		// i(nowUser)보다 작은 것이 aiRemoveCard에 포함되어 있으면 percent를 증가시키고,
		for(int i = value - 1; i > 0; --i) {
			if(Ai.aiRemoveCard.contains(i))
				if(Ai.aiRemoveCard.indexOf(i) != Ai.aiRemoveCard.lastIndexOf(i))
					percent += 10;
				else percent += 5;
		}
		// i(nowUser)보다 높거나 같은 것이 aiRemoveCard에 포함되어 있으면 percent를 낮춘다.
		for(int i = value; i <= 10; ++i) {
			if(Ai.aiRemoveCard.contains(i))
				if(Ai.aiRemoveCard.indexOf(i) != Ai.aiRemoveCard.lastIndexOf(i))
					percent -= 10;
				else percent -= 5;
		}
		return percent;
	}	
	
	// 배팅할 지 안 할지 선택하는 method
	public int getChoice() {	
		// 남은 라운드를 다 죽어도 ai가 이길 경우 (현재 라운드도 포함해서 계산해야만 하기때문에 총 20라운드지만 21에서 현재 라운드를 뺀다.)
		if(IndianPokerEdit.getUserCoin() + (21 - IndianPokerEdit.getRound()) <= getCoin() - (21 - IndianPokerEdit.getRound())) return 2;
		else if(User.nowUser == 10) return 2;	// user현재 카드가 10이면 무조건 폴드
		else if(User.nowUser == 1) return 1;	// user현재 카드가 1이면 무조건 배팅 선택
		// 1,2,3 라운드 경우
		else if(IndianPokerEdit.getRound() == 1 || IndianPokerEdit.getRound() == 2 || IndianPokerEdit.getRound() == 3) {
			if(User.nowUser == 2 || User.nowUser == 3 || User.nowUser == 4) return 1;
			else return 2; 
		}
		// 4라운드 경우 (Ai.aiRemoveCard.size() == 3)
		else if(IndianPokerEdit.getRound() == 4) {
			if(User.nowUser == 2 || User.nowUser == 3) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 5) return 1;
			else return 2;
		}
		// 5라운드 경우 (Ai.aiRemoveCard.size() == 4)
		else if(IndianPokerEdit.getRound() == 5) {
			if(User.nowUser == 2 || User.nowUser == 3) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 0) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 20) return 1;
			else return 2;
		}
		// 6라운드 경우 (Ai.aiRemoveCard.size() == 5)
		else if(IndianPokerEdit.getRound() == 6) {
			if(User.nowUser == 2 || User.nowUser == 3) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -15) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 15) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 25) return 1;
			else return 2;
		}
		// 7라운드 경우 (Ai.aiRemoveCard.size() == 6)
		else if(IndianPokerEdit.getRound() == 7) {
			if(User.nowUser == 2 || User.nowUser == 3) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 0) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 10) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 30) return 1;
			else return 2;
		}
		// 8라운드 경우 (Ai.aiRemoveCard.size() == 7)
		else if(IndianPokerEdit.getRound() == 8) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -25) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -15) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 15) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 35) return 1;
			else return 2;
		}
		// 9라운드 경우 (Ai.aiRemoveCard.size() == 8)
		else if(IndianPokerEdit.getRound() == 9) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -30) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -20) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 0) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 10) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 40) return 1;
			else return 2;
		}
		// 10라운드 경우 (Ai.aiRemoveCard.size() == 9)
		else if(IndianPokerEdit.getRound() == 10) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -25) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 5) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 45) return 1;
			else return 2;
		}
		//// 좀 더 느슨하게 수정해본다.
		// 11라운드 경우 (Ai.aiRemoveCard.size() == 10)
		else if(IndianPokerEdit.getRound() == 11) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -25) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 5) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 45) return 1;
			else if(User.nowUser == 7) return 1;
			else return 2;
		}
		// 12라운드 경우 (Ai.aiRemoveCard.size() == 11)
		else if(IndianPokerEdit.getRound() == 12) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -45) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -25) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 15) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 35) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 55) return 1;
			else return 2;
		}
		// 13라운드 경우 (Ai.aiRemoveCard.size() == 12)
		else if(IndianPokerEdit.getRound() == 13) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -50) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -30) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -10) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 10) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 30) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 60) return 1;
			else return 2;
		}
		// 14라운드 경우 (Ai.aiRemoveCard.size() == 13)
		else if(IndianPokerEdit.getRound() == 14) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -55) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -30) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -10) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 10) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 30) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 50) return 1;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 65) return 1;
			else return 2;			
		}
		// 15라운드 경우 (Ai.aiRemoveCard.size() == 14)
		else if(IndianPokerEdit.getRound() == 15) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -60) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -35) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -15) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 5) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 25) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 45) return 1;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 70) return 1;
			else return 2;			
		}
		// 16라운드 경우 (Ai.aiRemoveCard.size() == 15)
		else if(IndianPokerEdit.getRound() == 16) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -65) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -40) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -20) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 0) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 20) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 40) return 1;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 65) return 1;
			else return 2;			
		}
		// 17라운드 경우 (Ai.aiRemoveCard.size() == 16)
		else if(IndianPokerEdit.getRound() == 17) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -60) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -40) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -20) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 0) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 20) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 40) return 1;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 60) return 1;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 80) return 1;
			else return 2;			
		}
		// 18라운드 경우 (Ai.aiRemoveCard.size() == 17)
		else if(IndianPokerEdit.getRound() == 18) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -65) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -45) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -25) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 15) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 35) return 1;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 55) return 1;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 75) return 1;
			else return 2;			
		}
		// 19라운드 경우 (Ai.aiRemoveCard.size() == 18)
		else if(IndianPokerEdit.getRound() == 19) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -70) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -50) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -30) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= -10) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 10) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 30) return 1;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 50) return 1;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 70) return 1;
			else if(User.nowUser == 10 && this.detailPer(User.nowUser) >= 90) return 1;
			else return 2;			
		}
		// 20라운드 경우 (Ai.aiRemoveCard.size() == 19)
		else if(IndianPokerEdit.getRound() == 20) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -75) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -55) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -35) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= -15) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 5) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 25) return 1;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 45) return 1;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 65) return 1;
			else if(User.nowUser == 10 && this.detailPer(User.nowUser) >= 85) return 1;
			else return 2;			
		}
		else return 2;
	}
	
	// 라운드 첫 배팅 함수
	public int betCoin() {
		if(User.nowUser == 1) return 5;	// nowUser가 1이면 배팅 5
		// 1,2,3 라운드 경우
		else if(IndianPokerEdit.getRound() == 1 || IndianPokerEdit.getRound() == 2 || IndianPokerEdit.getRound() == 3) {
			if(User.nowUser == 2 || User.nowUser == 3 || User.nowUser == 4) return 2;
			else return 1; 
		}
		// 4라운드 경우 (Ai.aiRemoveCard.size() == 3)
		else if(IndianPokerEdit.getRound() == 4) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= 5) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 10) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 15) return 2;
			else return 1;
		}
		// 5라운드 경우 (Ai.aiRemoveCard.size() == 4)
		else if(IndianPokerEdit.getRound() == 5) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= 0) return 4;
			else if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -10) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 20) return 5;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 0) return 2;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 20) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 20) return 2;
			else return 1;
		}
		// 6라운드 경우 (Ai.aiRemoveCard.size() == 5)
		else if(IndianPokerEdit.getRound() == 6) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -5) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 15) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 5) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -25) return 2;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 25) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 5) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 2;
			else return 1;
		}
		// 7라운드 경우 (Ai.aiRemoveCard.size() == 6)
		else if(IndianPokerEdit.getRound() == 7) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -10) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 0) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -10) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -20) return 2;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 20) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 10) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 0) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 30) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 30) return 2;
			else return 1;
		}
		// 8라운드 경우 (Ai.aiRemoveCard.size() == 7)
		else if(IndianPokerEdit.getRound() == 8) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -25) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 5) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -5) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -15) return 2;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 15) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 5) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -5) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 15) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 35) return 2;
			else return 1;
		}
		// 9라운드 경우 (Ai.aiRemoveCard.size() == 8)
		else if(IndianPokerEdit.getRound() == 9) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -20) return 4;
			else if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -30) return 2;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= 0) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -20) return 2;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 10) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 0) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -20) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 40) return 5;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 20) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 40) return 2;
			else return 1;
		}
		// 10라운드 경우 (Ai.aiRemoveCard.size() == 9)
		else if(IndianPokerEdit.getRound() == 10) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -25) return 4;
			else if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -35) return 2;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -5) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -15) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -25) return 2;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 15) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -15) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 35) return 5;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 45) return 2;
			else return 1;
		}
		// 11라운드 경우 (Ai.aiRemoveCard.size() == 10)
		else if(IndianPokerEdit.getRound() == 11) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -30) return 5;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -10) return 4;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -20) return 2;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 10) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 0) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 30) return 5;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 20) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 50) return 5;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 40) return 2;
			else return 1;
		}
		// 12라운드 경우 (Ai.aiRemoveCard.size() == 11)
		else if(IndianPokerEdit.getRound() == 12) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -35) return 5;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -15) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 5) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -5) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 5;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 15) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 45) return 5;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 35) return 2;
			else return 1;
		}
		// 13라운드 경우 (Ai.aiRemoveCard.size() == 12)
		else if(IndianPokerEdit.getRound() == 13) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -40) return 5;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -20) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 0) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -10) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 20) return 5;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 10) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 40) return 5;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 30) return 2;
			else return 1;
		}
		// 14라운드 경우 (Ai.aiRemoveCard.size() == 13)
		else if(IndianPokerEdit.getRound() == 14) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -45) return 5;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -25) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -5) return 4;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -15) return 2;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 15) return 5;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 5) return 2;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 35) return 5;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 25) return 2;
			else return 1;		
		}
		// 15라운드 경우 (Ai.aiRemoveCard.size() == 14)
		else if(IndianPokerEdit.getRound() == 15) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -50) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -30) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -10) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 10) return 3;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 30) return 3;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 50) return 3;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 70) return 3;
			else return 1;		
		}
		// 16라운드 경우 (Ai.aiRemoveCard.size() == 15)
		else if(IndianPokerEdit.getRound() == 16) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -55) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -35) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -15) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 5) return 3;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 25) return 3;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 45) return 3;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 65) return 3;
			else return 1;		
		}
		// 17라운드 경우 (Ai.aiRemoveCard.size() == 16)
		else if(IndianPokerEdit.getRound() == 17) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -60) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -40) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -20) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 0) return 3;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 20) return 3;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 40) return 3;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 60) return 3;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 80) return 3;
			else return 1;					
		}
		// 18라운드 경우 (Ai.aiRemoveCard.size() == 17)
		else if(IndianPokerEdit.getRound() == 18) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -65) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -45) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -25) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= -5) return 3;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 15) return 3;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 35) return 3;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 55) return 3;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 75) return 3;
			else return 1;			
		}
		// 19라운드 경우 (Ai.aiRemoveCard.size() == 18)
		else if(IndianPokerEdit.getRound() == 19) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -70) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -50) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -30) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= -10) return 3;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 10) return 3;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 30) return 3;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 50) return 3;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 70) return 3;
			else return 1;		
		}
		// 20라운드 경우 (Ai.aiRemoveCard.size() == 19)
		else if(IndianPokerEdit.getRound() == 20) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -75) return 3;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -55) return 3;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -35) return 3;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= -15) return 3;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 5) return 3;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 25) return 3;
			else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 45) return 3;
			else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 65) return 3;
			else return 1;			
		}
		else return 1;
	}
	
	// 추가 배팅 함수
//	public int reBetCoin() {
//		
//	}
}

