package User;

import Util.Config;

import javax.swing.*;
import java.awt.*;

public class GraphicConfig {
    private JPanel configPanel;
    private JLabel configName;
    private JTextField nameTextField;
    private JTable table1;

    public GraphicConfig(Config config) {

        // GraphicConfig graphicConfig =new GraphicConfig(Config.getCurrentConfig());
        JFrame frame = new   JFrame("Configuration window");
        this.configPanel = new JPanel();
        this.configName = new JLabel("Configuration Name");
        this.nameTextField = new JTextField(2);

        this.configPanel.add(this.configName);
        this.configPanel.add(this.nameTextField);

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

        frame.getContentPane().add(new JScrollPane(this.table1));
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
