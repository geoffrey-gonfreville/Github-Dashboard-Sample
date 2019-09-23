<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.gds.beans.Repository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"/>
<jsp:useBean id="repository" class="com.gds.beans.Repository"/>
		<%
		Repository r = (Repository) request.getAttribute("repository");
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, new Locale("EN", "en"));
		String username = (String) request.getAttribute("username");
		%>
		<div class="repository-card">
			<div class="center">
				<p><%= username %></p>
				<a href="<%= r.getGithubLink() %>"><%= r.getGithubLink() %></a>
				
				<h3><%= r.getName() %></h3>
				<% if(!r.getLanguage().equals("null")){ %>
				<p><%= r.getLanguage() %></p>
				<% } %>
				<% if(!r.getDescription().equals("null")){ %>
				<p><%= r.getDescription() %></p>
				<% } %>
			</div>
			<p><%= r.getStars() %> stars</p>
			<p>Created at : <%= df.format(r.getCreationDate()) %></p>
			<p>Updated at : <%= df.format(r.getUpdateDate()) %></p>
		</div>
	</div>
</body>
</html>