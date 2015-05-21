package ua.pbank.onec.onecpb;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Vsevolod on 09.04.2015.
 */
//@WebServlet("/p24")
public class TariffRouter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root;
        String abonentId = "";
        String email = "";
        String fio = "";
        String telefon = "";
        String startSubscribe ="";
        String endSubscribe = "";
        String tarif="";
        String users="";
        String date="";
        String validTo="";
        String amount="";
        String urlInvoice="";
        String urlp24 = "";
        Map<Integer, Map<String, String>> invoices = new HashMap<Integer, Map<String, String>>();

        URLConnection con;
        URL url;
        PrintWriter out = response.getWriter();

        try {
            url = new URL("http://172.16.178.2:8008/bill/hs/stat/abonentinfo?login=mikhail.kozmenkov@privatbank.ua&indent");
            con = url.openConnection();
            con.setConnectTimeout(2000); // 2 seconds
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(con.getInputStream());
            root = document.getDocumentElement();

            if(root.getElementsByTagName("abonentId").getLength()!=0) abonentId = root.getElementsByTagName("abonentId").item(0).getFirstChild().getNodeValue();
            if(root.getElementsByTagName("fio").getLength()!=0) fio = root.getElementsByTagName("fio").item(0).getFirstChild().getNodeValue();
            if(root.getElementsByTagName("telefon").item(0).getChildNodes().getLength()!=0) telefon = root.getElementsByTagName("telefon").item(0).getFirstChild().getNodeValue();
            if(root.getElementsByTagName("email").getLength()!=0) email = root.getElementsByTagName("email").item(0).getFirstChild().getNodeValue();
            if(root.getElementsByTagName("startSubscribe").getLength()!=0) startSubscribe = root.getElementsByTagName("startSubscribe").item(0).getFirstChild().getNodeValue();
            if(root.getElementsByTagName("endSubscribe").getLength()!=0) endSubscribe = root.getElementsByTagName("endSubscribe").item(0).getFirstChild().getNodeValue();
            if(root.getElementsByTagName("tarif").getLength()!=0) tarif = root.getElementsByTagName("tarif").item(0).getFirstChild().getNodeValue();
            if(root.getElementsByTagName("users").getLength()!=0) users = root.getElementsByTagName("users").item(0).getFirstChild().getNodeValue();

            int length = root.getElementsByTagName("invoice").getLength();
            System.out.println(length);
            for(int i=0;i<length;i++){
                Map<String, String> item = new HashMap<String, String>();
                if(root.getElementsByTagName("Date").getLength()!=0) item.put("Date", root.getElementsByTagName("Date").item(0).getFirstChild().getNodeValue());
                if(root.getElementsByTagName("validTo").getLength()!=0) item.put("validTo", root.getElementsByTagName("validTo").item(0).getFirstChild().getNodeValue());
                if(root.getElementsByTagName("amount").getLength()!=0) item.put("amount", root.getElementsByTagName("amount").item(0).getFirstChild().getNodeValue());
                if(root.getElementsByTagName("url").getLength()!=0) item.put("url", root.getElementsByTagName("url").item(0).getFirstChild().getNodeValue());
                if(root.getElementsByTagName("urlp24").getLength()!=0) item.put("urlp24", urlp24 = root.getElementsByTagName("urlp24").item(0).getFirstChild().getNodeValue().replace("&amp;","&"));
                invoices.put(i,item);
            }

            out.println(abonentId);
            out.println(email);
            out.println(urlInvoice);
            out.println(tarif);
            out.println(users);
            out.println(date);
            out.println(validTo);
            out.println(amount);
            out.println(fio);
            out.println(startSubscribe);
            out.println(endSubscribe);
            out.println(urlp24);
            out.println(telefon);
            request.setAttribute("Error", "0");
            request.setAttribute("abonentId", abonentId);
            request.setAttribute("email", email);
            request.setAttribute("urlInvoice", urlInvoice);
            request.setAttribute("tarif", tarif);
            request.setAttribute("users", users);
            request.setAttribute("date", date);
            request.setAttribute("validTo", validTo);
            request.setAttribute("amount", amount);
            request.setAttribute("fio", fio);
            request.setAttribute("startSubscribe", startSubscribe);
            request.setAttribute("endSubscribe", endSubscribe);
            request.setAttribute("urlp24", urlp24);
            request.setAttribute("telefon", telefon);

        }
        catch (Exception e)
        {
            request.setAttribute("Error", "1");
        }

    }
}
