package Export;

import java.util.ArrayList;
import java.util.Hashtable;

public class Compilator {
    String openTag;
    String closeTag;

    //getters


    public String getOpenTag() {
        return openTag;
    }

    public String getCloseTag() {
        return closeTag;
    }


    //setters


    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }

    public void setOpenTag(String openTag) {
        this.openTag = openTag;
    }

    //builders

    public Compilator(){
        this.setOpenTag(new String());
        this.setCloseTag(new String());
    }


    public Compilator(String openTag,String closeTag){
        this.setCloseTag(closeTag);
        this.setOpenTag(openTag);
    }

    //méthodes

    public ArrayList<Node> compile(File file){
        return null;
    }

    private Object expr(){
        return null;
    }

    private Object exprend(){
        return null;
    }

    private Object mainTag(){
     return null;
    }

    private Object text(){
        return null;
    }

    private Object tag(){
        return null;
    }

    private Object tagEnd(){
        return null;
    }

    private Object word(){
        return null;
    }


}
