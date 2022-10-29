package com.library.view_lib;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class ShowMess {
	public ShowMess(String s) {
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 14)));
		JOptionPane.showMessageDialog(null, s);
	}
}
