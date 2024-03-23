import java.util.ArrayList;

public class BlackjackGame{
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    public BlackjackDealer theDealer;
    public BlackjackGameLogic gameLogic;
    double currentBet;
    double money;
    double totalWinnings;
    BlackjackGame(){
        currentBet = 0.0;
        totalWinnings = 0.0;
        gameLogic = new BlackjackGameLogic();
        theDealer = new BlackjackDealer();
        playerHand = new ArrayList<>();
        bankerHand = new ArrayList<>();
    }
    public double evaluateWinnings(){
        String result = gameLogic.whoWon(playerHand, bankerHand);
        //automatically draw no matter what if both hands are equal
        if("push".equals(result) || (playerHasBlackjack() && dealerHasBlackjack())){
            money += currentBet;
            return 0;
        }
        else if("player".equals(result) && playerHasBlackjack()){
            totalWinnings = 2.5*currentBet;
            money += 2.5*currentBet;
        }
        else if("player".equals(result)){
            totalWinnings = 2*currentBet;
            money += totalWinnings;
        }
        else if("dealer".equals(result)) {
            totalWinnings = -currentBet;
        }

        return totalWinnings;
    }
    //helper functions below
    //helper function for when the player presses "Hit"
    public Card hitCard(){
        Card newCard = theDealer.drawOne();
        playerHand.add(newCard);
        return newCard;
    }
    //helper function for when it's the dealer's turn to Hit
    public Card dealerHitCard(){
        Card newCard = theDealer.drawOne();
        bankerHand.add(newCard);
        return newCard;
    }
    //checks when a player busts
    public boolean checkPlayerBust(){
        return gameLogic.handTotal(playerHand) > 21;
    }
    //checks for when the dealer busts
    public boolean checkDealerBust(){
        return gameLogic.handTotal(bankerHand) > 21;
    }
    //sets the bet and changes the player's current money accordingly
    public void setBet(double bet){
        currentBet = bet;
        totalWinnings = 0;
        money -= bet;
    }
    //checks if the player has Blackjack
    public boolean playerHasBlackjack(){
        return gameLogic.handTotal(playerHand) == 21 && playerHand.size() == 2;
    }
    //checks if the dealer has Blackjack
    public boolean dealerHasBlackjack(){
        return gameLogic.handTotal(bankerHand) == 21 && bankerHand.size() == 2;
    }
    //prepares the next round with clean hands and a fresh deck
    public void newRound(){
        playerHand.clear();
        bankerHand.clear();

        theDealer.generateDeck();
        theDealer.shuffleDeck();

        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();
    }
}
