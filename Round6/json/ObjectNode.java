import java.util.Iterator;
import java.util.TreeMap;

public class ObjectNode extends Node implements Iterable<String>
{
    private final TreeMap<String, Node> nodes = new TreeMap<>();

    public ObjectNode() { }

    Node get(String key)
    {
        return nodes.get(key);
    }

    void set(String key, Node node)
    {
        nodes.put(key, node);
    }

    int size()
    {
        return nodes.size();
    }

    @Override
    public Iterator<String> iterator()
    {
        return nodes.keySet().iterator();
    }
}
