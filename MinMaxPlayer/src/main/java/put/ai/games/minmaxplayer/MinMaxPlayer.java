/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.minmaxplayer;

import java.util.List;
import java.util.Random;
import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;

public class MinMaxPlayer extends Player {

    private Random random = new Random(0xdeadbeef);


    @Override
    public String getName() {
        return "Aleksander Szymaniak 155922 Krystian Gołda 155996";
    }


    @Override
    public Move nextMove(Board b) {
        List<Move> moves = b.getMovesFor(getColor());

        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        // Alfa-Beta Pruning dla wszystkich możliwych ruchów
        for (Move move : moves) {
            // Symulacja wykonania ruchu
            Board simulatedBoard = b.clone();
            simulatedBoard.doMove(move);

            // Wywołanie Minimax z Alfa-Beta
            int value = alphaBeta(simulatedBoard, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    // Funkcja Alfa-Beta Pruning
    private int alphaBeta(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        if (depth == 0 || board.getWinner() != null) {
            return evaluate(board); // Funkcja oceny
        }

        List<Move> moves = board.getMovesFor(isMaximizing ? getColor() : getOpponent(getColor()));

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                Board simulatedBoard = board.clone();
                simulatedBoard.doMove(move);

                int eval = alphaBeta(simulatedBoard, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);

                if (beta <= alpha) {
                    break; // Odcięcie
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                Board simulatedBoard = board.clone();
                simulatedBoard.doMove(move);

                int eval = alphaBeta(simulatedBoard, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                if (beta <= alpha) {
                    break; // Odcięcie
                }
            }
            return minEval;
        }
    }

    // Funkcja oceny stanu gry
    private int evaluate(Board board) {
        // Przykład funkcji oceny: różnica punktów między graczami
        int myScore = board.getScore(getColor());
        int opponentScore = board.getScore(getOpponent(getColor()));

        return myScore - opponentScore;
    }
}
