import java.util.ArrayList;

public class MorrisGameBoard {
//    public BoardNode board;
//
//    public MorrisGameBoard(BoardNode board) {
//        this.board = board;
//    }
    public int runDepth;
    MorrisGameBoard(int runDepth){
        this.runDepth = runDepth;
    }

    public ArrayList<BoardNode> genMoveOpening(char[] position) {
        return genAdd(position);
    }

    private ArrayList<BoardNode> genAdd(char[] position) {
        ArrayList<BoardNode> posList = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'x') {
                char[] pos = position.clone();
                pos[i] = 'W';
                if (closeMill(i, pos)) {
                    posList = genRemove(pos, posList);
                } else {
                    posList.add(new BoardNode(pos));
                }
            }
        }
        return posList;
    }

    public char[] reverse(char[] position) {
        char[] revPos = new char[position.length];
        for (int i = 0; i < revPos.length; i++) {
            revPos[i] = position[i];
            if (revPos[i] == 'W') {
                revPos[i] = 'B';
            } else if (revPos[i] == 'B') {
                revPos[i] = 'W';
            }
        }
        return revPos;
    }

    public ArrayList<BoardNode> genMoveOpeningBlack(char[] position) {
        char[] pos = position;
        ArrayList<BoardNode> posList = genAdd(reverse(pos));
        for (BoardNode aPosList : posList) {
            aPosList.position = reverse(aPosList.position);
        }
        return posList;
    }

    private ArrayList<BoardNode> genRemove(char[] position, ArrayList<BoardNode> posList) {
        boolean closeMillFlag = true;
        int size = position.length;

        for (int i = 0; i < size; i++) {
            if (position[i] == 'B') {
                if (!closeMill(i, position)) {
                    char[] pos = position.clone();
                    pos[i] = 'x';
                    posList.add(new BoardNode(pos));
                    closeMillFlag = false;
                }
            }
        }

        if (closeMillFlag) {
            posList.add(new BoardNode(position));
        }

        return posList;
    }

