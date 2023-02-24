package Export;

public interface Visitor {
    //public void visit(ArrayList<Node> compilation);
    public String visitDocumentation(DocumentationNode documentationNode);
    public String visitCode(CodeNode codeNode);
}
