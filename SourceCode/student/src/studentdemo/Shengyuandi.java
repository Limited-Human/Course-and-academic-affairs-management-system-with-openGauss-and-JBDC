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
    DefaultTableModel tableModel;		// Ĭ����ʾ�ı��
    JButton btnAdd,btnCancel,btnConfirm;		// ������ť
    JTable table;		// ���
    JFrame f= new JFrame("�γ���Ϣ���봰��");
    JPanel panelUP;	//������Ϣ�����
    gsqlCon con=null;

    // ���캯��
    public Shengyuandi(){

        f.setBounds(300, 200, 1100, 700);		// ���ô����С
        f.setTitle("��Դ�ط���");		// ���ô�������
        f.setLayout(null);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ResultSet rs = null;
        Vector columnNames = new Vector();
        //��������
        columnNames.add("��Դ��");
        columnNames.add("ѧ������");


        Vector rowData = new Vector();
        //rowData���Դ�Ŷ���,��ʼ�����ݿ���ȡ
        try {
            String sql="SELECT lz_saddress01,COUNT(*) AS StudentCount " +
                    "FROM liuz_student01 " +
                    "GROUP BY lz_saddress01 " +
                    "ORDER BY StudentCount DESC ";
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

        f.add(s);

        f.setVisible(true);		// ��ʾ����
    }
    // �¼�����

    // ������
    public static void main(String[] args){
        new Shengyuandi();
    }
}
