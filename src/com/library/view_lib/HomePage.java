package com.library.view_lib;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.library.core_lib.entitis.Account;
import com.library.core_lib.manager.LibManagement;

public class HomePage extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public HomePage(int choose, String title, String ID) {
		libMgm = new LibManagement();
		setFont(new Font("Times New Roman", Font.BOLD, 18));
		setBackground(Color.BLACK);
		setType(Type.UTILITY);
		setTitle("TRANG CHỦ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 489);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		txtPassWord2 = new JPasswordField();
		txtPassWord2.setColumns(10);
		txtPassWord2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel = new JLabel(title);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(123, 104, 238));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel = new JPanel();
		panel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		String tt = "";
		if (choose == 1)
			tt = "Đăng ký";
		else if (choose == 0)
			tt = "Đăng nhập";
		else if (choose == 2)
			tt = "Đổi mật khẩu";
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), tt,
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(220, 20, 60)));
		panel.setBackground(Color.WHITE);
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(14)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 530,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
										.addGap(14)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(21, Short.MAX_VALUE)));

		btnLogIn = new JButton("Đăng nhập");
		btnLogIn.addActionListener(this);
		btnLogIn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnLogIn.setBackground(Color.LIGHT_GRAY);

		lblNewLabel_1_2 = new JLabel("Nhập mật khẩu mới:");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

		lblNewLabel_1_1 = new JLabel("Mật khẩu:");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

		txtPassWord1 = new JPasswordField();
		txtPassWord1.setColumns(10);
		txtPassWord1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1 = new JLabel("Tên đăng nhập:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

		btnLogUp = new JButton("Đăng ký");
		btnLogUp.addActionListener(this);
		btnLogUp.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnLogUp.setBackground(Color.LIGHT_GRAY);

		lblNewLabel_2 = new JLabel("(*)");
		lblNewLabel_2.setForeground(SystemColor.controlShadow);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

		lblNewLabel_3 = new JLabel("(*)");
		lblNewLabel_3.setForeground(SystemColor.controlShadow);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 14));

		lblNewLabel_4 = new JLabel("(*)");
		lblNewLabel_4.setForeground(SystemColor.controlShadow);
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 14));

		txtUserName = new JTextField(ID);
		txtUserName.setColumns(10);
		txtUserName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnChangePass = new JButton("Đổi mật khẩu");
		btnChangePass.addActionListener(this);
		btnChangePass.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnChangePass.setBackground(Color.LIGHT_GRAY);

		btnShowPass = new JButton("");
		btnShowPass.setBorder(null);
		btnShowPass.setIcon(new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/invisible_16px.png")));
		btnShowPass.setBackground(Color.WHITE);
		count = 0;
		btnShowPass.addActionListener(this);

		btnShowPass1 = new JButton("");
		btnShowPass1.setBorder(null);
		btnShowPass1
				.setIcon(new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/invisible_16px.png")));
		btnShowPass1.setBackground(Color.WHITE);
		count1 = 0;
		btnShowPass1.addActionListener(this);

		lblNewLabel_1_3 = new JLabel("Nhập lại mật khẩu:");
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 14));

		txtPassWord3 = new JPasswordField();
		txtPassWord3.setEchoChar('*');
		txtPassWord3.setColumns(10);
		txtPassWord3.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_5 = new JLabel("(*)");
		lblNewLabel_5.setForeground(SystemColor.controlShadow);
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 14));

		btnShowPass2 = new JButton("");
		btnShowPass2
				.setIcon(new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/invisible_16px.png")));
		btnShowPass2.addActionListener(this);
		btnShowPass2.setBorder(null);
		btnShowPass2.setBackground(Color.WHITE);
		gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap(13, Short.MAX_VALUE)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnChangePass, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 124,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblNewLabel_1_2, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lblNewLabel_1_3, GroupLayout.PREFERRED_SIZE, 124,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)))))
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panel
								.createSequentialGroup()
								.addComponent(txtUserName, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 17,
										GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(txtPassWord1, GroupLayout.PREFERRED_SIZE, 276,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(txtPassWord2, GroupLayout.PREFERRED_SIZE, 276,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(txtPassWord3, GroupLayout.PREFERRED_SIZE, 276,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 17,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 17,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 17,
																GroupLayout.PREFERRED_SIZE)))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnShowPass2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnShowPass, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnShowPass1, GroupLayout.PREFERRED_SIZE, 24,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup().addGap(26)
								.addComponent(btnLogUp, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnLogIn, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
				.addGap(29)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(54)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtUserName, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
				.addGap(34)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(txtPassWord1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnShowPass, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
				.addGap(45)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPassWord2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnShowPass1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
				.addGap(47)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_1_3, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPassWord3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnShowPass2, GroupLayout.PREFERRED_SIZE, 28,
										GroupLayout.PREFERRED_SIZE)))
				.addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnChangePass, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLogUp, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
						.addComponent(btnLogIn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
				.addGap(62)));
		txtPassWord1.setEchoChar('*');
		txtPassWord2.setEchoChar('*');
		if (choose == 0)
			setLogIn();
		else if (choose == 1)
			setLogUpNow();
		else if (choose == 2)
			setChangePass();
		setVisible(true);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
		if (choose == 0)
			setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	}

	private void logIn() {
		if (txtUserName.getText().equals("ADMIN"))
			if (String.valueOf(txtPassWord1.getPassword()).equals("ADMIN")) {
				new Library(txtUserName.getText());
				new ShowMess("Bạn đang truy cập với tư cách là ADMIN");
				dispose();
			} else
				new ShowMess("Đăng nhập không thành công!!!\nMật khẩu không chính xác");
		else if (txtUserName.getText().equals("") || String.valueOf(txtPassWord1.getPassword()).equals(""))
			new ShowMess("Đăng nhập không thành công!!!\nĐiền đầy đủ vào các ô");
		else if (libMgm.getAccountByUserName(txtUserName.getText()) == null)
			new ShowMess("Đăng nhập không thành công!!!\nTên đăng nhập không tồn tại");
		else if (!libMgm.getAccountByUserName(txtUserName.getText()).equals(String.valueOf(txtPassWord1.getPassword())))
			new ShowMess("Đăng nhập không thành công!!!\nMật khẩu không chính xác");
		else {
			new Library(txtUserName.getText());
			if (txtUserName.getText().substring(0, 2).equals("ND"))
				new ShowMess("Bạn đang truy cập với tư cách là Người đọc");
			if (txtUserName.getText().substring(0, 2).equals("NV"))
				new ShowMess("Bạn đang truy cập với tư cách là Nhân viên");
			if (txtUserName.getText().substring(0, 2).equals("TT"))
				new ShowMess("Bạn đang truy cập với tư cách là Thủ thư");
			if (txtUserName.getText().substring(0, 2).equals("TQ"))
				new ShowMess("Bạn đang truy cập với tư cách là Thủ quỹ");
			dispose();
		}
	}

	private void logUp() {
		if (txtUserName.getText().equals("") || String.valueOf(txtPassWord1.getPassword()).equals("")
				|| String.valueOf(txtPassWord2.getPassword()).equals(""))
			new ShowMess("Đăng ký không thành công!!! Điền đầy đủ vào các ô");
		else if (libMgm.showReaderByID(txtUserName.getText()) == null
				&& libMgm.showLibStaffByID(txtUserName.getText()) == null)
			new ShowMess("Tên đăng ký không khả dụng!!!\nHãy tạo đối tượng trước khi tạo tài khoản");
		else if (libMgm.getAccountByUserName(txtUserName.getText()) != null)
			new ShowMess("Đăng ký không thành công!!!\nTên đăng ký đã tồn tại");
		else if (!checkPass(String.valueOf(txtPassWord1.getPassword())))
			new ShowMess("Đăng ký không thành công!!!\nMật khẩu không chấp nhận có dấu");
		else if (!String.valueOf(txtPassWord1.getPassword()).equals(String.valueOf(txtPassWord2.getPassword())))
			new ShowMess("Đăng ký không thành công!!!\nMật khẩu nhập lại và mật khẩu không trùng khớp");
		else {
			libMgm.addAccount(new Account(txtUserName.getText(), String.valueOf(txtPassWord1.getPassword())));
			new ShowMess("Đăng ký thành công!!!");
			dispose();
		}
	}

	private void changePass() {
		if (txtUserName.getText().equals("") || String.valueOf(txtPassWord1.getPassword()).equals("")
				|| String.valueOf(txtPassWord2.getPassword()).equals(""))
			new ShowMess("Đổi mật khẩu không thành công!!!\nĐiền đầy đủ vào các ô");
		else if (!libMgm.getAccountByUserName(txtUserName.getText()).equals(String.valueOf(txtPassWord1.getPassword())))
			new ShowMess("Đổi mật khẩu không thành công!!!\nMật khẩu nhập vào không đúng");
		else if (!checkPass(String.valueOf(txtPassWord2.getPassword())))
			new ShowMess("Đổi mật khẩu không thành công!!!\nMật khẩu không chấp nhận có dấu");
		else if (!String.valueOf(txtPassWord2.getPassword()).equals(String.valueOf(txtPassWord3.getPassword())))
			new ShowMess("Đổi mật khẩu không thành công!!!\\nMật khẩu nhập lại và mật khẩu mới không trùng khớp");
		else {
			libMgm.updateAccount(new Account(txtUserName.getText(), String.valueOf(txtPassWord2.getPassword())));
			new ShowMess("Đổi mật khẩu thành công!!!");
			dispose();
		}
	}

	private void setLogIn() {
		lblNewLabel_1_2.setVisible(false);
		txtPassWord2.setVisible(false);
		btnLogUp.setVisible(false);
		btnChangePass.setVisible(false);
		lblNewLabel_4.setVisible(false);
		btnShowPass1.setVisible(false);
		btnShowPass2.setVisible(false);
		txtPassWord3.setVisible(false);
		lblNewLabel_5.setVisible(false);
		lblNewLabel_1_3.setVisible(false);
	}

	private void setChangePass() {
		btnLogUp.setVisible(false);
		btnLogIn.setVisible(false);
		txtUserName.setEnabled(false);
	}

	private void setLogUpNow() {
		txtUserName.setEnabled(false);
		lblNewLabel_1_3.setVisible(false);
		txtPassWord3.setVisible(false);
		lblNewLabel_5.setVisible(false);
		btnShowPass2.setVisible(false);
		btnLogIn.setVisible(false);
		btnChangePass.setVisible(false);
		lblNewLabel_1_2.setText("Nhập lại mật khẩu:");
	}

	private boolean checkPass(String pass) {
		return pass.replaceAll("[ -~]", "").length() == 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnLogIn))
			logIn();
		else if (e.getSource().equals(btnLogUp))
			logUp();
		else if (e.getSource().equals(btnChangePass))
			changePass();
		else if (e.getSource().equals(btnShowPass))
			if (count++ % 2 == 0) {
				txtPassWord1.setEchoChar((char) 0);
				btnShowPass
						.setIcon(new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/eye_16px.png")));
			} else {
				txtPassWord1.setEchoChar('*');
				btnShowPass.setIcon(
						new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/invisible_16px.png")));
			}
		else if (e.getSource().equals(btnShowPass1))
			if (count1++ % 2 == 0) {
				txtPassWord2.setEchoChar((char) 0);
				btnShowPass1
						.setIcon(new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/eye_16px.png")));
			} else {
				txtPassWord2.setEchoChar('*');
				btnShowPass1.setIcon(
						new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/invisible_16px.png")));
			}
		else if (e.getSource().equals(btnShowPass2))
			if (count2++ % 2 == 0) {
				txtPassWord3.setEchoChar((char) 0);
				btnShowPass2
						.setIcon(new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/eye_16px.png")));
			} else {
				txtPassWord3.setEchoChar('*');
				btnShowPass2.setIcon(
						new ImageIcon(HomePage.class.getResource("/com/library/view_lib/Icon/invisible_16px.png")));
			}
	}

	int count, count1, count2;
	private LibManagement libMgm;
	private JPanel contentPane;
	private JPasswordField txtPassWord1;
	private JPasswordField txtPassWord2;
	private JLabel lblNewLabel_1_2;
	private JLabel lblNewLabel_1_1;
	private JLabel lblNewLabel_1;
	private GroupLayout gl_panel;
	private GroupLayout gl_contentPane;
	private JButton btnLogIn;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JButton btnLogUp;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JTextField txtUserName;
	private JButton btnChangePass;
	private JButton btnShowPass;
	private JButton btnShowPass1;
	private JLabel lblNewLabel_1_3;
	private JPasswordField txtPassWord3;
	private JLabel lblNewLabel_5;
	private JButton btnShowPass2;
}
