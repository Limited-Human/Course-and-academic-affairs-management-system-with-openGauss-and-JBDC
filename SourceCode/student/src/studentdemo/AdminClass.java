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
public class AdminClass {
    DefaultTableModel tableModel;		// Ĭ����ʾ�ı��
    JButton btnAdd,btnDelete,btnCancel,btnChange;		// ������ť
    JTable table;		// ���
    JFrame f= new JFrame("�γ���Ϣ���봰��");
    JPanel panelUP;	//������Ϣ�����
    JLabel laCno,laName,laWay,laCollege,laBanzhuren;
    JTextField txtCno,txtName,txtRenshu,txtBanzhuren;

    JComboBox<depart> cmbxueyuan;
    gsqlCon con=null;
    // ���캯��
    public AdminClass(){


        f.setBounds(300, 200, 1100, 700);		// ���ô����С
        f.setTitle("�༶������");		// ���ô�������
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
        f.add(btnDelete);
        f.add(btnChange);
        f.add(btnCancel);


        panelUP = new JPanel();		// �½���ť������
        panelUP.setLayout(null);
        panelUP.setBounds(150,60,800,150);
        panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


        laCno = new JLabel("�༶���:");
        laBanzhuren=new JLabel("�����α��:");

        laCno.setPreferredSize(new Dimension(0,50));
        laName = new JLabel("�༶����:");



        laWay = new JLabel("����רҵ:");

        laCollege=new JLabel("����");
        txtCno = new JTextField();
        txtName = new JTextField();
        cmbxueyuan=new JComboBox<depart>();
        txtRenshu=new JTextField();
        txtBanzhuren=new JTextField();
        laCno.setBounds(10, 25,60, 25);
        txtCno.setBounds(74, 25, 90, 25);
        laName.setBounds(170, 25, 60, 25);
        txtName.setBounds(236, 25, 90,25);
        cmbxueyuan.setBounds(420, 18, 90,25);
        laWay.setBounds(346, 18, 85,25);
        laCollege.setBounds(540,25,70,12);
        txtRenshu.setBounds(630,18,85,25);
        txtBanzhuren.setBounds(68, 68, 90, 25);
        laBanzhuren.setBounds(5, 68, 85, 25);
        panelUP.add(btnAdd);
        panelUP.add(laCno);
        panelUP.add(laName);
        panelUP.add(txtCno);
        panelUP.add(txtName);
        panelUP.add(laWay);
        panelUP.add(laCollege);
        panelUP.add(cmbxueyuan);
        panelUP.add(txtRenshu);
        panelUP.add(laBanzhuren);
        panelUP.add(txtBanzhuren);

        ResultSet dp;
        try {
            String sql="SELECT * FROM liuz_major01;";
            dp = con.gsqlquery(con.GetCon(),sql);
            while(dp.next()){
                cmbxueyuan.addItem(new depart(dp.getString(1),dp.getString(2)));
            }
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        ResultSet rs = null;
        Vector columnNames = new Vector();
        //��������
        columnNames.add("�༶���");
        columnNames.add("�༶����");
        columnNames.add("�����α��");
        columnNames.add("�༶����");
        columnNames.add("����רҵ");
        Vector rowData = new Vector();
        //rowData���Դ�Ŷ���,��ʼ�����ݿ���ȡ
        try {
            rs= con.gsqlquery(con.GetCon(), "select * from liuz_class01;");
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
                depart de=(depart) cmbxueyuan.getSelectedItem();
                sql = "insert into liuz_class01 values( '" + txtCno.getText() + "','" + txtName.getText()  +"','" + txtBanzhuren.getText()  +"','"+ txtRenshu.getText()  +"','"
                        + de.toNber()+ "');";
                a= con.gsqlexc(con.GetCon(), sql);
                if(a>0) {
                    tableModel.addRow(new Object[] {txtCno.getText(), txtName.getText(),txtBanzhuren.getText(),txtRenshu.getText(),de.toNber()
                    });
                    txtCno.setText("");;
                    txtName.setText("");
                    txtRenshu.setText("");

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
                String sql="delete from liuz_class01 where lz_clno01='" + table.getValueAt(rowcount, 0)+"';";
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
                String sql="update liuz_class01 set lz_clname01 ='" + table.getValueAt(rowcount, 1)+
                        "',lz_tno01='"+table.getValueAt(rowcount, 2)+
                        "',lz_stucount01="+table.getValueAt(rowcount, 3)+
                        ",lz_mno01='"+table.getValueAt(rowcount, 4)+
                        "'where lz_clno01 ='"+table.getValueAt(rowcount, 0) +"';";
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
        new AdminClass();
    }
}