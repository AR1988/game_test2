package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MemoryGame {

    private static final int BOARD_SIZE = 4;
    private static final char HIDDEN_CARD = '*';

    private char[][] board;
    private char[][] cards;
    private int lastOpenedRow1, lastOpenedCol1, lastOpenedRow2, lastOpenedCol2;

    public MemoryGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        cards = generateCards();
        initializeBoard();
    }

    private char[][] generateCards() {
        char[] values = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        char[][] cards = new char[BOARD_SIZE][BOARD_SIZE];
        int cardCount = BOARD_SIZE * BOARD_SIZE / 2;

        for (int i = 0; i < cardCount; i++) {
            int row, col;
            do {
                row = new Random().nextInt(BOARD_SIZE);
                col = new Random().nextInt(BOARD_SIZE);
            } while (cards[row][col] != 0);

            int valueIndex = i / 2;
            cards[row][col] = values[valueIndex];
            cards[BOARD_SIZE - row - 1][BOARD_SIZE - col - 1] = values[valueIndex];
        }

        return cards;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Arrays.fill(board[i], HIDDEN_CARD);
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();

            System.out.print("Введите номер строки: ");
            int row = scanner.nextInt() - 1;
            if (row < 0 || row >= BOARD_SIZE) {
                System.out.println("Некорректный номер строки!");
                continue;
            }

            System.out.print("Введите номер столбца: ");
            int col = scanner.nextInt() - 1;
            if (col < 0 || col >= BOARD_SIZE) {
                System.out.println("Некорректный номер столбца!");
                continue;
            }

            if (board[row][col] != HIDDEN_CARD) {
                System.out.println("Эта карта уже открыта! Введите другие координаты.");
                continue;
            }

            board[row][col] = cards[row][col];
            printBoard();

            if (lastOpenedRow1 == -1) {
                lastOpenedRow1 = row;
                lastOpenedCol1 = col;
            } else {
                lastOpenedRow2 = row;
                lastOpenedCol2 = col;

                if (cards[lastOpenedRow1][lastOpenedCol1] != cards[lastOpenedRow2][lastOpenedCol2]) {
                    // Если карты не совпали, закрываем их
                    board[lastOpenedRow1][lastOpenedCol1] = HIDDEN_CARD;
                    board[lastOpenedRow2][lastOpenedCol2] = HIDDEN_CARD;
                }

                lastOpenedRow1 = lastOpenedCol1 = lastOpenedRow2 = lastOpenedCol2 = -1;
            }

            if (isGameOver()) {
                System.out.println("Поздравляем! Вы открыли все карты. Игра завершена!");
                break;
            }
        }
    }

    private boolean isGameOver() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == HIDDEN_CARD) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printBoard() {
        System.out.println("------ Memory Game ------");
        System.out.print("   ");
        for (int i = 1; i <= BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((i + 1) + " |");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        MemoryGame game = new MemoryGame();
        game.play();
    }
}
