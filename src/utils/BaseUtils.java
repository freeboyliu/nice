package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseUtils {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	// 连接到数据库的字符串
	private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useUnicode=true&characterEncoding=UTF-8";
	// 用户名
	private static final String USERNAME = "root";
	// 密码
	private static final String PWD = "wozuibang";
	//获得连接的方法
	// 定义一个数据库连接对象
	private Connection conn;
	
	// 获得数据库连接的方法
	public Connection getConn() {
		try {
			// 加载jdbc驱动包
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	//释放资源的方法
	public static void closeAll(ResultSet rs,PreparedStatement pst,Connection conn)
	{
		
		try {
			if(rs!=null)
			{
			rs.close();
			}
			if( pst!=null)
			{
				pst.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
