package Export;

import java.util.ArrayList;

public class LateXDoc implements Documentation,Visitor{
    @Override
    public void user(ArrayList<DocumentationNode> n) {
        String retour="";
        for (DocumentationNode doc:n) {
            doc.remove("user");
            String node=doc.getText();
            while(!doc.getArgs().isEmpty()){
                switch (doc.getArgs().get(0)){
                    case("bd"): node="\textbd{"+node+"}";
                                break;
                    case("it"):node="\textit"+node+"}";
                                break;
                    default: return;
                    }
                }
            retour=node+retour;
            }
        System.out.println(retour);
        }

    @Override
    public void dev(ArrayList<DocumentationNode> n) {

    }

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
}
