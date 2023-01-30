package Export;


public class LateXDoc implements Documentation,Visitor{
    @Override
    public void user(ArrayList<DocumentationNode> n) {
        String retour="";
        for (DocumentationNode doc:n) {
            doc.remove("user");
            String node;
            int titleindex=0;
            for (int i = 1; i < 6; i++) {
                if(doc.getArgs().contains("title"+i))
                    titleindex=i;
            }
            switch (titleindex){
                case 0: node=this.nodeTosring(doc);
                        break;
                case 1: doc.remove("title1");
                        node=this.nodeTosring(doc);
                        node="\\part{"+node+"}";
                        break;
                case 2:doc.remove("title2");
                        node=this.nodeTosring(doc);
                        node="\\chapter{"+node+"}";
                        break;
                case 3:doc.remove("title3");
                        node=this.nodeTosring(doc);
                        node="\\section{"+node+"}";
                        break;
                case 4:doc.remove("title4");
                        node=this.nodeTosring(doc);
                        node="\\subsection{"+node+"}";
                        break;
                case 5:doc.remove("title5");
                        node=this.nodeTosring(doc);
                        node="\\subsubsection{"+node+"}";
                        break;
                default:return;
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

    private String nodeTosring(DocumentationNode doc){
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
                case("img"):
                    node="\\begin{figure}"+'\n'+"\\centering"+'\n'+"\\includegraphics{"+node+"}"+'\n'+"\\end{figure}";
                    doc.remove("img");
                    break;
                case("link"):
                    node="\\url{"+node+"}";
                    doc.remove("link");
                    break;
                default:
                    return null;
            }
        }
        return node;
    }
}
