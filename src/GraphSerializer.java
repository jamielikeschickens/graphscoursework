import java.util.List;


public class GraphSerializer {

	private Graph graph;

	public GraphSerializer(Graph graph) {
		this.graph = graph;
	}

	public String serialize() {
		List<Node> nodes = graph.nodes();
		if (nodes.size() == 0) {
			return "";
		} else {
			String nodeAndNeighbours = "";
			for (int i = 0; i < largestNode(); i++) {
				Node node = graph.find(Integer.toString(i));
				if (node != null) {
					nodeAndNeighbours += node.name();
					for (int j = 0; j < largestNode(); j++) {
						Node neighbour = node.neighbours().get(j);
						nodeAndNeighbours += " ";
						nodeAndNeighbours += neighbour.name();
					}				
					nodeAndNeighbours += "\n";
				}
			}
			return nodeAndNeighbours;
		}
	}

	private int largestNode() {
		int maxNode = 1;
		for (Node n : graph.nodes()) {
			int currentNode = Integer.parseInt(n.name());
			if (currentNode > maxNode)
				maxNode = currentNode;
		}
		return maxNode;
	}

}
