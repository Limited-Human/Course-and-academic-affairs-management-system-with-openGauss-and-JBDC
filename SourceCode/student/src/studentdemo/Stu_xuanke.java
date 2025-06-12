package studentdemo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Stu_xuanke {
    DefaultTableModel tableModel;		// Ĭ����ʾ�ı��
    JButton btnAdd,btnCancel,btnxuanke;		// ������ť
    JTable table;		// ���
    JFrame f= new JFrame("�γ���Ϣ���봰��");
    JPanel panelUP;	//������Ϣ�����
    JLabel laYear,laTerm;
    JTextField txtYear;
    JComboBox cmbTerm;
    gsqlCon con=null;
    String StuID;
    // ���캯��
    public Stu_xuanke(String id){
        StuID=id;
        String[] iii={"1","2","3"};
        cmbTerm=new JComboBox(iii);
        f.setBounds(300, 200, 1100, 700);		// ���ô����С
        f.setTitle("ѧ��ѡ��");		// ���ô�������
        f.setLayout(null);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // �½�����ť���
        btnAdd = new JButton("��ѯ");
        btnAdd.setBounds(341, 110, 100,40);
        btnxuanke = new JButton("ѡ��");
        btnxuanke.setBounds(280, 450, 100,40);
        btnCancel = new JButton("�˳�");
        btnCancel.setBounds(620,450,100,40);

        f.add(btnCancel);
        f.add(btnxuanke);

        panelUP = new JPanel();		// �½���ť������
        panelUP.setLayout(null);
        panelUP.setBounds(150,60,800,150);
        panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));





        laYear= new JLabel("����ѧ��");
        laTerm= new JLabel("����ѧ��");

        txtYear=new JTextField();

        laYear.setBounds(168, 68, 85,25);
        txtYear.setBounds(236, 68, 90,25);
        laTerm.setBounds(346, 68, 85,25);
        cmbTerm.setBounds(420, 68, 85,25);


        panelUP.add(btnAdd);

        panelUP.add(laYear);
        panelUP.add(txtYear);
        panelUP.add(laTerm);
        panelUP.add(cmbTerm);




        ResultSet rs = null;
        Vector columnNames = new Vector();
        //��������
        columnNames.add("�γ̱��");
        columnNames.add("��ʦ���");
        columnNames.add("��ʦ����");
        columnNames.add("��ѧʱ��");
        columnNames.add("��ѧ�ص�");

        Vector rowData = new Vector();
        //rowData���Դ�Ŷ���,��ʼ�����ݿ���ȡ
        try {
            String sql="SELECT lz_cono01,lz_tno01,lz_tname01,lz_teach_time01,lz_teach_place01 FROM  liuz_teach_course01 NATURAL JOIN liuz_teacher01 WHERE lz_cono01 NOT IN (" +
                    "SELECT lz_cono01 FROM liuz_stu_grade01	WHERE lz_sno01 = '"+StuID+"');";
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
                // ���rowData���Ա������������
                tableModel.setRowCount(0);
                String sql=null;
                String year=txtYear.getText();
                String term=cmbTerm.getSelectedItem().toString();
                sql = "SELECT lz_cono01,lz_tno01,lz_tname01,lz_teach_time01,lz_teach_place01 FROM " +
                        " liuz_teach_course01 NATURAL JOIN liuz_teacher01 NATURAL JOIN liuz_course01 " +
                        " WHERE lz_cono01 NOT IN ( " +
                        " SELECT lz_cono01 FROM liuz_stu_grade01" +
                        " WHERE lz_sno01 = '"+StuID+"') AND lz_coyear01='"+year+"' AND lz_coterm01="+term+";";
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

                            // ��������ӵ����ģ��
                            tableModel.addRow(hang.toArray());
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //ѡ��
        btnxuanke.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int rowcount = table.getSelectedRow();
                if(rowcount==-1) {
                    JOptionPane.showMessageDialog(null, "��ѡ�пγ�!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
                }
               // String sql="insert into liuz_stu_grade01 values ('" +StuID+"','"+table.getValueAt(rowcount, 0)+"',null,null,'"+table.getValueAt(rowcount, 1)+"');";
                con.get_course(con.GetCon(),StuID,table.getValueAt(rowcount, 0).toString(),table.getValueAt(rowcount,1).toString());
                // con.gsqlexc(con.GetCon(), sql);
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
        new Stu_xuanke("jike01");
    }
}
