public class MaxMinGame {

    private int numEvaluated = 0;

    public int getNumEvaluated(){
        return numEvaluated;
    }


    public BoardNode MaxMinGame(MorrisGameBoard b, BoardNode board, int depth){
        int[] nums = b.countNums(board.position);
        if (depth == 0 || nums[0] < 3) {
            board.value = b.staticMinEnd(board.position, depth);
            numEvaluated++;
            return board;
        } else {
            board.child = b.genMoveMidEnd(board.position);
        }

        int maxValue = Integer.MIN_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++){
            BoardNode result = MinMaxGame (b, board.child.get(i), depth - 1);
            if (maxValue < result.value){
                maxValue = result.value;
                retboard = board.child.get(i);
                retboard.value = maxValue;
            }
        }
        return retboard;
    }

    public BoardNode MinMaxGame(MorrisGameBoard b, BoardNode board, int depth){
        int[] nums = b.countNums(board.position);
        if (depth == 0 || nums[1] < 3) {
            board.value = b.staticMinEnd(board.position, depth);
            numEvaluated++;
            return board;
        } else {
            board.child = b.genMoveMidEndBlack(board.position);
        }

        int minValue = Integer.MAX_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++){
            BoardNode result = MaxMinGame (b, board.child.get(i), depth - 1);
            if (minValue > result.value){
                minValue = result.value;
                retboard = board.child.get(i);
                retboard.value = minValue;
            }
        }
        return retboard;
    }

}
