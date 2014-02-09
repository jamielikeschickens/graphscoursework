import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GraphSearch {
	private Reader reader;
	private Graph graph;

	public static void main(String[] args) {
		String fileName = args[1];
		GraphSearch search = new GraphSearch();
		search.readGraph(fileName);
		if (search.isPartOne(args)) {
			search.printGraph(fileName);
		} else if (search.isPartTwo(args)) {
			search.printNeighbourSearchResult(args);
		}
	}
	
	public GraphSearch() { }
	
	public GraphSearch(Graph graph) {
		this.graph = graph;
	}
	
	public List<Node> neighbourSearch(int n) {
		List<Node> resultList = new ArrayList<Node>();
		
		for (Node node : graph.nodes()) {
			if (node.neighbours().size() >= n) {
				resultList.add(node);
			}
		}
		
		return resultList;
	}
	
	private void printNeighbourSearchResult(String[] args) {
		List<Node> results;
		int neighbourLimit;
		try {
			neighbourLimit = Integer.parseInt(args[2]);
			results = neighbourSearch(neighbourLimit);
			print("Number of nodes with at least " + neighbourLimit + " neighbours: " + results.size());
			print("\n");
		} catch (Exception e) {
			printError("Could not parse second argument.\n");
		}
	}

	private void printGraph(String fileName) {
		GraphSerializer serializer = new GraphSerializer(graph);
		print(serializer.serialize());
	}

	private static void print(String output) {
		System.out.print(output);
	}
	
	private static void printError(String output) {
		System.err.print(output);
	}

	private void readGraph(String fileName) {
		reader = new Reader();
		try {
			reader.read(fileName);
			graph = reader.graph();
		} catch (IOException e) {
			System.err.println("Invalid file name");
			System.exit(1);
		}
	}

	private boolean isPartOne(String[] args) {
		return args[0].equals("-p1");
	}
	
	private boolean isPartTwo(String[] args) {
		return args[0].equals("-p2");
	}

}
