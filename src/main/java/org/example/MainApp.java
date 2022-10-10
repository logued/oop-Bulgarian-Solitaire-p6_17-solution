package org.example;                                    // OOP 2022

/*
From: Big Java by C.Horstman -  modified solution October 2022       ArrayList demo.

In this assignment, you will model the (game theory) game of Bulgarian Solitaire.
The game starts with 45 card piles.
(They need not be playing cardPiles. Unmarked index card piles work just as well.)

Randomly divide the cards into some number of piles of random size. For example,
you might start with piles of size 20, 5, 1, 9, and 10. In each round, you take one
card from each pile, forming a new pile with these cardPiles.

For example, the sample
starting configuration would be transformed into piles of size 19, 4, 8, 9, and 5.
The solitaire is over when the piles have sizes 1, 2, 3, 4, 5, 6, 7, 8, and 9, in some order.
(It can be shown (mathematically) that you always end up with such a configuration.)

In your program,
produce a random starting configuration and print it. Then keep applying the solitaire
steps and print the result. Stop when the solitaire final configuration is reached.

https://en.wikipedia.org/wiki/Bulgarian_solitaire

https://github.com/logued/oop-Bulgarian-Solitaire-p6_17-solution.git

*/

import java.util.ArrayList;
import java.util.Random;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("***** Bulgarian Solitaire - Card Came Simulation *****");
        MainApp app = new MainApp();
        app.start();
    }
    public void start() {
        ArrayList<Integer> cardPiles = initializeCardPiles();  // will create and initialize card piles
        printCardPiles(cardPiles);

        while (!isGameOver(cardPiles)) {        // repeat the steps until game is over
            makeNewPile(cardPiles);
            printCardPiles(cardPiles);
        }

        System.out.println("The Game is finished. The card piles are:");
        printCardPiles(cardPiles);
    }

    public void makeNewPile(ArrayList<Integer> cardPiles) {
        // create card count for the new pile
        int newPileCardCount = 0;    // new pile has no cards initially

        // reduce each card pile by one
        for (int i = 0; i < cardPiles.size(); i++) {
            int cardCount = cardPiles.get(i);   // get card count for this pile
            cardPiles.set(i, cardCount - 1);    // set the pile to have one less card
            newPileCardCount += 1;
        }

        cardPiles.add(newPileCardCount);    // add the new pile with appropriate count

        // Remove empty Piles:
        // if a pile has been reduced to zero cards,
        // then we must remove that pile from the ArrayList.
        for (int i = 0; i < cardPiles.size(); i++) {
            int cardCount = cardPiles.get(i);

            if (cardCount == 0) {
                cardPiles.remove(i);
                i -= 1;     // this line corrects our index by subtracting 1 from it.
                            // If we don't do this, our index will be one ahead because of the removal,
                            // and the loop index will skip over element
            }
        }
    }

    public boolean isGameOver(ArrayList<Integer> cardPiles) {
        boolean isFinished = true;

        // if we have 9 piles, then check if the list of card piles contain 1,2,3,4,5,6,7,8,9 cards (not necessarily in order)
        if (cardPiles.size() == 9) {
            for (int i = 0; i < cardPiles.size(); i++) {
                if (!(cardPiles.contains(i + 1))) {     // as soon as we find one number missing, then we know we are not finished
                    isFinished = false;
                    break;
                }
            }
        } else {            // if we do not have 9 card piles yet, then we are not finished
            isFinished = false;
        }

        return isFinished;
    }

    public ArrayList<Integer> initializeCardPiles() {
        ArrayList<Integer> cardPilesList = new ArrayList<>();
        Random random = new Random();

        int numCardsRemaining = 45;     // game works with 45 cards (45 is a triangular number-see Wikipedia)
        while (numCardsRemaining > 0) {
            int randomNumber = random.nextInt(numCardsRemaining) + 1;   // get random number of cards for a pile
            cardPilesList.add(randomNumber);    // add the pile of cards to the list
            numCardsRemaining = numCardsRemaining - randomNumber;   // reduce remaining number of cards
        }

        return cardPilesList;   // return reference to the initialized arraylist of card piles
    }

    public void printCardPiles(ArrayList<Integer> cardPiles) {
        for (int i = 0; i < cardPiles.size(); i++) {
            System.out.print(cardPiles.get(i) + " ");
        }

        System.out.println();
    }
}