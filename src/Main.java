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
        matt.loadConfig();*/try {
new Compilator("<user> a <it> He <it/> t <user/> ").compile();
        } catch (Exception e){
            e.printStackTrace();
        }
        HashMap<String, Node> nodes = Node.getNodeRegistry();
Set<String> keys =  nodes.keySet();
for (String s : keys){
    Node nd =  nodes.get(s);
    System.err.println(nd);
    System.out.printf("Name : %s Text : %s Style :%s%s", nd.getName(),nd.getText(),""/*nd.getStyle()*/,System.lineSeparator());
}

     //   Config.setMainInputFileName("tester.LPC");
    //    Graphic.draw();

    }
}