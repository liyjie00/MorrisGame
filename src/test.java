import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class test {
    public static void main(String[] args){
        int depth = 3;
        MorrisGameBoard t = new MorrisGameBoard(depth);
////        String move = "BWxxWxxWxxBxBWWxxxxxxWWW";
////        String move = "WBWWWWWWWWWBWBWWWWWWWxWB";
        String move = "xxWBxxxxWBBBBWxBBxBBx";
        System.out.println(move + "-------");
//        String move = "WBWBWWWWWWWBWBWWBWWWWxWW";
        char[] m = move.toCharArray();
        Tournament tt = new Tournament();
        BoardNode root = new BoardNode(m);
//        root.position = t.reverse(root.position);
        BoardNode result = tt.MaxMinIM(48, t, root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
//        BoardNode result = tt.MinMaxIM(24, t, root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
//        result.position = t.reverse(result.position);
        System.out.println(String.valueOf(result.position)+" , " + result.value);
//        System.out.println(tt.getNumEvaluated());
//        System.out.println(t.staticMidEndImproved(m, depth));
//        System.out.println(root);


//        String move = "xxWBxxxxWBBBBWxBBxBBx";
//        char[] m = move.toCharArray();
//
//        System.out.println(t.staticMidEndImproved(m, depth, false));


    }



    public static String[] read(String fileName){
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


    public static void write (String newFileName, String move){
        FileWriter fw;
        try {
            fw = new FileWriter(newFileName, true);
            fw.write(move);
            fw.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
