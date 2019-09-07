package com.watersfall.clocgame.controller.filter;

import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Util;
import lombok.Getter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = "*.jsp")
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
		request.setAttribute("turn", Util.turn);
		try
		{
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession sess = req.getSession(false);
			if(sess != null && sess.getAttribute("user") != null)
			{
				int user = Integer.parseInt(sess.getAttribute("user").toString());
				Connection connection = Database.getDataSource().getConnection();
				req.setAttribute("home", new Nation(connection, user, false, true));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}
}
