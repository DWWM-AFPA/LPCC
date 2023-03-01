package Export;

import Util.LPCFile;
import net.sourceforge.plantuml.SourceStringReader;

import javax.imageio.ImageIO;
import javax.xml.transform.Source;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class DocumentationNode extends Node{
    protected boolean dev;
    protected ArrayList<String> args;

    protected boolean diagram;

    //getters


    public boolean isDiagram() {
        return diagram;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public boolean isDev() {
        return dev;
    }


    //setters


    public void setDiagram(boolean diagram) {
        this.diagram = diagram;
    }

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

    public File readdiagramm(String diagramname){
        String source=this.text;
        OutputStream out;
        try {
            out=new FileOutputStream(LPCFile.getOutputDirectory()+"\\"+diagramname+".png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        SourceStringReader reader=new SourceStringReader(source);
        try {
            reader.outputImage(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(LPCFile.getOutputDirectory()+"\\"+diagramname+".png");
    }




}
