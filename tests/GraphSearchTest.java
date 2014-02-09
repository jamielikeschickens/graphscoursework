import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class GraphSearchTest {
	private Graph graph;
	private List<Node> resultList;
	
	@Before
	public void setUp() {
		graph = new Graph();
		resultList = new ArrayList<Node>();
	}
	
	@Test
	public void emptyGraphReturnsNoNodesWithAtLeastZeroNeighboursReturnsEmptyList() throws Exception {
		assertEquals(resultList, runNeighbourSearch(0));
	}
	
	@Test
	public void graphWithOneNodeOneNeighbourWithAtLeastOneNeightbourReturnsOneNode() throws Exception {
		Node node1 = new Node("1");
		node1.addNeighbour(new Node("2"));
		graph.add(node1);
		resultList.add(node1);
		
		assertEquals(resultList, runNeighbourSearch(1));
	}
	
	@Test
	public void graphWithTwoNodesOneNodeWithAtLeastOneNeighbourReturnsOneNode() throws Exception {
		Node node1 = new Node("1");
		Node node3 = new Node("3");
		node1.addNeighbour(new Node("2"));
		graph.add(node1);
		graph.add(node3);
		resultList.add(node1);
		
		assertEquals(resultList, runNeighbourSearch(1));
	}
	
	@Test
	public void graphWithTwoNodesOneWithAtLeastTwoNeighboursReturnsOneNode() throws Exception {
		Node node1 = new Node("1");
		Node node3 = new Node("3");
		node1.addNeighbour(new Node("2"));
		node1.addNeighbour(new Node("4"));
		graph.add(node1);
		graph.add(node3);
		resultList.add(node1);

		assertEquals(resultList, runNeighbourSearch(2));
	}
	
	@Test
	public void graphWithTwoNodesBothWithAtLeastTwoNeighboursReturnsTwoNodes() throws Exception {
		Node node1 = new Node("1");
		Node node3 = new Node("3");
		node1.addNeighbour(new Node("2"));
		node1.addNeighbour(new Node("4"));
		node1.addNeighbour(new Node("5"));
		node3.addNeighbour(new Node("6"));
		node3.addNeighbour(new Node("7"));
		graph.add(node1);
		graph.add(node3);
		resultList.add(node1);
		resultList.add(node3);

		assertEquals(resultList, runNeighbourSearch(2));
	}
	
	public List<Node> runNeighbourSearch(int n) {
		return new GraphSearch(graph).neighbourSearch(n);
	}

}
