package Export;

import net.sourceforge.plantuml.SourceStringReader;

import javax.xml.transform.Source;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class DocumentationNode extends Node{
    protected boolean dev;
    protected ArrayList<String> args;

    //getters

    public ArrayList<String> getArgs() {
        return args;
    }

    public boolean isDev() {
        return dev;
    }


    //setters


    public void setDev(boolean dev) {
        this.dev = dev;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    //builders

    public DocumentationNode(){
        this.setDev(true);
        this.setArgs(new ArrayList<>());
        this.setText("");
    }

    public DocumentationNode(boolean dev,String text,ArrayList<String> args){
        this.setText(text);
        this.setArgs(args);
        this.setDev(dev);
    }

    //methodes

    public DocumentationNode add(String arg){
        this.args.add(arg);
        return this;
    }

    public DocumentationNode remove(String arg){
        this.args.remove(arg);
        return this;
    }

    //methodes

    public BufferedImage readdiagramm(){
        String source=this.text;
        OutputStream out;
        try {
            out=new FileOutputStream("diag.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        SourceStringReader reader=new SourceStringReader(source);
        BufferedImage im;
        try {
            reader.outputImage(out);
            im=new
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
