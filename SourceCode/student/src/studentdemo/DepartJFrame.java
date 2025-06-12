package studentdemo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
public class DepartJFrame {
	DefaultTableModel tableModel;		// Ĭ����ʾ�ı��
	JButton btnAdd,btnDelete,btnCancel,btnChange;		// ������ť
	JTable table;		// ���
	JFrame f= new JFrame("ϵ����Ϣ���봰��");
	JPanel panelUP;	//������Ϣ�����
	JLabel laCno,laName;
	JTextField txtDno,txtName;
	gsqlCon con=null;
	// ���캯��
	public  DepartJFrame(){
		f.setBounds(300, 200, 642, 462);		// ���ô����С
		f.setTitle("ϵ����Ϣ���봰��");		// ���ô�������
		f.setLayout(null);		
		// �½�����ť���
		btnAdd = new JButton("����");
		btnDelete = new JButton("ɾ��");
		btnDelete.setBounds(191,270,66,26);
		btnChange = new JButton("����");
		btnChange.setBounds(268,270,66,26);
		btnCancel = new JButton("�˳�");
		btnCancel.setBounds(345,270,66,26);
		f.add(btnDelete);
		f.add(btnChange);
		f.add(btnCancel);
		panelUP = new JPanel();		// �½���ť������
	    panelUP.setLayout(null);
		panelUP.setBounds(92,30,427,60);
		panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		laCno = new JLabel("ϵ��:");
		laCno.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		laName = new JLabel("ϵ��:");
		laName.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		txtDno = new JTextField();
		txtName = new JTextField();
		laCno.setBounds(20, 25,40, 12);
		laName.setBounds(163, 25, 40, 12);
		txtDno.setBounds(63, 18, 85, 25);
		txtName.setBounds(208, 18, 85,25);
		btnAdd.setBounds(313,17,66,26);
		panelUP.add(btnAdd);
		panelUP.add(laCno);
		panelUP.add(laName);
		panelUP.add(txtDno);
		panelUP.add(txtName);
	    ResultSet rs = null;
		Vector columnNames = new Vector();
		//��������
		columnNames.add("ϵ��");
		columnNames.add("ϵ��");
		Vector rowData = new Vector();
		//try {
		//	rs= con.gsqlquery(con.GetCon(), "select * from liuz_college01;");
		//	if(rs==null) {System.out.print("��ѯ������ ������������");}
		//} catch (SQLException e1) {
		//		e1.printStackTrace();
		//}
		//try {
		//    while(rs.next()){
		//		//rowData���Դ�Ŷ���
		//		Vector hang=new Vector();
		//		hang.add(rs.getString(1));
		//		hang.add(rs.getString(2));
		//		//���뵽rowData
		//		rowData.add(hang);
		//	}
		// } catch (SQLException e) {
		//		// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
		// �½����
		tableModel = new DefaultTableModel(rowData,columnNames);	
		table = new JTable(tableModel) {

			   @Override
			   public boolean isCellEditable(int row, int column) {
               if(column == 1)
			       return true;
               else
             	  return false;
			   }
			};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane s = new JScrollPane(table);
		s.setBounds(92, 120, 427, 120);
		// �����ͱ��ֱ���ӵ�������
		f.add(panelUP);
		f.add(s);
		// �¼�����
		MyEvent();
		f.setVisible(true);		// ��ʾ����
	}
	// �¼�����
	public void MyEvent(){
		// ����
		btnAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// ����һ�пհ�����
				String sql=null;
				int a;          
	            sql = "insert into Depts values( '" + txtDno.getText() + "','" + txtName.getText()  + "');";            
	            a= con.gsqlexc(con.GetCon(), sql);
	            if(a>0) {
	               tableModel.addRow(new Object[] {txtDno.getText(), txtName.getText()});
	           	   txtDno.setText("");
				   txtName.setText("");
	            }
			}
		});
		// ɾ��
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// ɾ��ָ����
				int rscount ;
				int rowcount = table.getSelectedRow();
				if(rowcount==-1) {	
				JOptionPane.showMessageDialog(null, "��ѡ������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
				}
				String sql="delete from Depts where Dno='" + table.getValueAt(rowcount, 0)+"';";
				rscount=con.gsqlexc(con.GetCon(), sql);
				if(rscount>0)
					tableModel.removeRow(rowcount);
			}
		});
		//����
		btnChange.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				int rowcount = table.getSelectedRow();
				if(rowcount==-1) {	
				JOptionPane.showMessageDialog(null, "��ѡ������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
				}
				String sql="update Depts set Dname='" + table.getValueAt(rowcount, 1)+"'where Dno ='"+table.getValueAt(rowcount, 0) +"';";
				con.gsqlexc(con.GetCon(), sql);
			}
		});
		// �˳�
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			f.dispose();
			}	
		});
	}
	// ������
	public static void main(String[] args){
		new DepartJFrame();
	}
}
