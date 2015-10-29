import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class FillData{
	public static final int MAX_THREADS = 100;



	public static void main(String args[]) throws ClassNotFoundException, PropertyVetoException, SQLException{

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
		devices.add("alpha");
		devices.add("beta");
		devices.add("gamma");
		devices.add("lambda");
		devices.add("pi");

		int rs ;
		String stmt = "insert into usagedata(cust_id,dev_id,website,usage_bytes,start_time,end_time)values (?, ?, ?, ?, ?, ?);";
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver
		cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/narcos" );
		cpds.setUser("root");
		cpds.setPassword("8088");

		cpds.setMinPoolSize(100);
		cpds.setInitialPoolSize(100);
		cpds.setAcquireIncrement(50);
		cpds.setMaxPoolSize(2000);


		/*	String host = "jdbc:mysql://localhost:3306/narcos";
			String url = host;
			String user = "root";*/
		Random random = new Random();
		ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
		for (int i = 0; i < MAX_THREADS; i++) {
			threadPool.submit(new Runnable() {
				public void run() {
					while(true){
						try{
							Connection con = cpds.getConnection();
							PreparedStatement pstmt;
							pstmt = con.prepareStatement(stmt);
							pstmt.setInt(1, random.nextInt(5000000));
							pstmt.setInt(4, random.nextInt(99999999));
							Date start = new Date(System.currentTimeMillis()-random.nextInt(5)*random.nextInt(60)*random.nextInt(1000));
							Date end = new Date(System.currentTimeMillis());
							pstmt.setString(2, devices.get(random.nextInt(5)));
							pstmt.setString(3, websites.get(random.nextInt(10)));
							Timestamp timestamp = new Timestamp(start.getTime());
							Timestamp timestamp2 = new Timestamp(end.getTime());
							pstmt.setTimestamp(5,timestamp);
							pstmt.setTimestamp(6,timestamp2);
							
							int rs = pstmt.executeUpdate();
							con.close();
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
}









