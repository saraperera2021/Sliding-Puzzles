import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class IceMapParser {
    private IceGridCell startNode;
    private IceGridCell finishNode;

    public IceGridCell getStartNode() {
        return startNode;
    }
    public IceGridCell getFinishNode() {
        return finishNode;
    }
    public IceMapParser(char[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        IceGridCell[][] iceMap = new IceGridCell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean isObstacle = map[i][j] == '0';
                iceMap[i][j] = new IceGridCell(i, j, isObstacle);
                if (map[i][j] == 'S') {
                    this.startNode = iceMap[i][j];
                } else if (map[i][j] == 'F') {
                    this.finishNode = iceMap[i][j];
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (iceMap[i][j].isObstacle()) {
                    if (i > 0 && iceMap[i - 1][j].isObstacle()) {
                        iceMap[i][j].setTopNearest(iceMap[i - 1][j]);
                    }
                    if (i < rows - 1 && iceMap[i + 1][j].isObstacle()) {
                        iceMap[i][j].setBottomNearest(iceMap[i + 1][j]);
                    }
                    if (j > 0 && iceMap[i][j - 1].isObstacle()) {
                        iceMap[i][j].setLeftNearest(iceMap[i][j - 1]);
                    }
                    if (j < cols - 1 && iceMap[i][j + 1].isObstacle()) {
                        iceMap[i][j].setRightNearest(iceMap[i][j + 1]);
                    }
                }
            }
        }
    }

    public static char[][] parseMap(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // Determine width and height of the map
        String line;
        int height = 0;
        int width = -1; // will store the width of first line when start reading
        while ((line = reader.readLine()) != null) {
            height++;
            if (width == -1) { // when reading first line
                width = line.length();
            } else if (line.length() != width) { // if the width of any line is different from previous line(s)
                throw new IOException("Map is not rectangular");
            }
        }

        // Initialize map array
        char[][] map = new char[height][width];

        // Reset reader to beginning of file
        reader.close();
        reader = new BufferedReader(new FileReader(filename));

        // Read map data and populate the map array
        int row = 0;
        while ((line = reader.readLine()) != null) {
            for (int col = 0; col < width; col++) {
                map[row][col] = line.charAt(col);
            }
            row++;
        }

        reader.close();
        return map;
    }

    public static void DisplayShortestPath(IceMapParser iceMapParser, Stack<IceGridCell.Path> shortestPath) {
        System.out.println("\nPath to 'S' to 'F'\n------------------");
        System.out.println("Start Node: (" + (iceMapParser.getStartNode().getColumn() + 1) + ", " +
                (iceMapParser.getStartNode().getRow() + 1) + ")");
        while (!shortestPath.isEmpty()) {
            IceGridCell.Path path = shortestPath.pop();
            int startNodeColumn = path.getStartNode().getColumn();
            int startNodeRow = path.getStartNode().getRow();
            int finishNodeColumn = path.getFinishNode().getColumn();
            int finishNodeRow = path.getFinishNode().getRow();

            String moveDirection = "";

            if (startNodeColumn == finishNodeColumn) { // move vertically
                moveDirection = (finishNodeRow > startNodeRow) ? "Move down to " : "Move up to ";
            } else if (finishNodeColumn > startNodeColumn) { //move horizontally
                moveDirection = "Move right to ";
            } else { // move left
                moveDirection = "Move left to ";
            }

            System.out.println(moveDirection + "(" + (finishNodeColumn + 1) + ", " + (finishNodeRow + 1) + ")");
        }
        System.out.println("DONE!");
    }
}
