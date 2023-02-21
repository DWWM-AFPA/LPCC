package Export;


import java.io.File;

public class FileException extends Exception{
    public FileException alreadyRead(File file) {
        System.err.println("Le fichier " + file.getPath() + " semble avoir déjà été parcouru");
        return this;
    }
}
