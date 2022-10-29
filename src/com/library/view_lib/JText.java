package com.library.view_lib;

import javax.swing.JTextField;

public class JText extends JTextField {
	private static final long serialVersionUID = 1L;

	public JText(String text) {
		super(text);
	}

	@Override
	public String getText() {
		return super.getText().trim().replaceAll("\\s+", " ");
	}

}
