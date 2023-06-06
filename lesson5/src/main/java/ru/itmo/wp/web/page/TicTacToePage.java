package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private static final int FIELD_SIZE = 3;
    private static final int[] dirX = {-1, -1, -1, 0};
    private static final int[] dirY = {-1, 0, 1, 1};
    private State state;

    private boolean isNumeric (String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }

        return true;
    }

    private boolean checkBorder(int posX, int posY) {
        return posX >= 0 && posX < FIELD_SIZE && posY >= 0 && posY < FIELD_SIZE;
    }

    private Phase checkPhase(State checkState) {
        int counter = 0;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                for (int direction = 0; direction < 4; direction++) {
                    if (checkBorder(i + dirX[direction], j + dirY[direction])
                            && checkBorder(i - dirX[direction], j - dirY[direction])
                            && state.cells[i][j] != null
                            && state.cells[i + dirX[direction]][j + dirY[direction]] == state.cells[i][j]
                            && state.cells[i - dirX[direction]][j - dirY[direction]] == state.cells[i][j]) {
                        return state.crossesMove ? Phase.WON_X : Phase.WON_O;
                    }
                }
                if (state.cells[i][j] != null) {
                    counter++;
                }
            }
        }
        if (counter == FIELD_SIZE * FIELD_SIZE) {
            return Phase.DRAW;
        }
        return Phase.RUNNING;
    }

    private void newGame(Map<String, Object> view, HttpServletRequest request) {
        if (request.getSession().getAttribute("state") != null) {
            state = (State) request.getSession().getAttribute("state");
            if (state.getPhase() == Phase.RUNNING) {
                view.put("state", state);
                return;
            }
        }
        state = new State(FIELD_SIZE);
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("state") != null) {
            state = (State) request.getSession().getAttribute("state");
        } else {
            state = new State(FIELD_SIZE);
        }
        if (state.phase == Phase.RUNNING) {
            boolean foundCellParameter = false;
            for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
                String key = param.getKey();
                if (key.startsWith("cell_") && key.length() == 7 && isNumeric(key.substring(5, 7))) {
                    int x = key.charAt(key.length() - 2) - '0';
                    int y = key.charAt(key.length() - 1) - '0';
                    if (checkBorder(x, y) && state.cells[x][y] == null) {
                        foundCellParameter = true;
                        state.cells[x][y] = state.crossesMove ? 'X' : 'O';
                    }
                }
            }
            if (foundCellParameter) {
                state.phase = checkPhase(state);
                state.crossesMove = !state.crossesMove;
                request.getSession().setAttribute("state", state);
            }
        }
        view.put("state", state);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("state") != null) {
            state = (State) request.getSession().getAttribute("state");
        } else {
            state = new State(FIELD_SIZE);
            request.getSession().setAttribute("state", state);
        }
        view.put("state", state);
    }

    enum Phase {
        RUNNING,
        WON_X,
        WON_O,
        DRAW
    }

    public static class State {
        int size;
        Character[][] cells;
        Phase phase;
        boolean crossesMove;

        public State(int size) {
            this.size = size;
            this.cells = new Character[size][size];
            this.phase = Phase.RUNNING;
            this.crossesMove = true;
        }

        public int getSize() {
            return size;
        }

        public Character[][] getCells() {
            return cells;
        }

        public Phase getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }
    }
}
