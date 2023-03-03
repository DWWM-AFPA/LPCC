package Util;

import Export.FileException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    protected static Config currentConfig;
    protected static HashMap<String,Config> configList = new HashMap<>();
    protected File configFile;
    protected String name;
    protected String language;
    protected File inputFolder;
    protected String mainInputFileName;
    protected File outputFolder;
    protected String[] tagsDelimiter;
    protected String userTag;
    protected String devTag;
    protected String[] tagCloser;
    protected String escapingString;
    protected HashMap<String,String> HTMLBindings = new HashMap<>();
    protected HashMap<String,String> LatexBindings = new HashMap<>();
    protected ArrayList<String> styleTagList = new ArrayList<>();

/*    private Config() {
        setCurrentConfig(this);
        this.setName("default");
        try{
            loadConfig(this);
        } catch (IOException io){
            System.err.println("config file problem");
            System.exit(-1);
        }
    }*/
    private Config(String name) {
        this.setName(name);
        setCurrentConfig(this);
        setConfigFile(new File(LPCFile.getConfigDirectory(),name+".cfg"));
        try {
            this.loadConfig();
        } catch (IOException io) {
            io.printStackTrace();
            System.err.println("config file problem");
            System.exit(-1);
        }
    }

    public static Config getConfigSingleton(String name){
        if (!configList.containsKey(name)) {
            Config config = new Config(name);
            configList.put(name, config);
            return config;
        }

        return configList.get(name);
    }

    public static Config getCurrentConfig() {
     //   if (currentConfig==null)
      //      return getConfigSingleton("DefaultConfig");
        return currentConfig;
    }

    public static void setCurrentConfig(Config currentConfig) {
        Config.currentConfig = currentConfig;
    }
    public static void setCurrentConfig(String currentConfigName) {
        Config.currentConfig = getConfigSingleton(currentConfigName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (configList.containsKey(this.getName())) {
            configList.remove(this.getName());
            configList.put(name,this);
        }

        this.name = name;
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
   //     System.out.println(this);
        this.configFile = configFile;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public File getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(File inputFolder) {
        if (inputFolder.mkdirs())
            JOptionPane.showMessageDialog(null,"Le chemin "+ inputFolder+" a été créé.");
        this.inputFolder = inputFolder;
    }

    public String getMainInputFileName() {
        return mainInputFileName;
    }

    public void setMainInputFileName(String mainInputFileName) {
        this.mainInputFileName = mainInputFileName;
    }

    public File getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(File outputFolder) {
        if (outputFolder.mkdirs())
            JOptionPane.showMessageDialog(null,"Le chemin "+ outputFolder+" a été créé.");

        this.outputFolder = outputFolder;
    }

    public String[] getTagsDelimiter() {
        return tagsDelimiter;
    }

    public void setTagsDelimiter(String[] tagsDelimiter) {
        this.tagsDelimiter = tagsDelimiter;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public String getDevTag() {
        return devTag;
    }

    public void setDevTag(String devTag) {
        this.devTag = devTag;
    }

    public String[] getTagCloser() {
        return tagCloser;
    }

    public void setTagCloser(String[] tagCloser) {
        this.tagCloser = tagCloser;
    }

    public String getEscapingString() {
        return escapingString;
    }

    public void setEscapingString(String escapingString) {
        this.escapingString = escapingString;
    }

    public HashMap<String, String> getHTMLBindings() {
        return HTMLBindings;
    }

    public void setHTMLBindings(HashMap<String, String> HTMLBindings) {
        this.HTMLBindings = HTMLBindings;
    }

    public HashMap<String, String> getLatexBindings() {
        return LatexBindings;
    }

    public void setLatexBindings(HashMap<String, String> latexBindings) {
        LatexBindings = latexBindings;
    }

    public ArrayList<String> getStyleTagList() {
        return styleTagList;
    }

    public void setStyleTagList(ArrayList<String> styleTagList) {
        this.styleTagList = styleTagList;
    }

    public Config loadConfig() throws  IOException {
    //    Config currentConfig = Config.getConfigSingleton(fileConfig.getName());
        String contenu = null;
        try {
            LPCFile.getAlreadyReadFile().remove(this.getConfigFile());
            contenu = LPCFile.read(this.getConfigFile());}
        catch (FileNotFoundException fe) {
            if (!this.getConfigFile().isFile())
            contenu = Config.getEmptyConfig();
            else
            {
                System.err.println("Loading config problem");
                fe.printStackTrace();
                System.exit(-1);
            }
        }
        catch (FileException fe) {
            System.err.println("Loading 2 times Config file problem ");
        }


        Scanner scan = new Scanner(contenu);
        int line =0;
        boolean LPCTag =false;
        while (scan.hasNext()) {
                String s = scan.nextLine().trim();
                line++;
                if (s.contains("LPC")&&!s.contains("//")&&!s.contains("\\")) {
                    LPCTag = !LPCTag;
                   // String[] tagTypes = s.split(";");
                    //for (String stype:tagTypes)
                    //    System.out.print("types         : " +stype);
                }
                else if (LPCTag&&!s.contains("//")) {
                    String[] tags = s.split(";");
                   // for (String st:tags){
                        this.getStyleTagList().add(tags[0].trim());
                        this.getHTMLBindings().put(tags[0].trim(),tags[1].trim());
                        this.getLatexBindings().put(tags[0].trim(),tags[2].trim());
                    //TODO arrayList compiler à transferer dans la this
                    //}
                }
                else {
                     switch(line){
                         //Current Configuration else if higher
                         case 2:
                             if(this.getName().equals("DefaultConfig")&&!s.equals("DefaultConfig")) {
                                 System.out.println("defaut chargement config :"+ s);
                                 //TODO attention au passage none static
                                 return new Config(s);
                                 /*this=null;
                                 return loadConfig(new Config(s));*/
                             }
                             break;
                             //language
                         case 5:
                            this.setLanguage(s);
                             break;
                            //inputDir
                         case 8:
                             if (!s.equals(""))
                                 this.setInputFolder(new File(s));
                             else
                                 this.setInputFolder(new File(LPCFile.documentsPath+"\\LPCC\\Input"));
                             break;
                            //main Input File name
                         case 11:
                             if (!s.equals(""))
                                this.setMainInputFileName(s);
                             else
                                this.setMainInputFileName("main");


                             //output Dir
                         case 14:
                             if (!s.equals(""))
                                 this.setOutputFolder(new File(s));
                             else
                                this.setOutputFolder(new File(LPCFile.documentsPath+"\\LPCC\\Output"));
                             break;
                             //tags delimiters
                         case 17:
                             this.setTagsDelimiter(s.split(";"));
                             break;
                             //user and dev tags
                         case 20:
                             String[] tags = s.split(";");
                             this.setUserTag(tags[0]);
                             this.setDevTag(tags[1]);
                             break;
                             //tag closer +
                         case 23:
                            this.setTagCloser(s.split(";"));
                            break;
                            //tag closer +
                         case 26:
                            this.setEscapingString(s);
                            break;
                     }

                }
        }
        return this;
        }

    public void updateConfig() throws IOException {
        this.configFile.delete();
        //new File("").delete();
        StringBuilder config = new StringBuilder();
        String ln = System.lineSeparator();
        config.append("//Current Configuration").append(ln)
            .append(Config.getCurrentConfig().getName())
            .append(ln).append(ln)
            .append("//language").append(ln)
            .append(this.getLanguage())
            .append(ln).append(ln)
            .append("//input directory (if null must be in my Documents)").append(ln)
            .append(this.getInputFolder().getPath())
            .append(ln).append(ln)
            .append("//main input file name").append(ln)
            .append(this.getMainInputFileName())
            .append(ln).append(ln)
            .append("//output directory (if null must be in my Documents)").append(ln)
            .append(this.getOutputFolder())
            .append(ln).append(ln)
            .append("//Tags Delimiter").append(ln)
            .append(this.getTagsDelimiter()[0]).append(';').append(this.getTagsDelimiter()[1])
            .append(ln).append(ln)
            .append("//user and developer documentation tags ").append(ln)
            .append(this.getUserTag()).append(';').append(this.getDevTag())
            .append(ln).append(ln)
            .append("//LPC Tag closer + LPC Tag position (can be start:\"/it\" or end:\"it/\")" ).append(ln)
            .append(this.getTagCloser()[0]).append(';').append(this.getTagCloser()[1])
            .append(ln).append(ln)
            .append("//LPC character escaping" ).append(ln)
            .append(this.getEscapingString())
            .append(ln).append(ln)
            .append("//Tags must be in the end of the file").append(ln);

        HashMap<String, String> htmlMap=this.getHTMLBindings();
        HashMap<String, String> latexMap=this.getLatexBindings();

        for (String style :this.getStyleTagList())
            config.append(style)
                    .append(';')
                    .append(htmlMap.get(style))
                    .append(';')
                    .append(latexMap.get(style))
                    .append(System.lineSeparator());

        LPCFile.create(LPCFile.getConfigDirectory(),this.getName(),"cfg",config.toString());
    }
    public boolean delete(String name){
        configList.remove(name);
        Config.setCurrentConfig(getConfigSingleton("DefaultConfig"));
       // this.configFile.deleteOnExit();
        boolean delete = this.configFile.delete();

            System.err.println(delete);


        return delete;
    }

    public static String getEmptyConfig() {
        return
                """
                        //Current Configuration
                        DefaultConfig

                        //language
                        .java

                        //input directory (if null must be in my Documents)


                        //main input file name


                        //output directory (if null must be in my Documents)


                        //Tags Delimiter
                        <  ; >

                        //user and developer documentation tags\s
                        user ; dev

                        //LPC Tag closer + LPC Tag position (can be start:"/it" or end:"it/")
                        / ; end

                        //LPC character escaping
                        //

                        //Tags must be in the end of the file
                        LPC    ;           HTML                      ;      LATEX
                        title1 ;            h1                       ;      \\title{ }
                        title2 ;            h2                       ;      \\chapter{chapter}
                        title3 ;            h3                       ;      \\section{section}
                        title4 ;            h4                       ;      \\subsection{subsection}
                        title5 ;            h5                       ;      \\subsubsection{subsubsection}
                        title6 ;            h6                       ;      \\paragraph{paragraph}
                        //title 7 ;       notInHTML                  ;      \\subparagraph{subparagraph}
                        it     ;            i                        ;      \\emph{accident}
                        //or \\textit for italic
                        bd     ;            b                        ;      \\textbf{greatest}\s
                        ul     ;            u                        ;      \\underline{science}\s
                        color  ;            #                        ;      \\color{blue}
                        image  ;        <img src="dinosaur.jpg">     ;      \\includegraphics{universe}""";

    }

    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                '}';
    }

}
