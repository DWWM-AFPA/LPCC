package Export;

import java.util.ArrayList;

public class DocumentationNode extends Node{

    protected static ArrayList<DocumentationNode> documentationNodeRegistry;

    String style;

    public DocumentationNode(String text,String style){
        this.setText(text);
        this.setStyle(style);
    }
    public DocumentationNode(String name){
        super(name);
    }

    public static ArrayList<DocumentationNode> getCodeNodeRegistry() {
        return documentationNodeRegistry;
    }

    public static void setCodeNodeRegistry(ArrayList<DocumentationNode> documentationNodeRegistry) {
        DocumentationNode.documentationNodeRegistry = documentationNodeRegistry;
    }

    public static ArrayList<DocumentationNode> getDocumentationNodeRegistry() {
        return documentationNodeRegistry;
    }

    public static void setDocumentationNodeRegistry(ArrayList<DocumentationNode> documentationNodeRegistry) {
        DocumentationNode.documentationNodeRegistry = documentationNodeRegistry;
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
