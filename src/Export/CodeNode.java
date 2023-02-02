package Export;

import Export.Visitor;

import java.util.HashMap;
import java.util.Hashtable;

public class CodeNode extends Node {
    protected HashMap<String,CodeNode> codeTable = new HashMap<>();

    protected boolean start;
    protected String name;

    //getters


    public HashMap<String, CodeNode> getCodeTable() {
        return codeTable;
    }

    public boolean isStart() {
        return start;
    }

    public String getName(){return name;}

    //setters


    public void setCodeTable(HashMap<String, CodeNode> codeTable) {
        this.codeTable = codeTable;
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

    public CodeNode(String content,String name){
        this.setName(name);
        this.setText(content);
    }
    public CodeNode(String content, String name, HashMap codeTable){
        this.setName(name);
        this.setText(content);
        this.setCodeTable(codeTable);

    }


}
