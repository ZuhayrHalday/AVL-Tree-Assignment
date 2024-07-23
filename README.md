## Technical Specifications

### Program Description

The program implements an AVL Tree, a self-balancing binary search tree. It supports insertion and search operations, ensuring that the tree remains balanced after each insertion. This AVL tree is used to store and retrieve data from a knowledge base, with each node containing a term, a statement, and a confidence score.

### Program Components

1. **AVLNode Class**
   - Represents a node in the AVL tree.
   - Stores data, references to left and right child nodes, and the height of the node.

2. **AVLTree Class**
   - Implements the AVL Tree data structure.
   - Supports insertion and search operations.
   - Maintains balance by performing rotations (left and right) as needed.
   - Tracks the number of insert and search operations.

3. **GenericsKbAVLApp Class**
   - Main application class to run the AVL tree operations.
   - Reads data from a knowledge base file and inserts it into the AVL tree.
   - Processes search queries from a file and outputs the results.
   - Displays the total number of insert and search operations.

### Usage

1. **Compiling the Program**

```shell
javac GenericsKbAVLApp.java
```

2. **Running the Program**

```shell
java GenericsKbAVLApp
```

3. **Input**

   - The program prompts for the name of the knowledge base file and the query file.
   - The knowledge base file should contain data in the format: `term\tstatement\tconfidence`.
   - The query file should contain terms to search for, one per line.

### Example Input Files

- **Knowledge Base File (kb.txt)**

```
apple\tA fruit\t0.9
banana\tA yellow fruit\t0.8
cat\tAn animal\t0.95
dog\tAnother animal\t0.85
```

- **Query File (queries.txt)**

```
apple
cat
elephant
```

### Example Output

```
Enter the name of the knowledge base file: kb.txt
Enter the name of the query file: queries.txt

Knowledge base loaded successfully.

apple: A fruit (0.9)

cat: An animal (0.95)

Term not found: "elephant"

Total Search Operations: 4
Total Insert Operations: 4
```

### Detailed Class and Method Descriptions

#### AVLNode Class

- **String data**: Stores the data for the node (term, statement, confidence).
- **AVLNode left**: Reference to the left child node.
- **AVLNode right**: Reference to the right child node.
- **int height**: Height of the node in the AVL tree.

**Constructor**:

- `public AVLNode(String data)`: Initializes the node with the given data, sets left and right child nodes to null, and height to 1.

#### AVLTree Class

- **AVLNode root**: Root node of the AVL tree.
- **int searchOpCount**: Counter for the number of search operations.
- **int insertOpCount**: Counter for the number of insert operations.

**Constructor**:

- `public AVLTree()`: Initializes an empty AVL tree.

**Methods**:

- `public int getSearchOpCount()`: Returns the number of search operations.
- `public int getInsertOpCount()`: Returns the number of insert operations.
- `public void insert(String data)`: Inserts a new node with the given data.
- `private AVLNode insertRec(AVLNode node, String data)`: Recursive method to insert data into the AVL tree.
- `public boolean search(String searchTerm)`: Searches for a term in the AVL tree.
- `private boolean searchRec(AVLNode node, String searchTerm)`: Recursive method to search for a term in the AVL tree.
- `private int getHeight(AVLNode node)`: Returns the height of a node.
- `private int getBalanceFactor(AVLNode node)`: Returns the balance factor of a node.
- `private AVLNode rightRotate(AVLNode y)`: Performs a right rotation on the given node.
- `private AVLNode leftRotate(AVLNode x)`: Performs a left rotation on the given node.
- `private boolean isBalanced(AVLNode node)`: Checks if a node is balanced.
- `private AVLNode balance(AVLNode node)`: Balances the AVL tree.

#### GenericsKbAVLApp Class

**Fields**:

- `private static AVLTree avlTree`: Instance of the AVLTree class.

**Methods**:

- `public static void main(String[] args)`: Main method to run the AVL tree application.
- `private static void readKB(String fileName)`: Reads and inserts the knowledge base from the specified file into an AVL Tree.
- `private static void searchQueries(String fileName)`: Performs AVL Tree searches based on queries from the specified file.

### Error Handling

- The program handles `FileNotFoundException` when the specified knowledge base or query file is not found, displaying an appropriate error message.

### Program Execution Flow

1. **Initialization**: The AVL tree is initialized.
2. **User Input**: The user is prompted to enter the names of the knowledge base file and the query file.
3. **Reading Knowledge Base**: The knowledge base file is read, and data is inserted into the AVL tree.
4. **Processing Queries**: The query file is read, and search operations are performed on the AVL tree.
5. **Output Results**: The results of the search operations and the total number of insert and search operations are displayed.
