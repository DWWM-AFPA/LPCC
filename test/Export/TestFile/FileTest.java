package Export.TestFile;

import Export.File;
import Export.FileException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    @Test
    void testConstructor(){
        String path = File.desktopPath+"\\Projet\\LPCC"+"\\";
        String name = "testCreate";
        String extension ="html";
        String content = "testCreate";
        assertEquals("C:\\Users\\CDA-11\\Documents\\LPCC\\testCreate.html",new File(name,extension).getFullPath());
        assertEquals("C:\\Users\\CDA-11\\Documents\\LPCC\\test.html",new File("test","html").getFullPath());
        assertEquals("C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC\\testCreate.html",new File(path,name,extension).getFullPath());
        assertEquals("C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC\\testCreate.html",new File(path,name,extension,false).getFullPath());
        assertEquals("C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC\\testCreate.html",new File(path,name,extension,true).getFullPath());

    }

    @Test
    void testCreate() throws Exception {
        String path = File.desktopPath+"\\Projet\\LPCC"+"\\";
        String name = "testCreate";
        String extension ="html";
        String content = "testCreate";



        assertEquals(new File(name,extension).create(content).read(),content);
        assertEquals(new File(path,name,extension).create(content).read(),content);
        assertEquals(new File(path,name,extension,false).create(content).read(),content);
        assertThrowsExactly(Export.FileException.class, new File(path,name,extension,true).create(content)::read,"Le fichier testCreate semble avoir déjà été parcouru");
        assertThrows(Export.FileException.class, () -> {new File("C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC"+"\\","testCreate","html").read();});
        assertThrows(FileNotFoundException.class, () -> {new File("C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC"+"\\","testCreateazerty","html").read();});

    }
    @Test
    void testRead() throws Exception {
        String path = File.desktopPath+"\\LPCC"+"\\";
        String name = "testCreated";
        String extension ="html";
        File test=new File(path,name,extension);
        String content = "testCreate";
        // assertEquals(test.read(),content);
      //    assertThrowsExactly(Export.FileException.class, test::read,"Le fichier "+test.getFullPath()+" semble avoir déjà été parcouru");
        assertThrowsExactly(FileNotFoundException.class, test::read);
            }
}