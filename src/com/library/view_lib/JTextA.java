package com.library.view_lib;

import javax.swing.JTextArea;

public class JTextA extends JTextArea {
	private static final long serialVersionUID = 1L;

	public JTextA(String text) {
		super(text);
	}

	@Override
	public String getText() {
		return super.getText().trim().replaceAll("\\s+", " ");
	}

}
