<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
       <div class="location">
           <strong>你现在所在的位置是:</strong>
           <span>角色管理页面</span>
       </div>
       <div class="search">
       <form method="get" action="${ctx }/role/rolelist.html">
			<input name="method" value="query" class="input-text" type="hidden">
			<span>角色编码：</span>
			<input name="queryRoleCode" type="text" value="${queryRoleCode }">
			
			<span>角色名称：</span>
			<input name="queryRoleName" type="text" value="${queryRoleName }">
		
			 <input	value="查 询" type="submit" id="searchbutton">
			 <a href="${ctx }/role/roleadd.html">添加角色</a>
		</form>
       </div>
       <!--账单表格 样式和供应商公用-->
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
              <th width="25%">角色编码</th>
              <th width="35%">角色名称</th>
              <th width="25%">创建时间</th>
          </tr>
          <c:forEach var="role" items="${rolelist }" varStatus="status">
				<tr>
					<td>
					<span>${role.roleCode }</span>
					</td>
					<td>
					<span>${role.roleName }</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${role.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${ctx}/static/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${ctx}/static/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userName }><img src="${ctx}/static/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
      </table>
  </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>