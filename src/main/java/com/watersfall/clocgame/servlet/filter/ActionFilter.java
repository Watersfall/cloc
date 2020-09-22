package com.watersfall.clocgame.servlet.filter;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;
import lombok.Getter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.PreparedStatement;

@WebFilter(urlPatterns = {"/*"})
public class ActionFilter implements Filter
{
	private @Getter ServletContext context;

	@Override
	public void init(FilterConfig config)
	{
		this.context = config.getServletContext();
	}

	@Override
	public void destroy(){}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getMethod().equalsIgnoreCase("POST"))
		{
			if(req.getSession() != null)
			{
				if(req.getSession().getAttribute("user") != null)
				{
					Executor executor = (connection -> {
						int user = UserUtils.getUser(req);
						PreparedStatement statement = connection.prepareStatement("UPDATE nation_stats SET last_login=? WHERE id=?");
						statement.setLong(1, System.currentTimeMillis());
						statement.setInt(2, user);
						statement.execute();
						return null;
					});
					Action.doAction(executor);
				}
			}
		}
		chain.doFilter(request, response);
	}
}
