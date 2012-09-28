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
<% ResultSet rs = bd.rawQuery("select id_guerrero, nombre, url_foto from guerrero where id_maestro = '"+session.getAttribute("user_id") +"' and ptsvida>0"); %>

<div id="contenido">
    <div class="formulario">
        <%@include file="/Formularios/nuevaHabilidad.jspf" %>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
