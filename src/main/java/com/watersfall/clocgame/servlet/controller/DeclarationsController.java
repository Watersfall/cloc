package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.action.Action;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.model.message.Declaration;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.util.Executor;
import com.watersfall.clocgame.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/declarations/"})
public class DeclarationsController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try(Connection conn = Database.getDataSource().getConnection())
		{
			ArrayList<Declaration> declarations = Declaration.getDeclarations(conn);
			req.setAttribute("declarations", declarations);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/declarations.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter writer = resp.getWriter();
		Executor executor = (conn) -> {
			int user = UserUtils.getUser(req);
			String message = req.getParameter("message");
			Nation nation = new Nation(conn, user, true);
			String response = Declaration.post(nation, message, conn);
			nation.update();
			return response;
		};
		writer.append(Action.doAction(executor));
	}
}