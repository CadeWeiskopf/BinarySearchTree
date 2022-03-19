//import java.util.Arrays;
import java.util.Scanner;

class BinarySearchTree {
    static Node nodes[];
    static Tree tree;

    public static void main(String[] args) {
        String input;
        int inputInts[] = null;
        boolean validInput;
        Scanner scanner = new Scanner(System.in);
        do {
            // get input (integers separated by spaces)
            printListPrompt();
            input = scanner.nextLine();
            input = input.trim();
            validInput = input.matches("[-\\d ]+");

            if (!validInput) {
                System.out.println("[!] Check input string (valid example: 1 2 -3 4 30 100 2603)...");
                continue;
            }
        

            // parse input into integers
            input = input.replaceAll("[ ]+", " ");
            String inputStrings[] = input.split(" ");
            inputInts = new int[inputStrings.length];
            for (int i = 0; i < inputStrings.length; i++) {
                try {
                    inputInts[i] = Integer.parseInt(inputStrings[i].trim());
                } catch (NumberFormatException e) {
                    System.out.println("[!] Check input string (valid example: 1 2 -3 4 30 100 2603)...");
                    validInput = false;
                    break;
                }
            }

        } while (!validInput);

        // make nodes and tree
        setNodes(inputInts);
        tree = new Tree(nodes);
        System.out.println(tree);

        input = "";
        validInput = false;
        int inputInt = 0;
        do {
            // get input (integer to search for)
            printSearchPrompt();
            input = scanner.nextLine();
            input = input.trim();
            validInput = input.matches("[-*\\d]+");
            if (!validInput) {
                System.out.println("[!] Please enter an integer that exists in the tree...");
                continue;
            }

            validInput = false;
            try {
                inputInt = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter an integer that exists in the tree...");
                validInput = false;
                continue;
            }
            for (int i : inputInts) {
                if (inputInt == i) {
                    validInput = true;
                    break;
                }
            }

            if (!validInput) {
                System.out.println("[!] Please enter an integer that exists in the tree...");
            }
        } while (!validInput);

        scanner.close();

        String searchRoute = tree.search(inputInt);
        System.out.println(searchRoute);
    }

    static void setNodes(int inputInts[]) {
        //Arrays.sort(inputInts);
        nodes = new Node[inputInts.length];
        for (int i = 0; i < inputInts.length; i++) {
            Node node = new Node(inputInts[i]);
            nodes[i] = node;
        }
    }

    static void printListPrompt() {
        System.out.println("\nInput list of integers separated by spaces (valid example: 1 2 3 4 30 100 2603)");
        System.out.print("=> ");
    }

    static void printSearchPrompt() {
        System.out.println("\nInput integer to search for");
        System.out.print("=> ");
    }
}

class Node {
    Node left, right;
    int value, index;

    Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (BinarySearchTree.tree != null && this == BinarySearchTree.tree.root) {
            return "Root=" + value + " (L=" + (left == null ? left : left.value) + " R=" + (right == null ? right : right.value) + ")";
        }
        return "Node=" + value + " (L=" + (left == null ? left : left.value) + " R=" + (right == null ? right : right.value) + ")";
    }
}

class Tree {
    Node nodes[];
    Node root;

    Tree(Node nodes[]) {
        this.nodes = nodes;

        // if sorted and want a balanced tree, use index = length/2
        //              int rootIndex = nodes.length / 2;
        // not sorting just use index = 0
        int rootIndex = 0;
        root = nodes[rootIndex];

        for (int i = 0; i < nodes.length; i++) {
            Node compareTo = root;
            Node parentNode = null;

            if (i == rootIndex) {
                continue;
            } else {
                boolean isLeft;

                if (nodes[i].value <= compareTo.value) {
                    // go to left
                    parentNode = compareTo;
                    compareTo = compareTo.left;
                    isLeft = true;
                    while (compareTo != null) {
                        parentNode = compareTo;
                        if (nodes[i].value <= compareTo.value) {
                            compareTo = compareTo.left;
                            isLeft = true;
                        } else {
                            compareTo = compareTo.right;
                            isLeft = false;
                        }
                    }
                } else {
                    // go to the right
                    parentNode = compareTo;
                    compareTo = compareTo.right;
                    isLeft = false;
                    while (compareTo != null) {
                        parentNode = compareTo;
                        if (nodes[i].value <= compareTo.value) {
                            compareTo = compareTo.left;
                            isLeft = true;
                        } else {
                            compareTo = compareTo.right;
                            isLeft = false;
                        }
                    }
                }

                if (isLeft) {
                    parentNode.left = nodes[i];
                } else {
                    parentNode.right = nodes[i];
                }
            }
        }
    }

    String search(int value) {
        System.out.println("\nSearching for " + value + "...");

        Node current = root;
        String route = "START";

        do {
            route += " -> " + current.value;
            if (value == current.value) {
                current = null;
            } else if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        } while (current != null);

        return route + "\n";
    }

    @Override
    public String toString() {
        String outString = "\nTREE:\n";

        for (Node node : nodes) {
            outString += node + "\n";
        }
        return outString;
    }
}