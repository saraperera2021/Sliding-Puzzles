import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                long startTime = System.nanoTime(); // start time
                System.out.println("\nWelcome to the Ice Sliding Puzzle Solver!");
                System.out.println("Choose a puzzle to solve:");

                System.out.println("1 - puzzle_10");
                System.out.println("2 - puzzle_20");
                System.out.println("3 - puzzle_40");
                System.out.println("4 - puzzle_80");
                System.out.println("5 - puzzle_160");
                System.out.println("6 - puzzle_320");
                System.out.println("7 - puzzle_2560");
                System.out.println("8 - custom puzzle");
                System.out.println("0 - Quit");

                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice (1-8): ");
                int input;
                try {
                    input = scanner.nextInt();
                } catch (Exception ex) {
                    System.out.println("Error Wrong input type, Try Again");
                    continue;
                }
                String fileName = switch (input) {
                    case 1 -> "puzzle_10";
                    case 2 -> "puzzle_20";
                    case 3 -> "puzzle_40";
                    case 4 -> "puzzle_80";
                    case 5 -> "puzzle_160";
                    case 6 -> "puzzle_320";
                    case 7 -> "puzzle_2560";
                    case 8 -> {
                        writeMapToFile(); // Write custom map to file
                        yield "MapData";
                    }
                    case 0 -> {
                        System.exit(0);
                        yield null;
                    }
                    default -> {
                        System.out.println("Invalid choice. Exiting...");
                        yield null;
                    }
                };

                if (fileName == null) {
                    return;
                }

                char[][] map = IceMapParser.parseMap(fileName + ".txt");

                printMap(map);
                System.out.println("Map successfully parsed\n");
                IceMapParser iceMapParser = new IceMapParser(map);
                Stack<IceGridCell.Path> shortestPath = Algorithm.findPath(iceMapParser.getStartNode(), iceMapParser.getFinishNode());

                if (shortestPath != null) {
                    IceMapParser.DisplayShortestPath(iceMapParser, shortestPath);
                } else {
                    System.out.println("\nPath not found!");
                }

                long endTime = System.nanoTime(); // end time
                long elapsedTime = endTime - startTime;
                System.out.println();
                System.out.println("Time taken: " + elapsedTime + " nanoseconds");

            } catch (IOException e) {
                System.err.println("Error reading input file: " + e.getMessage());
            }
        }
    }

    private static void writeMapToFile() throws IOException {
        // Write custom map data to a file
        char[][] map = {
                {'.', '.', '.', '.', '.', '0', '.', '.', '.', 'S'},
                {'.', '.', '.', '.', '0', '.', '.', '.', '.', '.'},
                {'0', '.', '.', '.', '.', '.', '0', '.', '.', '0'},
                {'.', '.', '.', '0', '.', '.', '.', '.', '0', '.'},
                {'.', 'F', '.', '.', '.', '.', '.', '.', '0', '.'},
                {'.', '0', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '0', '.', '.'},
                {'.', '0', '.', '0', '.', '.', '0', '.', '.', '0'},
                {'0', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '0', '0', '.', '.', '.', '.', '.', '0', '.'},
        };

        BufferedWriter writer = new BufferedWriter(new FileWriter("MapData.txt"));

        for (char[] row : map) {
            for (char cell : row) {
                writer.write(cell);
            }
            writer.newLine();
        }

        writer.close();
        System.out.println("Custom map successfully written to file.");
    }

    public static void printMap(char[][] map) {
        for (char[] row : map) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
