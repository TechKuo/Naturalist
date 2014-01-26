import java.util.*;

public class NaturalistTech extends Naturalist {
	
	HashMap<Node, Node[]> AdjList = new HashMap<Node, Node[]>(); 	//adjacency list for graph of a5
	ArrayList<Node> AnimalNodes = new ArrayList<Node>();			//list of nodes which contain animals
	Node ship = null;												//Node for the ship
	int i = 0;														//integer representing index of AnimalNodes
	
	/**Main method for a5. Moves a naturalist through an island so he collects animals on the island efficiently*/
	public void run() {
		ship = getLocation();
		initialTraversal();
		while (i <= AnimalNodes.size()-1)	{
			collectAnimals();
			dropAll();
		}
	}
	
	/**Method to initially traverse the map and store node data*/
	public void initialTraversal(){
		//Depth First Search
		Stack<Node> s = new Stack<Node>();
		Node current = getLocation();
		s.push(current);
		current.setUserData(new NodeData());									//Mark node as visited
		AdjList.put(getLocation(), getExits());									//store children of current node to create an adjacency list
		while(!s.isEmpty())
		{
			Node parent = s.peek();
			moveTo(parent);
			Node child = getUnvisitedChildNode();
			if(child!=null)
			{
				moveTo(child);
				child.setUserData(new NodeData());
				AdjList.put(getLocation(), getExits()); 						
				if(listAnimalsPresent() != null && !listAnimalsPresent().isEmpty()) 
					{AnimalNodes.add(child);} 									//store location of nodes with animals
				s.push(child);
			}
			else
			{	
				s.pop();
			}
		}
		clearNodes(); 
	}
	
	/**Finds the shortest path between two nodes x and y using a breadth first search.*/
	public ArrayList<Node> findShortestPath(Node x, Node y){
		//Breadth First Search
		Queue<Node> q = new LinkedList<Node>();
		q.add(x);
		x.setUserData(new NodeData());
		while (!q.isEmpty()){
			Node parent = q.remove();
			if (parent.equals(y)) break;
			Node child = null;
			while((child = getUnvisitedChildNode(parent)) != null){
				child.setUserData(new NodeData(parent));			//Store the parent of each node
				q.add(child);
			}
		}
		ArrayList<Node> path = new ArrayList<Node>();				//Stores path(series of nodes) from x to y
		Node current = y;
		while (!current.equals(x)){
			path.add(current);
			NodeData currentNodeData = current.getUserData();
			current = currentNodeData.previous;
		}
		clearNodes();
		Collections.reverse(path);									//Reverse path since it was computed in a backwards order
		return path;
	}
		
	/**Returns an accessible node adjacent to the current node that has not been visited. 
	 * If all accessible and adjacent nodes have been visited, returns null.*/
	public Node getUnvisitedChildNode(){
		Node unvisited = null;
		for (Node n : getExits()){
			if (n.getUserData() == null){
				unvisited = n;
				break;
			}
		}
		return unvisited;
	}
	
	/**Does exactly what the method above does except it uses the adjacency List created 
	 * by the initial traversal.*/
	public Node getUnvisitedChildNode(Node x){
		Node unvisited = null;
		for (Node n : AdjList.get(x)){
			if (n.getUserData() == null){
				unvisited = n;
				break;
			}
		}
		return unvisited;
	}
	
	/**Method for clearing visited property of nodes*/
	public void clearNodes(){
		for (Node n: AdjList.keySet()) n.setUserData(null);
	}
	
	/**Method for collecting animals on the island. It collects
	 * animals up to the MAX_ANIMAL_CAPACITY before returning to the ship.*/
	public void collectAnimals(){
		int inventory = 0;																//number of animals the Naturalist is carrying.
		while (inventory < MAX_ANIMAL_CAPACITY && i != AnimalNodes.size()){
			ArrayList<Node> path = findShortestPath(getLocation(),AnimalNodes.get(i));	//stores path from current location to first animal node
			for (Node n: path) moveTo(n);												//move to node containing animal(s)
			for (String animal : listAnimalsPresent()){
				if (inventory < MAX_ANIMAL_CAPACITY) {
					collect(animal);
					inventory++;
				}	
			}
			if(i <= AnimalNodes.size()-1 && listAnimalsPresent().isEmpty()) i++;
			else break;
		}
		ArrayList<Node> returnPath = findShortestPath(getLocation(),ship);				//stores path from current Location to ship
		for (Node n: returnPath) moveTo(n);												//return to ship when at MAX_ANIMAL_CAPACITY
	}
	
	/**Class for data stored in objects of the class Node. An instance 
	 * represents a datum stored in a Node.*/
	class NodeData {
			boolean visited = false; 													//stores whether a node has been visited or not
			Node previous = null;														//stores previous node traversed in bfs

			public NodeData(){															//default constructor, used simply when a node has been visited
				visited = true;
			}
			
			public NodeData(Node p){													//second constructor, used in calculating the shortest path between two nodes
				previous = p;
			}
						
	}


}

