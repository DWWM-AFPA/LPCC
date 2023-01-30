package Export;

import Export.Visitor;

import java.util.Hashtable;

public class CodeNode extends Node {
    protected Hashtable<Integer,String> appelCode;
    protected boolean start;
    protected String name;

    protected String extension;

    //getters


    public String getExtension() {
        return extension;
    }

    public Hashtable<Integer, String> getAppelCode() {
        return appelCode;
    }

    public boolean isStart() {
        return start;
    }

    public String getName(){return name;}

    //setters


    public void setAppelCode(Hashtable<Integer, String> appelCode) {
        this.appelCode = appelCode;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    //builders

    public CodeNode(){
        this.setText("");
        this.setStart(true);
        this.setAppelCode(new Hashtable<>());
        this.setExtension(".java");
    }

    public CodeNode(String text,Hashtable<Integer,String> appelCode,String name,String extension){
        this.setText(text);
        this.setAppelCode(appelCode);
        this.setStart(true);
        this.setName(name);
        this.setExtension(extension);
    }

    public CodeNode(String text,Hashtable<Integer,String> appelCode,boolean start,String name,String extension){
        this.setText(text);
        this.setStart(start);
        this.setAppelCode(appelCode);
        this.setName(name);
        this.setExtension(extension);
    }

    //methodes

    public CodeNode add(String c,Integer pos){
        this.appelCode.put(pos,c);
        return this;
    }
}
