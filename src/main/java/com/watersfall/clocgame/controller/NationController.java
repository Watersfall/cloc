package com.watersfall.clocgame.controller;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.LogType;
import com.watersfall.clocgame.model.nation.Army;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.war.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/nation.jsp", "/nation.do"})
public class NationController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		if(req.getParameter("id") != null)
		{
			int id = Integer.parseInt(req.getParameter("id"));
			try
			{
				Connection connection = Database.getDataSource().getConnection();
				req.setAttribute("nation", new Nation(connection, id, false));
				connection.close();
			}
			catch(Exception e)
			{
				//Ignore
			}
		}
		req.getServletContext().getRequestDispatcher("/WEB-INF/view/nation.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String action = req.getParameter("action");
		PrintWriter writer = resp.getWriter();

		if(req.getSession().getAttribute("user") == null)
		{
			writer.append(Responses.noLogin());
			return;
		}
		if(req.getParameter("id") == null)
		{
			writer.append(Responses.genericError());
			return;
		}
		if(action == null)
		{
			writer.append(Responses.genericError());
			return;
		}

		int idReceiever = Integer.parseInt(req.getParameter("id"));
		int idSender = Integer.parseInt(req.getSession().getAttribute("user").toString());
		Connection connection = null;
		try
		{
			connection = Database.getDataSource().getConnection();
			Nation sender = new Nation(connection, idSender, true);
			Nation receiver = new Nation(connection, idReceiever, true);
			int amount;
			switch(action)
			{
				case "sendcoal":
					if(req.getParameter("amount") == null)
					{
						writer.append(Responses.genericError());
						break;
					}
					amount = Integer.parseInt(req.getParameter("amount"));
					if(amount <= 0)
					{
						writer.append(Responses.negative());
						break;
					}
					else if(amount > sender.getEconomy().getCoal())
					{
						writer.append(Responses.notEnough());
						break;
					}
					else
					{
						sender.getEconomy().setCoal(sender.getEconomy().getCoal() - amount);
						receiver.getEconomy().setCoal(receiver.getEconomy().getCoal() + amount);
						sender.update();
						receiver.update();
						connection.commit();
						writer.append(Responses.sent());
					}
					break;
				case "sendiron":
					if(req.getParameter("amount") == null)
					{
						writer.append(Responses.genericError());
						break;
					}
					amount = Integer.parseInt(req.getParameter("amount"));
					if(amount <= 0)
					{
						writer.append(Responses.negative());
						break;
					}
					else if(amount > sender.getEconomy().getIron())
					{
						writer.append(Responses.notEnough());
						break;
					}
					else
					{
						sender.getEconomy().setIron(sender.getEconomy().getIron() - amount);
						receiver.getEconomy().setIron(receiver.getEconomy().getIron() + amount);
						sender.update();
						receiver.update();
						connection.commit();
						writer.append(Responses.sent());
					}
					break;
				case "sendoil":
					if(req.getParameter("amount") == null)
					{
						writer.append(Responses.genericError());
						break;
					}
					amount = Integer.parseInt(req.getParameter("amount"));
					if(amount <= 0)
					{
						writer.append(Responses.negative());
						break;
					}
					else if(amount > sender.getEconomy().getOil())
					{
						writer.append(Responses.notEnough());
						break;
					}
					else
					{
						sender.getEconomy().setOil(sender.getEconomy().getOil() - amount);
						receiver.getEconomy().setOil(receiver.getEconomy().getOil() + amount);
						sender.update();
						receiver.update();
						connection.commit();
						writer.append(Responses.sent());
					}
					break;
				case "sendsteel":
					if(req.getParameter("amount") == null)
					{
						writer.append(Responses.genericError());
						break;
					}
					amount = Integer.parseInt(req.getParameter("amount"));
					if(amount <= 0)
					{
						writer.append(Responses.negative());
						break;
					}
					else if(amount > sender.getEconomy().getSteel())
					{
						writer.append(Responses.notEnough());
						break;
					}
					else
					{
						sender.getEconomy().setSteel(sender.getEconomy().getSteel() - amount);
						receiver.getEconomy().setSteel(receiver.getEconomy().getSteel() + amount);
						sender.update();
						receiver.update();
						connection.commit();
						writer.append(Responses.sent());
					}
					break;
				case "sendnitrogen":
					if(req.getParameter("amount") == null)
					{
						writer.append(Responses.genericError());
						break;
					}
					amount = Integer.parseInt(req.getParameter("amount"));
					if(amount <= 0)
					{
						writer.append(Responses.negative());
						break;
					}
					else if(amount > sender.getEconomy().getNitrogen())
					{
						writer.append(Responses.notEnough());
						break;
					}
					else
					{
						sender.getEconomy().setNitrogen(sender.getEconomy().getNitrogen() - amount);
						receiver.getEconomy().setNitrogen(receiver.getEconomy().getNitrogen() + amount);
						sender.update();
						receiver.update();
						connection.commit();
						writer.append(Responses.sent());
					}
					break;
				case "sendmoney":
					if(req.getParameter("amount") == null)
					{
						writer.append(Responses.genericError());
						break;
					}
					amount = Integer.parseInt(req.getParameter("amount"));
					if(amount <= 0)
					{
						writer.append(Responses.negative());
						break;
					}
					else if(amount > sender.getEconomy().getBudget())
					{
						writer.append(Responses.notEnough());
						break;
					}
					else
					{
						sender.getEconomy().setBudget(sender.getEconomy().getBudget() - amount);
						receiver.getEconomy().setBudget(receiver.getEconomy().getBudget() + amount);
						sender.update();
						receiver.update();
						connection.commit();
						writer.append(Responses.sent());
					}
					break;
				case "war":
					if(!sender.canDeclareWar(receiver))
					{
						break;
					}
					else
					{
						sender.declareWar(receiver);
						connection.commit();
						writer.append(Responses.war());
					}
				case "land":
					if(sender.getOffensive() != receiver.getId() && sender.getDefensive() != receiver.getId())
					{
						writer.append(Responses.noWar());
					}
					else if(Log.checkLog(connection, sender.getId(), receiver.getForeign().getRegion(), LogType.LAND))
					{
						writer.append(Responses.alreadyAttacked());
					}
					else
					{
						//There's only the home army for every nation atm, will need to fix this when you can create more
						Army attacker = (Army) sender.getArmies().getArmies().values().toArray()[0];
						Army defender = (Army) receiver.getArmies().getArmies().values().toArray()[0];
						if(attacker.getAttackPower() > defender.getAttackPower())
						{
							defender.setArmy(defender.getArmy() - 10);
							defender.update();
							writer.append("Victory! You have killed 10k enemy soldiers!");
						}
						else
						{
							attacker.setArmy(attacker.getArmy() - 10);
							attacker.update();
							writer.append("Defeat! You have lost 10k soldiers!");
						}
						Log.createLog(connection, sender.getId(), receiver.getForeign().getRegion(), LogType.LAND, 0);
					}
					break;
			}
			connection.commit();
		}
		catch(SQLException e)
		{
			writer.append(Responses.genericException(e));
			try
			{
				connection.rollback();
			}
			catch(Exception ex)
			{
				//Ignored
			}
		}
		catch(NationNotFoundException e)
		{
			writer.append(Responses.noNation());
		}
		catch(NumberFormatException e)
		{
			writer.append(Responses.genericError());
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch(Exception e)
			{
				//Ignored
			}
		}


	}
}
