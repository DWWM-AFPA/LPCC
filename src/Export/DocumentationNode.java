package Export;

import java.util.ArrayList;

public class DocumentationNode extends Node{

    protected static ArrayList<DocumentationNode> documentationNodeRegistry;

    protected ArrayList<DocumentationNode> NodeContained;

    public DocumentationNode(String text,String style){
        this.setText(text);
    }
    public DocumentationNode(String name){
        super(name);
        this.setName(name);
    }

    public static ArrayList<DocumentationNode> getCodeNodeRegistry() {
        return documentationNodeRegistry;
    }

    public static void setCodeNodeRegistry(ArrayList<DocumentationNode> documentationNodeRegistry) {
        DocumentationNode.documentationNodeRegistry = documentationNodeRegistry;
    }


/*    public ArrayList<DocumentationNode> getNodeContained() {
        return NodeContained;
    }

    public void setNodeContained(ArrayList<DocumentationNode> nodeContained) {
        NodeContained = nodeContained;
    }*/





}
