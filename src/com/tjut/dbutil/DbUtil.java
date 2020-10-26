package com.tjut.dbutil;

import java.util.ArrayList;
import java.util.HashMap;

public class DbUtil {
	//获得行数
	public static int getRowsCount(String sql) {
		int count=0;
		ArrayList<HashMap<String, String>> list = null;
		try {
			list=DbcpUtils.executeQuery1(sql);
			if(list!=null)
				count=list.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//判断表中某个字段是否存在
		public static boolean judge(String table,String field,String value) {
			boolean flag = false;
			try {
				String sql = "select * from "+table+" where "+field+"=?";
				Object args[] = {value};
				int count=DbcpUtils.executeQuery(sql,args).size();
				if(count>0) {
					flag=true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}
	
}
