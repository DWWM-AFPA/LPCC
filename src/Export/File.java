package Export;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class File {
    protected String path;
    protected String name;
    protected boolean hasBeenRead;
    private static ArrayList<String> fileList;

    public File(String path) {
        this.setPath(path);
        this.setHasBeenRead(false);
    }
    public File(String path, boolean hasBeenRead) {
        this.setPath(path);
        this.setHasBeenRead(hasBeenRead);
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

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public String read() throws IOException {
        if (!fileList.contains(this.path)) {
            fileList.add(path);
            FileReader file= new FileReader(this.getPath());
            int i;
            StringBuilder retour = new StringBuilder();
            while((i= file.read()) != -1)
                retour.append((char)i);
            return retour.toString();}
        else {
            System.err.println("Le fichier "+this.getPath()+"semble avoir déjà été parcouru");
            return null;
        }
    }

    public File create(String content, String extension) throws IOException {
        extension = extension.charAt(1) == '.' ? extension: "."+extension;
        FileOutputStream outFile = new FileOutputStream(this.getPath() + extension);
        outFile.write(content.getBytes());
        outFile.close();
        return this;
    }
}
