
package Servlets;

import Clases.BD;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "atacar", urlPatterns = {"/atacar"})
public class atacar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession mensaje = request.getSession(true);
        PrintWriter out = response.getWriter();
        //Declaro variables
        boolean esVillano = true;        
        String id_hab = request.getParameter("hab");
        String id_round = request.getParameter("id_round");
        if(request.getParameter("esVillano").compareTo("false") == 0){esVillano = false;}
        
        BD bd = new BD();
        bd.conectar();
        
        bd.atacar(esVillano, id_round, id_hab);
        
        response.sendRedirect("/Tarea1/Paginas/batalla.jsp?id="+request.getParameter("id_batalla"));
    }

}
