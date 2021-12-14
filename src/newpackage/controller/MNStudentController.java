package newpackage.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import newpackage.model.Student;
import newpackage.service.StudentService;
import newpackage.service.StudentServiceImport;
import newpackage.until.ClassTableModel;
import newpackage.view.StudentFrame;

import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MNStudentController {
	private JPanel jpnView ;
	private JButton btnAdd;
	private JTextField jtfFind;
	
	private StudentService studentservice = null; 
	private String[] listColumn = {"STT" ,"Student ID" ,"Name" , "birthday" , "gender" ,"phone" , "address", "status"};
	
	private TableRowSorter<TableModel> rowSorter = null;
	
	public MNStudentController(JPanel jpnView, JButton btnAdd, JTextField jtfFind) {
		super();
		this.jpnView = jpnView;
		this.btnAdd = btnAdd;
		this.jtfFind = jtfFind;
		this.studentservice = new StudentServiceImport();
	}

	public void setDateToTable() {
		List<Student> listItem = studentservice.getList();
		
		DefaultTableModel model = new ClassTableModel().setTableStudent(listItem, listColumn);
		JTable table = new JTable(model);
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);
		
		jtfFind.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFind.getText();
				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				}else {
					rowSorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + text));
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFind.getText();
				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				}else {
					rowSorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					int selectdRowIndex = table.getSelectedRow();
					selectdRowIndex = table.convertColumnIndexToModel(selectdRowIndex);
					System.out.println(selectdRowIndex);
					
					Student student = new Student();
//					student.setStudent_id((String) model.getValueAt(selectdRowIndex, 0));
//					student.setStudent_id(null);
//					student.setStudent_id(model.getValueAt(selectdRowIndex, 1).toString());
//					student.setName(model.getValueAt(selectdRowIndex, 2).toString());
//					model.getColumnName(0);
//					student.setStudent_id((String) model.getValueAt(selectdRowIndex, 0));
//					model.getColumnName(0);
					student.setStudent_id(model.getColumnName(0));
//					student.setStudent_id(model.getValueAt(selectdRowIndex, 1).toString());
					student.setName(model.getValueAt(selectdRowIndex, 3).toString());
					StudentFrame frame = new StudentFrame(student);
					frame.setTitle("Information Student");
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
				super.mouseClicked(e);
			}
		});
		
		table.getTableHeader().setFont(new Font("Arrial", Font.BOLD, 14));
		table.getTableHeader().setPreferredSize(new Dimension(100,50));
		table.setRowHeight(50);
		table.validate();
		table.repaint();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(table);
		scrollPane.setPreferredSize(new Dimension(1300,400));
		
		jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scrollPane);
        jpnView.validate();
        jpnView.repaint();
	}
}
