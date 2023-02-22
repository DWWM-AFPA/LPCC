package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Config {
    protected String configname;
    protected String mainInputFileName;

    protected String sourcefolder;

    protected String destinationfolder;

    protected Hashtable<String,String> htmlTag;

    protected Hashtable<String,String> latexTag;


    public Config(){
        configname="default";
        this.readConfig();

    }

    public Config(String name) {
       configname = name;
       this.readConfig();
    }

    //getters et setters
    public String getName() {
        return configname;
    }

    public void setName(String name) {
        this.configname = name;
    }

    public String getConfigname() {
        return configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname;
    }

    public String getDestinationfolder() {
        return destinationfolder;
    }

    public void setDestinationfolder(String destinationfolder) {
        this.destinationfolder = destinationfolder;
    }

    public Hashtable<String, String> getHtmlTag() {
        return htmlTag;
    }

    public void setHtmlTag(Hashtable<String, String> htmlTag) {
        this.htmlTag = htmlTag;
    }

    public Hashtable<String, String> getLatexTag() {
        return latexTag;
    }

    public void setLatexTag(Hashtable<String, String> latexTag) {
        this.latexTag = latexTag;
    }

    public void setMainInputFileName(String mainInputFileName) {
        this.mainInputFileName = mainInputFileName;
    }

    public  String getMainInputFileName() {
        return mainInputFileName;
    }

    /*    public void loadConfig() throws FileException, IOException {
            Scanner scan = new Scanner(this.LPCFile.read());

            while (scan.hasNext()) {
                String key = scan.next();
                String value = scan.next();
                System.out.println("Key : "+key+" Value : "+value);
            }

        }*/
    public static void createConfig(String name,String contenu) throws IOException {
        LPCFile.create(new File("C:\\Users\\CDA-03\\Desktop\\LPCCConfig\\"),name,"config",contenu);
    }


    public void readConfig(){
        File in=new File(LPCFile.getConfigDirectory().getPath()+"\\"+this.configname+".config");
        System.out.println("config directory"+LPCFile.getConfigDirectory().getPath());
        String [] parts;
        Scanner sc = null;
        try {
            sc = new Scanner(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;
        int nbline=0;
        Hashtable<String,String> corresLatex = new Hashtable<>();
        Hashtable<String,String> corresHTML= new Hashtable<>();
        while (sc.hasNextLine()){
            if(nbline==0)
                mainInputFileName=sc.nextLine();
            if(nbline==1)
                destinationfolder=sc.nextLine();
            if(nbline>1) {
                line = sc.nextLine();
                parts = line.split(";");
                corresLatex.put(parts[0], parts[1]);
                corresHTML.put(parts[0],parts[2]);
            }
            nbline++;
        }
        latexTag=corresLatex;
        htmlTag=corresHTML;
    }
}
