public class MaxMinOpening {
    private int numEvaluated = 0;

    public int getNumEvaluated(){
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

    public BoardNode MaxMinOpening(MorrisGameBoard b, BoardNode board, int depth){
        if (depth == 0) {
            board.value = b.openingStatic(board.position);
            numEvaluated++;
            return board;
        } else {
            board.child = b.genMoveOpening(board.position);
        }

        int maxValue = Integer.MIN_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++){
            BoardNode result = MinMaxOpening (b, board.child.get(i), depth - 1);
            if (maxValue < result.value){
                maxValue = result.value;
                retboard = board.child.get(i);
                retboard.value = maxValue;
            }
        }
        return retboard;
    }

    public BoardNode MinMaxOpening(MorrisGameBoard b, BoardNode board, int depth){
        if (depth == 0) {
            board.value = b.openingStatic(board.position);
            numEvaluated++;
            return board;
        } else {
            board.child = b.genMoveOpeningBlack(board.position);
        }

        int minValue = Integer.MAX_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++){
            BoardNode result = MaxMinOpening (b, board.child.get(i), depth - 1);
            if (minValue > result.value){
                minValue = result.value;
                retboard = board.child.get(i);
                retboard.value = minValue;
            }
        }
        return retboard;
    }



}
