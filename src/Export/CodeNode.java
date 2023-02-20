package Export;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeNode extends Node {
  //  protected static HashMap<String,CodeNode> codeNodeRegistry;

    protected ArrayList<CodeNode> NodeContained;

    public CodeNode(String text,Node Parent){
    this.setText(text);
    }
    public CodeNode(String name){
    super(name);
    }



    /*public ArrayList<CodeNode> getNodeContained() {
        return NodeContained;
    }

    public void setNodeContained(ArrayList<CodeNode> nodeContained) {
        NodeContained = nodeContained;
    }*/


}
