package Export;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class HTMLExportVisitor implements Visitor{
    public String export(ArrayList<Node> nodes){
        StringBuilder retour = new StringBuilder();
        for (Node node:nodes) {
            System.out.println(node.getClass());
            if (node instanceof DocumentationNode) {
                String nodeName = node.accept(this);
                System.err.println(nodeName );
            }
            else if (node instanceof CodeNode) {
                System.err.println(node.accept(this));
            }
        }
        return retour.toString();
    }

    @Override
    public String visitDocumentation(DocumentationNode documentationNode) {
        if (this.getName() != null && this.getNodeContained() != null)
            return "Node name='" + name.toUpperCase() + '\'' +
                    ", nodeContained=\n  " + nodeContained +
                    '}';
        else if (this instanceof DocumentationNode && this.nodeContained != null) {
            return "{" +
                    "text='" + text + '\'' +
                    ", nodeContained=\n" + nodeContained +
                    '}';
        } else if (this instanceof DocumentationNode) {
            return "{" +
                    "text='" + text + "style =" +
                    ((DocumentationNode) this).getStyle() +
                    '}';
        } else if (this instanceof CodeNode) {
            return "\n  {" +
                    "code='" + text + '\'' +
                    ", nodeContained=\n     " + nodeContained +
                    '}';
        }
        return null;
    }

    @Override
    public String visitCode(CodeNode codeNode) {
        if (this.getName() != null&&this.getNodeContained()!=null)
            return "Node name='" + name.toUpperCase() + '\'' +
                    ", nodeContained=\n  " + nodeContained +
                    '}';
        else if (this instanceof DocumentationNode&&this.nodeContained!=null) {
            return "{" +
                    "text='" + text + '\'' +
                    ", nodeContained=\n" + nodeContained +
                    '}';}
        else if (this instanceof DocumentationNode) {
            return "{" +
                    "text='" + text + "style ="+
                    ((DocumentationNode) this).getStyle()+
                    '}';
        }else if (this instanceof CodeNode) {
            return "\n  {" +
                    "code='" + text + '\'' +
                    ", nodeContained=\n     " + nodeContained +
                    '}';
        }
        return null;
    }
}
