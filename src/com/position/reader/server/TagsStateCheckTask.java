package com.position.reader.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class TagsStateCheckTask extends TimerTask {
	
	private final static Logger log = Logger.getLogger(TagsStateCheckTask.class) ;
	@Override
	public void run() {

		//Map<Object, Map> map = CardPool.getInstance().getTags();
		try {

			for (Entry<Object, Map> entry : CardPool.getInstance().getTags().entrySet()) {
				if (!isTagAlive((Date) entry.getValue().get("updatetime"))) 
				{
					entry.getValue().put("positionx", RelationData.getInstance().getParas("positionBase")) ;
					entry.getValue().put("positiony", RelationData.getInstance().getParas("positionBase")) ;
					entry.getValue().put("positionz", RelationData.getInstance().getParas("positionBase")) ;
				}
				
			}

		} catch (ParseException e) {
			
			log.error(e); 
		}
	}

	public boolean isTagAlive(Date updatedate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();

		java.util.Date now = df.parse(df.format(calendar.getTime()));
		java.util.Date date = df.parse(df.format(updatedate));
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		if (day > 0 || hour > 0 || min > 0 || s >= 2)
			return false;
		return true;
	}

}
