package studentdemo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class MainTeacher implements ActionListener {
    JMenu file_, xinxi,xuanke,pingjia;
    JMenuItem exit_;
    JMenuItem geren,kebiao,chengji,course,guochengpingjia,yixiuxuefen;
    JButton btnCancel,btnChange;

    gsqlCon con =null;
    String TeaID;
    MainTeacher(String id){
        TeaID=id;
        Font menuFont = new Font("SansSerif", Font.PLAIN, 20);
        JFrame f= new JFrame("教务管理系统");
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        btnChange = new JButton("保存");
        btnChange.setBounds(200,120,66,26);
        btnCancel = new JButton("退出");
        btnCancel.setBounds(280,120,66,26);
        //创建菜单栏
        JMenuBar mb=new JMenuBar();
        //创建菜单
        file_=new JMenu("文件");
        xinxi=new JMenu("班级管理");

        xuanke=new JMenu("课程");
        pingjia=new JMenu("学院管理");

        //创建菜单条
        exit_=new JMenuItem("退出");
        exit_.addActionListener(this);
        //信息查询
        geren=new JMenuItem("个人信息查询");
        geren.addActionListener(this);
        kebiao=new JMenuItem("班级成员信息查询");
        kebiao.addActionListener(this);
        chengji=new JMenuItem("班级成员成绩查询");
        chengji.addActionListener(this);
        yixiuxuefen=new JMenuItem("学院成员查询");
        yixiuxuefen.addActionListener(this);
        //选课
        course=new JMenuItem("课程打分");
        course.addActionListener(this);

        guochengpingjia=new JMenuItem("学院成绩查询");
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
        yixiuxuefen.setFont(menuFont);

        file_.add(exit_);
        xinxi.add(geren);
        xinxi.add(kebiao);
        xinxi.add(chengji);

        xuanke.add(course);
        pingjia.add(guochengpingjia);
        pingjia.add(yixiuxuefen);

        mb.add(file_);
        mb.add(xinxi);
        mb.add(xuanke);
        mb.add(pingjia);




        Vector columnNames = new Vector();
        //设置列名
        columnNames.add("教师编号");
        columnNames.add("姓名");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("职称");
        columnNames.add("电话号码");
        columnNames.add("学院编号");
        Vector rowData = new Vector();
        ResultSet rs=null,rs2=null;
        try {
            rs= con.gsqlquery(con.GetCon(), "SELECT * FROM liuz_teacher01 WHERE lz_tno01="+"'"+TeaID+"';");
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
        DefaultTableModel tableModel = new DefaultTableModel(rowData,columnNames);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 5)
                    return true;
                else
                    return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(menuFont);
        JScrollPane s = new JScrollPane(table);
        s.setBounds(20, 20, 800, 80);

        btnChange.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int rowcount = table.getSelectedRow();
                int rscount;
                if(rowcount==-1) {
                    JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
                }

                String sql="update liuz_teacher01 set lz_tphone01="+table.getValueAt(rowcount, 5)+" where lz_tno01='"+
                        table.getValueAt(rowcount, 0)+"';";
                con.gsqlexc(con.GetCon(), sql);


            }
        });
        // 退出
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
        String id = "t01";
        new MainTeacher(id);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(exit_))
            System.exit(0);

        if(e.getSource().equals(kebiao))
            new Teacher_ClassMember(TeaID);
        if(e.getSource().equals(chengji))
            new Teacher_ClassGrade(TeaID);
        if(e.getSource().equals(course))
            new Teacher_dafen(TeaID);
        if(e.getSource().equals(yixiuxuefen))
            new Teacher_ClassMember(TeaID);
        if(e.getSource().equals(guochengpingjia))
            new Teacher_ClassGrade(TeaID);

    }
}
