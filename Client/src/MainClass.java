import java.awt.*;

public class Board{
	Client client;
	void displayMatrix(int[] board) {
		board = new int[]
	}
	void gameWon(int state);
	Board(Client client, String name);
	void start();
	
}

public class Client{
	void sendinput(int input);
}




/*public class MainClass{
    public static void main(String[] args) {
        String Str;
        String subStr;

        for (int i = 0; i < args.length; i++) {
            Str = args[i];
            subStr = Str.substring(0,1);
            if(subStr.compareTo("a")  == 0 || subStr.compareTo("e") == 0 || subStr.compareTo("i") == 0 || subStr.compareTo("o") == 0 || subStr.compareTo("u") == 0 ||
               subStr.compareTo("A") == 0 || subStr.compareTo("E") == 0 || subStr.compareTo("I") == 0 || subStr.compareTo("O") == 0 || subStr.compareTo("U") == 0){
                args[i] = subStr.toUpperCase().concat(Str.substring(1, Str.length())).concat("ay");

               }else{
                args[i] = Str.substring(1, Str.length()).concat(subStr).concat("ay");
                subStr = args[i].substring(0,1);
                args[i] = subStr.toUpperCase().concat(args[i].substring(1,args[i].length()));
               }
            System.out.printf("%s ", args[i]);
        }
        System.out.println();
    }
}*/