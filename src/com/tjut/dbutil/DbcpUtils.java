package com.tjut.dbutil;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

import com.tjut.bean.Account;


public class DbcpUtils {
	
	//�����ӳ��л������
	private static Connection getConnection()
    {
		Connection conn = null;
		try{
			Context ctx = new InitialContext();
			// DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/DBPool");		//DBPool��Context.xml�е�Resource name="jdbc/DBPool"��ͬ
			
			Context initContext = new InitialContext();   
			Context envContext = (Context)initContext.lookup("java:/comp/env");      
			DataSource ds = (DataSource)envContext.lookup("jdbc/xinbai");
			
			conn=ds.getConnection();
		}catch(Exception e){
			e.printStackTrace();           
		}     
		return conn;
    }
	
	//�ͷ����ӵ���Դ
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
	
	//��½�ж�
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
	
	//��ѯ�˻���Ϣ
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
           // ���ò���  
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
	
	//��ѯ����
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
	//��ѯ�˻�
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
	
	//�޸Ļ�����˻���Ϣ
	public static boolean AccountUpdate(String SQL, Object[] args)
    {  
		boolean flag=false;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getConnection();              
            ps = conn.prepareStatement(SQL);    
            // ���ò���  
            if (args != null && args.length > 0) {  
               for (int i = 0; i < args.length; i++) {  
                   ps.setObject(i + 1, args[i]);  
               }  
            }             
            int result = ps.executeUpdate();    //������Ӱ�������        
            if(result > 0)
               flag=true;
        }catch(Exception e){
            e.printStackTrace();           
        }finally {
            close(conn,ps,null);
        }
        return flag;
    }
	
	//������ִ�����
	public static boolean executeBatch(ArrayList<String> listSql,Object args[][]) {		// ����sql��䣬���п����ж��ռλ��
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
            	ps=conn.prepareStatement(listSql.get(i));		// ÿѭ��һ�ξͻ��ʼ��һ���µ�
            	if (args!= null && args.length > 0) {  
                    for (int j = 0; j< args[i].length; j++) {
                    	++index;			// �ʺŴ�1��ʼ
                        ps.setObject(index, args[i][j]);                         
                    }  
                 } 
                 ps.addBatch();            
                 ps.executeBatch(); 	// �������õ�ѭ���⣬����ִֻ�����һ��/ps=conn.prepareStatement(listSql.get(i));
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
	
	//�ɷ��ض������������ļ�¼
	public static ArrayList<HashMap<String,String>> executeQuery(String sql,int rowCount,int page){
		// ������ǵڼ�ҳ�ĵڼ�������
		Connection conn=null;
		ArrayList<HashMap<String,String>> list=null;
		Statement st=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		try{
			conn=getConnection();			
			st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(rowCount>0)
			     st.setMaxRows(page*rowCount);	//���㵱ǰҳ�Լ�֮ǰ�����м�¼����������ǰҳ�ļ�¼���ڲ�ѯ
			rs=st.executeQuery(sql);
			if(page>=0 && rowCount>0)			// �����α��ʼʱָ��page��һ����¼��ǰһ����¼��Ҳ����-1
				rs.absolute((page-1)*rowCount);		// �ƶ�����һҳ�����һ����¼����������������һҳ�����м�¼
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
	//ִ�е�����Ԥ����Ĳ�ѯ���,�ɷ��ض������������ļ�¼
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
	
	//һ��Ԥ����Ĳ�ѯ��䣬argsΪ��������
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
           // ���ò���  
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