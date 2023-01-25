package Export;

import Export.Visitor;

import java.util.Hashtable;

public class CodeNode extends Node {
    Hashtable<Integer,CodeNode> appelCode;
    boolean start;

    //getters


    public Hashtable<Integer, CodeNode> getAppelCode() {
        return appelCode;
    }

    public boolean isStart() {
        return start;
    }

    //setters


    public void setAppelCode(Hashtable<Integer, CodeNode> appelCode) {
        this.appelCode = appelCode;
    }

    public void setStart(boolean start) {
        this.start = start;
    }


    //builders

    public CodeNode(){
        this.setText("");
        this.setStart(false);
        this.setAppelCode(new Hashtable<>());
    }

    public CodeNode(String text,Hashtable<Integer,CodeNode> appelCode){
        this.setText(text);
        this.setAppelCode(appelCode);
        this.setStart(false);
    }

    public CodeNode(String text,Hashtable<Integer,CodeNode> appelCode,boolean start){
        this.setText(text);
        this.setStart(start);
        this.setAppelCode(appelCode);
    }

    //methodes

    public CodeNode add(CodeNode c,Integer pos){
        this.appelCode.put(pos,c);
        return this;
    }
}
