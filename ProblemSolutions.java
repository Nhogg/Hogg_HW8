/******************************************************************
 *
 *   Nathan Hogg / 002
 *
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     *
     * You are building a course curriculum along with required intermediate
     * exams certifications that must be taken by programmers in order to obtain
     * a new certification called 'master programmer'. In doing so, you are placing
     * prerequisites on intermediate exam certifications that must be taken first.
     * You are allowing the flexibility of taking the exams in any order as long as
     * any exam prerequisites are satisfied.
     *
     * Unfortunately, in the past, your predecessors have accidentally published
     * curriculums and exam schedules that were not possible to complete due to cycles
     * in prerequisites. You want to avoid this embarrassment by making sure you define
     * a curriculum and exam schedule that can be completed.
     *
     * Your goal is to ensure that any student pursuing the certificate of 'master
     * programmer', can complete 'n' certification exams, each being specific to a
     * topic. Some exams have prerequisites of needing to take and pass earlier
     * certificate exams. You do not want to force any order of taking the exams, but
     * you want to make sure that at least one order is possible.
     *
     * This method will save your embarrassment by returning true or false if
     * there is at least one order that can taken of exams.
     *
     * You wrote this method, and in doing so, you represent these 'n' exams as
     * nodes in a graph, numbered from 0 to n-1. And you represent the prerequisite
     * between taking exams as directed edges between two nodes which represent
     * those two exams.
     *
     * Your method expects a 2-dimensional array of exam prerequisites, where
     * prerequisites[i] = [ai, bi] indicating that you must take exam 'bi' first
     * if you want to take exam 'ai'. For example, the pair [0, 1], indicates that
     * to take exam certification '0', you have to first have the certification for
     * exam '1'.
     *
     * The method will return true if you can finish all certification exams.
     * Otherwise, return false (e.g., meaning it is a cyclic or cycle graph).
     *
     *     Example 1:
     *     Input: numExams = 2, prerequisites = [[1,0]]
     *     Output: true
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0. So, it is possible (no
     *     cyclic or cycle graph of prereqs).
     *
     *
     *     Example 2:
     *     Input: numExams = 2, prerequisites = [[1,0],[0,1]]
     *     Output: false
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0, and to take exams 0 you
     *     should also have completed the certification of exam
     *     1. So, it is impossible (it is a cycle graph).
     *
     * To do this, we can use a depth-first search. We
     * start by creating a directed graph from the
     * prerequisites. We then use a depth-first search to
     * traverse the graph and check if there is a cycle.
     * If there is a cycle, we will return false. If there
     * is no cycle, we return true.
     *
     * @param numExams          - number of exams, which will produce a graph of n nodes
     * @param prerequisites     - 2-dim array of directed edges.
     * @return boolean          - True if all exams can be taken, else false.
     */

    public boolean canFinish(int numExams, int[][] prerequisites) {

        int numNodes = numExams;  // # of nodes in graph

        // Build directed graph's adjacency list
        ArrayList<Integer>[] adj = getAdjList(numExams, prerequisites);

        int[] states = new int[numNodes];

        for (int i = 0; i < numNodes; i++) {
            if (states[i] == 0) {
                if (checkCycle(i, adj, states)) {
                    return false;
                }
            }
        }

        return true;

    }

    /**
     * Method checkCycle
     *
     * This method checks if there is a cycle in the directed graph.
     * * To do this, we use a depth-first search. We
     * start by creating a directed graph from the
     * prerequisites. We then use a depth-first search to
     * traverse the graph and check if there is a cycle.
     * * If there is a cycle, we return true. If there
     * is no cycle, we return false.
     */
    private boolean checkCycle(int node, ArrayList<Integer>[] adj, int[] states) {
        states[node] = 1;

        for (int neighbor : adj[node]) {
            if (states[neighbor] == 1) {
                return true;
            }
            if (states[neighbor] == 0) {
                if (checkCycle(neighbor, adj, states)) {
                    return true;
                }
            }
        }

        states[node] = 2;

        return false;
    }


    /**
     * Method getAdjList
     *
     * Building an Adjacency List for the directed graph based on number of nodes
     * and passed in directed edges.
     *
     * @param numNodes      - number of nodes in graph (labeled 0 through n-1) for n nodes
     * @param edges         - 2-dim array of directed edges
     * @return ArrayList<Integer>[]  - An adjacency list representing the provided graph.
     */

    private ArrayList<Integer>[] getAdjList(
            int numNodes, int[][] edges) {

        ArrayList<Integer>[] adj
                = new ArrayList[numNodes];      // Create an array of ArrayList ADT

        for (int node = 0; node < numNodes; node++){
            adj[node] = new ArrayList<Integer>();   // Allocate empty ArrayList per node
        }
        for (int[] edge : edges){
            adj[edge[0]].add(edge[1]);              // Add connected node edge [1] for node [0]
        }
        return adj;
    }


    /*
     * Assignment Graphing - Number of groups.
     *
     * There are n people. Some of them are connected
     * as friends forming a group. If person 'a' is
     * connected to person 'b', and person 'b' is
     * connected to person 'c', they form a connected
     * group.
     *
     * Not all groups are interconnected, meaning there
     * can be 1 or more groups depending on how people
     * are connected.
     *
     * This example can be viewed as a graph problem,
     * where people are represented as nodes, and
     * edges between them represent people being
     * connected. In this problem, we are representing
     * this graph externally as an non-directed
     * Adjacency Matrix. And the graph itself may not
     * be fully connected, it can have 1 or more
     * non-connected compoents (subgraphs).
     *
     * Example 1:
     *   Input :
         AdjMatrix = [[0,1,0], [1,0,0], [0,0,0]]
     *   Output: 2
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   Where nodes 0 and 1 aee connected, and node 2
     *   is NOT connected. This forms two groups of
     *   nodes.
     *
     * Example 2:
     *   Input : AdjMatrix = [ [0,0,0], [0,0,0], [0,0,0]]
     *   Output: 3
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   There are no connected nodes, hence forming
     *   three groups.
     *
     * Example 3:
     *   Input : AdjMatrix = [[0,1,0], [1,0,0], [0,1,0]]
     *   Output: 1
     *   Explanation, The adjacency Matrix defined an
     *   undirected graph of 3 nodes (index 0 to 2).
     *   All three nodes are connected by at least one
     *   edge. So they form on large group.
     */

    public int numGroups(int[][] adjMatrix) {
        int numNodes = adjMatrix.length;
        Map<Integer,List<Integer>> graph = new HashMap();
        int i = 0, j =0;

        /*
         * Converting the Graph Adjacency Matrix to
         * an Adjacency List representation. This
         * sample code illustrates a technique to do so.
         */

        for(i = 0; i < numNodes ; i++){
            for(j = 0; j < numNodes; j++){
                if( adjMatrix[i][j] == 1 && i != j ){
                    // Add AdjList for node i if not there
                    graph.putIfAbsent(i, new ArrayList());
                    // Add AdjList for node j if not there
                    graph.putIfAbsent(j, new ArrayList());

                    // Update node i adjList to include node j
                    graph.get(i).add(j);
                    // Update node j adjList to include node i
                    graph.get(j).add(i);
                }
            }
        }

        /*
         * This method returns the number of groups found in the graph.
         *
         * To do this, we loop over each node in the graph, checking if
         * it has been visited. If it hasn't we call exploreConnection to
         * explore all nodes connected to the starting node. This method
         * explores all nodes connected to the starting node, adding
         * them to visited to signal that a "group" has been found.
         */
        Set<Integer> visited = new HashSet<>();

        int groupCount = 0;

        for (int node = 0; node < numNodes; node++) {
            if (!visited.contains(node)) {
                exploreConnection(node, graph, visited);
                groupCount++;
            }
        }

        return groupCount;
    }

    /***
     * Helper method to perform a depth-first search of the graph.
     *
     * This method explores all nodes connected to the starting node, adding
     * them to visited to signal that a "group" has been found.
     * This ensures we don't double count group members.
     *
     * To do this, we use a stack to keep track of the nodes we need
     * to visit. We start by pushing the "start" node on the stack. This represents
     * the current node we are exploring. We then enter a loop that continues
     * until all nodes have been visited. On each iteration, we pop the top node and
     * check if it has been visited. If it hasn't we add it to the visited set.
     * We then iterate over all the neighbors of the current node and push them onto
     * the stack if they haven't been visited yet. This ensures that we explore all
     * nodes connected to the starting node.
     * This method is called recursively for each node in the graph until all nodes
     * have been visited.
     *
     * @param start
     * @param graph
     * @param visited
     */
    void exploreConnection(int start, Map<Integer, List<Integer>> graph, Set<Integer> visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);

                for (int neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
}
