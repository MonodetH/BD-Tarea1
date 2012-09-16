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
    <div class="formulario">
        <% if(bd.numGuerreros(sesion.getAttribute("user_id").toString()) > 0){ %>
        <%@include file="/Formularios/nuevoGuerrero.jspf" %>
        <% }else{ %>
        <%@include file="/Formularios/primerGuerrero.jspf" %>
        <% } %>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>