<%@page import="java.util.List"%>
<%@page import="com.gds.beans.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"/>
		<%
			User user = (User) request.getAttribute("user");
		%>
		<div class="user-card">
			<span>
				<p><b><%= user.getLogin() %></b></p>
				<p><%= user.getName() %></p>
			</span>
			<img src="	<%= user.getAvatar() %>"/>
		</div>
		<div class="repositories-list">
			<ul data-columns="3">
				<%
				for(int i = 0; i < user.getRepositories().size(); i++) 
				{
				%>
				<li><a href="repository?user=<%= user.getLogin() %>&repo=<%= user.getRepositories().get(i).getName() %>"><%= user.getRepositories().get(i).getName() %></a>
				<%
				} 
				%>
			</ul>
		</div>
	</div>
</body>
</html>