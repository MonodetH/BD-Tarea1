
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

@WebServlet(name = "premiar", urlPatterns = {"/premiar"})
public class premiar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession mensaje = request.getSession(true);
            PrintWriter out = response.getWriter();
            //Declaro variables
            String vida = request.getParameter("vida");
            String ataque = request.getParameter("ataque");
            String defensa = request.getParameter("defensa");
            
            BD bd = new BD();
            bd.conectar();
            
            ResultSet rs = bd.rawQuery("select * from maestro where premiosrestantes=0 and id_maestro="+mensaje.getAttribute("user_id").toString());
            if(rs.next()){
                mensaje.setAttribute("mensaje", "No hay premios que cobrar");
                response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
                return;
            }
            
            bd.masVida(vida);
            bd.masPtsFuerza(ataque);
            bd.masPtsFuerza(defensa);
            bd.menosPremio(mensaje.getAttribute("user_id").toString());
            
            response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(premiar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
