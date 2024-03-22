import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class AVLNodeExperiment {
    String data;
    AVLNodeExperiment left;
    AVLNodeExperiment right;
    int height;

    /**
     * Constructs an AVLNode with the given data.
     *
     * @param data The data to be stored in the node.
     */
    public AVLNodeExperiment(String data) {
        this.data = data;
        left = right = null;
        height = 1;
    }
}

class AVLTreeExperiment {
    AVLNodeExperiment root;
    private int searchOpCount = 0;
    private int insertOpCount = 0;

    /**
     * Constructs an empty AVL tree.
     */
    public AVLTreeExperiment() {
        root = null;
    }

    public int getSearchOpCount() {
        return searchOpCount;
    }

    public int getInsertOpCount() {
        return insertOpCount;
    }

    public void resetSearchOpCount() {
        searchOpCount = 0;
    }

    public void insert(String data) {
        root = insertRec(root, data);
        if (!isBalanced(root)) {
            root = balance(root);
        }
    }

    private AVLNodeExperiment insertRec(AVLNodeExperiment node, String data) {
        if (node == null) {
            insertOpCount++;
            return new AVLNodeExperiment(data);
        }

        if (data.compareTo(node.data) < 0) {
            node.left = insertRec(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insertRec(node.right, data);
        } else {
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalanceFactor(node);

        // If node becomes unbalanced after new data is inserted, then there are 4 cases

        // Left Left Case
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public boolean search(String searchTerm) {
        return searchRec(root, searchTerm);
    }

    private boolean searchRec(AVLNodeExperiment node, String searchTerm) {
        if (node == null) {
            return false;
        }

        String[] parts = node.data.split("\t");
        String term = parts[0];

        int comparisonResult = searchTerm.compareTo(term);
        if (comparisonResult == 0) {
            searchOpCount++;
            return true;
        } else if (comparisonResult < 0) {
            searchOpCount++;
            return searchRec(node.left, searchTerm);
        } else {
            searchOpCount++;
            return searchRec(node.right, searchTerm);
        }
    }

    /**
     * Calculates the height of a node.
     *
     * @param node The node to calculate the height for.
     * @return The height of the node.
     */
    private int getHeight(AVLNodeExperiment node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Calculates the balance factor for a given node.
     *
     * @param node The node to calculate the balance factor for.
     * @return The balance factor of the node.
     */
    private int getBalanceFactor(AVLNodeExperiment node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Rotates a node on the AVL tree left
     *
     * @param y The node to perform the rotation on.
     * @return The new root node after rotation.
     */
    private AVLNodeExperiment rightRotate(AVLNodeExperiment y) {
        AVLNodeExperiment x = y.left;
        AVLNodeExperiment swapVariable = x.right;

        // Perform rotation
        x.right = y;
        y.left = swapVariable;

        // Update heights
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        // Return new root
        return x;
    }

    /**
     * Rotates a node on the AVL tree left.
     *
     * @param x The node to perform the rotation on.
     * @return The new root node after rotation.
     */
    private AVLNodeExperiment leftRotate(AVLNodeExperiment x) {
        AVLNodeExperiment y = x.right;
        AVLNodeExperiment swapVariable = y.left;

        // Perform rotation
        y.left = x;
        x.right = swapVariable;

        // Update heights
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        // Return new root
        return y;
    }

    private boolean isBalanced(AVLNodeExperiment node) {
        int balanceFactor = getBalanceFactor(node);
        return balanceFactor >= -1 && balanceFactor <= 1;
    }

    private AVLNodeExperiment balance(AVLNodeExperiment node) {
        if (node == null) {
            return null;
        }

        int balanceFactor = getBalanceFactor(node);

        // Left subtree is heavier
        if (balanceFactor > 1) {
            // Left-Left case
            if (getHeight(node.left.left) >= getHeight(node.left.right)) {
                return rightRotate(node);
            }
            // Left-Right case
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // Right subtree is heavier
        else if (balanceFactor < -1) {
            // Right-Right case
            if (getHeight(node.right.right) >= getHeight(node.right.left)) {
                return leftRotate(node);
            }
            // Right-Left case
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }
}

public class Experimentation {
    private static final int[] datasetSizes = { 10, 100, 1000, 10000, 100000 }; // Dataset sizes
    private static final int numQueries = 10; // Number of queries
    private static final String queryFile = "GenericsKB-queries.txt"; // Query file

    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("experiment_results.txt");

            // Write column headers
            writer.write("Dataset Size\tInsert Min\tInsert Max\tInsert Avg\tSearch Min\tSearch Max\tSearch Avg\n");

            // Read dataset from file
            List<String> dataset = readDatasetFromFile("GenericsKB.txt");

            // Iterate over dataset sizes
            for (int size : datasetSizes) {
                List<Integer> insertOpCounts = new ArrayList<>();
                List<Integer> searchOpCounts = new ArrayList<>();

                // Perform experiment for current dataset size
                for (int i = 0; i < 10; i++) {
                    AVLTree avl = new AVLTree();
                    List<String> subset = generateRandomSubset(size, dataset);

                    // Load data into AVL tree
                    for (String item : subset) {
                        avl.insert(item);
                    }

                    // Perform searches and record operation counts
                    int insertOpCount = avl.getInsertOpCount();
                    int searchOpCount = 0;
                    try {
                        Scanner scanner = new Scanner(new File(queryFile));
                        while (scanner.hasNextLine()) {
                            String query = scanner.nextLine().trim();
                            boolean found = avl.search(query);
                            if (found) {
                                searchOpCount = avl.getSearchOpCount(); // Add current search count
                            }
                            // Reset search operation count for the next iteration
                            avl.resetSearchOpCount();
                        }
                        scanner.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    insertOpCounts.add(insertOpCount);
                    searchOpCounts.add(searchOpCount);
                }

                // Calculate min, max, and average of operation counts
                int minInsertOpCount = insertOpCounts.stream().min(Integer::compareTo).orElse(0);
                int maxInsertOpCount = insertOpCounts.stream().max(Integer::compareTo).orElse(0);
                int avgInsertOpCount = (int) insertOpCounts.stream().mapToInt(Integer::intValue).average().orElse(0);
                int minSearchOpCount = searchOpCounts.stream().min(Integer::compareTo).orElse(0);
                int maxSearchOpCount = searchOpCounts.stream().max(Integer::compareTo).orElse(0);
                int avgSearchOpCount = (int) searchOpCounts.stream().mapToInt(Integer::intValue).average().orElse(0);

                // Write experiment results to file
                writer.write(String.format("%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n", size, minInsertOpCount,
                        maxInsertOpCount, avgInsertOpCount, minSearchOpCount, maxSearchOpCount, avgSearchOpCount));
            }

            writer.close();
            System.out.println("Experiment completed. Results written to experiment_results.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readDatasetFromFile(String fileName) {
        List<String> dataset = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                dataset.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private static List<String> generateRandomSubset(int size, List<String> dataset) {
        List<String> subset = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(dataset.size());
            subset.add(dataset.get(index));
        }
        return subset;
    }
}
