/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package newpackage.view;

import newpackage.controller.MNStudentController;
import newpackage.dao.StudentDAOImport;
import newpackage.model.Student;
import newpackage.until.ClassTableModel;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowSorter.SortKey;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.mysql.cj.protocol.a.authentication.MysqlClearPasswordPlugin;
import com.mysql.cj.xdevapi.Table;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.ref.Cleaner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author DELL
 */
public class StudentJPanel extends javax.swing.JPanel {

    /**
     * Creates new form StudentJPanel
     */
	private DefaultTableModel model;
	private JTable table;
    public StudentJPanel() {
        initComponents();
        
        MNStudentController controller = new MNStudentController(jpnView, btnAdd , jtfFind);
        controller.setDateToTable();
        controller.setEvent();
    }
//    public void ReLoad() {
//    	MNStudentController controller = new MNStudentController(jpnView, btnAdd , jtfFind);
//    	controller.setDateToTable();
//    	controller.setEvent();
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        jtfFind = new javax.swing.JTextField();
        jpnView = new javax.swing.JPanel();

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));

        btnAdd.setBackground(Color.GREEN);
        btnAdd.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnAdd.setText("Insert");
        btnAdd.setBorder(null);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jtfFind.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
        jpnView.setLayout(jpnViewLayout);
        jpnViewLayout.setHorizontalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
        );
        
        btnSort = new JButton();
        btnSort.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MNStudentController controller = new MNStudentController(jpnView, btnAdd , jtfFind);
    			controller.setDateToTable();
    			model = (DefaultTableModel) controller.getTable().getModel();
    			StudentDAOImport sdi = new StudentDAOImport();
				List<Student> listItem = sdi.sortList();
				String[] listColumn = {"STT" ,"Student ID" ,"Name" , "birthday" , "gender" ,"phone" , "address", "status"};
				table = controller.getTable();
				TableRowSorter<TableModel> rowSorter = null;
				model = new ClassTableModel().setTableStudent(listItem, listColumn);
				table = new JTable(model);
				rowSorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(rowSorter);
				if (controller.getTable().getSelectedRow() != -1) {
					int selectdRowIndex = controller.getTable().getSelectedRow();
					selectdRowIndex = controller.getTable().convertColumnIndexToModel(selectdRowIndex);
					
					
					
					Student student = new Student();
					student.setStudent_id(model.getValueAt(selectdRowIndex, 1).toString());
					student.setName( model.getValueAt(selectdRowIndex, 2).toString());
					student.setBirthday((Date)model.getValueAt(selectdRowIndex, 3));
					student.setGender(model.getValueAt(selectdRowIndex, 4).toString());
					student.setPhone(model.getValueAt(selectdRowIndex, 5) != null ?
							model.getValueAt(selectdRowIndex, 5).toString() : null);
					student.setAddress(model.getValueAt( selectdRowIndex, 6) != null ?
							model.getValueAt(selectdRowIndex, 6).toString() : null);
					student.setStatus((boolean)model.getValueAt(selectdRowIndex, 7));
					
					
				}
				
