package Export;

import Util.Config;
import Util.LPCFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LATEXExportVisitor implements Visitor {
    public String exportUser(ArrayList<Node> nodes) {
        StringBuilder retour = new StringBuilder();
        for (Node node : nodes) {
            if (node instanceof DocumentationNode) {
                String nodeName = node.accept(this);
            }
        }
        return retour.toString();
    }
    public String exportDev(ArrayList<Node> nodes) {
        StringBuilder retour = new StringBuilder();
        for (Node node : nodes) {
            if (node instanceof DocumentationNode) {
                String nodeName = node.accept(this);
            } else if (node instanceof CodeNode) {
                String nodeName = node.accept(this);
            }
        }
        return retour.toString();
    }
    public String exportCode(ArrayList<Node> nodes) {
        StringBuilder retour = new StringBuilder();
        for (Node node : nodes) {
            if (node instanceof CodeNode) {
                String nodeName = node.accept(this);
            }
        }
        return retour.toString();
    }

    private StringBuilder makeLATEXTag(StringBuilder stringBuilder, String stringToAppend) {
        return stringBuilder.append('\\'); //.append(stringToAppend).append('>');
    }

    private StringBuilder makeLATEXTag(StringBuilder stringBuilder, char charToAppend) {
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
                                makeLATEXTag(dev, title);
                                style.remove(styles);
                                break;
                            }
                        }
                        //if no titles, defines <p>
                        title = title == null ? "p" : title;

                        //opens all the style tags
                        for (String styles : style)
                            makeLATEXTag(dev, styles);

                        dev.append(node.getText());

                        //closes all the tag in the reverse order
                        for (int i = style.size() - 1; i >= 0; i--)
                            makeLATEXTag(dev, '/' + style.get(i));

                        //closes title or standard <p>
                        makeLATEXTag(dev, '/' + title);
                        title = null;
                    } else {
                        makeLATEXTag(dev, 'p');
                        dev.append(node.getText());
                        makeLATEXTag(dev, "/p");
                    }
                }
                if (node instanceof CodeNode) {
                    makeLATEXTag(dev, 'p');
                    makeLATEXTag(dev, 'i');
                    dev.append(node.getName());
                    makeLATEXTag(dev, "/i");
                    makeLATEXTag(dev, "/p");
                    //TODO comment afficher le code en HTML ? Juste comme ça ?
                }
            }

            try {
                File LATEXOutputDirectory = new File(Config.getCurrentConfig().getOutputFolder().getPath()+"\\LATEX\\Dev");
                LATEXOutputDirectory.mkdirs();
                File devFile = LPCFile.create(LATEXOutputDirectory, "Developer Documentation", "tex", dev.toString());
            } catch (IOException io) {
                System.err.println("impossible de créer le fichier dev\n"+io);
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
                                makeLATEXTag(user, title);
                                style.remove(styles);
                                break;
                            }
                        }
                        //if no titles, defines <p>
                        title = title == null ? "p" : title;

                        //opens all the style tags
                        for (String styles : style)
                            makeLATEXTag(user, styles);

                        user.append(node.getText());

                        //closes all the tag in the reverse order
                        for (int i = style.size() - 1; i >= 0; i--)
                            makeLATEXTag(user, '/' + style.get(i));

                        //closes title or standard <p>
                        makeLATEXTag(user, '/' + title);
                        title = null;
                    } else {
                        makeLATEXTag(user, 'p');
                        user.append(node.getText());
                        makeLATEXTag(user, "/p");
                    }
                }
            }

            try {
                File LATEXOutputDirectory = new File(Config.getCurrentConfig().getOutputFolder().getPath()+"\\LATEX\\User");
                LATEXOutputDirectory.mkdirs();
                File userFile = LPCFile.create(LATEXOutputDirectory, "User Documentation", "tex", user.toString());
            } catch (IOException io) {
                System.err.println("impossible de créer le fichier user\n"+io);

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
                makeLATEXTag(code, 'p');
                code.append(node.getText());
                makeLATEXTag(code, "/p");
            }
            else if (node.getNodeContained()!=null)
                getContainedCode(node,code);
        }


        try {
            File LATEXOutputDirectory = new File(Config.getCurrentConfig().getOutputFolder().getPath()+"\\Code");
            LATEXOutputDirectory.mkdirs();
            File codeFile = LPCFile.create(LATEXOutputDirectory, codeName, Config.getCurrentConfig().getLanguage(), code.toString());
        } catch (IOException io) {
            System.err.println("impossible de créer le fichier code\n"+io);
            System.exit(-1);
        }
        return null;
    }

    protected void getContainedCode(Node parentNode,StringBuilder sb) {
        for (Node node : parentNode.getNodeContained()) {
            if (node.getName()==null) {
                makeLATEXTag(sb, 'p');
                sb.append(node.getText());
                makeLATEXTag(sb, "/p");
            } else if (node.getNodeContained()!=null) {
                getContainedCode(node,sb);
            }


        }
    }
}

