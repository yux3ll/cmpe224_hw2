import java.util.*;

public class UberStuff {
//-----------------------------------------------------
// Title: UberStuff
// Author: Yüksel Çağlar Baypınar
// ID: 43951623744
// Section: 1
// Assignment: 2
// Description: Class that implements the Uber stuff, uses a hashmap to store the network and a queue to keep track of the vertices, in order to check if a graph can be converted to a tree.
//-----------------------------------------------------

    public static void main(String[] args) {

        Map<String, List<String>> lovelyNetwork = new LinkedHashMap<>();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of taxi pickups: ");
        int numPickups = scanner.nextInt();

        System.out.print("Enter the number of taxi rides: ");
        int numRides = scanner.nextInt();
        scanner.nextLine(); // to get rid of the newline character

        System.out.println("Enter the taxi rides:");

        for (int i = 0; i < numRides; i++) {
            String[] rides = scanner.nextLine().split(" "); // [pickup, destination]
            String source = rides[0];
            String destination = rides[1];

            if (!lovelyNetwork.containsKey(source)) { // if the source is not in the map, add it
                lovelyNetwork.put(source, new ArrayList<>());
            }
            if (!lovelyNetwork.containsKey(destination)) { // if the destination is not in the map, add it
                lovelyNetwork.put(destination, new ArrayList<>());
            }

            lovelyNetwork.get(source).add(destination); // add the destination to the source's list
        }


        for (String pickup : lovelyNetwork.keySet()) { // print the network

            List<String> destinations = lovelyNetwork.get(pickup); // get the destinations of the pickup
            System.out.print(pickup + ": "); // print the pickup

            for (int i = destinations.size() - 1; i >= 0; i--) { // print the destinations in reverse order of user input -
                System.out.print(destinations.get(i) + " "); // - because the destinations are added to the list in reverse order for some reason
            }
            System.out.print("\n");
        }

        boolean isTreeable = isTree(lovelyNetwork); // check if the network is a treeable(?) network

        System.out.println(isTreeable ? "This ride network can be kept in a tree structure." : "This ride network cannot be kept in a tree structure."); //neat little printing technique i learnt the other day
    }

    private static boolean isTree(Map<String, List<String>> graph) { // check if the graph can be implemented as a tree by the given conditions
        int numVertices = graph.size();
        int numEdges = 0;

        Set<String> visited = new HashSet<>(); // to keep track of visited vertices
        Queue<String> pQueue = new LinkedList<>(); // to keep track of vertices to be visited
        String startVertex = graph.keySet().iterator().next(); // get the first vertex

        visited.add(startVertex); // add the first vertex to the visited set
        pQueue.offer(startVertex); // add the first vertex to the queue

        while (!pQueue.isEmpty()) {// while there are vertices to be visited
            String vertex = pQueue.poll(); // get the first vertex in the queue
            List<String> neighbors = graph.get(vertex);// get the neighbors of the vertex

            if (neighbors != null) { // if the vertex has neighbors
                for (String neighbor : neighbors) { // for each neighbor
                    numEdges++;
                    if (!visited.contains(neighbor)) { // if the neighbor is not visited
                        visited.add(neighbor); // add it to the visited set
                        pQueue.offer(neighbor); // add it to the queue
                    } else {// already visited neighbor-cycle detected
                        return false; // not a tree
                    }
                }
            }
        }

        return numEdges == numVertices - 1; // if the number of edges is one less than the number of vertices, it is a tree, the rest of the method acts almost like a guard clause, so this is the last line of the method
    }
}
