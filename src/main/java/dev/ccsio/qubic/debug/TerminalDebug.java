package dev.ccsio.qubic.debug;

import dev.ccsio.qubic.types.Coordinates;
import dev.ccsio.qubic.game.GameBoard;
import dev.ccsio.qubic.game.GameMaster;
import dev.ccsio.qubic.game.OpponentAlgorithm;
import dev.ccsio.qubic.game.WinningLinesRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A terminal implementation method to access the game used for debugging.
 */
public class TerminalDebug {
    static Scanner scanner = new Scanner(System.in);
    /**
     * A method to print the gameboard in the console.
     * @param board - The gameboard which is a 3D int array.
     */
    public static void printBoard(GameBoard gb) {
        int[][][] board = gb.getBoard();
        for (int z = 3; z >= 0; z--) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    int i = board[z][y][x];
                    if (i == 0) {
                        System.out.print(" \u001B[37m" + i + "\u001B[0m ");
                    } else if (i == 1) {
                        System.out.print(" \u001B[31m" + i + "\u001B[0m ");
                    } else {
                        System.out.print("\u001B[34m" + i + "\u001B[0m ");
                    }
                }
                System.out.println(); 
            }
            System.out.println();
        }
    }

    public static void printBoard(GameBoard gameBoard, Boolean compact) {
        if (!compact) { 
            return; 
        }
        System.out.println(" z = 3        z = 2        z = 1        z = 0");
        // System.out.println("0  0  0  0   0  0  0  0   0  0  0  0  -1  0  0  0");   
        for (int y = 0; y < 4; y++) {
            for (int z = 3; z >= 0; z--) {
                for (int x = 0; x < 4; x++) {
                    int v = gameBoard.getValueAt(new Coordinates(x, y, z));
                    if (v == 0) {
                        System.out.print(" \u001B[37m" + v + "\u001B[0m ");
                    } else if (v == 1) {
                        System.out.print(" \u001B[31m" + v + "\u001B[0m ");
                    } else {
                        System.out.print("\u001B[34m" + v + "\u001B[0m ");
                    }
                }
                System.out.print(" ");
            }
            System.out.print(" | y = " + y);
            System.out.println();
        }

    }

    private static String stringInput(String message) {
        System.out.println(message);
        return scanner.nextLine().toLowerCase();
    }

    private static int intInput(String message) {
        System.out.println(message);
        return scanner.nextInt();
    }

    public static void playGame() {
        String mode = stringInput("Select Mode [t|s]: ");
        if (mode.equals("t")) {
            // two-player
        } else if (mode.equals("s")) {
            List<Coordinates> inputList = new ArrayList<>();

            GameMaster gameMaster = GameMaster.getInstance();
            gameMaster.init(intInput("Choose Difficulty [0|2]: "));

            while (true) {
                int player = gameMaster.getCurrentPlayer();
                
                System.out.println("Current Player: " + player);
                System.out.println();
                // printBoard(gameMaster.getGameBoard());
                printBoard(gameMaster.getGameBoard(), true);

                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int z = scanner.nextInt();
                Coordinates coordinates = new Coordinates(x, y, z);
                printSpaces();

                if (!gameMaster.handleInput(coordinates)) {
                    System.out.println("Invalid Input!");
                } else {
                    inputList.add(coordinates);
                    if (gameMaster.getGameBoard().checkWinWithInput(coordinates)) {
                        System.out.println("Player -1 Won.");
                        break;
                    }
                    gameMaster.applyOAMove(gameMaster.computeOAMove());
                    if (gameMaster.getGameBoard().checkWinWithInput(coordinates)) {
                        System.out.println("Player 1 Won.");
                        break;
                    } 
                    
                }
            }
            for (Coordinates c : inputList) {
                System.out.println(c);
            }
        }

    }

    public static void testBoardEval() {
        OpponentAlgorithm OA = new OpponentAlgorithm(0, 1);
        GameBoard gb = new GameBoard();
        int player = -1;
        int mark;

        while (true) {
            System.out.println("x, y, z, mark: ");  
            String[] inputs = scanner.nextLine().split("\\s+");  
            
            int x = Integer.parseInt(inputs[0]);  
            int y = Integer.parseInt(inputs[1]);  
            int z = Integer.parseInt(inputs[2]);  
            
            if (inputs.length > 3) {  
                mark = Integer.parseInt(inputs[3]);  
            } else {  
                mark = player;  
            }
            Coordinates coordinates = new Coordinates(x, y, z);
            printSpaces();
            gb.placeMark(coordinates, mark);
            // This debug statement is broken fix before use
            // System.out.println("Board Eval: " + OA.evaluateGameState(gb));
            printBoard(gb, true);
            player *= -1;
        }
    }

    public static void placeAllWinningLines() {
        WinningLinesRecord winningLinesList = new WinningLinesRecord();
        // System.out.println(winningLinesList.getWinningLines().size());
        winningLinesList.deleteLines(new Coordinates(0, 0, 0));
        winningLinesList.deleteLines(new Coordinates(3, 3, 3));
        // System.out.println(winningLinesList.getWinningLines().size());

        for (List<Coordinates> line : winningLinesList.getWinningLines()) {
            GameBoard gb = new GameBoard();
            for (Coordinates coordinates : line) {
                gb.placeMark(coordinates, 1);
            }
            
            printBoard(gb);
            scanner.nextInt();
            printSpaces();
        }
    }

    private static void printSpaces() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }
}
