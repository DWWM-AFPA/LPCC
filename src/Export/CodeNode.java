package Export;

import java.util.HashMap;

public class CodeNode extends Node {
    protected HashMap<String,int[]> codeLocation = new HashMap<>();

    protected boolean start;
    protected String name;

    //getters



    public HashMap<String,int[]> getCodeLocation() {
        return codeLocation;
    }

    public boolean isStart() {
        return start;
    }

    public String getName(){return name;}

    //setters



    public void setCodeLocation(HashMap<String, int[]> codeLocation) {
        this.codeLocation = codeLocation;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setName(String name) {
        this.name = name;
    }


    //builders

    public CodeNode(String name){
        this.setName(name);
        this.setText("");
    }

    public CodeNode(String name, String content){
        this.setName(name);
        this.setText(content);
    }
    public CodeNode(String name, String codeTableName, int pos1, int pos2){
        this.setName(name);
        this.setText("");
        this.addCodeLocation(codeTableName,pos1,pos2);
    }

    protected void addCodeLocation(String name,int pos1,int pos2){
        int[] tbl = {pos1,pos2};
        codeLocation.put(name,tbl);
    }


}
