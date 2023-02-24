package Export;


public class CodeasDiagr implements Visitor {
   /* @Override
    public void visit(ArrayList<Node> compilation) {

    }*/

    @Override
    public String visitDocumentation(DocumentationNode documentationNode) {
    return null;
    }

    @Override
    public String visitCode(CodeNode codeNode) {

        return null;
    }
}
