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
    DefaultTableModel tableModel;		// 默认显示的表格
    JButton btnAdd,btnCancel,btnAll;		// 各处理按钮
    JTable table;		// 表格
    JFrame f= new JFrame("课程信息输入窗口");
    JPanel panelUP;	//增加信息的面板
    JLabel laYear,laTerm;
    JTextField txtYear;
    JComboBox cmbTerm;
    gsqlCon con=null;
    String StuID;
    // 构造函数
    public Stu_GetCourse(String id){
        StuID=id;
        String[] iii={"1","2","3"};
        cmbTerm=new JComboBox(iii);
        f.setBounds(300, 200, 1100, 700);		// 设置窗体大小
        f.setTitle("学生课表查询");		// 设置窗体名称
        f.setLayout(null);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // 新建各按钮组件
        btnAdd = new JButton("查询");
        btnAdd.setBounds(341, 110, 100,40);
        btnAll=new JButton("查询所有");
        btnCancel = new JButton("退出");
        btnAll.setBounds(450,450,150,26);
        btnCancel.setBounds(620,450,66,26);

        f.add(btnCancel);


        panelUP = new JPanel();		// 新建按钮组件面板
        panelUP.setLayout(null);
        panelUP.setBounds(150,60,800,150);
        panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));





        laYear= new JLabel("开设学年");
        laTerm= new JLabel("开设学期");

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
        //设置列名
        columnNames.add("课程名");
        columnNames.add("任课教师");
        columnNames.add("教学时间");
        columnNames.add("教学地点");
        columnNames.add("考核方式");
        columnNames.add("学分");
        columnNames.add("开设学年+学期");
        Vector rowData = new Vector();
        //rowData可以存放多行,开始从数据库里取
        try {
            //rs= con.gsqlquery(con.GetCon(), "select * from liuz_Course01 WHERE lz_sno01="+"'"+StuID+"';");
            String sql="SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01 " +
                    "FROM(SELECT * 	FROM(SELECT * FROM (SELECT lz_cono01,lz_tno01 FROM liuz_stu_grade01 WHERE lz_sno01='"+StuID+"') " +
                    "NATURAL JOIN liuz_teach_course01) NATURAL JOIN liuz_course01) NATURAL JOIN liuz_teacher01;";
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
                hang.add(rs.getString(6));
                hang.add(rs.getString(7)+"+"+rs.getString(8));
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
        f.add(btnAll);
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
                // 清空rowData，以便重新填充数据
                tableModel.setRowCount(0);

                // 获取用户输入的学年和学期
                String year = txtYear.getText();
                String term = (String) cmbTerm.getSelectedItem();

                // 构造SQL查询语句
                String sql="SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01 " +
                        "FROM(SELECT * 	FROM(SELECT * FROM (SELECT lz_cono01,lz_tno01 FROM liuz_stu_grade01 WHERE lz_sno01='"+StuID+"') " +
                        "NATURAL JOIN liuz_teach_course01"+
                        ") NATURAL JOIN liuz_course01  WHERE lz_coyear01 ='" +year+"' AND lz_coterm01 ="+term+") NATURAL JOIN liuz_teacher01;";

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
                            hang.add(rs.getString(6));
                            hang.add(rs.getString(7)+"+"+rs.getString(8));

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
        btnAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空rowData，以便重新填充数据
                tableModel.setRowCount(0);

                // 获取用户输入的学年和学期
                String year = txtYear.getText();
                String term = (String) cmbTerm.getSelectedItem();

                // 构造SQL查询语句
                String sql="SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01 " +
                        "FROM(SELECT * 	FROM(SELECT * FROM (SELECT lz_cono01,lz_tno01 FROM liuz_stu_grade01 WHERE lz_sno01='"+StuID+"') " +
                        "NATURAL JOIN liuz_teach_course01) NATURAL JOIN liuz_course01) NATURAL JOIN liuz_teacher01;";
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
                            hang.add(rs.getString(6));
                            hang.add(rs.getString(7)+"+"+rs.getString(8));

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
        String id="jike01";
        new Stu_GetCourse(id);
    }
}
