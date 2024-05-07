import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Algorithm {
    public static Stack<IceGridCell.Path> findPath(IceGridCell startNode, IceGridCell finishNode) {
        PriorityQueue<IceGridCell.Path> queue = new PriorityQueue<>();
        Set<IceGridCell> visited = new HashSet<>();
        Set<IceGridCell.Path> pathMap = new HashSet<>();
        Stack<IceGridCell.Path> shortestPath = new Stack<>();

        visited.add(startNode);

        // Initial path from startNode to itself
        IceGridCell.Path initialPath = new IceGridCell.Path(startNode, startNode);
        queue.offer(initialPath);
        pathMap.add(initialPath);

        while (!queue.isEmpty()) {
            IceGridCell.Path currentPath = queue.poll();
            if (currentPath.getFinishNode() == finishNode) {
                // Reconstruct the shortest path if finishNode is reached
                IceGridCell.Path tempPath = currentPath;
                while (tempPath.getStartNode() != startNode) {
                    shortestPath.push(tempPath);
                    for (IceGridCell.Path path : pathMap) {
                        if (path.getFinishNode() == tempPath.getStartNode()) {
                            tempPath = path;
                            break;
                        }
                    }
                }
                shortestPath.push(tempPath); // Push the startNode path
                return shortestPath;
            }

            // Explore and enqueue paths
            for (IceGridCell.Direction direction : IceGridCell.Direction.values()) {
                IceGridCell endNode = currentPath.getFinishNode().findFinishNode(direction, finishNode);
                if (endNode != null && !visited.contains(endNode)) {
                    visited.add(endNode);
                    IceGridCell.Path newPath = new IceGridCell.Path(currentPath.getFinishNode(), endNode);
                    queue.offer(newPath);
                    pathMap.add(newPath);
                }
            }
        }
        return null; // No path found
    }
}
