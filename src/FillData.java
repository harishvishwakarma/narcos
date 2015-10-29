import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FillData{
	public static final int MAX_THREADS = 100;
	
	
	
	public static void main(String args[]) throws ClassNotFoundException{

		Class.forName("com.mysql.jdbc.Driver");
		ArrayList<String> websites = new ArrayList<String>();
		websites.add("www.google.com");
		websites.add("www.facebook.com");
		websites.add("www.instagram.com");
		websites.add("web.whatsapp.com");
		websites.add("www.thenextweb.com");
		websites.add("www.twitter.com");
		websites.add("www.forbes.com");
		websites.add("www.9gag.com");
		websites.add("www.mashable.com");
		websites.add("www.stackoverflow.com");
		websites.add("www.github.com");
		
		
		ArrayList<String> devices = new ArrayList<String>();
		
		
		Connection con = null;
		int rs ;
		PreparedStatement pstmt;
		String stmt = "insert into usagedata(cust_id,dev_id,website,usage_bytes,start_time,end_time)values (?, 'd', ?, ?, ?, ?);";


		try {
			String host = "jdbc:mysql://localhost:3306/narcos";
			String url = host;
			String user = "root";
			Random random = new Random();
			String password = "8088";
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(stmt);
			 ExecutorService threadPool = Executors.newFixedThreadPool(10);
			 for (int i = 0; i < MAX_THREADS; i++) {
			    threadPool.submit(new Runnable() {
			         public void run() {
			        	 int rs;
			     		while(true){
			     		try{
			     		pstmt.setInt(1, random.nextInt(5000000));
			     		pstmt.setInt(3, random.nextInt(99999999));
			     		Date start = new Date(System.currentTimeMillis()-random.nextInt(5)*random.nextInt(60)*random.nextInt(1000));
			     		Date end = new Date(System.currentTimeMillis());
			     		pstmt.setString(2, websites.get(random.nextInt(10)));
			     		Timestamp timestamp = new Timestamp(start.getTime());
			     		Timestamp timestamp2 = new Timestamp(end.getTime());
			     		pstmt.setTimestamp(4,timestamp);
			     		pstmt.setTimestamp(5,timestamp2);
			     		rs = pstmt.executeUpdate();
			     		}
			     		catch(SQLException e){
			     			e.printStackTrace();
			     		}
			     		}
			         }
			     });
			 }

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
}

