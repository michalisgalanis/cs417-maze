package algorithms;

import java.util.Iterator;
import java.util.LinkedList;
import grid.Grid;
import util.*;

public class BFS extends Algorithms {

    public BFS(Grid grid, int grass_cost, String world_name) {
        super(grid, grass_cost, world_name);
        calc(grid.getStartidx(), grid.getTerminalidx());
        grid.VisualizeGrid(world_name + toString(), steps.stream().mapToInt(i -> i).toArray());
    }

    void calc(int start, int end) {
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[vertices];
        LinkedList<Integer> adjLists[] = graph.getAdjacentLists();
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();
        LinkedList<Integer> searched = new LinkedList<>();
        int[] parents = new int[vertices];

        // Mark the current node as visited and enqueue it
        visited[start] = true;
        queue.add(start);

        int current_vertex;
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            current_vertex = queue.poll();
            searched.add(current_vertex);

            if (current_vertex == end)
                break;
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adjLists[current_vertex].listIterator();
            while (i.hasNext()) {
                int next = i.next();
                if (!visited[next]) {
                    visited[next] = true;
                    parents[next] = current_vertex;
                    queue.add(next);
                }
            }
        }
        traceSteps(start,end,parents);
        steps.pollFirst(); // Remove first and last element
        //steps.pollLast();
        System.out.println(world_name + toString() + " Finished with " + steps.size() + " steps, " + Util.calculateAlgoCost(steps, grid) + " cost and " + Util.calculateAlgoCost(searched, grid) + " search cost!");
    }

    @Override
    public String toString() {
        return ": BFS (" + Util.getCostType(Util.CELL_TYPES.GRASS) + ")";
    }

    private void traceSteps(int start, int end, int[] parents){    
        while(end != start){
            steps.add(end);
            end = parents[end];
        }
    }
}