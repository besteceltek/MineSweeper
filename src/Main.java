import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Get user input for the number of rows and columns to initialize the MineSweeper game.
        // Değerlendirme formu 7
        int row, column;
        Scanner input = new Scanner(System.in);
        System.out.print("Satır sayısını girin: ");
        row = input.nextInt();
        System.out.print("Sütun sayısını girin: ");
        column = input.nextInt();

        // Create an instance of the MineSweeper class with the specified matrix size.
        MineSweeper mine = new MineSweeper(row,column);

        // Start and play the MineSweeper game.
        mine.play();
    }
}