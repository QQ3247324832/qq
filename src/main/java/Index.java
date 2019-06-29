import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 登录
 * @author 星辰520 
 * @date 2019-06-29
 */
@WebServlet(urlPatterns = "/qq/index.do")
public class Index extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String id =request.getParameter("username");
		String password =request.getParameter("password");
		PrintWriter pw = response.getWriter();
		System.out.println("Index"+"id:"+id+" password:"+password);
		Connection conn = Sql.getConnection();
		String sql ="select count(*) from user where Account=? and password=?";
		try {
			PreparedStatement ps =conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.setString(2, password);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()){
				Cookie cookie = new Cookie("name",id);
				response.addCookie(cookie);
				pw.write("{\"name\":\"成功\"}");
			}else{
				pw.write("{\"name\":\"失败\"}");
			}
		} catch (SQLException e) {
			System.out.println("Index:数据库语句错误");
			e.printStackTrace();
		}finally {
			Sql.exit(conn);
		}
	}
}