//    public Boolean closeMill(int loc, char[] position) {
//        char move = position[loc];
//        switch (loc) {
//            case 0:
//                return (position[2] == move && position[4] == move)
//                        || (position[6] == move && position[18] == move);
//            case 1:
//                return position[11] == move && position[20] == move;
//            case 2:
//                return (position[0] == move && position[4] == move)
//                        || (position[7] == move && position[15] == move);
//            case 3:
//                return position[10] == move && position[17] == move;
//            case 4:
//                return (position[0] == move && position[2] == move)
//                        || (position[8] == move && position[12] == move);
//            case 5:
//                return position[9] == move && position[14] == move;
//            case 6:
//                return (position[0] == move && position[18] == move)
//                        || (position[7] == move && position[8] == move);
//            case 7:
//                return (position[2] == move && position[15] == move)
//                        || (position[6] == move && position[8] == move);
//            case 8:
//                return (position[4] == move && position[12] == move)
//                        || (position[6] == move && position[7] == move);
//            case 9:
//                return (position[5] == move && position[14] == move)
//                        || (position[10] == move && position[11] == move);
//            case 10:
//                return (position[9] == move && position[11] == move)
//                        || (position[3] == move && position[17] == move);
//            case 11:
//                return (position[1] == move && position[20] == move)
//                        || (position[9] == move && position[10] == move);
//            case 12:
//                return (position[13] == move && position[14] == move)
//                        || (position[4] == move && position[8] == move);
//            case 13:
//                return (position[16] == move && position[19] == move)
//                        || (position[12] == move && position[14] == move);
//            case 14:
//                return (position[12] == move && position[13] == move)
//                        || (position[5] == move && position[9] == move);
//            case 15:
//                return (position[16] == move && position[17] == move)
//                        || (position[2] == move && position[7] == move);
//            case 16:
//                return (position[15] == move && position[17] == move)
//                        || (position[13] == move && position[19] == move);
//            case 17:
//                return (position[15] == move && position[16] == move)
//                        || (position[3] == move && position[10] == move);
//            case 18:
//                return (position[0] == move && position[6] == move)
//                        || (position[19] == move && position[20] == move);
//            case 19:
//                return (position[18] == move && position[20] == move)
//                        || (position[13] == move && position[16] == move);
//            case 20:
//                return (position[1] == move && position[11] == move)
//                        || (position[18] == move && position[19] == move);
//
//        }
//        return false;
//    }

    public int[] countNums(char[] position) {
        int[] counts = new int[2];
        char pos;
        for (char aPosition : position) {
            pos = aPosition;
            if (pos == 'W') {
                counts[0]++;
            }
            if (pos == 'B') {
                counts[1]++;
            }
        }
        return counts;
    }

    public int openingStatic(char[] position) {
        int[] count = countNums(position);
        return count[0] - count[1];
    }

    public ArrayList<BoardNode> genMoveMidEndBlack(char[] board) {
        char[] revPos = reverse(board);

        ArrayList<BoardNode> posList = genMoveMidEnd(revPos);

        for (BoardNode b : posList) {
            b.position = reverse(b.position);
        }

        return posList;
    }


    public ArrayList<BoardNode> genMoveMidEnd(char[] position) {
        int[] nums = countNums(position);

        if (nums[0] == 3) {
            return genHopping(position);
        } else {
            return genMove(position);
        }
    }


    private ArrayList<BoardNode> genHopping(char[] position) {
        ArrayList<BoardNode> posList = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'W') {
                for (int j = 0; j < position.length; j++) {
                    if (position[j] == 'x') {
                        char[] pos = position.clone();
                        pos[j] = 'W';
                        pos[i] = 'x';

                        if (closeMill(j, pos)) {
                            posList = genRemove(pos, posList);
                        } else {
                            posList.add(new BoardNode(pos));
                        }
                    }
                }
            }
        }
        return posList;
    }


    private ArrayList<BoardNode> genMove(char[] position) {
        ArrayList<BoardNode> posList = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'W') {
                int[] nList = neighbor(i);
                for (int j : nList) {
                    if (position[j] == 'x') {
                        char[] pos = position.clone();
                        pos[i] = 'x';
                        pos[j] = 'W';

                        if (closeMill(j, pos)) {
                            posList = genRemove(pos, posList);
                        } else {
                            posList.add(new BoardNode(pos));
                        }
                    }
                }
            }
        }
        return posList;
    }


