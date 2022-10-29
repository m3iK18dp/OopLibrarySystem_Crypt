package com.library.view_lib;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

public class ShowTable extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable jTable;
	private JScrollPane sp;
	private GroupLayout groupLayout;

	public ShowTable(Object[][] ob, String[] titleTable, String title) {
		setType(Type.UTILITY);
		getContentPane().setBackground(Color.BLACK);
		setBackground(Color.WHITE);
		setTitle("THÔNG TIN " + title);
		setFont(new Font("Times New Roman", Font.BOLD, 14));
		jTable = new JTable();
		jTable.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		jTable.setBackground(Color.WHITE);
		jTable.setModel(new DefaultTableModel(ob, titleTable));
		jTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
		jTable.setFont(new Font("Times New Roman", Font.BOLD, 14));
		sp = new JScrollPane(jTable);
		sp.setFont(new Font("Times New Roman", Font.BOLD, 14));
		sp.setBackground(Color.WHITE);
		groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(sp, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(sp, GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE).addContainerGap()));
		getContentPane().setLayout(groupLayout);
		setSize(1650, 700);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
