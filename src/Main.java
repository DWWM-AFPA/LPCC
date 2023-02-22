import Export.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, FileException {
/*        File file = new File("input","LPCC");

        Compilator compTest = new Compilator(file.read());
        compTest.compile();
       // Node.getAllNodes();
        Config matt= new Config("matt",new File("matt","config"));
     //   matt.createConfig();
        matt.loadConfig();*/
        new Compilator(
                "<dev> ceci est du code : " +
                            "<codeTest> code code code <codeTest/>"+
                        "<dev/>"+
                "<code> azerty <test> <code/>" +"<user> a <it> He <it/> t <user/> " +
                "<user> test <bd> azerty<bd/> <user/>").compile();

/*        HashMap<String, Node> nodes = Node.getNodeRegistry();
        Set<String> keys =  nodes.keySet();
        for (String s : keys){
            Node nd =  nodes.get(s);
            System.err.println(nd);
            System.out.printf("Name : %s Text : %s Style :%s%s", nd.getName(),nd.getText(),""*//*nd.getStyle()*//*,System.lineSeparator());
        }*/
        String ln=System.lineSeparator();
        System.out.println(ln+"Nombre de Nodes compris : "+Node.getNodeList().size());
        for (Node node : Node.getNodeList()) {
            System.out.printf("%sMain Node : \n  nom : %s Nombre de Nodes contenus : %s%s",ln, node.getName(), node.getNodeContained().size()/*nd.getStyle()*/, ln);
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
        }

     //   Config.setMainInputFileName("tester.LPC");
    //    Graphic.draw();

    }
}