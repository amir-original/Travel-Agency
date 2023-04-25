package travelAgency.ui.component;

import com.toedter.calendar.JDateChooser;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import travelAgency.ui.App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UiComponents {

    public UiComponents() {
    }

    public JButton button(String label, int width, int height) {
        final JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    public JButton button(String label) {
        return new JButton(label);
    }

    public JLabel label(String text, Font font, int alignment) {
        final JLabel jLabel = label(text);
        jLabel.setFont(font);
        jLabel.setHorizontalAlignment(alignment);
        return jLabel;
    }

    public JLabel label(String text) {
        return new JLabel(text);
    }

    public <T> JComboBox<T> dropdown(T[] values,int width,int height){
        final JComboBox<T> comboBox = new JComboBox<>(values);
        comboBox.setPreferredSize(new Dimension(width,height));
        return comboBox;
    }

    public JDateChooser dateChooser(int width,int height){
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(width,height));
        return dateChooser;
    }

    public JSpinner inputNumber(int startWith,int min,int max,int width,int height){
        JSpinner inputNumber = new JSpinner(new SpinnerNumberModel(startWith, min, max, 1));
        inputNumber.setPreferredSize(new Dimension(width,height));
        return inputNumber;
    }

    public JLabel h1Label(String txt,int horizontalAlignment){
        JLabel header = new JLabel(txt, horizontalAlignment);
        header.setFont(new Font("Arial",Font.BOLD,35));
        return header;
    }

    public JTextArea disableTextArea(String txt){
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        textArea.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
        textArea.append(txt);
        return textArea;
    }

    public JPanel boarderLayoutPanel(){
        return panel(new BorderLayout());
    }

    public JPanel flowLayoutPanel(int align,int hgap,int vgap){
       return panel(new FlowLayout(align,hgap,vgap));
    }

    public JPanel flowLayoutPanel(){
        return panel(new FlowLayout());
    }

    public JPanel panel(LayoutManager layoutManager){
        return new JPanel(layoutManager);
    }

    public JDatePickerImpl datePicker(){
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        return new JDatePickerImpl(datePanel);
    }

    public void showMessageDialog(Component parentComponent,String message) {
        JOptionPane.showMessageDialog(parentComponent, message);
    }

    public JScrollPane scrollPanel(JPanel panel){
        return new JScrollPane(panel);
    }

    public JTextField textInput(int columns){
        return new JTextField(columns);
    }

    private void createBackButton(JFrame frame) {
        final JButton button = button("Back",100,30);
        goBackToHomePageAction(button,frame);
    }


    public JTable createReadOnlyTable() {
        JTable table = new JTable();
        final DefaultTableModel tableModel = createReadOnlyTableModel();
        table.removeAll();
        table.setModel(tableModel);
        return table;
    }

    public JPanel createBoxLayoutPanel(int axis) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,axis));
        return mainPanel;
    }

    public DefaultTableModel createReadOnlyTableModel(){
        return new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void goBackToHomePageAction(JButton button,JFrame frame) {
        button.addActionListener(e -> {
            final App app = new App();
            app.run();
            frame.dispose();
        });
    }

}
