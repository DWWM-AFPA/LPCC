package Export;

import java.util.ArrayList;

public abstract class Node {
    protected String text;
    protected static ArrayList<Node> nodeList= new ArrayList<>();

    //getters

public static void getAllNodes(){
    for(Node name:nodeList) {
        System.out.println("-".repeat(20));
        System.out.println(((CodeNode) name).getName());
        System.out.println(name.getText());

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

}
