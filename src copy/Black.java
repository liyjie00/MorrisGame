import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Black implements Runnable {
    private String fileName;
    private Turns turn;
    private MorrisGameBoard b;
    private Tournament t;

    public Black(String fileName, Turns turn, MorrisGameBoard b, Tournament t){
        this.fileName = fileName;
        this.turn = turn;
        this.b = b;
        this.t = t;
    }


    @Override
    public void run(){
        try {
            while(true) {
                turn.turnB.acquire();
                String[] rFile = read(fileName);
                String move = rFile[0];
                if (move.matches("white win") || move.matches("black win")){
                    System.out.println(move);
                    break;
                }

                int count = Integer.valueOf(rFile[1]);
                BoardNode root = new BoardNode(move.toCharArray());
                root.position = b.reverse(root.position);
                BoardNode result = t.MaxMinIM(count-1, b, root, 5, Integer.MIN_VALUE, Integer.MAX_VALUE);
                result.position = b.reverse(result.position);
                root.position = b.reverse(root.position);

                System.out.println(String.valueOf(result.position) + " " + count + " black");

                if (count > 18){
                    int numWhite = b.countNums(result.position)[0];
                    if (numWhite < 3){
                        write(fileName, "black win".toCharArray());
                        turn.turnW.release();
                        break;
                    }
                }

//                write(fileName, String.valueOf(result.position));
                write(fileName, result.position);
                turn.turnW.release();
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
