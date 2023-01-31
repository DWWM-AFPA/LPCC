package Export;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompilatorTest {

    Compilator c=new Compilator("<it> texte </it>");
    DocumentationNode ref=new DocumentationNode(false," texte ",new ArrayList<>(List.of("it")));
    DocumentationNode refit=new DocumentationNode(false," texte it ",new ArrayList<>(List.of("it")));
    DocumentationNode refbdit=new DocumentationNode(false," texte it et bold ",new ArrayList<>(List.of("it","bd")));
    Compilator c0=new Compilator("<it> texte it <bd> texte it et bold $code$ ceci est du code /$code$ </it></bd>");
    @Test
    void compile() throws Exception {
        DocumentationNode doc= (DocumentationNode) c.compile().get(0);
        DocumentationNode doc0=(DocumentationNode) c0.compile().get(1);
        DocumentationNode doc1=(DocumentationNode) c0.compile().get(3);
        assertEquals(ref.getArgs().get(0),doc.getArgs().get(0));
        assertEquals(ref.getText(),c.compile().get(0).getText());
        assertEquals(refit.getText(),c0.compile().get(0).getText());
        assertEquals(refbdit.getText(),c0.compile().get(1).getText());
        assertEquals(refbdit.getArgs(),doc0.getArgs());
        assertEquals(" ",doc1.getText());
    }
}