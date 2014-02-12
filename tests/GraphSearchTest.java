import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		assertEquals(new ArrayList<Node>(), neighbourSearch(0));
	}
	
	@Test
	public void graphWithOneNodeOneNeighbourWithAtLeastOneNeightbourReturnsOneNode() throws Exception {
		Node node = new Node("1");
		node.addNeighbour(new Node("2"));
		graph.add(node);
		resultList.add(node);
		
		assertEquals(resultList, neighbourSearch(1));
	}
	
	@Test
	public void graphWithTwoNodesOneNodeWithAtLeastOneNeighbourReturnsOneNode() throws Exception {
		Node node = new Node("1");
		Node node3 = new Node("3");
		node.addNeighbour(new Node("2"));
		graph.add(node);
		graph.add(node3);
		resultList.add(node);
		
		assertEquals(resultList, neighbourSearch(1));
	}
	
	@Test
	public void graphWithTwoNodesOneWithAtLeastTwoNeighboursReturnsOneNode() throws Exception {
		Node node = new Node("1");
		Node node3 = new Node("3");
		node.addNeighbour(new Node("2"));
		node.addNeighbour(new Node("4"));
		graph.add(node);
		graph.add(node3);
		resultList.add(node);

		assertEquals(resultList, neighbourSearch(2));
	}
	
	@Test
	public void graphWithTwoNodesBothWithAtLeastTwoNeighboursReturnsTwoNodes() throws Exception {
		Node node = new Node("1");
		Node node_three = new Node("3");
		node.addNeighbour(new Node("2"));
		node.addNeighbour(new Node("4"));
		node.addNeighbour(new Node("5"));
		node_three.addNeighbour(new Node("6"));
		node_three.addNeighbour(new Node("7"));
		graph.add(node);
		graph.add(node_three);
		resultList.add(node);
		resultList.add(node_three);

		assertEquals(resultList, neighbourSearch(2));
	}
	
	@Test
	public void nodeWithNoNeighboursHasNoFullyConnectedNeighbours() throws Exception {
		graph.add(new Node("1"));
		assertNodeListEquals(new ArrayList<Node>(), findFullyConnectedNeighbours());
	}
	
	@Test
	public void twoNodesThatAreFullyConnected() throws Exception {
		Node node = new Node("1");
		Node node_two = new Node("2");
		node.addNeighbour(node_two);
		node_two.addNeighbour(node);
		graph.add(node);
		graph.add(node_two);
		resultList.add(node);
		resultList.add(node_two);
		assertNodeListEquals(resultList, findFullyConnectedNeighbours());
	}

	@Test
	public void twoNodesThatAreFullyConnectedAndOneNotFullyConnectedNode() throws Exception {
		Node node = new Node("1");
		Node node_two = new Node("2");
		Node node_three = new Node("3");
		Node node_four = new Node("4");
		node.addNeighbour(node_two);
		node_two.addNeighbour(node);
		node_three.addNeighbour(node_four);
		graph.add(node);
		graph.add(node_two);
		graph.add(node_three);
		graph.add(node_four);
		resultList.add(node);
		resultList.add(node_two);
		assertNodeListEquals(resultList, findFullyConnectedNeighbours());
	}
	
	@Test
	public void noFullyConnectedNeighboursTwoNodesWithOneNeighbourOneWithoutNeighbours() throws Exception {
		Node node = new Node("1");
		Node node_two = new Node("2");
		Node node_three = new Node("3");
		node.addNeighbour(node_two);
		node_two.addNeighbour(node_three);
		graph.add(node);
		graph.add(node_two);
		graph.add(node_three);
		assertNodeListEquals(new ArrayList<Node>(), findFullyConnectedNeighbours());
	}
	
	@Test
	public void twoFullyConnectedNodesWithOneNotFullyConnected() throws Exception {
		Node node = new Node("1");
		Node node_two = new Node("2");
		Node node_three = new Node("3");
		Node node_four = new Node("4");
		
		node.addNeighbour(node_two);
		node.addNeighbour(node_three);
		
		node_two.addNeighbour(node);
		node_two.addNeighbour(node_three);
		
		node_three.addNeighbour(node);
		node_three.addNeighbour(node_two);
		node_three.addNeighbour(node_four);
		
		graph.add(node);
		graph.add(node_two);
		graph.add(node_three);
		graph.add(node_four);
		
		resultList.add(node);
		resultList.add(node_two);
		assertNodeListEquals(resultList, findFullyConnectedNeighbours());
	}
	
	@Test
	public void threeFullyConnectedNeighboursTwoNotFullyConnected() throws Exception {
		Node node = new Node("1");
		Node node_two = new Node("2");
		Node node_three = new Node("3");
		Node node_four = new Node("4");
		Node node_five = new Node("5");
		
		node.addNeighbour(node_two);
		node.addNeighbour(node_three);
		node.addNeighbour(node_four);
		node.addNeighbour(node_five);
		
		node_two.addNeighbour(node);
		node_two.addNeighbour(node_four);
		node_two.addNeighbour(node_five);
		
		node_three.addNeighbour(node);
		
		node_four.addNeighbour(node);
		node_four.addNeighbour(node_two);
		node_four.addNeighbour(node_five);
		
		node_five.addNeighbour(node);
		node_five.addNeighbour(node_two);
		node_five.addNeighbour(node_four);
		
		graph.add(node);
		graph.add(node_two);
		graph.add(node_three);
		graph.add(node_four);
		graph.add(node_five);
		
		resultList.add(node_two);
		// Three has only 1 neighbour Node 1, which is also connected to it. Therefore fully connected
		resultList.add(node_three);
		resultList.add(node_four);
		resultList.add(node_five);
		assertNodeListEquals(resultList, findFullyConnectedNeighbours());
	}
	
	@Test
	public void emptyGraphHasNoCliques() throws Exception {
		assertEquals(0, getCliquesOfSize(0));
	}
	
	@Test
	public void oneNodeHasNoCliques() throws Exception {
		Node node = new Node("1");
		graph.add(node);
		
		assertEquals(0, getCliquesOfSize(0));
		assertEquals(0, getCliquesOfSize(1));
	}
	
	@Test
	public void twoConnectedNodesHaveOneCliqueOfSizeTwo() throws Exception {
		Node node = new Node("1");
		Node node_two = new Node("2");
		
		node.addNeighbour(node_two);
		node_two.addNeighbour(node);
		
		graph.add(node);
		graph.add(node_two);
		
		assertEquals(1, getCliquesOfSize(2));
	}
	
	@Test
	public void threeConnectedNodesHaveThreeCliquesOfSizeTwo() throws Exception {
		Node node = new Node("1");
		Node node_two = new Node("2");
		Node node_three = new Node("3");
		
		node.addNeighbour(node_two);
		node.addNeighbour(node_three);
		node_two.addNeighbour(node);
		node_two.addNeighbour(node_three);
		node_three.addNeighbour(node);
		node_three.addNeighbour(node_two);
		
		graph.add(node);
		graph.add(node_two);
		graph.add(node_three);
		
		assertEquals(3, getCliquesOfSize(2));
	}

	private int getCliquesOfSize(int sizeOfClique) {
		return new GraphSearch(graph).cliquesOfSize(sizeOfClique);
	}
	
	private void assertNodeListEquals(List<Node> expected, List<Node> actual) {
		String errorMessage = "Expected: " + createArrayOfNodeNames(expected) + " " +
								"Actual: " + createArrayOfNodeNames(actual);
		for (Node item : expected) {
			assertTrue(errorMessage, actual.contains(item));
		}
		
		for (Node item : actual) {
			assertTrue(errorMessage, expected.contains(item));
		}
	}
	
	private List<String> createArrayOfNodeNames(List<Node> nodes) {
		List<String> listOfNodeNames = new ArrayList<>();
		for (Node node : nodes) {
			listOfNodeNames.add(node.name());
		}
		return listOfNodeNames;
	}
	
	private List<Node> findFullyConnectedNeighbours() {
		return new GraphSearch(graph).findFullyConnectedNodes();
	}
	
	private List<Node> neighbourSearch(int n) {
		return new GraphSearch(graph).neighbourSearch(n);
	}

}
