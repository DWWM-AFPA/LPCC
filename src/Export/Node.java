package Export;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Node {
    protected String text;
    protected static ArrayList<Node> nodeList= new ArrayList<>();

    //getters

public static void getAllNodes(){
    for(Node name:nodeList) {
        System.out.println("-".repeat(20));
        System.out.println(((CodeNode) name).getName());
        System.out.println(name.getText());
     //   try {
        CodeNode node = (CodeNode) name;
        HashMap<String, int[]> map = node.getCodeLocation();
        System.out.println(map);

       /* catch (NullPointerException e){
            System.out.println();
        }
*/
    }
}
    public String getText() {
        return text;
    }

    //setters


    public void setText(String text) {
        this.text = text;
    }

    //builder

    public Node(){
        nodeList.add(this);
        this.setText("");
    }

    public Node(String text){
        this.setText(text);
    }

    /*public boolean nodeExists(String name){
    return nodeList.contains()
    }*/

}


