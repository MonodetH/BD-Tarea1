
<%@page import="java.sql.ResultSet"%>
<%@page import="Clases.BD"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%  HttpSession sesion= request.getSession(false);
    if(sesion.getAttribute("nick") == null){
        response.sendRedirect("/Tarea1/index.jsp");
    }
    BD bd = new BD();
    bd.conectar();
%>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<%@include file="/WEB-INF/jspf/sidebar.jspf" %>
<div id="contenido">
    <a href="/Tarea1/Paginas/nueva_batalla.jsp">Iniciar Reto</a>
    <hr>
    <div id="batallasActuales">
<% 
    ResultSet rs = bd.batallasActuales(sesion.getAttribute("user_id").toString()); 
    while(rs.next()){
%>        
        <div class="batAct">
            <%  out.println("<a href='/Tarea1/Paginas/batalla.jsp?id="+rs.getString("id_batalla") +"'>"+rs.getString("nombreBatalla")+"</a>"); %>      
        </div>
<%  } %>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
