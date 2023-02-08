package Export;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LateXDocTest {

    DocumentationNode bold=new DocumentationNode(false,"texte en bold",new ArrayList<String>(List.of("bd")));
    DocumentationNode it=new DocumentationNode(false,"texte en italique",new ArrayList<>(List.of("it")));

    DocumentationNode title=new DocumentationNode(false,"titre",new ArrayList<>(List.of("title1")));

    DocumentationNode bdit=new DocumentationNode(false,"texte en italique et bold",new ArrayList<>(List.of("it","bd")));
    LateXDoc l;


    {
        try {
            l = new LateXDoc();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void user() {
        l.user(new ArrayList<>(List.of(it,bold)));
        l.user(new ArrayList<>(List.of(bdit,title)));
    }

    @Test
    void dev() {
    }

    @Test
    void visit() {
    }
}