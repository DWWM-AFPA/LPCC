package Export;
import Util.LPCFile;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class LateXDoc implements Visitor{

    protected  Hashtable<String,String> corresbalise;
    private String projectname;

    //getters

    public LateXDoc(Hashtable<String,String> corresbalise,String projectname) throws FileNotFoundException {
        this.corresbalise=corresbalise;
        this.projectname=projectname;
    }

    //setters

    public void setCorresbalise(Hashtable<String,String> corresbal0){
       corresbalise=corresbal0;
    }

    public void user(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        try {
            LPCFile.create(LPCFile.getOutputDirectory(),projectname+"_user","tex",nodeinterpret(n));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Une erreur a eu lieu lors de la creation du fichier");
        }
    }

    public void dev(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        for (DocumentationNode doc: n) {
            doc.remove("dev");
        }
        try {
            LPCFile.create(LPCFile.getOutputDirectory(),projectname+"_dev","tex",nodeinterpret(n));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Une erreur a eu lieu lors de la creation du fichier");

        }
    }

    @Override
    public void visit(ArrayList<Node> compilation) {
        ArrayList<DocumentationNode> dev=new ArrayList<>();
        ArrayList<DocumentationNode> user=new ArrayList<>();

        for (Node n: compilation) {
            if(n instanceof DocumentationNode){
                //n.setText(n.getText().replaceAll("\n","\\\\newline    "));
                if(((DocumentationNode) n).getArgs().contains("dev")){
                    dev.add((DocumentationNode) n);
                }
                else {
                    user.add((DocumentationNode) n);
                }
            }
        }
        try {
            if (!user.isEmpty())
                this.user(user);
        } catch (LPCSyntaxException e) {
            JOptionPane.showInputDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        try {
            if (!dev.isEmpty())
                this.dev(dev);
        } catch (LPCSyntaxException e) {
            JOptionPane.showInputDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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
        return node.toString();
    }

    private String nodeinterpret(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        StringBuilder retour= new StringBuilder();
        retour.append("\\begin{document}");
        for (int j=0;j<n.size();j++) {
            DocumentationNode doc=n.get(j);
            String node="";
            int titleindex=0;
            for (int i = 1; i < 6; i++) {
                if(doc.getArgs().contains("title"+i)) {
                    if (titleindex == 0)
                            titleindex = i;
                    else
                        throw new LPCSyntaxException("Un titre doit être d'un seul type");
                }
            }
            if(doc.isDiagram()){
                int coupurepos=doc.getText().indexOf(':');
                String[] infos= {doc.getText().substring(0,coupurepos),doc.getText().substring(coupurepos+1)};
                File img=doc.readdiagramm(infos[0]);
                node=("\\begin{figure}" + '\n' + "\\centering" + '\n' + "\\includegraphics{"
                        + img.getName() + "}" + '\n' + "\\end{figure}");
            }
            else {
                System.out.println("titleindex=" + titleindex);
                switch (titleindex) {
                    case 0:
                        node = this.nodeTosring(doc);
                        break;
                    case 1:
                        doc.remove("title1");
                        System.out.println("Je passe bien par titre 1");
                        node = this.nodeTosring(doc);
                        node = "\\part{" + node;
                        System.out.println("next node contain title1" + n.get(j + 1).getArgs().contains("title1"));
                        while (j < n.size() && n.get(j + 1).getArgs().contains("title1")) {
                            System.out.println("je passe par le while " + node);
                            n.get(j + 1).remove("title1");
                            node = node + nodeTosring(n.get(j + 1));
                            if (j + 1 < n.size())
                                j++;
                            else
                                node = node + "}";
                        }
                        node = node + "}";
                        break;
                    case 2:
                        doc.remove("title2");
                        node = this.nodeTosring(doc);
                        node = "\\chapter{" + node;
                        while (j < n.size() && n.get(j + 1).getArgs().contains("title2")) {
                            n.get(j + 1).remove("title2");
                            node = node + nodeTosring(n.get(j + 1));
                            j++;
                        }
                        node = node + "}";
                        break;
                    case 3:
                        doc.remove("title3");
                        node = this.nodeTosring(doc);
                        node = "\\section{" + node;
                        while (j < n.size() && n.get(j + 1).getArgs().contains("title3")) {
                            n.get(j + 1).remove("title3");
                            node = node + nodeTosring(n.get(j + 1));
                            j++;
                        }
                        node = node + "}";
                        break;
                    case 4:
                        doc.remove("title4");
                        node = this.nodeTosring(doc);
                        node = "\\subsection{" + node;
                        while (j < n.size() && n.get(j + 1).getArgs().contains("title4")) {
                            n.get(j + 1).remove("title4");
                            node = node + nodeTosring(n.get(j + 1));
                            j++;
                        }
                        node = node + "}";
                        break;
                    case 5:
                        doc.remove("title5");
                        node = this.nodeTosring(doc);
                        node = "\\subsubsection{" + node;
                        while (j < n.size() && n.get(j + 1).getArgs().contains("title5")) {
                            n.get(j + 1).remove("title5");
                            node = node + nodeTosring(n.get(j + 1));
                            j++;
                        }
                        node = node + "}";
                        break;
                    default:
                        break;
                }
            }
            retour.append(node);
        }
        retour.append("\\end{document}");
        System.out.println("retour="+retour);
        return retour.toString();
    }

}
