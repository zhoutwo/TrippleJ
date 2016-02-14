package gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboBoxRow extends JPanel {
	private final JComboBox<String> cb;
	private final JLabel l;
	private final String[] itemList;
	
	public ComboBoxRow(String lt, String[] s) {
		super();
		itemList = s;
		this.setPreferredSize(new Dimension(250, 30));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		l = new JLabel(lt);
		Dimension ld = new Dimension(80, 30);
		l.setMinimumSize(ld);
		l.setPreferredSize(ld);
		l.setMaximumSize(ld);
		cb = new JComboBox<String>(s);
		Dimension cd = new Dimension(150, 30);
		cb.setMinimumSize(cd);
		cb.setPreferredSize(cd);
		cb.setMaximumSize(cd);
		this.add(l);
		this.add(Box.createHorizontalGlue());
		this.add(cb);
	}
	
	public String getValue() {
		return itemList[cb.getSelectedIndex()];
	}
}
