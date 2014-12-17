package com.position.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.position.reader.server.CardPool;
import com.position.reader.server.PositionSocketManager;
import com.position.reader.server.RelationData;
import com.position.util.PositionUtil;

public class ReaderInfoServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ReaderInfoServlet.class);
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
			if (PositionSocketManager.getInstance().getIoAcceptor().getManagedSessionCount() == 0) {
				pw.print("");
			} else {

				Map<Long, IoSession> map = PositionSocketManager.getInstance().getIoAcceptor().getManagedSessions();

				String readerId;
			    Object readerName ;
				Object area ;
				Object heartbeat_time ;
				List list = new ArrayList();
				

				Map value =null ;
				InetSocketAddress inetSocketAddress = null;
				for (Entry<Long, IoSession> entry : map.entrySet()) {
					
					
					if ( entry.getValue().isClosing() || ! entry.getValue().isConnected())
						continue ;
					value = new HashMap();
					inetSocketAddress = (InetSocketAddress) entry.getValue().getRemoteAddress();
					readerId = (entry.getValue().getAttribute("readerId") == null) ? "-1" : entry.getValue().getAttribute("readerId").toString();
					value.put("ip", inetSocketAddress.getAddress().getHostAddress());
					value.put("port", inetSocketAddress.getPort());
					value.put("readerId", readerId);
					if (  RelationData.getInstance().getReaderById(readerId) == null){
						readerName = "Î´×¢²á" ;
						area = "Î´×¢²á" ;
						heartbeat_time = null ;
					}
					else
					{
						readerName = RelationData.getInstance().getReaderById(readerId).getValue("readername") ;
						area = RelationData.getInstance().getReaderById(readerId).getValue("area") ;
						heartbeat_time = RelationData.getInstance().getReaderById(readerId).getValue("heartbeat_time") ;
					}
					value.put("readerName",readerName );
					value.put("area", area);
					value.put("heartbeat_time", heartbeat_time);
					value.put("updatetime",new Date() );
					

					list.add(value);
					/*
					 * if ( entry.getValue().getAttribute("readerId") == null )
					 * {
					 * 
					 * }
					 */

				}

				String html = PositionUtil.createHtmlContent(list, "readerInfor.ftl");
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
