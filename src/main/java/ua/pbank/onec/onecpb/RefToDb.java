package ua.pbank.onec.onecpb;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/reference")
public class RefToDb extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name;
        String email;
        String login;
        String phone;
        String refId;
        Connection con;
        PreparedStatement pStmt;
        request.setCharacterEncoding("UTF-8");

            refId = request.getParameter("ref_id");
            name = request.getParameter("name");
            email = request.getParameter("email");
            login = request.getParameter("login");
            phone = request.getParameter("phone");
            if(refId != null && name != null && email!=null && login!=null){
                try {
                    InitialContext ic = new InitialContext();
                    Context xmlContext = (Context) ic.lookup("java:comp/env");
                    DataSource myDatasource;
                    myDatasource = (DataSource) xmlContext.lookup("jdbc/freshStatDS");
                    con = myDatasource.getConnection();
                    String query = "INSERT INTO pb_register_users (ref_id, fio, email, login, phone) values(?, ?, ?, ?, ?)";
                    pStmt = con.prepareStatement(query);
                    pStmt.setString(1, refId);
                    pStmt.setString(2, name);
                    pStmt.setString(3, email);
                    pStmt.setString(4, login);
                    pStmt.setString(5, phone.replace("(", "").replace(")", "").replace(" ", ""));
                    pStmt.executeUpdate();
                    pStmt.close();
                    con.close();
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
