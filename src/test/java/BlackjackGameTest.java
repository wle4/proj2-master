import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackGameTest {

    //------TESTS FOR GAME------
    // Test for evaluating money winnings
    @Test
    void evaluateDraw() {
        BlackjackGame game = new BlackjackGame();
        Card playercard1 = new Card("spades", 10);
        Card playercard2 = new Card("clubs", 7);
        Card bankercard1 = new Card("hearts", 10);
        Card bankercard2 = new Card("diamonds", 7);
        game.playerHand.add(playercard1);
        game.playerHand.add(playercard2);
        game.bankerHand.add(bankercard1);
        game.bankerHand.add(bankercard2);
        game.money = 500;
        game.setBet(200);
        game.evaluateWinnings();
        assertEquals(500, game.money, "Draw evaluated winnings incorrect");
    }

    @Test
    void evaluatePlayerWin() {
        BlackjackGame game = new BlackjackGame();
        Card playercard1 = new Card("spades", 10);
        Card playercard2 = new Card("clubs", 10);
        Card bankercard1 = new Card("hearts", 10);
        Card bankercard2 = new Card("diamonds", 8);
        game.playerHand.add(playercard1);
        game.playerHand.add(playercard2);
        game.bankerHand.add(bankercard1);
        game.bankerHand.add(bankercard2);
        game.money = 500; //300
        game.setBet(200);
        game.evaluateWinnings();
        assertEquals(700, game.money, "Player win evaluated winnings incorrect");
    }

    @Test
    void evalutePlayerBlackjack() {
        BlackjackGame game = new BlackjackGame();
        Card playercard1 = new Card("spades", 1);
        Card playercard2 = new Card("clubs", 10);
        Card bankercard1 = new Card("hearts", 10);
        Card bankercard2 = new Card("diamonds", 8);
        game.money = 500; //300
        game.setBet(200);
        game.playerHand.add(playercard1);
        game.playerHand.add(playercard2);
        game.bankerHand.add(bankercard1);
        game.bankerHand.add(bankercard2);
        game.evaluateWinnings();
        assertEquals(800, game.money, "Player blackjack evaluated winnings incorrect");
    }

    @Test
    void evaluateDealerWin() {
        BlackjackGame game = new BlackjackGame();
        Card playercard1 = new Card("spades", 10);
        Card playercard2 = new Card("clubs", 7);
        Card bankercard1 = new Card("hearts", 10);
        Card bankercard2 = new Card("diamonds", 10);
        game.playerHand.add(playercard1);
        game.playerHand.add(playercard2);
        game.bankerHand.add(bankercard1);
        game.bankerHand.add(bankercard2);
        game.money = 500; //300
        game.setBet(200);
        game.evaluateWinnings();
        assertEquals(300, game.money, "Dealer win evaluated winnings incorrect");
    }

    // Check for hit card with dealer and player
    @Test
    void testHitCard() {
        BlackjackGame game = new BlackjackGame();
        game.theDealer.generateDeck();
        game.hitCard();
        assertEquals(game.playerHand.size(), 1, "hitCard() expected to update playerHand");
    }

    @Test
    void testDealerHitCard() {
        BlackjackGame game = new BlackjackGame();
        game.theDealer.generateDeck();
        game.dealerHitCard();
        assertEquals(game.bankerHand.size(), 1, "dealerHitCard() expected to update bankerHand");
    }

    // Tests for player or dealer bust
    @Test
    void testPlayerBust() {
        BlackjackGame game = new BlackjackGame();
        Card playercard1 = new Card("spades", 9);
        Card playercard2 = new Card("clubs", 7);
        Card playercard3 = new Card("spades", 6);
        game.playerHand.add(playercard1);
        game.playerHand.add(playercard2);
        game.playerHand.add(playercard3);
        assertTrue(game.checkPlayerBust(), "Player expected to bust");
    }

    @Test
    void testDealerBust() {
        BlackjackGame game = new BlackjackGame();
        Card bankercard1 = new Card("spades", 9);
        Card bankercard2 = new Card("clubs", 7);
        Card bankercard3 = new Card("spades", 6);
        game.bankerHand.add(bankercard1);
        game.bankerHand.add(bankercard2);
        game.bankerHand.add(bankercard3);
        assertTrue(game.checkDealerBust(), "Dealer expected to bust");
    }

    // setBet() test
    @Test
    void testSetBet() {
        BlackjackGame game = new BlackjackGame();
        game.money = 500;
        game.setBet(200);
        assertEquals(game.money, 300, "Money not updated correctly when bet set");
        assertEquals(game.currentBet, 200, "Current bet not set");
    }

    // Test for testing player/dealer when hit blackjack
    @Test
    void testPlayerBlackjack() {
        BlackjackGame game = new BlackjackGame();
        Card playercard1 = new Card("spades", 1);
        Card playercard2 = new Card("clubs", 10);
        game.playerHand.add(playercard1);
        game.playerHand.add(playercard2);
        assertTrue(game.playerHasBlackjack(), "Player expected to hit blackjack");
    }

    @Test
    void testDealerBlackjack() {
        BlackjackGame game = new BlackjackGame();
        Card bankercard1 = new Card("spades", 1);
        Card bankercard2 = new Card("hearts", 10);
        game.bankerHand.add(bankercard1);
        game.bankerHand.add(bankercard2);
        assertTrue(game.dealerHasBlackjack(), "Dealer expected to hit blackjack");
    }

    // Test new round and see if old hand is different than new hand
    @Test
    void testNewRound() {
        BlackjackGame game = new BlackjackGame();
        Card playercard1 = new Card("spades", 1);
        Card playercard2 = new Card("clubs", 10);
        Card bankercard1 = new Card("spades", 1);
        Card bankercard2 = new Card("hearts", 10);
        game.playerHand.add(playercard1);
        game.playerHand.add(playercard2);
        game.bankerHand.add(bankercard1);
        game.bankerHand.add(bankercard2);
        ArrayList<Card> oldPlayerHand = new ArrayList<>();
        ArrayList<Card> oldDealerhand = new ArrayList<>();
        oldPlayerHand.addAll(game.playerHand);
        oldDealerhand.addAll(game.bankerHand);
        game.newRound();
        assertNotEquals(game.playerHand, oldPlayerHand, "Player hand not updated after new round");
        assertNotEquals(game.bankerHand, oldDealerhand, "Dealer hand not updated after new round");
    }

}