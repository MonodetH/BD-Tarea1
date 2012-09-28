
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

@WebServlet(name = "defender", urlPatterns = {"/defender"})
public class defender extends HttpServlet {
    
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
        
        if(bd.atacar(esVillano, id_round, id_hab) == 1){
            try {
                ResultSet bat = bd.rawQuery("select * from batalla where id_ganador is not null and id_batalla="+request.getParameter("id_batalla").toString());
                if(bat.next()){
                    if(bd.ganoVillano(request.getParameter("id_batalla").toString())){
                        if(esVillano){
                            response.sendRedirect("/Tarea1/Paginas/victoria.jsp");
                            return;
                        }else{
                            mensaje.setAttribute("mensaje", "Su guerrero ha muerto en batalla y ya no se mostrara mas en el sistema. VENDETTA!!");
                            response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
                            return;
                        }
                    }else{
                        if(esVillano){
                            mensaje.setAttribute("mensaje", "Su guerrero ha muerto en batalla y ya no se mostrara mas en el sistema. VENDETTA!!");
                            response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
                            return;
                        }else{
                            response.sendRedirect("/Tarea1/Paginas/victoria.jsp");
                            return;
                        }
                    }
                }else{
                    bd.nuevoRound(request.getParameter("id_batalla").toString());
                }
            } catch (SQLException ex) {
                Logger.getLogger(defender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        response.sendRedirect("/Tarea1/Paginas/batalla.jsp?id="+request.getParameter("id_batalla"));
        
    }
}
