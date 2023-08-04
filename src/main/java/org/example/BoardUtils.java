package org.example;

import static org.example.Constants.*;

/**
 * @author Andrej Reutow
 * created on 04.08.2023
 */
public class BoardUtils {

    public static String boardToString(final Card[][] cardBoard) {

        final StringBuilder buffer = new StringBuilder();

        buffer.append(MEMORY_GAME_HEADER)
                .append(System.lineSeparator());

        int row = cardBoard.length;
        int column = cardBoard[0].length;

        buffer.append(String.format("%" + (CELL_WIDTH + 1)  + "s", ""));
        for (int i = 1; i <= column; i++) {
            buffer.append(String.format("%" + CELL_WIDTH + "s|", i));
        }

        buffer.append(System.lineSeparator());

        for (int i = 0; i < row; i++) {
            buffer.append(String.format("%" + CELL_WIDTH + "d|", i + 1));
            for (int j = 0; j < column; j++) {
                buffer.append(String.format("%" + CELL_WIDTH + "s|", cardBoard[i][j]));
            }
            buffer.append(System.lineSeparator());
        }
        buffer.append(MEMORY_GAME_FOOTER);

        return buffer.toString();
    }
}
