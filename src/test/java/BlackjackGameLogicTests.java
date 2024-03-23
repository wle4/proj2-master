import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
class BlackjackGameLogicTests {

    //------TESTS FOR handTotal()------
    @Test
    void handTotalNoAcesTest() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("hearts", 10));
        hand.add(new Card("spades", 5));
        assertEquals(15, gameLogic.handTotal(hand), "BlackjackGameLogic: Incorrect total for hand with no aces");
    }
    @Test
    void handTotalWithAcesTest() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("hearts", 10));
        hand.add(new Card("spades", 1)); // Ace
        assertEquals(21, gameLogic.handTotal(hand), "BlackjackGameLogic: Incorrect total for hand with aces");
    }
    @Test
    void handTotalWithMultipleAcesTest() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("hearts", 9));
        hand.add(new Card("spades", 1));
        hand.add(new Card("diamonds", 1));
        assertEquals(21, gameLogic.handTotal(hand), "BlackjackGameLogic: Incorrect total for hand with multiple aces");
    }
    //------END OF TESTS FOR handTotal()------

    //------TESTS FOR evaluateBankerDraw()------
    @Test
    void evaluateBankerDraw16Test() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("hearts", 10));
        hand.add(new Card("spades", 6));
        assertTrue(gameLogic.evaluateBankerDraw(hand), "BlackjackGameLogic: Dealer should draw another card with total of 16");
    }
    @Test
    void evaluateBankerDraw17Test() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("hearts", 10));
        hand.add(new Card("spades", 7));
        assertFalse(gameLogic.evaluateBankerDraw(hand), "BlackjackGameLogic: Dealer should not draw another card with total of 17");
    }
    //------END OF TESTS FOR evaluateBankerDraw()------

    //------TESTS FOR whoWon()------
    @Test
    void playerWin(){
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();

        ArrayList<Card> myCards = new ArrayList<>();
        ArrayList<Card> dealerCards = new ArrayList<>();

        myCards.add(new Card("hearts", 10));
        myCards.add(new Card("spade", 10));

        dealerCards.add(new Card("hearts", 11));
        dealerCards.add(new Card("spade", 9));

        assertEquals("player", gameLogic.whoWon(myCards, dealerCards), "BlackjackGameLogic: Player has 20 and Dealer has 19, but player does not win");
    }
    @Test
    void playerWinWithAces(){
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();

        ArrayList<Card> myCards = new ArrayList<>();
        ArrayList<Card> dealerCards = new ArrayList<>();

        myCards.add(new Card("hearts", 1));
        myCards.add(new Card("spade", 10));

        dealerCards.add(new Card("hearts", 11));
        dealerCards.add(new Card("spade", 9));

        assertEquals("player", gameLogic.whoWon(myCards, dealerCards), "BlackjackGameLogic: Player has 21 (with ace) and Dealer has 19, but player does not win");
    }
    @Test
    void playerWinWithDealerAces(){
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();

        ArrayList<Card> myCards = new ArrayList<>();
        ArrayList<Card> dealerCards = new ArrayList<>();

        myCards.add(new Card("hearts", 1));
        myCards.add(new Card("spade", 10));

        dealerCards.add(new Card("hearts", 9));
        dealerCards.add(new Card("spade", 1));

        assertEquals("player", gameLogic.whoWon(myCards, dealerCards), "BlackjackGameLogic: Player has 21 (with ace) and Dealer has 20 (with ace), but player does not win");
    }
    @Test
    void dealerWin(){
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();

        ArrayList<Card> myCards = new ArrayList<>();
        ArrayList<Card> dealerCards = new ArrayList<>();

        myCards.add(new Card("hearts", 9));
        myCards.add(new Card("spade", 10));

        dealerCards.add(new Card("hearts", 11));
        dealerCards.add(new Card("spade", 10));

        assertEquals("dealer", gameLogic.whoWon(myCards, dealerCards), "BlackjackGameLogic: Player has 19 and Dealer has 20, but dealer does not win");
    }
    @Test
    void dealerWinWithAces(){
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();

        ArrayList<Card> myCards = new ArrayList<>();
        ArrayList<Card> dealerCards = new ArrayList<>();

        myCards.add(new Card("hearts", 9));
        myCards.add(new Card("spade", 10));

        dealerCards.add(new Card("hearts", 1));
        dealerCards.add(new Card("spade", 9));

        assertEquals("dealer", gameLogic.whoWon(myCards, dealerCards), "BlackjackGameLogic: Player has 19 and Dealer has 20 (with ace), but dealer does not win");
    }
    @Test
    void dealerWinWithPlayerAces(){
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();

        ArrayList<Card> myCards = new ArrayList<>();
        ArrayList<Card> dealerCards = new ArrayList<>();

        myCards.add(new Card("hearts", 1));
        myCards.add(new Card("spade", 8));

        dealerCards.add(new Card("hearts", 9));
        dealerCards.add(new Card("spade", 1));

        assertEquals("dealer", gameLogic.whoWon(myCards, dealerCards), "BlackjackGameLogic: Player has 19 (with ace) and Dealer has 20 (with ace), but dealer does not win");
    }
    @Test
    void playerDealerDraw(){
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();

        ArrayList<Card> myCards = new ArrayList<>();
        ArrayList<Card> dealerCards = new ArrayList<>();

        myCards.add(new Card("hearts", 1));
        myCards.add(new Card("spade", 10));

        dealerCards.add(new Card("hearts", 11));
        dealerCards.add(new Card("spade", 1));

        assertEquals("push", gameLogic.whoWon(myCards, dealerCards), "BlackjackGameLogic: Player and Dealer should draw when they both have the same value cards");
    }
    //------END OF TESTS FOR whoWon()------
}