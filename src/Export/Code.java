package Export;

import java.io.IOException;
import java.util.*;

public class Code implements Visitor{
    @Override
    public void visit(ArrayList<Node> compilation) {
        ArrayList<CodeNode> ref=new ArrayList<>();
        for (Node n: compilation) {
            if(n instanceof CodeNode) {
                ref.add((CodeNode) n);
                if (((CodeNode) n).getAppelCode() != null) {
                    for (Node m : compilation) {
                        if (m instanceof CodeNode && ((CodeNode) n).getAppelCode().contains(((CodeNode) m).getName())) {
                            ((CodeNode) m).setStart(false);
                        }
                    }
                }
            }
        }
        for (Node n : compilation) {
            if (n instanceof CodeNode && ((CodeNode) n).isStart()) {
                try {
                    LPCFile.create(null,((CodeNode) n).getName(),".java",extractcode((CodeNode) n,ref));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }




        public String extractcode(CodeNode code,ArrayList<CodeNode> refs){
        String retour = "";
        ArrayList<String> remplacement=new ArrayList<>();
        ArrayList<Integer> keys= new ArrayList<>();
        Hashtable<Integer,CodeNode> appelcode=new Hashtable<>();
        if(code.getAppelCode()!=null) {
            for (Integer i : code.getAppelCode().keySet()) {
                keys.add(i);
                for (CodeNode c : refs) {
                    if (Objects.equals(code.getAppelCode().get(i), c.getName())) {
                        appelcode.put(i,c);
                    }
                }
            }
            if(appelcode.isEmpty()){
                throw new IllegalArgumentException("Ce code n'est pas compilable");
            }
        }
        ArrayList<String> coddeslice=new ArrayList<>();
        int inf=0;
        int sup=0;
        if(keys.isEmpty()) {
            return code.getText();
        }
        sup =  keys.get(keys.size()-1);
        for (int i=0;i<keys.size();i++) {
            remplacement.add(extractcode(appelcode.get(keys.get(i)),refs));
            if(i==0) {
                coddeslice.add(code.getText().substring(0,sup));
            }
            else {
                inf = keys.get(keys.size()-1-i);
                coddeslice.add(code.getText().substring(inf,sup));
            }
            inf = keys.get(keys.size()-1-i);
            if(i+1<keys.size()){
                sup=keys.get(keys.size()-i-2);
            }
            else {
                coddeslice.add(code.getText().substring(inf));
            }
        }
        for (int i = 0; i < remplacement.size(); i++) {
            if(i+1<remplacement.size()) {
                retour = retour + coddeslice.get(i) + remplacement.get(remplacement.size()-1-i);
            }
            else {
                retour=retour+coddeslice.get(i)+remplacement.get(remplacement.size()-1-i)+coddeslice.get(i+1);
            }
        }
        return retour;
    }
}
