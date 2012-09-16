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

@WebServlet(name = "agregarHabilidad", urlPatterns = {"/agregarHabilidad"})
public class agregarHabilidad extends HttpServlet {

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
            ResultSet respuesta=null;
            PrintWriter out = response.getWriter();
            //Declaro variables
            String id_guerrero = request.getParameter("id_guerrero");
            String nombre = request.getParameter("nombre");
            String tipo = request.getParameter("tipo");
            String pts = null;
            
            if(tipo.compareTo("1") == 0){
                pts = "2";
            }else{
                pts = "1";
            }
            
            BD bd = new BD();
            bd.conectar();
            
            ResultSet rs = bd.rawQuery("select * from habilidad where id_guerrero ='"+id_guerrero+"' and nombre='"+nombre+"'");
            
            if(rs.next()){
                mensaje.setAttribute("mensaje", "Este guerrero ya tiene una habilidad con ese nombre");
                response.sendRedirect("/Tarea1/Paginas/add_habilidad.jsp");
                return;
            }
            
            bd.regHabilidad(id_guerrero, nombre, pts, tipo);
            response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
            
        } catch (SQLException ex) {
            Logger.getLogger(agregarGuerrero.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
