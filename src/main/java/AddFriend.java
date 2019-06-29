import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * 添加好友
 * @author xinYing
 * @date 2019-06-29
 */
@WebServlet(urlPatterns = "/qq/Add_friend.do")
public class AddFriend extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Override
    public void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        //好友ID
        String account =request.getParameter("Account");
        //被添加好友ID
        String accountAdd =request.getParameter("Account_add");
        //好友分组ID
        String accountGroup =request.getParameter("group_ID");
        PrintWriter pw = response.getWriter();
        System.out.println("AddFriend:"+"ID:"+account+" Account_add:"+accountAdd+" Account_group:"+accountGroup);
        Connection conn = Sql.getConnection();
        //表名
        String tableName="user_friend";
        String sql = "INSERT into "+tableName+" VALUES((select ID FROM user where Account=?),?,?,1)";
        try {
            Map<String,String> map = new HashMap<>(16);
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, account);
            ps.setString(2, accountAdd);
            ps.setString(3, accountGroup);
            System.out.println("AddFriend"+ps.toString());
            int len =   ps.executeUpdate();
            if(len==1){
                map.put("name", "添加好友成功");
            }else{
                pw.write("{\"name\":\"添加好友失败\"}");
            }
            JSONObject jo=JSONObject.fromObject(map);
            pw.write(jo.toString());
        } catch (SQLException e) {
            pw.write("{\"name\":\"改好友已存在\"}");
        }finally {
            Sql.exit(conn);
        }
    }

}
