import java.util.ArrayList;
import java.util.Iterator;

public class ArrayNode extends Node implements Iterable<Node>
{
    private final ArrayList<Node> nodes = new ArrayList<>();

    public ArrayNode() { }

    @Override
    public Iterator<Node> iterator()
    {
        return nodes.iterator();
    }

    void add(Node node)
    {
        nodes.add(node);
    }

    int size()
    {
        return nodes.size();
    }
}
