package Export;

import java.util.ArrayList;

public class LateXDoc implements Documentation,Visitor{
    @Override
    public void user(ArrayList<DocumentationNode> n) {
        StringBuilder retour= new StringBuilder();
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
            retour.insert(0,node);
            }
        System.out.println(retour);
        }

    @Override
    public void dev(ArrayList<DocumentationNode> n) {
        StringBuilder retour= new StringBuilder();
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
            retour.insert(0, node);
        }
        //File f=new File()
        System.out.println(retour);
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
        StringBuilder node= new StringBuilder(doc.getText());
        while(!doc.getArgs().isEmpty()){
            if(doc.getArgs().get(0).length()>4&&doc.getArgs().get(0).substring(0,5).equals("color")){
                String colorvalue=doc.getArgs().get(0).substring(6);
                node = new StringBuilder("\\textcolor{" + colorvalue + "}" + "{" + node + "}");
                doc.remove(doc.getArgs().get(0));
            }
            else {
                switch (doc.getArgs().get(0)) {
                    case ("bd"):
                        node = new StringBuilder("\\textbd{" + node + "}");
                        doc.remove("bd");
                        break;
                    case ("it"):
                        node = new StringBuilder("\\textit{" + node + "}");
                        doc.remove("it");
                        break;
                    case ("ul"):
                        node = new StringBuilder("\\underline{" + node + "}");
                        doc.remove("ul");
                        break;
                    case ("img"):
                        node = new StringBuilder("\\begin{figure}" + '\n' + "\\centering" + '\n' + "\\includegraphics{" + node + "}" + '\n' + "\\end{figure}");
                        doc.remove("img");
                        break;
                    case ("link"):
                        node = new StringBuilder("\\url{" + node + "}");
                        doc.remove("link");
                        break;
                    default:
                        return null;
                }
            }
        }
        return node.toString();
    }
}
