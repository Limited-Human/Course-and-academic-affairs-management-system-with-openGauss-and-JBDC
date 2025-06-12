package studentdemo;

import org.omg.CORBA.INTERNAL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class CourseJFrame {
	DefaultTableModel tableModel;		// Ĭ����ʾ�ı��
	JButton btnAdd,btnDelete,btnCancel,btnChange,btnShouke;		// ������ť
	JTable table;		// ���
	JFrame f= new JFrame("�γ���Ϣ���봰��");
	JPanel panelUP;	//������Ϣ�����
	JLabel laCno,laName,laCredit,laWay,laYear,laTerm,laCollege;
	JTextField txtCno,txtName,txtCredit,txtYear,txtCollege;
	JComboBox cmbWay,cmbTerm;
	gsqlCon con=null;
	// ���캯��
	public CourseJFrame(){
		String[] ttt={"����","����"};
		cmbWay=new JComboBox(ttt);
		String[] iii={"1","2","3"};
		cmbTerm=new JComboBox(iii);
		f.setBounds(300, 200, 1100, 700);		// ���ô����С
		f.setTitle("�γ���Ϣ���봰��");		// ���ô�������
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setLayout(null);		
		// �½�����ť���
		btnAdd = new JButton("����");
		btnAdd.setBounds(341, 110, 100,40);
		btnDelete = new JButton("ɾ��");
		btnDelete.setBounds(460,450,66,26);
		btnChange = new JButton("����");
		btnChange.setBounds(540,450,66,26);
		btnCancel = new JButton("�˳�");
		btnCancel.setBounds(620,450,66,26);
		btnShouke= new JButton("ѡ���ڿ���ʦ");
		btnShouke.setBounds(320,450,130,30);
		f.add(btnDelete);
		f.add(btnChange);
		f.add(btnCancel);
		f.add(btnShouke);


		panelUP = new JPanel();		// �½���ť������
	    panelUP.setLayout(null);
		panelUP.setBounds(150,60,800,150);
		panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


		laCno = new JLabel("�γ̺�:");

		laCno.setPreferredSize(new Dimension(0,50));
		laName = new JLabel("�γ���:");

		laCredit = new JLabel("ѧ   ��:");

		laWay = new JLabel("���˷�ʽ:");
		laYear= new JLabel("����ѧ��");
		laTerm= new JLabel("����ѧ��");
		laCollege=new JLabel("����ѧԺ");
		txtCno = new JTextField();
		txtName = new JTextField();
		txtCredit = new JTextField();
		txtYear=new JTextField();
		txtCollege=new JTextField();
		laCno.setBounds(25, 25,40, 25);
		txtCno.setBounds(74, 25, 90, 25);
		laName.setBounds(185, 25, 40, 25);
		txtName.setBounds(236, 25, 90,25);
		laCredit.setBounds(25, 68, 85, 25);
		txtCredit.setBounds(74, 68, 90, 25);
		cmbWay.setBounds(420, 18, 90,25);
		laWay.setBounds(346, 18, 85,25);
		laYear.setBounds(168, 68, 85,25);
		txtYear.setBounds(236, 68, 90,25);
		laTerm.setBounds(346, 68, 85,25);
		cmbTerm.setBounds(420, 68, 85,25);
		laCollege.setBounds(540,25,70,12);
		txtCollege.setBounds(630,18,85,25);

		panelUP.add(btnAdd);
		panelUP.add(laCno);
		panelUP.add(laName);
		panelUP.add(laCredit);
		panelUP.add(txtCno);
		panelUP.add(txtName);
		panelUP.add(txtCredit);
		panelUP.add(laWay);
		panelUP.add(cmbWay);
		panelUP.add(laYear);
		panelUP.add(txtYear);
		panelUP.add(laTerm);
		panelUP.add(cmbTerm);
		panelUP.add(laCollege);
		panelUP.add(txtCollege);



		ResultSet rs = null;
		Vector columnNames = new Vector();
		//��������
		columnNames.add("�γ̺�");
		columnNames.add("�γ���");
		columnNames.add("���˷�ʽ");
		columnNames.add("ѧ��");
		columnNames.add("����ѧ��");
		columnNames.add("����ѧ��");
		columnNames.add("����ѧԺ");
		Vector rowData = new Vector();
		//rowData���Դ�Ŷ���,��ʼ�����ݿ���ȡ
		 try {
			rs= con.gsqlquery(con.GetCon(), "select * from liuz_Course01;");
			if(rs==null) {System.out.print("��ѯ������ ������������");}
		  } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
		  try {
			while(rs.next()){
				//rowData���Դ�Ŷ���
			    System.out.print("���� ������������");
				Vector hang=new Vector();
				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getString(4));
				hang.add(rs.getString(5));
				hang.add(rs.getString(6));
				hang.add(rs.getString(7));
				//���뵽rowData
				rowData.add(hang);
			}
		  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //�½����
		tableModel = new DefaultTableModel(rowData,columnNames);	
		table = new JTable(tableModel) {
			   @Override
			   public boolean isCellEditable(int row, int column) {
                  if(column == 0)
			          return false;
                  else
                	  return true;
			   }
			}; 
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane s = new JScrollPane(table);
		s.setBounds(150, 250, 800, 180);
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
				String sql=null;
				int a;           
	           sql = "insert into liuz_course01 values( '" + txtCno.getText() + "','" + txtName.getText()  +"','" + cmbWay.getSelectedItem()  +"','"
					   + txtCredit.getText() +  "','" + txtYear.getText() +"','" + cmbTerm.getSelectedItem()+ "','" + txtCollege.getText()  +"');";
	           a= con.gsqlexc(con.GetCon(), sql);
	            if(a>0) {
	                 tableModel.addRow(new Object[] {txtCno.getText(), txtName.getText(),cmbWay.getSelectedItem(),txtCredit.getText(),
							 txtYear.getText(),cmbTerm.getSelectedItem(),txtCollege.getText()});
	           	   txtCno.setText("");;
	               txtName.setText("");
	               txtCredit.setText("");
				   txtYear.setText("");
				   txtCollege.setText("");
	               }
			}
		});
		// ɾ��
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// ɾ��ָ����
				int rowcount = table.getSelectedRow();
				int rscount;
				if(rowcount==-1) {	
				   JOptionPane.showMessageDialog(null, "��ѡ������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
				}
				String sql="delete from liuz_course01 where lz_Cono01='" + table.getValueAt(rowcount, 0)+"';";
				rscount=con.gsqlexc(con.GetCon(), sql);
			   if(rscount> 0) {
				   tableModel.removeRow(rowcount);
				}
			}
		});

		btnChange.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				int rowcount = table.getSelectedRow();
				if(rowcount==-1) {	
				    JOptionPane.showMessageDialog(null, "��ѡ������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
				}
				String sql="update liuz_course01 set lz_coname01 ='" + table.getValueAt(rowcount, 1)+
						"',lz_coway01='"+table.getValueAt(rowcount, 2)+
						"',lz_cocredit01='"+table.getValueAt(rowcount, 3)+
						"',lz_coyear01='"+table.getValueAt(rowcount, 4)+
						"',lz_coterm01='"+table.getValueAt(rowcount, 5)+
						"',lz_cno01='"+table.getValueAt(rowcount, 6)+
						"'where lz_cono01 ='"+table.getValueAt(rowcount, 0) +"';";
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
		btnShouke.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowcount = table.getSelectedRow();
				int rscount;
				if(rowcount==-1) {
					JOptionPane.showMessageDialog(null, "��ѡ������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
				}
				new SelectTeacher(table.getValueAt(rowcount, 0).toString());

			}
		});
	}
	// ������
	public static void main(String[] args){
		new CourseJFrame();
	}
}