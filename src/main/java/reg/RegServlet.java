package reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "root", "Admin");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname = request.getParameter("fullname");
		String uname = request.getParameter("username");
		String email = request.getParameter("email");
		String pword = request.getParameter("password");
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into reg values (?,?,?,?)");
			pstmt.setString(1, fname);
			pstmt.setString(2, uname);
			pstmt.setString(3, email);
			pstmt.setString(4, pword);
			pstmt.executeUpdate();
			
			PrintWriter pw = response.getWriter();
			pw.println("User Registered Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
