package studentdemo;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class TeacherJFrame implements FocusListener{
    DefaultTableModel tableModel;		// 默认显示的表格
    JButton btnAdd,btnDelete,btnCancel,btnChange;		// 各处理按钮
    JTable table;		// 表格
    JFrame f= new JFrame("系别信息输入窗口");
    JPanel panelUP;	//增加信息的面板
    JLabel latno,laName,laClass,lazhicheng,laAge,laSex,laphone,lashenfen;
    JTextField txtSno,txtName,txtAge,txtphone;
    JComboBox<depart> cmbDept,cmbshenfen;
    JComboBox cmbSex;
    JComboBox<Integer> cmbzhicheng;
    gsqlCon con=new gsqlCon();
    public TeacherJFrame(){
        f.setBounds(300, 200, 1100, 700);		// 设置窗体大小
        f.setTitle("教师信息输入窗口");		// 设置窗体名称
        f.setLayout(null);
        // 新建各按钮组件
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        btnAdd = new JButton("插入");
        btnAdd.setBounds(341,110,100,40);
        btnDelete = new JButton("删除");
        btnDelete.setBounds(460,450,66,26);
        btnChange = new JButton("保存");
        btnChange.setBounds(540,450,66,26);
        btnCancel = new JButton("退出");
        btnCancel.setBounds(620,450,66,26);
        f.add(btnDelete);
        f.add(btnChange);
        f.add(btnCancel);



        panelUP = new JPanel();		// 新建按钮组件面板
        panelUP.setLayout(null);
        panelUP.setBounds(150,60,800,150);
        panelUP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


        latno = new JLabel("编号:");
        latno.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        laName = new JLabel("姓名:");
        laName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        laClass = new JLabel("学院:");
        laClass.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        lazhicheng = new JLabel("职称:");
        lazhicheng.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        laSex = new JLabel("性别:");
        laSex.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        laAge = new JLabel("年龄:");
        laAge.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        laphone = new JLabel("电话");
        lashenfen = new JLabel("系统身份:");
        lashenfen.setFont(new Font("微软雅黑", Font.PLAIN, 13));


        String[] ssss=new String[]{"男","女"};
        String[] zzzz=new String[]{"教授","副教授","讲师"};
        cmbSex=new JComboBox(ssss);
        cmbzhicheng = new JComboBox(zzzz);
        cmbDept=new JComboBox<>();
        cmbshenfen=new JComboBox<>();
        ResultSet dp;
        try {
            String sql="SELECT lz_cno01,lz_cname01 FROM liuz_college01;";
            dp= con.gsqlquery(con.GetCon(), sql);
            while(dp.next()){
                cmbDept.addItem(new depart(dp.getString(1),dp.getString(2)));
            }
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        try {
            String sql="SELECT lz_rno01,lz_rname01 FROM liuz_role01;";
            dp= con.gsqlquery(con.GetCon(), sql);
            while(dp.next()){
                cmbshenfen.addItem(new depart(dp.getString(1),dp.getString(2)));
            }
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        txtSno = new JTextField();
        txtName = new JTextField();
        txtphone=new JTextField();
        txtAge = new JTextField();

        latno.setBounds(25, 25,40, 12);
        txtSno.setBounds(68, 18, 90, 25);
        laName.setBounds(168, 25, 40, 12);
        txtName.setBounds(236, 18, 90,25);
        laClass.setBounds(25, 68, 85, 25);
        lashenfen.setBounds(540,68,70,12);
        cmbDept.setBounds(68, 68, 90, 25);
        cmbshenfen.setBounds(630,68,85,25);
        lazhicheng.setBounds(168, 68, 85,25);
        laSex.setBounds(346, 68, 85,25);
        cmbSex.setBounds(420, 68, 85,25);
        cmbzhicheng.setBounds(236, 68, 90,25);
        laAge.setBounds(346, 18, 85,25);
        txtAge.setBounds(420, 18, 90,25);
        laphone.setBounds(540,25,70,12);
        txtphone.setBounds(630,18,85,25);

        panelUP.add(btnAdd);
        panelUP.add(latno);
        panelUP.add(txtSno);
        panelUP.add(laName);
        panelUP.add(txtName);
        panelUP.add(laClass);
        panelUP.add(lashenfen);
        panelUP.add(cmbDept);
        panelUP.add(cmbshenfen);
        panelUP.add(lazhicheng);
        panelUP.add(laSex);
        panelUP.add(cmbSex);
        panelUP.add(laAge);
        panelUP.add(cmbzhicheng);
        panelUP.add(laphone);
        panelUP.add(txtphone);
        panelUP.add(txtAge);

        ResultSet rs = null;
        Vector columnNames = new Vector();
        //设置列名
        columnNames.add("编号");
        columnNames.add("姓名");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("职称");
        columnNames.add("电话");
        columnNames.add("学院");
        columnNames.add("系统身份");
        Vector rowData = new Vector();
        //rowData可以存放多行,开始从数据库里取
        try {
            rs= con.gsqlquery(con.GetCon(), "SELECT * FROM liuz_teacher01;");
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
                hang.add(rs.getString(8));
                //加入到rowData
                rowData.add(hang);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 新建表格
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
                int a;
                depart de1=(depart)cmbDept.getSelectedItem();
                depart de2=(depart)cmbshenfen.getSelectedItem();
                //
                sql = "insert into liuz_teacher01 values( '" + txtSno.getText() + "','" + txtName.getText() +"','"+
                        cmbSex.getSelectedItem()+"','" +txtAge.getText()+ "','"+ cmbzhicheng.getSelectedItem()+"','" +  txtphone.getText() +
                        "','" + de1.toNber()+"','"+ de2.toNber() +"');";
                a= con.gsqlexc(con.GetCon(), sql);
                if(a>0) {
                    tableModel.addRow(new Object[] {txtSno.getText(), txtName.getText(),cmbSex.getSelectedItem(),txtAge.getText(),cmbzhicheng.getSelectedItem(),txtphone.getText(),de1.toNber(),de2.toNber()});
                    txtSno.setText("");
                    txtName.setText("");

                    cmbDept.setDefaultLocale(null);
                }
            }
        });
        // 删除
        btnDelete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                // 删除指定行
                int rowcount = table.getSelectedRow();
                int rscount;
                if(rowcount==-1) {
                    JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
                }
                String sql="delete from liuz_teacher01 where lz_tno01 ='" + table.getValueAt(rowcount, 0)+"';";
                rscount=con.gsqlexc(con.GetCon(), sql);
                if(rscount>0)
                    tableModel.removeRow(rowcount);
            }
        });
        btnChange.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int rowcount = table.getSelectedRow();
                int rscount;
                if(rowcount==-1) {
                    JOptionPane.showMessageDialog(null, "请选中数据!","提示消息",JOptionPane.WARNING_MESSAGE);
                }

                String sql="update liuz_teacher01 set lz_tname01='"+ table.getValueAt(rowcount, 1) +"'," +
                        "lz_tsex01='"+table.getValueAt(rowcount, 2)+"'," +"lz_tage01='"+table.getValueAt(rowcount, 3)+"'," +"lz_title01='"+table.getValueAt(rowcount, 4)+"'," +"lz_tphone01='"+table.getValueAt(rowcount, 5)+
                        "'," +"lz_cno01='"+table.getValueAt(rowcount, 6)+"'," +"lz_rno01='"+table.getValueAt(rowcount, 7)+"'where lz_tno01 ='"+table.getValueAt(rowcount, 0) +"';";

                con.gsqlexc(con.GetCon(), sql);

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
        new TeacherJFrame();
    }
    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub

        cmbzhicheng.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        cmbzhicheng.setForeground(Color.BLACK);
    }
    @Override
    public void focusLost(FocusEvent e) {
        // TODO Auto-generated method stub

    }
}
