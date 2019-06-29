import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
/**
 * 查找网友
 * @author xinying
 * @date 2019-06-29
 */
@WebServlet(urlPatterns = "/qq/Find_ID.do")
public class Find_ID extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String account =request.getParameter("ID");
		PrintWriter pw = response.getWriter();
		Connection conn = Sql.getConnection();
		String sql ="select Account,username,url from user where Account=? or username=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,account);
			ps.setString(2,account);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()){
				resultSet.previous();
				Map<String,List<String>> map = new HashMap<>(16);
				List<String> listAccount = new ArrayList<>();
				List<String> listUsername = new ArrayList<>();
				List<String> listUrl= new ArrayList<>();

				while(resultSet.next()){
					listAccount.add(resultSet.getString(1));
					listUsername.add(resultSet.getString(2));
					listUrl.add(resultSet.getString(3));
					
				}
				map.put("ID", listAccount);
				map.put("name",listUsername);
				map.put("url", listUrl);
				JSONObject jo=JSONObject.fromObject(map);
				pw.write(jo.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Sql.exit(conn);
		}
	}
}
