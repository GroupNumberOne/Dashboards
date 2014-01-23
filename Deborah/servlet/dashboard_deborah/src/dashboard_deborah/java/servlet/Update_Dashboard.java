package dashboard_deborah.java.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dashboard_deborah.java.code.tsvtest;

@WebServlet("/update_dashboard")
public class Update_Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		tsvtest executer = new tsvtest();
		try {
			executer.Execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// get response writer
		PrintWriter writer = response.getWriter();
		
		// build HTML code
		String htmlResponse = "<head><meta http-equiv=\"refresh\" content=\"0; url=http://infprj01-56.no-ip.org/dashboard/deborah/\" /></head>";
		writer.println(htmlResponse);
	}
}
