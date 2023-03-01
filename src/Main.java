import Export.*;
import Export.Compiler;
import Util.*;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, FileException {
/*        File file = new File("input","LPCC");

        Compilator compTest = new Compilator(file.read());
        compTest.compile();
       // Node.getAllNodes();*/
    //    Config matt= new Config("matt");
   //     Config.createEmptyConfig();
        JFileChooser choose = new JFileChooser(LPCFile.getConfigDirectory());
        choose.showDialog(null,"select");
        Config.loadConfig(choose.getSelectedFile());
        Config.updateConfig(choose.getSelectedFile());

                //,new File("matt","config"));
     //   matt.createConfig();
      /* matt.loadConfig();*/
     /*   new Compilator(
                "<dev> ceci est du code : " +
                            "<codeTest> code code code <codeTest/>"+
                        "<dev/>"+
                "<code> azerty <test> zob <code/>" +
                        "<user> <bd> a <it>  He <bd/><it/> t <user/> " +
                "<user> test <bd> azerty<bd/> <user/>").compile();
*//*        new Compilator(
                          "<code> azerty " +
                                  "<test> " +
                                  "<code/>" ).compile();*//*
*//*        new Compilator(
                        "<user> test <bd>   azerty    <bd/> <user/>").compile();*//*

*/
        /*HashMap<String, Node> nodes = Node.getNodeRegistry();
        Set<String> keys =  nodes.keySet();
        for (String s : keys){
            Node nd =  nodes.get(s);
            System.err.println(nd);
            System.out.printf("Name : %s Text : %s Style :%s%s", nd.getName(),nd.getText(),""*//**//*nd.getStyle()*//**//*,System.lineSeparator());
        }/**/

        new Compiler("<dev> <title1><it>documentation  titre 1 <it/><title1/> text dev <code> c'est du code <codeTest> un autre bout de code <codeAlone> apres l'autre code <codeTest/> encore dans code <code/> fin dans dev <dev/><codeAlone>le code de codeAlone<codeAlone/>"+"<user > documentation Utilisateur<user/>").compile();

       // new Compilator("<user> documentation dev <code> c'est du code <codeTest> un autre bout de code <codeAlone> apres l'autre code <codeTest/> encore dans code <code/> fin dans dev <user/><codeAlone>le code de codeAlone<codeAlone/>").compile();

    HTMLExportVisitor exportVisitor = new HTMLExportVisitor();
        System.out.println(exportVisitor.export(Node.getNodeList()));
 /*       String ln=System.lineSeparator();
        System.out.println(ln+"Nombre de Nodes compris : "+Node.getNodeList().size());

        for (Node node : Node.getNodeList()) {
            System.out.printf("%s  %S Nombre de Nodes contenus : %s%s",ln, node.getName(), node.getNodeContained().size()*//*nd.getStyle()*//*, ln);
            for (Node node1:node.getNodeContained()){
                if (node1 instanceof DocumentationNode) {
                    DocumentationNode doc=(DocumentationNode)node1;
                    System.out.printf("      Name : %s Text : %s Style : %s %s", doc.getName(), doc.getText(), doc.getStyle(),ln);
                }
                if (node1 instanceof CodeNode) {
                    CodeNode code = (CodeNode)node1;
                    System.out.printf("      Name : %s Code : %s %s", code.getName(), code.getText(),ln);
                }

            }
        }*/

     //   Config.setMainInputFileName("tester.LPC");
      //  Graphic.draw();

    }
}