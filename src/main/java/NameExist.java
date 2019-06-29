import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户名验证
 * @author xinYing
 * @date 2019-06-29
 */
@WebServlet(urlPatterns = "/qq/name.do")
public class NameExist extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String username =request.getParameter("username");
		PrintWriter pw = response.getWriter();
		System.out.println("NameExist:"+username);
		Connection conn = Sql.getConnection();
		String sql = "select * from user where Account=?";
		try {
			PreparedStatement ps =conn.prepareStatement(sql);
			ps.setString(1,username );
			ResultSet resultSet =ps.executeQuery();
			if(resultSet.next()){
				pw.write("{\"name\":\"0\"}");
			}else{
				pw.write("{\"name\":\"-1\"}");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Sql.exit(conn);
		}
	}
}
