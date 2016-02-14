package gui;

import java.awt.Dimension;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;


public class FormattedTextFieldRow extends TextFieldRow{
	private JFormattedTextField ft;
	
	public FormattedTextFieldRow(String lt, boolean isRating) {
		super(lt);
		this.remove(t);
		// Why is this not working.
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		if (isRating) {
			format.setMinimumIntegerDigits(1);
			format.setMaximumIntegerDigits(2);
			format.setMaximumFractionDigits(2);
			formatter.setMaximum(10);
		} else {
			format.setMaximumFractionDigits(0);
		}
//		formatter.setFormat(format);
		ft = new JFormattedTextField(formatter);
		Dimension ftd = new Dimension(150, 30);
		ft.setMinimumSize(ftd);
		ft.setPreferredSize(ftd);
		ft.setMaximumSize(ftd);
		this.add(ft);
	}
	
	public void setValue(String s) {
		throw new UnsupportedOperationException();
	}
	
	public String getValue() {
		return ft.getText();
	}
}
