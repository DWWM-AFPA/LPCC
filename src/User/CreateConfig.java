package User;

import Util.LPCFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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

    private JLabel lpc;

    private JLabel html;

    private JLabel latex;


    //builders

    public CreateConfig(Graphic fenetre){
        this.fenetre=fenetre;
        this.configname=null;
        this.resultdirectorypath=null;
        //on definit les élements du Jpanel d'ajout d'un tag
        lpctag=new JTextField(10);
        lpctag.setVisible(false);

        this.lpc=new JLabel("Entrer un tag LPC");
        lpc.setVisible(false);

        htmltag=new JTextField(10);
        htmltag.setVisible(false);

        this.html=new JLabel("Entrer un tag HTML");
        html.setVisible(false);

        latextag=new JTextField(10);
        latextag.setVisible(false);

        this.latex=new JLabel("Entrer un tag latex");
        latex.setVisible(false);



        //on definit les élements

        nomconfig=new JTextField(10);
        nomconfig.setVisible(true);

        defineresultdirectorypath=new JButton("Choose result location");
        defineresultdirectorypath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choose=new JFileChooser();
                choose.showOpenDialog(null);
                choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File f=null;
                while (f==null) {
                    f = choose.getSelectedFile();
                }
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
                    JOptionPane message=new JOptionPane("Il faut définir un nom !");
                    return;
                }
                if(check.exists()) {
                    JOptionPane message = new JOptionPane("Nom de config non disponible");
                    return;
                }
                configname=nomconfig.getText();
                defintag.setVisible(true);
                latextag.setVisible(true);
                htmltag.setVisible(true);
                lpctag.setVisible(true);
                latex.setVisible(true);
                lpc.setVisible(true);
                html.setVisible(true);
                nomconfig.setVisible(false);
                validconfig.setVisible(false);
                defineresultdirectorypath.setVisible(false);
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
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Les trois champs doivent être remplit","Erreur",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        defintag.setVisible(false);

        //on remplit
        latextags=new Hashtable<>();
        htmltags=new Hashtable<>();
        File in=new File(LPCFile.getConfigDirectory().getPath()+"\\"+"default"+".config");
        Scanner sc = null;
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

        //on place les elements
        this.setLayout(new FlowLayout());
        this.add(nomconfig);
        this.add(defineresultdirectorypath);
        this.add(validconfig);
        this.add(lpc);
        this.add(lpctag);
        this.add(latex);
        this.add(latextag);
        this.add(html);
        this.add(htmltag);
        this.add(defintag);
        this.setVisible(false);
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
}
