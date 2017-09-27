package net.skhu.IndianPoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BeforeAi{
	private int coin;
	List<Integer> aiPokerDeck = new ArrayList<Integer>();
	
	static public List<Integer> aiRemoveCard = new ArrayList<Integer>();	// ai 꺼낸 카드 저장 리스트
	
	public BeforeAi() {
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
	public int getChoice(int index) {
		// 라운드 별로 총 카드 수가 달라지므로 라운드 별로 나눠야 한다.
		// System.out.println(User.nowUser);
		if(User.nowUser == 1) return 1;
		else if(User.nowUser == 2 && this.detailPer(User.nowUser) >= -60) return 1;
		else if(User.nowUser == 3 && this.detailPer(User.nowUser) >= -40) return 1;
		else if(User.nowUser == 4 && this.detailPer(User.nowUser) >= -20) return 1;
		else if(User.nowUser == 5 && this.detailPer(User.nowUser) >= 25) return 1;
		else if(User.nowUser == 6 && this.detailPer(User.nowUser) >= 35) return 1;
		else if(User.nowUser == 7 && this.detailPer(User.nowUser) >= 40) return 1;
		else if(User.nowUser == 8 && this.detailPer(User.nowUser) >= 70) return 1;
		else if(User.nowUser == 9 && this.detailPer(User.nowUser) >= 80) return 1;
		else return 2;
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

