package studentdemo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Teacher_ClassMember {
    DefaultTableModel tableModel;		// Ĭ����ʾ�ı��
    JButton btnAdd,btnCancel,btnConfirm,btnAll;		// ������ť
    JTable table;		// ���
    JFrame f= new JFrame("�γ���Ϣ���봰��");
    JPanel panelUP;	//������Ϣ�����
    JLabel laYear,laTerm;
    JTextField txtYear;
    JComboBox cmbTerm;
    gsqlCon con=null;
    String TeaID;
    // ���캯��
    public Teacher_ClassMember(String id){
        TeaID= id;
        String[] iii={"1","2","3"};
        cmbTerm=new JComboBox(iii);
        f.setBounds(300, 200, 1100, 700);		// ���ô����С
        f.setTitle("�༶��Ա��Ϣ����");		// ���ô�������
        f.setLayout(null);
        // �½�����ť���
        btnAdd = new JButton("��ѯ");
        btnAdd.setBounds(341, 110, 100,40);
        btnConfirm = new JButton("ȷ������");
        btnConfirm.setBounds(280, 450, 100,40);
        btnAll=new JButton("���ò�ѯ");
        btnAll.setBounds(450,450,150,26);
        btnCancel = new JButton("�˳�");
        btnCancel.setBounds(620,450,100,40);

        f.add(btnCancel);
        f.add(btnConfirm);
        f.add(btnAll);

        panelUP = new JPanel();		// �½���ť������
        panelUP.setLayout(null);
        panelUP.setBounds(150,60,800,150);
        panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


        laYear= new JLabel("����");
        laTerm= new JLabel("����ѧ��");

        txtYear=new JTextField();

        laYear.setBounds(168, 68, 85,25);
        txtYear.setBounds(236, 68, 90,25);
        laTerm.setBounds(346, 68, 85,25);
        cmbTerm.setBounds(420, 68, 85,25);


        panelUP.add(btnAdd);

        panelUP.add(laYear);
        panelUP.add(txtYear);
        //panelUP.add(laTerm);
        //panelUP.add(cmbTerm);




        ResultSet rs = null;
        Vector columnNames = new Vector();
        //��������
        columnNames.add("ѧ��");
        columnNames.add("����");
        columnNames.add("�Ա�");
        columnNames.add("����");
        columnNames.add("��ַ");
        columnNames.add("����ѧ��");
        columnNames.add("�༶���");

        Vector rowData = new Vector();
        //rowData���Դ�Ŷ���,��ʼ�����ݿ���ȡ
        try {
            String sql="SELECT * FROM liuz_student01 WHERE lz_clno01 IN( SELECT lz_clno01 FROM liuz_class01 " +
                    "WHERE lz_tno01='"+TeaID+"') ORDER BY lz_clno01;";
            rs= con.gsqlquery(con.GetCon(), sql);
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
                if(column == 4)
                    return true;
                else
                    return false;
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
                // ���rowData���Ա������������
                tableModel.setRowCount(0);

                // ��ȡ�û������ѧ���ѧ��
                String year = txtYear.getText();
                String term = (String) cmbTerm.getSelectedItem();
                sql="SELECT * FROM liuz_student01 WHERE lz_clno01 IN( SELECT lz_clno01 FROM liuz_class01 " +
                        "WHERE lz_tno01='"+TeaID+"') and lz_sname01 = '"+txtYear.getText()+"' ORDER BY lz_clno01;";
                // ִ��SQL��ѯ
                ResultSet rs = null;
                try {
                    rs = con.gsqlquery(con.GetCon(), sql);
                    if (rs != null) {
                        while (rs.next()) {
                            // �ӽ�����л�ȡ���ݣ�����ӵ�rowData
                            Vector<Object> hang = new Vector<>();
                            hang.add(rs.getString(1));
                            hang.add(rs.getString(2));
                            hang.add(rs.getString(3));
                            hang.add(rs.getString(4));
                            hang.add(rs.getString(5));
                            hang.add(rs.getString(6));
                            hang.add(rs.getString(7));

                            // ��������ӵ����ģ��
                            tableModel.addRow(hang.toArray());
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });
        btnConfirm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int rowcount = table.getSelectedRow();
                if(rowcount==-1) {
                    JOptionPane.showMessageDialog(null, "��ѡ�пγ�!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
                }
                String sql="update liuz_stu_grade01 set lz_score01="+table.getValueAt(rowcount, 4)+" where lz_cono01='"+
                        table.getValueAt(rowcount, 2)+"' and lz_sno01='"+table.getValueAt(rowcount, 0)+"';";

                con.gsqlexc(con.GetCon(), sql);
            }
        });
        //���ò�ѯ
        btnAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // ���rowData���Ա������������
                tableModel.setRowCount(0);
                // ����SQL��ѯ���
                String sql="SELECT * FROM liuz_student01 WHERE lz_clno01 IN( SELECT lz_clno01 FROM liuz_class01 " +
                        "WHERE lz_tno01='"+TeaID+"') ORDER BY lz_clno01;";
                // ִ��SQL��ѯ
                ResultSet rs = null;
                try {
                    rs = con.gsqlquery(con.GetCon(), sql);
                    if (rs != null) {
                        while (rs.next()) {
                            // �ӽ�����л�ȡ���ݣ�����ӵ�rowData
                            Vector<Object> hang = new Vector<>();
                            hang.add(rs.getString(1));
                            hang.add(rs.getString(2));
                            hang.add(rs.getString(3));
                            hang.add(rs.getString(4));
                            hang.add(rs.getString(5));
                            hang.add(rs.getString(6));
                            hang.add(rs.getString(7));

                            // ��������ӵ����ģ��
                            tableModel.addRow(hang.toArray());
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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
        new Teacher_ClassMember("t01");
    }
}
