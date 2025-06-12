package studentdemo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Teacher_ClassGrade {
    DefaultTableModel tableModel;		// 默认显示的表格
    JButton btnAdd,btnCancel,btnConfirm;		// 各处理按钮
    JTable table;		// 表格
    JFrame f= new JFrame("课程信息输入窗口");
    JPanel panelUP;	//增加信息的面板
    JLabel laYear,laTerm;
    JTextField txtYear;
    JComboBox cmbTerm;
    gsqlCon con=null;
    String TeaID;
    // 构造函数
    public Teacher_ClassGrade(String id){
        TeaID= id;
        String[] iii={"1","2","3"};
        cmbTerm=new JComboBox(iii);
        f.setBounds(300, 200, 1100, 700);		// 设置窗体大小
        f.setTitle("班级成员成绩管理");		// 设置窗体名称
        f.setLayout(null);
        // 新建各按钮组件
        btnAdd = new JButton("查询");
        btnAdd.setBounds(341, 110, 100,40);
        btnConfirm = new JButton("重置查询");
        btnConfirm.setBounds(280, 450, 100,40);
        btnCancel = new JButton("退出");
        btnCancel.setBounds(620,450,100,40);

        f.add(btnCancel);
        f.add(btnConfirm);

        panelUP = new JPanel();		// 新建按钮组件面板
        panelUP.setLayout(null);
        panelUP.setBounds(150,60,800,150);
        panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


        laYear= new JLabel("姓名");
        laTerm= new JLabel("开设学期");

        txtYear=new JTextField();

        laYear.setBounds(168, 68, 85,25);
        txtYear.setBounds(236, 68, 90,25);
        laTerm.setBounds(346, 68, 85,25);
        cmbTerm.setBounds(420, 68, 85,25);


        panelUP.add(btnAdd);

        panelUP.add(laYear);
        panelUP.add(txtYear);
        //panelUP.add(laTerm);
       // panelUP.add(cmbTerm);


        ResultSet rs = null;
        Vector columnNames = new Vector();
        //设置列名
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.add("课程编号");
        columnNames.add("课程名");
        columnNames.add("分数");


        Vector rowData = new Vector();
        //rowData可以存放多行,开始从数据库里取
        try {
            String sql="SELECT lz_sno01,lz_sname01,lz_cono01,lz_coname01,lz_score01 FROM(" +
                    "SELECT lz_sno01,lz_sname01,lz_cono01,lz_score01 FROM liuz_stu_grade01 NATURAL JOIN (" +
                    " SELECT lz_sno01,lz_clno01,lz_sname01 FROM liuz_student01" +
                    " WHERE lz_clno01 IN(" +
                    "SELECT lz_clno01 FROM liuz_class01" +
                    " WHERE lz_tno01='"+TeaID+"'))) NATURAL JOIN liuz_course01;";
            rs= con.gsqlquery(con.GetCon(), sql);
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

                //加入到rowData
                rowData.add(hang);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //新建表格
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
                // 清空rowData，以便重新填充数据
                tableModel.setRowCount(0);

                // 获取用户输入的学年和学期
                String year = txtYear.getText();
                sql="SELECT lz_sno01,lz_sname01,lz_cono01,lz_coname01,lz_score01 FROM(" +
                        "SELECT lz_sno01,lz_sname01,lz_cono01,lz_score01 FROM liuz_stu_grade01 NATURAL JOIN (" +
                        " SELECT lz_sno01,lz_clno01,lz_sname01 FROM liuz_student01" +
                        " WHERE lz_clno01 IN(" +
                        "SELECT lz_clno01 FROM liuz_class01" +
                        " WHERE lz_tno01='"+TeaID+"') and lz_sname01 = '"+year+"')) NATURAL JOIN liuz_course01;";

                // 执行SQL查询
                ResultSet rs = null;
                try {
                    rs = con.gsqlquery(con.GetCon(), sql);
                    if (rs != null) {
                        while (rs.next()) {
                            // 从结果集中获取数据，并添加到rowData
                            Vector<Object> hang = new Vector<>();
                            hang.add(rs.getString(1));
                            hang.add(rs.getString(2));
                            hang.add(rs.getString(3));
                            hang.add(rs.getString(4));
                            hang.add(rs.getString(5));


                            // 将新行添加到表格模型
                            tableModel.addRow(hang.toArray());
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });
        btnConfirm.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // 清空rowData，以便重新填充数据
                tableModel.setRowCount(0);
                // 构造SQL查询语句
                String sql="SELECT lz_sno01,lz_sname01,lz_cono01,lz_coname01,lz_score01 FROM(" +
                        "SELECT lz_sno01,lz_sname01,lz_cono01,lz_score01 FROM liuz_stu_grade01 NATURAL JOIN (" +
                        " SELECT lz_sno01,lz_clno01,lz_sname01 FROM liuz_student01" +
                        " WHERE lz_clno01 IN(" +
                        "SELECT lz_clno01 FROM liuz_class01" +
                        " WHERE lz_tno01='"+TeaID+"'))) NATURAL JOIN liuz_course01;";
                // 执行SQL查询
                ResultSet rs = null;
                try {
                    rs = con.gsqlquery(con.GetCon(), sql);
                    if (rs != null) {
                        while (rs.next()) {
                            // 从结果集中获取数据，并添加到rowData
                            Vector<Object> hang = new Vector<>();
                            hang.add(rs.getString(1));
                            hang.add(rs.getString(2));
                            hang.add(rs.getString(3));
                            hang.add(rs.getString(4));
                            hang.add(rs.getString(5));
                            // 将新行添加到表格模型
                            tableModel.addRow(hang.toArray());
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
        new Teacher_ClassGrade("t01");
    }
}
