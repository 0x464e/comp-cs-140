package fi.tuni.prog3.json;

/**
 * An abstract super class for different types of JSON nodes.
 */
public abstract class Node {
  /**
   * Checks whether this node represents a JSON value.
   * @return true if this node represents a JSON value, otherwise false.
   */
  public boolean isValue() {
    return this instanceof ValueNode;
  }

  /**
   * Checks whether this node represents a JSON array.
   * @return true if this node represents a JSON array, otherwise false.
   */
  public boolean isArray() {
    return this instanceof ArrayNode;
  }

  /**
   * Checks whether this node represents a JSON object.
   * @return true if this node represents a JSON object, otherwise false.
   */
  public boolean isObject() {
    return this instanceof ObjectNode;
  }

  /**
   * The only constructor. Will be invoked, usually implicitly, by subclass constructors.
   */
  protected Node() { }

  public void printSimple() {
    StringBuilder sb = new StringBuilder();
    printSimple(this, sb);
    System.out.print(sb.toString());
  }

  public void printJson() {
    throw new UnsupportedOperationException("printJson has not been implemented!");
  }

  private static final String NL = System.lineSeparator();

  private static String numberToString(Double d) {
    String str = Double.toString(d);
    if(str.endsWith(".0")) {
      str = str.substring(0, str.length() - 2);
    }
    return str;
  }

  private void printSimple(Node node, StringBuilder sb) {
    if(node.isObject()) {
      sb.append("fi.tuni.prog3.json.ObjectNode").append(NL);
      ObjectNode objNode = (ObjectNode) node;
      for(String name : objNode) {
        sb.append(name).append(": ");
        printSimple(objNode.get(name), sb);
      }
    }
    else if(node.isArray()) {
      sb.append("fi.tuni.prog3.json.ArrayNode").append(NL);
      ArrayNode arrNode = (ArrayNode) node;
      for(Node aNode : arrNode) {
        printSimple(aNode, sb);
      }
    }
    else if(node.isValue()) {
      ValueNode valNode = (ValueNode) node;
      String typeStr = "NullValue";
      String valStr = "null";
      if(valNode.isNumber()) {
        typeStr = "NumberValue";
        valStr = numberToString(valNode.getNumber());
      }
      else if(valNode.isBoolean()) {
        typeStr = "BooleanValue";
        valStr = Boolean.toString(valNode.getBoolean());
      }
      else if(valNode.isString()) {
        typeStr = "StringValue";
        valStr = "\"" + valNode.getString() + "\"";
      }
      sb.append(String.format("%s(%s)%n", typeStr, valStr));
    }
  }
}
