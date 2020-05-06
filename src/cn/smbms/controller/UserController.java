package cn.smbms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.PageSupport;

// 让这个类具备处理请求的功能
@Controller
// 设置它操作的模块是用户模块
@RequestMapping("/user")
public class UserController {
    // 创建一个业务层的对象
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    // 定义一个可以访问页面的请求方法
    @RequestMapping("/login.html")
    public String login() {
        // 直接访问login.jsp页面
        return "login";
    }
    
    // 定义一个dologin的请求方法，让它去与数据库中保存的信息进行匹配，
    // 如果成功就进行页面的跳转，如果失败就在回到login.jsp页面
    @RequestMapping(value = "/dologin.html",method = RequestMethod.POST)
    public String dologin(@RequestParam("userCode") String userCode,
        @RequestParam("userPassword") String userPassword,
        HttpServletRequest request,
        HttpSession session) throws Exception {
        // 调用业务层中根据userCode和userPassword两个参数实现登录的方法
        User user = userService.login(userCode, userPassword);
        if(user != null) {
            // 跳转到首页中
            // return "逻辑视图名";它是服务器端的一个跳转，它是使用的是转发
            // 如果要使用重定向来进行跳转
            // response.sendRedirect(跳转的请求路径);
            // 在SpringMVC框架中可以使用 redirect来标识 重定向
            // 使用forward来标识 转发
            // forward:逻辑视图，它将报No mapping for POST /SMBMSJDBC/user/frame 这个错误
            // 这两个标识 后面跟的是一个请求的url
            // redirect:请求的url
            // 将登录成功的用户保存到session会话中
            session.setAttribute("user", user);
            return "redirect:frame.html";
        }else {
            // 这种形式是服务器端的跳转(转发)
            /*request.setAttribute("error", "用户名或密码错误！！");
            return "login";*/
            throw new Exception("用户名或密码错误！！");
        }
    }
    
    // 定义一个实现转发的请求方法
    @RequestMapping("/frame.html")
    public String main() {
        return "frame";
    }
    
    // 模拟异常登录的方法
    @RequestMapping(value = "/exlogin.html",method = RequestMethod.GET)
    public String exLogin(@RequestParam("userCode") String userCode,
        @RequestParam("userPassword") String userPassword) {
        User user = userService.login(userCode, userPassword);
        if (user == null) {
            // 抛出一个异常
            throw new RuntimeException("用户名或密码错误");
        }
        return "redirect:frame.html";
    }
    // 处理异常登录的操作
    // @ExceptionHandler 支持HandlerExceptionResolver视图的一种方式
    // HandlerExceptionResolver视图中有一个resolverHandler方法，
    // 如果有异常就处理，没有异常就不做事情
    /*@ExceptionHandler(value = RuntimeException.class)
    public String handlerException(RuntimeException e,
        HttpServletRequest req) {
        req.setAttribute("exception", e);
        return "error";
    }*/
    
    @RequestMapping(value="/loginOut",method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        if (session != null) {
            // 清空session
            session.invalidate();
        }
        return "redirect:login.html";
    }
    
    // 查询用户列表的方法
    @RequestMapping(value="/userlist.html",method=RequestMethod.GET)
    public String query(
        @RequestParam(name="queryname",required = false) String queryname,
        @RequestParam(name="queryUserRole",required = false) String queryUserRole,
        @RequestParam(name="pageIndex",required = false) String pageIndex,
        Model model){
        // 保存用户角色id
        int _queryUserRole = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = 5;
        //当前页码
        int currentPageNo = 1;
        if(queryname == null){
            queryname = "";
        }
        // 判断角色id是否有数据
        if(queryUserRole != null && !queryUserRole.equals("")){
            _queryUserRole = Integer.parseInt(queryUserRole);
        }
        // 判断页面的值
        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                // 使用全局异常来进行异常信息的显示
                throw new RuntimeException("页面的值不正确！！");
                //response.sendRedirect("error.jsp");
            }
        } 
        //总数量（表）    
        int totalCount  = userService.getUserCount(queryname,_queryUserRole);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        // 获得总页数
        int totalPageCount = pages.getTotalPageCount();
        
        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        
        // 获得显示数据的集合
        userList = userService.getUserList(queryname,_queryUserRole,currentPageNo, pageSize);
        // 将数据放入到model对象中
        model.addAttribute("userList", userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        // 分页的jsp中需要的参数
        model.addAttribute("queryUserName", queryname);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }
    
    // 定义一个访问增加用户页面的方法
    @RequestMapping(value = "/adduser.html",method = RequestMethod.GET)
    // 方式一：将创建的对象保存到数据模型中
    //public String addUser(User user,Model model) {
    // 方式二：使用注解将对象保存到数据模型中
    public String addUser(@ModelAttribute("user") User user) {
        // 此处的User就相当于创建一个保存用户信息的空对象
        // 当在表单中输入数据后，数据就会自动的装入到这个user对象中
        //model.addAttribute("user",user);
        return "useradd";
    }
    
    @RequestMapping(value = "/usersave.html",method = RequestMethod.POST)
    public String userSave(User user,HttpSession session) {
        // 需要有创建者id,其实就是去获得保存在session中的用户对象的id值
        int createdBy = ((User)session.getAttribute("user")).getId();
        // 需要创建时间,获得系统的当前时间
        user.setCreatedBy(createdBy);
        user.setCreationDate(new Date());
        // 调用增加用户的方法
        boolean isOk = userService.add(user);
        if (isOk) {
            // 成功后页面上的数据也需要刷新，使用这里我们访问
            return "redirect:userlist.html";
        }else {
            return "useradd";
        }
    }
    
    @RequestMapping(value="/add.html",method=RequestMethod.GET)
    public String add(@ModelAttribute("user") User user) {
        return "user/useradd";
    }
    
    // 这里增加也使用同一个请求
    @RequestMapping(value="/add.html",method=RequestMethod.POST)
    public String addsave(User user,
        HttpSession session) {
        int createdBy = ((User)session.getAttribute("user")).getId();
        // 需要创建时间,获得系统的当前时间
        user.setCreatedBy(createdBy);
        user.setCreationDate(new Date());
        // 调用增加用户的方法
        boolean isOk = userService.add(user);
        if (isOk) {
            // 成功后页面上的数据也需要刷新，使用这里我们访问
            return "redirect:userlist.html";
        }else {
            return "user/useradd";
        }
    }
    
}
