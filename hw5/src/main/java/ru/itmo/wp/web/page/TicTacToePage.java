package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    public static class State {
        int size = 3;
        String[][] cells;
        String phase;
        boolean crossesMove;
        int movesDone;

        State() {
            cells = new String[size][size];
            phase = "RUNNING";
            crossesMove = true;
            movesDone = 0;
        }

        public int getSize() {return size; }
        public String[][] getCells() {return cells; }
        public String getPhase() {return phase; }
        public boolean isCrossesMove() {return crossesMove; }
        public void makeMove(int row, int col, String value) {
            if (!phase.equals("RUNNING") || cells[row][col] != null || row >= size || col >= size) {
                return;
            }
            cells[row][col] = value;
            movesDone++;
            boolean fullRow = true;
            boolean fullColumn = true;
            boolean fullDiag1 = true;
            boolean fullDiag2 = true;

            for (int i = 1; i < size; i++) {
                fullRow &= (cells[row][i] != null) && (cells[row][i - 1] != null) &&
                        cells[row][i].equals(cells[row][i - 1]);
                fullColumn &= (cells[i][col] != null) && (cells[i - 1][col] != null) &&
                        cells[i][col].equals(cells[i - 1][col]);
                fullDiag1 &= (cells[i][i] != null) && (cells[i - 1][i - 1] != null) &&
                        cells[i][i].equals(cells[i - 1][i - 1]);
                fullDiag2 &= (cells[i][size - 1 - i] != null) && (cells[i - 1][size - i] != null) &&
                        cells[i][size - 1 - i].equals(cells[i - 1][size - i]);
            }
            if (fullColumn || fullRow || fullDiag1 || fullDiag2) {
                if (crossesMove) {
                    phase = "WON_X";
                } else {
                    phase = "WON_O";
                }
                return;
            }
            if (movesDone == size * size) {
                phase = "DRAW";
                return;
            }
            crossesMove ^= true;
        }
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (state == null) {
            state = new State();
        }
        for (int row = 0; row < state.getSize(); row++) {
            for (int col = 0; col < state.getSize(); col++) {
                if (request.getParameter("cell_" + row + col) != null) {
                    if (state.isCrossesMove()) {
                        state.makeMove(row, col, "X");
                    } else {
                        state.makeMove(row, col, "O");
                    }
                }
            }
        }
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State();
        view.put("state", state);
        request.getSession().setAttribute("state", state);
    }

    private void action(Map<String, Object> view, HttpServletRequest request) {
        State state = (State) request.getSession().getAttribute("state");
        if (state == null) {
            state = new State();
        }
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }
}
