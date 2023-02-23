package User;

import Util.Config;
import Util.LPCFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;

public class UpdatePanel extends JPanel {

    private Config config;

    private Graphic fenetre;

    private Hashtable<String,String> latextags;

    private Hashtable<String,String> htmltags;

    private String newdirectorypath;

    private JButton changresultdirectorypath;

    private JButton addtag;

    private JButton validconfig;

    private JButton defintag;

    private JLabel infos;

    private JTextField lpctag;

    private JTextField htmltag;

    private JTextField latextag;

    private JTextField nomconfig;

    private JTextField actualpath;

    private JTextField newpath;

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

    private Hashtable<Integer,Integer> filepos;

    public UpdatePanel(Graphic fenetre){
        //on initialise
        this.fenetre=fenetre;
        latextags=new Hashtable<>();
        htmltags=new Hashtable<>();

        //on cree les elements graphiques

        actualpath=new JTextField(config.getDestinationfolder());
        actualpath.setVisible(true);
        actualpath.setEditable(false);
        actualpath.setRequestFocusEnabled(false);

        changresultdirectorypath=new JButton("Change");
        changresultdirectorypath.setVisible(true);
        changresultdirectorypath.addActionListener(new ActionListener() {
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
                newpath.setText(f.getPath());
                newpath.revalidate();
                newpath.repaint();
            }
        });

        newpath=new JTextField(10);
        newpath.setEditable(false);
        newpath.setRequestFocusEnabled(false);
        newpath.setVisible(true);

        validconfig=new JButton("Valider");



    }





    //getters et setters


    public void setConfig(String config) {
        this.config = new Config(config);
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

    public String getNewdirectorypath() {
        return newdirectorypath;
    }

    public void setNewdirectorypath(String newdirectorypath) {
        this.newdirectorypath = newdirectorypath;
    }

    public JButton getChangresultdirectorypath() {
        return changresultdirectorypath;
    }

    public void setChangresultdirectorypath(JButton changresultdirectorypath) {
        this.changresultdirectorypath = changresultdirectorypath;
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

    public JButton getDefintag() {
        return defintag;
    }

    public void setDefintag(JButton defintag) {
        this.defintag = defintag;
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

    public void setLatextag(JTextField latextag) {
        this.latextag = latextag;
    }

    public JTextField getNomconfig() {
        return nomconfig;
    }

    public void setNomconfig(JTextField nomconfig) {
        this.nomconfig = nomconfig;
    }

    public JTextField getActualpath() {
        return actualpath;
    }

    public void setActualpath(JTextField actualpath) {
        this.actualpath = actualpath;
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



    public void setName(JPanel name) {
        this.name = name;
    }

    public JPanel getTag() {
        return tag;
    }

    public void setTag(JPanel tag) {
        this.tag = tag;
    }

    public JButton getSaveconfig() {
        return saveconfig;
    }

    public void setSaveconfig(JButton saveconfig) {
        this.saveconfig = saveconfig;
    }

    public String getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(String entrypoint) {
        this.entrypoint = entrypoint;
    }

    public JLabel getMain() {
        return main;
    }

    public void setMain(JLabel main) {
        this.main = main;
    }

    public JLabel getNom() {
        return nom;
    }

    public void setNom(JLabel nom) {
        this.nom = nom;
    }

    public JButton getAnnuler() {
        return annuler;
    }

    public void setAnnuler(JButton annuler) {
        this.annuler = annuler;
    }

    public JButton getAnnuler1() {
        return annuler1;
    }

    public void setAnnuler1(JButton annuler1) {
        this.annuler1 = annuler1;
    }
}
