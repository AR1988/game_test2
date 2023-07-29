package org.example;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ContinuousSnakeGame {

    private static final int BOARD_SIZE = 10;
    private static final char EMPTY_CELL = ' ';
    private static final char SNAKE_BODY = 'O';
    private static final char FOOD = '*';

    private char[][] board;
    private int snakeLength;
    private int[] snakeRowPositions;
    private int[] snakeColPositions;
    private int foodRow;
    private int foodCol;
    private int direction;
    private boolean gameOver;

    public ContinuousSnakeGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        snakeRowPositions = new int[BOARD_SIZE * BOARD_SIZE];
        snakeColPositions = new int[BOARD_SIZE * BOARD_SIZE];
        snakeLength = 1;
        direction = 1; // 0 - up, 1 - right, 2 - down, 3 - left
        gameOver = false;
        initBoard();
        generateFood();
    }

    private void initBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
        board[0][0] = SNAKE_BODY; // Initial position of the snake
        snakeRowPositions[0] = 0;
        snakeColPositions[0] = 0;
    }

    private void generateFood() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(BOARD_SIZE);
            col = random.nextInt(BOARD_SIZE);
        } while (board[row][col] != EMPTY_CELL);

        foodRow = row;
        foodCol = col;
        board[row][col] = FOOD;
    }

    private void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void play() {
        Timer timer = new Timer();
        TimerTask snakeMoveTask = new TimerTask() {
            @Override
            public void run() {
                if (!gameOver) {
                    updateGame();
                    printBoard();
                } else {
                    System.out.println("Game Over!");
                    timer.cancel();
                }
            }
        };
        // Здесь устанавливаем интервал обновления игры (в миллисекундах)
        timer.scheduleAtFixedRate(snakeMoveTask, 0, 500); // Обновление каждые 500 мс
    }

    private void updateGame() {
        int newRow = snakeRowPositions[0];
        int newCol = snakeColPositions[0];
        switch (direction) {
            case 0: // Up
                newRow--;
                break;
            case 1: // Right
                newCol++;
                break;
            case 2: // Down
                newRow++;
                break;
            case 3: // Left
                newCol--;
                break;
        }

        if (newRow < 0 || newRow >= BOARD_SIZE || newCol < 0 || newCol >= BOARD_SIZE) {
            gameOver = true; // Snake hit the boundary
            return;
        }

        if (board[newRow][newCol] == SNAKE_BODY) {
            gameOver = true; // Snake collided with itself
            return;
        }

        if (newRow == foodRow && newCol == foodCol) {
            // Snake ate the food
            snakeLength++;
            generateFood();
        } else {
            // Move the tail of the snake
            board[snakeRowPositions[snakeLength - 1]][snakeColPositions[snakeLength - 1]] = EMPTY_CELL;
        }

        // Move the head of the snake
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeRowPositions[i] = snakeRowPositions[i - 1];
            snakeColPositions[i] = snakeColPositions[i - 1];
        }
        snakeRowPositions[0] = newRow;
        snakeColPositions[0] = newCol;

        // Update the board with the new snake position
        for (int i = 0; i < snakeLength; i++) {
            board[snakeRowPositions[i]][snakeColPositions[i]] = SNAKE_BODY;
        }
    }

    public static void main(String[] args) {
        ContinuousSnakeGame game = new ContinuousSnakeGame();
        game.play();
    }
}