//    private ArrayList<Integer> neighbors(int loc) {
//        ArrayList<Integer> nList = new ArrayList<>();
//
//        switch (loc) {
//            case 0:
//                nList.add(1);
//                nList.add(2);
//                nList.add(6);
//                break;
//
//            case 1:
//                nList.add(0);
//                nList.add(11);
//                break;
//
//            case 2:
//                nList.add(0);
//                nList.add(3);
//                nList.add(4);
//                nList.add(7);
//                break;
//
//            case 3:
//                nList.add(2);
//                nList.add(10);
//                break;
//
//            case 4:
//                nList.add(2);
//                nList.add(5);
//                nList.add(8);
//                break;
//
//            case 5:
//                nList.add(4);
//                nList.add(9);
//                break;
//
//            case 6:
//                nList.add(0);
//                nList.add(7);
//                nList.add(18);
//                break;
//
//            case 7:
//                nList.add(2);
//                nList.add(6);
//                nList.add(8);
//                nList.add(15);
//                break;
//
//            case 8:
//                nList.add(4);
//                nList.add(7);
//                nList.add(12);
//                break;
//
//            case 9:
//                nList.add(5);
//                nList.add(10);
//                nList.add(14);
//                break;
//
//            case 10:
//                nList.add(3);
//                nList.add(9);
//                nList.add(11);
//                nList.add(17);
//                break;
//
//            case 11:
//                nList.add(1);
//                nList.add(10);
//                nList.add(20);
//                break;
//
//            case 12:
//                nList.add(8);
//                nList.add(13);
//                break;
//
//            case 13:
//                nList.add(12);
//                nList.add(14);
//                nList.add(16);
//                break;
//
//            case 14:
//                nList.add(9);
//                nList.add(13);
//                break;
//
//            case 15:
//                nList.add(7);
//                nList.add(16);
//                break;
//
//            case 16:
//                nList.add(13);
//                nList.add(15);
//                nList.add(17);
//                nList.add(19);
//                break;
//
//            case 17:
//                nList.add(10);
//                nList.add(16);
//                break;
//
//            case 18:
//                nList.add(6);
//                nList.add(19);
//                break;
//
//            case 19:
//                nList.add(16);
//                nList.add(18);
//                nList.add(20);
//                break;
//
//            case 20:
//                nList.add(11);
//                nList.add(19);
//                break;
//
//
//        }
//        return nList;
//    }


    public int staticMinEnd(char[] position, int depth) {
//        System.out.println(String.valueOf(position));
        int[] nums = countNums(position);
        Boolean myNextTurn = (runDepth - depth) % 2 == 0;
        if (nums[1] <= 2) {
             return 10000;
        } else if (nums[0] <= 2) {
            return -10000;
        }

        ArrayList<BoardNode> list = new ArrayList<>();
        char[] revPos = reverse(position);
        list = genMoveMidEnd(revPos);


        int numBlackMoves = list.size();
//        System.out.println(numBlackMoves);
        if (numBlackMoves == 0) {
            return 10000;
        } else {
            return 1000 * (nums[0] - nums[1]) - numBlackMoves;
        }

    }

    public int openingStaticImproved(char[] position) {
        int piecesDiff = openingStatic(position);
        int num2PiecesConf = count2PiecesConf(position);
        int num3PiecesConf = 0;
        if (num2PiecesConf >= 2) {
            num3PiecesConf = num2PiecesConf - 1;
        }

        return piecesDiff + (2 * num2PiecesConf) + (3 * num3PiecesConf);
    }


    public int staticMidEndImproved(char[] position, int depth) {
        int staticMidEnd = staticMinEnd(position, depth);

        if (staticMidEnd == 10000) {
            staticMidEnd *= (depth + 1);
        }

        if (staticMidEnd == -10000){
            return  staticMidEnd;
        }

        return staticMidEnd + 100 * countMills(position) + 300 * doubleMills(position);
//        return staticMidEnd + 400 * num2PiecesConf + 600* num3PiecesConf;
    }


    private int doubleMills(char[] position) {
        int num = 0;
        int len = position.length;
        for (int i = 0; i < len; i++) {
            if (position[i] == 'W') {
                int[] neList = neighbor(i);
                for (int n : neList) {
                    if (position[n] == 'x') {
                        char[] pos = position.clone();
                        pos[n] = 'W';
                        if (closeMill(n, pos)) {
                            num++;
                        }
                    }
                }
            }
        }
        return num;
    }


    private int countMills(char[] position) {
        char[] pos = position.clone();
        int len = pos.length;
        int numMills = 0;
        for (int i = 0; i < len; i++) {
            if (position[i] == 'W') {
                int[][] millPos = getMills(i);
                for (int[] mill : millPos) {
                    if (isMill(position, i, mill) && (pos[i] != '*' || pos[mill[0]] != '*' || pos[mill[1]] != '*')) {
                        numMills++;
                        pos[i] = '*';
                        pos[mill[0]] = '*';
                        pos[mill[1]] = '*';
                    }
                }
            }
        }
        return numMills;
    }

    private Boolean isMill(char[] position, int loc, int[] locList) {
        return position[loc] == position[locList[0]] && position[loc] == position[locList[1]];
    }

    private int count2PiecesConf(char[] position) {
        return count2PiecesW(position) - count2PiecesB(position);
    }

    private int count2PiecesW(char[] position) {
        int num = 0;
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'x') {
                int[][] millList = getMills(i);
                for (int[] m : millList) {
                    if (position[m[0]] == 'W' && position[m[1]] == 'W') {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    private int count2PiecesB(char[] position) {
        int num = 0;
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'x') {
                int[][] millList = getMills(i);
                for (int[] m : millList) {
                    if (position[m[0]] == 'B' && position[m[1]] == 'B') {
                        num++;
                    }
                }
            }
        }
        return num;
    }


    public int[][] getMills(int loc) {
        switch (loc) {
            case 0:
                return new int[][]{{2, 4}, {6, 18}};
            case 1:
                return new int[][]{{11, 20}};
            case 2:
                return new int[][]{{0, 4}, {7, 15}};
            case 3:
                return new int[][]{{10, 17}};
            case 4:
                return new int[][]{{2, 0}, {8, 12}};
            case 5:
                return new int[][]{{9, 14}};
            case 6:
                return new int[][]{{0, 18}, {7, 8}};
            case 7:
                return new int[][]{{2, 15}, {6, 8}};
            case 8:
                return new int[][]{{12, 4}, {6, 7}};
            case 9:
                return new int[][]{{5, 14}, {10, 11}};
            case 10:
                return new int[][]{{9, 11}, {3, 17}};
            case 11:
                return new int[][]{{1, 20}, {9, 10}};
            case 12:
                return new int[][]{{13, 14}, {4, 8}};
            case 13:
                return new int[][]{{16, 19}, {12, 14}};
            case 14:
                return new int[][]{{12, 13}, {5, 9}};
            case 15:
                return new int[][]{{16, 17}, {2, 7}};
            case 16:
                return new int[][]{{15, 17}, {13, 19}};
            case 17:
                return new int[][]{{15, 16}, {3, 10}};
            case 18:
                return new int[][]{{0, 6}, {19, 20}};
            case 19:
                return new int[][]{{18, 20}, {16, 13}};
            case 20:
                return new int[][]{{1, 11}, {19, 18}};
            default:
                return null;
        }
    }


    public int[] neighbor(int loc) {
        switch (loc) {
            case 0:
                return new int[]{1, 2, 6};
            case 1:
                return new int[]{0, 11};
            case 2:
                return new int[]{0, 3, 4, 7};
            case 3:
                return new int[]{2, 10};
            case 4:
                return new int[]{2, 5, 8};
            case 5:
                return new int[]{4, 9};
            case 6:
                return new int[]{0, 7, 18};
            case 7:
                return new int[]{2, 6, 8, 15};
            case 8:
                return new int[]{4, 7, 12};
            case 9:
                return new int[]{5, 10, 14};
            case 10:
                return new int[]{3, 9, 11, 17};
            case 11:
                return new int[]{1, 10, 20};
            case 12:
                return new int[]{8, 13};
            case 13:
                return new int[]{12, 14, 16};
            case 14:
                return new int[]{9, 13};
            case 15:
                return new int[]{7, 16};
            case 16:
                return new int[]{13, 15, 17, 19};
            case 17:
                return new int[]{10, 16};
            case 18:
                return new int[]{6, 19};
            case 19:
                return new int[]{16, 18, 20};
            case 20:
                return new int[]{11, 19};
            default:
                return null;
        }
    }

    private Boolean closeMill(int loc, char[] position) {
        int[][] mills = getMills(loc);
        char move = position[loc];
        boolean hasMill = false;
        for (int[] mill : mills) {
            hasMill = hasMill || (position[mill[0]] == move && position[mill[1]] == move);
        }
        return hasMill;
    }

//    public static void main(String[] args){
////        System.out.println(getMills(0));
//
//        String move = "WWBWBWBxxWBxBBWxxxxBxWWW";
//        char[] m = move.toCharArray();
//        System.out.println(staticMidEndImproved(move.toCharArray(), 5));
////        int[][] a = getMills(0);
////        for (int[] b : a){
////            for (int c : b){
////                System.out.println(c);
////            }
////            System.out.println();
////        }
//    }


}
