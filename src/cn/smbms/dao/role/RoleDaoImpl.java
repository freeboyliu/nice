package cn.smbms.dao.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;

import cn.smbms.dao.BaseDao;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
@Repository
public class RoleDaoImpl implements RoleDao{

	@Override
	public List<Role> getRoleList(Connection connection) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Role> roleList = new ArrayList<Role>();
		if(connection != null){
			String sql = "select * from smbms_role";
			Object[] params = {};
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			while(rs.next()){
				Role _role = new Role();
				_role.setId(rs.getInt("id"));
				_role.setRoleCode(rs.getString("roleCode"));
				_role.setRoleName(rs.getString("roleName"));
				roleList.add(_role);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		
		return roleList;
	}

	@Override
	public List<Role> getRoleList(Connection connection, String roleCode, String roleName) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Role> roleList = new ArrayList<Role>();
		if(connection != null){
			StringBuffer sql = new StringBuffer();
			sql.append("select * from smbms_role where 1=1 ");
			List<Object> list = new ArrayList<Object>();
			if(!StringUtils.isNullOrEmpty(roleName)){
				sql.append(" and roleName like ?");
				list.add("%"+roleName+"%");
			}
			if(!StringUtils.isNullOrEmpty(roleCode)){
				sql.append(" and roleCode like ?");
				list.add("%"+roleCode+"%");
			}
			Object[] params = list.toArray();
			System.out.println("sql ----> " + sql.toString());
			rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
			while(rs.next()){
				Role _role = new Role();
				_role.setId(rs.getInt("id"));
				_role.setRoleCode(rs.getString("roleCode"));
				_role.setRoleName(rs.getString("roleName"));
				_role.setCreatedBy(rs.getInt("createdBy"));
				_role.setModifyBy(rs.getInt("modifyBy"));
				_role.setCreationDate(rs.getTimestamp("creationDate"));
				_role.setModifyDate(rs.getTimestamp("modifyDate"));
				roleList.add(_role);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
				
		return roleList;
	}

	@Override
	public int add(Connection connection, Role role) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstm = null;
		int flag = 0;
		if(null != connection){
			String sql = "INSERT INTO smbms_role(`roleCode`,`roleName`,`createdBy`,`creationDate`) "+
					"values(?,?,?,?)";
			Object[] params = {role.getRoleCode(),role.getRoleName(),
					role.getCreatedBy(),role.getCreationDate()};
			flag = BaseDao.execute(connection, pstm, sql, params);
			BaseDao.closeResource(null, pstm, null);
			System.out.println("dao--------flag " + flag);
		}
		return flag;
	}

}
