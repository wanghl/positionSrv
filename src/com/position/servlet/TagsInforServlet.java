package com.position.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.position.reader.server.CardPool;
import com.position.util.PositionUtil;

public class TagsInforServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(TagsInforServlet.class) ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		try {
			Map<Object, Map> map = CardPool.getInstance().getTags();
			System.out.println(map.size());
			List<Map> list = new ArrayList<Map>();
			for (Entry<Object, Map> tags : map.entrySet()) {
				tags.getValue().put("physicalid", tags.getKey());

				list.add(tags.getValue());
			}

			String html = PositionUtil.createHtmlContent(list, "tagInfor.ftl");
			pw.print(html);
		} catch (Exception e) {
			pw.print("");
			log.error(e);
			
		}
		finally{
			if ( pw != null)
			{
				pw.close(); 
			}
		}
	}
}
