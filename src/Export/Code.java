package Export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class Code implements Visitor{
    @Override
    public void visit(ArrayList<Node> compilation) {
        for (Node n : compilation) {
            if (n instanceof CodeNode && ((CodeNode) n).isStart()) {

            }
        }
    }


    private String extractcode(CodeNode code){
        String retour;
        LinkedHashMap<Integer,String> remplacement=new LinkedHashMap<>();
        Integer[] keys= (Integer[]) code.getAppelCode().entrySet().toArray();
        ArrayList<String> coddeslice=new ArrayList<>();
        int inf=0;
        int sup=keys[0];
        for (int i=0;i<keys.length;i++) {
            remplacement.put(keys[i], extractcode(code.getAppelCode().get(keys[i])));
            coddeslice.add(code.getText().substring(inf,sup));
            inf=keys[i];
            if(i+1<keys.length){
                sup=keys[i+1];
            }
            else {
                sup=code.getText().length();
            }
        }

    }
}
