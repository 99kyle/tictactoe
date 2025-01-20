
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//V1 of tictactoe

public class Main {

    //Initial Values and declarations
    static final int ROWS = 3;
    static final int COLUMNS = 3;
    static int PLAYER_TURN = 0;
    static int GAME_STATE = 0;
    static int WINNER = 0;
    //static int[] possible_moves = {0,1,2,3,4,5,6,7,8};
    static ArrayList<String> possible_moves = new ArrayList<>(List.of("0","1","2","3","4","5","6","7","8"));

    static ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
    static String[][] playboard = new String[COLUMNS][ROWS];



    //board.add(new ArrayList<String>{"0","1","2"});
    public static void main(String[] args) {
        gameStart();

        while(true) {
            //Game State 1 - Ongoing game
            while (GAME_STATE == 1) {
                Scanner scn = new Scanner(System.in);
                char player_shape = (PLAYER_TURN == 1) ? 'X':'A'; //lambda exp to display player tile
                System.out.print("Player " + PLAYER_TURN + "'s (" + player_shape + ") turn, Input move: ");
                int move = scn.nextInt();
                if (possible_moves.contains(Integer.toString(move))) { //checks if user move is in possible moves
                    replaceTile(move);
                    printBoard();
                    changeTurns();
                    WINNER = winCheck(); //returns 1 or 2 if P1 or P2 satisfies win condition, otherwise returns 0
                    //System.out.println("Winnerval: " + WINNER);
                } else {
                    System.out.println("Invalid move, try again!");
                    printBoard();
                }

                if (WINNER != 0) GAME_STATE=2; //move to next game state if player wins or game is tied

            }
            //Game State 2 - asks player if they would like to play again
            while (GAME_STATE==2){
                if (WINNER==3) System.out.println("Tie!");
                else System.out.println("Winner: Player " + WINNER);
                System.out.print("Enter 1 to play again: ");
                Scanner scn = new Scanner(System.in);
                int option = scn.nextInt();
                if (option == 1) gameStart();
                else GAME_STATE=0;
            }
            //breaks while loop if player decides to end game
            if (GAME_STATE==0) break;

        }

    }
    //fills board with values from 0 to 8 to represent available moves
    static String[][] fillBoard(){
        int x = 0;
        for(int i = 0; i<COLUMNS; i++){
            for(int j = 0; j<ROWS; j++){
                playboard[i][j] = Integer.toString(x++);
            }
        }
        return playboard;
    }

    //prints playboard
    static void printBoard(){
        for(int i = 0; i<COLUMNS; i++){
            for(int j = 0; j<ROWS; j++){
                System.out.print(playboard[i][j] + "\t");
            }
            System.out.print("\n");
        }
    }

    //Resets game values
    static void gameStart(){
        possible_moves = new ArrayList<>(List.of("0","1","2","3","4","5","6","7","8"));
        PLAYER_TURN = (Math.random() < 0.5) ? 1 : 2;
        GAME_STATE = 1;
        WINNER = 0;
        fillBoard();
        printBoard();
    }
    //int[][] playboard;
    //Replaces open tile with player's tile and removes tile as possible move
    static void replaceTile(int move){
        for(int i = 0; i<COLUMNS; i++){
            for(int j = 0; j<ROWS; j++){
                if (Integer.toString(move).equals(playboard[i][j])){
                    if(PLAYER_TURN == 1) playboard[i][j] = "X";
                    else if (PLAYER_TURN == 2) playboard[i][j] = "A";
                }
            }
        }
        possible_moves.remove(Integer.toString(move));
    }

    //can just change this to boolean later
    static void changeTurns(){
        if (PLAYER_TURN == 1) PLAYER_TURN = 2;
        else if (PLAYER_TURN == 2) PLAYER_TURN = 1;
    }


    //checks playboard array if win condition is met
    static int winCheck(){
        //horizontal check

        for(int i = 0; i<COLUMNS; i++){
            if ((playboard[i][0] + playboard[i][1] + playboard[i][2]).equals("XXX")) return 1;
            else if ((playboard[i][0] + playboard[i][1] + playboard[i][2]).equals("AAA")) return 2;
        }

        //vertical check
        for(int j = 0; j<ROWS; j++){
            if ((playboard[0][j] + playboard[1][j] + playboard[2][j]).equals("XXX")) return 1;
            else if ((playboard[0][j] + playboard[1][j] + playboard[2][j]).equals("AAA")) return 2;
        }

        //left to right diagonal check
        if ((playboard[0][0] + playboard[1][1] + playboard[2][2]).equals("XXX")) return 1;
        else if ((playboard[0][0] + playboard[1][1] + playboard[2][2]).equals("AAA")) return 2;


        //right to left diagonal check
        if ((playboard[0][2] + playboard[1][1] + playboard[2][0]).equals("XXX")) return 1;
        else if ((playboard[0][2] + playboard[1][1] + playboard[2][0]).equals("AAA")) return 2;

        if (possible_moves.isEmpty()) return 3;


        return 0; //return 0 if no win condition is met
    }

}