package com.watersfall.clocgame.servlet.filter;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Util;
import lombok.Getter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = {
		"/air/*", "/cities/*", "/city/*", "/createtreaty/*", "/decisions/*", "/declarations/*", "/land/*", "/main/*",
		"/map/*", "/nation/*", "/navy/*", "/news/*", "/policy/*", "/rankings/*", "/register/*", "/settings/*",
		"/technology/*", "/techtree/*", "/treaties/*", "/treaty/*", "/index/", "/"})
public class LoggedInFilter implements Filter
{
	private @Getter ServletContext context;

	@Override
	public void init(FilterConfig config)
	{
		this.context = config.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getMethod().equalsIgnoreCase("GET"));
		{
			request.setAttribute("turn", Util.turn);
			try(Connection connection = Database.getDataSource().getConnection())
			{
				HttpSession sess = req.getSession(true);
				if(sess != null && sess.getAttribute("user") != null)
				{
					int user = Integer.parseInt(sess.getAttribute("user").toString());
					req.setAttribute("home", new Nation(connection, user, false));
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}
}
