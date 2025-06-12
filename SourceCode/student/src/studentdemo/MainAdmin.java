package studentdemo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainAdmin implements ActionListener {
    JMenu file_, renyuan,kecheng,xuexiao;
    JMenuItem exit_;
    JMenuItem jiaoshi,xuesheng,zhuanye,banji,kechengshezhi,shengyuandi,shouke;
    JButton btnCancel,btnChange;

    gsqlCon con =null;

    MainAdmin(){

        Font menuFont = new Font("SansSerif", Font.PLAIN, 20);
        JFrame f= new JFrame("教务管理系统");
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        btnChange = new JButton("保存");
        btnChange.setBounds(200,200,66,26);
        btnCancel = new JButton("退出");
        btnCancel.setBounds(280,200,66,26);
        //创建菜单栏
        JMenuBar mb=new JMenuBar();
        //创建菜单
        file_=new JMenu("文件");
        renyuan=new JMenu("人员管理");
        kecheng=new JMenu("课程管理");
        xuexiao=new JMenu("学校管理");

        //创建菜单条
        exit_=new JMenuItem("退出");
        exit_.addActionListener(this);
        //人员管理

        jiaoshi=new JMenuItem("教师管理");
        jiaoshi.addActionListener(this);
        xuesheng=new JMenuItem("学生管理");
        xuesheng.addActionListener(this);
        shengyuandi=new JMenuItem("学生生源地");
        shengyuandi.addActionListener(this);
        //课程管理
        kechengshezhi=new JMenuItem("课程管理");
        kechengshezhi.addActionListener(this);
        shouke=new JMenuItem("授课管理");
        shouke.setFont(menuFont);
        shouke.addActionListener(this);
        kechengshezhi.setFont(menuFont);
        //学校管理
        zhuanye=new JMenuItem("专业管理");
        zhuanye.addActionListener(this);
        banji=new JMenuItem("班级管理");
        banji.addActionListener(this);

        file_.setFont(menuFont);
        renyuan.setFont(menuFont);
        kecheng.setFont(menuFont);
        xuexiao.setFont(menuFont);

        exit_.setFont(menuFont);
        renyuan.setFont(menuFont);
        jiaoshi.setFont(menuFont);
        xuesheng.setFont(menuFont);
        zhuanye.setFont(menuFont);
        banji.setFont(menuFont);
        shengyuandi.setFont(menuFont);

        file_.add(exit_);

        renyuan.add(jiaoshi);
        renyuan.add(xuesheng);
        renyuan.add(shengyuandi);

        kecheng.add(kechengshezhi);
        kecheng.add(shouke);
        xuexiao.add(banji);
        xuexiao.add(zhuanye);

        mb.add(file_);
        mb.add(renyuan);
        mb.add(kecheng);
        mb.add(xuexiao);


        f.setJMenuBar(mb);

        f.setSize(850, 462);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
    }
    public static void main(String args[])
    {
        new MainAdmin();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(exit_))
            System.exit(0);

        if(e.getSource().equals(xuesheng))
            new StudentJFrame();
        if(e.getSource().equals(kechengshezhi))
            new CourseJFrame();
        if(e.getSource().equals(jiaoshi))
            new TeacherJFrame();
        if(e.getSource().equals(shengyuandi))
            new Shengyuandi();
        if(e.getSource().equals(shouke))
            new CourseJFrame();
        if(e.getSource().equals(zhuanye))
            new AdminMajor();
        if(e.getSource().equals(banji))
            new AdminClass();
    }
}
