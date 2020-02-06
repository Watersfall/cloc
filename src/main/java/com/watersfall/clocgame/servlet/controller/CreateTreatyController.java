package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/createtreaty/"})
public class CreateTreatyController extends HttpServlet
{
	@Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/createtreaty.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter writer = response.getWriter();
		Executor executor = (conn) -> {
			int user = UserUtils.getUser(request);
			String name = request.getParameter("name");
			if(name.length() > 32)
			{
				return Responses.tooLong();
			}
			else
			{
				Nation nation = new Nation(conn, user, true);
				if(nation.getTreaty() != null)
				{
					nation.leaveTreaty();
				}
				Treaty treaty = Treaty.createTreaty(conn, name);
				nation.joinTreaty(treaty.getId(), true, true);
				conn.commit();
				response.setStatus(201);
				return Integer.toString(treaty.getId());
			}
		};
		writer.append(Action.doAction(executor));
	}
}
