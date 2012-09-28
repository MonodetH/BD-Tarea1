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

@WebServlet(name = "agregarGuerrero", urlPatterns = {"/agregarGuerrero"})
public class agregarGuerrero extends HttpServlet {

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
            String user_id = request.getParameter("user_id");
            String nombre = request.getParameter("nombre");
            String img_url = request.getParameter("img_url");
            String tipo_primer = request.getParameter("tipo_primer");
            
            BD bd = new BD();
            bd.conectar();
            
            ResultSet rs = bd.rawQuery("select * from guerrero where id_maestro ='"+user_id+"' and nombre='"+nombre+"'");
            
            if(rs.next()){
                mensaje.setAttribute("mensaje", "Ya tienes un guerrero con ese nombre");
                response.sendRedirect("/Tarea1/Paginas/add_guerrero.jsp");
                return;
            }
            
            if(tipo_primer.compareTo("none") == 0){
                bd.regGuerrero(user_id,"1",nombre,img_url);
                response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
            }else if(tipo_primer.compareTo("fuerte") == 0){
                bd.regGuerrero(user_id,"4",nombre,img_url);
                rs = bd.rawQuery("select id_guerrero from guerrero where id_maestro ='"+user_id+"' and nombre='"+nombre+"'");
                rs.next();
                String guerrero_id = rs.getString("id_guerrero");
                bd.regHabilidad(guerrero_id, "AtaqueF", "2", "1");
                bd.regHabilidad(guerrero_id, "DefensaF", "5", "2");
                
                response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
            }else if(tipo_primer.compareTo("agil") == 0){
                bd.regGuerrero(user_id,"3",nombre,img_url);
                rs = bd.rawQuery("select id_guerrero from guerrero where id_maestro ='"+user_id+"' and nombre='"+nombre+"'");
                rs.next();
                String guerrero_id = rs.getString("id_guerrero");
                bd.regHabilidad(guerrero_id, "AtaqueA", "5", "1");
                bd.regHabilidad(guerrero_id, "DefensaA", "2", "2");
                
                response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(agregarGuerrero.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
