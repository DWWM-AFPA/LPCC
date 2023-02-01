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
    Compilator c1=new Compilator("  $code$ ceci est du code $shape$ qui appelle un autre code /$code$$shape$ litteral prog /$shape$ ");
    Code test=new Code();
    @Test
    void compile() throws Exception {
        DocumentationNode doc= (DocumentationNode) c.compile().get(0);
        DocumentationNode doc0=(DocumentationNode) c0.compile().get(1);
        DocumentationNode doc1=(DocumentationNode) c0.compile().get(3);
        CodeNode doc2=(CodeNode) c1.compile().get(0);
        CodeNode doc3=(CodeNode) c1.compile().get(1);
        ArrayList<CodeNode> args=new ArrayList<>(List.of(doc2,doc3));
        assertEquals(ref.getArgs().get(0),doc.getArgs().get(0));
        assertEquals(ref.getText(),c.compile().get(0).getText());
        assertEquals(refit.getText(),c0.compile().get(0).getText());
        assertEquals(refbdit.getText(),c0.compile().get(1).getText());
        assertEquals(refbdit.getArgs(),doc0.getArgs());
        assertEquals(" ceci est du code ",c0.compile().get(2).getText());
        assertEquals(" ",doc1.getText());
        assertEquals(" ceci est du code  qui appelle un autre code ",c1.compile().get(0).getText());
        assertEquals(" ceci est du code  litteral prog  qui appelle un autre code ",test.extractcode(doc2,args));
    }
}