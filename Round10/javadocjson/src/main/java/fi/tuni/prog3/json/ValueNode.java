package fi.tuni.prog3.json;

/**
 * A class for representing a JSON value. The value can be either a double, a boolean, a String or null.
 */
public final class ValueNode extends Node
{
    /**
     * Constructs a JSON value node that stores the given double value.
     *
     * @param value The double value to store in the new JSON value node.
     */
    public ValueNode(double value)
    {

    }

    /**
     * Constructs a JSON value node that stores the given boolean value.
     *
     * @param value The boolean value to store in the new JSON value node.
     */
    public ValueNode(boolean value)
    {

    }

    /**
     * Constructs a JSON value node that stores the given string or null.
     *
     * @param value The string or null to store in the new JSON value node.
     */
    public ValueNode(String value)
    {

    }

    /**
     * Checks whether this value node stores a number (double).
     *
     * @return true if this node stores a double value, otherwise false.
     */
    public boolean isNumber()
    {
        return true;
    }

    /**
     * Checks whether this value node stores a boolean value.
     *
     * @return true if this node stores a boolean value, otherwise false.
     */
    public boolean isBoolean()
    {
        return true;
    }

    /**
     * Checks whether this value node stores a string.
     *
     * @return true if this node stores a string, otherwise false.
     */
    public boolean isString()
    {
        return true;
    }

    /**
     * Checks whether this value node stores null.
     *
     * @return true if this node stores null, otherwise false.
     */
    public boolean isNull()
    {
        return true;
    }

    /**
     * Returns the stored value as a number (double).
     *
     * @return the stored number as a double value.
     * @throws IllegalStateException if the stored value is not a number.
     */
    public double getNumber()
    {
        return 0;
    }

    /**
     * Returns the stored value as a boolean value.
     *
     * @return the stored boolean value.
     * @throws IllegalStateException if the stored value is not a boolean value.
     */
    public boolean getBoolean()
    {
        return true;
    }

    /**
     * Returns the stored value as a string.
     *
     * @return the stored string.
     * @throws IllegalStateException if the stored value is not a string.
     */
    public String getString()
    {
        return "";
    }

    /**
     * Returns the stored value as null.
     *
     * @return null.
     * @throws IllegalStateException if the stored value is not null.
     */
    public Object getNull()
    {
        return null;
    }


}
