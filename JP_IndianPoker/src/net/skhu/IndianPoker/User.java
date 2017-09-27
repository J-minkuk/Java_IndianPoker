package net.skhu.IndianPoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
	private int coin;
	static public int nowUser;		// 현재 UserCard 를 Ai클래스에서 이용하기 위한 변수
	List<Integer> userPokerDeck = new ArrayList<Integer>();
	
	public User() {
		int[] deck = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5,
				  	  6, 6, 7, 7, 8, 8, 9, 9, 10, 10 };
	
		for(int i=0; i < deck.length; ++i)
			userPokerDeck.add(deck[i]);
		Collections.shuffle(userPokerDeck);
	}
	
	public int getCard(int index) {
		nowUser = userPokerDeck.get(index);
		return nowUser;
	}
	
	public int getCoin() {
		return coin;
	}
	
	public void setCoin(int coin) {
		this.coin = coin;
	}
}