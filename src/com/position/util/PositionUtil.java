package com.position.util;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import freemarker.template.Configuration;
import freemarker.template.Template;

public class PositionUtil {
	

	public static String createHtmlContent(List list, String templateFilenName) {
		Map<String, List> data = new HashMap<String, List>();
		data.put("items", list);

		StringWriter sw = new StringWriter();
		try {

			Configuration cfg = new Configuration();
			// System.out.println(
			// MCEUtil.class.getResource("/report.ftl").getFile() );
			cfg.setDirectoryForTemplateLoading(new File(PositionUtil.class.getResource("/").getFile()));

			Template temp = cfg.getTemplate(templateFilenName, "utf-8");

			temp.process(data, sw);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return sw.toString();
	}

}
