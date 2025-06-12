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
	DefaultTableModel tableModel;		// 默认显示的表格
	JButton btnAdd,btnDelete,btnCancel,btnChange;		// 各处理按钮
	JTable table;		// 表格
	JFrame f= new JFrame("系别信息输入窗口");
	JPanel panelUP;	//增加信息的面板
	JLabel laCno,laName;
	JTextField txtDno,txtName;
	gsqlCon con=null;
	// 构造函数
	public  DepartJFrame(){
		f.setBounds(300, 200, 642, 462);		// 设置窗体大小
		f.setTitle("系别信息输入窗口");		// 设置窗体名称
		f.setLayout(null);		
		// 新建各按钮组件
		btnAdd = new JButton("插入");
		btnDelete = new JButton("删除");
		btnDelete.setBounds(191,270,66,26);
		btnChange = new JButton("保存");
		btnChange.setBounds(268,270,66,26);
		btnCancel = new JButton("退出");
		btnCancel.setBounds(345,270,66,26);
		f.add(btnDelete);
		f.add(btnChange);
		f.add(btnCancel);
		panelUP = new JPanel();		// 新建按钮组件面板
	    panelUP.setLayout(null);
		panelUP.setBounds(92,30,427,60);
		panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		laCno = new JLabel("系号:");
		laCno.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		laName = new JLabel("系名:");
		laName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
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
		//设置列名
		columnNames.add("系号");
		columnNames.add("系名");
		Vector rowData = new Vector();
		//try {
		//	rs= con.gsqlquery(con.GetCon(), "select * from liuz_college01;");
		//	if(rs==null) {System.out.print("查询无数据 ！！！！！！");}
		//} catch (SQLException e1) {
		//		e1.printStackTrace();
		//}
		//try {
		//    while(rs.next()){
		//		//rowData可以存放多行
		//		Vector hang=new Vector();
		//		hang.add(rs.getString(1));
		//		hang.add(rs.getString(2));
		//		//加入到rowData
		//		rowData.add(hang);
		//	}
		// } catch (SQLException e) {
		//		// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
		// 新建表格
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
		// 将面板和表格分别添加到窗体中
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
				// 增加一行空白区域
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
		// 删除
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// 删除指定行
				int rscount ;
				int rowcount = table.getSelectedRow();
				if(rowcount==-1) {	
				JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
				}
				String sql="delete from Depts where Dno='" + table.getValueAt(rowcount, 0)+"';";
				rscount=con.gsqlexc(con.GetCon(), sql);
				if(rscount>0)
					tableModel.removeRow(rowcount);
			}
		});
		//保存
		btnChange.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				int rowcount = table.getSelectedRow();
				if(rowcount==-1) {	
				JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
				}
				String sql="update Depts set Dname='" + table.getValueAt(rowcount, 1)+"'where Dno ='"+table.getValueAt(rowcount, 0) +"';";
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
		new DepartJFrame();
	}
}
