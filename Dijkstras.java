/* Dijkstras shortest path cost algorithm for directed graphs */

/*
	@author "Devendra Lattu"
	Jun 23, 2017
*/

package ctci;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstras {

	private static Scanner in;
	private static final int MAX = Integer.MAX_VALUE;

	class VertexDistance {
		String vertex;
		int distance;

		public VertexDistance(String vertex, int distance) {
			this.vertex = vertex;
			this.distance = distance;
		}
	}

	class Neighbors {
		VertexDistance vertexDistance;
		int weight;

		public Neighbors(VertexDistance vertexDistance, int weight) {
			this.vertexDistance = vertexDistance;
			this.weight = weight;
		}
	}

	public static void main(String[] args) {

		Dijkstras dj = new Dijkstras();

		in = new Scanner(System.in);

		System.out.println("Enter number of vertices = ");
		int V = in.nextInt();

		System.out.println("Enter number of edges = ");
		int E = in.nextInt();

		VertexDistance[] vertexDist = new VertexDistance[V];
		Map<String, Integer> posMap = new HashMap<>();
		Map<VertexDistance, List<Neighbors>> neighborMap = new HashMap<>();

		System.out.println("Enter directed edges with the weights. Example: v1 v2 w");
		in.nextLine();
		for (int i = 0; i < E; i++) {

			String line = in.nextLine();

			String[] lineArr = line.split(" ");
			String fromVertex = lineArr[0];
			String toVertex = lineArr[1];
			int distFromTo = Integer.parseInt(lineArr[2]);

			if (!posMap.containsKey(fromVertex)) {
				posMap.put(fromVertex, posMap.size());
			}
			int fromIdx = posMap.get(fromVertex);

			if (!posMap.containsKey(toVertex)) {
				posMap.put(toVertex, posMap.size());
			}
			int toIdx = posMap.get(toVertex);

			if (vertexDist[fromIdx] == null) {
				vertexDist[fromIdx] = dj.new VertexDistance(fromVertex, MAX);
			}
			if (vertexDist[toIdx] == null) {
				vertexDist[toIdx] = dj.new VertexDistance(toVertex, MAX);
			}

			Neighbors n = dj.new Neighbors(vertexDist[toIdx], distFromTo);

			List<Neighbors> neighborList = neighborMap.getOrDefault(vertexDist[fromIdx], new ArrayList<>());
			neighborList.add(n);
			neighborMap.put(vertexDist[fromIdx], neighborList);
		}

		System.out.println("Enter start and end vertex");
		String line = in.nextLine();
		String[] lineArr = line.split(" ");
		String start = lineArr[0];
		String end = lineArr[1];

		int fromIdx = posMap.get(start);
		vertexDist[fromIdx].distance = 0;

		int toIdx = posMap.get(end);

		PriorityQueue<VertexDistance> Q = new PriorityQueue<>(6, new Comparator<VertexDistance>() {
			@Override
			public int compare(VertexDistance o1, VertexDistance o2) {
				return o1.distance - o2.distance;
			}
		});

		addToQueue(vertexDist, Q);

		while (!Q.isEmpty()) {
			VertexDistance vd = Q.poll();
			if (vd.vertex.equals(end)) {
				break;
			}

			List<Neighbors> neighborsList = neighborMap.get(vd);

			for (Neighbors n : neighborsList) {
				if (n.vertexDistance.distance > vd.distance + n.weight) {
					n.vertexDistance.distance = vd.distance + n.weight;
					
					Q.remove(n.vertexDistance);
					Q.add(n.vertexDistance);
				}
			}
		}

		System.out.println("Distance: " + vertexDist[toIdx].distance);
	}

	private static void addToQueue(VertexDistance[] vertexDist, PriorityQueue<VertexDistance> Q) {
		for (VertexDistance vd : vertexDist) {
			Q.add(vd);
		}
	}

	private static void printQueue(PriorityQueue<VertexDistance> Q) {
		for (VertexDistance vd : Q) {
			System.out.println(vd.vertex + " " + vd.distance);
		}
		System.out.println();
	}

}

/*
Input:
Enter number of vertices = 
9
Enter number of edges = 
18
Enter directed edges with the weights. Example: v1 v2 w
A B 5
A C 3
A E 2
B D 2
C B 1
C D 1
D A 1
D G 2
D H 1
E A 1
E I 8
E H 4
F B 3
F G 1
G I 2
G C 3
H F 2
H G 2
Enter start and end vertex
A I

Output:
Distance: 8
*/
