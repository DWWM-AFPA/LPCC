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
        File outputFolder =Config.getCurrentConfig().getOutputFolder();

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
                    makeHTMLTag(dev, 'p');
                    makeHTMLTag(dev, 'i');
                    dev.append(node.getName());
                    makeHTMLTag(dev, "/i");
                    makeHTMLTag(dev, "/p");
                    //TODO comment afficher le code en HTML ? Juste comme ça ?
                }
            }

            try {
                File devFile = LPCFile.create(outputFolder, "Developer Documentation", "html", dev.toString());
            } catch (IOException io) {
                System.exit(-1);
            }
            dev = null;
        } else if (documentationNode.getName().equals("user")) {
            StringBuilder user = new StringBuilder();
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
                                makeHTMLTag(user, title);
                                style.remove(styles);
                                break;
                            }
                        }
                        //if no titles, defines <p>
                        title = title == null ? "p" : title;

                        //opens all the style tags
                        for (String styles : style)
                            makeHTMLTag(user, styles);

                        user.append(node.getText());

                        //closes all the tag in the reverse order
                        for (int i = style.size() - 1; i >= 0; i--)
                            makeHTMLTag(user, '/' + style.get(i));

                        //closes title or standard <p>
                        makeHTMLTag(user, '/' + title);
                        title = null;
                    } else {
                        makeHTMLTag(user, 'p');
                        user.append(node.getText());
                        makeHTMLTag(user, "/p");
                    }
                }
            }

            try {
                File userFile = LPCFile.create(outputFolder, "User Documentation", "html", user.toString());
            } catch (IOException io) {
                System.exit(-1);
            }
            user = null;
        }
        return null;
    }

    @Override
    public String visitCode(CodeNode codeNode) {

        StringBuilder code = new StringBuilder();
        String codeName = codeNode.getName();
        ArrayList<Node> nodes = codeNode.getNodeContained();
        for (Node node : nodes) {
            if (node.getName()==null) {
                makeHTMLTag(code, 'p');
                code.append(node.getText());
                makeHTMLTag(code, "/p");
            }
            else if (node.getNodeContained()!=null)
                getContainedCode(node,code);
        }


        try {
            File codeFile = LPCFile.create(Config.getCurrentConfig().getOutputFolder(), codeName, "html", code.toString());
        } catch (IOException io) {
            System.exit(-1);
        }
        return null;
    }

    protected void getContainedCode(Node parentNode,StringBuilder sb) {
        for (Node node : parentNode.getNodeContained()) {
            if (node.getName()==null) {
                makeHTMLTag(sb, 'p');
                sb.append(node.getText());
                makeHTMLTag(sb, "/p");
            } else if (node.getNodeContained()!=null) {
                getContainedCode(node,sb);
            }


        }
    }
}

