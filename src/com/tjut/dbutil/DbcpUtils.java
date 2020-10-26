package com.tjut.dbutil;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

import com.tjut.bean.Account;


public class DbcpUtils {
	
	//从连接池中获得连接
	private static Connection getConnection()
    {
		Connection conn = null;
		try{
			Context ctx = new InitialContext();
			// DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/DBPool");		//DBPool和Context.xml中的Resource name="jdbc/DBPool"相同
			
			Context initContext = new InitialContext();   
			Context envContext = (Context)initContext.lookup("java:/comp/env");      
			DataSource ds = (DataSource)envContext.lookup("jdbc/xinbai");
			
			conn=ds.getConnection();
		}catch(Exception e){
			e.printStackTrace();           
		}     
		return conn;
    }
	
	//释放连接等资源
	public static void close(Connection conn,Statement st,ResultSet rs){
		try{
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//登陆判断
	public static Boolean executeQuery(String SQL)
    {  
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Boolean existUser = false;
        try{ 
           conn = getConnection();        
           st = conn.createStatement();
           rs = st.executeQuery(SQL); 
           if(rs.next()) {
        	   existUser = true;
           }
           return existUser;
        }
        catch(Exception e){
            e.printStackTrace();        
        }finally {
            close(conn,st,rs);
        }
        return false;
    }
	
	//查询账户信息
	public static Account AccountQuery(String SQL,Object args[])
    {  
		Account account = new Account();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd=null;
        try{ 
           conn = getConnection();
           ps = conn.prepareStatement(SQL);    
           // 设置参数  
           if (args != null && args.length > 0) {  
               for (int i = 0; i < args.length; i++) {  
                   ps.setObject(i + 1, args[i]);  
               }  
           }             
           rs = ps.executeQuery();
           while(rs.next()) {
        	   account.setAccount_id(rs.getInt("account_id"));
        	   account.setAccount_code(rs.getString("password"));
        	   account.setAccount_num(rs.getString("account_num"));
        	   account.setBalance(rs.getFloat("balance"));
        	   account.setCustomer_id(rs.getInt("customer_id"));
        	   account.setStatus(rs.getInt("status"));
           }
        }
        catch(Exception e){
            e.printStackTrace();        
        } 
        finally{
            close(conn,ps,rs);
        }
        return account;
    }
	
	//查询城市
	public static String QueryPC(String SQL)
    {  
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Boolean existUser = false;
        String name = "";
        try{ 
           conn = getConnection();        
           st = conn.createStatement();
           rs = st.executeQuery(SQL); 
           if(rs.next()) {
        	   name = rs.getString("name");
           }
        }
        catch(Exception e){
            e.printStackTrace();        
        }finally {
            close(conn,st,rs);
        }
        return name;
    }
	//查询账户
	public static String Querynum(String SQL)
    {  
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        String account_num = "";
        try{ 
           conn = getConnection();        
           st = conn.createStatement();
           rs = st.executeQuery(SQL); 
           if(rs.next()) {
        	   account_num = rs.getString("account_num");
           }
        }
        catch(Exception e){
            e.printStackTrace();        
        }finally {
            close(conn,st,rs);
        }
        return account_num;
    }	
	
	
	public static String CustomerQuery(String SQL)
    {  
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Boolean existUser = false;
        String customer_id = "";
        try{ 
           conn = getConnection();        
           st = conn.createStatement();
           rs = st.executeQuery(SQL); 
           if(rs.next()) {
        	   customer_id = rs.getString("customer_id");
           }
        }
        catch(Exception e){
            e.printStackTrace();        
        }finally {
            close(conn,st,rs);
        }
        return customer_id;
    }
	
	public static String EmployeeQuery(String SQL)
    {  
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Boolean existUser = false;
        String customer_id = "";
        try{ 
           conn = getConnection();        
           st = conn.createStatement();
           rs = st.executeQuery(SQL); 
           if(rs.next()) {
        	   customer_id = rs.getString("customer_id");
           }
        }
        catch(Exception e){
            e.printStackTrace();        
        }finally {
            close(conn,st,rs);
        }
        return customer_id;
    }
	
	//修改或添加账户信息
	public static boolean AccountUpdate(String SQL, Object[] args)
    {  
		boolean flag=false;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getConnection();              
            ps = conn.prepareStatement(SQL);    
            // 设置参数  
            if (args != null && args.length > 0) {  
               for (int i = 0; i < args.length; i++) {  
                   ps.setObject(i + 1, args[i]);  
               }  
            }             
            int result = ps.executeUpdate();    //返回受影响的行数        
            if(result > 0)
               flag=true;
        }catch(Exception e){
            e.printStackTrace();           
        }finally {
            close(conn,ps,null);
        }
        return flag;
    }
	
	//批处理执行语句
	public static boolean executeBatch(ArrayList<String> listSql,Object args[][]) {		// 多条sql语句，其中可能有多个占位符
		Connection conn=null;
		boolean blnFlag=true;		
		PreparedStatement ps=null;		
		int index=0;
		try{
			if(conn==null || conn.isClosed() || conn.isReadOnly()){
				conn=getConnection();				
			}
			conn.setAutoCommit(false);			
            for (int i = 0; i < listSql.size(); i++) {
            	ps=conn.prepareStatement(listSql.get(i));		// 每循环一次就会初始化一个新的
            	if (args!= null && args.length > 0) {  
                    for (int j = 0; j< args[i].length; j++) {
                    	++index;			// 问号从1开始
                        ps.setObject(index, args[i][j]);                         
                    }  
                 } 
                 ps.addBatch();            
                 ps.executeBatch(); 	// 不允许拿到循环外，这样只执行最后一条/ps=conn.prepareStatement(listSql.get(i));
                 index=0;
            }
            conn.commit();
            conn.setAutoCommit(true);
        }catch(Exception e){
			e.printStackTrace();
			try{
				conn.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
			blnFlag=false;
		}finally{
			close(conn,ps,null);
		}
		return blnFlag;
	}
	
	//可返回多行满足条件的记录
	public static ArrayList<HashMap<String,String>> executeQuery(String sql,int rowCount,int page){
		// 请求的是第几页的第几行数据
		Connection conn=null;
		ArrayList<HashMap<String,String>> list=null;
		Statement st=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		try{
			conn=getConnection();			
			st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(rowCount>0)
			     st.setMaxRows(page*rowCount);	//计算当前页以及之前的所有记录数，超过当前页的记录不在查询
			rs=st.executeQuery(sql);
			if(page>=0 && rowCount>0)			// 由于游标初始时指向page第一条记录的前一条记录，也即是-1
				rs.absolute((page-1)*rowCount);		// 移动到上一页的最后一条记录，用来遍历现在这一页的所有记录
			rsmd=rs.getMetaData();
			list=new ArrayList<HashMap<String,String>>();
			
			while(rs.next()){
				int columnCount=rsmd.getColumnCount();
				HashMap<String,String> hash=new HashMap<String,String>();				
				for(int i=1;i<=columnCount;i++){
					hash.put(rsmd.getColumnName(i),rs.getString(i));
				}
				list.add(hash);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(conn,st,rs);
		}
		return list;		
	}
	//执行单条非预编译的查询语句,可返回多条满足条件的记录
	public static ArrayList<HashMap<String,String>> executeQuery1(String SQL)
    {  
		ArrayList<HashMap<String,String>> list=null;
		HashMap<String,String> hash=null;
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd=null;
        try{ 
           conn = getConnection();        
           st = conn.createStatement();
           rs = st.executeQuery(SQL); 
           rsmd=rs.getMetaData();
           list=new ArrayList<HashMap<String,String>>();
           while(rs.next()) {
        	   int columnCount=rsmd.getColumnCount();
        	   hash=new HashMap<String,String>();
        	   for(int i=1;i<=columnCount;i++) {
        		   hash.put(rsmd.getColumnName(i), rs.getString(i));
        	   }
        	   list.add(hash);
           }
           return list;
        }
        catch(Exception e){
            e.printStackTrace();        
        }finally {
            close(conn,st,rs);
        }
        return null;
    }
	
	//一条预编译的查询语句，args为参数数组
	public static ArrayList<HashMap<String,String>> executeQuery(String SQL,Object args[])
    {  
		ArrayList<HashMap<String,String>> list=null;
		HashMap<String,String> hash=null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd=null;
        try{ 
           conn = getConnection();
           ps = conn.prepareStatement(SQL);    
           // 设置参数  
           if (args != null && args.length > 0) {  
               for (int i = 0; i < args.length; i++) {  
                   ps.setObject(i + 1, args[i]);  
               }  
           }             
           rs = ps.executeQuery();
           rsmd=rs.getMetaData();
           list=new ArrayList<HashMap<String,String>>();
           while(rs.next()) {
        	   int columnCount=rsmd.getColumnCount();
        	   hash=new HashMap<String,String>();
        	   for(int i=1;i<=columnCount;i++) {
        		   hash.put(rsmd.getColumnName(i), rs.getString(i));
        	   }
        	   list.add(hash);
           }
           return list;
        }
        catch(Exception e){
            e.printStackTrace();        
        } 
        finally{
            close(conn,ps,rs);
        }
        return null;
    }
	
}