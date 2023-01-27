package Export;

import java.util.ArrayList;

public class LateXDoc implements Documentation,Visitor{
    @Override
    public void user(ArrayList<DocumentationNode> n) {
        String retour="";
        for (DocumentationNode doc:n) {
            doc.remove("user");
            String node;
            if(doc.getArgs().contains("title1")) {
                doc.remove("title1");
                node=this.nodeTosring(doc);
            node="\\part{"+node+"}";
            }
            else {
                if(doc.getArgs().contains("title2")) {
                    doc.remove("title2");
                    node = this.nodeTosring(doc);
                    node="\\chapter{"+node+"}";
                }
                else {
                    if(doc.getArgs().contains("title3")){
                        doc.remove("title3");
                        node = this.nodeTosring(doc);
                        node="\\section{"+node+"}";
                    }
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

    public String nodeTosring(DocumentationNode doc){
        String node=doc.getText();
        while(!doc.getArgs().isEmpty()){
            switch (doc.getArgs().get(0)) {
                case ("bd"):
                    node = "\\textbd{" + node + "}";
                    doc.remove("bd");
                    break;
                case ("it"):
                    node = "\\textit{" + node + "}";
                    doc.remove("it");
                    break;
                case("ul"):
                    node="\\underline{"+node+"}";
                    doc.remove("ul");
                    break;
                default:
                    return null;
            }
        }
        return node;
    }
}
