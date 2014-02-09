import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GraphSearch {
	private Reader reader;
	private Graph graph;

	public static void main(String[] args) {
		String fileName = args[1];
		GraphSearch search = new GraphSearch();
		if (search.isPartOne(args)) {
			search.readGraph(fileName);
			search.printGraph(fileName);
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

	private void printGraph(String fileName) {
		GraphSerializer serializer = new GraphSerializer(reader.graph());
		print(serializer.serialize());
	}

	private void print(String output) {
		System.out.print(output);
	}

	private void readGraph(String fileName) {
		reader = new Reader();
		try {
			reader.read(fileName);
		} catch (IOException e) {
			System.err.println("Invalid file name");
			System.exit(1);
		}
	}

	private boolean isPartOne(String[] args) {
		return args[0].equals("-p1");
	}

}
