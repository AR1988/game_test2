package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameBoard {
    private static final Logger log = LogManager.getLogger(GameBoard.class);

    private final int bourdHigh;
    private final int boardLength;

    public GameBoard(int high, int length) {
        if (!isEven(high)) {
            throw new IllegalArgumentException("Высота поля должно быть четным числом");
        }
        if (!isEven(length)) {
            throw new IllegalArgumentException("Ширина поля должно быть четным числом");
        }
        this.bourdHigh = high;
        this.boardLength = length;
    }

    private boolean isEven(int number) {
        return number % 2 == 0;
    }

    public Card[][] fillFieldWithCards() {
        int maxPossibleCards = Card.icons.length;

        Card[][] cards = new Card[bourdHigh][boardLength];
        int maxCard = (cards[0].length * cards.length) / 2;

        if (maxCard > maxPossibleCards) {
            log.error("Упс, у нас нет столько карточек для заполнения поля. Максимальное количество карточек {}", maxPossibleCards);
        }

        final Set<Card> cardList = new HashSet<>(maxCard);
        while (cardList.size() != maxCard) {
            cardList.add(new Card());
        }

        for (Card card : cardList) {
            placeSymbol(cards, card);
            placeRepeatSymbol(cards, card);
        }

        return cards;
    }

    public Card[][] fillEmptyField() {
        final Card[][] emptyField = new Card[bourdHigh][boardLength];
        for (int i = 0; i < emptyField.length; i++) {
            for (int j = 0; j < emptyField[i].length; j++) {
                emptyField[i][j] = new Card('#');
            }
        }

        return emptyField;
    }

    public Card[][] openField(Card[][] emptyField, Card[][] cards, int row, int column) {
        final Card[][] arrayCopy = Arrays.copyOf(emptyField, emptyField.length);
        arrayCopy[row][column] = cards[row][column];
        return arrayCopy;
    }

    private void placeSymbol(Card[][] cards, Card card) {
        final Random random = new Random();
        while (true) {
            int row = random.nextInt(cards.length);
            int column = random.nextInt(cards[0].length);

            final Card cartAtPostion = cards[row][column];
            if (cartAtPostion == null) {
                cards[row][column] = card;
                return;
            }
        }
    }

    private void placeRepeatSymbol(Card[][] cards, Card card) {
        placeSymbol(cards, card);
    }

    public void printBoard(Card[][] cardBoard) {
        String boardToString = BoardUtils.boardToString(cardBoard);
        System.out.println(boardToString);
    }

}

