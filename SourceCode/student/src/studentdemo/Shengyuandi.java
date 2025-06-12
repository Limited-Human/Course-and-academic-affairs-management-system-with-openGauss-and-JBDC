package studentdemo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Shengyuandi {
    DefaultTableModel tableModel;		// 默认显示的表格
    JButton btnAdd,btnCancel,btnConfirm;		// 各处理按钮
    JTable table;		// 表格
    JFrame f= new JFrame("课程信息输入窗口");
    JPanel panelUP;	//增加信息的面板
    gsqlCon con=null;

    // 构造函数
    public Shengyuandi(){

        f.setBounds(300, 200, 1100, 700);		// 设置窗体大小
        f.setTitle("生源地分析");		// 设置窗体名称
        f.setLayout(null);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ResultSet rs = null;
        Vector columnNames = new Vector();
        //设置列名
        columnNames.add("生源地");
        columnNames.add("学生数量");


        Vector rowData = new Vector();
        //rowData可以存放多行,开始从数据库里取
        try {
            String sql="SELECT lz_saddress01,COUNT(*) AS StudentCount " +
                    "FROM liuz_student01 " +
                    "GROUP BY lz_saddress01 " +
                    "ORDER BY StudentCount DESC ";
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
                if(column == 4)
                    return true;
                else
                    return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane s = new JScrollPane(table);
        s.setBounds(150, 250, 800, 180);

        f.add(s);

        f.setVisible(true);		// 显示窗体
    }
    // 事件处理

    // 主函数
    public static void main(String[] args){
        new Shengyuandi();
    }
}
