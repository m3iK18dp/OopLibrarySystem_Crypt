package com.library.view_lib;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.library.core_lib.entitis.Account;
import com.library.core_lib.entitis.Book;
import com.library.core_lib.entitis.LibStaff;
import com.library.core_lib.entitis.Reader;
import com.library.core_lib.entitis.RentalInformation;
import com.library.core_lib.manager.LibManagement;

public class Library extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Xử lý sự kiện của sách
	private void addBook() {
		if (txtBookID.getText().equals("") || txtBookName.getText().equals("") || txtBookAuthor.getText().equals("")
				|| txtBookQuantity.getText().equals("") || txtBookKindOfBook.getText().equals(""))
			new ShowMess("Thêm không thành công!!!\nNhập đủ các dòng (*) để thêm sách");
		else
			try {
				if (libMgm.showBookByID(txtBookID.getText()) != null)
					new ShowMess("Thêm không thành công!!!\nID " + txtBookID.getText() + " đã được sử dụng");
				else if (txtBookID.getText().replaceAll("[a-zA-Z0-9]", "").length() != 0)
					new ShowMess("Thêm không thành công!!!\nID sách không cho phép nhập có dấu hoặc ký tự đặc biệt");
				else if (Integer.parseInt(txtBookQuantity.getText()) < 0)
					new ShowMess("Thêm không thành công!!!\nSố lượng không chấp nhận số âm");
				else if (libMgm.addBook(new Book(txtBookID.getText(), txtBookName.getText(), txtBookAuthor.getText(),
						Integer.parseInt(txtBookQuantity.getText()), txtBookKindOfBook.getText())))
					new ShowMess("Thêm thành công!!!");
				else
					new ShowMess("Thêm không thành công!!! Kiểm tra lại thông tin đã nhập");
			} catch (Exception e) {
				new ShowMess("Thêm không thành công!!!\nSố lượng chỉ chấp nhập ký tự số");
			}
	}

	private void updateBook() {
		if (txtBookID.getText().equals(""))
			new ShowMess("Sửa không thành công!!!\nĐiền ID để sửa thông tin sách");
		else
			try {
				Book b = libMgm.showBookByID(txtBookID.getText());
				if (b == null)
					new ShowMess("Sửa không thành công!!!\nID " + txtBookID.getText() + " chưa có trong danh sách");
				else {
					if (!txtBookAuthor.getText().equals(""))
						b.setAuthor(txtBookAuthor.getText());
					if (!txtBookKindOfBook.getText().equals(""))
						b.setKindOfBook(txtBookKindOfBook.getText());
					if (!txtBookQuantity.getText().equals(""))
						b.setQuantity(Integer.parseInt(txtBookQuantity.getText()));
					if (b.getQuantity() < 0)
						new ShowMess("Sửa không thành công!!!\nSố lượng không chấp nhận số âm");
					else if (libMgm.updateBook(b))
						new ShowMess("Sửa thành công!!!");
					else
						new ShowMess("Sửa không thành công!!!\nKiểm tra lại số liệu đã nhập");
				}
			} catch (Exception e) {
				new ShowMess("Sửa không thành công!!!\nSố lượng chỉ chấp nhập ký tự số");
			}
	}

	private void deleteBook() {
		if (!txtBookID.getText().equals(""))
			if (libMgm.deleteBookByID(txtBookID.getText()))
				new ShowMess("Xóa thành công!!!");
			else
				new ShowMess("Xóa không thành công!!!\nID " + txtBookID.getText() + " này không có trong danh sách");
		else
			new ShowMess("Xóa không thành công!!!\nNhập ID để thực hiện thao tác xóa");
	}

	private void showAllBook() {
		List<Book> b = libMgm.showAllBook();
		if (b.size() > 0)
			new ShowTable(convertListBookToArray(b),
					new String[] { "ID sách", "Tên", "Tác giả", "Số lượng", "Loại sách" }, "CỦA TẤT CẢ CÁC SÁCH");
		else
			new ShowMess("Danh sách chưa có cuốn sách nào cả");
	}

	private void showBookByID() {
		if (!txtBookID.getText().equals("")) {
			Book b = libMgm.showBookByID(txtBookID.getText());
			if (b != null)
				new ShowTable(
						new String[][] {
								{ b.getID(), b.getName(), b.getAuthor(), "" + b.getQuantity(), b.getKindOfBook() } },
						new String[] { "ID sách", "Tên", "Tác giả", "Số lượng", "Loại sách" },
						"CỦA SÁCH CÓ ID LÀ " + txtBookID.getText());
			else
				new ShowMess("ID " + txtBookID.getText() + " chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showBookInText() {
		if (!txtBookID.getText().equals("")) {
			Book b = libMgm.showBookByID(txtBookID.getText());
			if (b != null) {
				txtBookName.setText(b.getName());
				txtBookAuthor.setText(b.getAuthor());
				txtBookQuantity.setText("" + b.getQuantity());
				txtBookKindOfBook.setText(b.getKindOfBook());
			} else
				new ShowMess("ID " + txtBookID.getText() + " chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showBookByName() {
		if (!txtBookName.getText().equals("")) {
			List<Book> b = libMgm.showBookByName(txtBookName.getText());
			if (b.size() > 0)
				new ShowTable(convertListBookToArray(b),
						new String[] { "ID sách", "Tên", "Tác giả", "Số lượng", "Loại sách" },
						"CỦA TẤT CẢ CUỐN SÁCH CÓ TÊN LÀ " + txtBookName.getText());
			else
				new ShowMess("Không có cuốn sách nào có tên là " + txtBookName.getText());
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập tên sách vào dòng Name để thực hiện thao tác");
	}

	private void clearBook() {
		int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn bộ sách", "Xác nhận",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION)
			libMgm.clearBook();
	}

	private void refreshBook() {
		txtBookID.setText("");
		txtBookName.setText("");
		txtBookAuthor.setText("");
		txtBookQuantity.setText("");
		txtBookKindOfBook.setText("");
	}

	private String[][] convertListBookToArray(List<Book> bookList) {
		String[][] dataBook = new String[bookList.size()][5];
		for (int i = 0; i < bookList.size(); i++)
			dataBook[i] = new String[] { bookList.get(i).getID(), bookList.get(i).getName(),
					bookList.get(i).getAuthor(), "" + bookList.get(i).getQuantity(), bookList.get(i).getKindOfBook() };
		return dataBook;
	}

	// Xử lý sự kiện của reader
	private void addReader() {
		boolean check = true;
		if (txtReaderID.getText().equals("") || txtReaderFullName.getText().equals("")
				|| txtReaderGender.getText().equals("") || txtReaderDob.getText().equals("")
				|| txtReaderAddress.getText().equals("") || txtReaderPhoneNumber.getText().equals("")
				|| txtReaderStartDate.getText().equals("") || txtReaderEndDate.getText().equals(""))
			new ShowMess("Thêm không thành công!!!\nNhập đủ các dòng (*) để thêm người đọc");
		else if (libMgm.showReaderByID(txtReaderID.getText()) != null)
			new ShowMess("Thêm không thành công!!!\nID" + txtReaderID.getText() + " đã được sử dụng");
		else {
			try {
				if (!txtReaderID.getText().substring(0, 2).equals("ND")) {
					new ShowMess("Thêm không thành công!!!\nID của người đọc có dạng ND + số");
					check = false;
				} else
					Integer.parseInt(txtReaderID.getText().substring(2));
			} catch (Exception e) {
				new ShowMess("Thêm không thành công!!!\nID của người đọc có dạng ND + số");
				check = false;
			}
			if (check)
				try {
					Reader r = new Reader();
					r.setID(txtReaderID.getText());
					r.setFullName(txtReaderFullName.getText());
					r.setGender(txtReaderGender.getText());
					r.setAddress(txtReaderAddress.getText());
					r.setLicense(txtReaderLicense.getText());
					r.setPhoneNumber(txtReaderPhoneNumber.getText());
					r.setGmail(txtReaderGmail.getText());
					r.setKindOfReader(txtReaderKindOfReader.getText());
					r.setDob(Date.valueOf(txtReaderDob.getText()));
					if (r.getDob().compareTo(new Date(System.currentTimeMillis())) >= 0)
						new ShowMess("Thêm không thành công!!!\nNgày sinh không phù hợp");
					else {
						r.setStartDate(Date.valueOf(txtReaderStartDate.getText()));
						r.setEndDate(Date.valueOf(txtReaderEndDate.getText()));
						if (r.getStartDate().compareTo(r.getEndDate()) >= 0)
							new ShowMess("Thêm không thành công!!!\nNgày đăng ký phải trước ngày hết hạn");
						else if (libMgm.addReader(r))
							new HomePage(1, "TẠO TÀI KHOẢN CHO NGƯỜI ĐỌC", txtReaderID.getText());
						else
							new ShowMess("Thêm không thành công!!!\nKiểm tra lại thông tin đã nhập");
					}
				} catch (Exception e) {
					new ShowMess("Thêm không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
				}
		}
	}

	private void updateReader() {
		if (txtReaderID.getText().equals(""))
			new ShowMess("Sửa không thành công!!!\nĐiền ID để sửa thông tin người đọc");
		else
			try {
				Reader r = libMgm.showReaderByID(txtReaderID.getText());
				if (r == null)
					new ShowMess("Sửa không thành công!!!\nID " + txtReaderID.getText() + " chưa có trong danh sách");
				else {
					if (!txtReaderFullName.getText().equals(""))
						r.setFullName(txtReaderFullName.getText());
					if (!txtReaderGender.getText().equals(""))
						r.setGender(txtReaderGender.getText());
					if (!txtReaderDob.getText().equals(""))
						r.setDob(Date.valueOf(txtReaderDob.getText()));
					if (r.getStartDate().compareTo(r.getEndDate()) >= 0)
						new ShowMess("Sửa không thành công!!!\nNgày đăng ký phải trước ngày hết hạn");
					else {
						if (!txtReaderAddress.getText().equals(""))
							r.setAddress(txtReaderAddress.getText());
						if (!txtReaderLicense.getText().equals(""))
							r.setLicense(txtReaderLicense.getText());
						if (!txtReaderPhoneNumber.getText().equals(""))
							r.setPhoneNumber(txtReaderPhoneNumber.getText());
						if (!txtReaderGmail.getText().equals(""))
							r.setGmail(txtReaderGmail.getText());
						if (!txtReaderKindOfReader.getText().equals(""))
							r.setKindOfReader(txtReaderKindOfReader.getText());
						if (!txtReaderStartDate.getText().equals(""))
							r.setStartDate(Date.valueOf(txtReaderStartDate.getText()));
						if (!txtReaderEndDate.getText().equals(""))
							r.setEndDate(Date.valueOf(txtReaderEndDate.getText()));
						if (r.getStartDate().compareTo(r.getEndDate()) >= 0)
							new ShowMess("Sửa không thành công!!!\nNgày đăng ký phải trước ngày hết hạn");
						else if (libMgm.updateReader(r))
							new ShowMess("Sửa thành công!!!");
						else
							new ShowMess("Sửa không thành công!!!\nKiểm tra lại số liệu đã nhập");
					}
				}
			} catch (Exception e) {
				new ShowMess("Sửa không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
			}
	}

	private void deleteReader() {
		if (!txtReaderID.getText().equals(""))
			if (libMgm.deleteReaderByID(txtReaderID.getText())) {
				libMgm.deleteAccountByUserName(txtReaderID.getText());
				new ShowMess("Xóa thành công");
			} else
				new ShowMess("Xóa không thành công!!!\nID " + txtReaderID.getText() + " không có trong danh sách");
		else
			new ShowMess("Xóa không thành công!!!\nNhập ID để thực hiện thao tác xóa");
	}

	private void showAllReader() {
		List<Reader> r = libMgm.showAllReader();
		if (r.size() > 0)
			new ShowTable(convertListReaderToArray(r),
					new String[] { "ID người đọc", "Họ và tên", "Giới tính", "Ngày sinh", "Địa chỉ", "CMND/CCCD",
							"Số điện thoại", "Gmail", "Đối tượng", "Ngày đăng ký", "Hạn đăng ký" },
					"CỦA TẤT CẢ NGƯỜI ĐỌC");
		else
			new ShowMess("Danh sách chưa có người đọc nào cả");
	}

	private void showReaderByID() {
		if (!txtReaderID.getText().equals("")) {
			Reader r = libMgm.showReaderByID(txtReaderID.getText());
			if (r != null)
				new ShowTable(
						new String[][] { { r.getID(), r.getFullName(), r.getGender(), "" + r.getDob(), r.getAddress(),
								r.getLicense(), r.getPhoneNumber(), r.getGmail(), r.getKindOfReader(),
								"" + r.getStartDate(), "" + r.getEndDate() } },
						new String[] { "ID người đọc", "Họ và tên", "Giới tính", "Ngày sinh", "Địa chỉ", "CMND/CCCD",
								"Số điện thoại", "Gmail", "Đối tượng", "Ngày đăng ký", "Hạn đăng ký" },
						"CỦA TẤT CẢ NGƯỜI ĐỌC CÓ ID LÀ " + txtReaderID.getText());
			else
				new ShowMess("ID " + txtReaderID.getText() + "  chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showReaderInText() {
		if (!txtReaderID.getText().equals("")) {
			Reader r = libMgm.showReaderByID(txtReaderID.getText());
			if (r != null) {
				txtReaderFullName.setText(r.getFullName());
				txtReaderGender.setText(r.getGender());
				txtReaderDob.setText("" + r.getDob());
				txtReaderAddress.setText(r.getAddress());
				txtReaderLicense.setText(r.getLicense());
				txtReaderPhoneNumber.setText(r.getPhoneNumber());
				txtReaderGmail.setText(r.getGmail());
				txtReaderKindOfReader.setText(r.getKindOfReader());
				txtReaderStartDate.setText("" + r.getStartDate());
				txtReaderEndDate.setText("" + r.getEndDate());
			} else
				new ShowMess("\nID " + txtReaderID.getText() + " chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showReaderByName() {
		if (!txtReaderFullName.getText().equals("")) {
			List<Reader> r = libMgm.showReaderByName(txtReaderFullName.getText());
			if (r.size() > 0)
				new ShowTable(convertListReaderToArray(libMgm.showReaderByName(txtReaderFullName.getText())),
						new String[] { "ID người đọc", "Họ và tên", "Giới tính", "Ngày sinh", "Địa chỉ", "CMND/CCCD",
								"Số điện thoại", "Gmail", "Đối tượng", "Ngày đăng ký", "Hạn đăng ký" },
						"CỦA TẤT CẢ NGƯỜI ĐỌC CÓ TÊN LÀ " + txtReaderFullName.getText());
			else
				new ShowMess("Không có người đọc nào có tên là " + txtReaderFullName.getText());
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập tên người đọc vào dòng Name để thực hiện thao tác");
	}

	private void showReaderOverdue() {
		List<Reader> r = libMgm.showReaderOverdue();
		if (r.size() > 0)
			new ShowTable(convertListReaderToArray(r),
					new String[] { "ID người đọc", "Họ và tên", "Giới tính", "Ngày sinh", "Địa chỉ", "CMND/CCCD",
							"Số điện thoại", "Gmail", "Đối tượng", "Ngày đăng ký", "Hạn đăng ký" },
					"CỦA TẤT CẢ NGƯỜI ĐỌC ĐÃ HẾT HẠN ĐĂNG KÝ");
		else
			new ShowMess("Không có người đọc nào hết hạn");
	}

	private void clearReader() {
		int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn bộ người đọc", "Xác nhận",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			libMgm.clearAccount("ND");
			libMgm.clearReader();
		}
	}

	private void refreshReader() {
		txtReaderID.setText("");
		txtReaderFullName.setText("");
		txtReaderGender.setText("");
		txtReaderDob.setText("");
		txtReaderAddress.setText("");
		txtReaderLicense.setText("");
		txtReaderPhoneNumber.setText("");
		txtReaderGmail.setText("");
		txtReaderKindOfReader.setText("");
		txtReaderStartDate.setText("");
		txtReaderEndDate.setText("");
	}

	private String[][] convertListReaderToArray(List<Reader> readerList) {
		String[][] dataReader = new String[readerList.size()][11];
		for (int i = 0; i < readerList.size(); i++) {
			dataReader[i] = new String[] { readerList.get(i).getID(), readerList.get(i).getFullName(),
					readerList.get(i).getGender(), "" + readerList.get(i).getDob(), readerList.get(i).getAddress(),
					readerList.get(i).getLicense(), readerList.get(i).getPhoneNumber(), readerList.get(i).getGmail(),
					readerList.get(i).getKindOfReader(), "" + readerList.get(i).getStartDate(),
					"" + readerList.get(i).getEndDate() };
		}
		return dataReader;
	}

	// Xử lý sự kiện của Lib staff
	private void addLibStaff() {
		boolean check = true;
		if (txtLibStaffID.getText().equals("") || txtLibStaffFullName.getText().equals("")
				|| txtLibStaffGender.getText().equals("") || txtLibStaffDob.getText().equals("")
				|| txtLibStaffAddress.getText().equals("") || txtLibStaffLicense.getText().equals("")
				|| txtLibStaffPhoneNumber.getText().equals("") || txtLibStaffStartWorkDate.getText().equals("")
				|| txtLibStaffPosition.getText().equals(""))
			new ShowMess("Thêm không thành công!!!\nNhập đủ các dòng (*) để thêm");
		else if (libMgm.showLibStaffByID(txtLibStaffID.getText()) != null)
			new ShowMess("Thêm không thành công!!!\nID " + txtLibStaffID.getText() + " đã được sử dụng");
		else {
			LibStaff ls = new LibStaff();
			try {
				ls.setID(txtLibStaffID.getText());
				if (!txtLibStaffID.getText().substring(0, 2).equals("NV")
						&& !txtLibStaffID.getText().substring(0, 2).equals("TT")
						&& !txtLibStaffID.getText().substring(0, 2).equals("TQ")) {
					new ShowMess(
							"Thêm không thành công!!!\nNếu là Nhân viên thì ID có dạng NV + số\nNếu là Thủ thư thì ID có dạng TT + số\nNếu là Thủ quỹ thì ID có dạng TQ + số");
					check = false;
				} else
					Integer.parseInt(txtLibStaffID.getText().substring(2));
			} catch (Exception e) {
				new ShowMess(
						"Thêm không thành công!!!\nNếu là Nhân viên thì ID có dạng NV + số\nNếu là Thủ thư thì ID có dạng TT + số\nNếu là Thủ quỹ thì ID có dạng TQ + số");
				check = false;
			}
			if (check)
				try {

					ls.setDob(Date.valueOf(txtLibStaffDob.getText()));
					if (ls.getDob().compareTo(new Date(System.currentTimeMillis())) >= 0) {
						new ShowMess("Thêm không thành công!!!\nNgày sinh không phù hợp");
						check = false;
					}
					if (check)
						ls.setStartWorkDate(Date.valueOf(txtLibStaffStartWorkDate.getText()));
					if (check && !txtLibStaffBasicSalary.getText().equals("")) {
						ls.setBasicSalary(Integer.parseInt(txtLibStaffBasicSalary.getText()));
						if (ls.getBasicSalary() < 0) {
							new ShowMess("Thêm không thành công!!!\nTiền lương cơ bản không chấp nhận số âm");
							check = false;
						}
					}
					if (check && !txtLibStaffSalaryBonus.getText().equals("")) {
						ls.setSalaryBonus(Integer.parseInt(txtLibStaffSalaryBonus.getText()));
						if (ls.getSalaryBonus() < 0) {
							new ShowMess("Thêm không thành công!!!\nLương cơ bản chỉ chấp nhập ký tự số");
							check = false;
						}
					}
					if (check && !txtLibStaffPenalty.getText().equals("")) {
						ls.setPenalty(Integer.parseInt(txtLibStaffPenalty.getText()));
						if (ls.getPenalty() < 0) {
							new ShowMess("Thêm không thành công!!!\nTiền phạt không chấp nhận số âm");
							check = false;
						}
					}
					ls.setActualSalary();
					if (check) {
						ls.setFullName(txtLibStaffFullName.getText());
						ls.setGender(txtLibStaffGender.getText());
						ls.setAddress(txtLibStaffAddress.getText());
						ls.setLicense(txtLibStaffLicense.getText());
						ls.setPhoneNumber(txtLibStaffPhoneNumber.getText());
						ls.setGmail(txtLibStaffGmail.getText());
						ls.setPosition("" + txtLibStaffPosition.getText());
						if (libMgm.addLibStaff(ls)) {
							new ShowMess("Thêm thành công");
							new HomePage(1,
									txtLibStaffID.getText().substring(0, 2).equals("NV")
											? "ĐĂNG KÝ TÀI KHOẢN CHO NHÂN VIÊN"
											: "ĐĂNG KÝ TÀI KHOẢN CHO QUẢN LÝ",
									txtLibStaffID.getText());
						} else
							new ShowMess("Thêm không thành công!!!\nKiểm tra lại thông tin đã nhập");
					}
				} catch (NumberFormatException e) {
					new ShowMess("Thêm không thành công!!!\nLương cơ bản không chấp nhận số âm");
				} catch (IllegalArgumentException e) {
					new ShowMess("Thêm không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
				} catch (Exception e) {
					new ShowMess("Thêm không thành công!!!\nKiểm tra lại thông tin đã nhập");
				}
		}
	}

	private void updateLibStaff() {
		boolean check = true;
		if (txtLibStaffID.getText().equals(""))
			new ShowMess("Sửa không thành công!!!\nĐiền ID để sửa thông tin nhân viên");
		else
			try {
				LibStaff ls = libMgm.showLibStaffByID(txtLibStaffID.getText());
				if (ls == null)
					new ShowMess("\nID " + txtLibStaffID.getText() + " chưa có trong danh sách");
				else {
					if (!txtLibStaffDob.getText().equals("")) {
						ls.setDob(Date.valueOf(txtLibStaffDob.getText()));
						if (ls.getDob().compareTo(new Date(System.currentTimeMillis())) >= 0) {
							new ShowMess("Sửa không thành công!!!\nNgày sinh không phù hợp");
							check = false;
						}
					}
					if (check && !txtLibStaffStartWorkDate.getText().equals(""))
						ls.setStartWorkDate(Date.valueOf(txtLibStaffStartWorkDate.getText()));
					if (check && !txtLibStaffBasicSalary.getText().equals("")) {
						ls.setBasicSalary(Integer.parseInt(txtLibStaffBasicSalary.getText()));
						if (ls.getBasicSalary() < 0) {
							new ShowMess("Sửa không thành công!!!\nTiền lương cơ bản không chấp nhận số âm");
							check = false;
						}
					}
					if (check && !txtLibStaffSalaryBonus.getText().equals("")) {
						ls.setSalaryBonus(Integer.parseInt(txtLibStaffSalaryBonus.getText()));
						if (ls.getSalaryBonus() < 0) {
							new ShowMess("Sửa không thành công!!!\nLương thưởng không chấp nhận số âm");
							check = false;
						}
					}
					if (check && !txtLibStaffPenalty.getText().equals("")) {
						ls.setPenalty(Integer.parseInt(txtLibStaffPenalty.getText()));
						if (ls.getPenalty() < 0) {
							new ShowMess("Sửa không thành công!!!\nTiền phạt không chấp nhận số âm");
							check = false;
						}
					}
					ls.setActualSalary();
					if (check) {
						if (!txtLibStaffFullName.getText().equals(""))
							ls.setFullName(txtLibStaffFullName.getText());
						if (!txtLibStaffGender.getText().equals(""))
							ls.setGender(txtLibStaffGender.getText());
						if (!txtLibStaffAddress.getText().equals(""))
							ls.setAddress(txtLibStaffAddress.getText());
						if (!txtLibStaffLicense.getText().equals(""))
							ls.setLicense(txtLibStaffLicense.getText());
						if (!txtLibStaffPhoneNumber.getText().equals(""))
							ls.setPhoneNumber(txtLibStaffPhoneNumber.getText());
						if (!txtLibStaffGmail.getText().equals(""))
							ls.setGmail(txtLibStaffGmail.getText());
						if (!txtLibStaffPosition.getText().equals(""))
							ls.setPosition("" + txtLibStaffPosition.getText());
						if (libMgm.addLibStaff(ls))
							new ShowMess("Sửa thành công");
						else
							new ShowMess("Sửa không thành công!!!\nKiểm tra lại các thông tin đã nhập");
					}
				}
			} catch (NumberFormatException e) {
				new ShowMess("Sửa không thành công!!!\nLương cơ bản chỉ chấp nhận chữ số");
			} catch (IllegalArgumentException e) {
				new ShowMess("Sửa không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
			} catch (Exception e) {
				new ShowMess("Sửa không thành công!!!\nKiểm tra lại thông tin đã nhập");
			}
	}

	private void deleteLibStaff() {
		if (!txtLibStaffID.getText().equals(""))
			if (libMgm.deleteLibStaffByID(txtLibStaffID.getText())) {
				new ShowMess("Xóa thành công");
				libMgm.deleteAccountByUserName(txtLibStaffID.getText());
			} else
				new ShowMess("Xóa không thành công!!!\nID " + txtLibStaffID.getText() + " không có trong danh sách");
		else
			new ShowMess("Xóa không thành công!!!\nNhập ID để thực hiện thao tác xóa");
	}

	private void showAllLibStaff() {
		List<LibStaff> ls = libMgm.showAllLibStaff();
		if (ls.size() > 0)
			new ShowTable(convertListLibStaffToArray(ls),
					new String[] { "ID nhân viên", "Họ và tên", "Giới tính", "Ngày sinh", "Địa chỉ", "CMND/CCCD",
							"Số điện thoại", "Gmail", "Ngày bắt đầu làm việc", "Chức vụ", "Lương cơ bản",
							"Lương thưởng", "Tiền phạt", "Lương thực lĩnh" },
					"CỦA TẤT CẢ NGƯỜI QUẢN LÝ THƯ VIỆN");
		else
			new ShowMess("Danh sách chưa có nhân viên nào cả");
	}

	private void showLibStaffByID() {
		if (!txtLibStaffID.getText().equals("")) {
			LibStaff ls = libMgm.showLibStaffByID(txtLibStaffID.getText());
			if (ls != null)
				new ShowTable(
						new String[][] { { ls.getID(), ls.getFullName(), ls.getGender(), "" + ls.getDob(),
								ls.getAddress(), ls.getLicense(), "" + ls.getPhoneNumber(), ls.getGmail(),
								"" + ls.getStartWorkDate(), ls.getPosition(), "" + ls.getBasicSalary(),
								"" + ls.getSalaryBonus(), "" + ls.getPenalty(), "" + ls.getActualSalary() } },
						new String[] { "ID nhân viên", "Họ và tên", "Giới tính", "Ngày sinh", "Địa chỉ", "CMND/CCCD",
								"Số điện thoại", "Gmail", "Ngày bắt đầu làm việc", "Chức vụ", "Lương cơ bản",
								"Lương thưởng", "Tiền phạt", "Lương thực lĩnh" },
						"CỦA NGƯỜI QUẢN LÝ CÓ ID LÀ " + txtLibStaffID.getText());
			else
				new ShowMess("ID " + txtLibStaffID.getText() + " chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showLibStaffInText() {
		if (!txtLibStaffID.getText().equals("")) {
			LibStaff ls = libMgm.showLibStaffByID(txtLibStaffID.getText());
			if (ls != null) {
				txtLibStaffFullName.setText(ls.getFullName());
				txtLibStaffGender.setText(ls.getGender());
				txtLibStaffDob.setText("" + ls.getDob());
				txtLibStaffAddress.setText(ls.getAddress());
				txtLibStaffLicense.setText(ls.getLicense());
				txtLibStaffPhoneNumber.setText(ls.getPhoneNumber());
				txtLibStaffGmail.setText(ls.getGmail());
				txtLibStaffStartWorkDate.setText("" + ls.getStartWorkDate());
				txtLibStaffPosition.setText(ls.getPosition());
				txtLibStaffBasicSalary.setText("" + ls.getBasicSalary());
				txtLibStaffSalaryBonus.setText("" + ls.getSalaryBonus());
				txtLibStaffPenalty.setText("" + ls.getPenalty());
			} else
				new ShowMess("ID " + txtLibStaffID.getText() + " chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showLibStaffByName() {
		if (!txtLibStaffFullName.getText().equals("")) {
			List<LibStaff> ls = libMgm.showLibStaffByName(txtLibStaffFullName.getText());
			if (ls.size() > 0)
				new ShowTable(convertListLibStaffToArray(ls),
						new String[] { "ID nhân viên", "Họ và tên", "Giới tính", "Ngày sinh", "Địa chỉ", "CMND/CCCD",
								"Số điện thoại", "Gmail", "Ngày bắt đầu làm việc", "Chức vụ", "Lương cơ bảng",
								"Lương thưởng", "Tiền phạt", "Lương thực lĩnh" },
						"CỦA TẤT CẢ NGƯỜI QUẢN LÝ CÓ TÊN LÀ " + txtLibStaffFullName.getText());
			else
				new ShowMess("Không có người đọc nào có tên là " + txtLibStaffFullName.getText());
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập tên nhân viên vào dòng Name để thực hiện thao tác");
	}

	private void clearLibStaff() {
		int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn bộ nhân viên", "Xác nhận",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			libMgm.clearAccount("NV");
			libMgm.clearAccount("QL");
			libMgm.clearLibStaff();
		}
	}

	private void refreshLibStaff() {
		txtLibStaffID.setText("");
		txtLibStaffFullName.setText("");
		txtLibStaffGender.setText("");
		txtLibStaffDob.setText("");
		txtLibStaffAddress.setText("");
		txtLibStaffLicense.setText("");
		txtLibStaffPhoneNumber.setText("");
		txtLibStaffGmail.setText("");
		txtLibStaffStartWorkDate.setText("");
		txtLibStaffPosition.setText("");
		txtLibStaffBasicSalary.setText("");
		txtLibStaffSalaryBonus.setText("");
		txtLibStaffPenalty.setText("");
	}

	private String[][] convertListLibStaffToArray(List<LibStaff> libStaffList) {
		String[][] dataLibStaff = new String[libStaffList.size()][14];
		for (int i = 0; i < libStaffList.size(); i++)
			dataLibStaff[i] = new String[] { libStaffList.get(i).getID(), libStaffList.get(i).getFullName(),
					libStaffList.get(i).getGender(), "" + libStaffList.get(i).getDob(),
					libStaffList.get(i).getAddress(), libStaffList.get(i).getLicense(),
					libStaffList.get(i).getPhoneNumber(), libStaffList.get(i).getGmail(),
					"" + libStaffList.get(i).getStartWorkDate(), libStaffList.get(i).getPosition(),
					"" + libStaffList.get(i).getBasicSalary(), "" + libStaffList.get(i).getSalaryBonus(),
					"" + libStaffList.get(i).getPenalty(), "" + libStaffList.get(i).getActualSalary() };
		return dataLibStaff;
	}

	// Xử lý sự kiện của RentalInfor
	private void addRentalInfor() {
		boolean check = true;
		if (txtRentalInforID.getText().equals("") || txtRentalInforReaderID.getText().equals("")
				|| txtRentalInforBookID.getText().equals("") || txtRentalInforLibStaffID.getText().equals("")
				|| txtRentalInforBookBorrowDate.getText().equals("")
				|| txtRentalInforBookReturnDate.getText().equals(""))
			new ShowMess("Mượn sách không thành công!!!\nNhập đủ các dòng (*) để thêm");
		else if (libMgm.showRentalInforByID(txtRentalInforID.getText()) != null)
			new ShowMess("Thêm không thành công!!!\nID " + txtRentalInforID.getText() + " đã được sử dụng");
		else
			try {
				RentalInformation ri = new RentalInformation();
				ri.setID(txtRentalInforID.getText());
				if (txtRentalInforID.getText().replaceAll("[0-9a-zA-Z]", "").length() != 0)
					new ShowMess("Mượn không thành công!!!\nID không cho phép nhập có dấu hoặc ký tự đặc biệt");
				if (libMgm.showRentalInforByID(txtRentalInforID.getText()) != null) {
					new ShowMess("Mượn sách không thành công!!!\nID " + txtRentalInforID.getText()
							+ " đã có trong danh sách");
					check = false;
				}
				ri.setReaderID(txtRentalInforReaderID.getText());
				if (check && libMgm.showReaderByID(ri.getReaderID()) == null) {
					new ShowMess("Mượn sách không thành công!!!\nNgười đọc ID " + txtRentalInforReaderID.getText()
							+ " không có trong danh sách");
					check = false;
				}
				ri.setBookID(txtRentalInforBookID.getText());
				Book b = libMgm.showBookByID(ri.getBookID());
				if (check && b == null) {
					new ShowMess("Mượn sách không thành công!!!\nSách ID " + txtRentalInforBookID.getText()
							+ " không có trong danh sách");
					check = false;
				}
				if (check)
					for (RentalInformation cB : libMgm.showRentalInforByReaderID(ri.getReaderID()))
						if (cB.getBookID().equals(ri.getBookID())) {
							new ShowMess("Hiện tại sách này bạn đang mượn. Không thể mượn được 2 lần");
							check = false;
							break;
						}
				if (check && b.getQuantity() < 1) {
					new ShowMess(
							"Mượn sách không thành công!!!\nSách ID " + txtRentalInforBookID.getText() + " đã hết");
					check = false;
				}
				if (check && libMgm.showLibStaffByID(txtRentalInforLibStaffID.getText()) == null) {
					new ShowMess("Mượn sách không thành công!!!\nNhân viên ID " + txtRentalInforLibStaffID.getText()
							+ " không có trong danh sách");
					check = false;
				}
				ri.setLibStaffID(txtRentalInforLibStaffID.getText());
				if (check && libMgm.showLibStaffByID(txtRentalInforLibStaffID.getText()) == null)
					new ShowMess("Mượn sách không thành công!!!\\nID nhân viên cho mượn sách không đúng");
				if (check) {
					ri.setNote(txtRentalInforNote.getText());
					ri.setBookBorrowDate(Date.valueOf(txtRentalInforBookBorrowDate.getText()));
					ri.setBookReturnDate(Date.valueOf(txtRentalInforBookReturnDate.getText()));
					if (ri.getBookBorrowDate().compareTo(ri.getBookReturnDate()) > 0)
						new ShowMess("Mượn sách không thành công!!!\nNgày mượn phải trước ngày trả");
					else if (libMgm.addRentalInfor(ri)) {
						b.setQuantity(b.getQuantity() - 1);
						libMgm.updateBook(b);
						new ShowMess("Mượn sách thành công");
					} else
						new ShowMess("Mượn sách không thành công!!!Kiểm tra lại thông tin đã nhập");
				}
			} catch (Exception e) {
				e.printStackTrace();
				new ShowMess("Mượn sách không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
			}
	}

	private void updateRentalInfor() {
		boolean check = true;
		if (txtRentalInforID.getText().equals(""))
			new ShowMess("Sửa không thành công!!!\nĐiền ID để sửa thông tin sách");
		else
			try {
				RentalInformation ri = libMgm.showRentalInforByID(txtRentalInforID.getText());
				if (ri == null)
					new ShowMess("ID " + txtRentalInforID.getText() + " đã có trong danh sách");
				else {
					Book bOld = libMgm.showBookByID(ri.getBookID());
					Book bNew = null;
					if (!txtRentalInforReaderID.getText().equals("")) {
						ri.setReaderID(txtRentalInforReaderID.getText());
						if (check && libMgm.showReaderByID(ri.getReaderID()) == null) {
							new ShowMess("Sửa không thành công!!!\nNgười đọc ID " + txtRentalInforReaderID.getText()
									+ " không có trong danh sách");
							check = false;
						}
					}
					if (check && !txtRentalInforBookID.getText().equals("")) {
						ri.setBookID(txtRentalInforBookID.getText());
						bNew = libMgm.showBookByID(ri.getBookID());
						if (bNew == null) {
							new ShowMess("Sửa không thành công!!!\nSách ID " + txtRentalInforBookID.getText()
									+ " không có trong danh sách");
							check = false;
						}
						if (check)
							for (RentalInformation cB : libMgm.showRentalInforByReaderID(ri.getReaderID())) {
								if (cB.getBookID().equals(ri.getBookID())) {
									new ShowMess("Hiện tại sách ID " + txtRentalInforBookID.getText()
											+ " bạn đang mượn. Không thể mượn được 2 lần");
									check = false;
									break;
								}
							}
						if (check && bNew.getQuantity() < 1) {
							new ShowMess("Sửa không thành công!!!\nSách ID " + txtRentalInforID.getText() + " đã hết");
							check = false;
						}
					}
					if (check && !txtRentalInforLibStaffID.getText().equals("")) {
						ri.setLibStaffID(txtRentalInforLibStaffID.getText());
						if (libMgm.showLibStaffByID(ri.getLibStaffID()) == null) {
							new ShowMess("Sửa không thành công!!!\nNhân viên ID" + txtRentalInforLibStaffID.getText()
									+ " không có trong danh sách");
							check = false;
						}
					}
					if (check) {
						if (!txtRentalInforNote.getText().equals(""))
							ri.setNote(txtRentalInforNote.getText());
						if (!txtRentalInforBookBorrowDate.getText().equals(""))
							ri.setBookBorrowDate(Date.valueOf(txtRentalInforBookBorrowDate.getText()));
						if (!txtRentalInforBookReturnDate.getText().equals(""))
							ri.setBookReturnDate(Date.valueOf(txtRentalInforBookReturnDate.getText()));
						if (ri.getBookBorrowDate().compareTo(ri.getBookReturnDate()) > 0)
							new ShowMess("Sửa không thành công!!!\nNgày mượn phải trước ngày trả");
						else if (libMgm.addRentalInfor(ri)) {
							if (bNew != null) {
								bOld.setQuantity(bOld.getQuantity() + 1);
								libMgm.updateBook(bOld);
								bNew.setQuantity(bNew.getQuantity() - 1);
								libMgm.updateBook(bNew);
							}
							new ShowMess("Sửa thành công");
						} else
							new ShowMess("Sửa không thành công!!!\nKiểm tra lại thông tin đã nhập");
					}
				}
			} catch (Exception e) {
				new ShowMess("Sửa không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
			}
	}

	private void deleteRentalInfor() {
		if (!txtRentalInforID.getText().equals(""))
			if (libMgm.deleteRentalInforByID(txtRentalInforID.getText())) {
				Book b = libMgm.showBookByID(txtRentalInforBookID.getText());
				b.setQuantity(b.getQuantity() + 1);
				libMgm.updateBook(b);
				new ShowMess("Trả sách thành công!!!");
			} else
				new ShowMess("Trả không thành công!!!\nID " + txtRentalInforID.getText() + " không có trong danh sách");
		else
			new ShowMess("Trả sách không thành công!!!\nNhập ID để thực hiện thao tác xóa");
	}

	private void showAllRentalInfor() {
		List<RentalInformation> ri = libMgm.showAllRentalInfor();
		if (ri.size() > 0)
			new ShowTable(convertListRentalInforToArray(ri), new String[] { "ID", "ID người đọc", "ID sách",
					"ID nhân viên", "Ngày mượn sách", "Ngày trả sách", "Note" }, "CỦA TẤT CẢ LẦN MƯỢN");
		else
			new ShowMess("Danh sách chưa có thông tin mượn nào");
	}

	private void showRentalInforByID() {
		if (!txtRentalInforID.getText().equals("")) {
			RentalInformation ri = libMgm.showRentalInforByID(txtRentalInforID.getText());
			if (ri != null)
				new ShowTable(
						new String[][] { { ri.getID(), ri.getReaderID(), ri.getBookID(), ri.getLibStaffID(),
								"" + ri.getBookBorrowDate(), "" + ri.getBookReturnDate(), ri.getNote() } },
						new String[] { "ID", "ID người đọc", "ID sách", "ID nhân viên", "Ngày mượn sách",
								"Ngày trả sách", "Note" },
						"CỦA LẦN MƯỢN CÓ ID LÀ " + txtRentalInforID.getText());
			else
				new ShowMess("\nID " + txtRentalInforID.getText() + " chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showRentalInforInText() {
		if (!txtRentalInforID.getText().equals("")) {
			RentalInformation ri = libMgm.showRentalInforByID(txtRentalInforID.getText());
			if (ri != null) {
				txtRentalInforReaderID.setText(ri.getReaderID());
				txtRentalInforBookID.setText(ri.getBookID());
				txtRentalInforLibStaffID.setText(ri.getLibStaffID());
				txtRentalInforBookBorrowDate.setText("" + ri.getBookBorrowDate());
				txtRentalInforBookReturnDate.setText("" + ri.getBookReturnDate());
				txtRentalInforNote.setText(ri.getNote());
			} else
				new ShowMess("\nID " + txtRentalInforID.getText() + " chưa có trong danh sách");
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID để thực hiện thao tác");
	}

	private void showRentalInforByReaderID() {
		if (!txtRentalInforReaderID.getText().equals("")) {
			if (libMgm.showReaderByID(txtRentalInforReaderID.getText()) == null)
				new ShowMess("Người đọc ID " + txtRentalInforReaderID.getText() + " chưa có trong danh sách người đọc");
			else {
				List<RentalInformation> ri = libMgm.showRentalInforByReaderID(txtRentalInforReaderID.getText());
				if (ri.size() > 0)
					new ShowTable(convertListRentalInforToArray(ri),
							new String[] { "ID", "ID người đọc", "ID sách", "ID nhân viên", "Ngày mượn sách",
									"Ngày trả sách", "Note" },
							"TẤT CẢ LẦN LƯỢN CỦA NGƯỜI ĐỌC CÓ ID LÀ " + txtRentalInforReaderID.getText());
				else
					new ShowMess("Người đọc ID " + txtRentalInforReaderID.getText() + " chưa mượn cuốn sách nào cả");
			}
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID của người đọc để thực hiện thao tác");
	}

	private void showRentalInforByBookID() {
		if (!txtRentalInforBookID.getText().equals("")) {
			if (libMgm.showBookByID(txtRentalInforBookID.getText()) == null)
				new ShowMess("Sách có ID là " + txtRentalInforBookID.getText() + " chưa có trong danh sách sách");
			else {
				List<RentalInformation> ri = libMgm.showRentalInforByBookID(txtRentalInforBookID.getText());
				if (ri.size() > 0)
					new ShowTable(convertListRentalInforToArray(ri),
							new String[] { "ID", "ID người đọc", "ID sách", "ID nhân viên", "Ngày mượn sách",
									"Ngày trả sách", "Note" },
							"TẤT CẢ LẦN LƯỢN CỦA SÁCH CÓ ID LÀ " + txtRentalInforBookID.getText());
				else
					new ShowMess("Sách có ID " + txtRentalInforBookID.getText() + " chưa có ai mượn");
			}
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID của sách để thực hiện thao tác");
	}

	private void showRentalInforByLibStaffID() {
		if (!txtRentalInforLibStaffID.getText().equals("")) {
			if (libMgm.showLibStaffByID(txtRentalInforLibStaffID.getText()) == null)
				new ShowMess(
						"Nhân viên có ID " + txtRentalInforLibStaffID.getText() + " chưa có trong danh sách nhân viên");
			else {
				List<RentalInformation> ri = libMgm.showRentalInforByLibStaffID(txtRentalInforLibStaffID.getText());
				if (ri.size() > 0)
					new ShowTable(convertListRentalInforToArray(ri),
							new String[] { "ID", "ReaderID", "BookID", "LibStaffID", "Ngày mượn sách", "Ngày trả sách",
									"Note" },
							"TẤT CẢ LẦN LƯỢN CỦA NGƯỜI QUẢN LÝ CÓ ID " + txtRentalInforLibStaffID.getText()
									+ " cho mượn");
				else
					new ShowMess("Nhân viên có ID " + txtRentalInforLibStaffID.getText() + " chưa cho ai mượn sách");
			}
		} else
			new ShowMess("Thao tác không thành công!!!\nNhập ID của nhân viên để thực hiện thao tác");
	}

	private void showRentalInforOverdue() {
		List<RentalInformation> ri = libMgm.showRentalInforOverdue();
		if (ri.size() > 0)
			new ShowTable(convertListRentalInforToArray(ri), new String[] { "ID", "ReaderID", "BookID", "LibStaffID",
					"Ngày mượn sách", "Ngày trả sách", "Note" }, "CỦA TẤT CẢ LẦN MƯỢN");
		else
			new ShowMess("Không có đối tượng tới hạn cần trả sách");
	}

	private void showInforOverdue() {
		String[][] infors = libMgm.showInforOverdue();
		if (infors.length > 0)
			new ShowTable(infors, new String[] { "ID", " ID người đọc", "Tên người đọc", "CMND/CCCD người đọc",
					"Số điện thoại người đọc", "Gmail người đọc", "ID sách", "Tên sách", "Tác giả", "ID nhân viên",
					"Tên nhân viên", "CMND/CCCD nhân viên", "Số điện thoại nhân viên", "Gmail nhân viên" },
					"CỦA NHỮNG ĐỐI TƯỢNG CẦN PHẢI TRẢ SÁCH");
		else
			new ShowMess("Không có đối tượng tới hạn cần trả sách");
	}

	private void clearRentalInfor() {
		int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn bộ thông tin mượn sách", "Xác nhận",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION)
			libMgm.clearRentalInfor();
	}

	private void refreshRentalInfor() {
		txtRentalInforID.setText("");
		txtRentalInforReaderID.setText("");
		txtRentalInforBookID.setText("");
		txtRentalInforLibStaffID.setText("");
		txtRentalInforBookBorrowDate.setText("");
		txtRentalInforBookReturnDate.setText("");
		txtRentalInforNote.setText("");
	}

	private String[][] convertListRentalInforToArray(List<RentalInformation> RentalInforList) {
		String[][] dataRentalInfor = new String[RentalInforList.size()][7];
		for (int i = 0; i < RentalInforList.size(); i++)
			dataRentalInfor[i] = new String[] { RentalInforList.get(i).getID(), RentalInforList.get(i).getReaderID(),
					RentalInforList.get(i).getBookID(), "" + RentalInforList.get(i).getLibStaffID(),
					"" + RentalInforList.get(i).getBookBorrowDate(), "" + RentalInforList.get(i).getBookReturnDate(),
					RentalInforList.get(i).getNote() };
		return dataRentalInfor;
	}

	// Xử lý thông tin cá nhân account
	// Reader
	private void updateInforReader() {
		if (txtReaderFullName_1.getText().equals("") || txtReaderGender_1.getText().equals("")
				|| txtReaderDob_1.getText().equals("") || txtReaderAddress_1.getText().equals("")
				|| txtReaderPhoneNumber_1.getText().equals(""))
			new ShowMess("Cập nhập không thành công!!!\nNhập đầy thông tin vào ô (*)");
		else
			try {
				Reader r = libMgm.showReaderByID(txtReaderID_1.getText());
				r.setFullName(txtReaderFullName_1.getText());
				r.setGender(txtReaderGender_1.getText());
				r.setDob(Date.valueOf(txtReaderDob_1.getText()));
				if (r.getStartDate().compareTo(r.getEndDate()) >= 0)
					new ShowMess("Cập nhập không thành công!!!\nNgày đăng ký phải trước ngày hết hạn");
				else {
					r.setAddress(txtReaderAddress_1.getText());
					r.setLicense(txtReaderLicense_1.getText());
					r.setPhoneNumber(txtReaderPhoneNumber_1.getText());
					r.setGmail(txtReaderGmail_1.getText());
					r.setKindOfReader(txtReaderKindOfReader_1.getText());
					r.setStartDate(Date.valueOf(txtReaderStartDate_1.getText()));
					r.setEndDate(Date.valueOf(txtReaderEndDate_1.getText()));
					if (r.getStartDate().compareTo(r.getEndDate()) >= 0)
						new ShowMess("Cập nhập không thành công!!!\nNgày đăng ký phải trước ngày hết hạn");
					else if (libMgm.updateReader(r))
						new ShowMess("Cập nhập thành công!!!");
					else
						new ShowMess("Cập nhập không thành công!!!\nKiểm tra lại số liệu đã nhập");
				}
			} catch (Exception e) {
				new ShowMess("Cập nhập không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
			}
	}

	private void showInforBorrow(String readerId) {
		List<RentalInformation> ri = libMgm.showRentalInforByReaderID(txtReaderID_1.getText());
		if (ri.size() > 0)
			new ShowTable(
					convertListRentalInforToArray(ri), new String[] { "ID", "ID người đọc", "ID sách", "ID nhân viên",
							"Ngày mượn sách", "Ngày trả sách", "Note" },
					"TẤT CẢ LẦN LƯỢN CỦA NGƯỜI ĐỌC CÓ ID LÀ " + txtReaderID_1.getText());
		else
			new ShowMess("Người đọc ID " + txtReaderID_1.getText() + " chưa mượn cuốn sách nào cả");
	}

	private void undoReader() {
		Reader r = libMgm.showReaderByID(txtReaderID_1.getText());
		txtReaderFullName_1.setText(r.getFullName());
		txtReaderGender_1.setText(r.getGender());
		txtReaderDob_1.setText("" + r.getDob());
		txtReaderAddress_1.setText(r.getAddress());
		txtReaderLicense_1.setText(r.getLicense());
		txtReaderPhoneNumber_1.setText(r.getPhoneNumber());
		txtReaderGmail_1.setText(r.getGmail());
		txtReaderKindOfReader_1.setText(r.getKindOfReader());
		txtReaderStartDate_1.setText("" + r.getStartDate());
		txtReaderEndDate_1.setText("" + r.getEndDate());
	}

	private void undoLibStaff() {
		LibStaff ls = libMgm.showLibStaffByID(txtLibStaffID_1.getText());
		txtLibStaffFullName_1.setText(ls.getFullName());
		txtLibStaffGender_1.setText(ls.getGender());
		txtLibStaffDob_1.setText("" + ls.getDob());
		txtLibStaffAddress_1.setText(ls.getAddress());
		txtLibStaffLicense_1.setText(ls.getLicense());
		txtLibStaffPhoneNumber_1.setText(ls.getPhoneNumber());
		txtLibStaffGmail_1.setText(ls.getGmail());
		txtLibStaffStartWorkDate_1.setText("" + ls.getStartWorkDate());
		txtLibStaffPosition_1.setText(ls.getPosition());
		txtLibStaffBasicSalary_1.setText("" + ls.getBasicSalary());
		txtLibStaffSalaryBonus_1.setText("" + ls.getSalaryBonus());
		txtLibStaffPenalty_1.setText("" + ls.getPenalty());
		txtActualSalaryQL.setText("" + ls.getActualSalary());
	}

	// QL
	private void updateInforLibStaff() {
		boolean check = true;
		if (txtLibStaffFullName_1.getText().equals("") || txtLibStaffGender_1.getText().equals("")
				|| txtLibStaffDob_1.getText().equals("") || txtLibStaffAddress_1.getText().equals("")
				|| txtLibStaffLicense_1.getText().equals("") || txtLibStaffPhoneNumber_1.getText().equals(""))
			new ShowMess("Cập nhập không thành công!!!\nNhập đầy thông tin vào ô (*)");
		else
			try {
				LibStaff ls = libMgm.showLibStaffByID(txtLibStaffID_1.getText());
				ls.setDob(Date.valueOf(txtLibStaffDob_1.getText()));
				if (ls.getDob().compareTo(new Date(System.currentTimeMillis())) >= 0) {
					new ShowMess("Cập nhập không thành công!!!\nNgày sinh không phù hợp");
					check = false;
				}
				if (check)
					ls.setStartWorkDate(Date.valueOf(txtLibStaffStartWorkDate_1.getText()));
				if (check) {
					ls.setBasicSalary(Integer.parseInt(txtLibStaffBasicSalary_1.getText()));
					if (ls.getBasicSalary() < 0) {
						new ShowMess("Cập nhập không thành công!!!\nTiền lương cơ bản không chấp nhận số âm");
						check = false;
					}
				}
				if (check) {
					ls.setSalaryBonus(Integer.parseInt(txtLibStaffSalaryBonus_1.getText()));
					if (ls.getSalaryBonus() < 0) {
						new ShowMess("Cập nhập không thành công!!!\nLương thưởng không chấp nhận số âm");
						check = false;
					}
				}
				if (check) {
					ls.setPenalty(Integer.parseInt(txtLibStaffPenalty_1.getText()));
					if (ls.getPenalty() < 0) {
						new ShowMess("Cập nhập không thành công!!!\nTiền phạt không chấp nhận số âm");
						check = false;
					}
				}
				if (check) {
					ls.setActualSalary();
					ls.setFullName(txtLibStaffFullName_1.getText());
					ls.setGender(txtLibStaffGender_1.getText());
					ls.setAddress(txtLibStaffAddress_1.getText());
					ls.setLicense(txtLibStaffLicense_1.getText());
					ls.setPhoneNumber(txtLibStaffPhoneNumber_1.getText());
					ls.setGmail(txtLibStaffGmail_1.getText());
					ls.setPosition("" + txtLibStaffPosition_1.getText());
					if (libMgm.updateLibStaff(ls))
						new ShowMess("Cập nhập thành công");
					else
						new ShowMess("Cập nhập không thành công!!!\nKiểm tra lại các thông tin đã nhập");
				}
			} catch (NumberFormatException e) {
				new ShowMess("Cập nhập không thành công!!!\nLương cơ bản chỉ chấp nhận chữ số");
			} catch (IllegalArgumentException e) {
				new ShowMess("Cập nhập không thành công!!!\nNgày tháng năm phải được nhập dưới dạng yyyy-MM-dd");
			} catch (Exception e) {
				new ShowMess("Cập nhập không thành công!!!\nKiểm tra lại thông tin đã nhập");
			}
	}

	private void showAccount(String lv) {
		List<Account> accounts = new ArrayList<>();
		if (lv.equals("ND"))
			accounts = libMgm.showAllAccount(lv);
		else {
			accounts.addAll(libMgm.showAllAccount("TT"));
			accounts.addAll(libMgm.showAllAccount("NV"));
		}
		if (accounts.size() > 0)
			new ShowTable(covertListAccountToArray(accounts), new String[] { "Tên đăng nhập", "Mật khẩu" },
					"TẤT CẢ TÀI KHOẢN CỦA " + (lv.equals("NV") ? "NHÂN VIÊN" : "NGƯỜI ĐỌC"));
		else
			new ShowMess("Hiện không có ai");
	}

	private String[][] covertListAccountToArray(List<Account> accounts) {
		String[][] ac = new String[accounts.size()][2];
		for (int i = 0; i < accounts.size(); i++)
			ac[i] = new String[] { accounts.get(i).getUserName(), accounts.get(i).getPassword() };
		return ac;
	}

	public Library(String ID) {
		int idTab = 0;
		setType(Type.UTILITY);
		libMgm = new LibManagement();
		setBackground(Color.BLACK);
		setTitle("QUẢN LÝ THƯ VIỆN");
		setFont(new Font("Times New Roman", Font.BOLD, 20));
		setForeground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 616);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new LineBorder(Color.BLACK, 3));
		setContentPane(contentPane);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		tabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setFont(new Font("Times New Roman", Font.BOLD, 12));
		tabbedPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPane.setOpaque(true);
		tabbedPane.setBackground(Color.BLACK);
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE));
		if (ID.equals("ADMIN") || ID.substring(0, 2).equals("NV")) {
			pnlRentalInfor = new JPanel();
			pnlRentalInfor.setToolTipText("Quản lý thông tin mượn");
			pnlRentalInfor.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnlRentalInfor.setBackground(Color.WHITE);

			tabbedPane.addTab("QUẢN LÝ YÊU CẦU", null, pnlRentalInfor, "Quản lý mượn sách");
			tabbedPane.setBackgroundAt(idTab++, Color.WHITE);
			btnAddRentalInfor = new JButton("Xem yêu cầu");
			btnAddRentalInfor.addActionListener(this);
			btnAddRentalInfor.setToolTipText("Nhập các trường để mượn sách");
			btnAddRentalInfor.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnAddRentalInfor.setBackground(Color.LIGHT_GRAY);

			btnUpdateRentalInfor = new JButton("Mượn sách");
			btnUpdateRentalInfor.addActionListener(this);
			btnUpdateRentalInfor.setToolTipText("Nhập để chỉnh sửa thông qua ID");
			btnUpdateRentalInfor.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnUpdateRentalInfor.setBackground(Color.LIGHT_GRAY);

			btnDeleteRentalInfor = new JButton("Mua sách");
			btnDeleteRentalInfor.addActionListener(this);
			btnDeleteRentalInfor.setToolTipText("Nhập ID để trả sách");
			btnDeleteRentalInfor.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnDeleteRentalInfor.setBackground(Color.LIGHT_GRAY);

			btnShowAllRentalInfor = new JButton("Hiển thị tất cả thông tin mượn");
			btnShowAllRentalInfor.addActionListener(this);
			btnShowAllRentalInfor.setToolTipText("Hiển thị tất cả thông tin mượn");
			btnShowAllRentalInfor.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowAllRentalInfor.setBackground(Color.LIGHT_GRAY);

			btnRentalInforOverdue = new JButton("Trả sách");
			btnRentalInforOverdue.addActionListener(this);
			btnRentalInforOverdue.setToolTipText(
					"Hiển thị thông tin mượn sách quá hạn (Hết hạn khi sách đó mượn quá hạn hoặc thẻ của người đọc mượn sách bị hết hạn");
			btnRentalInforOverdue.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnRentalInforOverdue.setBackground(Color.LIGHT_GRAY);

			txtRentalInforID = new JText("");
			txtRentalInforID.setColumns(10);
			txtRentalInforID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtRentalInforReaderID = new JText("");
			txtRentalInforReaderID.setColumns(10);
			txtRentalInforReaderID.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtRentalInforBookID = new JText("");
			txtRentalInforBookID.setColumns(10);
			txtRentalInforBookID.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtRentalInforLibStaffID = new JText("");
			txtRentalInforLibStaffID.setColumns(10);
			txtRentalInforLibStaffID.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtRentalInforBookBorrowDate = new JText("");
			txtRentalInforBookBorrowDate.setColumns(10);
			txtRentalInforBookBorrowDate.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtRentalInforBookReturnDate = new JText("");
			txtRentalInforBookReturnDate.setColumns(10);
			txtRentalInforBookReturnDate.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_18 = new JLabel("ID:");
			lblNewLabel_18.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_19 = new JLabel("Reader ID:");
			lblNewLabel_19.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_20 = new JLabel("Book ID:");
			lblNewLabel_20.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_21 = new JLabel("Lib Staff ID:");
			lblNewLabel_21.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_22 = new JLabel("Ngày mượn sách");
			lblNewLabel_22.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_23 = new JLabel("Ngày trả sách:");
			lblNewLabel_23.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_24 = new JLabel("Chú thích:");
			lblNewLabel_24.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnShowRentalInforByReaderID = new JButton("Gia hạn sách");
			btnShowRentalInforByReaderID.addActionListener(this);
			btnShowRentalInforByReaderID.setToolTipText("Hiển thị thông qua  ID của người đọc");
			btnShowRentalInforByReaderID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowRentalInforByReaderID.setBackground(Color.LIGHT_GRAY);

			btnShowRentalInforByBookID = new JButton("Hiển thị bằng Book ID");
			btnShowRentalInforByBookID.addActionListener(this);
			btnShowRentalInforByBookID.setToolTipText("Hiển thị thông qua ID của sách");
			btnShowRentalInforByBookID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowRentalInforByBookID.setBackground(Color.LIGHT_GRAY);

			btnShowRentalInforByLibStaffID = new JButton("Hiển thị bằng Lib Staff ID");
			btnShowRentalInforByLibStaffID.addActionListener(this);
			btnShowRentalInforByLibStaffID.setToolTipText("Hiển thị thông qua ID của nhân viên cho mượn sách");
			btnShowRentalInforByLibStaffID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowRentalInforByLibStaffID.setBackground(Color.LIGHT_GRAY);

			btnShowRentalInforByID = new JButton("Hiển thị bằng ID");
			btnShowRentalInforByID.addActionListener(this);
			btnShowRentalInforByID.setToolTipText("Hiển thị thông qua ID");
			btnShowRentalInforByID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowRentalInforByID.setBackground(Color.LIGHT_GRAY);

			btnClearRentalInfor = new JButton("Xóa tất cả thông tin mượn");
			btnClearRentalInfor.setForeground(Color.WHITE);
			btnClearRentalInfor.addActionListener(this);
			btnClearRentalInfor.setToolTipText("Xóa tất cả thông tin mượn trong bảng (Cẩn thận khi dùng thao tác này)");
			btnClearRentalInfor.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnClearRentalInfor.setBackground(Color.DARK_GRAY);

			lblNewLabel_25 = new JLabel("(*)");
			lblNewLabel_25.setForeground(SystemColor.controlShadow);
			lblNewLabel_25.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_25_1 = new JLabel("(*)");
			lblNewLabel_25_1.setForeground(SystemColor.controlShadow);
			lblNewLabel_25_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_25_2 = new JLabel("(*)");
			lblNewLabel_25_2.setForeground(SystemColor.controlShadow);
			lblNewLabel_25_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_25_3 = new JLabel("(*)");
			lblNewLabel_25_3.setForeground(SystemColor.controlShadow);
			lblNewLabel_25_3.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_25_4 = new JLabel("(*)");
			lblNewLabel_25_4.setForeground(SystemColor.controlShadow);
			lblNewLabel_25_4.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_25_5 = new JLabel("(*)");
			lblNewLabel_25_5.setForeground(SystemColor.controlShadow);
			lblNewLabel_25_5.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnShowRentalInforInText = new JButton("Cập nhập");
			btnShowRentalInforInText.setToolTipText("Nhập ID rồi ấn cập nhập để thêm thêm text tự động vào form");
			btnShowRentalInforInText.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnShowRentalInforInText.setBackground(Color.WHITE);
			btnShowRentalInforInText.addActionListener(this);

			btnRefreshRentalInfor = new JButton("Làm mới");
			btnRefreshRentalInfor.addActionListener(this);
			btnRefreshRentalInfor.setToolTipText("Làm mới form, các text field sẽ trống");
			btnRefreshRentalInfor.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnRefreshRentalInfor.setBackground(Color.WHITE);

			btnShowInforOverdue = new JButton("Hiển thị thông tin cần trả sách");
			btnShowInforOverdue.addActionListener(this);
			btnShowInforOverdue.setToolTipText("Hiển thị thông tin liên quan đến đối tượng cần trả sách");
			btnShowInforOverdue.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowInforOverdue.setBackground(Color.LIGHT_GRAY);

			txtRentalInforNote = new JTextA("");
			txtRentalInforNote.setFont(new Font("Times New Roman", Font.ITALIC, 14));
			txtRentalInforNote.setLineWrap(true);
			txtRentalInforNote.setBorder(new LineBorder(Color.LIGHT_GRAY));
			txtRentalInforNote.setBackground(Color.WHITE);

			gl_pnlRentalInfor = new GroupLayout(pnlRentalInfor);
			gl_pnlRentalInfor.setHorizontalGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlRentalInfor.createSequentialGroup().addContainerGap().addGroup(gl_pnlRentalInfor
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlRentalInfor.createSequentialGroup().addGroup(gl_pnlRentalInfor
									.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlRentalInfor.createSequentialGroup()
											.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
													.addComponent(lblNewLabel_19, GroupLayout.DEFAULT_SIZE, 115,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_20, GroupLayout.DEFAULT_SIZE, 115,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_21, GroupLayout.DEFAULT_SIZE, 115,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_22, GroupLayout.DEFAULT_SIZE, 115,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_23, GroupLayout.DEFAULT_SIZE, 115,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_24, GroupLayout.DEFAULT_SIZE, 115,
															Short.MAX_VALUE))
											.addGap(18))
									.addGroup(gl_pnlRentalInfor.createSequentialGroup()
											.addComponent(lblNewLabel_18, GroupLayout.DEFAULT_SIZE, 129,
													Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
											.addComponent(txtRentalInforBookReturnDate, 424, 439, Short.MAX_VALUE)
											.addComponent(txtRentalInforBookBorrowDate, 424, 439, Short.MAX_VALUE)
											.addComponent(txtRentalInforLibStaffID, 424, 439, Short.MAX_VALUE)
											.addComponent(txtRentalInforBookID, 424, 439, Short.MAX_VALUE)
											.addComponent(txtRentalInforReaderID, 424, 439, Short.MAX_VALUE)
											.addComponent(txtRentalInforNote, GroupLayout.DEFAULT_SIZE, 439,
													Short.MAX_VALUE)
											.addComponent(txtRentalInforID, Alignment.TRAILING,
													GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel_25_2, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_3, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_4, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_5, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlRentalInfor.createSequentialGroup()
													.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
															.addComponent(lblNewLabel_25_1, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_25))
													.addGap(10)
													.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
															.addComponent(btnShowRentalInforInText,
																	GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
															.addComponent(btnRefreshRentalInfor,
																	GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))))
									.addPreferredGap(ComponentPlacement.RELATED))
							.addGroup(
									gl_pnlRentalInfor.createSequentialGroup()
											.addComponent(btnShowRentalInforByReaderID, GroupLayout.DEFAULT_SIZE, 239,
													Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
													.addComponent(btnShowInforOverdue, Alignment.TRAILING,
															GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
													.addComponent(btnShowRentalInforByBookID, GroupLayout.DEFAULT_SIZE,
															237, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnShowRentalInforByLibStaffID, GroupLayout.DEFAULT_SIZE, 238,
													Short.MAX_VALUE)
											.addGap(5)))
							.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_pnlRentalInfor.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED).addComponent(
													btnClearRentalInfor, GroupLayout.DEFAULT_SIZE, 249,
													Short.MAX_VALUE))
									.addComponent(btnRentalInforOverdue, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
									.addComponent(btnAddRentalInfor, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
									.addComponent(btnShowAllRentalInfor, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
									.addComponent(btnDeleteRentalInfor, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
									.addComponent(btnUpdateRentalInfor, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
									.addGroup(gl_pnlRentalInfor.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnShowRentalInforByID, GroupLayout.DEFAULT_SIZE, 249,
													Short.MAX_VALUE)))
							.addContainerGap()));
			gl_pnlRentalInfor.setVerticalGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlRentalInfor.createSequentialGroup().addGap(11).addGroup(gl_pnlRentalInfor
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlRentalInfor.createSequentialGroup().addGroup(gl_pnlRentalInfor
									.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnShowRentalInforInText, GroupLayout.DEFAULT_SIZE, 31,
											Short.MAX_VALUE)
									.addComponent(txtRentalInforID, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
									.addComponent(lblNewLabel_18, GroupLayout.PREFERRED_SIZE, 26,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_25)).addGap(11)
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_19, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(txtRentalInforReaderID, GroupLayout.PREFERRED_SIZE, 27,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_1, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(btnRefreshRentalInfor, GroupLayout.PREFERRED_SIZE, 29,
													GroupLayout.PREFERRED_SIZE))
									.addGap(17)
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_20, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(txtRentalInforBookID, GroupLayout.PREFERRED_SIZE, 27,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_2, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addGap(17)
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_21, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(txtRentalInforLibStaffID, GroupLayout.PREFERRED_SIZE, 27,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_3, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addGap(17)
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_22, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(txtRentalInforBookBorrowDate, GroupLayout.PREFERRED_SIZE, 27,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_4, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addGap(17)
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_23, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(txtRentalInforBookReturnDate, GroupLayout.PREFERRED_SIZE, 27,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_25_5, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addGap(17)
									.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel_24, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(txtRentalInforNote, GroupLayout.PREFERRED_SIZE, 93,
													GroupLayout.PREFERRED_SIZE))
									.addGap(7))
							.addGroup(gl_pnlRentalInfor.createSequentialGroup()
									.addComponent(btnAddRentalInfor, GroupLayout.PREFERRED_SIZE, 45,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnUpdateRentalInfor, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnDeleteRentalInfor, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnShowAllRentalInfor, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnShowRentalInforByID, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
									.addGap(101)))
							.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
							.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(btnClearRentalInfor, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnShowInforOverdue, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlRentalInfor.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(btnShowRentalInforByBookID, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnShowRentalInforByReaderID, GroupLayout.DEFAULT_SIZE, 51,
											Short.MAX_VALUE)
									.addComponent(btnShowRentalInforByLibStaffID, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnRentalInforOverdue, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addContainerGap()));
			pnlRentalInfor.setLayout(gl_pnlRentalInfor);
		}
		if (ID.substring(0, 2).equals("NV") || ID.equals("ADMIN")) {
			pnlReader = new JPanel();
			pnlReader.setForeground(SystemColor.controlShadow);
			pnlReader.setToolTipText("Quản lý người đọc");
			pnlReader.setFont(new Font("Times New Roman", Font.BOLD, 16));
			pnlReader.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnlReader.setBackground(Color.WHITE);
			tabbedPane.addTab("QUẢN LÝ NGƯỜI ĐỌC", null, pnlReader, "Quản lý người đọc");
			tabbedPane.setBackgroundAt(idTab++, Color.WHITE);
			btnUpdateReader = new JButton("Chỉnh sửa người đọc");
			btnUpdateReader.addActionListener(this);
			btnUpdateReader.setToolTipText("Điền thông cần cần sửa vào bảng (Lưu ý chỉnh sửa theo ID)");
			btnUpdateReader.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnUpdateReader.setBackground(Color.LIGHT_GRAY);

			btnDeleteReader = new JButton("Xóa người đọc");
			btnDeleteReader.addActionListener(this);
			btnDeleteReader.setToolTipText("Điền ID cần xóa vào bảng");
			btnDeleteReader.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnDeleteReader.setBackground(Color.LIGHT_GRAY);

			btnShowAllReader = new JButton("Hiển thị tất cả người đọc");
			btnShowAllReader.addActionListener(this);
			btnShowAllReader.setToolTipText("Hiển thị tất cả người đọc");
			btnShowAllReader.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowAllReader.setBackground(Color.LIGHT_GRAY);

			btnShowReaderByID = new JButton("Hiển thị người đọc bằng ID");
			btnShowReaderByID.addActionListener(this);
			btnShowReaderByID.setToolTipText("Hiển thị thông tin người đọc thông qua ID");
			btnShowReaderByID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowReaderByID.setBackground(Color.LIGHT_GRAY);

			btnShowReaderByName = new JButton("Hiển thị người đọc bằng tên");
			btnShowReaderByName.addActionListener(this);
			btnShowReaderByName.setToolTipText("Hiển thị thông tin người đọc thông qua tên");
			btnShowReaderByName.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowReaderByName.setBackground(Color.LIGHT_GRAY);

			btnClearReader = new JButton("Xóa tất cả người đọc");
			btnClearReader.setForeground(Color.WHITE);
			btnClearReader.addActionListener(this);
			btnClearReader.setToolTipText("Xóa tất cả người đọc (Cẩn thận với thao tác này)");
			btnClearReader.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnClearReader.setBackground(Color.DARK_GRAY);

			txtReaderID = new JText("");
			txtReaderID.setColumns(10);
			txtReaderID.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel = new JLabel("ID:");
			lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1 = new JLabel("Họ và tên:");
			lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderFullName = new JText("");
			txtReaderFullName.setColumns(10);
			txtReaderFullName.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderGender = new JText("");
			txtReaderGender.setColumns(10);
			txtReaderGender.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderDob = new JText("");
			txtReaderDob.setColumns(10);
			txtReaderDob.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderAddress = new JText("");
			txtReaderAddress.setColumns(10);
			txtReaderAddress.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderLicense = new JText("");
			txtReaderLicense.setColumns(10);
			txtReaderLicense.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderPhoneNumber = new JText("");
			txtReaderPhoneNumber.setColumns(10);
			txtReaderPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderGmail = new JText("");
			txtReaderGmail.setColumns(10);
			txtReaderGmail.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderKindOfReader = new JText("");
			txtReaderKindOfReader.setColumns(10);
			txtReaderKindOfReader.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderStartDate = new JText("");
			txtReaderStartDate.setColumns(10);
			txtReaderStartDate.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderEndDate = new JText("");
			txtReaderEndDate.setColumns(10);
			txtReaderEndDate.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_1_1 = new JLabel("Giới tính:");
			lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_2 = new JLabel("Ngày sinh:");
			lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_3 = new JLabel("Địa chỉ:");
			lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_4 = new JLabel("CMND/CCCD:");
			lblNewLabel_1_4.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_5 = new JLabel("Số điện thoại:");
			lblNewLabel_1_5.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_6 = new JLabel("Gmail:");
			lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_7 = new JLabel("Đối tượng:");
			lblNewLabel_1_7.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_8 = new JLabel("Ngày đăng ký:");
			lblNewLabel_1_8.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_9 = new JLabel("Hạn đăng ký:");
			lblNewLabel_1_9.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnAddReader = new JButton("Thêm người đọc");
			btnAddReader.addActionListener(this);
			btnAddReader.setToolTipText("Điền tất cả thông tin để thêm");
			btnAddReader.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnAddReader.setBackground(Color.LIGHT_GRAY);

			btnShowReaderOverdue = new JButton("Hiển thị những người quá hạn");
			btnShowReaderOverdue.addActionListener(this);
			btnShowReaderOverdue.setToolTipText("Hiển thị thông tin người đã quá hạn đăng ký");
			btnShowReaderOverdue.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowReaderOverdue.setBackground(Color.LIGHT_GRAY);

			lblNewLabel_26 = new JLabel("(*)");
			lblNewLabel_26.setForeground(SystemColor.controlShadow);
			lblNewLabel_26.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_27 = new JLabel("(*)");
			lblNewLabel_27.setForeground(SystemColor.controlShadow);
			lblNewLabel_27.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_28 = new JLabel("(*)");
			lblNewLabel_28.setForeground(SystemColor.controlShadow);
			lblNewLabel_28.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_29 = new JLabel("(*)");
			lblNewLabel_29.setForeground(SystemColor.controlShadow);
			lblNewLabel_29.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_30 = new JLabel("(*)");
			lblNewLabel_30.setForeground(SystemColor.controlShadow);
			lblNewLabel_30.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_31 = new JLabel("(*)");
			lblNewLabel_31.setForeground(SystemColor.controlShadow);
			lblNewLabel_31.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_32 = new JLabel("(*)");
			lblNewLabel_32.setForeground(SystemColor.controlShadow);
			lblNewLabel_32.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_33 = new JLabel("(*)");
			lblNewLabel_33.setForeground(SystemColor.controlShadow);
			lblNewLabel_33.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnShowReaderInText = new JButton("Cập nhập");
			btnShowReaderInText.setToolTipText("Nhập ID rồi ấn cập nhập để thêm thêm text tự động vào form");
			btnShowReaderInText.addActionListener(this);
			btnShowReaderInText.setBackground(Color.WHITE);
			btnShowReaderInText.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnRefreshReader = new JButton("Làm mới");
			btnRefreshReader.addActionListener(this);
			btnRefreshReader.setToolTipText("Làm mới form, các text field sẽ trống");
			btnRefreshReader.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnRefreshReader.setBackground(Color.WHITE);
			gl_pnlReader = new GroupLayout(pnlReader);
			gl_pnlReader.setHorizontalGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlReader
					.createSequentialGroup()
					.addGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlReader
							.createSequentialGroup().addGap(27)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlReader
									.createSequentialGroup()
									.addGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
											.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
									.addGap(10))
									.addGroup(gl_pnlReader.createSequentialGroup()
											.addGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING)
													.addComponent(lblNewLabel_1_9, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_8, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_7, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_6, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_5, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_4, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_3, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_2, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, 179,
															Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.LEADING)
									.addComponent(txtReaderFullName, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderID, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderDob, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderAddress, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderLicense, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderPhoneNumber, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderGmail, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderKindOfReader, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderStartDate, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtReaderGender, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 399,
											Short.MAX_VALUE)
									.addComponent(txtReaderEndDate, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_26, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_27, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_28, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_29, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_30, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_31, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_32, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_33, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE))
							.addGap(82))
							.addGroup(gl_pnlReader.createSequentialGroup().addGap(460)
									.addComponent(btnRefreshReader, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(btnShowReaderInText, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnAddReader, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
							.addComponent(btnUpdateReader, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
							.addComponent(btnShowReaderByName, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
							.addComponent(btnShowReaderByID, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 272,
									Short.MAX_VALUE)
							.addComponent(btnShowAllReader, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 272,
									Short.MAX_VALUE)
							.addComponent(btnDeleteReader, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 272,
									Short.MAX_VALUE)
							.addComponent(btnClearReader, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
							.addComponent(btnShowReaderOverdue, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
					.addContainerGap()));
			gl_pnlReader.setVerticalGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlReader
					.createSequentialGroup()
					.addGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlReader
							.createSequentialGroup().addGap(9)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnShowReaderInText, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnRefreshReader, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
							.addGap(17)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
									.addComponent(txtReaderID, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
									.addComponent(lblNewLabel_26, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderFullName, GroupLayout.PREFERRED_SIZE, 27,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
									.addComponent(lblNewLabel_27, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderGender, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
									.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_28, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderDob, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
									.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_29, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderAddress, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
									.addComponent(lblNewLabel_1_3, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_30, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderLicense, GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1_4, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderPhoneNumber, GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1_5, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_31, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderGmail, GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1_6, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderKindOfReader, GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1_7, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtReaderStartDate, GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1_8, GroupLayout.PREFERRED_SIZE, 21,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_32, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)))
							.addGroup(
									gl_pnlReader.createSequentialGroup().addGap(33)
											.addComponent(btnAddReader, GroupLayout.PREFERRED_SIZE, 51,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnUpdateReader, GroupLayout.PREFERRED_SIZE, 47,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnDeleteReader, GroupLayout.PREFERRED_SIZE, 46,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnShowAllReader, GroupLayout.PREFERRED_SIZE, 46,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnShowReaderByID, GroupLayout.PREFERRED_SIZE, 47,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnShowReaderByName, GroupLayout.PREFERRED_SIZE, 48,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnShowReaderOverdue, GroupLayout.PREFERRED_SIZE, 48,
													GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_pnlReader.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlReader
							.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlReader.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_pnlReader.createSequentialGroup()
											.addComponent(lblNewLabel_33, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addGap(33))
									.addGroup(gl_pnlReader.createSequentialGroup()
											.addGroup(gl_pnlReader.createParallelGroup(Alignment.BASELINE)
													.addComponent(btnClearReader, GroupLayout.PREFERRED_SIZE, 47,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(txtReaderEndDate, GroupLayout.DEFAULT_SIZE, 28,
															Short.MAX_VALUE))
											.addContainerGap())))
							.addGroup(gl_pnlReader
									.createSequentialGroup().addGap(15).addComponent(lblNewLabel_1_9,
											GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addContainerGap()))));
			pnlReader.setLayout(gl_pnlReader);
		}
		if (ID.substring(0, 2).equals("TT") || ID.equals("ADMIN"))

		{
			pnlBook = new JPanel();
			pnlBook.setToolTipText("Quản lý sách");
			pnlBook.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnlBook.setBackground(Color.WHITE);
			pnlBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
			tabbedPane.addTab("QUẢN LÝ SÁCH", null, pnlBook, "Quản lý sách có trong thư viện");
			tabbedPane.setBackgroundAt(idTab++, Color.WHITE);

			btnAddBook = new JButton("Thêm sách");
			btnAddBook.addActionListener(this);
			btnAddBook.setToolTipText("Điền tất cả các thông tin để thêm sách");
			btnAddBook.setBackground(Color.LIGHT_GRAY);
			btnAddBook.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnUpdateBook = new JButton("Sửa thông tin sách");
			btnUpdateBook.addActionListener(this);
			btnUpdateBook.setToolTipText("Cập nhập sách thông qua ID");
			btnUpdateBook.setBackground(Color.LIGHT_GRAY);
			btnUpdateBook.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnShowAllBook = new JButton("Hiển thị tất cả sách");
			btnShowAllBook.addActionListener(this);
			btnShowAllBook.setToolTipText("Hiển thị tất cả sách");
			btnShowAllBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowAllBook.setBackground(Color.LIGHT_GRAY);

			btnDeleteBook = new JButton("Xóa sách");
			btnDeleteBook.addActionListener(this);
			btnDeleteBook.setToolTipText("Xóa sách thông qua ID");
			btnDeleteBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnDeleteBook.setBackground(Color.LIGHT_GRAY);

			btnShowBookByID = new JButton("Hiển thị sách bằng ID");
			btnShowBookByID.addActionListener(this);
			btnShowBookByID.setToolTipText("Hiển thị sách thông qua ID");
			btnShowBookByID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowBookByID.setBackground(Color.LIGHT_GRAY);

			btnShowBookByName = new JButton("Hiển thị sách bằng tên");
			btnShowBookByName.addActionListener(this);
			btnShowBookByName.setToolTipText("Hiển thị sách thông qua tên sách");
			btnShowBookByName.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowBookByName.setBackground(Color.LIGHT_GRAY);

			btnClearBook = new JButton("Xóa tất cả sách");
			btnClearBook.setForeground(Color.WHITE);
			btnClearBook.addActionListener(this);
			btnClearBook.setToolTipText("Xóa tất cả sách (Cẩn thận với thao tác này)");
			btnClearBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnClearBook.setBackground(Color.DARK_GRAY);

			txtBookID = new JText("");
			txtBookID.setColumns(10);
			txtBookName = new JText("");
			txtBookName.setColumns(10);
			txtBookName.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtBookKindOfBook = new JText("");
			txtBookKindOfBook.setColumns(10);
			txtBookKindOfBook.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtBookAuthor = new JText("");
			txtBookAuthor.setColumns(10);
			txtBookAuthor.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtBookQuantity = new JText("");
			txtBookQuantity.setColumns(10);
			txtBookQuantity.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_2 = new JLabel("ID:");
			lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_3 = new JLabel("Tên sách:");
			lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_4 = new JLabel("Tác giả:");
			lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_5 = new JLabel("Số lượng:");
			lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_6 = new JLabel("Loại sách:");
			lblNewLabel_6.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_34 = new JLabel("(*)");
			lblNewLabel_34.setForeground(SystemColor.controlShadow);
			lblNewLabel_34.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_35 = new JLabel("(*)");
			lblNewLabel_35.setForeground(SystemColor.controlShadow);
			lblNewLabel_35.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_36 = new JLabel("(*)");
			lblNewLabel_36.setForeground(SystemColor.controlShadow);
			lblNewLabel_36.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_37 = new JLabel("(*)");
			lblNewLabel_37.setForeground(SystemColor.controlShadow);
			lblNewLabel_37.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_38 = new JLabel("(*)");
			lblNewLabel_38.setForeground(SystemColor.controlShadow);
			lblNewLabel_38.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnShowBookInText = new JButton("Cập nhập");
			btnShowBookInText.setToolTipText("Nhập ID rồi ấn cập nhập để thêm thêm text tự động vào form");
			btnShowBookInText.addActionListener(this);
			btnShowBookInText.setBackground(Color.WHITE);
			btnShowBookInText.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnRefreshBook = new JButton("Làm mới");
			btnRefreshBook.addActionListener(this);
			btnRefreshBook.setToolTipText("Làm mới form, các text field sẽ trống");
			btnRefreshBook.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnRefreshBook.setBackground(Color.WHITE);

			gl_pnlBook = new GroupLayout(pnlBook);
			gl_pnlBook.setHorizontalGroup(gl_pnlBook.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlBook.createSequentialGroup().addGap(30)
							.addGroup(gl_pnlBook.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
									.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
									.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
									.addComponent(lblNewLabel_5, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
									.addComponent(lblNewLabel_6, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlBook.createParallelGroup(Alignment.LEADING)
									.addComponent(txtBookID, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtBookAuthor, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtBookName, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtBookQuantity, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
									.addComponent(txtBookKindOfBook, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
							.addGap(4)
							.addGroup(gl_pnlBook.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_35, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_36, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(
											lblNewLabel_37, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_38)
									.addGroup(gl_pnlBook.createSequentialGroup()
											.addComponent(lblNewLabel_34, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlBook
													.createParallelGroup(Alignment.LEADING)
													.addComponent(btnShowBookInText, GroupLayout.DEFAULT_SIZE, 118,
															Short.MAX_VALUE)
													.addComponent(
															btnRefreshBook, GroupLayout.DEFAULT_SIZE, 118,
															Short.MAX_VALUE))))
							.addGap(18)
							.addGroup(gl_pnlBook.createParallelGroup(Alignment.TRAILING)
									.addComponent(btnAddBook, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217,
											Short.MAX_VALUE)
									.addComponent(btnShowBookByID, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
									.addComponent(btnShowAllBook, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
									.addComponent(btnDeleteBook, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
									.addComponent(btnUpdateBook, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
									.addComponent(btnClearBook, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217,
											Short.MAX_VALUE)
									.addComponent(btnShowBookByName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217,
											Short.MAX_VALUE))
							.addContainerGap()));
			gl_pnlBook.setVerticalGroup(gl_pnlBook.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlBook
					.createSequentialGroup().addGap(26)
					.addGroup(gl_pnlBook.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlBook
							.createSequentialGroup()
							.addGroup(gl_pnlBook.createParallelGroup(Alignment.BASELINE, false)
									.addComponent(btnAddBook, GroupLayout.PREFERRED_SIZE, 43,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(txtBookID, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_34, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(btnShowBookInText, GroupLayout.PREFERRED_SIZE, 34,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlBook.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnUpdateBook, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
									.addComponent(btnRefreshBook, GroupLayout.PREFERRED_SIZE, 34,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDeleteBook, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(15))
							.addGroup(gl_pnlBook.createSequentialGroup()
									.addGroup(gl_pnlBook.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtBookName, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 21,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_35, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addGap(46)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlBook.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_pnlBook.createSequentialGroup()
									.addGroup(gl_pnlBook.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtBookAuthor, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 21,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_36, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addGap(31))
							.addGroup(gl_pnlBook.createSequentialGroup()
									.addComponent(btnShowAllBook, GroupLayout.PREFERRED_SIZE, 51,
											GroupLayout.PREFERRED_SIZE)
									.addGap(13)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlBook.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlBook
							.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtBookQuantity, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_37, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlBook.createSequentialGroup()
									.addComponent(btnShowBookByID, GroupLayout.PREFERRED_SIZE, 46,
											GroupLayout.PREFERRED_SIZE)
									.addGap(8)))
					.addGroup(gl_pnlBook.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlBook.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnShowBookByName, GroupLayout.PREFERRED_SIZE, 48,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE).addComponent(
											btnClearBook, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlBook.createSequentialGroup().addGap(48)
									.addGroup(gl_pnlBook.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtBookKindOfBook, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 21,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_38, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))))
					.addContainerGap()));
			pnlBook.setLayout(gl_pnlBook);
		}
		if (ID.substring(0, 2).equals("TQ") || ID.equals("ADMIN")) {
			pnlLibStaff = new JPanel();
			pnlLibStaff.setForeground(SystemColor.controlShadow);
			pnlLibStaff.setToolTipText("Quản lý kiểm kê");
			pnlLibStaff.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnlLibStaff.setBackground(Color.WHITE);
			pnlLibStaff.setFont(new Font("Times New Roman", Font.BOLD, 14));
			tabbedPane.addTab("QUẢN LÝ KIỂM KÊ", null, pnlLibStaff, "Quản lý kiểm kê");
			tabbedPane.setBackgroundAt(idTab++, Color.WHITE);

			btnAddLibStaff = new JButton("Thêm nhân viên");
			btnAddLibStaff.addActionListener(this);
			btnAddLibStaff.setToolTipText("Nhập tất cả dữ liệu để thêm nhân viên");
			btnAddLibStaff.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnAddLibStaff.setBackground(Color.LIGHT_GRAY);

			btnUpdateStaff = new JButton("Chỉnh sửa nhân viên");
			btnUpdateStaff.addActionListener(this);
			btnUpdateStaff.setToolTipText("Chỉnh sửa nhân viên thông qua ID");
			btnUpdateStaff.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnUpdateStaff.setBackground(Color.LIGHT_GRAY);

			btnDeleteStaff = new JButton("Xóa nhân viên");
			btnDeleteStaff.addActionListener(this);
			btnDeleteStaff.setToolTipText("Xóa nhân viên thông qua ID");
			btnDeleteStaff.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnDeleteStaff.setBackground(Color.LIGHT_GRAY);

			btnShowAllStaff = new JButton("Hiển thị tất cả nhân viên");
			btnShowAllStaff.addActionListener(this);
			btnShowAllStaff.setToolTipText("Hiển thị tất cả nhân viên");
			btnShowAllStaff.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowAllStaff.setBackground(Color.LIGHT_GRAY);

			btnShowStaffByID = new JButton("Hiển thị nhân viên bằng ID");
			btnShowStaffByID.addActionListener(this);
			btnShowStaffByID.setToolTipText("Hiển thị nhân viên bằng ID");
			btnShowStaffByID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowStaffByID.setBackground(Color.LIGHT_GRAY);

			btnShowStaffByName = new JButton("Hiển thị nhân viên bằng tên");
			btnShowStaffByName.addActionListener(this);
			btnShowStaffByName.setToolTipText("Hiển thị nhân viên bằng tên");
			btnShowStaffByName.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowStaffByName.setBackground(Color.LIGHT_GRAY);

			btnClearStaff = new JButton("Xóa tất cả nhân viên");
			btnClearStaff.setForeground(Color.WHITE);
			btnClearStaff.addActionListener(this);
			btnClearStaff.setToolTipText("Xóa tất cả nhân viên (Cẩn thận cho thao tác này)");
			btnClearStaff.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnClearStaff.setBackground(Color.DARK_GRAY);

			txtLibStaffID = new JText("");
			txtLibStaffID.setColumns(10);
			txtLibStaffID.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffFullName = new JText("");
			txtLibStaffFullName.setColumns(10);
			txtLibStaffFullName.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffGender = new JText("");
			txtLibStaffGender.setColumns(10);
			txtLibStaffGender.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffDob = new JText("");
			txtLibStaffDob.setColumns(10);
			txtLibStaffDob.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffAddress = new JText("");
			txtLibStaffAddress.setColumns(10);
			txtLibStaffAddress.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffLicense = new JText("");
			txtLibStaffLicense.setColumns(10);
			txtLibStaffLicense.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffPhoneNumber = new JText("");
			txtLibStaffPhoneNumber.setColumns(10);
			txtLibStaffPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffGmail = new JText("");
			txtLibStaffGmail.setColumns(10);
			txtLibStaffGmail.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffStartWorkDate = new JText("");
			txtLibStaffStartWorkDate.setColumns(10);
			txtLibStaffStartWorkDate.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffPosition = new JText("");
			txtLibStaffPosition.setColumns(10);
			txtLibStaffPosition.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffBasicSalary = new JText("");
			txtLibStaffBasicSalary.setColumns(10);
			txtLibStaffBasicSalary.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_7 = new JLabel("ID:");
			lblNewLabel_7.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_8 = new JLabel("Họ và tên:");
			lblNewLabel_8.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_9 = new JLabel("Giới tính:");
			lblNewLabel_9.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_10 = new JLabel("Ngày sinh:");
			lblNewLabel_10.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_11 = new JLabel("Địa chỉ:");
			lblNewLabel_11.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_12 = new JLabel("CMND/CCCD");
			lblNewLabel_12.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_13 = new JLabel("Số điện thoại:");
			lblNewLabel_13.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_14 = new JLabel("Gmail:");
			lblNewLabel_14.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_15 = new JLabel("Ngày bắt đầu làm việc:");
			lblNewLabel_15.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_16 = new JLabel("Chức vụ:");
			lblNewLabel_16.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_17 = new JLabel("Lương cơ bản:");
			lblNewLabel_17.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffSalaryBonus = new JText("");
			txtLibStaffSalaryBonus.setColumns(10);
			txtLibStaffSalaryBonus.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtLibStaffPenalty = new JText("");
			txtLibStaffPenalty.setColumns(10);
			txtLibStaffPenalty.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_17_1 = new JLabel("Lương thưởng:");
			lblNewLabel_17_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_17_2 = new JLabel("Tiền phạt");
			lblNewLabel_17_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_39 = new JLabel("(*)");
			lblNewLabel_39.setBackground(Color.WHITE);
			lblNewLabel_39.setForeground(SystemColor.controlShadow);
			lblNewLabel_39.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_40 = new JLabel("(*)");
			lblNewLabel_40.setBackground(Color.WHITE);
			lblNewLabel_40.setForeground(SystemColor.controlShadow);
			lblNewLabel_40.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_41 = new JLabel("(*)");
			lblNewLabel_41.setBackground(Color.WHITE);
			lblNewLabel_41.setForeground(SystemColor.controlShadow);
			lblNewLabel_41.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_42 = new JLabel("(*)");
			lblNewLabel_42.setBackground(Color.WHITE);
			lblNewLabel_42.setForeground(SystemColor.controlShadow);
			lblNewLabel_42.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_43 = new JLabel("(*)");
			lblNewLabel_43.setBackground(Color.WHITE);
			lblNewLabel_43.setForeground(SystemColor.controlShadow);
			lblNewLabel_43.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_44 = new JLabel("(*)");
			lblNewLabel_44.setBackground(Color.WHITE);
			lblNewLabel_44.setForeground(SystemColor.controlShadow);
			lblNewLabel_44.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_45 = new JLabel("(*)");
			lblNewLabel_45.setBackground(Color.WHITE);
			lblNewLabel_45.setForeground(SystemColor.controlShadow);
			lblNewLabel_45.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_46 = new JLabel("(*)");
			lblNewLabel_46.setBackground(Color.WHITE);
			lblNewLabel_46.setForeground(SystemColor.controlShadow);
			lblNewLabel_46.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_47 = new JLabel("(*)");
			lblNewLabel_47.setBackground(Color.WHITE);
			lblNewLabel_47.setForeground(SystemColor.controlShadow);
			lblNewLabel_47.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnShowLibStaffInText = new JButton("Cập nhập");
			btnShowLibStaffInText.setToolTipText("Nhập ID rồi ấn cập nhập để thêm thêm text tự động vào form");
			btnShowLibStaffInText.addActionListener(this);
			btnShowLibStaffInText.setFont(new Font("Times New Roman", Font.BOLD, 14));
			btnShowLibStaffInText.setBackground(Color.WHITE);

			btnRefreshLibStaff = new JButton("Làm mới");
			btnRefreshLibStaff.addActionListener(this);
			btnRefreshLibStaff.setToolTipText("Làm mới form, các text field sẽ trống");
			btnRefreshLibStaff.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnRefreshLibStaff.setBackground(Color.WHITE);
			gl_pnlLibStaff = new GroupLayout(pnlLibStaff);
			gl_pnlLibStaff.setHorizontalGroup(gl_pnlLibStaff.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(33)
							.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewLabel_8, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_7, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_10, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_11, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_12, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_13, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_14, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_15, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_16, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_17, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_9, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_17_1, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
									.addComponent(lblNewLabel_17_2, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlLibStaff
									.createSequentialGroup()
									.addComponent(txtLibStaffID, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_39, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnShowLibStaffInText, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnAddLibStaff, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
									.addGroup(gl_pnlLibStaff.createSequentialGroup()
											.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.TRAILING)
													.addComponent(txtLibStaffPhoneNumber, GroupLayout.DEFAULT_SIZE, 394,
															Short.MAX_VALUE)
													.addComponent(txtLibStaffDob, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffAddress, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffLicense, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffGmail, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffStartWorkDate, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffPosition, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffBasicSalary, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffSalaryBonus, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffPenalty, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffFullName, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
													.addComponent(txtLibStaffGender, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.LEADING).addGroup(
													gl_pnlLibStaff.createSequentialGroup().addGroup(gl_pnlLibStaff
															.createParallelGroup(Alignment.LEADING)
															.addComponent(lblNewLabel_40, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_41, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_42, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_43, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_44, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_46, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_47, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE))
															.addPreferredGap(ComponentPlacement.RELATED)
															.addComponent(btnRefreshLibStaff, GroupLayout.DEFAULT_SIZE,
																	106, Short.MAX_VALUE))
													.addComponent(lblNewLabel_45, GroupLayout.PREFERRED_SIZE, 17,
															GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.TRAILING)
													.addComponent(btnClearStaff, GroupLayout.DEFAULT_SIZE, 227,
															Short.MAX_VALUE)
													.addComponent(btnShowStaffByName, GroupLayout.DEFAULT_SIZE, 227,
															Short.MAX_VALUE)
													.addComponent(btnShowStaffByID, GroupLayout.DEFAULT_SIZE, 227,
															Short.MAX_VALUE)
													.addComponent(btnShowAllStaff, GroupLayout.DEFAULT_SIZE, 227,
															Short.MAX_VALUE)
													.addComponent(btnDeleteStaff, GroupLayout.DEFAULT_SIZE, 227,
															Short.MAX_VALUE)
													.addComponent(btnUpdateStaff, Alignment.LEADING,
															GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))))
							.addContainerGap()));
			gl_pnlLibStaff.setVerticalGroup(gl_pnlLibStaff.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(0)
							.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(btnAddLibStaff, GroupLayout.PREFERRED_SIZE, 49,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(btnShowLibStaffInText))
									.addGroup(Alignment.LEADING, gl_pnlLibStaff.createSequentialGroup().addGap(31)
											.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.LEADING)
													.addComponent(lblNewLabel_7, Alignment.TRAILING,
															GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
													.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
															.addComponent(txtLibStaffID, GroupLayout.DEFAULT_SIZE, 31,
																	Short.MAX_VALUE)
															.addComponent(lblNewLabel_39, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlLibStaff
									.createSequentialGroup().addGap(6)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(1).addComponent(
													txtLibStaffFullName, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
											.addComponent(lblNewLabel_40, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(btnRefreshLibStaff, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_8, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffGender, GroupLayout.DEFAULT_SIZE, 28,
													Short.MAX_VALUE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_9, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
											.addComponent(lblNewLabel_41, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffDob, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_10, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
											.addComponent(lblNewLabel_42, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffAddress, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_11, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
											.addComponent(lblNewLabel_43, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffLicense, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_12, GroupLayout.PREFERRED_SIZE, 21,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_44, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addGap(9)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblNewLabel_13, GroupLayout.PREFERRED_SIZE, 21,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
													.addComponent(txtLibStaffPhoneNumber, GroupLayout.PREFERRED_SIZE,
															28, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblNewLabel_45, GroupLayout.PREFERRED_SIZE, 17,
															GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffGmail, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_14, GroupLayout.PREFERRED_SIZE, 21,
													GroupLayout.PREFERRED_SIZE)))
									.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(11)
											.addComponent(btnUpdateStaff, GroupLayout.PREFERRED_SIZE, 49,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnDeleteStaff, GroupLayout.PREFERRED_SIZE, 52,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnShowAllStaff, GroupLayout.PREFERRED_SIZE, 52,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnShowStaffByID, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlLibStaff
									.createSequentialGroup()
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffStartWorkDate, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_15, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
											.addComponent(lblNewLabel_46, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffPosition, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_16, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
											.addComponent(lblNewLabel_47, GroupLayout.PREFERRED_SIZE, 17,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffBasicSalary, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_17, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffSalaryBonus, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_17_1, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlLibStaff.createParallelGroup(Alignment.BASELINE)
											.addComponent(txtLibStaffPenalty, GroupLayout.DEFAULT_SIZE, 28,
													Short.MAX_VALUE)
											.addGroup(gl_pnlLibStaff.createSequentialGroup().addGap(7).addComponent(
													lblNewLabel_17_2, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
									.addGap(10))
									.addGroup(gl_pnlLibStaff.createSequentialGroup()
											.addComponent(btnShowStaffByName, GroupLayout.PREFERRED_SIZE, 54,
													GroupLayout.PREFERRED_SIZE)
											.addGap(68).addComponent(btnClearStaff, GroupLayout.PREFERRED_SIZE, 52,
													GroupLayout.PREFERRED_SIZE)))
							.addGap(21)));
			pnlLibStaff.setLayout(gl_pnlLibStaff);
		}
		// AccountND
		if (ID.substring(0, 2).equals("ND")) {
			pnlAccountND = new JPanel();
			pnlAccountND.setFont(new Font("Times New Roman", Font.BOLD, 14));
			pnlAccountND.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnlAccountND.setBackground(Color.WHITE);

			tabbedPane.addTab("NGƯỜI ĐỌC " + ID, null, pnlAccountND, null);
			tabbedPane.setBackgroundAt(idTab++, Color.WHITE);

			btnNDLogOut = new JButton("Đăng xuất");
			btnNDLogOut.addActionListener(this);
			btnNDLogOut.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnNDLogOut.setBackground(Color.LIGHT_GRAY);

			btnNDUpdateInfor = new JButton("Cập nhập thông tin cá nhân");
			btnNDUpdateInfor.addActionListener(this);
			btnNDUpdateInfor.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnNDUpdateInfor.setBackground(Color.LIGHT_GRAY);

			lblNewLabel_48 = new JLabel("QUẢN LÝ THÔNG TIN CÁ NHÂN");
			lblNewLabel_48.setForeground(new Color(147, 112, 219));
			lblNewLabel_48.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_48.setFont(new Font("Times New Roman", Font.BOLD, 20));

			btnShowInforBorrow = new JButton("Xem thông tin sách mượn");
			btnShowInforBorrow.addActionListener(this);
			btnShowInforBorrow.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnShowInforBorrow.setBackground(Color.LIGHT_GRAY);

			btnNDCheckBook = new JButton("Xem kho sách");
			btnNDCheckBook.addActionListener(this);
			btnNDCheckBook.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnNDCheckBook.setBackground(Color.LIGHT_GRAY);

			btnNDChangePass = new JButton("Đổi mật khẩu");
			btnNDChangePass.addActionListener(this);
			btnNDChangePass.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnNDChangePass.setBackground(Color.LIGHT_GRAY);

			lblNewLabel_52 = new JLabel("ID:");
			lblNewLabel_52.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_10 = new JLabel("Họ và tên:");
			lblNewLabel_1_10.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_1_1 = new JLabel("Giới tính:");
			lblNewLabel_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_2_1 = new JLabel("Ngày sinh:");
			lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_3_1 = new JLabel("Địa chỉ:");
			lblNewLabel_1_3_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_4_1 = new JLabel("CMND/CCCD:");
			lblNewLabel_1_4_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_5_1 = new JLabel("Số điện thoại:");
			lblNewLabel_1_5_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_6_1 = new JLabel("Gmail:");
			lblNewLabel_1_6_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_7_1 = new JLabel("Đối tượng:");
			lblNewLabel_1_7_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_8_1 = new JLabel("Ngày đăng ký:");
			lblNewLabel_1_8_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_1_9_1 = new JLabel("Hạn đăng ký:");
			lblNewLabel_1_9_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderEndDate_1 = new JText("");
			txtReaderEndDate_1.setEnabled(false);
			txtReaderEndDate_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderStartDate_1 = new JText("");
			txtReaderStartDate_1.setEnabled(false);
			txtReaderStartDate_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
			txtReaderKindOfReader_1 = new JText("");
			txtReaderKindOfReader_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderGmail_1 = new JText("");
			txtReaderGmail_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderPhoneNumber_1 = new JText("");
			txtReaderPhoneNumber_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderLicense_1 = new JText("");
			txtReaderLicense_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderAddress_1 = new JText("");
			txtReaderAddress_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderDob_1 = new JText("");
			txtReaderDob_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderGender_1 = new JText("");
			txtReaderGender_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderFullName_1 = new JText("");
			txtReaderFullName_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtReaderID_1 = new JText(ID);
			txtReaderID_1.setEnabled(false);
			txtReaderID_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
			undoReader();

			btnUndoND = new JButton("Hoàn tác");
			btnUndoND.addActionListener(this);
			btnUndoND.setBackground(Color.WHITE);
			btnUndoND.setFont(new Font("Times New Roman", Font.BOLD, 12));

			lblNewLabel_53 = new JLabel("(*)");
			lblNewLabel_53.setForeground(SystemColor.controlShadow);
			lblNewLabel_53.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_54 = new JLabel("(*)");
			lblNewLabel_54.setForeground(SystemColor.controlShadow);
			lblNewLabel_54.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_55 = new JLabel("(*)");
			lblNewLabel_55.setForeground(SystemColor.controlShadow);
			lblNewLabel_55.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_56 = new JLabel("(*)");
			lblNewLabel_56.setForeground(SystemColor.controlShadow);
			lblNewLabel_56.setFont(new Font("Times New Roman", Font.BOLD, 14));
			gl_pnlAccountND = new GroupLayout(pnlAccountND);
			gl_pnlAccountND.setHorizontalGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlAccountND.createSequentialGroup().addGroup(gl_pnlAccountND
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlAccountND.createSequentialGroup().addContainerGap()
									.addComponent(lblNewLabel_48, GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE))
							.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(83).addGroup(gl_pnlAccountND
									.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlAccountND.createSequentialGroup()
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_52, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtReaderID_1,
																	GroupLayout.PREFERRED_SIZE, 399,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_1_10, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtReaderFullName_1,
																	GroupLayout.PREFERRED_SIZE, 399,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtReaderGender_1,
																	GroupLayout.PREFERRED_SIZE, 399,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_1_2_1, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtReaderDob_1,
																	GroupLayout.PREFERRED_SIZE, 399,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_1_3_1, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtReaderAddress_1,
																	GroupLayout.PREFERRED_SIZE, 399,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_1_4_1, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtReaderLicense_1,
																	GroupLayout.PREFERRED_SIZE, 399,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_1_5_1, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtReaderPhoneNumber_1,
																	GroupLayout.PREFERRED_SIZE, 399,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createSequentialGroup()
															.addComponent(lblNewLabel_1_6_1, GroupLayout.PREFERRED_SIZE,
																	179, GroupLayout.PREFERRED_SIZE)
															.addGap(10)
															.addComponent(txtReaderGmail_1, GroupLayout.PREFERRED_SIZE,
																	399, GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addComponent(btnUndoND, GroupLayout.DEFAULT_SIZE, 84,
															Short.MAX_VALUE)
													.addComponent(lblNewLabel_53, GroupLayout.PREFERRED_SIZE, 17,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(lblNewLabel_54, GroupLayout.PREFERRED_SIZE, 17,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(lblNewLabel_55, GroupLayout.PREFERRED_SIZE, 17,
															GroupLayout.PREFERRED_SIZE)
													.addComponent(lblNewLabel_56, GroupLayout.PREFERRED_SIZE, 17,
															GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING, false)
													.addComponent(btnNDLogOut, Alignment.TRAILING,
															GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
															Short.MAX_VALUE)
													.addComponent(btnNDChangePass, Alignment.TRAILING,
															GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
															Short.MAX_VALUE)
													.addComponent(btnNDCheckBook, Alignment.TRAILING,
															GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
															Short.MAX_VALUE)
													.addComponent(btnShowInforBorrow, Alignment.TRAILING,
															GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
															Short.MAX_VALUE)
													.addComponent(btnNDUpdateInfor, Alignment.TRAILING,
															GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)))
									.addGroup(gl_pnlAccountND.createSequentialGroup()
											.addComponent(lblNewLabel_1_7_1, GroupLayout.PREFERRED_SIZE, 179,
													GroupLayout.PREFERRED_SIZE)
											.addGap(10).addComponent(txtReaderKindOfReader_1,
													GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_pnlAccountND.createSequentialGroup()
											.addComponent(lblNewLabel_1_8_1, GroupLayout.PREFERRED_SIZE, 179,
													GroupLayout.PREFERRED_SIZE)
											.addGap(10).addComponent(txtReaderStartDate_1, GroupLayout.PREFERRED_SIZE,
													399, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_pnlAccountND.createSequentialGroup()
											.addComponent(lblNewLabel_1_9_1, GroupLayout.PREFERRED_SIZE, 179,
													GroupLayout.PREFERRED_SIZE)
											.addGap(10).addComponent(txtReaderEndDate_1, GroupLayout.PREFERRED_SIZE,
													399, GroupLayout.PREFERRED_SIZE)))))
							.addContainerGap()));
			gl_pnlAccountND.setVerticalGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlAccountND.createSequentialGroup()
							.addComponent(lblNewLabel_48, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addGap(17)
							.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlAccountND
									.createSequentialGroup()
									.addComponent(btnNDUpdateInfor, GroupLayout.PREFERRED_SIZE, 44,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnShowInforBorrow, GroupLayout.PREFERRED_SIZE, 44,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNDCheckBook, GroupLayout.PREFERRED_SIZE, 44,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNDChangePass, GroupLayout.PREFERRED_SIZE, 44,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 202, Short.MAX_VALUE).addComponent(
											btnNDLogOut, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_pnlAccountND.createSequentialGroup().addGroup(gl_pnlAccountND
											.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel_52, GroupLayout.PREFERRED_SIZE, 28,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(2)
													.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.BASELINE)
															.addComponent(txtReaderID_1, GroupLayout.PREFERRED_SIZE, 27,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(btnUndoND))))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addComponent(lblNewLabel_1_10, GroupLayout.PREFERRED_SIZE, 29,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(2)
															.addComponent(txtReaderFullName_1,
																	GroupLayout.PREFERRED_SIZE, 27,
																	GroupLayout.PREFERRED_SIZE)))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.BASELINE)
															.addComponent(txtReaderGender_1, GroupLayout.PREFERRED_SIZE,
																	28, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_53, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_2_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.BASELINE)
															.addComponent(txtReaderDob_1, GroupLayout.PREFERRED_SIZE,
																	28, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_54, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_3_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.BASELINE)
															.addComponent(txtReaderAddress_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_55, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_4_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addComponent(txtReaderLicense_1, GroupLayout.PREFERRED_SIZE, 28,
															GroupLayout.PREFERRED_SIZE))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_5_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.BASELINE)
															.addComponent(txtReaderPhoneNumber_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_56, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_6_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addComponent(txtReaderGmail_1, GroupLayout.PREFERRED_SIZE, 28,
															GroupLayout.PREFERRED_SIZE))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_7_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addComponent(txtReaderKindOfReader_1, GroupLayout.PREFERRED_SIZE,
															28, GroupLayout.PREFERRED_SIZE))
											.addGap(11)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(3)
															.addComponent(lblNewLabel_1_8_1, GroupLayout.PREFERRED_SIZE,
																	21, GroupLayout.PREFERRED_SIZE))
													.addComponent(txtReaderStartDate_1, GroupLayout.PREFERRED_SIZE, 28,
															GroupLayout.PREFERRED_SIZE))
											.addGap(15)
											.addGroup(gl_pnlAccountND.createParallelGroup(Alignment.LEADING)
													.addComponent(lblNewLabel_1_9_1, GroupLayout.PREFERRED_SIZE, 29,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_pnlAccountND.createSequentialGroup().addGap(1)
															.addComponent(txtReaderEndDate_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE)))))
							.addContainerGap()));
			pnlAccountND.setLayout(gl_pnlAccountND);
		}
		// ////////////////// NV
		if (ID.substring(0, 2).equals("TT") || ID.substring(0, 2).equals("TQ") || ID.substring(0, 2).equals("NV")) {
			pnlAccountQL = new JPanel();
			pnlAccountQL.setFont(new Font("Times New Roman", Font.BOLD, 14));
			pnlAccountQL.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnlAccountQL.setBackground(Color.WHITE);
			tabbedPane.addTab(
					(ID.substring(0, 2).equals("TT") ? "THỦ THƯ "
							: ID.substring(0, 2).equals("TQ") ? "THỦ QUỸ " : "NHÂN VIÊN ") + ID,
					null, pnlAccountQL, null);
			tabbedPane.setBackgroundAt(idTab++, Color.WHITE);

			lblNewLabel_50 = new JLabel("QUẢN LÝ THÔNG TIN");
			lblNewLabel_50.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_50.setForeground(new Color(147, 112, 219));
			lblNewLabel_50.setFont(new Font("Times New Roman", Font.BOLD, 20));

			lblNewLabel_7_2 = new JLabel("ID:");
			lblNewLabel_7_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffID_1 = new JText(ID);
			txtLibStaffID_1.setEnabled(false);
			txtLibStaffID_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_8_2 = new JLabel("Họ và tên:");
			lblNewLabel_8_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffFullName_1 = new JText("");
			txtLibStaffFullName_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_9_2 = new JLabel("Giới tính:");
			lblNewLabel_9_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffGender_1 = new JText("");
			txtLibStaffGender_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_10_2 = new JLabel("Ngày sinh:");
			lblNewLabel_10_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffDob_1 = new JText("");
			txtLibStaffDob_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_11_2 = new JLabel("Địa chỉ:");
			lblNewLabel_11_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffAddress_1 = new JText("");
			txtLibStaffAddress_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_12_2 = new JLabel("CMND/CCCD");
			lblNewLabel_12_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffLicense_1 = new JText("");
			txtLibStaffLicense_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_13_2 = new JLabel("Số điện thoại:");
			lblNewLabel_13_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffPhoneNumber_1 = new JText("");
			txtLibStaffPhoneNumber_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_14_2 = new JLabel("Gmail:");
			lblNewLabel_14_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffGmail_1 = new JText("");
			txtLibStaffGmail_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_15_2 = new JLabel("Ngày bắt đầu làm việc:");
			lblNewLabel_15_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffStartWorkDate_1 = new JText("");
			txtLibStaffStartWorkDate_1.setEnabled(false);
			txtLibStaffStartWorkDate_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_16_2 = new JLabel("Chức vụ:");
			lblNewLabel_16_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffPosition_1 = new JText("");
			txtLibStaffPosition_1.setEnabled(false);
			txtLibStaffPosition_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_17_4 = new JLabel("Lương cơ bản:");
			lblNewLabel_17_4.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffBasicSalary_1 = new JText("");
			txtLibStaffBasicSalary_1.setEnabled(false);
			txtLibStaffBasicSalary_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_17_1_2 = new JLabel("Lương thưởng:");
			lblNewLabel_17_1_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffSalaryBonus_1 = new JText("");
			txtLibStaffSalaryBonus_1.setEnabled(false);
			txtLibStaffSalaryBonus_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
			lblNewLabel_17_2_2 = new JLabel("Tiền phạt");
			lblNewLabel_17_2_2.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtLibStaffPenalty_1 = new JText("");
			txtLibStaffPenalty_1.setEnabled(false);
			txtLibStaffPenalty_1.setFont(new Font("Times New Roman", Font.BOLD, 14));

			txtActualSalaryQL = new JText("");
			txtActualSalaryQL.setColumns(10);
			txtActualSalaryQL.setEnabled(false);
			txtActualSalaryQL.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblActualSalaryQL = new JLabel("Lương thực lĩnh:");
			lblActualSalaryQL.setFont(new Font("Times New Roman", Font.BOLD, 14));

			undoLibStaff();
			btnLibStaffLogOut_1 = new JButton("Đăng xuất");
			btnLibStaffLogOut_1.addActionListener(this);
			btnLibStaffLogOut_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnLibStaffLogOut_1.setBackground(Color.LIGHT_GRAY);

			btnQLChangePass = new JButton("Đổi mật khẩu");
			btnQLChangePass.addActionListener(this);
			btnQLChangePass.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnQLChangePass.setBackground(Color.LIGHT_GRAY);

			btnQLUpdateInfor = new JButton("Cập nhập thông tin");
			btnQLUpdateInfor.addActionListener(this);
			btnQLUpdateInfor.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnQLUpdateInfor.setBackground(Color.LIGHT_GRAY);

			btnUndoQL = new JButton("Hoàn tác");
			btnUndoQL.addActionListener(this);
			btnUndoQL.setFont(new Font("Times New Roman", Font.BOLD, 12));
			btnUndoQL.setBackground(Color.WHITE);

			btnShowAllAccountNV = new JButton("Xem tất cả tài khoản Nhiên viên");
			btnShowAllAccountNV.addActionListener(this);
			btnShowAllAccountNV.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnShowAllAccountNV.setBackground(Color.LIGHT_GRAY);

			lblNewLabel_63 = new JLabel("(*)");
			lblNewLabel_63.setForeground(SystemColor.controlShadow);
			lblNewLabel_63.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_64 = new JLabel("(*)");
			lblNewLabel_64.setForeground(SystemColor.controlShadow);
			lblNewLabel_64.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_65 = new JLabel("(*)");
			lblNewLabel_65.setForeground(SystemColor.controlShadow);
			lblNewLabel_65.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_66 = new JLabel("(*)");
			lblNewLabel_66.setForeground(SystemColor.controlShadow);
			lblNewLabel_66.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_67 = new JLabel("(*)");
			lblNewLabel_67.setForeground(SystemColor.controlShadow);
			lblNewLabel_67.setFont(new Font("Times New Roman", Font.BOLD, 14));

			lblNewLabel_68 = new JLabel("(*)");
			lblNewLabel_68.setForeground(SystemColor.controlShadow);
			lblNewLabel_68.setFont(new Font("Times New Roman", Font.BOLD, 14));

			btnLogUpND_1 = new JButton("Hiển thị tài khoản Người đọc");
			btnLogUpND_1.addActionListener(this);
			if (ID.substring(0, 2).equals("TT")) {
				btnShowAllAccountNV.setVisible(false);
				btnLogUpND_1.setVisible(false);
			}
			if (ID.substring(0, 2).equals("TQ"))
				btnLogUpND_1.setVisible(false);
			if (ID.substring(0, 2).equals("NV")) {
				btnShowAllAccountNV.setVisible(false);
			}
			btnLogUpND_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnLogUpND_1.setBackground(Color.LIGHT_GRAY);
			gl_pnlAccountQL = new GroupLayout(pnlAccountQL);
			gl_pnlAccountQL.setHorizontalGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlAccountQL.createSequentialGroup().addContainerGap().addGroup(gl_pnlAccountQL
							.createParallelGroup(Alignment.TRAILING)
							.addComponent(lblNewLabel_50, GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
							.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_pnlAccountQL.createSequentialGroup()
											.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_7_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffID_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_8_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffFullName_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_9_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffGender_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_10_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffDob_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_11_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffAddress_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_12_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffLicense_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_13_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffPhoneNumber_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_14_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffGmail_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_15_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffStartWorkDate_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_16_2, GroupLayout.PREFERRED_SIZE,
																	189, GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffPosition_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(lblNewLabel_17_2_2,
																	GroupLayout.PREFERRED_SIZE, 189,
																	GroupLayout.PREFERRED_SIZE)
															.addGap(10).addComponent(txtLibStaffPenalty_1,
																	GroupLayout.PREFERRED_SIZE, 394,
																	GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.TRAILING).addGroup(
													gl_pnlAccountQL.createSequentialGroup().addGroup(gl_pnlAccountQL
															.createParallelGroup(Alignment.LEADING)
															.addComponent(lblNewLabel_63, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_64, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_65, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_66, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_67, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE)
															.addComponent(lblNewLabel_68, GroupLayout.PREFERRED_SIZE,
																	17, GroupLayout.PREFERRED_SIZE))
															.addGap(94))
													.addGroup(gl_pnlAccountQL.createSequentialGroup()
															.addComponent(btnUndoQL, GroupLayout.PREFERRED_SIZE, 84,
																	GroupLayout.PREFERRED_SIZE)
															.addGap(18)))
											.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.TRAILING)
													.addGroup(gl_pnlAccountQL
															.createParallelGroup(Alignment.LEADING, false)
															.addComponent(btnLibStaffLogOut_1, Alignment.TRAILING,
																	GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(btnQLChangePass, Alignment.TRAILING,
																	GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(btnQLUpdateInfor, Alignment.TRAILING,
																	GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
													.addComponent(btnShowAllAccountNV, GroupLayout.PREFERRED_SIZE, 223,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addComponent(lblActualSalaryQL, GroupLayout.PREFERRED_SIZE,
																	173, GroupLayout.PREFERRED_SIZE)
															.addComponent(txtActualSalaryQL, GroupLayout.PREFERRED_SIZE,
																	223, GroupLayout.PREFERRED_SIZE))))
									.addGroup(gl_pnlAccountQL.createSequentialGroup().addGroup(gl_pnlAccountQL
											.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_pnlAccountQL.createSequentialGroup()
													.addComponent(lblNewLabel_17_4, GroupLayout.PREFERRED_SIZE, 189,
															GroupLayout.PREFERRED_SIZE)
													.addGap(10).addComponent(txtLibStaffBasicSalary_1,
															GroupLayout.PREFERRED_SIZE, 394,
															GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_pnlAccountQL.createSequentialGroup()
													.addComponent(lblNewLabel_17_1_2, GroupLayout.PREFERRED_SIZE, 189,
															GroupLayout.PREFERRED_SIZE)
													.addGap(10).addComponent(txtLibStaffSalaryBonus_1,
															GroupLayout.PREFERRED_SIZE, 394,
															GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
													Short.MAX_VALUE)
											.addComponent(btnLogUpND_1, GroupLayout.PREFERRED_SIZE, 223,
													GroupLayout.PREFERRED_SIZE))))
							.addContainerGap()));
			gl_pnlAccountQL.setVerticalGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlAccountQL.createSequentialGroup().addGroup(gl_pnlAccountQL
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlAccountQL.createSequentialGroup()
									.addComponent(lblNewLabel_50, GroupLayout.PREFERRED_SIZE, 32,
											GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(17)
													.addComponent(btnQLUpdateInfor, GroupLayout.PREFERRED_SIZE, 44,
															GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(btnQLChangePass, GroupLayout.PREFERRED_SIZE, 44,
															GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(btnShowAllAccountNV, GroupLayout.PREFERRED_SIZE, 44,
															GroupLayout.PREFERRED_SIZE)
													.addGap(108)
													.addComponent(lblActualSalaryQL, GroupLayout.PREFERRED_SIZE, 23,
															GroupLayout.PREFERRED_SIZE)
													.addGap(6)
													.addComponent(txtActualSalaryQL, GroupLayout.PREFERRED_SIZE, 34,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
													.addComponent(btnLogUpND_1, GroupLayout.PREFERRED_SIZE, 44,
															GroupLayout.PREFERRED_SIZE)
													.addGap(18).addComponent(btnLibStaffLogOut_1,
															GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_pnlAccountQL.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addComponent(lblNewLabel_7_2, GroupLayout.PREFERRED_SIZE,
																	31, GroupLayout.PREFERRED_SIZE)
															.addGroup(gl_pnlAccountQL
																	.createParallelGroup(Alignment.BASELINE)
																	.addComponent(txtLibStaffID_1,
																			GroupLayout.PREFERRED_SIZE, 31,
																			GroupLayout.PREFERRED_SIZE)
																	.addComponent(btnUndoQL)))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addComponent(lblNewLabel_8_2, GroupLayout.PREFERRED_SIZE,
																	32, GroupLayout.PREFERRED_SIZE)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(1)
																	.addComponent(txtLibStaffFullName_1,
																			GroupLayout.PREFERRED_SIZE, 31,
																			GroupLayout.PREFERRED_SIZE)))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_9_2,
																			GroupLayout.PREFERRED_SIZE, 24,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffGender_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_10_2,
																			GroupLayout.PREFERRED_SIZE, 24,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffDob_1, GroupLayout.PREFERRED_SIZE,
																	28, GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_11_2,
																			GroupLayout.PREFERRED_SIZE, 26,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffAddress_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(3)
																	.addComponent(lblNewLabel_12_2,
																			GroupLayout.PREFERRED_SIZE, 21,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffLicense_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(9)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_13_2,
																			GroupLayout.PREFERRED_SIZE, 21,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffPhoneNumber_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(11)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(3)
																	.addComponent(lblNewLabel_14_2,
																			GroupLayout.PREFERRED_SIZE, 21,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffGmail_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_15_2,
																			GroupLayout.PREFERRED_SIZE, 23,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffStartWorkDate_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_16_2,
																			GroupLayout.PREFERRED_SIZE, 23,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffPosition_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_17_4,
																			GroupLayout.PREFERRED_SIZE, 23,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffBasicSalary_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_17_1_2,
																			GroupLayout.PREFERRED_SIZE, 23,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffSalaryBonus_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE))
													.addGap(6)
													.addGroup(gl_pnlAccountQL.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(7)
																	.addComponent(lblNewLabel_17_2_2,
																			GroupLayout.PREFERRED_SIZE, 25,
																			GroupLayout.PREFERRED_SIZE))
															.addComponent(txtLibStaffPenalty_1,
																	GroupLayout.PREFERRED_SIZE, 28,
																	GroupLayout.PREFERRED_SIZE)))))
							.addGroup(gl_pnlAccountQL.createSequentialGroup().addGap(85)
									.addComponent(lblNewLabel_63, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addGap(19)
									.addComponent(lblNewLabel_64, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addGap(20)
									.addComponent(lblNewLabel_65, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addGap(20)
									.addComponent(lblNewLabel_66, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addGap(22)
									.addComponent(lblNewLabel_67, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)
									.addGap(20).addComponent(lblNewLabel_68, GroupLayout.PREFERRED_SIZE, 17,
											GroupLayout.PREFERRED_SIZE)))
							.addContainerGap()));
			pnlAccountQL.setLayout(gl_pnlAccountQL);
		}
		/////////////// AMDIN
		if (ID.equals("ADMIN"))

		{
			pnlADMIN = new JPanel();
			pnlADMIN.setFont(new Font("Times New Roman", Font.BOLD, 14));
			pnlADMIN.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnlADMIN.setBackground(Color.WHITE);
			tabbedPane.addTab("ADMIN", null, pnlADMIN, null);
			tabbedPane.setBackgroundAt(idTab++, Color.WHITE);

			lblNewLabel_51 = new JLabel("ADMIN");
			lblNewLabel_51.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_51.setForeground(new Color(147, 112, 219));
			lblNewLabel_51.setFont(new Font("Times New Roman", Font.BOLD, 20));

			btnDeleteAllAccount = new JButton("Cập nhập dữ liệu");
			btnDeleteAllAccount.addActionListener(this);
			btnDeleteAllAccount.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnDeleteAllAccount.setBackground(Color.LIGHT_GRAY);

			btnLogOutADMIN = new JButton("Đăng xuất");
			btnLogOutADMIN.addActionListener(this);
			btnLogOutADMIN.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnLogOutADMIN.setBackground(Color.LIGHT_GRAY);

			btnShowAllAccount_1 = new JButton("Bảo trì hệ thống");
			btnShowAllAccount_1.addActionListener(this);
			btnShowAllAccount_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnShowAllAccount_1.setBackground(Color.LIGHT_GRAY);

			btnDropDatabase = new JButton("Nâng cấp hệ thống");
			btnDropDatabase.addActionListener(this);
			btnDropDatabase.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
			btnDropDatabase.setBackground(Color.LIGHT_GRAY);

			gl_pnlADMIN = new GroupLayout(pnlADMIN);
			gl_pnlADMIN.setHorizontalGroup(gl_pnlADMIN.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlADMIN
					.createSequentialGroup()
					.addGroup(gl_pnlADMIN.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlADMIN.createSequentialGroup().addContainerGap().addComponent(lblNewLabel_51,
									GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE))
							.addGroup(gl_pnlADMIN.createSequentialGroup().addGap(389).addGroup(gl_pnlADMIN
									.createParallelGroup(Alignment.LEADING, false)
									.addComponent(btnLogOutADMIN, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
									.addComponent(btnShowAllAccount_1, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
									.addComponent(btnDeleteAllAccount, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnDropDatabase, GroupLayout.PREFERRED_SIZE, 223,
											GroupLayout.PREFERRED_SIZE))))
					.addContainerGap()));
			gl_pnlADMIN.setVerticalGroup(gl_pnlADMIN.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlADMIN
					.createSequentialGroup()
					.addComponent(lblNewLabel_51, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(18)
					.addComponent(btnShowAllAccount_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnDeleteAllAccount, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnDropDatabase, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(254)
					.addComponent(btnLogOutADMIN, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(20, Short.MAX_VALUE)));
			pnlADMIN.setLayout(gl_pnlADMIN);
		}
		contentPane.setLayout(gl_contentPane);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAddRentalInfor))
			addRentalInfor();
		else if (e.getSource().equals(btnUpdateRentalInfor))
			updateRentalInfor();
		else if (e.getSource().equals(btnDeleteRentalInfor))
			deleteRentalInfor();
		else if (e.getSource().equals(btnShowAllRentalInfor))
			showAllRentalInfor();
		else if (e.getSource().equals(btnRentalInforOverdue))
			showRentalInforOverdue();
		else if (e.getSource().equals(btnShowRentalInforByReaderID))
			showRentalInforByReaderID();
		else if (e.getSource().equals(btnShowRentalInforByBookID))
			showRentalInforByBookID();
		else if (e.getSource().equals(btnShowRentalInforByLibStaffID))
			showRentalInforByLibStaffID();
		else if (e.getSource().equals(btnShowRentalInforByID))
			showRentalInforByID();
		else if (e.getSource().equals(btnClearRentalInfor))
			clearRentalInfor();
		else if (e.getSource().equals(btnShowRentalInforInText))
			showRentalInforInText();
		else if (e.getSource().equals(btnRefreshRentalInfor))
			refreshRentalInfor();
		else if (e.getSource().equals(btnShowInforOverdue))
			showInforOverdue();
		else if (e.getSource().equals(btnUpdateReader))
			updateReader();
		else if (e.getSource().equals(btnDeleteReader))
			deleteReader();
		else if (e.getSource().equals(btnShowAllReader))
			showAllReader();
		else if (e.getSource().equals(btnShowReaderByID))
			showReaderByID();
		else if (e.getSource().equals(btnShowReaderByName))
			showReaderByName();
		else if (e.getSource().equals(btnClearReader))
			clearReader();
		else if (e.getSource().equals(btnAddReader))
			addReader();
		else if (e.getSource().equals(btnShowReaderOverdue))
			showReaderOverdue();
		else if (e.getSource().equals(btnShowReaderInText))
			showReaderInText();
		else if (e.getSource().equals(btnRefreshReader))
			refreshReader();
		else if (e.getSource().equals(btnAddBook))
			addBook();
		else if (e.getSource().equals(btnUpdateBook))
			updateBook();
		else if (e.getSource().equals(btnShowAllBook))
			showAllBook();
		else if (e.getSource().equals(btnDeleteBook))
			deleteBook();
		else if (e.getSource().equals(btnShowBookByID))
			showBookByID();
		else if (e.getSource().equals(btnShowBookByName))
			showBookByName();
		else if (e.getSource().equals(btnClearBook))
			clearBook();
		else if (e.getSource().equals(btnShowBookInText))
			showBookInText();
		else if (e.getSource().equals(btnRefreshBook))
			refreshBook();
		else if (e.getSource().equals(btnAddLibStaff))
			addLibStaff();
		else if (e.getSource().equals(btnUpdateStaff))
			updateLibStaff();
		else if (e.getSource().equals(btnDeleteStaff))
			deleteLibStaff();
		else if (e.getSource().equals(btnShowAllStaff))
			showAllLibStaff();
		if (e.getSource().equals(btnShowStaffByID))
			showLibStaffByID();
		else if (e.getSource().equals(btnShowStaffByName))
			showLibStaffByName();
		else if (e.getSource().equals(btnClearStaff))
			clearLibStaff();
		else if (e.getSource().equals(btnShowLibStaffInText))
			showLibStaffInText();
		else if (e.getSource().equals(btnRefreshLibStaff))
			refreshLibStaff();
		else if (e.getSource().equals(btnNDLogOut)) {
			dispose();
			new HomePage(0, "ĐĂNG NHẬP ĐỂ VÀO HỆ THỐNG THƯ VIỆN", "");
		} else if (e.getSource().equals(btnNDUpdateInfor))
			updateInforReader();
		else if (e.getSource().equals(btnShowInforBorrow))
			showInforBorrow(txtReaderID_1.getText());
		else if (e.getSource().equals(btnNDCheckBook))
			showAllBook();
		else if (e.getSource().equals(btnNDChangePass))
			new HomePage(2, "ĐỔI MẬT KHẨU NGƯỜI DÙNG", txtReaderID_1.getText());
		else if (e.getSource().equals(btnUndoND))
			undoReader();
		else if (e.getSource().equals(btnLibStaffLogOut_1)) {
			dispose();
			new HomePage(0, "ĐĂNG NHẬP ĐỂ VÀO HỆ THỐNG THƯ VIỆN", "");
		} else if (e.getSource().equals(btnQLChangePass))
			new HomePage(2, "ĐỔI MẬT KHẨU QUẢN LÝ", txtLibStaffID_1.getText());
		else if (e.getSource().equals(btnQLUpdateInfor))
			updateInforLibStaff();
		else if (e.getSource().equals(btnUndoQL))
			undoLibStaff();
		else if (e.getSource().equals(btnShowAllAccountNV))
			showAccount("");
		else if (e.getSource().equals(btnLogUpND_1))
			showAccount("ND");
		else if (e.getSource().equals(btnDeleteAllAccount)) {
			int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn bộ tài khoản", "Xác nhận",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION)
				libMgm.clearAccount("");
		} else if (e.getSource().equals(btnLogOutADMIN)) {
			dispose();
			new HomePage(0, "ĐĂNG NHẬP ĐỂ VÀO HỆ THỐNG THƯ VIỆN", "");
		} else if (e.getSource().equals(btnShowAllAccount_1))
			new ShowTable(covertListAccountToArray(libMgm.showAllAccount("")),
					new String[] { "Tên đăng nhập", "Mật khẩu" }, "TẤT CẢ TÀI KHOẢN");
		else if (e.getSource().equals(btnDropDatabase)) {
			int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn dữ liệu lưu trữ hiện có",
					"Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION)
				libMgm.dropDataBase();
		}
	}

	// Khai báo biến
	private LibManagement libMgm;
	private JTabbedPane tabbedPane;
	private GroupLayout gl_contentPane;
	private GroupLayout gl_pnlRentalInfor;
	private GroupLayout gl_pnlReader;
	private GroupLayout gl_pnlBook;
	private GroupLayout gl_pnlLibStaff;
	private GroupLayout gl_pnlAccountND;
	private GroupLayout gl_pnlAccountQL;
	private GroupLayout gl_pnlADMIN;
	private JPanel pnlReader;
	private JPanel pnlBook;
	private JPanel pnlLibStaff;
	private JPanel contentPane;
	private JPanel pnlRentalInfor;
	private JPanel pnlADMIN;
	private JPanel pnlAccountQL;
	private JPanel pnlAccountND;
	private JButton btnShowRentalInforByReaderID;
	private JButton btnShowRentalInforByBookID;
	private JButton btnShowRentalInforByLibStaffID;
	private JButton btnShowRentalInforByID;
	private JButton btnClearRentalInfor;
	private JButton btnUpdateReader;
	private JButton btnDeleteReader;
	private JButton btnShowAllReader;
	private JButton btnShowReaderByID;
	private JButton btnShowReaderByName;
	private JButton btnShowReaderOverdue;
	private JButton btnClearReader;
	private JButton btnAddBook;
	private JButton btnUpdateBook;
	private JButton btnShowAllBook;
	private JButton btnDeleteBook;
	private JButton btnShowBookByID;
	private JButton btnShowBookByName;
	private JButton btnClearBook;
	private JButton btnAddLibStaff;
	private JButton btnUpdateStaff;
	private JButton btnDeleteStaff;
	private JButton btnShowAllStaff;
	private JButton btnShowStaffByID;
	private JButton btnShowStaffByName;
	private JButton btnClearStaff;
	private JButton btnAddRentalInfor;
	private JButton btnUpdateRentalInfor;
	private JButton btnDeleteRentalInfor;
	private JButton btnShowAllRentalInfor;
	private JButton btnRentalInforOverdue;
	private JButton btnShowBookInText;
	private JButton btnShowReaderInText;
	private JButton btnShowLibStaffInText;
	private JButton btnShowRentalInforInText;
	private JButton btnRefreshRentalInfor;
	private JButton btnRefreshReader;
	private JButton btnRefreshLibStaff;
	private JButton btnRefreshBook;
	private JButton btnShowInforOverdue;
	private JButton btnShowInforBorrow;
	private JButton btnNDCheckBook;
	private JButton btnNDChangePass;
	private JButton btnLibStaffLogOut_1;
	private JButton btnQLChangePass;
	private JButton btnQLUpdateInfor;
	private JButton btnDeleteAllAccount;
	private JButton btnLogOutADMIN;
	private JButton btnNDLogOut;
	private JButton btnNDUpdateInfor;
	private JButton btnUndoND;
	private JButton btnUndoQL;
	private JButton btnShowAllAccountNV;
	private JButton btnShowAllAccount_1;
	private JButton btnLogUpND_1;
	private JButton btnAddReader;
	private JButton btnDropDatabase;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_1_1;
	private JLabel lblNewLabel_1_2;
	private JLabel lblNewLabel_1_3;
	private JLabel lblNewLabel_1_4;
	private JLabel lblNewLabel_1_5;
	private JLabel lblNewLabel_1_6;
	private JLabel lblNewLabel_1_7;
	private JLabel lblNewLabel_1_8;
	private JLabel lblNewLabel_1_9;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_12;
	private JLabel lblNewLabel_13;
	private JLabel lblNewLabel_14;
	private JLabel lblNewLabel_15;
	private JLabel lblNewLabel_16;
	private JLabel lblNewLabel_17;
	private JLabel lblNewLabel_17_1;
	private JLabel lblNewLabel_17_2;
	private JLabel lblNewLabel_18;
	private JLabel lblNewLabel_19;
	private JLabel lblNewLabel_20;
	private JLabel lblNewLabel_21;
	private JLabel lblNewLabel_22;
	private JLabel lblNewLabel_23;
	private JLabel lblNewLabel_24;
	private JLabel lblNewLabel_25;
	private JLabel lblNewLabel_25_1;
	private JLabel lblNewLabel_25_2;
	private JLabel lblNewLabel_25_3;
	private JLabel lblNewLabel_25_4;
	private JLabel lblNewLabel_25_5;
	private JLabel lblNewLabel_26;
	private JLabel lblNewLabel_27;
	private JLabel lblNewLabel_28;
	private JLabel lblNewLabel_29;
	private JLabel lblNewLabel_30;
	private JLabel lblNewLabel_31;
	private JLabel lblNewLabel_32;
	private JLabel lblNewLabel_33;
	private JLabel lblNewLabel_34;
	private JLabel lblNewLabel_35;
	private JLabel lblNewLabel_36;
	private JLabel lblNewLabel_37;
	private JLabel lblNewLabel_38;
	private JLabel lblNewLabel_39;
	private JLabel lblNewLabel_40;
	private JLabel lblNewLabel_41;
	private JLabel lblNewLabel_42;
	private JLabel lblNewLabel_43;
	private JLabel lblNewLabel_44;
	private JLabel lblNewLabel_45;
	private JLabel lblNewLabel_46;
	private JLabel lblNewLabel_47;
	private JLabel lblNewLabel_51;
	private JLabel lblNewLabel_50;
	private JLabel lblNewLabel_7_2;
	private JLabel lblNewLabel_8_2;
	private JLabel lblNewLabel_9_2;
	private JLabel lblNewLabel_10_2;
	private JLabel lblNewLabel_11_2;
	private JLabel lblNewLabel_12_2;
	private JLabel lblNewLabel_13_2;
	private JLabel lblNewLabel_14_2;
	private JLabel lblNewLabel_15_2;
	private JLabel lblNewLabel_16_2;
	private JLabel lblNewLabel_17_4;
	private JLabel lblNewLabel_17_1_2;
	private JLabel lblNewLabel_17_2_2;
	private JLabel lblNewLabel_48;
	private JLabel lblNewLabel_52;
	private JLabel lblNewLabel_1_10;
	private JLabel lblNewLabel_1_1_1;
	private JLabel lblNewLabel_1_2_1;
	private JLabel lblNewLabel_1_3_1;
	private JLabel lblNewLabel_1_4_1;
	private JLabel lblNewLabel_1_5_1;
	private JLabel lblNewLabel_1_6_1;
	private JLabel lblNewLabel_1_7_1;
	private JLabel lblNewLabel_1_8_1;
	private JLabel lblNewLabel_1_9_1;
	private JLabel lblActualSalaryQL;
	private JLabel lblNewLabel_53;
	private JLabel lblNewLabel_54;
	private JLabel lblNewLabel_55;
	private JLabel lblNewLabel_56;
	private JLabel lblNewLabel_63;
	private JLabel lblNewLabel_64;
	private JLabel lblNewLabel_65;
	private JLabel lblNewLabel_66;
	private JLabel lblNewLabel_67;
	private JLabel lblNewLabel_68;
	private JText txtReaderID;
	private JText txtReaderFullName;
	private JText txtReaderGender;
	private JText txtReaderDob;
	private JText txtReaderAddress;
	private JText txtReaderLicense;
	private JText txtReaderPhoneNumber;
	private JText txtReaderGmail;
	private JText txtReaderKindOfReader;
	private JText txtReaderStartDate;
	private JText txtReaderEndDate;
	private JText txtBookID;
	private JText txtBookName;
	private JText txtBookKindOfBook;
	private JText txtBookAuthor;
	private JText txtBookQuantity;
	private JText txtLibStaffID;
	private JText txtLibStaffFullName;
	private JText txtLibStaffGender;
	private JText txtLibStaffDob;
	private JText txtLibStaffAddress;
	private JText txtLibStaffLicense;
	private JText txtLibStaffPhoneNumber;
	private JText txtLibStaffGmail;
	private JText txtLibStaffStartWorkDate;
	private JText txtLibStaffPosition;
	private JText txtLibStaffBasicSalary;
	private JText txtLibStaffSalaryBonus;
	private JText txtLibStaffPenalty;
	private JText txtRentalInforID;
	private JText txtRentalInforReaderID;
	private JText txtRentalInforBookID;
	private JText txtRentalInforLibStaffID;
	private JText txtRentalInforBookBorrowDate;
	private JText txtRentalInforBookReturnDate;
	private JText txtReaderEndDate_1;
	private JText txtReaderStartDate_1;
	private JText txtReaderKindOfReader_1;
	private JText txtReaderGmail_1;
	private JText txtReaderPhoneNumber_1;
	private JText txtReaderLicense_1;
	private JText txtReaderAddress_1;
	private JText txtReaderDob_1;
	private JText txtReaderGender_1;
	private JText txtReaderFullName_1;
	private JText txtReaderID_1;
	private JText txtLibStaffID_1;
	private JText txtLibStaffFullName_1;
	private JText txtLibStaffGender_1;
	private JText txtLibStaffDob_1;
	private JText txtLibStaffAddress_1;
	private JText txtLibStaffLicense_1;
	private JText txtLibStaffPhoneNumber_1;
	private JText txtLibStaffGmail_1;
	private JText txtLibStaffStartWorkDate_1;
	private JText txtLibStaffPosition_1;
	private JText txtLibStaffBasicSalary_1;
	private JText txtLibStaffSalaryBonus_1;
	private JText txtLibStaffPenalty_1;
	private JText txtActualSalaryQL;
	private JTextA txtRentalInforNote;
}
