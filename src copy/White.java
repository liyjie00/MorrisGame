import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class White implements Runnable {
    private String fileName;
    private Turns turn;
    private MorrisGameBoard b;
    private Tournament t;

    public White(String fileName, Turns turn, MorrisGameBoard b, Tournament t){
        this.fileName = fileName;
        this.turn = turn;
        this.b = b;
        this.t = t;
    }

    @Override
    public void run(){
        try {
            while(true) {
                turn.turnW.acquire();
                String[] rFile = read(fileName);
                String move = rFile[0];

                if (move.matches("white win") || move.matches("black win")){
                    System.out.println(move);
                    break;
                }
                long t1 = System.currentTimeMillis();
                int count = Integer.valueOf(rFile[1]);
                BoardNode root = new BoardNode(move.toCharArray());
                BoardNode result = t.MaxMinIM(count-1, b, root, 6, Integer.MIN_VALUE, Integer.MAX_VALUE);
                long t2 = System.currentTimeMillis();

                System.out.println(String.valueOf(result.position) + " " + count + " white " +
                        "\t" + result.value + "\t"+ + t.getNumEvaluated() + "\t" +  ((double)(t2 - t1)/(double)1000));

                if (count > 18){
                    int numBlack = b.countNums(result.position)[1];
                    if (numBlack < 3){
                        write(fileName, "white win".toCharArray());
                        turn.turnB.release();
                        break;
                    }
                }
//                write(fileName, String.valueOf(result.position));
                write(fileName, result.position);
                turn.turnB.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String[] read(String fileName){
        String[] result = new String[2];
        try{
            Scanner sc = new Scanner(new File(fileName));
            int count = 0;
            String move = "";
            while (sc.hasNextLine()){
                move = sc.nextLine();
                count++;
            }

            result[0] = move;
            result[1] = String.valueOf(count);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return result;

    }


    public void write (String newFileName, char[] move){
        FileWriter fw;
        try {
            fw = new FileWriter(newFileName, true);
            fw.write("\n");

            for(char a : move){
                fw.write(a);
            }
            fw.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