//        		getRowSorter(treeTableModel.getRoot()).sort(true);
        	}
        });
        btnSort.setText("Sort");
        btnSort.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSort.setBorder(null);
        btnSort.setBackground(Color.GREEN);
        
        JButton btnExport = new JButton();
        btnExport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFileChooser fileChooser = new JFileChooser();
        		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("File Text(.txt)", ".txt");
        		fileChooser.setFileFilter(fileFilter);
        		fileChooser.setMultiSelectionEnabled(true);
        		
        		int x = fileChooser.showDialog(btnExport, "Chọn file");
        		if (x == JFileChooser.APPROVE_OPTION) {
        			File f = fileChooser.getSelectedFile();
        			try {
        				FileOutputStream fos = new FileOutputStream(f + ".txt");
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        MNStudentController controller = new MNStudentController(jpnView, btnAdd , jtfFind);
            			controller.setDateToTable();
                        model = (DefaultTableModel) controller.getTable().getModel();
                        int size = model.getRowCount();
                        //System.out.println(size);
                        Student student[] = new Student [size];
                        for (int i=0; i<size; i++) {
                        	student[i] = new Student();
                        	student[i].setStudent_id(model.getValueAt(i, 1).toString());
                        	student[i].setName(model.getValueAt(i, 2).toString());
                        	student[i].setBirthday((Date) model.getValueAt(i, 3));
                        	student[i].setGender(model.getValueAt(i, 4).toString());
                        	student[i].setPhone(model.getValueAt(i, 5).toString());
                        	student[i].setAddress(model.getValueAt(i, 6).toString());
                        	student[i].setStatus((Boolean)model.getValueAt(i, 7));
                        	os.writeObject(student[i]);
                        }
                        os.flush();
                        os.close();
                        fos.close();
                        JOptionPane.showMessageDialog(null, "Lưu file thành công"); 
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
						 JOptionPane.showMessageDialog(null, "Lưu file thất bại"); 
					}
        		}
        		
        	}
        });
        btnExport.setText("Export");
        btnExport.setFont(new Font("Arial", Font.PLAIN, 18));
        btnExport.setBorder(null);
        btnExport.setBackground(Color.GREEN);
        
        JButton btnImport = new JButton();
        btnImport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFileChooser fileChooser = new JFileChooser();
        		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("file", ".txt");
        		fileChooser.setFileFilter(fileFilter);
        		fileChooser.setMultiSelectionEnabled(false);
        		
        		int x = fileChooser.showDialog(btnExport, "Chọn file");
        		if (x == JFileChooser.APPROVE_OPTION) {
        			try {
        				File f = fileChooser.getSelectedFile();
        				FileInputStream fos = new FileInputStream(f);
        				ObjectInputStream os = new ObjectInputStream(fos);
                        MNStudentController controller = new MNStudentController(jpnView, btnAdd , jtfFind);
            			controller.setDateToTable();
                        model = (DefaultTableModel) controller.getTable().getModel();
                        int size = model.getRowCount();
                        
                        //System.out.println(size);
                        List<Student> listStudents = new ArrayList<Student>();
                        Student student[] = new Student [size];
                        for (int i=0; i<size; i++) {
                        	student[i] = (Student) os.readObject();
                        	listStudents.add(student[i]);
                        }
                        Object[] row = new Object[8];
                        for (int i=0; i<listStudents.size(); i++) {
							row[0] = (size+1) + i;
							row[1] = listStudents.get(i).getStudent_id();
							row[2] = listStudents.get(i).getName();
							row[3] = listStudents.get(i).getBirthday();
							row[4] = listStudents.get(i).getGender();
							row[5] = listStudents.get(i).getPhone();
							row[6] = listStudents.get(i).getAddress();
							row[7] = listStudents.get(i).getStatus();
							model.addRow(new Object[] {row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]});
                        }
                        os.close();
                        fos.close();
                        JOptionPane.showMessageDialog(null, "Đọc file thành công"); 

        			} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
						 JOptionPane.showMessageDialog(null, "Đọc file thất bại"); 
					}
        			
        		}
        	}
        });
        btnImport.setText("Import");
        btnImport.setFont(new Font("Arial", Font.PLAIN, 18));
        btnImport.setBorder(null);
        btnImport.setBackground(Color.GREEN);
        
        btnSave = new JButton();
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MNStudentController controller = new MNStudentController(jpnView, btnAdd , jtfFind);
    			controller.setDateToTable();
                model = (DefaultTableModel) controller.getTable().getModel();
                int size = model.getRowCount();
                System.out.println(size);
                for (int i = size - 1; i >= 0; i--) {
                	
                    model.removeRow(i);
                }
        	}
        });
        btnSave.setText("Clear");
        btnSave.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSave.setBorder(null);
        btnSave.setBackground(Color.GREEN);

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRootLayout.setHorizontalGroup(
        	jpnRootLayout.createParallelGroup(Alignment.TRAILING)
        		.addComponent(jpnView, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        		.addGroup(jpnRootLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jpnRootLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jtfFind, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        				.addGroup(jpnRootLayout.createSequentialGroup()
        					.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnSort, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnExport, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnImport, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        jpnRootLayout.setVerticalGroup(
        	jpnRootLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jpnRootLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jtfFind, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jpnRootLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jpnRootLayout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        					.addComponent(btnSort, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        					.addComponent(btnExport, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        					.addComponent(btnImport, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        				.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addComponent(jpnView, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
        );
        jpnRoot.setLayout(jpnRootLayout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jpnRoot, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jpnRoot, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        this.setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnView;
    private javax.swing.JTextField jtfFind;
    private JButton btnSort;
    private JButton btnSave;
}
