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
			laName = new JLabel("�� �� ��:");
		//	laName.setText("yonghu");
			laName.setFont(new Font("΢���ź�", Font.PLAIN, 13));
			laPwd = new JLabel("��      ��:");
			laPwd.setFont(new Font("΢���ź�", Font.PLAIN, 13));
			txtName = new JTextField();
			txtPwd = new JTextField();
			User=new JLabel("�û�����");
			User.setVisible(true);
			String[] user={"ѧ��","��ʦ","ϵͳ����Ա"};
			userRole=new JComboBox(user);
			userRole.setVisible(true);
			laName.setSize(80,35);
			laPwd.setSize(80,35);
			txtName.setPreferredSize(new Dimension(150, 30));
			txtPwd.setPreferredSize(new Dimension(150, 30));

			btnLogin = new JButton("��¼");

			setJButton(btnLogin);
			btnExit = new JButton("ȡ��");		

			setJButton(btnExit);
			btnLogin.addActionListener(this);
			btnExit.addActionListener(this);
			this.setTitle("�㽭��ҵ��ѧ�������ϵͳ��¼");

			this.add(laName);
			this.add(txtName);
			this.add(laPwd);

			this.add(txtPwd);
			this.add(User);
			this.add(userRole);
			this.add(btnLogin);
			this.add(btnExit);
			this.setTitle("��ӭ��½");
			this.setSize(700,200);
			this.setLocationRelativeTo(null);//������ʾ

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Ĭ�Ϲرշ�ʽ

			this.setVisible(true);
			this.setResizable(true);
		}
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			new LoginJFrame();	
		}
		//���ð�ť���͸�� 
		private void setJButton(JButton btn) {  
			btn.setBackground(new Color(240, 155, 204));// �ۺ�ɫ
			btn.setFont(new Font("Dialog", Font.BOLD, 18));  
			btn.setOpaque(true);  //���ò�͸��
			btn.setBorder(BorderFactory.createEmptyBorder());  
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "ȡ��") {
				this.dispose();
			}
			if(e.getActionCommand() == "��¼") {
				if(!txtName.getText().isEmpty() && !txtPwd.getText().isEmpty()) {
					//�û��������붼�Ѿ���д
					String user=txtName.getText();
					String pass=txtPwd.getText();
					if(userRole.getSelectedItem().equals("��ʦ")){
						System.out.print(pass);
						if(con.Login_Teacher(con.GetCon(),user,pass)) {
							JOptionPane.showMessageDialog(null, "��¼�ɹ�!", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
							new MainTeacher(txtName.getText());
							this.dispose();
						}else{
							JOptionPane.showMessageDialog(null, "�������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
							txtPwd.setText("");
						}
					}
					if(userRole.getSelectedItem().equals("ѧ��")){

						System.out.print(pass);
						if(con.Login_Student(con.GetCon(),user,pass)) {
							JOptionPane.showMessageDialog(null, "��¼�ɹ�!", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
							new MainStudent(txtName.getText());
							this.dispose();
						}else{
							JOptionPane.showMessageDialog(null, "�������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
							txtPwd.setText("");
						}
					}
					if(userRole.getSelectedItem().equals("ϵͳ����Ա")){
						new MainAdmin();
						this.dispose();
					}
				}else if(txtName.getText().isEmpty()&&txtPwd.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "�������û���������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
					clear();
				}else if(txtName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "�������û���!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
					clear();
				}else if(txtPwd.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "����������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
					clear();
				}else {
					JOptionPane.showMessageDialog(null, "�û��������������\n����������","��ʾ��Ϣ",JOptionPane.ERROR_MESSAGE);
					clear();
				}
			}
		}
		private void clear() {
			txtName.setText("");
			txtPwd.setText("");
		}
	}