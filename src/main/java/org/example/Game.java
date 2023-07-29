package org.example;

public class Game {

    private GameBoard gameBoard;
    private SelectedCard selectedCardFirst;
    private SelectedCard selectedCardSecond;
    private Card[][] emptyField;
    private Card[][] fieldWithCards;
    private Card[][] boardToShow;

    /**
     * @param boardHigh   высота доски, строки
     * @param boardLength ширина доски, колонки
     */
    public void startGame(int boardHigh, int boardLength) {
        gameBoard = new GameBoard(boardHigh, boardLength);
        this.boardToShow = gameBoard.fillFieldWithCards();
        this.fieldWithCards = boardToShow;
        gameBoard.printBoardToFile(this.boardToShow);

        this.boardToShow = gameBoard.fillEmptyField();
        this.emptyField = boardToShow;
        printBoard();

    }

    public void openCard(int cardCount, SelectedCard selectedCard) {
        this.boardToShow = this.gameBoard.openField(emptyField, fieldWithCards, selectedCard.rowIndex(), selectedCard.columnIndex());
        Card card = this.boardToShow[selectedCard.rowIndex()][selectedCard.columnIndex()];
        if (cardCount == 0) {
            this.selectedCardFirst = selectedCard;
            this.selectedCardFirst.setCard(card);

        } else {
            this.selectedCardSecond = selectedCard;
            this.selectedCardSecond.setCard(card);
        }
        printBoard();
    }

    public void printBoard() {
        gameBoard.printBoard(this.boardToShow);
    }

    public void clearBoard() {
        validate();
        printBoard();
    }

    public void validate() {
        if (selectedCardFirst.getCard().equals(selectedCardSecond.getCard())) {
            System.out.println("\t есть совпадение");
        } else {
            updateBoard();
            System.out.println("\t нет угадал");
        }
    }

    private void updateBoard() {
        this.boardToShow[this.selectedCardFirst.rowIndex()][this.selectedCardFirst.columnIndex()] = new Card('#');
        this.boardToShow[this.selectedCardSecond.rowIndex()][this.selectedCardSecond.columnIndex()] = new Card('#');
    }
}
