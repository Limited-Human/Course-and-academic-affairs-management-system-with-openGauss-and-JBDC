package studentdemo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Stu_GetCourse {
    DefaultTableModel tableModel;		// Ĭ����ʾ�ı��
    JButton btnAdd,btnCancel,btnAll;		// ������ť
    JTable table;		// ���
    JFrame f= new JFrame("�γ���Ϣ���봰��");
    JPanel panelUP;	//������Ϣ�����
    JLabel laYear,laTerm;
    JTextField txtYear;
    JComboBox cmbTerm;
    gsqlCon con=null;
    String StuID;
    // ���캯��
    public Stu_GetCourse(String id){
        StuID=id;
        String[] iii={"1","2","3"};
        cmbTerm=new JComboBox(iii);
        f.setBounds(300, 200, 1100, 700);		// ���ô����С
        f.setTitle("ѧ���α��ѯ");		// ���ô�������
        f.setLayout(null);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // �½�����ť���
        btnAdd = new JButton("��ѯ");
        btnAdd.setBounds(341, 110, 100,40);
        btnAll=new JButton("��ѯ����");
        btnCancel = new JButton("�˳�");
        btnAll.setBounds(450,450,150,26);
        btnCancel.setBounds(620,450,66,26);

        f.add(btnCancel);


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
        columnNames.add("�γ���");
        columnNames.add("�ον�ʦ");
        columnNames.add("��ѧʱ��");
        columnNames.add("��ѧ�ص�");
        columnNames.add("���˷�ʽ");
        columnNames.add("ѧ��");
        columnNames.add("����ѧ��+ѧ��");
        Vector rowData = new Vector();
        //rowData���Դ�Ŷ���,��ʼ�����ݿ���ȡ
        try {
            //rs= con.gsqlquery(con.GetCon(), "select * from liuz_Course01 WHERE lz_sno01="+"'"+StuID+"';");
            String sql="SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01 " +
                    "FROM(SELECT * 	FROM(SELECT * FROM (SELECT lz_cono01,lz_tno01 FROM liuz_stu_grade01 WHERE lz_sno01='"+StuID+"') " +
                    "NATURAL JOIN liuz_teach_course01) NATURAL JOIN liuz_course01) NATURAL JOIN liuz_teacher01;";
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
                hang.add(rs.getString(7)+"+"+rs.getString(8));
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
                    return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane s = new JScrollPane(table);
        s.setBounds(150, 250, 800, 180);
        f.add(panelUP);
        f.add(s);
        f.add(btnAll);
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

                // ��ȡ�û������ѧ���ѧ��
                String year = txtYear.getText();
                String term = (String) cmbTerm.getSelectedItem();

                // ����SQL��ѯ���
                String sql="SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01 " +
                        "FROM(SELECT * 	FROM(SELECT * FROM (SELECT lz_cono01,lz_tno01 FROM liuz_stu_grade01 WHERE lz_sno01='"+StuID+"') " +
                        "NATURAL JOIN liuz_teach_course01"+
                        ") NATURAL JOIN liuz_course01  WHERE lz_coyear01 ='" +year+"' AND lz_coterm01 ="+term+") NATURAL JOIN liuz_teacher01;";

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
                            hang.add(rs.getString(7)+"+"+rs.getString(8));

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
        btnAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // ���rowData���Ա������������
                tableModel.setRowCount(0);

                // ��ȡ�û������ѧ���ѧ��
                String year = txtYear.getText();
                String term = (String) cmbTerm.getSelectedItem();

                // ����SQL��ѯ���
                String sql="SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01 " +
                        "FROM(SELECT * 	FROM(SELECT * FROM (SELECT lz_cono01,lz_tno01 FROM liuz_stu_grade01 WHERE lz_sno01='"+StuID+"') " +
                        "NATURAL JOIN liuz_teach_course01) NATURAL JOIN liuz_course01) NATURAL JOIN liuz_teacher01;";
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
                            hang.add(rs.getString(7)+"+"+rs.getString(8));

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
        String id="jike01";
        new Stu_GetCourse(id);
    }
}
