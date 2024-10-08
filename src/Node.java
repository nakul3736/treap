public class Node {
    private String key;
    private int value;

    private Node left;
    private Node right;
    private Node parent;


    public Node(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //getter for value
    public int getValue() {
        return value;
    }

    //getter for key
    public String getKey() {
        return key;
    }

    //setter for left
    public void setLeft(Node node) {
        left = node;
    }

    //setter for right
    public void setRight(Node node) {
        right = node;
    }

    //getter for right
    public Node getLeft() {
        return left;
    }

    //getter for right
    public Node getRight() {
        return right;
    }

    //setter for parent
    public void setParent(Node node) {
        parent = node;
    }

    //getter for parent
    public Node getParent() {
        return parent;
    }

}
