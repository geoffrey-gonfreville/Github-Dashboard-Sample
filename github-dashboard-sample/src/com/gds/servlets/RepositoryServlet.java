package com.gds.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gds.beans.Repository;

public class RepositoryServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String username = (String) request.getParameter("user");
		Repository repository = new Repository((String) request.getParameter("repo"));
		
		HttpURLConnection conn = (HttpURLConnection) new URL("https://api.github.com/repos/" + username + "/" + repository.getName()).openConnection();
		conn.addRequestProperty("User-agent", "Mozilla/5.0");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String line = "", inputLine;
		while((inputLine = in.readLine()) != null) {
			line += "\n" + inputLine;
		}
		in.close();
		
		try {
			createRepository(repository, line);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("username", username);
		request.setAttribute("repository", repository);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/repository.jsp").forward(request, response);
	}
	
	private void createRepository(Repository repository, String result) throws ParseException {
		String tmp_date;
		String tmp_time;
		
		String language = Arrays
				.stream(result.split("\"language\":"))
				.skip(1)
				.map(l -> l.split(",")[0])
				.map(l -> {
					if(l.contains("\"")) {
						l = l.replace("\"", " ");
					}
					return l;
				})
				.collect(Collectors.joining());
		
		if(language.contains(" ")) {language = language.split(" ")[1];}
		
		repository.setLanguage(language);
		repository.setStars(Arrays
				.stream(result.split("\"stargazers_count\":"))
				.skip(1)
				.mapToInt(l -> Integer.parseInt(l.split(",")[0])).sum());
		
		String description = Arrays
				.stream(result.split("\"description\":"))
				.skip(1)
				.map(l -> l.split(",")[0])
				.map(l -> {
					if(l.contains("\"")) {
						l = l.split("\"")[1];
						l = l.concat("\"");
					}
					return l;
				})
				.collect(Collectors.joining());
		
		if(description.contains("\"")) {description = description.split("\"")[0];}
		repository.setDescription(description);
		
		String creation_date = Arrays
				.stream(result.split("\"created_at\":"))
				.skip(1)
				.map(l -> l.split(",")[0])
				.map(l -> l.split("\"")[1])
				.map(l -> l.concat("\""))
				.collect(Collectors.joining());
		
		if(creation_date.contains("\"")) {creation_date = creation_date.split("\"")[0];}
		
		tmp_date = creation_date.split("T")[0];
		tmp_time = creation_date.split("T")[1].split("Z")[0];
		creation_date = tmp_date + " " + tmp_time;
		repository.setCreationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creation_date));
		
		String update_date = Arrays
				.stream(result.split("\"updated_at\":"))
				.skip(1)
				.map(l -> l.split(",")[0])
				.map(l -> l.split("\"")[1])
				.map(l -> l.concat("\""))
				.collect(Collectors.joining());
		
		if(update_date.contains("\"")) {update_date = update_date.split("\"")[0];}
		
		tmp_date = update_date.split("T")[0];
		tmp_time = update_date.split("T")[1].split("Z")[0];
		update_date = tmp_date + " " + tmp_time;
		repository.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(update_date));
		
		String link = Arrays
				.stream(result.split("\"html_url\":"))
				.skip(1)
				.map(l -> l.split(",")[0])
				.skip(1)
				.map(l -> l.split("\"")[1])
				.map(l -> l.concat("\""))
				.collect(Collectors.joining());
		
		if(link.contains("\"")) {link = link.split("\"")[0];}
		repository.setGithubLink(link);
	}
}
