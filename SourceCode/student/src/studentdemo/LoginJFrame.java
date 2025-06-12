package studentdemo;	
import java.awt.*;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;

public class LoginJFrame extends JFrame  implements ActionListener{

		JLabel laName,laPwd,User;
		JTextField txtName;
		JTextField txtPwd;
		JComboBox userRole;
		JButton btnLogin,btnExit;
		gsqlCon con=null;
		public LoginJFrame() {

			this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
			laName = new JLabel("用 户 名:");
		//	laName.setText("yonghu");
			laName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
			laPwd = new JLabel("密      码:");
			laPwd.setFont(new Font("微软雅黑", Font.PLAIN, 13));
			txtName = new JTextField();
			txtPwd = new JTextField();
			User=new JLabel("用户类型");
			User.setVisible(true);
			String[] user={"学生","教师","系统管理员"};
			userRole=new JComboBox(user);
			userRole.setVisible(true);
			laName.setSize(80,35);
			laPwd.setSize(80,35);
			txtName.setPreferredSize(new Dimension(150, 30));
			txtPwd.setPreferredSize(new Dimension(150, 30));

			btnLogin = new JButton("登录");

			setJButton(btnLogin);
			btnExit = new JButton("取消");		

			setJButton(btnExit);
			btnLogin.addActionListener(this);
			btnExit.addActionListener(this);
			this.setTitle("浙江工业大学教务管理系统登录");

			this.add(laName);
			this.add(txtName);
			this.add(laPwd);

			this.add(txtPwd);
			this.add(User);
			this.add(userRole);
			this.add(btnLogin);
			this.add(btnExit);
			this.setTitle("欢迎登陆");
			this.setSize(700,200);
			this.setLocationRelativeTo(null);//居中显示

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭方式

			this.setVisible(true);
			this.setResizable(true);
		}
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			new LoginJFrame();	
		}
		//设置按钮风格：透明 
		private void setJButton(JButton btn) {  
			btn.setBackground(new Color(240, 155, 204));// 粉红色
			btn.setFont(new Font("Dialog", Font.BOLD, 18));  
			btn.setOpaque(true);  //设置不透明
			btn.setBorder(BorderFactory.createEmptyBorder());  
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "取消") {
				this.dispose();
			}
			if(e.getActionCommand() == "登录") {
				if(!txtName.getText().isEmpty() && !txtPwd.getText().isEmpty()) {
					//用户名和密码都已经填写
					String user=txtName.getText();
					String pass=txtPwd.getText();
					if(userRole.getSelectedItem().equals("教师")){
						System.out.print(pass);
						if(con.Login_Teacher(con.GetCon(),user,pass)) {
							JOptionPane.showMessageDialog(null, "登录成功!", "提示消息", JOptionPane.WARNING_MESSAGE);
							new MainTeacher(txtName.getText());
							this.dispose();
						}else{
							JOptionPane.showMessageDialog(null, "密码错误!","提示消息",JOptionPane.WARNING_MESSAGE);
							txtPwd.setText("");
						}
					}
					if(userRole.getSelectedItem().equals("学生")){

						System.out.print(pass);
						if(con.Login_Student(con.GetCon(),user,pass)) {
							JOptionPane.showMessageDialog(null, "登录成功!", "提示消息", JOptionPane.WARNING_MESSAGE);
							new MainStudent(txtName.getText());
							this.dispose();
						}else{
							JOptionPane.showMessageDialog(null, "密码错误!","提示消息",JOptionPane.WARNING_MESSAGE);
							txtPwd.setText("");
						}
					}
					if(userRole.getSelectedItem().equals("系统管理员")){
						new MainAdmin();
						this.dispose();
					}
				}else if(txtName.getText().isEmpty()&&txtPwd.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入用户名和密码!","提示消息",JOptionPane.WARNING_MESSAGE);
					clear();
				}else if(txtName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入用户名!","提示消息",JOptionPane.WARNING_MESSAGE);
					clear();
				}else if(txtPwd.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入密码!","提示消息",JOptionPane.WARNING_MESSAGE);
					clear();
				}else {
					JOptionPane.showMessageDialog(null, "用户名或者密码错误！\n请重新输入","提示消息",JOptionPane.ERROR_MESSAGE);
					clear();
				}
			}
		}
		private void clear() {
			txtName.setText("");
			txtPwd.setText("");
		}
	}