package net.skhu.IndianPoker;

import java.util.Scanner;

public class IndianPokerEdit {
	static int round = 1;	// 1라운드부터 시작
	static int userBet;		// user 배팅 코인 수 입력 받는 변수
	static int userCoin = 0;// ai가 남은 라운드를 다 죽어도 되는지 판단하기 위한 user의 현재 코인 수를 가져오는 변수
	
	public static int getUserCoin() {
		return userCoin;
	}
	
	public static int getUserBet() {
		return userBet;
	}
	
	public static int getRound() {
		return round;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int flag = 0;
		// ai, user 및 덱 생성
		Ai ai = new Ai();
		User user = new User();
		
		// 20코인으로 디폴트 세팅
		ai.setCoin(20);
		user.setCoin(20);
		// user, ai 배팅 코인 수의 총합 선언 및 초기화
		int userSum = 0;
		int aiSum = 0;
		
		// Indian 시작
		while(round <= 20) {
			if(user.getCoin() < 1 || ai.getCoin() < 1) {
				System.out.println("게임 종료");
				System.out.println("[ai코인: "+ai.getCoin()+"] [user코인: "+user.getCoin()+"]");
				if(ai.getCoin() > user.getCoin()) System.out.println("최종 결과: [ai 승]");
				else if(ai.getCoin() < user.getCoin()) System.out.println("최종 결과: [user 승]");
				else System.out.println("최종결과: 무승부");
				break;
			}
			// user 선 배팅 판단 flag%2 == 0 (if)
			Indian : if(flag % 2 == 0) {
				System.out.println();
				System.out.println("=====["+round+"라운드]=====");
				System.out.println("user 배팅 차례입니다.");
				System.out.println("[ai의 카드: "+ai.getCard(flag)+"]");
				user.getCard(flag);	// ai가 배팅하기 위해 nowUser 최신화
				System.out.println("[ai코인: "+ai.getCoin()+"] [user코인: "+user.getCoin()+"]");
				System.out.print("배팅은 [1] 포기는 [2]를 입력하세요: ");
				int choice = scan.nextInt();
				// user 초기 배팅 선택 (if)
				if(choice == 1) {
					System.out.print("배팅할 코인 개수를 입력하세요: ");
					userBet = scan.nextInt();
					userSum += userBet;
					// ai 배팅 선택 (if)
					if(ai.getChoice() == 1) {
						int aiBet = ai.betCoin();
						aiSum += aiBet;
						System.out.println("[ai가 배팅 코인: "+aiBet+"] [user가 배팅 코인: "+userBet+"]");
					}
					// ai 배팅 선택 (if) 끝
					// ai 배팅 포기 (else)
					else {
						System.out.println();
						System.out.println("ai가 배팅을 포기하였습니다. ai의 코인이 1개 줄고 새 라운드를 시작합니다.");
						System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
						ai.setCoin(ai.getCoin()-1);
						user.setCoin(user.getCoin()+1);
						userCoin = user.getCoin();
						// ai 카드에서 꺼낸 것 저장
						ai.addAiRemoveCard(ai.getCard(flag));
						++flag; ++round; userSum = 0; aiSum = 0;
						break Indian;
					}
					// ai 배팅 포기 (else) 끝
					System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
					// addBet 시작
					while(userSum != aiSum) {
						// userSum이 더 큰 경우 (if)
						addBet : if(userSum > aiSum) {
							// ai 추가 배팅 선택 (if)
							if(ai.getChoice() == 1) {
								int aiBetAgain = ai.betCoin();
								aiSum += aiBetAgain;
								System.out.println("[ai 추가배팅: "+aiBetAgain+"]");
								System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
								break addBet;
							}
							// ai 추가 배팅 선택 (if) 끝
							// ai 추가 배팅 포기 (else)
							else {
								System.out.println();
								System.out.println("ai가 추가 배팅을 포기하였습니다. 새 라운드를 시작합니다.");
								System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
								ai.setCoin(ai.getCoin()-aiSum);
								user.setCoin(user.getCoin()+aiSum);
								userCoin = user.getCoin();
								// ai 카드에서 꺼낸 것 저장
								ai.addAiRemoveCard(ai.getCard(flag));
								++flag; ++round; userSum = 0; aiSum = 0;
								break Indian;				
							}
							// ai 추가 배팅 포기 (else) 끝
						}
						// userSum이 더 큰 경우 (if) 끝
						// userSum이 더 작은 경우 (if)
						else if(userSum < aiSum) {
							System.out.println();
							System.out.print("user 추가 배팅은 [1] 포기는 [2]를 입력하세요: ");
							int choiceAgain = scan.nextInt();
							// user 추가 배팅 선택 (if)
							if(choiceAgain == 1) {
								System.out.print("배팅할 코인 개수를 입력하세요: ");
								userBet = scan.nextInt();
								userSum += userBet;
								System.out.println("[user 추가배팅: "+userBet+"]");
								System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
								break addBet;
							}
							// user 추가 배팅 선택 (if) 끝
							// user 추가 배팅 포기 (else)
							else {
								System.out.println();
								System.out.println("user가 추가 배팅을 포기하였습니다. 새 라운드를 시작합니다.");
								System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
								ai.setCoin(ai.getCoin()+userSum);
								user.setCoin(user.getCoin()-userSum);
								userCoin = user.getCoin();
								++flag; ++round; userSum = 0; aiSum = 0;
								break Indian;
							}
							// user 추가 배팅 포기 (else) 끝
						}
						// userSum이 더 작은 경우 (if) 끝
					}
					// addBet 끝
					// userSum과 aiSum이 같은 경우 (if)
					if(userSum == aiSum) {
						// ai 카드 > user 카드 (if)
						if(ai.getCard(flag) > user.getCard(flag)) {
							System.out.println();
							System.out.println(round+"라운드 결과: ai 승");
							System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
							System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
							ai.setCoin(ai.getCoin()+userSum);
							user.setCoin(user.getCoin()-userSum);
							userCoin = user.getCoin();
							// ai 카드에서 꺼낸 것 저장
							ai.addAiRemoveCard(ai.getCard(flag));
							++flag; ++round; userSum = 0; aiSum = 0;
							break Indian;
						}
						// ai카드 > user카드 (if) 끝
						// ai카드 < user카드 (else if)
						else if(ai.getCard(flag) < user.getCard(flag)) {
							System.out.println();
							System.out.println(round+"라운드 결과: user 승");
							System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
							System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
							ai.setCoin(ai.getCoin()-aiSum);
							user.setCoin(user.getCoin()+aiSum);
							userCoin = user.getCoin();
							// ai 카드에서 꺼낸 것 저장
							ai.addAiRemoveCard(ai.getCard(flag));
							++flag; ++round; userSum = 0; aiSum = 0;
							break Indian;
						}
						// ai카드 < user카드 (else if) 끝
						// ai카드 == user카드 (else)
						else {
							System.out.println();
							System.out.println(round+"라운드 결과: 무승부");
							System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
							System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
							// ai 카드에서 꺼낸 것 저장
							ai.addAiRemoveCard(ai.getCard(flag));
							++flag; ++round; userSum = 0; aiSum = 0;
							break Indian;
						}
						// ai카드 == user카드 (else) 끝
					}
					// userSum과 aiSum이 같은 경우 (if) 끝
				}
				// user 초기 배팅 선택 (if) 끝
				// user 초기 배팅 포기 (else)
				else {
					System.out.println();
					System.out.println("user가 이번 라운드 배팅을 포기하였습니다. user의 코인이 1개 줄고 새 라운드를 시작합니다.");
					System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
					ai.setCoin(ai.getCoin()+1);
					user.setCoin(user.getCoin()-1);
					userCoin = user.getCoin();
					// ai 카드에서 꺼낸 것 저장
					ai.addAiRemoveCard(ai.getCard(flag));
					++flag; ++round; userSum = 0; aiSum = 0;
					break Indian;
				}
				// user 초기 배팅 포기 (else) 끝
			}
			// user 선 배팅 판단 flag%2 == 0 (if) 끝
			// ai 선 배팅 판단 flag%2 == 1 (else)
			else {
				System.out.println();
				System.out.println("=====["+round+"라운드]=====");
				System.out.println("ai 배팅 차례입니다.");
				System.out.println("[ai의 카드: "+ai.getCard(flag)+"]");
				user.getCard(flag);	// ai가 배팅하기 위해 nowUser 최신화
				System.out.println("[ai코인: "+ai.getCoin()+"] [user코인: "+user.getCoin()+"]");
				// ai 초기 배팅 선택 (if)
				if(ai.getChoice() == 1) {
					int aiBet = ai.betCoin();
					aiSum += aiBet;
					System.out.println("[ai 배팅코인: "+aiBet+"]");
					System.out.print("배팅은 [1] 포기는 [2]를 입력하세요: ");
					int choice = scan.nextInt();
					// user 배팅 선택 (if)
					if(choice == 1) {
						System.out.print("배팅할 코인 개수를 입력하세요: ");
						userBet = scan.nextInt();
						userSum += userBet;
						System.out.println("[ai가 배팅 코인: "+aiBet+"] [user가 배팅 코인: "+userBet+"]");
						System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
						// addBet2 시작
						while(userSum != aiSum) {
							// userSum이 더 큰 경우 (if)
							addBet2 : if(userSum > aiSum) {
								// ai 추가 배팅 선택 (if)
								if(ai.getChoice() == 1) {
									int aiBetAgain = ai.betCoin();
									aiSum += aiBetAgain;
									System.out.println("[ai 추가배팅: "+aiBetAgain+"]");
									System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
									break addBet2;
								}
								// ai 추가 배팅 선택 (if) 끝
								// ai 추가 배팅 포기 (else)
								else {
									System.out.println();
									System.out.println("ai가 추가 배팅을 포기하였습니다. 새 라운드를 시작합니다.");
									System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
									ai.setCoin(ai.getCoin()-aiSum);
									user.setCoin(user.getCoin()+aiSum);
									userCoin = user.getCoin();
									// ai 카드에서 꺼낸 것 저장
									ai.addAiRemoveCard(ai.getCard(flag));
									++flag; ++round; userSum = 0; aiSum = 0;
									break Indian;				
								}
								// ai 추가 배팅 포기 (else) 끝
							}
							// userSum이 더 큰 경우 (if) 끝
							// userSum이 더 작은 경우 (if)
							else if(userSum < aiSum) {
								System.out.print("user 추가 배팅은 [1] 포기는 [2]를 입력하세요: ");
								int choiceAgain = scan.nextInt();
								// user 추가 배팅 선택 (if)
								if(choiceAgain == 1) {
									System.out.print("배팅할 코인 개수를 입력하세요: ");
									userBet = scan.nextInt();
									userSum += userBet;
									System.out.println("[user 추가배팅: "+userBet+"]");
									System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
									break addBet2;
								}
								// user 추가 배팅 선택 (if) 끝
								// user 추가 배팅 포기 (else)
								else {
									System.out.println();
									System.out.println("user가 추가 배팅을 포기하였습니다. 새 라운드를 시작합니다.");
									System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
									ai.setCoin(ai.getCoin()+userSum);
									user.setCoin(user.getCoin()-userSum);
									userCoin = user.getCoin();
									// ai 카드에서 꺼낸 것 저장
									ai.addAiRemoveCard(ai.getCard(flag));
									++flag; ++round; userSum = 0; aiSum = 0;
									break Indian;
								}
								// user 추가 배팅 포기 (else) 끝
							}
							// userSum이 더 작은 경우 (if) 끝
						}
						// addBet2 끝	
					}
					// user 배팅 선택 (if) 끝
					// user 배팅 포기 (else)
					else {
						System.out.println();
						System.out.println("user가 배팅을 포기하였습니다. user의 코인이 1개 줄고 새 라운드를 시작합니다.");
						System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
						ai.setCoin(ai.getCoin()+1);
						user.setCoin(user.getCoin()-1);
						userCoin = user.getCoin();
						// ai 카드에서 꺼낸 것 저장
						ai.addAiRemoveCard(ai.getCard(flag));
						++flag; ++round; userSum = 0; aiSum = 0;
						break Indian;
					}
					// user 배팅 포기 (else) 끝
					System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
					
					// userSum과 aiSum이 같은 경우 (if)
					if(userSum == aiSum) {
						// ai 카드 > user 카드 (if)
						if(ai.getCard(flag) > user.getCard(flag)) {
							System.out.println();
							System.out.println(round+"라운드 결과: ai 승");
							System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
							System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
							ai.setCoin(ai.getCoin()+userSum);
							user.setCoin(user.getCoin()-userSum);
							userCoin = user.getCoin();
							// ai 카드에서 꺼낸 것 저장
							ai.addAiRemoveCard(ai.getCard(flag));
							++flag; ++round; userSum = 0; aiSum = 0;
							break Indian;
						}
						// ai카드 > user카드 (if) 끝
						// ai카드 < user카드 (else if)
						else if(ai.getCard(flag) < user.getCard(flag)) {
							System.out.println();
							System.out.println(round+"라운드 결과: user 승");
							System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
							System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
							ai.setCoin(ai.getCoin()-aiSum);
							user.setCoin(user.getCoin()+aiSum);
							userCoin = user.getCoin();
							// ai 카드에서 꺼낸 것 저장
							ai.addAiRemoveCard(ai.getCard(flag));
							++flag; ++round; userSum = 0; aiSum = 0;
							break Indian;
						}
						// ai카드 < user카드 (else if) 끝
						// ai카드 == user카드 (else)
						else {
							System.out.println();
							System.out.println(round+"라운드 결과: 무승부");
							System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
							System.out.println("[userSum = "+userSum+"] [aiSum = "+aiSum+"]");
							// ai 카드에서 꺼낸 것 저장
							ai.addAiRemoveCard(ai.getCard(flag));
							++flag; ++round; userSum = 0; aiSum = 0;
							break Indian;
						}
						// ai카드 == user카드 (else) 끝
					}
					// userSum과 aiSum이 같은 경우 (if) 끝
				}
				// ai 초기 배팅 선택 (if) 끝
				// ai 초기 배팅 포기 (else)
				else {
					System.out.println();
					System.out.println("ai가 이번 라운드 배팅을 포기하였습니다. ai의 코인이 1개 줄고 새 라운드를 시작합니다.");
					System.out.println("[ai카드: "+ai.getCard(flag)+"] [user카드: "+user.getCard(flag)+"]");
					ai.setCoin(ai.getCoin()-1);
					user.setCoin(user.getCoin()+1);
					userCoin = user.getCoin();
					// ai 카드에서 꺼낸 것 저장
					ai.addAiRemoveCard(ai.getCard(flag));
					++flag; ++round; userSum = 0; aiSum = 0;
					break Indian;
				}
				// ai 초기 배팅 포기 (else) 끝
				
			}
			// ai 선 배팅 판단 flag%2 == 1 (else) 끝
		}
		// Indian 끝
		if(round > 20) {
			System.out.println("게임 종료");
			System.out.println("[ai코인: "+ai.getCoin()+"] [user코인: "+user.getCoin()+"]");
			if(ai.getCoin() > user.getCoin()) System.out.println("최종 결과: [ai 승]");
			else if(ai.getCoin() < user.getCoin()) System.out.println("최종 결과: [user 승]");
			else System.out.println("최종결과: 무승부");
		}
	}

}
