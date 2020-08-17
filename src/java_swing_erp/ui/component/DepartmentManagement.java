package java_swing_erp.ui.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java_swing_erp.dto.Department;
import java_swing_erp.ui.component.content.DepartmentPanel;
import java_swing_erp.ui.component.table.DepartmentTable;

@SuppressWarnings("serial")
public class DepartmentManagement extends JFrame implements ActionListener {

    private JPanel contentPane;
    private DepartmentPanel pDepartment;
    private JPanel pBtns;
    private JPanel pTable;
    private JButton btnAdd;
    private JButton btnCancel;
    private JScrollPane scrollPane;
    private DepartmentTable table;
    private ArrayList<Department> deptList = new ArrayList<>();

    public DepartmentManagement() {
        initComponents();
        
        Department dept = new Department(4, "컴퓨터공학", "053-111-1111");
        pDepartment.setItem(dept);
        
//        stdList = new ArrayList<Student>();
        deptList.add(new Department(1, "경영학", "053-111-1111"));
        deptList.add(new Department(2, "물리학", "053-222-2222"));
        deptList.add(new Department(3, "교육학", "053-333-3333"));

        table.setItems(deptList);
    }

    private void initComponents() {
        setTitle("학과 관리");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(550, 100, 450, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        pDepartment = new DepartmentPanel();
        contentPane.add(pDepartment);
        
        pBtns = new JPanel();
        contentPane.add(pBtns);
        
        btnAdd = new JButton("추가");
        btnAdd.addActionListener(this);
        pBtns.add(btnAdd);
        
        btnCancel = new JButton("취소");
        btnCancel.addActionListener(this);
        pBtns.add(btnCancel);
        
        pTable = new JPanel();
        contentPane.add(pTable);
        pTable.setLayout(new BorderLayout(0, 0));
        
        scrollPane = new JScrollPane();
        pTable.add(scrollPane, BorderLayout.CENTER);
        
        table = new DepartmentTable();
        //popupmenu 장착
        
        CustomPopupMenu popMenu = new CustomPopupMenu(this);
        table.setComponentPopupMenu(popMenu);
        scrollPane.setViewportView(table);
    }

    public void actionPerformed(ActionEvent e) {
//        System.out.println(e);
        if (e.getSource() instanceof JButton) {
            if (e.getSource() == btnCancel) {
                actionPerformedBtnCancel(e);
            }
            if (e.getSource() == btnAdd) {
                if (e.getActionCommand().equals("추가")) {
                    actionPerformedBtnAdd(e);
                }else {
                    actionPerformedBtnUpdate();
                }
            }
        }
        if (e.getSource() instanceof JMenuItem) {
            if (e.getActionCommand().equals("수정")) {
                actionPerformedMenuUpdate();
            }
            if (e.getActionCommand().equals("삭제")) {
                actionPerformedMenuDelete();
            }
            if (e.getActionCommand().equals("세부 정보")) {
                actionPerformedMenuDetail();
            }
        }
    }
    
    private void actionPerformedBtnUpdate() {
        //1. StudentPanel에서 수정된 학생정보를 가져옴
        //2. stdList에서 학생정보 수정
        //3. studentTable에서 학생정보 수정
        //4. clearTf()
        //5. studentPanel setEditableTf(true)
        //6. btnAdd 텍스를 추가로 변경
        //7. message()
        Department updatedDept = pDepartment.getItem();
        int idx = deptList.indexOf(updatedDept);
        deptList.set(idx, updatedDept);
        table.updateRow(idx, updatedDept);
        pDepartment.clearTf();
        pDepartment.setEditableNoTf(true);
        btnAdd.setText("추가");
        JOptionPane.showMessageDialog(null, String.format("%s(%d) 수정 완료!!", updatedDept.getName(), updatedDept.getNo()));
    }

    private void actionPerformedMenuUpdate() {
        System.out.println("수정~~~");
        int selIdx = table.getSelectedRow();
        if (selIdx == -1) {
            JOptionPane.showMessageDialog(null, "해당 항목을 선택하세요.");
            return;
        }
        Department selDept = deptList.get(selIdx);
        pDepartment.setItem(selDept);
        //1. 버튼의 텍스를 수정으로 변경
        //2. pStudent 학번은 변경 불가능하게..
        btnAdd.setText("수정");
        pDepartment.setEditableNoTf(false);
    }

    private void actionPerformedMenuDelete() {
//        System.out.println("삭제~~~");  
        int selIdx = table.getSelectedRow();
        if (selIdx == -1) {
            JOptionPane.showMessageDialog(null, "해당 항목을 선택하세요.");
            return;
        }
        Department deletedDept = deptList.remove(selIdx);
        table.removeRow(selIdx);
        JOptionPane.showMessageDialog(null, String.format("%s(%d) 삭제 완료!!", deletedDept.getName(), deletedDept.getNo()));
    }

    private void actionPerformedMenuDetail() {
        System.out.println("세부 정보~~~");    
        int selIdx = table.getSelectedRow();
        if (selIdx == -1) {
            JOptionPane.showMessageDialog(null, "해당 항목을 선택하세요.");
            return;
        }
        Department detailStd = deptList.get(selIdx);
        System.out.println(detailStd);
        DepartmentDetailDlg dlg = new DepartmentDetailDlg();
        dlg.setDepartment(detailStd);
        dlg.setTfNotEditable();
        dlg.setVisible(true);
    }

    protected void actionPerformedBtnAdd(ActionEvent e) {
        //1. StudentPanel에서 getStudent()
        //2. StudentTable addRow();
        //3. StudentPanel clearTf();
        //4. Message();
        Department newDept = pDepartment.getItem();
//        System.out.println(newStd);
        table.addRow(newDept);
        pDepartment.clearTf();
        JOptionPane.showMessageDialog(null, String.format("%s(%d) 추가 완료!!", newDept.getName(), newDept.getNo()));
        deptList.add(newDept);
    }
    
    protected void actionPerformedBtnCancel(ActionEvent e) {
        pDepartment.clearTf();
        if (btnAdd.getText().equals("수정")) {
            btnAdd.setText("추가");
            pDepartment.setEditableNoTf(true);
            table.clearSelection();
        }
    }
   
}





