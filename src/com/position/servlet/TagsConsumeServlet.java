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
import com.position.reader.server.TagsInforConsumeQueue;
import com.position.util.PositionUtil;

public class TagsConsumeServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(TagsConsumeServlet.class);
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
			if (TagsInforConsumeQueue.getInstance().isEmpty()) {
				pw.write("");
			}
			else{
				Map<String ,Object > tag = TagsInforConsumeQueue.getInstance().takeOne() ;
				
				String html = PositionUtil.createHtmlContent(tag, "tagInforConsume.ftl");
				pw.print(html);
			}
		} catch (Exception e) {
			pw.print("");
			log.error(e);

		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
