package studentdemo;
import java.sql.*;
import javax.swing.JOptionPane;

public class gsqlCon{
  //创建数据库连接。
  public static Connection GetCon() {
	  //设置驱动
	  String driver = "org.postgresql.Driver";
	  //数据库连接描述符
      String sourceURL = "jdbc:postgresql://192.168.211.137:26000/liuzmis01";
      Connection conn = null;
      try {
        //加载数据库驱动。
        Class.forName(driver).newInstance();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      try {
        //创建数据库连接。
        System.out.println("Connection start!");
        conn = DriverManager.getConnection(sourceURL, "zjutuser", "openGauss@123");
        System.out.println("Connection succeed!");
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      return conn;
  }
  //执行普通SQL语句。
  public static int gsqlexc(Connection conn,String sql) {
    Statement stmt = null;
    int rc = 0;
    try {
      stmt = conn.createStatement();
      //执行普通SQL语句。
      rc = stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (stmt != null) {
        try {
          stmt.close();
          conn.close();
          JOptionPane.showMessageDialog(null, "不能做该操作！","提示消息",JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
      }
      e.printStackTrace();
    }
    return rc;
  }
  //执行普通SQL查询语句。
  public static ResultSet gsqlquery(Connection conn,String sql) throws SQLException {
	  Statement stmt = null;
	  ResultSet rs = null;
	  try {
		  stmt = conn.createStatement();
		  //执行普通SQL语句。
		  System.out.print("开始启动查询操作！");
		  rs  = stmt.executeQuery(sql);
		  System.out.print("完成查询操作！");
	  } catch (SQLException e) {
		  System.out.print("未能完成查询操作！");
		  stmt.close();
		  conn.close();
		  JOptionPane.showMessageDialog(null, "不能做该操作！","提示消息",JOptionPane.WARNING_MESSAGE);
	  }
	  conn.close();
	  return  rs ;
 };
//创建班级Class表查询语句
public static ResultSet gsqlclass(Connection conn) throws SQLException {
	Statement stmt = null;
	ResultSet rs = null;
	try {
		stmt = conn.createStatement();
		System.out.print("开始启动查询操作！");
		rs  = stmt.executeQuery("select * from liuz_class01");
	} catch (SQLException e) {

		System.out.print("未能完成查询操作！");
		stmt.close();
		conn.close();
		JOptionPane.showMessageDialog(null, "不能做该操作！","提示消息",JOptionPane.WARNING_MESSAGE);
	}
System.out.print("结束查询操作！");
	if(rs==null) {System.out.print("无数据！");}
	conn.close();
	return  rs ;
 };



	//执行存储过程。

  public static Boolean Login_Teacher(Connection conn, String user,String pass) {
	  CallableStatement cstmt = null;
	  String passwordFromDb=null;//存储从数据库中检索的密码
	  boolean loginSuccess=false;
	  try {
		  cstmt=conn.prepareCall("{CALL login_teacher(?,?)}");
		  cstmt.setString(1, user);
		  cstmt.registerOutParameter(2, Types.VARCHAR);  //注册out类型的参数，类型为整型。
		  cstmt.execute();//执行存储过程
		  passwordFromDb=cstmt.getString(2);
		  if(passwordFromDb!=null){
			  System.out.println(pass);
			  loginSuccess=passwordFromDb.equals(pass);
			  System.out.println(loginSuccess);
			  return loginSuccess;
		  }
		  cstmt.close();
		  return loginSuccess;
	  } catch (SQLException e) {
		  if (cstmt != null) {
			  try {
				  cstmt.close();
				  conn.close();
			  } catch (SQLException e1) {
				  e1.printStackTrace();
			  }
		  }
		  e.printStackTrace();
	  }
	  return false;
  }
	public static Boolean Login_Student(Connection conn, String user,String pass) {
		CallableStatement cstmt = null;
		String passwordFromDb=null;//存储从数据库中检索的密码
		boolean loginSuccess=false;
		try {
			cstmt=conn.prepareCall("{CALL login_student(?,?)}");
			cstmt.setString(1, user);
			cstmt.registerOutParameter(2, Types.VARCHAR);  //注册out类型的参数，类型为整型。
			cstmt.execute();//执行存储过程
			passwordFromDb=cstmt.getString(2);
			//System.out.print(passwordFromDb);
			if(passwordFromDb!=null){
				System.out.println(pass);
				loginSuccess=passwordFromDb.equals(pass);
				System.out.println(loginSuccess);
				return loginSuccess;
			}
			cstmt.close();
			return loginSuccess;
		} catch (SQLException e) {
			if (cstmt != null) {
				try {
					cstmt.close();
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return false;
	}
	public static double calculate_Gpa(Connection conn, String user) {
		CallableStatement cstmt = null;
		Double GPA=null;
		boolean loginSuccess=false;
		try {
			cstmt=conn.prepareCall("{CALL calculate_gpa(?,?)}");
			cstmt.setString(1, user);
			cstmt.registerOutParameter(2, Types.DOUBLE);  //注册out类型的参数，类型为整型。
			cstmt.execute();//执行存储过程
			GPA=cstmt.getDouble(2);

			cstmt.close();
			return GPA;
		} catch (SQLException e) {
			if (cstmt != null) {
				try {
					cstmt.close();
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return GPA;
	}
	public static void get_course(Connection conn, String sno,String classno,String teacherno) {
		CallableStatement cstmt = null;
		try {
			cstmt=conn.prepareCall("{CALL stu_get_courese(?,?,?)}");
			cstmt.setString(1, sno);
			cstmt.setString(2, classno);
			cstmt.setString(3, teacherno);
			cstmt.execute();//执行存储过程
			cstmt.close();
		} catch (SQLException e) {
			if (cstmt != null) {
				try {
					cstmt.close();
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}

  public static void main(String[] args) {
		  gsqlCon c =new gsqlCon();
		  Connection connection=c.GetCon();
		  //String SQL="SELECT * FROM liuz_teacheraccount01;";
		  //try {
		  //  ResultSet rs =c.gsqlquery(connection,SQL);
		  //  while(rs.next()){
		  //	  System.out.println(1);
		  //	  String id=rs.getString("lz_tno01");
		  //	  System.out.println(id);
		  //  }
		  //
		  //} catch (Exception e){
		  //
		  //}
		  String t=new String("jike01"),p=new String("zjut12345");
		  Boolean b=Login_Student(connection,t,p);

		  System.out.print(b);

  }

}
