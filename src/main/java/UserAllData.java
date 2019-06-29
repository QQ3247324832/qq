import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
/**
 * 获取所有信息
 * @author 星辰520
 * @date 2019-06-29
 */
@WebServlet(urlPatterns = "/qq/UserAllData.do")
public class UserAllData extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String id =request.getParameter("username");
		PrintWriter pw = response.getWriter();
		System.out.println("UserAllData:ID:"+id);
		Connection conn = Sql.getConnection();
		String sql = "select * from user where Account=?";
		try {
			Cookie[] cookies =request.getCookies();
			System.out.println("cookie："+cookies[0].getValue());
			}catch(Exception e){
				pw.write("{\"Account\":\"非法访问\"}");
				return;
			}
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,id);
			ResultSet resultSet = preparedStatement.executeQuery();

			Map<String,String> map = new HashMap<>(16);
			if(resultSet.next()){
				map.put("Account", resultSet.getString("Account"));
				map.put("password", resultSet.getString("password"));
				map.put("username", resultSet.getString("username"));
				map.put("state", resultSet.getString("state"));
				map.put("url", resultSet.getString("url"));
				map.put("register_date", resultSet.getString("register_date"));
			}
			System.out.println("测试");
			JSONObject jo=JSONObject.fromObject(map);
			pw.write(jo.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Sql.exit(conn);
		}
		
	}
}
