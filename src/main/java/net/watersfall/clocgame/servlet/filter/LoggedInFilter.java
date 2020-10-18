package net.watersfall.clocgame.servlet.filter;

import lombok.Getter;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.database.Database;
import net.watersfall.clocgame.util.Time;
import net.watersfall.clocgame.util.UserUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = {"/*"})
public class LoggedInFilter implements Filter
{

	private static boolean containsExtension(String string)
	{
		return string.endsWith(".ico") || string.endsWith(".svg") || string.endsWith(".png") || string.endsWith(".jpg")
				|| string.endsWith(".jpeg") || string.endsWith(".js") || string.endsWith(".css");
	}

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
		if(req.getMethod().equalsIgnoreCase("GET") && !containsExtension(req.getRequestURI()))
		{
			req.setAttribute("turn", Time.month);
			req.setAttribute("time", new Time());
			req.setAttribute("description", "A World War 1 themed online nation sim");
			try(Connection connection = Database.getDataSource().getConnection())
			{
				HttpSession sess = req.getSession(true);
				if(sess != null && sess.getAttribute("user") != null)
				{
					int user = UserUtils.getUser(req);
					NationDao dao = new NationDao(connection, false);
					req.setAttribute("home", dao.getNationById(user));
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
