package Export;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Node {
    protected String name;
    protected String text;
   // protected static ArrayList<Node> nodeList= new ArrayList<>();
   protected static HashMap<String,Node> nodeRegistry = new HashMap<>();

    protected ArrayList<Node> nodeContained;


    //builder
    public Node(){
        this.setText("");
    }

    public Node(String name){
        //nodeList.add(this);
        nodeRegistry.put(name,this);
        nodeContained=new ArrayList<>();
        this.setText("");
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



    public void add(Node node){
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

}


