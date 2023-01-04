import java.io.IOException;
import java.util.Scanner;

public class HexGameAutoPlay {
    public static void main(String args[]) throws IOException {
        System.out.println("Auto Game 1: 11x11");
        HexGame auto1 = new HexGame("moves.txt");
        auto1.runGame();
        System.out.println("\nAuto Game 2: 11x11");
        HexGame auto2 = new HexGame("moves2.txt");
        auto2.runGame();
        Scanner scan = new Scanner(System.in);
        System.out.println("\n-------------------------------\n\t\t  Custom Game\n-------------------------------");

        //this doesn't account for bad input, must be int
        System.out.println("Input Game Height: ");
        int h = scan.nextInt();
        System.out.println("Input Game Width: ");
        int w = scan.nextInt();
        HexGame custom = new HexGame(h,w);
        custom.runGame();
    }
}
