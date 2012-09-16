<%-- 
    Document   : p_libro
    Created on : 24-abr-2012, 16:36:05
    Author     : MonodetH
--%>


<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%  HttpSession sesion= request.getSession(false);
    if(sesion.getAttribute("nickname") == null){
        response.sendRedirect("/Cuebook/index.jsp");
    }
%>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<header>
    <div id="logo">LOGO AQUI</div>
    <div id="s"> cuadro de busqueda</div>
    <div id="logout">Hola <% out.println(sesion.getAttribute("nickname")); %> - <a href="/Cuebook/logout">Logout</a></div>
</header>
<div id="contenido">
    <div id="datos">
        <div id="nombre">
            FOTO Y NOMBRE
        </div>
        <div id="gusta">
            ME_GUSTA
        </div>
        Datos blabla
    </div>
    <div id="comment_wrap">
        FORM
        <div id="comments">
            COMENTARIOS ANTERIORES
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
