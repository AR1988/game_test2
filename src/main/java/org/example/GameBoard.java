package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GameBoard {

    int bourdHigh;
    int boardLength;

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
            throw new IllegalArgumentException("Упс, у нас нет столько карточек для заполнения поля. Максимальное количесво карточек " + maxPossibleCards);
        }

        Set<Card> cardList = new HashSet<>(maxCard);
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
        Card[][] emptyField = new Card[bourdHigh][boardLength];
        for (int i = 0; i < emptyField.length; i++) {
            for (int j = 0; j < emptyField[i].length; j++) {
                emptyField[i][j] = new Card('#');
            }
        }

        return emptyField;
    }

    public Card[][] openField(Card[][] emptyField, Card[][] cards, int row, int column) {
        Card[][] arrayCopy = Arrays.copyOf(emptyField, emptyField.length);
        arrayCopy[row][column] = cards[row][column];
        return arrayCopy;
    }

    private void placeSymbol(Card[][] cards, Card card) {
        Random random = new Random();
        while (true) {
            int row = random.nextInt(cards.length);
            int column = random.nextInt(cards[0].length);
            Card cartAtPostion = cards[row][column];
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
        int cellWidth = 4;
        System.out.println("------ Memory Game ------");

        // Выводим номера колонок
        System.out.print(String.format("%" + (cellWidth + 1) + "s", " "));
        for (int i = 1; i <= cardBoard[0].length; i++) {
            System.out.print(String.format("%" + cellWidth + "s|", i));
        }
        System.out.println();

        for (int i = 0; i < cardBoard.length; i++) {
            System.out.print(String.format("%" + cellWidth + "d|", i + 1));
            for (int j = 0; j < cardBoard[0].length; j++) {
                System.out.print(String.format("%" + cellWidth + "s|", cardBoard[i][j]));
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }

    public void printBoardToFile(Card[][] cardBoard) {
        int cellWidth = 4;
        String dirPath = "games";
        File folder = new File(dirPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String fileNameSuffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        String fileName = fileNameSuffix + "_memogame.txt";

        File file = new File(folder, fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("------ Memory Game ------");

            // Выводим номера колонок
            writer.print(String.format("%" + (cellWidth + 1) + "s", " "));
            for (int i = 1; i <= cardBoard[0].length; i++) {
                writer.print(String.format("%" + cellWidth + "s|", i));
            }
            writer.println();

            for (int i = 0; i < cardBoard.length; i++) {
                writer.print(String.format("%" + cellWidth + "d|", i + 1));
                for (int j = 0; j < cardBoard[0].length; j++) {
                    writer.print(String.format("%" + cellWidth + "s|", cardBoard[i][j]));
                }
                writer.println();
            }
            writer.println("-------------------------");

            System.out.println("Board printed to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

