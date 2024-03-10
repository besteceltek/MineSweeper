import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// Değerlendime formu 5
public class MineSweeper {
    int rowNumber, colNumber, mineCount, uncoveredCount;
    String[][] playerMine, actualMine;
    Random random = new Random();
    Scanner input = new Scanner(System.in);

    // Constructor initializes the MineSweeper game with the specified number of rows and columns.
    MineSweeper(int rowNumber, int colNumber) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.playerMine = new String[rowNumber][colNumber];
        this.actualMine = new String[rowNumber][colNumber];
        this.uncoveredCount = 0;

        // Değerlendirme formu 8
        this.mineCount = rowNumber * colNumber / 4;

        // Initialize the game by setting up the minefield and the player's minefield.
        initializeGame();
    }

    void initializeGame() {
        setMineField();
        setPlayerMineField();
    }

    // Method to set up the minefield with random mine placements.
    void setMineField() {
        System.out.println("Mayınların Konumu");
        int minesPlaced = 0;

        // Fill the actualMine array with "-" to represent empty cells.
        for (String[] strings : actualMine) {
            Arrays.fill(strings, "-");
        }
        // Randomly place mines in the minefield.
        while (minesPlaced < mineCount) {
            int randomRow = random.nextInt(rowNumber);
            int randomColumn = random.nextInt(colNumber);

            // Check if the current cell does not already have a mine.
            if (!actualMine[randomRow][randomColumn].equals("*")) {
                actualMine[randomRow][randomColumn] = "*";
                minesPlaced++;
            }
        }
        // Print the minefield with mine locations for debugging purposes.
        for (String[] strings : actualMine) {
            System.out.println(Arrays.toString(strings));
        }
    }

    // Method to initialize the player's minefield with empty cells.
    void setPlayerMineField() {
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                playerMine[i][j] = "-";
            }
        }
    }

    // Method to print the minefield.
    void printMine(String[][] mine) {
        System.out.print("  ");
        for (int i = 0; i < rowNumber; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < rowNumber; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < colNumber; j++) {
                System.out.print(mine[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to fix the matrix size if it is not applicable (less than 2x2).
    void matrixFixer() {
        boolean check = true;
        while (check) {
            System.out.println("Field size is not applicable, please create a field 2x2 at minimum.");
            System.out.print("Satır sayısını girin: ");
            this.rowNumber = input.nextInt();
            System.out.print("Sütun sayısını girin: ");
            this.colNumber = input.nextInt();
            if (this.colNumber >= 2 && this.rowNumber >= 2) {
                check = false;
            }
        }
    }

    // Method to check if the given coordinates are within the valid range.
    boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < rowNumber && col >= 0 && col < colNumber;
    }

    // Method to check if there is a mine at the specified coordinates.
    boolean isMine(int row, int col) {
        return actualMine[row][col].equals("*");
    }

    // Method to count the number of surrounding mines for a given cell.
    int countSurroundingMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isValidCoordinate(i, j) && isMine(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // Method to uncover a cell and update the player's minefield.
    // Değerlendirme formu 12-13
    void uncoverCell(int row, int col) {
        if (isValidCoordinate(row, col) && playerMine[row][col].equals("-")) {
            if (isMine(row, col)) {
                // Değerlendirme formu 15
                System.out.println("Mayına bastınız! Oyunu kaybettiniz.");
                printMine(actualMine);
                System.exit(0);
            } else {
                int surroundingMines = countSurroundingMines(row, col);
                playerMine[row][col] = String.valueOf(surroundingMines);
                uncoveredCount++;

                if (surroundingMines == 0) {
                    uncoverAdjacentCells(row, col);
                }
            }
        }
    }

    // Method to uncover all adjacent cells of a given cell.
    void uncoverAdjacentCells(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                uncoverCell(i, j);
            }
        }
    }

    // Method to start and play the MineSweeper game.
    void play() {
        if (this.colNumber < 2 && this.rowNumber < 2) {
            // If the matrix size is less than 2x2, fix the matrix size.
            matrixFixer();
        }

        System.out.println("========================");
        System.out.println("Mayın Tarlası Oyununa Hoşgeldiniz!");

        // Continue playing until all cells without mines are uncovered.
        // Değerlendirme formu 14
        while (uncoveredCount < rowNumber * colNumber - mineCount) {

            // Değerlendirme formu 11
            printMine(playerMine);

            // Get user input for row and column.
            // Değerlendirme formu 9
            System.out.print("Satır Giriniz : ");
            int playerRow = input.nextInt();
            System.out.print("Sütun Giriniz : ");
            int playerCol = input.nextInt();

            // Check if the entered coordinates are valid.
            // Değerlendirme formu 10
            if (!isValidCoordinate(playerRow, playerCol)) {
                System.out.println("Geçersiz koordinat! Lütfen tekrar girin.");
                //continue;
            }

            // Check if the cell has already been selected.
            if (!playerMine[playerRow][playerCol].equals("-")) {
                System.out.println("Bu koordinat daha önce seçildi. Başka bir koordinat girin.");
                //continue;
            }

            // Uncover the selected cell.
            uncoverCell(playerRow, playerCol);
        }

        // Display a congratulatory message if all non-mine cells are uncovered.
        // Değerlendirme formu 15
        System.out.println("Tebrikler! Tüm noktaları açtınız. Oyunu kazandınız!");
        printMine(playerMine);
        input.close();
    }
}
