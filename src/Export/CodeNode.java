package Export;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeNode extends Node {
  //  protected static HashMap<String,CodeNode> codeNodeRegistry;

    protected ArrayList<CodeNode> NodeContained;

    /** this particular constructor create a CodeNode which have to be the only one */
    private CodeNode(String name){
        super(name);
        ;
    }
    public static CodeNode getCodeNodeInstance(String name){
        CodeNode instance;
        HashMap<String, Node> map= Node.getNodeRegistry();
        if (!Node.getNodeRegistry().containsKey(name))
            instance =new CodeNode(name);
        else
            instance=(CodeNode) map.get(name);
        return instance;
    }
    public CodeNode(String name,String text){

        this.setText(text);
    }
    public CodeNode(String text,Node Parent){
        this.setText(text);
    }



    /*public ArrayList<CodeNode> getNodeContained() {
        return NodeContained;
    }

    public void setNodeContained(ArrayList<CodeNode> nodeContained) {
        NodeContained = nodeContained;
    }*/

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitCode(this);
    }
}
