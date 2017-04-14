package com.sunvsoft.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sunvsoft.sqlset.SetSql;

public class Sequence {
	private static final Lock LOCK = new ReentrantLock();
	private static long lastTime = System.currentTimeMillis();
	public static long idForOrder;
	/**
	 * 获取idForOrder
	 */
	static {

		ResultSet idResultSet = new SetSql()
				.setSql("select * from order_id_get");
		try {
			if(idResultSet.next()){
			idForOrder = idResultSet.getLong("order_id");}
			else{
				new SetSql().setSqlNotReturn("insert into order_id_get(order_id) values('1')");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("finally")
	public static long nextId() {
		LOCK.lock();
		try {
			boolean done = false;
			while (!done) {
				long now = System.currentTimeMillis();
				if (now == lastTime) {
					try {
						Thread.currentThread();
						Thread.sleep(1);
					} catch (java.lang.InterruptedException e) {
					}
					continue;
				} else {
					lastTime = now;
					++idForOrder;
					new SetSql()
							.setSqlNotReturn("update order_id_get set order_id = "
									+ idForOrder);
					done = true;
				}
			}
		} finally {
			LOCK.unlock();
			return idForOrder;
		}
	}
}