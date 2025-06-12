package studentdemo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainStudent implements ActionListener {
    JMenu file_, xinxi,xuanke,pingjia;
    JMenuItem exit_;
    JMenuItem geren,kebiao,chengji,course,guochengpingjia,yixiuxuefen;
    JButton btnCancel,btnChange;

    gsqlCon con =null;
    String StuID;
    MainStudent(String id){
        StuID=id;
        Font menuFont = new Font("SansSerif", Font.PLAIN, 20);
        JFrame f= new JFrame("�������ϵͳ");
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        btnChange = new JButton("����");
        btnChange.setBounds(250,130,80,40);
        btnCancel = new JButton("�˳�");
        btnCancel.setBounds(350,130,80,40);
        //�����˵���
        JMenuBar mb=new JMenuBar();
        //�����˵�
        file_=new JMenu("�ļ�");
        xinxi=new JMenu("��Ϣ��ѯ");
        xuanke=new JMenu("ѡ��");
        pingjia=new JMenu("��ѧ����");

        //�����˵���
        exit_=new JMenuItem("�˳�");
        exit_.addActionListener(this);
        //��Ϣ��ѯ
        geren=new JMenuItem("������Ϣ��ѯ");
        geren.addActionListener(this);
        kebiao=new JMenuItem("�γ̱�鿴");
        kebiao.addActionListener(this);
        chengji=new JMenuItem("�ɼ���ѯ");
        chengji.addActionListener(this);
        yixiuxuefen=new JMenuItem("����ѧ��");
        yixiuxuefen.addActionListener(this);
        //ѡ��
        course=new JMenuItem("ѡ��");
        course.addActionListener(this);
        //
        guochengpingjia=new JMenuItem("��������");
        guochengpingjia.addActionListener(this);

        file_.setFont(menuFont);
        xinxi.setFont(menuFont);
        xuanke.setFont(menuFont);
        pingjia.setFont(menuFont);

        exit_.setFont(menuFont);
        geren.setFont(menuFont);
        kebiao.setFont(menuFont);
        chengji.setFont(menuFont);
        course.setFont(menuFont);
        guochengpingjia.setFont(menuFont);

        file_.add(exit_);
        xinxi.add(geren);
        xinxi.add(kebiao);
        xinxi.add(chengji);

        xuanke.add(course);
        pingjia.add(guochengpingjia);

        mb.add(file_);
        mb.add(xinxi);
        mb.add(xuanke);
        mb.add(pingjia);



        Vector columnNames = new Vector();
        //��������
        columnNames.add("ѧ��");
        columnNames.add("����");
        columnNames.add("�Ա�");
        columnNames.add("����");
        columnNames.add("סַ");
        columnNames.add("����ѧ��");
        columnNames.add("�༶���");
        Vector rowData = new Vector();
        ResultSet rs=null;
        try {
            rs= con.gsqlquery(con.GetCon(), "SELECT * FROM liuz_student01 WHERE lz_sno01="+"'"+StuID+"';");
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
        // �½����
        DefaultTableModel tableModel = new DefaultTableModel(rowData,columnNames);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 4)
                    return true;
                else
                    return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //table.setFont(menuFont);
        JScrollPane s = new JScrollPane(table);
        s.setBounds(20, 20, 800, 80);

        btnChange.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowcount = table.getSelectedRow();
                int rscount;
                if(rowcount==-1) {
                    JOptionPane.showMessageDialog(null, "��ѡ������!","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);
                }

                String sql="update liuz_student01 set lz_sno01='" + table.getValueAt(rowcount, 0)+"',lz_sname01='"+ table.getValueAt(rowcount, 1) +"'," +
                        "lz_ssex01='"+table.getValueAt(rowcount, 2)+"'," +"lz_sage01='"+table.getValueAt(rowcount, 3)+"'," +"lz_saddress01='"+table.getValueAt(rowcount, 4)+"'," +"lz_credit_sum01='"+table.getValueAt(rowcount, 5)+
                        "'," +"lz_clno01='"+table.getValueAt(rowcount, 6)+"'where lz_sno01 ='"+table.getValueAt(rowcount, 0) +"';";

                con.gsqlexc(con.GetCon(), sql);


            }
        });
        // �˳�
        btnCancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                f.dispose();
                new LoginJFrame();
            }
        });

        f.add(s);
        f.setJMenuBar(mb);
        f.add(btnCancel);
        f.add(btnChange);
        f.setSize(850, 462);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
    }
    public static void main(String args[])
    {
        String id = "jike01";
        new MainStudent(id);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(exit_))
            System.exit(0);

        if(e.getSource().equals(kebiao))
            new Stu_GetCourse(StuID);
        if(e.getSource().equals(chengji))
            new Stu_GetGrade(StuID);
        if(e.getSource().equals(course))
            new Stu_xuanke(StuID);
        if(e.getSource().equals(guochengpingjia))
            new Stu_pingjia(StuID);

    }
}
