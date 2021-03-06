
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

@WebServlet(name = "nuevaBatalla", urlPatterns = {"/nuevaBatalla"})
public class nuevaBatalla extends HttpServlet {

    
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
            String nombre = request.getParameter("nombre");
            String id_villano = request.getParameter("id_villano");
            String id_heroe = request.getParameter("id_heroe");
            
            int id_batalla;
            BD bd = new BD();
            bd.conectar();
            
            id_batalla = bd.nuevaBatalla(nombre, id_villano, id_heroe);            
            bd.nuevoRound(String.valueOf(id_batalla), id_villano, null, null, String.valueOf(1), bd.getVida(id_heroe), bd.getVida(id_villano));
            
            response.sendRedirect("/Tarea1/Paginas/batalla.jsp?id="+String.valueOf(id_batalla));
            return;
        } catch (SQLException ex) {
            Logger.getLogger(nuevaBatalla.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
    }
}
