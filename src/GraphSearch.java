import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
			print("number of nodes with fully connected neighbours: " + search.findFullyConnectedNeighbours().size());
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

	public List<Node> findFullyConnectedNeighbours() {
		Set<Node> fullyConnectedNeighours = new HashSet<Node>();
		for (Node node : graph.nodes()) {
			List<Node> nodeAndNeighbours = new ArrayList<Node>();
			nodeAndNeighbours.add(node);
			nodeAndNeighbours.addAll(node.neighbours());
			boolean fullyConnected = false;
			for (Node neighbour : node.neighbours()) {
				nodeAndNeighbours.remove(neighbour);
				if (neighbour.neighbours().containsAll(nodeAndNeighbours)) {
					fullyConnected = true;
				}
				nodeAndNeighbours.add(neighbour);
			}
			if (fullyConnected)
				fullyConnectedNeighours.add(node);
		}
		return new ArrayList<Node>(fullyConnectedNeighours);
	}

}
