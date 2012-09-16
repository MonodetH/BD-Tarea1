package Servlets;

import Clases.BD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BD bd = new BD();
            bd.conectar();
            ResultSet respuesta = null;
            respuesta = bd.login(request.getParameter("email"), request.getParameter("pass"));
            
            if( respuesta.next() == false){
                bd.desconectar();
                response.sendRedirect("/Tarea1/index.jsp");
            }else{
                String nick = respuesta.getString("PSEUDONIMO");
                String id = respuesta.getString("ID_MAESTRO");

                HttpSession sesion = request.getSession(true);
                sesion.setAttribute("nick", nick );
                sesion.setAttribute("user_id", id);

                bd.desconectar();
                response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
