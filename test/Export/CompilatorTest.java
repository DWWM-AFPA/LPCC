package Export;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompilatorTest {

    Compilator c=new Compilator("<it> texte /<it>");
    DocumentationNode ref=new DocumentationNode(false," texte ",new ArrayList<>(List.of("it")));
    @Test
    void compile() {
        DocumentationNode doc= (DocumentationNode) c.compile().get(0);
        assertEquals(ref.getArgs().get(0),doc.getArgs().get(0));
        assertEquals(ref.getText(),c.compile().get(0).getText());
    }
}