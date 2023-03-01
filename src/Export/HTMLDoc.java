package Export;
import net.sourceforge.plantuml.SourceStringReader;
import Export.Visitor;
import Util.LPCFile;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class HTMLDoc implements Visitor{

    protected Hashtable<String,String> corresbalise;

    private String projectname;

    //getters

    public HTMLDoc(Hashtable<String,String> corresbalise,String projectname) throws FileNotFoundException {
        this.corresbalise=corresbalise;
        this.projectname=projectname;
    }

    //setters

    public void setCorresbalise(Hashtable<String,String> corresbal0){
        corresbalise=corresbal0;
    }

    @Override
    public void visit(ArrayList<Node> compilation) {
        ArrayList<DocumentationNode> dev = new ArrayList<>();
        ArrayList<DocumentationNode> user = new ArrayList<>();
        for (Node n : compilation) {
            if (n instanceof DocumentationNode) {
                if (((DocumentationNode) n).getArgs().contains("dev")) {
                    dev.add((DocumentationNode) n);
                }
                if (((DocumentationNode) n).getArgs().contains("user")) {
                    user.add((DocumentationNode) n);
                }
            }
        }
        try {
            if (!user.isEmpty())
                this.user(user);
        } catch (LPCSyntaxException e) {
            JOptionPane.showInputDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            if (!dev.isEmpty())
                this.dev(dev);
        } catch (LPCSyntaxException e) {
            JOptionPane.showInputDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void user(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        try {
            LPCFile.create(LPCFile.getOutputDirectory(),projectname+"_user","html",nodeinterpret(n));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Une erreur a eu lieu lors de la creation du fichier");
        }
    }


    public void dev(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        for (DocumentationNode doc: n) {
            doc.remove("dev");
        }
        try {
            LPCFile.create(LPCFile.getOutputDirectory(),projectname+"_dev","html",nodeinterpret(n));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Une erreur a eu lieu lors de la creation du fichier");
        }
    }


    private String nodeinterpret(ArrayList<DocumentationNode> n) throws LPCSyntaxException {
        StringBuilder retour= new StringBuilder();
        retour.append("<!DOCTYPE html>").append('\n').append("<html>").append('\n').append("<body>");
        for (int j=0;j<n.size();j++) {
            DocumentationNode doc = n.get(j);
            String node = "";
            int titleindex = 0;
            for (int i = 1; i < 6; i++) {
                if (doc.getArgs().contains("title" + i)) {
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
                retour.append("<img src=" + img.getAbsolutePath() + "/>");
            }
            else {
                System.out.println("titleindex=" + titleindex);
                if (titleindex==0) {
                    retour.append("<p>");
                    node = this.nodeTosring(doc);
                    retour.append(node);
                    retour.append("</p>");
                }
                else {
                    doc.remove("title"+titleindex);
                    node = this.nodeTosring(doc);
                    node = "<h"+titleindex+">" + node;
                    while (j < n.size() && n.get(j + 1).getArgs().contains("title"+titleindex)) {
                        n.get(j + 1).remove("title"+titleindex);
                        node = node + nodeTosring(n.get(j + 1));
                        if (j + 1 < n.size())
                            j++;
                        else
                            node = node + "</h"+titleindex+">";
                    }
                    node = node + "</h"+titleindex+">";
                    retour.append(node);
                }
        }
        }
        retour.append("</html>");
        return retour.toString();
    }

    private String nodeTosring(DocumentationNode doc){
        StringBuilder node= new StringBuilder(doc.getText());

        while(!doc.getArgs().isEmpty()){
            if(doc.getArgs().get(0).length()>4&&doc.getArgs().get(0).substring(0,5).equals("color")){
                String colorvalue=doc.getArgs().get(0).substring(6);
                node = new StringBuilder("<FONT COLOR=" + colorvalue + ">"+ node + "</FONT>");
                doc.remove(doc.getArgs().get(0));
            }
            else {
                String firstarg=doc.getArgs().get(0);
                switch (firstarg) {
                    case ("img"):
                        node = new StringBuilder("<img src=" + node + "/>");
                        doc.remove("img");
                        break;
                    default:
                        node=new StringBuilder("<"+this.corresbalise.get(doc.getArgs().get(0))+">"+node+"</"+this.corresbalise.get(doc.getArgs().get(0))+">");
                        doc.remove(doc.getArgs().get(0));
                }
            }
        }
        return node.toString();
    }

}
