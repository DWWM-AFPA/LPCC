package Export;
import Util.LPCFile;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class LateXDoc implements Visitor{

    protected  Hashtable<String,String> corresbalise;

    //getters

    public LateXDoc(Hashtable<String,String> corresbalise) throws FileNotFoundException {
        this.corresbalise=corresbalise;
    }

    public LateXDoc(String configname) throws FileNotFoundException {

    }

    //setters

    public void setCorresbalise(Hashtable<String,String> corresbal0){
       corresbalise=corresbal0;
    }

    public void user(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        StringBuilder retour= new StringBuilder();
        retour.append("\\begin{document}");
        for (DocumentationNode doc:n) {
            //doc.remove("user");
            String node;
            int titleindex=0;
            for (int i = 1; i < 6; i++) {
                if(doc.getArgs().contains("title"+i)) {
                    if(titleindex == 0)
                        titleindex = i;
                    else
                        throw new LPCSyntaxException("Un titre doit être d'un seul type");
                }
            }
            System.out.println("title ibndex="+titleindex);
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
            retour.append(node);
            }
        retour.append("\\end{document}");
        try {
            LPCFile.create(LPCFile.getOutputDirectory(),"Userdoc","tex",retour.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dev(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        StringBuilder retour= new StringBuilder();
        retour.append("\\begin{document}");
        for (DocumentationNode doc:n) {
            System.out.println("est ce que je passe ici ?");
            doc.remove("dev");
            String node;
            int titleindex=0;
            for (int i = 1; i < 6; i++) {
                if(doc.getArgs().contains("title"+i))
                    if(titleindex==0)
                        titleindex = i;
                    else
                        throw new LPCSyntaxException("Un titre doit être d'un seul type");
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
        retour.append("\\end{document}");
        try {
            LPCFile.create(LPCFile.getOutputDirectory(),"Devdoc","tex",retour.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                else {
                    user.add((DocumentationNode) n);
                }
            }
        }
        System.out.println("user length= "+user.size());
        try {
            this.user(user);
        } catch (LPCSyntaxException e) {
            //TODO message renvoyé
            new JOptionPane(e.getMessage());
        }
        try {
            this.dev(dev);
        } catch (LPCSyntaxException e) {
            //TODO message renvoyé
            new JOptionPane(e.getMessage());
        }
    }

    private String nodeTosring(DocumentationNode doc){
        StringBuilder node= new StringBuilder(doc.getText());

        while(!doc.getArgs().isEmpty()){
            System.out.println("node to string= "+node.toString());
            if(doc.getArgs().get(0).length()>4&&doc.getArgs().get(0).substring(0,5).equals("color")){
                String colorvalue=doc.getArgs().get(0).substring(6);
                node = new StringBuilder("\\textcolor{" + colorvalue + "}" + "{" + node + "}");
                doc.remove(doc.getArgs().get(0));
            }
            else {
                String firstarg=doc.getArgs().get(0);
                switch (firstarg) {
                    case ("img"):
                        node = new StringBuilder("\\begin{figure}" + '\n' + "\\centering" + '\n' + "\\includegraphics{"
                                + node + "}" + '\n' + "\\end{figure}");
                        doc.remove("img");
                        break;
                    default:
                        node=new StringBuilder(this.corresbalise.get(doc.getArgs().get(0))+node+"}");
                        System.out.println(firstarg);
                        System.out.println(node);
                        doc.remove(doc.getArgs().get(0));
                }
            }
        }
        System.out.println("node to string= "+node);
        return node.toString();
    }
}
