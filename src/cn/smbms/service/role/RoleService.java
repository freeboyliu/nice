package cn.smbms.service.role;

import java.util.List;

import cn.smbms.pojo.Role;

public interface RoleService {
	
	public List<Role> getRoleList(String roleCode,String roleName);
	public List<Role> getRoleList();
	public boolean add(Role role);
}
