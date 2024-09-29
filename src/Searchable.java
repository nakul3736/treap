import java.util.List;

public interface Searchable {
    public boolean add(String key, int heapValue);

    public boolean build(String[] keys, int[] heapValues);

    public boolean find(String key);

    public List<String> findPath(String key);

    public boolean changeOrder(String key, int heapValue);

    public boolean remove(String key);

    public int size();
}
