package Export;

import java.util.ArrayList;

public class DocumentationNode extends Node{


    String style;

    public DocumentationNode(String text,String style){
        this.setText(text);
        this.setStyle(style);
    }
    public DocumentationNode(String name){
        super(name);
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

/*    public ArrayList<DocumentationNode> getNodeContained() {
        return NodeContained;
    }

    public void setNodeContained(ArrayList<DocumentationNode> nodeContained) {
        NodeContained = nodeContained;
    }*/





}
