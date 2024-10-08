/*
    SearchTree basically keeps the most searched key to the top and
    least frequent used element at leaf.

    We will extend AbstrctTree and make heap value equals to number of times it is accessed
* */
public class SearchTree extends AbstractTree {

    private Node root;
    private int size;

    //constructor for searchTree
    public SearchTree() {
        root = null;
    }

    /*
        @Params :- key to be added in searchTree
    * */
    public boolean add(String key) {

        //check if key is null, or it already exists in tree
        if(key == null || searchInBST(root, key.toLowerCase()) != null) return false;

        //call addNode from AbtractTree to add node to tree, pass 0 as intial heap value
        root = addNode(root, key, 0);

        if(root == null) return false;

        //increment size
        size++;
        return true;
    }

    /*
        @Params :-
        key :- Key to be searched
    * */
    public boolean find(String key) {

        //check for null key
        if(key == null) return false;

        //call search from AbstractTree to search key in tree
        Node res = searchInBST(root, key.toLowerCase());

        //if it does not exist return false
        if(res == null) return false;

        //As we access the element we will increment the heap value by 1
        int heapValue = res.getValue();

        //remove the element and insert again with incremented heap value
        remove(key);
        root = addNode(root, key, heapValue+1);
        size++;
        return true;
    }

    /*
        @Params :-
        key :- key to be removed
    * */
    public boolean remove(String key) {

        //check if key is null or if it does not exist
        if(key == null || searchInBST(root, key) == null) return false;

        //call removeNode from AbstractTree class to remove key
       root = removeNode(root, key);

        //check if it exists after removal
        if(find(key)) return false;

        size--;

        return true;
    }

    public boolean audit() {
        return isHeapOrdered(root) && isBinarySearchTree(root);
    }

    private boolean isHeapOrdered(Node node) {
        if (node == null) {
            return true;
        }

        if (node.getLeft() != null && node.getValue() < node.getLeft().getValue()) {
            return false;
        }

        if (node.getRight() != null && node.getValue() < node.getRight().getValue()) {
            return false;
        }

        return isHeapOrdered(node.getLeft()) && isHeapOrdered(node.getRight());
    }

    private boolean isBinarySearchTree(Node node) {
        if (node == null) {
            return true;
        }

        if (node.getLeft() != null && customCompare(node.getKey(), node.getLeft().getKey()) < 0) {
            return false;
        }

        if (node.getRight() != null && customCompare(node.getKey(), node.getRight().getKey()) > 0) {
            return false;
        }

        return isBinarySearchTree(node.getLeft()) && isBinarySearchTree(node.getRight());
    }
}
