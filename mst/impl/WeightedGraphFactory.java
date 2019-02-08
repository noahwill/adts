package impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * GraphFactory
 * 
 * File format (based on files provided by Sedgwick):
 * number of vertices
 * number of edges
 * each edge: vertex vertex (weight)
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 18, 2015
 */
public class WeightedGraphFactory {

    private static Random randy = new Random();
    
    


    public static WeightedGraph weightedUndirectedALGraphRandom(int vertices, int edges) {
        WeightedGraph graph = null;
        do {
            WeightedAdjListGraph.WALGBuilder builder = new WeightedAdjListGraph.WALGBuilder(vertices);

            for (int i = 0; i < edges; i++) {
                int first = randy.nextInt(vertices);
                int second = first + ((int) (5 * randy.nextGaussian()));
                if (second >= 0 && second < vertices && first != second)
                    builder.connectUndirected(first, second, randy.nextDouble());
                else
                    i--;
            }
            graph = builder.getGraph();
        } while (!isConnected(graph)); // repeat if the graph is not connected

        return graph;
    }

    /**
     * 
     * @param graph
     *            a graph to inspect
     * @return true if the graph is connect, false otherwise
     */
    private static boolean isConnected(WeightedGraph graph) {
        boolean connected = true;
        // check to see if the graph is connected with BFS
        Queue<Integer> BFSQueue = new java.util.LinkedList<Integer>();
        /*
         * I didn't use a bitvector, because they only improve the constant, not
         * the order of growth
         */
        boolean[] discovered = new boolean[graph.numVertices()];
        // initialize to false
        for (int i = 0; i < discovered.length; i++)
            discovered[i] = false;
        BFSQueue.add(0);
        discovered[0] = true;
        while (!BFSQueue.isEmpty()) {
            for (int adjacent : graph.adjacents(BFSQueue.remove())) {
                if (!discovered[adjacent]) {
                    BFSQueue.add(adjacent);
                    discovered[adjacent] = true;
                }
            }
        }
        // check if all vertices where discovered
        for (boolean v : discovered)
            if (!v) {
                connected = false;
                break;
            }
        return connected;
    }

    public static WeightedGraph weightedUndirectedAMGraphRandom(int vertices, int edges) {
        WeightedAdjMatrixGraph.WAMGBuilder builder;
        WeightedGraph graph = null;
        do {
            builder = new WeightedAdjMatrixGraph.WAMGBuilder(vertices);
            for (int i = 0; i < edges; i++) {
                int first = randy.nextInt(vertices);
                int second = first + ((int) (5 * randy.nextGaussian()));
                if (second >= 0 && second < vertices && first != second)
                    builder.connectUndirected(first, second, randy.nextDouble());
                else
                    i--;
            }
            graph = builder.getGraph();
        } while (!isConnected(graph)); // repeat if the graph is not connected

        return graph;

    }
   
    
    
    public static WeightedGraph weightedUndirectedAMGraphCopy(WeightedGraph g) {
        WeightedAdjMatrixGraph.WAMGBuilder builder = 
                new WeightedAdjMatrixGraph.WAMGBuilder(g.numVertices());
        
        for (WeightedEdge e : g.edges())
            builder.connectUndirected(e.first, e.second, e.weight); 
        return builder.getGraph();
        
    }
    
    public static WeightedGraph weightedUndirectedALGraphFromFile(String filename) {
        try {
            Scanner file = new Scanner(new File(filename));
            WeightedAdjListGraph.WALGBuilder builder = 
                    new WeightedAdjListGraph.WALGBuilder(Integer.parseInt(file.nextLine()));
            int numEdges = Integer.parseInt(file.nextLine());
            for (int i = 0; i < numEdges; i++) {
                StringTokenizer tokey = new StringTokenizer(file.nextLine());
                builder.connectUndirected(Integer.parseInt(tokey.nextToken()), 
                        Integer.parseInt(tokey.nextToken()), 
                        Double.parseDouble(tokey.nextToken()));
            }
            file.close();
            return builder.getGraph(); 
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return null;
        }
    }

    public static WeightedGraph weightedDirectedALGraphFromFile(String filename) {
        try {
            Scanner file = new Scanner(new File(filename));
            WeightedAdjListGraph.WALGBuilder builder = 
                    new WeightedAdjListGraph.WALGBuilder(Integer.parseInt(file.nextLine()));
            int numEdges = Integer.parseInt(file.nextLine());
            for (int i = 0; i < numEdges; i++) {
                StringTokenizer tokey = new StringTokenizer(file.nextLine());
                builder.connect(Integer.parseInt(tokey.nextToken()), 
                        Integer.parseInt(tokey.nextToken()), 
                        Double.parseDouble(tokey.nextToken()));
            }
            file.close();
            return builder.getGraph(); 
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return null;
        }
    }
    
}
