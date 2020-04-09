package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.model.military.Bomber;
import com.watersfall.clocgame.model.military.Fighter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/main/")
public class MainController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("fighters", Fighter.values());
		req.setAttribute("bombers", Bomber.values());
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
	}
}
