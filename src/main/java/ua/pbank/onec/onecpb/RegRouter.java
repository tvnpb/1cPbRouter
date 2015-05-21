package ua.pbank.onec.onecpb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vsevolod on 20.04.2015.
 */
@WebServlet("/*")
public class RegRouter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] url;
        String path;
        path=request.getPathInfo();
        try {
                url = path.split("/");
                if(url.length!=0) {
                    Cookie refCookie = new Cookie("refId", url[1]);
                    refCookie.setPath("/");
                    refCookie.setMaxAge(7200);
                    response.addCookie(refCookie);
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            response.sendRedirect("/register/manually");
        }

    }
}
