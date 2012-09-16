<%-- 
    Document   : p_usuario
    Created on : 20-abr-2012, 2:09:05
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
        <h2>Datos</h2>
    </div>
    <hr>
    <div id="gustos">
        <h2>Gustos</h2>
        <div id="tabs">
            <ul>
                <li>Todos</li>
                <li>Libros</li>
                <li>Pelis</li>
                <li>Series</li>
            </ul>
        </div>
        <div id="tab_frame">
            AQUI LA LISTA DINAMICA
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>