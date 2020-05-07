package data.gameEngine.pathfinding;

import data.movables.Movable;

import java.util.ArrayList;

public class AStarPathFinder implements PathFinder {

    private ArrayList closed = new ArrayList();
    private SortedList open = new SortedList();
    private TileBasedMap map;
    private int maxSearchDistance;
    private Node[][] nodes;
    private boolean allowDiagMovement;
    private AStarHeuristic heuristic;

    public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement) {
        this(map, maxSearchDistance, allowDiagMovement, new ClosestHeuristic());
    }

    public AStarPathFinder(TileBasedMap map, int maxSearchDistance,
            boolean allowDiagMovement, AStarHeuristic heuristic) {
        this.heuristic = heuristic;
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;
        this.allowDiagMovement = allowDiagMovement;

        nodes = new Node[map.getHeightInTiles()][map.getWidthInTiles()];
        for (int y=0;y<map.getHeightInTiles();y++) {
            for (int x=0;x<map.getWidthInTiles();x++) {
                nodes[y][x] = new Node(x,y);
            }
        }
    }

    @Override
    public FPath findPath(Movable movable, int sx, int sy, int tx, int ty) {

        if (map.blocked(movable, tx, ty)) {
            return null;
        }

        nodes[sy][sx].cost = 0;
        nodes[sy][sx].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[sy][sx]);

        nodes[ty][tx].parent = null;

        int maxDepth = 0;
        while (maxDepth < maxSearchDistance && open.size() != 0) {

            Node current = getFirstInOpen();
            if (current == nodes[ty][tx]) {
                break;
            }

            removeFromOpen(current);
            addToClosed(current);

            for (int y=-1;y<2;y++) {
                for (int x = -1; x < 2; x++) {

                    if ((x == 0) && (y == 0)) {
                        continue;
                    }

                    if (!allowDiagMovement) {
                        if (x != 0 && y != 0) {
                            continue;
                        }
                    }
                    
                    int xp = x + current.x;
                    int yp = y + current.y;
                    
                    if (isValidLocation(movable, sx, sy, xp, yp)) {
                        
                        float nextStepCost = current.cost + getMovementCost(movable, current.x, current.y, xp, yp);
                        Node neighbour = nodes[yp][xp];
                        map.pathFinderVisited(xp, yp);
                        
                        if (nextStepCost < neighbour.cost) {
                            if (inOpenList(neighbour)) {
                                removeFromOpen(neighbour);
                            }
                            if (inClosedList(neighbour)) {
                                removeFromClosed(neighbour);
                            }
                        }

                        if (!inOpenList(neighbour) && !inClosedList(neighbour)) {
                            neighbour.cost = nextStepCost;
                            neighbour.heuristic = getHeuristicCost(movable, xp, yp, tx, ty);
                            int depth = neighbour.setParent(current);
                            maxDepth = Math.max(maxDepth, depth);
                            addToOpen(neighbour);
                        }
                    }
                }
            }
        }

        if (nodes[ty][tx].parent == null) {
            return null;
        }

        FPath path = new FPath();
        Node target = nodes[ty][tx];
        while (target != nodes[sy][sx]) {
            path.prependStep(target.x, target.y);
            target = target.parent;
        }
        path.prependStep(sx,sy);

        return path;
    }

    private float getHeuristicCost(Movable movable, int xp, int yp, int tx, int ty) {
        return heuristic.getCost(map, movable, xp, yp, tx, ty);
    }

    private void removeFromClosed(Node neighbour) {
        closed.remove(neighbour);
    }

    private boolean inClosedList(Node neighbour) {
        return closed.contains(neighbour);
    }

    private boolean inOpenList(Node neighbour) {
        return open.contains(neighbour);
    }

    private float getMovementCost(Movable movable, int x, int y, int xp, int yp) {
        return map.getCost(movable, x, y, xp, yp);
    }

    private boolean isValidLocation(Movable movable, int sx, int sy, int x, int y) {
        boolean invalid = x < 0 || x >= map.getWidthInTiles() || y < 0 || y >= map.getHeightInTiles();
        if (!invalid && (sx != x || sy != y)) {
            invalid = map.blocked(movable, x, y);
        }
        return !invalid;
    }

    private void addToClosed(Node current) {
        closed.add(current);
    }

    private void addToOpen(Node current) {
        open.add(current);
    }

    private void removeFromOpen(Node current) {
        open.remove(current);
    }

    private Node getFirstInOpen() {
        return (Node) open.first();
    }


    private class Node implements Comparable {

        private int x;
        private int y;
        private float cost;
        private Node parent;
        private float heuristic;
        private int depth;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int setParent(Node parent) {
            depth = parent.depth + 1;
            this.parent = parent;

            return depth;
        }

        public int compareTo(Object other) {
            Node o = (Node) other;

            float f = heuristic + cost;
            float of = o.heuristic + o.cost;

            if (f < of) {
                return -1;
            } else if (f > of) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

