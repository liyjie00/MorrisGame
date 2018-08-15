public class ABOpening {
    private int numEvaluated = 0;

    public int getNumEvaluated() {
        return numEvaluated;
    }

//    public void buildTree(BoardNode node, int depth, boolean flag){
//        if (depth > 0){
//            if (flag){
//                node.child = b.genMoveOpening(node.position);
//                int size = node.child.size();
////                System.out.println(size);
//                for (int i = 0; i < size; i++){
//                    buildTree(node.child.get(i), depth - 1, false);
//                }
//
//            } else {
//                node.child = b.genMoveOpeningBlack(node.position);
//                int size = node.child.size();
//                for (int i = 0; i < size; i++){
//                    buildTree(node.child.get(i), depth - 1, true);
//                }
//            }
//        }
//    }

    public BoardNode MaxMinABOpening(MorrisGameBoard b, BoardNode board, int depth, int alpha, int beta) {
        if (depth == 0) {
            board.value = b.openingStatic(board.position);
            numEvaluated++;
            return board;
        } else {
            board.child = b.genMoveOpening(board.position);
        }

        if (board.child.size() == 0){
            board.value = b.staticMidEndImproved(board.position, depth, true);
            numEvaluated++;
            return board;
        }

        int maxValue = Integer.MIN_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++) {
            BoardNode result = MinMaxABOpening(b, board.child.get(i), depth - 1, alpha, beta);
            if (maxValue < result.value) {
                maxValue = result.value;
                retboard = board.child.get(i);
                retboard.value = maxValue;
            }

            if (maxValue >= beta){
                return retboard;
            } else if (maxValue > alpha){
                alpha = maxValue;
            }
        }
        return retboard;
    }

    public BoardNode MinMaxABOpening(MorrisGameBoard b, BoardNode board, int depth, int alpha, int beta) {
        if (depth == 0) {
            board.value = b.openingStatic(board.position);
            numEvaluated++;
            return board;
        } else {
            board.child = b.genMoveOpeningBlack(board.position);
        }
        if (board.child.size() == 0){
            board.value = b.staticMidEndImproved(board.position, depth, false);
            numEvaluated++;
            return board;
        }
        int minValue = Integer.MAX_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++) {
            BoardNode result = MaxMinABOpening(b, board.child.get(i), depth - 1, alpha, beta);
            if (minValue > result.value) {
                minValue = result.value;
                retboard = board.child.get(i);
                retboard.value = minValue;
            }

            if (minValue <= alpha){
                return retboard;
            } else if (minValue > beta){
                beta = minValue;
            }
        }
        return retboard;
    }

}
