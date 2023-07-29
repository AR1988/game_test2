package org.example;

import java.util.Scanner;

public class MemoGame {

    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Game game = new Game();

        System.out.println("Введите размер поля:");
        System.out.println("Высота (до 18)");
        int BOARD_HIGH = scanner.nextInt();
        System.out.println("Ширина (до 18)");
        int BOARD_WEIGHT = scanner.nextInt();

        game.startGame(BOARD_HIGH, BOARD_WEIGHT);

        int cardCount = 0;
        while (true) {
            SelectedCard selectedCard = consoleInput(BOARD_HIGH, BOARD_WEIGHT);
            game.openCard(cardCount, selectedCard);
            cardCount++;
            System.out.println("открыта карточка " + cardCount + " " + selectedCard);

            if (cardCount == 2) {
                String s = "";
                while (!s.equals("y")) {
                    System.out.println("() введите y для продолжения");
                    System.out.println("() введите n для завершения игры");
                    System.out.println("() введите b что бы открыть карту");
                    s = scanner.nextLine();

                    switch (s) {
                        case "n" -> {
                            System.out.println("game over");
                            return;
                        }
                        case "y" -> {
                        }
                        case "b" -> {
                            game.printBoard();
                        }
                    }
                    System.out.println("Продолжаем игру");
                }
                cardCount = 0;
                System.out.println("Следующий шаг");
                System.out.println("_".repeat(20));
                game.clearBoard();
            }
        }
    }

    private static SelectedCard consoleInput(int maxRow, int maxColumn) {
        int rowIndex, columnIndex;
        while (true) {
            System.out.println("Введите номер строки от 1 до " + maxRow);
            String row = scanner.nextLine();
            try {
                rowIndex = Integer.parseInt(row);
                if (rowIndex > maxRow) {
                    System.out.println("введите число от 1 до" + maxRow);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число");
                continue;
            }

            System.out.println("Введите номер колонки от 1 до " + maxColumn);
            String column = scanner.nextLine();
            try {
                columnIndex = Integer.parseInt(column);
                if (columnIndex > maxColumn) {
                    System.out.println("введите число от 1 до" + maxColumn);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число");
                continue;
            }
            break;
        }

        return new SelectedCard(--rowIndex, --columnIndex);
    }
}
