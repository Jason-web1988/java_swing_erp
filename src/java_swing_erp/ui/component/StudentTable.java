package java_swing_erp.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java_swing_erp.dto.Student;

@SuppressWarnings("serial")
public class StudentTable extends JTable {
	private CustomModel model;
	

	public StudentTable() {
		initComponents();
	}
	
	private void initComponents() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void loadData(ArrayList<Student> stdlist) {
		model = new CustomModel(getRows(stdlist),getcolName());
		setModel(model);
	}
	
	private String[] getcolName() {
		return new String[] {
			"번호", "성명", "국어", "영어", "수학", "총점", "평균"};
	}
	
	private Object[][] getRows(ArrayList<Student> stdlist) {
		Object[][] rows = new Object[stdlist.size()][];
		for(int i =0; i<rows.length; i++) {
			rows[i] =  toArray(stdlist.get(i));
		}
		return rows;
	}
	
	private Object[] toArray(Student std) {
		// TODO Auto-generated method stub
		return new Object[] {
				std.getNo(),
				std.getName(),
				std.getKor(),
				std.getEng(),
				std.getMath(),
				std.getTotal(),
				std.getAverage()};
	}

	public void setItems(ArrayList<Student> stdlist) {
		loadData(stdlist);
		
		//column width
		tableSetWidth(150,200,100,100,100,100,100);
		//column alingnment
		tableCellAlign(SwingConstants.CENTER, 0,1);
		tableCellAlign(SwingConstants.RIGHT, 2,3,4,5,6);
		
	}
	private void tableCellAlign(int align, int...idx) {
		DefaultTableCellRenderer dtcr= new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);
		
		TableColumnModel tcm = getColumnModel();
		for(int i=0; i<idx.length; i++) {
			tcm.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}
	private void tableSetWidth(int...width) {
		TableColumnModel tcm = getColumnModel();
		for(int i=0; i<width.length; i++) {
			tcm.getColumn(i).setPreferredWidth(width[i]);
		}
	}
	
	public void addRow(Student student) {
		model.addRow(toArray(student));
	}
	
	public void removeRow(int idx) {
		model.removeRow(idx);
		
	}

	public void updateRow(int idx, Student updatetstd) {
		model.removeRow(idx);
		model.insertRow(idx, toArray(updatetstd));
		
	}
	
	
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		double value =  (double)getValueAt(row, 6);
		if(value >=90) {
			c.setBackground(new Color(255, 0, 0, 127));
		}else {
			c.setBackground(Color.white);
		}
		super.prepareRenderer(renderer, row, column);
		return c;
	}



	private class CustomModel extends DefaultTableModel{

		public CustomModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false; //각셀수정 불가능
		}
		
		
	}

	
	
	

	
}	
