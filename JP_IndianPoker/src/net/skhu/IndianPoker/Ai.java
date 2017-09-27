package net.skhu.IndianPoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
	/* 
	 * (참고사항) 매개변수 int index 지금 필요없는데 나중에 필요할 수도 있으니 일단 남겨놓음 
	 * System.out.println(User.nowUser);
	 * 라운드 별로 총 카드 수가 달라지므로 라운드 별로 나눠야 한다.
	 */
	public int getChoice(int index) {		
		// 남은 라운드를 다 죽어도 ai가 이길 경우 (현재 라운드도 포함해서 계산해야만 하기때문에 총 20라운드지만 21에서 현재 라운드를 뺀다.)
		if(IndianPokerEdit.getUserCoin() + (21 - IndianPokerEdit.getRound()) < getCoin() - (21 - IndianPokerEdit.getRound())) return 2;
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
		else if(IndianPokerEdit.getRound() == 9) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -25) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 5) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 45) return 1;
			else return 2;
		}
		// 11라운드 경우 (Ai.aiRemoveCard.size() == 10)
		else if(IndianPokerEdit.getRound() == 10) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -30) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -10) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= 10) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 30) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 50) return 1;
			else if(User.nowUser == 7) return 1;
			else return 2;
		}
		// 12라운드 경우 (Ai.aiRemoveCard.size() == 11)
		else if(IndianPokerEdit.getRound() == 11) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -50) return 1;
			else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -25) return 1;
			else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -5) return 1;
			else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 1;
			else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 45) return 1;
			else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 55) return 1;
			else return 2;
		}
		// 13라운드 경우 (Ai.aiRemoveCard.size() == 12)
		else if(IndianPokerEdit.getRound() == 12) {
			if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -50) return 1;
			/*
			 * 13라운드 짜야함
			 */
		}
		
	}
	
	// 라운드 첫 배팅 함수
	public int betCoin() {
		Random random = new Random();
		int bet = random.nextInt(4) + 1;
		while(IndianPokerEdit.getUserBet() > bet)
			bet = random.nextInt(4) + 1;
		return bet;
	}
	
	// 추가 배팅 함수
//	public int reBetCoin() {
//		
//	}
}

