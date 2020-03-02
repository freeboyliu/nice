package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseUtils {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	// ���ӵ����ݿ���ַ���
	private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useUnicode=true&characterEncoding=UTF-8";
	// �û���
	private static final String USERNAME = "root";
	// ����
	private static final String PWD = "wozuibang";
	//������ӵķ���
	// ����һ�����ݿ����Ӷ���
	private Connection conn;
	
	// ������ݿ����ӵķ���
	public Connection getConn() {
		try {
			// ����jdbc������
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	//�ͷ���Դ�ķ���
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
