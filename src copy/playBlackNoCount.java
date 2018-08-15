import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class playBlackNoCount {

    public static void main(String[] args) {

        int depth = Integer.valueOf(args[0]);
        int count = Integer.valueOf(args[1]);
        MorrisGameBoard b = new MorrisGameBoard(depth);
        Tournament t = new Tournament();
        try {
            String fileName = "/Users/jie/Desktop/board.txt";
            String[] rFile = read(fileName);
            String move = rFile[0];
            if (move.matches("white win") || move.matches("black win")) {
                System.out.println(move);
                System.exit(0);
            }
            long t1 = System.currentTimeMillis();

            System.out.println(move + " " + (count - 1) + " white");

            BoardNode root = new BoardNode(move.toCharArray());
            root.position = b.reverse(root.position);
            BoardNode result = t.MaxMinIM(count - 1, b, root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
            result.position = b.reverse(result.position);
            long t2 = System.currentTimeMillis();


            if (String.valueOf(result.position).matches(String.valueOf(root.position))) {
                System.out.println("black no move");
                System.out.println("white win");
                System.exit(0);
            }


            root.position = b.reverse(root.position);

            System.out.println(String.valueOf(result.position) + " " + count + " black " +
                    "\t" + result.value + "\t" + +t.getNumEvaluated() + "\t" + ((double) (t2 - t1) / (double) 1000));


            if (result.child.size() == 0 && result.value >= 10000) {
                System.out.println("white no move");
            }

            if (count > 18) {
                int numWhite = b.countNums(result.position)[0];
                if (numWhite < 3) {
                    write(fileName, "black win".toCharArray());
                    System.out.println("black win");
                    System.exit(0);

                }
            }
            write(fileName, result.position);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String[] read(String fileName) {
        String[] result = new String[2];
        try {
            Scanner sc = new Scanner(new File(fileName));
            int count = 0;
            String move = "";
            while (sc.hasNextLine()) {
                move = sc.nextLine();
                count++;
            }

            result[0] = move;
            result[1] = String.valueOf(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;

    }


    private static void write(String newFileName, char[] move) {
        FileWriter fw;
        try {
            fw = new FileWriter(newFileName);

            fw.write(String.valueOf(move));
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
