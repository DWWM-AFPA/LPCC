package Util;

import Export.FileException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;

public abstract class LPCFile extends File {
    protected String path;
    protected String extension;
    protected String name;
    protected String fullPath;
    protected boolean hasBeenRead;
    protected String content;
    private static final ArrayList<File> alreadyReadFile=new ArrayList<>();
    private static final File ConfigDirectory = setConfigDirectory();

    private static final ArrayList<File> inputFileList = new ArrayList<>();
    public static final String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
    public static final String documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();

    public LPCFile(String pathname) {
        super(pathname);
    }


    public static File getConfigDirectory() {
        return ConfigDirectory;
    }

    private static File setConfigDirectory() {
        //mes documents
        String LPCCfolder = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\LPCC\\Config";
        File retour=new File(LPCCfolder);
        if (retour.mkdirs())
            JOptionPane.showMessageDialog(null,"Nouveau dossier configuration créé :"+
                    System.lineSeparator()+
                    LPCCfolder);
        return retour;
    }
    public static ArrayList<File> getAlreadyReadFile(){
        return alreadyReadFile;
    }


    /** create a File from a Class File parent Directory,extension without "." and automatically in LowerCase  */
    public static File create(File parent, String name, String extension, String content) throws IOException {
        File file = new File(parent,name+"."+extension.toLowerCase());
        file.createNewFile();
        FileOutputStream outFile = new FileOutputStream(file);
        outFile.write(content.getBytes());
        outFile.close();
        return file;
    }

    /** read a File and return the String the first time, and throws an exception if it is already read */
    //si le user ouvre le dir et qu'il contient
    public static String read(File file) throws IOException, FileException {
        //evite de lire 2x le même File
        if (!alreadyReadFile.contains(file)) {
            alreadyReadFile.add(file);
            FileReader fileReader= new FileReader(file);
            int i;
            StringBuilder retour = new StringBuilder();
            while((i= fileReader.read()) != -1)
                retour.append((char)i);
            return retour.toString();
        }
        else {
            System.err.println("Le fichier "+file.getPath()+" semble avoir déjà été parcouru");
            throw new FileException();
        }
    }

    public static File getMainFile() throws FileException, IOException {
        File retour = null;
        File inputDirectory = Config.getCurrentConfig().getInputFolder();
        File[] fileList = inputDirectory.listFiles();
        //TODO améliorer ça quand on choisi un File avec le Chooser
        if (fileList==null)
            JOptionPane.showMessageDialog(null,"Attention, le chemin de la configuration actuelle " +
                    "n'est pas valide,\n veuillez selectionner le fichier à compiler");
        else
            for (File s : fileList)
            {
                if (s.getName().equals(Config.getCurrentConfig().getMainInputFileName()))
                    retour = s;
            }
        if (retour == null) {
            JFileChooser choose = new JFileChooser(inputDirectory);
            choose.showOpenDialog(null);
            retour = choose.getSelectedFile();
            Config.getCurrentConfig().setInputFolder(retour);
        }
        return retour;
    }


}
