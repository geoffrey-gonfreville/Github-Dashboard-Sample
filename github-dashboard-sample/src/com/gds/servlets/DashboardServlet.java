package com.gds.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gds.beans.Repository;
import com.gds.beans.User;

public class DashboardServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String username = request.getParameter("username");
		
		HttpURLConnection conn = (HttpURLConnection) new URL("https://api.github.com/users/" + username).openConnection();
		conn.addRequestProperty("User-agent", "Mozilla/5.0");
		
		String result = checkUser(conn);
		
		if(result == null) {
			String error = "User " + username + " not found";
			request.setAttribute("error", error);
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/search.jsp");
			dispatcher.forward(request, response);
		}
		else {
			User user = new User();
			createUser(user, username, result);
			
			conn = (HttpURLConnection) new URL("https://api.github.com/users/" + username +"/repos").openConnection();
			conn.addRequestProperty("User-agent", "Mozilla/5.0");
			
			String tmp[] = checkUserRepositories(conn);
			
			List<Repository> repositories = new ArrayList<Repository>();
			
			for (int i = 0; i < tmp.length; i++) {
				repositories.add(new Repository(tmp[i]));
			}
			
			user.setRepositories(repositories);
			
			request.setAttribute("user", user);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
		}
	}
	
	private String checkUser(HttpURLConnection conn) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "", inputLine;
			while((inputLine = in.readLine()) != null) {
				line += "\n" + inputLine;
			}
			in.close();
			
			return line;
		} catch (IOException e) {
			return null;
		}
	}
	
	private String[] checkUserRepositories(HttpURLConnection conn) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String line2 = "", inputLine2;
		while((inputLine2 = in.readLine()) != null) {
			line2 += "\n" + inputLine2;
		}
		in.close();
		
		String tmp = Arrays.stream(line2.split("\"full_name\":"))
		.skip(1)
		.map(l -> l.split(",")[0])
		.map(l -> l.split("/")[1])
		.map(l -> l.replace("\"", " "))
		.collect(Collectors.joining());
		
		String repositories[] = tmp.split(" ");
		
		return repositories;
	}
	
	private void createUser(User user, String username, String result) {
		user.setLogin(username);
		user.setName(Arrays
				.stream(result.split("\"name\":"))
				.skip(1).map(l -> l.split(",")[0])
				.map(l -> l.split("\"")[1])
				.collect(Collectors.joining()));
		user.setAvatar(Arrays
				.stream(result.split("\"avatar_url\":"))
				.skip(1).map(l -> l.split(",")[0])
				.map(l -> l.split("\"")[1])
				.collect(Collectors.joining()));
	}
}
