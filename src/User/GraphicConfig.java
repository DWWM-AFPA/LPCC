package User;

import Util.Config;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicConfig extends JFrame{
    private JLabel configName;
    private JTextField nameTextField;
    private JTable table1;
    private JTable table2;

    public GraphicConfig(Config config) {

        super("Configuration window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.configName = new JLabel("Configuration Name");
        this.nameTextField = new JTextField(config.getName());





        String[] columns = new String[] {"language","input Directory","main Input File name","output directory","left tags delimiter","right tags delimiter","user tag","dev tag","lpc tag closer","lpc tag position","escaping string"};
        String[][] data = new String[][] {
                {config.getLanguage(),
                config.getInputFolder().getPath(),
                config.getMainInputFileName(),
                config.getOutputFolder().getPath(),
                config.getTagsDelimiter()[0],
                config.getTagsDelimiter()[1],
                config.getUserTag(),
                config.getDevTag(),
                config.getTagCloser()[0],
                config.getTagCloser()[1],
                config.getEscapingString()}
        };


      table1=new JTable(data,columns);
      table1.setSize(1500,50);
      table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

      ArrayList<String > tagList = config.getStyleTagList();

      String[] columnsTblExport = new String[] {"LPC","HTML","LATEX"};
      String[][] tags = new String[tagList.size()][3];
        for (int i = 0; i < tagList.size(); i++) {
            tags[i][0]=tagList.get(i);
            tags[i][1]=config.getHTMLBindings().get(tagList.get(i));
            tags[i][2]=config.getHTMLBindings().get(tagList.get(i));
        }


        table2=new JTable(tags,columnsTblExport);
        table2.setSize(1500,50);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JPanel p = new JPanel();
        p.add(this.configName);
        p.add(this.nameTextField);

        getContentPane().setLayout(new BorderLayout());

        JScrollPane tbl = new JScrollPane(this.table1);
        table1.setSize(500,20);
        tbl.setSize(500,20);

        JScrollPane tbl2 = new JScrollPane(this.table2);
        tbl.createHorizontalScrollBar();
        p.setVisible(true);
        getContentPane().add(p,BorderLayout.NORTH);
        getContentPane().add(tbl,BorderLayout.CENTER);
        getContentPane().add(tbl2,BorderLayout.SOUTH);


        pack();
        setSize(800,700);
        setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
