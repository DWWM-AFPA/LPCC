package Export;

import java.util.ArrayList;

public class DocumentationNode extends Node{


    ArrayList<String> style;

    public DocumentationNode(String text,ArrayList<String> style){

        this.setText(text);
        this.cloneStyle(style);
    }
    public DocumentationNode(String name){
        super(name);
    }

    public ArrayList<String> getStyle() {
        return this.style!=null ? style:this.setStyle(new ArrayList<>());
    }
    public String getStyle(int pos) {
        return this.style!=null ? this.getStyle().get(pos):null;
    }

    public ArrayList<String> setStyle(ArrayList<String> style) {
        return this.style = style;
    }
    public ArrayList<String> cloneStyle(ArrayList<String> style) {
        if (style==null)
            return null;
        return this.setStyle((ArrayList<String>) style.clone());
    }

    public void addStyle(String style){
        this.getStyle().add(style);
    }


/*    public ArrayList<DocumentationNode> getNodeContained() {
        return NodeContained;
    }

    public void setNodeContained(ArrayList<DocumentationNode> nodeContained) {
        NodeContained = nodeContained;
    }*/


    public String accept(Visitor visitor) {
        return visitor.visitDocumentation(this);
    }


}
