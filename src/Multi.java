import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

class Multi extends Thread{  
	PreparedStatement pstmt;
	ArrayList<String> websites;
	
	Random random = new Random();
	
	public Multi(PreparedStatement pstmt, ArrayList<String> websites){
		this.pstmt=pstmt;
		this.websites=websites;
	}
	public void run(){ 
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
};
