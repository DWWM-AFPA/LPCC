package Export.TestFile;

import Export.File;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    @Test
    void testCreate() throws Exception {
        String path = "C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC"+"\\";
        String name = "testCreate";
        String extension ="html";
        String content = "testCreate";
        assertEquals(new File(path,name,extension).create(content).read(),content);

        assertThrows(FileNotFoundException.class, () -> {
            new File("C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC"+"\\","testCreated","html").read();});

    }
    @Test
    void testRead() throws Exception {
        String path = "C:\\Users\\CDA-11\\Desktop\\Projet\\LPCC"+"\\";
        String name = "testCreated";
        String extension ="html";
        File test=new File(path,name,extension);
        String content = "testCreate";
        // assertEquals(test.read(),content);

      //    assertThrowsExactly(Export.FileException.class, test::read,"Le fichier "+test.getFullPath()+" semble avoir déjà été parcouru");

        assertThrowsExactly(FileNotFoundException.class, test::read);
            }
}