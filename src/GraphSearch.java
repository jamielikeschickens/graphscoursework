import java.awt.font.NumericShaper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class GraphSearch {
	private Reader reader;
	private Graph graph;

	public static void main(String[] args) {
		String fileName = args[1];
		GraphSearch search = new GraphSearch();
		search.readGraph(fileName);
		if (search.isPart(1, args)) {
			search.printGraph();
		} else if (search.isPart(2, args)) {
			try {
				int minimumNumberOfNeighbours = Integer.parseInt(args[2]);
				search.printNeighbourSearchResult(minimumNumberOfNeighbours);
			} catch (Exception e) {
				search.printErrorAndExit("Can't parse 3rd argument");;
			}
		} else if (search.isPart(3, args)) {
			print("number of nodes with fully connected neighbours: " + search.findFullyConnectedNodes().size());
		}
	}

	public GraphSearch() { }

	public GraphSearch(Graph graph) {
		this.graph = graph;
	}

	private void readGraph(String fileName) {
		reader = new Reader();
		try {
			reader.read(fileName);
			graph = reader.graph();
		} catch (IOException e) {
			printErrorAndExit("Invalid file name");
		}
	}

	private void printErrorAndExit(String errorMessage) {
		printError(errorMessage);
		exitWithError();
	}

	private void exitWithError() {
		System.exit(1);
	}

	public List<Node> neighbourSearch(int minimumNumberOfNeighbours) {
		List<Node> nodesWithNorMoreNeighbours = new ArrayList<Node>();

		for (Node node : graph.nodes()) {
			if (nodeHasMinimumNumberOfNeigboursOrMore(minimumNumberOfNeighbours, node)) {
				nodesWithNorMoreNeighbours.add(node);
			}
		}

		return nodesWithNorMoreNeighbours;
	}

	private boolean nodeHasMinimumNumberOfNeigboursOrMore(int minimumNumberOfNeighbours, Node node) {
		return node.neighbours().size() >= minimumNumberOfNeighbours;
	}

	private void printNeighbourSearchResult(int minimumNumberOfNeighbours) {
		try {
			List<Node> results = neighbourSearch(minimumNumberOfNeighbours);
			print("Number of nodes with at least " + minimumNumberOfNeighbours + " neighbours: " + results.size());
			print("\n");
		} catch (Exception e) {
			printErrorAndExit("Could not parse second argument.\n");
		}
	}

	private void printGraph() {
		GraphSerializer serializer = new GraphSerializer(graph);
		print(serializer.serialize());
	}

	private boolean isPart(int part, String[] args) {
		return args[0].equals("-p" + Integer.toString(part));
	}

	private static void print(String output) {
		System.out.print(output);
	}

	private static void printError(String output) {
		System.err.print(output);
	}

	public List<Node> findFullyConnectedNodes() {
		Set<Node> fullyConnectedNodes = new HashSet<Node>();
		for (Node node : graph.nodes()) {
			addFullyConnectedNode(fullyConnectedNodes, node);
		}
		return new ArrayList<Node>(fullyConnectedNodes);
	}

	/** Finds all fully connected nodes in a graph where a fully connected node is defined as
	 * 	one where the set of the node and its neighbours excluding the current neighbour is a subset of
	 * 	the neighbour's neighbours.
	 * @param fullyConnectedNeighours
	 * @param node
	 */
	private void addFullyConnectedNode(Set<Node> fullyConnectedNeighours,
			Node node) {
		List<Node> nodeAndNeighbours = new ArrayList<Node>();
		nodeAndNeighbours.add(node);
		nodeAndNeighbours.addAll(node.neighbours());
		if (isNeighbourFullyConnectedToNode(node,nodeAndNeighbours))
			fullyConnectedNeighours.add(node);
	}

	private boolean isNeighbourFullyConnectedToNode(Node node,List<Node> nodeAndNeighbours) {
		boolean fullyConnected = false;
		for (Node neighbour : node.neighbours()) {
			nodeAndNeighbours.remove(neighbour);
			if (isNodeConnectedToAllNeighbours(neighbour, nodeAndNeighbours)) {
				fullyConnected = true;
			}
			nodeAndNeighbours.add(neighbour);
		}
		return fullyConnected;
	}

	private boolean isNodeConnectedToAllNeighbours(Node node, List<Node> neighbours) {
		return node.neighbours().containsAll(neighbours);
	}

	public int cliquesOfSize(int sizeOfClique) {
		
		int numberOfNodesInGraph = graph.nodes().size();
		if (numberOfNodesInGraph <= 1)
			return 0;
		
		return powerSet(graph.nodes()).size();
	}
	
	private <T> List<List<T>> powerSet(List<T> list) {
		List<List<T>> powerSet = new ArrayList<List<T>>();
		Iterator<List<T>> iterator = list.iterator()
		while (iterator.hasNext()) {
			T item = iterator.next();
			iterator.remove(item);
			for (T otherItem : list) {
				ArrayList<T> memberOfPowerSet = new ArrayList<T>();
				memberOfPowerSet.add(item);
				memberOfPowerSet.add(otherItem);
				powerSet.add(memberOfPowerSet);
			}
			list.add(item);
		}
		return powerSet;
	}

}
