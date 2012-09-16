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
<% ResultSet rs = bd.rawQuery("select id_guerrero, nombre, url_foto from guerrero where id_maestro = '"+session.getAttribute("user_id") +"'"); %>

<div id="contenido">
    <div id="guerreros">
<% 
while(rs.next()){ 
    out.println("<a href='/Tarea1/Paginas/add_habilidad_pt2.jsp?id_guerrero="+rs.getString("id_guerrero")+"'>");
    out.println("<div class='guerrero'>");
    out.println("<div class='foto'><img src='"+ rs.getString("url_foto") +"' /></div>");
    out.println("<div class='nombre_g'>"+rs.getString("NOMBRE")+"</div>");
    out.println("</div></a>");
} 
%>    
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>