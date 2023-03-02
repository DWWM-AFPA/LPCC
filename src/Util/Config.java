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
        setCurrentConfig(this);
        this.setName(name);
        try {
            loadConfig(this);
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
        if (currentConfig==null)
            return getConfigSingleton("DefaultConfig");
        return currentConfig;
    }

    public static void setCurrentConfig(Config currentConfig) {
        Config.currentConfig = currentConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static Config loadConfig(Config fileConfig) throws  IOException {
    //    Config currentConfig = Config.getConfigSingleton(fileConfig.getName());
        String contenu = null;
        try {
            File configFile = new File(LPCFile.getConfigDirectory().getPath()+"\\"+fileConfig.getName()+".cfg");
        contenu = LPCFile.read(configFile);}
        catch (FileNotFoundException fe) {
            System.err.println("problem config");
            fe.printStackTrace();

        }
        catch (FileException ignored) {}
        Scanner scan = new Scanner(contenu);
        int line =0;
        boolean LPCTag =false;
        while (scan.hasNext()) {
                String s = scan.nextLine().trim();
                line++;
                if (s.contains("LPC")&&!s.contains("//")) {
                    LPCTag = !LPCTag;
                   // String[] tagTypes = s.split(";");
                    //for (String stype:tagTypes)
                    //    System.out.print("types         : " +stype);
                }
                else if (LPCTag&&!s.contains("//")) {
                    String[] tags = s.split(";");
                   // for (String st:tags){
                        fileConfig.getStyleTagList().add(tags[0].trim());
                        fileConfig.getHTMLBindings().put(tags[0].trim(),tags[1].trim());
                        fileConfig.getLatexBindings().put(tags[0].trim(),tags[2].trim());
                    //TODO arrayList compiler à transferer dans la config
                    //}
                }
                else {
                     switch(line){
                         //Current Configuration else if higher
                         case 2:
                             if(fileConfig.getName().equals("DefaultConfig")&&!s.equals("DefaultConfig")) {
                                 System.out.println("defaut chargement config :"+ s);
                                 fileConfig=null;
                                 return loadConfig(new Config(s));
                             }
                             break;
                             //language
                         case 5:
                            fileConfig.setLanguage(s);
                             break;
                            //inputDir
                         case 8:
                             if (!s.equals(""))
                                 fileConfig.setInputFolder(new File(s));
                             else
                                 fileConfig.setInputFolder(new File(LPCFile.documentsPath+"\\LPCC\\Input"));
                             break;
                            //main Input File name
                         case 11:
                             if (!s.equals(""))
                                fileConfig.setMainInputFileName(s);
                             else
                                fileConfig.setMainInputFileName("main");


                             //output Dir
                         case 14:
                             if (!s.equals(""))
                                 fileConfig.setOutputFolder(new File(s));
                             else
                                fileConfig.setOutputFolder(new File(LPCFile.documentsPath+"\\LPCC\\Output"));
                             break;
                             //tags delimiters
                         case 17:
                             fileConfig.setTagsDelimiter(s.split(";"));
                             break;
                             //user and dev tags
                         case 20:
                             String[] tags = s.split(";");
                             fileConfig.setUserTag(tags[0]);
                             fileConfig.setDevTag(tags[1]);
                             break;
                             //tag closer +
                         case 23:
                            fileConfig.setTagCloser(s.split(";"));
                            break;
                            //tag closer +
                         case 26:
                            fileConfig.setEscapingString(s);
                            break;
                     }

                }
        }
        return fileConfig;
        }



    public void createConfig() throws IOException {
        LPCFile.create(LPCFile.getConfigDirectory(),"DefaultConfig","cfg","");
    }
    public static void createEmptyConfig() throws IOException {
        LPCFile.create(LPCFile.getConfigDirectory(),"DefaultConfig","cfg",
               "//Current Configuration\n" +
                       "DefaultConfig\n" +
                       "\n" +
                       "//language\n" +
                       ".java\n" +
                       "\n" +
                       "//input directory (if null must be in my Documents)\n" +
                       "\n" +
                       "\n" +
                       "//main input file name\n" +
                       "\n" +
                       "\n" +
                       "//output directory (if null must be in my Documents)\n" +
                       "\n" +
                       "\n" +
                       "//Tags Delimiter\n" +
                       "<  ; >\n" +
                       "\n" +
                       "//user and developer documentation tags \n" +
                       "user ; dev\n" +
                       "\n" +
                       "//LPC Tag closer + LPC Tag position (can be start:\"/it\" or end:\"it/\")\n" +
                       "/ ; end\n" +
                       "\n" +
                       "//LPC character escaping\n" +
                       "//\n" +
                       "\n" +
                       "//Tags must be in the end of the file\n" +
                       "LPC    ;           HTML                      ;      LATEX\n" +
                       "title1 ;            h1                       ;      \\title{ }\n" +
                       "title2 ;            h2                       ;      \\chapter{chapter}\n" +
                       "title3 ;            h3                       ;      \\section{section}\n" +
                       "title4 ;            h4                       ;      \\subsection{subsection}\n" +
                       "title5 ;            h5                       ;      \\subsubsection{subsubsection}\n" +
                       "title6 ;            h6                       ;      \\paragraph{paragraph}\n" +
                       "//title 7 ;       notInHTML                  ;      \\subparagraph{subparagraph}\n" +
                       "it     ;            i                        ;      \\emph{accident}\n" +
                       "//or \\textit for italic\n" +
                       "bd     ;            b                        ;      \\textbf{greatest} \n" +
                       "ul     ;            u                        ;      \\underline{science} \n" +
                       "color  ;            #                        ;      \\color{blue}\n" +
                       "image  ;        <img src=\"dinosaur.jpg\">     ;      \\includegraphics{universe}"
        );
    }

    public void UpdateConfig() throws IOException {
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
        HashMap<String, String> latexMap=this.getHTMLBindings();

        for (String style :this.getStyleTagList())
            config.append(style)
                    .append(';')
                    .append(htmlMap.get(style))
                    .append(';')
                    .append(latexMap.get(style))
                    .append(System.lineSeparator());

        LPCFile.create(LPCFile.getConfigDirectory(),this.getName(),"cfg",config.toString());
    }


}
