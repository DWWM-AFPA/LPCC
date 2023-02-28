package Export;

import Util.Config;
import Util.LPCFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HTMLExportVisitor implements Visitor {
    public String export(ArrayList<Node> nodes) {
        StringBuilder retour = new StringBuilder();
        for (Node node : nodes) {
            //  System.out.println(node.getClass());
            if (node instanceof DocumentationNode) {
                String nodeName = node.accept(this);
                //    System.out.println(nodeName );
            } else if (node instanceof CodeNode) {
                String nodeName = node.accept(this);
                //System.out.println(nodeName );
            }
        }
        return retour.toString();
    }

    private StringBuilder makeHTMLTag(StringBuilder stringBuilder, String stringToAppend) {
        return stringBuilder.append('<').append(stringToAppend).append('>');
    }

    private StringBuilder makeHTMLTag(StringBuilder stringBuilder, char charToAppend) {
        return stringBuilder.append('<').append(charToAppend).append('>');
    }

    @Override
    public String visitDocumentation(DocumentationNode documentationNode) {
        HashMap<String, String> styleMap = Config.getCurrentConfig().getHTMLBindings();


        if (documentationNode.getName().equals("dev")) {

            StringBuilder dev = new StringBuilder();
            ArrayList<Node> nodes = documentationNode.getNodeContained();
            for (Node node : nodes) {
                if (node instanceof DocumentationNode) {
                    ArrayList<String> style = ((DocumentationNode) node).getStyle();
                    //looking for the title
                    String title = null;
                    if (style != null) {
                        for (String styles : style) {
                            if (styles.contains("title")) {
                                title = styleMap.get(styles);
                                makeHTMLTag(dev, title);
                                style.remove(styles);
                                break;
                            }
                        }
                        //if no titles, defines <p>
                        title = title == null ? "p" : title;

                        //opens all the style tags
                        for (String styles : style)
                            makeHTMLTag(dev, styles);

                        dev.append(node.getText());

                        //closes all the tag in the reverse order
                        for (int i = style.size() - 1; i >= 0; i--)
                            makeHTMLTag(dev, '/' + style.get(i));

                        //closes title or standard <p>
                        makeHTMLTag(dev, '/' + title);
                        title = null;
                    } else {
                        makeHTMLTag(dev, 'p');
                        dev.append(node.getText());
                        makeHTMLTag(dev, "/p");
                    }
                }
                if (node instanceof CodeNode) {
                    makeHTMLTag(dev, "p");
                    dev.append(node.getName());
                    makeHTMLTag(dev, "/p");
                    //TODO comment afficher le code en HTML ? Juste comme ça ?
                }
            }
            try {
                File devFile = LPCFile.create(LPCFile.getOutputDirectory(), "Developer Documentation", "html", dev.toString());
            } catch (IOException io) {
                System.exit(-1);
            }
            dev=null;
        } else if (documentationNode.getName().equals("user")) {
            StringBuilder user = new StringBuilder();
            for (Node node : documentationNode.getNodeContained()) {
                makeHTMLTag(user,'<');
                user.append(node.getText());
                makeHTMLTag(user,"/>");
            }

            try {
                File userFile = LPCFile.create(LPCFile.getOutputDirectory(), "User Documentation", "html", user.toString());
            } catch (IOException io) {
                System.exit(-1);
            }
            user=null;
        }
        return null;
    }

    @Override
    public String visitCode(CodeNode codeNode) {


        StringBuilder code = new StringBuilder();
        String codeName = codeNode.getName();
        if (codeNode)

            getContainedCode(code,node);


            try {
                File codeFile = LPCFile.create(LPCFile.getOutputDirectory(), codeName, "html", code.toString());
            } catch (IOException io) {
                System.exit(-1);
            }
        return null;
    }
    protected String getContainedCode(StringBuilder stringBuilder,Node node){
        ArrayList<Node> nodes = node.getNodeContained();
        for (Node node : nodes) {
        if (node.getName()==null&&node.getNodeContained()==null)) {
            makeHTMLTag(stringBuilder, "p");
            stringBuilder.append(node.getText());
            makeHTMLTag(stringBuilder, "p");
        }
        return null;
    }

}

