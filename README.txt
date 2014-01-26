Not entering any contests
My run method does four things:
1) stores where the ship node is

2) executes initialTraversal() which traverses the island's nodes using a depth first search
while it does this, it also stores an adjacency list of the island-graph and a list of nodes
where animals can be found

3) executes collectAnimals() which calls a method multiple time called findShortestPath() 
which uses a breadth first search to calculate the shortest path between two nodes. 
collectAnimals moves the Naturalist so that he collects animals up to the MAX_ANIMAL_CAPACITY 
using the shortest path each time and then returns to the ship

4) drops animals off on the ship after each time collectAnimals() is executed.

Overall, I thought this was a very good and educational assignment.
I don't have much to say about the competition since I don't plan to enter 
any of the contests but I would like to say that I feel like there should 
have been more exposure to the pseudo code for depth first search and
breadth first search given to students in class.

Other than that, I liked how all the assignments were linked together in some way 
and it's been fun coding these assignments. 
