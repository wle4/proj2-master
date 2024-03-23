import java.util.ArrayList;

public class BlackjackGameLogic {
    public String whoWon(ArrayList<Card> playerHand1, ArrayList<Card> dealerHand) {
        int playerSum = handTotal(playerHand1); //finds total for the player
        int dealerSum = handTotal(dealerHand); //finds total for the dealer

        if(playerSum > dealerSum && playerSum < 22){
            return "player";
        }
        else if(dealerSum > 21){
            return "player";
        }
        else if(playerSum == dealerSum){
            return "push";
        }
        else{
            return "dealer";
        }
    }
    public int handTotal(ArrayList<Card> hand){
        int total = 0;
        int numAces = 0;
        for(Card card:hand){
            if(card.value == 1){
                numAces++;
                total += 11;
            }
            else if(card.value>10){
                total += 10;
            }
            else {
                total += card.value;
            }
        }
        //handles the aces separately since they can have either 1 or 11
        while(numAces > 0 && total > 21){
            numAces--;
            total-=10;
        }
        return total;
    }
    public boolean evaluateBankerDraw(ArrayList<Card> hand){
        int dealerSum = 0;
        int numAces = 0;

        for(Card card:hand){
            if(card.value == 1){
                numAces++;
            }
            else if(card.value > 10){
                dealerSum += 10;
            }
            else {
                dealerSum += card.value;
            }
        }

        //handles the aces separately since they can have either 1 or 11
        for(int i = 0; i < numAces; i++){
            if(dealerSum + 11 <= 21){
                dealerSum+=11;
            }
            else{
                dealerSum++;
            }
        }

        return dealerSum < 17;
    }
}

