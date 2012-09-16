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

@WebServlet(name = "registro", urlPatterns = {"/registro"})
public class registro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession mensaje = request.getSession(true);
        ResultSet respuesta=null;
        PrintWriter out = response.getWriter();
        //Declaro variables
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String pass2 = request.getParameter("pass2");
        String nick = request.getParameter("nick");
        String sTipo = request.getParameter("tipo");
        int tipo = 1;
        if(sTipo.compareTo("Villano") == 0){tipo = 0;}
        
        BD bd = new BD();
        if(pass.compareTo(pass2)== 0){
            try {
                bd.conectar();
                String x = bd.regUser(email, pass, nick, tipo);
                mensaje.setAttribute("mensaje", x);
                bd.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(registro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            HttpSession sesion = request.getSession(false);
            sesion.setAttribute("mensaje", "Las contraseñas deben coincidir" );
        }
        if (mensaje.getAttribute("mensaje") == "Ok"){
            response.sendRedirect("/Tarea1/login?email="+email+"&pass="+pass);
        }else{
            response.sendRedirect("/Tarea1/index.jsp");
        }
    }
}
