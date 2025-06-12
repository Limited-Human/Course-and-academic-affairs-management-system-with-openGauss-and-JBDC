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
	DefaultTableModel tableModel;		// 默认显示的表格
	JButton btnAdd,btnDelete,btnCancel,btnChange,btnShouke;		// 各处理按钮
	JTable table;		// 表格
	JFrame f= new JFrame("课程信息输入窗口");
	JPanel panelUP;	//增加信息的面板
	JLabel laCno,laName,laCredit,laWay,laYear,laTerm,laCollege;
	JTextField txtCno,txtName,txtCredit,txtYear,txtCollege;
	JComboBox cmbWay,cmbTerm;
	gsqlCon con=null;
	// 构造函数
	public CourseJFrame(){
		String[] ttt={"考察","考试"};
		cmbWay=new JComboBox(ttt);
		String[] iii={"1","2","3"};
		cmbTerm=new JComboBox(iii);
		f.setBounds(300, 200, 1100, 700);		// 设置窗体大小
		f.setTitle("课程信息输入窗口");		// 设置窗体名称
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setLayout(null);		
		// 新建各按钮组件
		btnAdd = new JButton("插入");
		btnAdd.setBounds(341, 110, 100,40);
		btnDelete = new JButton("删除");
		btnDelete.setBounds(460,450,66,26);
		btnChange = new JButton("保存");
		btnChange.setBounds(540,450,66,26);
		btnCancel = new JButton("退出");
		btnCancel.setBounds(620,450,66,26);
		btnShouke= new JButton("选择授课老师");
		btnShouke.setBounds(320,450,130,30);
		f.add(btnDelete);
		f.add(btnChange);
		f.add(btnCancel);
		f.add(btnShouke);


		panelUP = new JPanel();		// 新建按钮组件面板
	    panelUP.setLayout(null);
		panelUP.setBounds(150,60,800,150);
		panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


		laCno = new JLabel("课程号:");

		laCno.setPreferredSize(new Dimension(0,50));
		laName = new JLabel("课程名:");

		laCredit = new JLabel("学   分:");

		laWay = new JLabel("考核方式:");
		laYear= new JLabel("开设学年");
		laTerm= new JLabel("开设学期");
		laCollege=new JLabel("所属学院");
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
		//设置列名
		columnNames.add("课程号");
		columnNames.add("课程名");
		columnNames.add("考核方式");
		columnNames.add("学分");
		columnNames.add("开设学年");
		columnNames.add("开设学期");
		columnNames.add("所属学院");
		Vector rowData = new Vector();
		//rowData可以存放多行,开始从数据库里取
		 try {
			rs= con.gsqlquery(con.GetCon(), "select * from liuz_Course01;");
			if(rs==null) {System.out.print("查询无数据 ！！！！！！");}
		  } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
		  try {
			while(rs.next()){
				//rowData可以存放多行
			    System.out.print("数据 ！！！！！！");
				Vector hang=new Vector();
				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getString(4));
				hang.add(rs.getString(5));
				hang.add(rs.getString(6));
				hang.add(rs.getString(7));
				//加入到rowData
				rowData.add(hang);
			}
		  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //新建表格
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
		// 事件处理
		MyEvent();
		f.setVisible(true);		// 显示窗体
	}
	// 事件处理
	public void MyEvent(){
		// 增加
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
		// 删除
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// 删除指定行
				int rowcount = table.getSelectedRow();
				int rscount;
				if(rowcount==-1) {	
				   JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
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
				    JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
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
		// 退出
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
					JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
				}
				new SelectTeacher(table.getValueAt(rowcount, 0).toString());

			}
		});
	}
	// 主函数
	public static void main(String[] args){
		new CourseJFrame();
	}
}