package Export;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class LateXDoc implements Visitor{

    protected  Hashtable<String,String> corresbal;

    //getters

    public void setCorresbal(Hashtable<String,String> corresbal0){
       corresbal=corresbal0;
    }



    public LateXDoc() throws FileNotFoundException {
        Hashtable<String,String> corresdefault=new Hashtable<>();
        java.io.File in=new File("defaultlatex.config");
        String line;
        String[] part;
        Scanner sc=new Scanner(in);
        while (sc.hasNextLine()){
            line= sc.nextLine();
            part=line.split(";");
            corresdefault.put(part[0],part[1]);
        }
        setCorresbal(corresdefault);
    }

    public LateXDoc(String configname) throws FileNotFoundException {
        java.io.File in=new File(configname);
        String [] parts;
        Scanner sc = new Scanner(in);
        String line;
        Hashtable<String,String> corresdefault = new Hashtable<>();
        while (sc.hasNextLine()){
            line=sc.nextLine();
            parts = line.split(";");
            corresdefault.put(parts[0],parts[1]);
        }
        setCorresbal(corresdefault);
    }

    public void user(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        StringBuilder retour= new StringBuilder();
        retour.append("\\begin{document}");
        for (DocumentationNode doc:n) {
            doc.remove("user");
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
        retour.append("\\end{document}");
        System.out.println(retour);
        }

    public void dev(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        StringBuilder retour= new StringBuilder();
        for (DocumentationNode doc:n) {
            doc.remove("user");
            String node;
            int titleindex=0;
            for (int i = 1; i < 6; i++) {
                if(doc.getArgs().contains("title"+i))
                    titleindex=i;
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
            if(doc.getArgs().get(0).length()>4&&doc.getArgs().get(0).substring(0,5).equals("color")){
                String colorvalue=doc.getArgs().get(0).substring(6);
                node = new StringBuilder("\\textcolor{" + colorvalue + "}" + "{" + node + "}");
                doc.remove(doc.getArgs().get(0));
            }
            else {
                String firstarg=doc.getArgs().get(0);
                switch (firstarg) {
                    case ("img"):
                        node = new StringBuilder("\\begin{figure}" + '\n' + "\\centering" + '\n' + "\\includegraphics{" + node + "}" + '\n' + "\\end{figure}");
                        doc.remove("img");
                        break;
                    default:
                        node=new StringBuilder(this.corresbal.get(doc.getArgs().get(0))+node+"}");
                        doc.remove(doc.getArgs().get(0));
                }
            }
        }
        return node.toString();
    }
}
