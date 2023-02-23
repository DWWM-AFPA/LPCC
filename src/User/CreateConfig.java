package User;

import Util.LPCFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class CreateConfig extends JPanel {

    private Graphic fenetre;

    private String configname;

    private Hashtable<String,String> latextags;

    private Hashtable<String,String> htmltags;

    private String resultdirectorypath;

    private JButton defineresultdirectorypath;

    private JButton addtag;

    private JButton validconfig;

    private JButton defintag;

    private JLabel infos;

    private JTextField lpctag;

    private JTextField htmltag;

    private JTextField latextag;

    private JTextField nomconfig;

    private JTextField mainname;

    private JLabel lpc;

    private JLabel html;

    private JLabel latex;

    private JPanel name;

    private JPanel tag;

    private JButton saveconfig;

    private String entrypoint;

    private JLabel main;

    private JLabel nom;

    private JButton annuler;

    private JButton annuler1;

    //builders

    public CreateConfig(Graphic fenetre){
        this.fenetre=fenetre;
        //on definit les élements du Jpanel d'ajout d'un tag
        lpctag=new JTextField(10);
        lpctag.setVisible(true);

        this.lpc=new JLabel("Entrer un tag LPC");
        lpc.setVisible(true);

        htmltag=new JTextField(10);
        htmltag.setVisible(true);

        this.html=new JLabel("Entrer un tag HTML");
        html.setVisible(true);

        latextag=new JTextField(10);
        latextag.setVisible(true);

        this.latex=new JLabel("Entrer un tag latex");
        latex.setVisible(true);



        //on definit les élements

        nom=new JLabel("Nom config");
        nom.setVisible(true);

        mainname=new JTextField(10);
        mainname.setVisible(true);

        name=new JPanel();


        tag=new JPanel();


        nomconfig=new JTextField(10);
        nomconfig.setVisible(true);

        main=new JLabel("Main name");
        main.setVisible(true);

        defineresultdirectorypath=new JButton("Choose result location");
        defineresultdirectorypath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choose=new JFileChooser();
                int buttonResult;
                File f=null;
                choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                buttonResult = choose.showOpenDialog(null);
                if(buttonResult == JFileChooser.APPROVE_OPTION)
                    f = choose.getSelectedFile();
                System.out.println("path=" +f.getPath());
                resultdirectorypath=f.getPath();
            }
        });
        defineresultdirectorypath.setVisible(true);

        validconfig=new JButton("Valider");
        validconfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File check=new File(LPCFile.getConfigDirectory().getPath()+nomconfig.getText()+".config");
                if(nomconfig.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Il faut définir un nom !","Erreur",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(check.exists()) {
                    JOptionPane.showMessageDialog(null,"Nom de config non disponible","Erreur",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!mainname.getText().isEmpty())
                    entrypoint=mainname.getText();
                configname=nomconfig.getText();
                tag.setVisible(true);
                name.setVisible(false);
                fenetre.getFrame().pack();
            }
        });
        validconfig.setVisible(true);

        defintag=new JButton("Add Tag");
        defintag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!lpctag.getText().isEmpty()&&!htmltag.getText().isEmpty()&&!latextag.getText().isEmpty()){
                    if(!latextags.containsKey(lpctag.getText())) {
                        latextags.put(lpctag.getText(), latextag.getText());
                        htmltags.put(lpctag.getText(), htmltag.getText());
                        lpctag.setText("");
                        htmltag.setText("");
                        latextag.setText("");
                        repaint();
                        revalidate();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Les trois champs doivent être remplit","Erreur",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        defintag.setVisible(true);

        saveconfig=new JButton("Sauvegarder");
        saveconfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuffer content=new StringBuffer();
                if(entrypoint!=null){
                    content.append(entrypoint).append(".lpc");
                }
                else {
                    content.append("main.lpc");
                }
                content.append('\n');
                if(resultdirectorypath!=null){
                    content.append(resultdirectorypath);
                }
                else {
                    content.append(LPCFile.desktopPath);
                }
                content.append('\n');
                for (String s :latextags.keySet()) {
                    content.append(s).append(";").append(latextags.get(s)).append(";").append(htmltags.get(s));
                    content.append('\n');
                }
                int last = content.lastIndexOf("\n");
                if (last >= 0)
                    content.delete(last, content.length());
                try {
                    LPCFile.create(LPCFile.getConfigDirectory(),configname,"config",content.toString());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                fenetre.getPanelcompile().setVisible(true);
                fenetre.getCreate().setVisible(false);
                reset();
                fenetre.getFrame().pack();
                fenetre.getFrame().revalidate();
                fenetre.getConfigsel().readconfignames();
                fenetre.getFrame().repaint();
            }
        });

        annuler=new JButton("Annuler");
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        annuler.setVisible(true);

        annuler1=new JButton("Annuler");
        annuler1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        annuler1.setVisible(true);

        //on remplit


        //on place les elements
        this.setLayout(new FlowLayout());
        this.add(name);
        this.add(tag);
        name.add(nom);
        name.add(nomconfig);
        name.add(defineresultdirectorypath);
        name.add(main);
        name.add(mainname);
        name.add(validconfig);
        name.add(annuler);
        tag.add(saveconfig);
        tag.add(lpc);
        tag.add(lpctag);
        tag.add(latex);
        tag.add(latextag);
        tag.add(html);
        tag.add(htmltag);
        tag.add(defintag);
        tag.add(annuler1);

        this.reset();


        this.fenetre.getFrame().pack();
        this.fenetre.getFrame().repaint();
        this.fenetre.getFrame().revalidate();
    }






    //getters et setters


    public Graphic getFenetre() {
        return fenetre;
    }

    public void setFenetre(Graphic fenetre) {
        this.fenetre = fenetre;
    }

    public String getConfigname() {
        return configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname;
    }

    public Hashtable<String, String> getLatextags() {
        return latextags;
    }

    public void setLatextags(Hashtable<String, String> latextags) {
        this.latextags = latextags;
    }

    public Hashtable<String, String> getHtmltags() {
        return htmltags;
    }

    public void setHtmltags(Hashtable<String, String> htmltags) {
        this.htmltags = htmltags;
    }

    public String getResultdirectorypath() {
        return resultdirectorypath;
    }

    public void setResultdirectorypath(String resultdirectorypath) {
        this.resultdirectorypath = resultdirectorypath;
    }

    public JButton getAddtag() {
        return addtag;
    }

    public void setAddtag(JButton addtag) {
        this.addtag = addtag;
    }

    public JButton getValidconfig() {
        return validconfig;
    }

    public void setValidconfig(JButton validconfig) {
        this.validconfig = validconfig;
    }

    public JLabel getInfos() {
        return infos;
    }

    public void setInfos(JLabel infos) {
        this.infos = infos;
    }

    public JTextField getLpctag() {
        return lpctag;
    }

    public void setLpctag(JTextField lpctag) {
        this.lpctag = lpctag;
    }

    public JTextField getHtmltag() {
        return htmltag;
    }

    public void setHtmltag(JTextField htmltag) {
        this.htmltag = htmltag;
    }

    public JTextField getLatextag() {
        return latextag;
    }

    public void setLatextag(JTextField latexdag) {
        this.latextag = latexdag;
    }

    public JTextField getNomconfig() {
        return nomconfig;
    }

    public void setNomconfig(JTextField nomconfig) {
        this.nomconfig = nomconfig;
    }

    public JLabel getLpc() {
        return lpc;
    }

    public void setLpc(JLabel lpc) {
        this.lpc = lpc;
    }

    public JLabel getHtml() {
        return html;
    }

    public void setHtml(JLabel html) {
        this.html = html;
    }

    public JLabel getLatex() {
        return latex;
    }

    public void setLatex(JLabel latex) {
        this.latex = latex;
    }

    private void reset(){
        this.configname=null;
        this.resultdirectorypath=null;
        this.entrypoint=null;
        latextags=new Hashtable<>();
        htmltags=new Hashtable<>();
        File in=new File(LPCFile.getConfigDirectory().getPath()+"\\"+"default"+".config");
        Scanner sc ;
        try {
            sc = new Scanner(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;
        String [] parts;
        int nbline=0;
        while (sc.hasNextLine()){
            line = sc.nextLine();
            if(nbline>1) {
                parts = line.split(";");
                latextags.put(parts[0],parts[1]);
                htmltags.put(parts[0],parts[2]);
            }
            nbline++;
        }
        this.setVisible(false);
        fenetre.getPanelcompile().setVisible(true);
        name.setVisible(true);
        tag.setVisible(false);
        fenetre.getFrame().pack();
    }
}
