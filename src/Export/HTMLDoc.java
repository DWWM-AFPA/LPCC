package Export;

import Export.Visitor;

import java.util.ArrayList;

public class HTMLDoc implements Visitor,Documentation{
    @Override
    public void visit(ArrayList<Node> compilation) {
        ArrayList<DocumentationNode> dev=new ArrayList<>();
        ArrayList<DocumentationNode> user=new ArrayList<>();
        for (Node n: compilation) {
            if(n instanceof DocumentationNode){
                if(((DocumentationNode) n).getArgs().contains("dev")){
                    dev.add((DocumentationNode) n);
                }
                if(((DocumentationNode) n).getArgs().contains("user")){
                    user.add((DocumentationNode) n);
                }
            }
        }
        this.user(user);
        this.dev(dev);
    }

    @Override
    public void user(ArrayList<DocumentationNode> n) {

    }

    @Override
    public void dev(ArrayList<DocumentationNode> n) {

    }
}
