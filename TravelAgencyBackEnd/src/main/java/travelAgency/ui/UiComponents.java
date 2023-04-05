package travelAgency.ui;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class UiComponents {

    public JButton button(String label,int width,int height) {
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


}
