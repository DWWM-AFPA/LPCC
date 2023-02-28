package Export;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Node {
    protected String name;
    protected String text;
    protected static ArrayList<Node> nodeList = new ArrayList<>();
    protected static HashMap<String, Node> nodeRegistry = new HashMap<>();

    protected ArrayList<Node> nodeContained;


    //builder
    public Node() {
        this.setText("");
    }

    public Node(String name) {
        nodeList.add(this);
        this.setName(name);
        if (!name.equals("user") && !name.equals("dev"))
            Node.nodeRegistry.put(name, this);
        nodeContained = new ArrayList<>();
        //TODO this.setText("");
    }
    //getters


    public String getText() {
        return text;
    }

    //setters


    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HashMap<String, Node> getNodeRegistry() {
        return nodeRegistry;
    }

    public static void setNodeRegistry(HashMap<String, Node> nodeRegistry) {
        Node.nodeRegistry = nodeRegistry;
    }

    public ArrayList<Node> getNodeContained() {
        return nodeContained;
    }

    public void setNodeContained(ArrayList<Node> nodeContained) {
        this.nodeContained = nodeContained;
    }

    public static ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public static void setNodeList(ArrayList<Node> nodeList) {
        Node.nodeList = nodeList;
    }

    public void add(Node node) {
        nodeContained.add(node);
    }

/*    public static void getAllNodes(){
        for(Node name:nodeList) {
            System.out.println("-".repeat(20));
            System.out.println(((CodeNode) name).getName());
            System.out.println(name.getText());
            //   try {
            CodeNode node = (CodeNode) name;
            //HashMap<String, int[]> map = node.getCodeLocation();
            //System.out.println(map);

       *//* catch (NullPointerException e){
            System.out.println();
        }
*//*
        }
    }*/

    public String accept(Visitor visitor) {
        return null;
    }


   /* @Override
    public String toString() {
        if (this.getName() != null&&this.getNodeContained()!=null)
            return "Node name='" + name.toUpperCase() + '\'' +
                    ", nodeContained=\n  " + nodeContained +
                    '}';
        else if (this instanceof DocumentationNode&&this.nodeContained!=null) {
            return "{" +
                    "text='" + text + '\'' +
                    ", nodeContained=\n" + nodeContained +
                    '}';}
        else if (this instanceof DocumentationNode) {
            return "{" +
                    "text='" + text + "style ="+
                    ((DocumentationNode) this).getStyle()+
                    '}';
        }else if (this instanceof CodeNode) {
            return "\n  {" +
                    "code='" + text + '\'' +
                    ", nodeContained=\n     " + nodeContained +
                    '}';
        }
     return null;
    }*/
}


