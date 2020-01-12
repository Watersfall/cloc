package com.watersfall.clocgame.servlet.controller;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.database.Database;
import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.NotLoggedInException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.Production;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.util.UserUtils;
import com.watersfall.clocgame.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/production/*"})
public class ProductionController extends HttpServlet
{
	public final static String URL = "/{id}";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HashMap<String, String> url = Util.urlConvert(URL, request.getPathInfo());
		if(url.get("id") != null && url.get("id").equalsIgnoreCase("all"))
		{
			request.getRequestDispatcher("/WEB-INF/view/includes/allproduction.jsp").forward(request, response);
		}
		else
		{
			request.getRequestDispatcher("/WEB-INF/view/production.jsp").forward(request, response);
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Connection conn = null;
		PrintWriter writer = resp.getWriter();
		HashMap<String, String> url = Util.urlConvert(URL, req.getPathInfo());
		try
		{
			conn = Database.getDataSource().getConnection();
			int user = UserUtils.getUser(req);
			int productionId = Integer.parseInt(url.get("id"));
			Nation nation = UserUtils.getUserNation(conn, true, req);
			if(productionId != 0 && nation.getProductionById(productionId) == null)
			{
				writer.append(Responses.notYourCity());
			}
			else
			{
				if(req.getParameter("factories") != null)
				{
					int newFactoryCount = Integer.parseInt(req.getParameter("factories"));
					Technologies newProduction = Technologies.valueOf(req.getParameter("production"));
					if(newProduction.getTechnology().isProducible())
					{
						if((newFactoryCount - nation.getProductionById(productionId).getFactories()) <= nation.getFreeFactories())
						{
							if(!newProduction.name().equalsIgnoreCase(nation.getProductionById(productionId).getProduction()))
							{
								nation.getProductionById(productionId).setEfficiency((int)(nation.getProductionById(productionId).getEfficiency() * 0.5));
							}
							if(newFactoryCount > nation.getProductionById(productionId).getFactories())
							{
								int newEfficiency = ((nation.getProductionById(productionId).getFactories() * nation.getProductionById(productionId).getEfficiency()) +
										(newFactoryCount - nation.getProductionById(productionId).getFactories() * 2500)) / newFactoryCount;
								nation.getProductionById(productionId).setEfficiency(newEfficiency);
							}
							nation.getProductionById(productionId).setFactories(newFactoryCount);
							nation.getProductionById(productionId).setProduction(newProduction.name());
							if(nation.getProductionById(productionId).getEfficiency() < 2500)
							{
								nation.getProductionById(productionId).setEfficiency(2500);
							}
							writer.append(Responses.updated());
						}
						else
						{
							writer.append("Not enough factories: " + (newFactoryCount - nation.getProductionById(productionId).getFactories()));
						}
					}
					else
					{
						writer.append(Responses.genericError());
					}
				}
				else if(req.getParameter("delete") != null)
				{
					Production.deleteProductionById(productionId, conn);
				}
				else if(req.getParameter("new") != null)
				{
					if(nation.getFreeFactories() <= 0)
					{
						writer.append("Not enough factories");
					}
					else
					{
						Production.createDefaultProduction(user, conn);
					}
				}
			}
			nation.update();
			conn.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try
			{
				conn.rollback();
			}
			catch(Exception ex)
			{
				//Ignore
			}
			writer.append(Responses.genericException(e));
			e.printStackTrace();
		}
		catch(NotLoggedInException e)
		{
			writer.append(Responses.noLogin());
		}
		catch(NumberFormatException | NullPointerException e)
		{
			writer.append(Responses.genericError());
			e.printStackTrace();
		}
		catch(NationNotFoundException e)
		{
			writer.append(Responses.noNation());
		}
		catch(CityNotFoundException e)
		{
			writer.append(Responses.noCity());
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch(Exception ex)
			{
				//Ignore
			}
		}
	}
}
