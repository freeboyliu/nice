package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.StudentDao;
import entity.Student;

public class StudentDaoImpl implements StudentDao {
	private Connection conn=null;
	private PreparedStatement pst=null;
	private ResultSet rs=null;
	public List<Student> ShowAll() {
	List<Student> list=new ArrayList<Student>();
	try {
		//创建连接对象
		conn=getConn();
		//创建要执行的sql命令
		String sql="select * from studentinfo";
		//创建执行对象
		pst=conn.prepareStatement(sql);
		// 因为没有?号，就直接调用方法执行
		rs = pst.executeQuery();
		// 处理结果，将结果集中的数据保存到集合中
		while (rs.next()) {
			Student flo=new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6));
			list.add(flo);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(rs, pst, conn);
	return list;
}
