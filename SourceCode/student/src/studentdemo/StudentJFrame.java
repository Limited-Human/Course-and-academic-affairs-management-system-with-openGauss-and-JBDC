package studentdemo;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class StudentJFrame implements FocusListener{
	DefaultTableModel tableModel;		// 默认显示的表格
	JButton btnAdd,btnDelete,btnCancel,btnChange;		// 各处理按钮
	JTable table;		// 表格
	JFrame f= new JFrame("系别信息输入窗口");
	JPanel panelUP;	//增加信息的面板
	JLabel laSno,laName,laClass,laAge,laAddress,laSex,laxuefen;
	JTextField txtSno,txtName,txtAddress,txtxuefen;
	JComboBox<depart> cmbDept;
	JComboBox cmbSex;
	JComboBox<Integer> cmbAge;
	gsqlCon con=new gsqlCon();
	public StudentJFrame(){
		f.setBounds(300, 200, 1100, 700);		// 设置窗体大小
		f.setTitle("学生信息输入窗口");		// 设置窗体名称
		f.setLayout(null);		
		// 新建各按钮组件
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		btnAdd = new JButton("插入");
		btnAdd.setBounds(341,110,100,40);
		btnDelete = new JButton("删除");
		btnDelete.setBounds(460,450,66,26);
		btnChange = new JButton("保存");
		btnChange.setBounds(540,450,66,26);
		btnCancel = new JButton("退出");
		btnCancel.setBounds(620,450,66,26);
		f.add(btnDelete);
		f.add(btnChange);
		f.add(btnCancel);



		panelUP = new JPanel();		// 新建按钮组件面板
	    panelUP.setLayout(null);
		panelUP.setBounds(150,60,800,150);
		panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


		laSno = new JLabel("学号:");
		laSno.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		laName = new JLabel("姓名:");
		laName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		laClass = new JLabel("系别:");
		laClass.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		laAge = new JLabel("年龄:");
		laAge.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		laSex = new JLabel("性别:");
		laSex.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		laAddress = new JLabel("地址:");
		laAddress.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		laxuefen = new JLabel("已修学分");

		List<Integer> ageList = new ArrayList<>();
		for (int i = 10; i <= 50; i++) {
			ageList.add(i);
		}
		String[] ssss=new String[]{"男","女"};
		cmbSex=new JComboBox(ssss);
		cmbAge = new JComboBox<>(ageList.toArray(new Integer[0]));
		cmbDept=new JComboBox<>();
	    ResultSet dp;
		try {
			dp = con.gsqlclass(con.GetCon());
			while(dp.next()){
				 cmbDept.addItem(new depart(dp.getString(1),dp.getString(2)));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		txtSno = new JTextField();
		txtName = new JTextField();
		txtxuefen=new JTextField();
		txtAddress = new JTextField();

		laSno.setBounds(25, 25,40, 12);
		txtSno.setBounds(68, 18, 90, 25);
		laName.setBounds(168, 25, 40, 12);
		txtName.setBounds(236, 18, 90,25);
		laClass.setBounds(25, 68, 85, 25);
		cmbDept.setBounds(68, 68, 90, 25);
		laAge.setBounds(168, 68, 85,25);
		laSex.setBounds(346, 68, 85,25);
		cmbSex.setBounds(420, 68, 85,25);
		cmbAge.setBounds(236, 68, 90,25);
		laAddress.setBounds(346, 18, 85,25);
		txtAddress.setBounds(420, 18, 90,25);
		laxuefen.setBounds(540,25,70,12);
		txtxuefen.setBounds(630,18,85,25);

		panelUP.add(btnAdd);
		panelUP.add(laSno);
		panelUP.add(txtSno);
		panelUP.add(laName);
		panelUP.add(txtName);
		panelUP.add(laClass);
		panelUP.add(cmbDept);
		panelUP.add(laAge);
		panelUP.add(laSex);
		panelUP.add(cmbSex);
		panelUP.add(laAddress);
		panelUP.add(cmbAge);
		panelUP.add(laxuefen);
		panelUP.add(txtxuefen);
		panelUP.add(txtAddress);
		ResultSet rs = null;
		Vector columnNames = new Vector();
			//设置列名
		columnNames.add("学号");
		columnNames.add("姓名");
		columnNames.add("性别");
		columnNames.add("年龄");
		columnNames.add("住址");
		columnNames.add("已修学分");
		columnNames.add("班级编号");
		Vector rowData = new Vector();
		//rowData可以存放多行,开始从数据库里取
		try {
			rs= con.gsqlquery(con.GetCon(), "SELECT * FROM liuz_student01;");
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
		// 新建表格
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
				depart de=(depart)cmbDept.getSelectedItem();
				//
	            sql = "insert into liuz_student01 values( '" + txtSno.getText() + "','" + txtName.getText() +"','"+cmbSex.getSelectedItem()+"','" + cmbAge.getSelectedItem()+"','" + txtAddress.getText()+ "','"+ txtxuefen.getText() +"','" + de.toNber()+ "');";
	            a= con.gsqlexc(con.GetCon(), sql);
	            if(a>0) {
	               tableModel.addRow(new Object[] {txtSno.getText(), txtName.getText(),cmbSex.getSelectedItem(),cmbAge.getSelectedItem(),txtAddress.getText(),de.toString(),txtxuefen.getText()});
	           	   txtSno.setText("");
				   txtName.setText("");

				   cmbDept.setDefaultLocale(null);
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
				String sql="delete from liuz_student01 where lz_sno01 ='" + table.getValueAt(rowcount, 0)+"';";
				rscount=con.gsqlexc(con.GetCon(), sql);
			  if(rscount>0) 
				  tableModel.removeRow(rowcount);
			}
		});
		btnChange.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {	
				int rowcount = table.getSelectedRow();
				int rscount;
				if(rowcount==-1) {	
				  JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
				}

					String sql="update liuz_student01 set lz_sno01='" + table.getValueAt(rowcount, 0)+"',lz_sname01='"+ table.getValueAt(rowcount, 1) +"'," +
							   "lz_ssex01='"+table.getValueAt(rowcount, 2)+"'," +"lz_sage01='"+table.getValueAt(rowcount, 3)+"'," +"lz_saddress01='"+table.getValueAt(rowcount, 4)+"'," +"lz_credit_sum01='"+table.getValueAt(rowcount, 5)+
							   "'," +"lz_clno01='"+table.getValueAt(rowcount, 6)+"'where lz_sno01 ='"+table.getValueAt(rowcount, 0) +"';";

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
	}
	// 主函数
	public static void main(String[] args){
		new StudentJFrame();
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

		cmbAge.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		cmbAge.setForeground(Color.BLACK);
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
}
