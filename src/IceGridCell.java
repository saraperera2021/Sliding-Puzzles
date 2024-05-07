public class IceGridCell {
    private final int column;
    private final int row;
    private final boolean isObstacle;
    private IceGridCell topNearest, bottomNearest
            , rightNearest, leftNearest;
    private int distance = 0;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean isObstacle() {
        return !this.isObstacle;
    }

    public void setTopNearest(IceGridCell topNearest) {
        this.topNearest = topNearest;
    }

    public void setRightNearest(IceGridCell rightNearest) {
        this.rightNearest = rightNearest;
    }

    public void setBottomNearest(IceGridCell bottomNearest) {
        this.bottomNearest = bottomNearest;
    }

    public void setLeftNearest(IceGridCell leftNearest) {
        this.leftNearest = leftNearest;
    }

    public IceGridCell(int row, int column, boolean isObstacle) {
        this.row = row;
        this.column = column;
        this.isObstacle = isObstacle;
    }

    public IceGridCell findFinishNode(Direction direction, IceGridCell finishNode) {
        IceGridCell currentNode = this;
        IceGridCell priorNode = null;

        do {
            // Stopping when we reach the F node in the middle of sliding
            if (currentNode == finishNode) return currentNode;
            priorNode = currentNode;
            currentNode = switch (direction) {
                case UP -> currentNode.topNearest;
                case DOWN -> currentNode.bottomNearest;
                case LEFT -> currentNode.leftNearest;
                case RIGHT -> currentNode.rightNearest;
            };
        }
        while (currentNode != null && !currentNode.isObstacle);
        return priorNode;
    }

    public int findDistanceToNode(IceGridCell finishNode) {
        int distance = Math.abs(this.row - finishNode.row) + Math.abs(this.column - finishNode.column);
        finishNode.distance = (this.row == finishNode.row || this.column == finishNode.column) ? distance : -1;
        return finishNode.distance;
    }

    public static class Path implements Comparable<Path> {
        private final IceGridCell startNode;
        private final IceGridCell finishNode;
        private final int distance;

        public Path(IceGridCell startNode, IceGridCell finishNode) {
            this.startNode = startNode;
            this.finishNode = finishNode;
            this.distance = startNode.findDistanceToNode(finishNode);
        }

        public IceGridCell getStartNode() {
            return startNode;
        }

        public IceGridCell getFinishNode() {
            return finishNode;
        }

        @Override
        public int compareTo(Path other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
