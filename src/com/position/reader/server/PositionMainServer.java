package com.position.reader.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Timer;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.position.message.sender.SenderTask;

public class PositionMainServer extends HttpServlet{
	/**
	 * 
	 */
	private final static Logger log = Logger.getLogger(PositionMainServer.class) ;
	private static final long serialVersionUID = 1L;

	public void init() {
		RelationData.getInstance().fill();
		Reader task = new Reader();
		Thread threadTask = new Thread(task);
		threadTask.start();
	}

	public class Reader implements Runnable {
		@Override
		public void run() {
			try {


				// DBOperator.createInstance().saveOrUpdate("update rfid_cardstate set state=0",
				// null) ;

				IoAcceptor connector = new NioSocketAcceptor();
				connector.setHandler(new PositionHandler());
				connector.getFilterChain().addLast("controler", new IOControler());
				connector.getFilterChain().addLast("excuteThreadPool", new ExecutorFilter(ThreadPoolManager.getThreadPoolInstance()));
				connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ReaderProtocolCodeFactory()));
				connector.setHandler(new PositionHandler());
				// connector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE,
				// timeOut);
				// PositionMessageSender sender = new PositionMessageSender() ;
				// Thread senderThread = new Thread(sender) ;
				// senderThread.start();
				Integer serverPort = 8807 ;
				Object port = RelationData.getInstance().getParas("localPort") ;
				if ( port != null )
				{
					serverPort = Integer.parseInt(port.toString()) ;
				}
				connector.bind(new InetSocketAddress(8807));
				
				PositionSocketManager.getInstance().buildManager(connector); 
				log.info("服务启动准备接收阅读器数据，端口: "+ 8807);
				Timer timer = new Timer() ;
				TagsStateCheckTask task = new TagsStateCheckTask();
				timer.schedule(task, 1000, 1000);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("服务启动失败！ " + e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("服务启动失败！ " + e);
			}
		}

	}

}