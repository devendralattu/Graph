package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class PlacesConnectivity {

	private Map<String, LinkedList<String>> map = new HashMap<String, LinkedList<String>>();

	public void longAddEdge(String v, String c) {
		LinkedList<String> ll;

		if (null != map.get(v)) {
			ll = map.get(v);
		} else {
			ll = new LinkedList<String>();
		}

		ll.add(c);
		map.put(v, ll);

	}

	void placesBFS(String s) {
		Map<String, Boolean> visited = new HashMap<String, Boolean>(map.size());
		Queue<String> queue = new LinkedList<String>();

		queue.add(s);
		while (!queue.isEmpty()) {
			String v = queue.poll();
			if (null != visited.get(v)) {
				continue;
			}

			visited.put(v, true);

			System.out.print(v + " ");

			if (null == map.get(v)) {
				continue;
			}
			for (String elem : map.get(v)) {
				if (null == visited.get(elem)) {
					queue.add(elem);
				}
			}
		}
	}

	void placesDFS(String s) {
		Map<String, Boolean> visited = new HashMap<String, Boolean>(map.size());
		Stack<String> stack = new Stack<String>();

		stack.add(s);
		while (!stack.isEmpty()) {
			String v = stack.pop();
			if (null != visited.get(v)) {
				continue;
			}

			visited.put(v, true);

			System.out.print(v + " ");

			LinkedList<String> ll = map.get(v);
			
			if(null == ll){
				continue;
			}
			
			for (int i = ll.size() - 1; i >= 0; i--) {
				String elem = ll.get(i);
				if (null == visited.get(elem)) {
					stack.add(elem);
				}
			}
		}
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("Enter number of edges = ");
		int e = in.nextInt();

		PlacesConnectivity g = new PlacesConnectivity();

		System.out.println("Enter connectivity:");
		in.nextLine();
		while (e-- > 0) {
			String s = in.nextLine();
			String sArr[] = s.split(" ");
			g.longAddEdge(sArr[0], sArr[1]);
		}

		System.out.println("Enter start place: ");
		String s = in.nextLine();

		System.out.println("\nFollowing is Breadth First Traversal " + "(starting from vertex " + s + ")");
		g.placesBFS(s);

		System.out.println("\n\nFollowing is Depth First Traversal " + "(starting from vertex " + s + ")");
		g.placesDFS(s);

	}

}

/** Output **/
/*
Enter number of edges = 
5
Enter connectivity:
Baltimore Ellicot
Baltimore DC
Ellicot Hudson
Ellicot Arlington
DC Virginia
Enter start place: 
Baltimore

Following is Breadth First Traversal (starting from vertex Baltimore)
Baltimore Ellicot DC Hudson Arlington Virginia 

Following is Depth First Traversal (starting from vertex Baltimore)
Baltimore Ellicot Hudson Arlington DC Virginia 
*/
