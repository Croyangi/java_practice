import java.util.stream.*;
import java.util.Arrays;
import java.util.Scanner;

public class testing {

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void finishGame() {  
        clearScreen();
        System.out.println("Good Game!");
    }


    public static void main(String[] args) {

        int[][] board = {
            {0,0,0},
            {0,0,0},
            {0,0,0}
        };

        //Game Loop System
        int rowSelection = 0;
        int columnSelection = 0;
        boolean playerXTurn = true;

        while (true) {

            //Print Board UI
            System.out.println("\n\n\n\n\n");

            for(int i = 0; i < 3; i++) { 
                String row = Arrays.toString(board[i]);
                String rowUI = row.replace(",", " |").replace("[", "").replace("]", "").replace("-1", "O").replace("1", "X").replace("0", " ");
                System.out.println(rowUI);
                if (i != 2) { System.out.println("--+---+--"); } 
                }


            //Announcing Turns
            System.out.println("\n");

            if (playerXTurn) {
                System.out.println("Player X's turn!");
            } else {
                System.out.println("Player O's turn!");
            }


            //Anti-stupidity system for players
            while (true) {
                while (true) {
                    System.out.println("\n");

                    Scanner in = new Scanner(System.in);
                    System.out.print("What row do you want to mark?\nChoose 1-3.\n>>> ");
                    rowSelection = in.nextInt();

                    if (rowSelection == 1 || rowSelection == 2 || rowSelection == 3) {
                        rowSelection -= 1;
                        break;

                    } else {
                        System.out.println("Please input correctly.");
                    }
                    }

                while (true) {
                    Scanner in = new Scanner(System.in);
                    System.out.print("What column do you want to mark?\nChoose 1-3.\n>>> ");
                    columnSelection = in.nextInt();

                    if (columnSelection == 1 || columnSelection == 2 || columnSelection == 3) {
                        columnSelection -= 1;
                        break;

                    } else {
                        System.out.println("Please input correctly.");
                    }
                    }

                if (board[rowSelection][columnSelection] == 0) {
                    break;
                } else {
                    System.out.println("This plot is already marked.");
                }
                    break;
                }
                
            
            //Marking board and switching turns
            if (playerXTurn) {
                board[rowSelection][columnSelection] = 1;
                playerXTurn = false;
            } else {
                board[rowSelection][columnSelection] = -1;
                playerXTurn = true;
            }

            //Row Win Condition Check
            for(int i = 0; i < 3; i++) { 
                    
                int sum = IntStream.of(board[i]).sum();
            
                if (sum == 3) {
                    finishGame();
                    System.out.println("X's win!");
                    System.exit(0);

                } else if (sum == -3) {
                    finishGame();
                    System.out.println("O's win!");
                    System.exit(0);
                } 
            }


            //Column Win Condition Check
            for(int i = 0; i < 3; i++) { 
                
                int sum = board[0][i] + board[1][i] + board[2][i];
             
                if (sum == 3) {
                    finishGame();
                    System.out.println("X's win!");
                    System.exit(0);

                } else if (sum == -3) {
                    finishGame();
                    System.out.println("O's win!");
                    System.exit(0);
                } 
            }


            //Diagnols Win Condition Check
            int sum = board[0][0] + board[1][1] + board[2][2];
            
            if (sum == 3) {
                finishGame();
                System.out.println("X's win!");
                System.exit(0);

            } else if (sum == -3) {
                finishGame();
                System.out.println("O's win!");
                System.exit(0);
            }

            sum = board[0][2] + board[1][1] + board[2][0];

            if (sum == 3) {
                finishGame();
                System.out.println("X's win!");
                System.exit(0);

            } else if (sum == -3) {
                finishGame();
                System.out.println("O's win!");
                System.exit(0);
            }

        }
            
    }

}