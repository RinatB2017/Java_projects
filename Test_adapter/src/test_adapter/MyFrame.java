//--------------------------------------------------------------------------------
package test_adapter;
//--------------------------------------------------------------------------------

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
//--------------------------------------------------------------------------------
// http://stackoverflow.com/questions/17951886/cannot-add-checkbox-to-the-jlist
//--------------------------------------------------------------------------------

class CheckListItem {

    private String label;
    private boolean isSelected = false;

    public CheckListItem(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return label;
    }
}
//--------------------------------------------------------------------------------

class CheckListRenderer extends JCheckBox implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        setEnabled(list.isEnabled());
        setSelected(((CheckListItem) value).isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
    }
}
//--------------------------------------------------------------------------------

public class MyFrame {

    DefaultListModel model;
    int n = 0;

    class TestActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //model.addElement(new CheckListItem("xxx"+n));
            //n++;
            int cnt = 0;
            for (int i = 0; i < model.size(); i++) {
                if (((CheckListItem) model.getElementAt(i)).isSelected()) {
                    cnt++;
                }
            }
            System.out.println(cnt);
        }
    }

    public void test() {
        model = new DefaultListModel();

        for (n = 0; n < 5; n++) {
            //model.addElement(Integer.toString(n));
            model.addElement(new CheckListItem("xxx" + n));
        }

        JList list = new JList(model);
        list.setCellRenderer(new CheckListRenderer());
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                // Get index of item clicked
                int index = list.locationToIndex(event.getPoint());
                CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);
                // Toggle selected state
                item.setSelected(!item.isSelected());
                // Repaint cell
                list.repaint(list.getCellBounds(index, index));
            }
        });

        ActionListener actionListener = new TestActionListener();
        JButton btn = new JButton("test");
        btn.addActionListener(actionListener);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(list, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);

        JFrame frame = new JFrame("test");
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        //frame.pack();
        frame.setVisible(true);
    }
}
//--------------------------------------------------------------------------------
