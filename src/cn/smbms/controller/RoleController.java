package cn.smbms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;

//让这个类具备处理请求的功能
@Controller
//设置它的操作模块是角色管理
@RequestMapping("/role")
public class RoleController {
	//创建业务层对象
	@Autowired
	private RoleService roleService;
	
	//查询角色列表
	@RequestMapping(value="/rolelist.html",method=RequestMethod.GET)
	public String query(
				@RequestParam(name="queryRoleCode",required = false) String queryRoleCode,
				@RequestParam(name="queryRoleName",required = false) String queryRoleName,
				Model model
			) {
		//创建保存数据的集合
		List<Role> rolelist=null;
		//将数据保存在集合中
		rolelist=roleService.getRoleList(queryRoleCode,queryRoleName);
		//将集合存到model对象
		model.addAttribute("rolelist", rolelist);
		return "rolelist";
	}
	
	//访问添加页面
	@RequestMapping(value="/roleadd.html",method=RequestMethod.GET)
	public String addRole(@ModelAttribute("role") Role role) {
		return "roleadd";
	}
	
	//添加角色的方法
	/**
	 * @param role
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/rolesave.html",method=RequestMethod.POST)
	public String roleSave(Role role,HttpSession session) {
		//获得创建这id
		int createdBy=((User)session.getAttribute("user")).getId();
		//创建时间，存到实体中
		role.setCreatedBy(createdBy);
		role.setCreationDate(new Date());
		//调用方法
		boolean isOk=roleService.add(role);
		if(isOk) {
			return "redirect:rolelist.html";
		}
		return "roleadd";
	}
}
