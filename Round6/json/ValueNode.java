public class ValueNode<T> extends Node
{
    private final T value;

    public ValueNode(T value)
    {
        this.value = value;
    }

    boolean isNumber()
    {
        return value instanceof Number;
    }

    boolean isBoolean()
    {
        return value instanceof Boolean;
    }

    boolean isString()
    {
        return value instanceof String;
    }

    boolean isNull()
    {
        return value == null;
    }

    double getNumber()
    {
        return (int) value;
    }

    boolean getBoolean()
    {
        return (boolean) value;
    }

    String getString()
    {
        return (String) value;
    }
}
