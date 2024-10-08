import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Treap extends AbstractTree implements Searchable{

    //class to store data for build method
    public class Data {
        private int value;
        private String key;

        public Data(int value, String key) {
            this.key = key;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }

    }
    private Node root;
    private int size;

    //constructor to initialize treap
    public Treap() {
        root = null;
    }

    /*
        @Params :-
        key :- string key which is to be added
        value :- value associated with specific key
    * */
    @Override
    public boolean add(String key, int heapValue) {

        //checking boundary cases
        if(key == null || heapValue<0) return false;

        //checking whether key already present in the tree
        if(searchInBST(root, key) != null) return false;

        //using addNode function from AbstractTree class
        root = addNode(root, key, heapValue);

        if(root == null) return false;

        size++;
        return true;
    }

    /*
        @Params :-
        keys :- arrays of keys which is to be added
        heapValues :- arrays of value for each of the key
    * */
    @Override
    public boolean build(String[] keys, int[] heapValues) {


        if(keys == null || heapValues == null) return false;

        int n = keys.length, m = heapValues.length;

        if(n != m) return false;

        Data[] data = new Data[n];

        //looping through each key-value to put it in Data class
        for(int i=0;i<n;i++) {
            if(heapValues[i] < 0) return false;
            data[i] = new Data(heapValues[i], keys[i]);
        }

        //custom sort to sort the array descending by heapValue
        Arrays.sort(data, (o1, o2) -> (o1.getValue() - o2.getValue())*-1);

        if(root != null) {
            root = null;
            size = 0;
        }

        //looping through key and adding it to treap
        for(int i=0;i<n;i++) {

            if(find(data[i].key)) return false;

            //as the first element will be the root as it has the highest heapValue
            if(i == 0) {
                if(!add(data[i].getKey(), data[i].getValue())) {
                    return false;
                }
            }else {
                //For rest of the elements we will simply do insertion in binary search tree
                if(addNodeInBST(root, new Node(data[i].getKey(), data[i].getValue())) == null) {
                    return false;
                }
                size++;
            }
        }

        return true;
    }

    /*
        @Params :-
        key :- key to be searched in treap
     */
    @Override
    public boolean find(String key) {

        if(key == null) return false;

        //call searchInBST of AbstractTree class
        return searchInBST(root, key) != null;
    }

    /*
        @Params :-
        key :- key of which path is to be found
    * */
    @Override
    public List<String> findPath(String key) {
        //check if it does not exist in treap
        if(!find(key)) return null;

        List<String> path = new ArrayList<>();

        //call findPathInBST to find path in binary Search Tree
        findPathInBST(root, key, path);

        return path;
    }

    /*
        @Params :-
        key :- key of which the value is to be changed
        heapValue :- new Value
    */
    @Override
    public boolean changeOrder(String key, int heapValue) {

        //check boundary cases
        if(key == null || heapValue<0 || !find(key)) return false;

        //remove the key and add it again with new heapValue
        return remove(key) && add(key, heapValue);
    }

    /*
        @Params :-
        key :- key which is to be removed
    */
    @Override
    public boolean remove(String key) {

        //check boundary cases
        if(key == null || !find(key)) return false;

        //call removeNode from AbstractTree class
        root = removeNode(root, key);

        //check after removing , whether it exist or not
        if(find(key)) return false;

        size--;

        return true;
    }

    /*
        return the number of elements present in the treap
    */
    @Override
    public int size() {
        return size;
    }

    //getter for root
    public Node getRoot() {
        return root;
    }

    /*
        @Params :-
        node :- root of the treap
        key :- key whose path is to found
        path :- List inside which we will push the path from key to root

        returns path from key to root recursively
    * */
    protected void findPathInBST(Node node,String key, List<String> path) {
        if(node == null) return;

        //push current node to the list
        path.add(0, node.getKey());

        //if current node key is greater the passed key then move left, if smaller than move right
        if(customCompare(node.getKey(), key) == 0) {
            return;
        } else if (customCompare(node.getKey(), key) > 0) {
            findPathInBST(node.getLeft(), key, path);
        }else {
            findPathInBST(node.getRight(), key, path);
        }

    }

    public boolean audit() {
        return isMaxHeap(root) && isBinarySearchTree(root);
    }

    private boolean isMaxHeap(Node node) {
        if (node == null) {
            return true;
        }

        if (node.getRight() != null && node.getValue() < node.getRight().getValue()) {
            return false;
        }

        if (node.getLeft() != null && node.getValue() < node.getLeft().getValue()) {
            return false;
        }

        return isMaxHeap(node.getLeft()) && isMaxHeap(node.getRight());
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
