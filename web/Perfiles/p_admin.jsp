<%-- 
    Document   : p_admin
    Created on : 24-abr-2012, 2:10:24
    Author     : MonodetH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%  HttpSession sesion= request.getSession(false);
    if(sesion.getAttribute("nickname") == null){
        response.sendRedirect("/Cuebook/index.jsp");
    }
    if(sesion.getAttribute("is_admin") == "0"){
        response.sendRedirect("/Cuebook/Perfiles/p_usuario.jsp");
    }
%>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<header>
    <div id="logo">LOGO AQUI</div>
    <div id="s"> cuadro de busqueda</div>
    <div id="logout">Hola <% out.println(sesion.getAttribute("nickname")); %> - <a href="/Cuebook/logout">Logout</a></div>
</header>
<div id="contenido">
    <div id="menu_admin">
        <div id="tabs">
            <ul>
                <li id="ir_perfil"><a href="/Cuebook/Perfiles/p_usuario.jsp">Ir a perfil</a></li>
                <li><a href="#" >Ingresar Libro</a></li>
                <li>Ingresar Serie</li>
                <li>Ingresar Pelicula</li>
                <li>Editar Libro</li>
                <li>Editar Serie</li>
                <li>Editar Pelicula</li>
                <li>Agregar Capitulo</li>
            </ul>
        </div>
        <div id="tab_frame">
            <div id="ing_libro" class="menu_admin">
                <h2>ingresar libro</h2>
                <%  if(session.getAttribute("Mensaje")!=null){
                        out.println(session.getAttribute("Mensaje"));
                        session.removeAttribute("Mensaje");
                    }
                %>
                <%@include file="../Formularios/a_libro.jspf" %>
            </div>
            <div id="ing_peli" class="menu_admin">
                <h2>ingresar pelicula</h2>
                <%@include file="../Formularios/a_peli.jspf" %>
            </div>
            <div id="ing_serie" class="menu_admin">
                <h2>ingresar serie</h2>
                <%@include file="../Formularios/a_serie.jspf" %>
            </div>
            <div id="ing_cap" class="menu_admin">
                <h2>ingresar capitulo</h2>
                <%@include file="../Formularios/a_cap.jspf" %>
            </div>
            <div id="edit_libro" class="menu_admin">
                <h2>editar libro</h2>
                <%@include file="../Formularios/e_libro.jspf" %>
            </div>
            <div id="edit_peli" class="menu_admin">
                <h2>editar pelicula</h2>
                <%@include file="../Formularios/e_peli.jspf" %>
            </div>
            <div id="edit_serie" class="menu_admin">
                <h2>editar serie</h2>
                <%@include file="../Formularios/e_serie.jspf" %>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>