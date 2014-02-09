import java.util.List;


public class GraphSerializer {

	private Graph graph;

	public GraphSerializer(Graph graph) {
		this.graph = graph;
	}

	public String serialize() {
		List<Node> nodes = graph.nodes();
		if (nodes.size() == 0)	return "";

		String nodeAndNeighbours = "";
		for (Node node : nodes) {
			nodeAndNeighbours += node.name();
			for (Node neighbour : node.neighbours()) {
				nodeAndNeighbours += " ";
				nodeAndNeighbours += neighbour.name();
			}				
			nodeAndNeighbours += "\n";
		}
		return nodeAndNeighbours;
	}
}
