package fi.tuni.prog3.json;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A class for representing a JSON array.
 */
public class ArrayNode extends Node implements Iterable<Node>
{
    private final ArrayList<Node> nodes = new ArrayList<>();

    /**
     * Constructs an initially empty JSON array node.
     */
    public ArrayNode() { }

    /**
     * Returns a fi.tuni.prog3.json.Node iterator that iterates the JSON nodes stored in this JSON array.
     * @return a fi.tuni.prog3.json.Node iterator that iterates the JSON nodes stored in this JSON array.
     */
    @Override
    public Iterator<Node> iterator()
    {
        return nodes.iterator();
    }

    /**
     * Adds a new JSON node to the end of this JSON array.
     * @param node the new JSON node to be added.
     */
    void add(Node node)
    {
        nodes.add(node);
    }

    /**
     * Returns the number of JSON nodes stored in this JSON array.
     * @return the number of JSON nodes in this JSON array.
     */
    int size()
    {
        return nodes.size();
    }
}
