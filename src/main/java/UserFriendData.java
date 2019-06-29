import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 获取用户好友信息
 * @author 星辰520
 */
@WebServlet(urlPatterns = "/qq/UserFriendData.do")
public class UserFriendData extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String id =request.getParameter("username");
		PrintWriter pw = response.getWriter();
		Connection conn = Sql.getConnection();
		String sql = "select g.`name`,f.friend_ID from user_group g right JOIN (select friend_ID,group_ID,start from user_friend where ID=(select ID from user where Account=?) and start=0) f on g.id=f.group_ID ORDER BY g.id";
		StringBuilder builder = new StringBuilder();
		try {
			PreparedStatement  ps =conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(id));
			ResultSet resultSet =ps.executeQuery();
			String str = "";
			while(resultSet.next()){
				String groupingName = resultSet.getString(1);
				if(groupingName.equals(str)){
					builder.append(resultSet.getString(2)).append(",");
				}else{
					builder.append("|").append(resultSet.getString(1)).append(",").append(resultSet.getString(2)).append(",");
					str=resultSet.getString(1);
				}
			}
			System.out.println("UserFriendData:"+builder);
			pw.write("{\"name\":\""+builder.toString()+"\"}");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Sql.exit(conn);
		}
	}
}
