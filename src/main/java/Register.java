import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 注册
 * @author 星辰520
 */
@WebServlet(urlPatterns = "/qq/register.do")
public class Register extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String name =request.getParameter("name");
		String username =request.getParameter("username");
		String password =request.getParameter("password");
		name=URLDecoder.decode(name,"utf-8");
		PrintWriter pw = response.getWriter();
		System.out.println("Register:"+"name"+name+" username:"+username+" password:"+password);
		Connection conn = Sql.getConnection();
		String sql = "insert into user values(null,?,?,?,0,'mr.png',now())";
		try {
			PreparedStatement  ps =conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(username));
			ps.setString(2, password);
			ps.setString(3, name);
			int len =ps.executeUpdate();
			if(len==1){
					pw.write("{\"name\":\"成功\"}");
				}else{
					pw.write("{\"name\":\"失败\"}");
				}
			} catch (SQLException e) {
				pw.write("{\"name\":\"失败\"}");
				e.printStackTrace();
			}finally {
				Sql.exit(conn);
			}
	}
}
