package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ItemsAPI") 
public class ItemAPI  extends HttpServlet {
	
	Item itemObj = new Item();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 String output = itemObj.insertItem(request.getParameter("itemName"),
			request.getParameter("itemPrice"),
			request.getParameter("category"),
			request.getParameter("userid"));
			response.getWriter().write(output);
			}
	
	
	
	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request)
	{
	 Map<String, String> map = new HashMap<String, String>();
	try
	 {
	 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
	 String queryString = scanner.hasNext() ?
	 scanner.useDelimiter("\\A").next() : "";
	 scanner.close();
	 String[] params = queryString.split("&");
	 for (String param : params)
	 { 
		 String[] p = param.split("=");
		 map.put(p[0], p[1]);
		 }
		 }
		catch (Exception e)
		 {
		 }
		return map;
		}
	
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 Map paras = getParasMap(request);
			 String output = itemObj.updateItem(paras.get("hidItemIDSave").toString(),
			paras.get("itemname").toString(),
			paras.get("itemPrice").toString(),
			paras.get("category").toString(),
			paras.get("userid").toString());
			response.getWriter().write(output);
			} 
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 Map paras = getParasMap(request);
			 String output = itemObj.deleteItem(paras.get("itemID").toString());
			response.getWriter().write(output);
			}
}
