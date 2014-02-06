import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GraphSerializerTest {

	private Graph graph;
	
	@Before
	public void setUp() {
		graph = new Graph();
	}

	@Test
	public void emptyGraphReturnsEmptyString() {
		assertEquals("", serializeGraph());
	}
	
	@Test
	public void graphWithOneNodeAtOneReturnsOne() throws Exception {
		addNode("1");
		assertEquals("1\n", serializeGraph());
	}
	
	@Test
	public void graphWithOneNodeAtTwoReturnsTwo() throws Exception {
		addNode("2");
		assertEquals("2\n", serializeGraph());
	}
	
	@Test
	public void graphWithOneNodeAndOneNeighbourReturnsNodeAndNeighbour() throws Exception {
		Node node = new Node("1");
		node.addNeighbour(new Node("2"));
		graph.add(node);
		assertEquals("1 2\n", serializeGraph());
	}
	
	@Test
	public void graphWithOneNodeAndTwoNeighboursReturnsNodeAndBothNeighbours() throws Exception {
		Node node = new Node("1");
		node.addNeighbour(new Node("2"));
		node.addNeighbour(new Node("3"));
		graph.add(node);
		assertEquals("1 2 3\n", serializeGraph());
	}

	@Test
	public void graphWithTwoNodesAndTwoNeighbours() throws Exception {
		Node node = new Node("1");
		node.addNeighbour(new Node("3"));
		Node node2 = new Node("2");
		node2.addNeighbour(new Node("4"));
		graph.add(node);
		graph.add(node2);
		assertEquals("1 3\n2 4\n", serializeGraph());
	}
	private String serializeGraph() {
		return new GraphSerializer(graph).serialize();
	}
	
	private void addNode(String nodeName) {
		graph.add(new Node(nodeName));
	}
	


}
