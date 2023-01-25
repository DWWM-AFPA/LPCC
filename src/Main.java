import Export.*;

public class Main {
    public static void main(String[] args) {
       // System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String dir = System.getProperty("user.dir");
        File f = new File(dir+"\\testFile.lpc");
        try{
           // System.out.print(f.read());
            new File("C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC"+"\\testOut").create("azerty","html");
        } catch (Exception e){System.err.println(f.getPath());}
    }
}