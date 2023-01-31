package Export;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class File {
    protected String path;
    protected String extension;
    protected String name;
    protected String fullPath;
    protected boolean hasBeenRead;
    private static final ArrayList<String> fileList = new ArrayList<>();

    public static final String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
    public static final String documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();

    public File(String name,String extension) {
        //path = mes documents
        this.setPath(documentsPath+"\\LPCC\\");
        java.io.File file= new java.io.File(desktopPath+"\\Projet\\LPCC");
        if (file.mkdir())
            System.out.println("Dossier créé dans : "+ file);
        else if (                !file.isDirectory()     &&                (JOptionPane.showConfirmDialog(null,"Voulez vous créer l'arborescence dossier "+file,"Création dossiers",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION))
            file.mkdirs();

        this.setName(name);
        this.setExtension(extension);
        this.setFullPath(this.getPath()+this.getName()+this.getExtension());
        this.setHasBeenRead(false);
    }
    public File(String path,String name,String extension) {
        this.setPath(path);
        this.setName(name);
        this.setExtension(extension);
        this.setFullPath(this.getPath()+this.getName()+this.getExtension());
        this.setHasBeenRead(false);
    }
    public File(String path,String name,String extension,boolean hasBeenRead) {
        this.setPath(path);
        this.setName(name);
        this.setExtension(extension);
        this.setFullPath(this.getPath()+this.getName()+this.getExtension());
        this.setHasBeenRead(hasBeenRead);
        if(!hasBeenRead)
            fileList.remove(this.getFullPath());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        extension = extension.charAt(0) == '.' ? extension: "."+extension;
        this.extension = extension;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public String read() throws IOException, FileException {
        if (!fileList.contains(this.getFullPath())) {
            fileList.add(this.getFullPath());
            FileReader file= new FileReader(this.getFullPath());
            int i;
            StringBuilder retour = new StringBuilder();
            while((i= file.read()) != -1)
                retour.append((char)i);
            return retour.toString();}
        else {
            throw new FileException().alreadyRead(this);
            //System.err.println("Le fichier "+this.getPath()+" semble avoir déjà été parcouru");

        }
    }

    public File create(String content) throws IOException {
        //FileOutputStream outFile = new FileOutputStream(this.getPath() + extension);
        FileOutputStream outFile = new FileOutputStream(this.getFullPath());
      //  System.out.println(this.getFullPath());

        outFile.write(content.getBytes());
        outFile.close();
        return this;
    }
}
