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
            if (node instanceof DocumentationNode  && node.getName().equals("user")) {
                String nodeName = node.accept(this);
            }
        }
        return retour.toString();
    }
    public String exportDev(ArrayList<Node> nodes) {
        StringBuilder retour = new StringBuilder();
        for (Node node : nodes) {
            if( node.getName().equals("dev"))
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



/*    private StringBuilder makeLATEXTag(StringBuilder stringBuilder, char charToAppend) {
        return stringBuilder.append('<').append(charToAppend).append('>');
    }*/

    @Override
    public String visitDocumentation(DocumentationNode documentationNode) {
        HashMap<String,String> styleMap = Config.getCurrentConfig().getLatexBindings();
        File outputFolder =Config.getCurrentConfig().getOutputFolder();
        Boolean devContainsMainTitle = null;
        if (documentationNode.getName().equals("dev")) {
            StringBuilder dev = new StringBuilder("""
                    \\documentclass{article}
                    \\usepackage{graphicx} % Required for inserting images
                    
                    """);

            ArrayList<Node> nodes = documentationNode.getNodeContained();

            for (Node node : nodes) {
                if (node instanceof DocumentationNode) {
                    ArrayList<String> styles = ((DocumentationNode) node).getStyle();
                    //looking for the title
                    String title = null;
                    if (styles != null) {
                        int count =0;
                        if (styles.contains("title1")) {
                            int s = styles.indexOf("title1");
                            if (s!=0) {
                                styles.add(0,styles.remove(s));
                            }
                        }

                        for (String style : styles) {
                            if (style.contains("title")) {
                                if (devContainsMainTitle==null && style.equals("title1"))
                                    devContainsMainTitle=true;

                                else if (devContainsMainTitle==null) {
                                    dev.append("\n\\begin{document}\n");
                                    devContainsMainTitle = false;
                                }
                                title = styleMap.get(style);

                                dev.append(title).append('{');
                                count++;
                                styles.remove(style);
                            }
                            for (String otherStyle : styles) {
                                dev.append(styleMap.get(otherStyle)).append('{');
                                count++;
                            }
                        }
                        dev.append(node.getText());
                        dev.append("}".repeat(Math.max(0, count)));

                        if (devContainsMainTitle!=null&&devContainsMainTitle) {
                            dev.append("""
                                    \n
                                    \\begin{document}
                                    \\maketitle
                                    
                                    """);
                            devContainsMainTitle=false;
                        }

                    } else {
                        if (devContainsMainTitle==null) {
                            dev.append("\n\\begin{document}\n");
                            devContainsMainTitle = false;
                        }
                     //   makeLATEXTag(dev, 'p');
                        dev.append(node.getText());
                      //  makeLATEXTag(dev, "/p");
                    }
                }
                else if (node instanceof CodeNode) {
                    //TODO penser à mettre un boolean si on veut le code via le nom ou le contenu
                    dev.append("\n").append(node.getName()).append("\n");
              /*      makeLATEXTag(dev, 'p');
                    makeLATEXTag(dev, 'i');
                    dev.append(node.getName());
                    makeLATEXTag(dev, "/i");
                    makeLATEXTag(dev, "/p");*/

                }
            }
            dev.append("\n \\end{document}");
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
            StringBuilder user =new StringBuilder("""
                    \\documentclass{article}
                    \\usepackage{graphicx} % Required for inserting images
                    """);
            ArrayList<Node> nodes = documentationNode.getNodeContained();

            for (Node node : nodes) {
                if (node instanceof DocumentationNode) {
                    ArrayList<String> styles = ((DocumentationNode) node).getStyle();
                    //looking for the title
                    String title = null;
                    if (styles != null) {
                        int count = 0;
                        if (styles.contains("title1")) {
                            int s = styles.indexOf("title1");
                            if (s != 0) {
                                styles.add(0, styles.remove(s));
                            }
                        }

                        for (String style : styles) {
                            if (style.contains("title")) {
                                if (devContainsMainTitle == null && style.equals("title1"))
                                    devContainsMainTitle = true;

                                else if (devContainsMainTitle == null) {
                                    user.append("\n\\begin{document}\n");
                                    devContainsMainTitle = false;
                                }
                                title = styleMap.get(style);

                                user.append(title).append('{');
                                count++;
                                styles.remove(style);
                            }
                            for (String otherStyle : styles) {
                                user.append(styleMap.get(otherStyle)).append('{');
                                count++;
                            }
                        }
                        user.append(node.getText());
                        user.append("}".repeat(Math.max(0, count)));

                        if (devContainsMainTitle != null && devContainsMainTitle) {
                            user.append("""
                                    \\begin{document}
                                    \\maketitle
                                                                        
                                    """);
                            devContainsMainTitle = false;
                        }

                    }
                    else {
                        if (devContainsMainTitle == null) {
                            user.append("\n\\begin{document}\n");
                            devContainsMainTitle = false;
                        }
                        user.append(node.getText());
                    }
                } else System.err.println("devrait pas arriver");
            }

            user.append("\\end{document}");
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
               // makeLATEXTag(code, 'p');
            //    code.append(node.getText());
            //    makeLATEXTag(code, "/p");
            }
            else if (node.getNodeContained()!=null)
                getContainedCode(node,code);
        }


        try {
            File LATEXOutputDirectory = new File(Config.getCurrentConfig().getOutputFolder().getPath()+"\\Code");
            LATEXOutputDirectory.mkdirs();
            File codeFile = LPCFile.create(LATEXOutputDirectory, codeName, Config.getCurrentConfig().getLanguage(), code.toString()+"\\end{document}");
        } catch (IOException io) {
            System.err.println("impossible de créer le fichier code\n"+io);
            System.exit(-1);
        }
        return null;
    }

    protected void getContainedCode(Node parentNode,StringBuilder sb) {
        for (Node node : parentNode.getNodeContained()) {
            if (node.getName()==null) {
              //  makeLATEXTag(sb, 'p');
              //  sb.append(node.getText());
              //  makeLATEXTag(sb, "/p");
            } else if (node.getNodeContained()!=null) {
                getContainedCode(node,sb);
            }


        }
    }
}

