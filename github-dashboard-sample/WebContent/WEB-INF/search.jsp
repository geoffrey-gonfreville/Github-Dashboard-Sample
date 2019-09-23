<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"/>
		<%
			String error = (String) request.getAttribute("error");
		%>
		<div class="search">
			<form method="post" action="dashboard">
				<input type="text" placeholder="Username" id="username" name="username"/>
				<input type="submit" value="SEARCH"/>
				<% if(error != null) {%><p class="error"><%= error %></p><% } %>
			</form>
		</div>
	</div>
</body>
</html>