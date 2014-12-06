package com.position.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zephyr.sql.DBConnection;

public class DBOperator {
	
	private DBOperator() {};
	
	public static DBOperator createInstance()
	{
		return new DBOperator() ;
	}

	private final static Logger log = Logger.getLogger(DBOperator.class);

	public List<DBInstance> query(String sql, Object[] paras) throws SQLException {
		DBConnection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmeta = null;
		DBInstance dbInstance = null;
		List<DBInstance> list = new ArrayList<DBInstance>();
		try {
			connection = DBConnection.getInstance("tools");
			ps = connection.prepareStatement(sql);

			int j = 1;
			if (paras != null) {
				for (int i = 0; i < paras.length; i++) {

					ps.setObject(j, paras[i]);
					j++;
				}
			}
			rs = ps.executeQuery();

			rsmeta = rs.getMetaData();
			int numberOfMetaData = rsmeta.getColumnCount();
			while (rs.next()) {
				dbInstance = new DBInstance();
				for (int r = 1; r < numberOfMetaData + 1; r++) {
					dbInstance.putValue(rsmeta.getColumnName(r), rs.getObject(r));

				}
				list.add(dbInstance);
			}
		} catch (SQLException e) {
			log.error("数据库操作失败,sql :" + sql);
			log.error(e);
			throw e;
		} finally {

			if (connection != null) {
				connection.close();
			}
		}

		return list;
	}

	public List<DBInstance> saveOrUpdate(String sql, DBInstance parmeter) throws Exception {

		DBConnection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmeta = null;
		List<DBInstance> list = new ArrayList<DBInstance>();
		DBInstance dbInstance = null;
		try {
			connection = DBConnection.getInstance("tools");
			ps = connection.prepareStatement(sql);
			if (parmeter != null) {
				setValue(ps, sql, parmeter);
				if (sql.toLowerCase().startsWith("select")) {
					rs = ps.executeQuery();

					rsmeta = rs.getMetaData();
					int numberOfMetaData = rsmeta.getColumnCount();
					while (rs.next()) {
						dbInstance = new DBInstance();
						for (int r = 1; r < numberOfMetaData + 1; r++) {
							dbInstance.putValue(rsmeta.getColumnName(r), rs.getObject(r));
						}
						list.add(dbInstance);
					}
				} else {
					ps.executeUpdate();
				}
			} else {
				ps.executeUpdate();
			}

		} catch (Exception e) {
			log.error("数据库操作失败,sql :" + sql);
			log.error(e);
			throw e;
		} finally {
			if (connection != null) {
				connection.close();

			}

		}

		return list;

	}

	private void setValue(PreparedStatement ps, String sql, DBInstance map) throws SQLException {
		if (sql.startsWith("insert")) {
			sql = sql.substring(0, sql.indexOf("values") - 1);
			sql = sql.substring(sql.indexOf("(") + 1, sql.length()).replace(")", "");
			String[] paras = sql.split(",");
			for (int i = 1; i <= paras.length; i++) {
				ps.setObject(i, map.getValue(paras[i - 1].toString().trim()));
			}
		} else {
			String[] p = sql.split(" ");
			String paras;
			for (int i = 1; i <= p.length; i++) {
				if (p[i].startsWith("=")) {
					paras = p[i - 1].trim();
					ps.setObject(i, map.getValue(paras));
				} else if (p[i].indexOf("=") > 0) {
					paras = p[i].split("=")[0].replace("?", "").replace(",", "").trim();
					ps.setObject(i, map.getValue(paras));
				}
			}
		}
	}

}
