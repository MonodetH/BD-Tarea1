<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Clases.BD"%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>

<% 
    HttpSession sesion = request.getSession(false);
    
    if (session.getAttribute("user_id") != null){
        response.sendRedirect("/Tarea1/Paginas/p_usuario.jsp");
    }
    BD bd = new BD();
    bd.conectar();
%>

<%@include file="WEB-INF/jspf/head.jspf" %>
<div id="contenido" style="width: 100%">
    <div id="login-reg">
        <div id="login" style="text-align: center;">
            <h3 style="text-align: center;">Login</h3>
            <form method="POST" action="login" style="display: inline;">
                <input type="email" name="email" placeholder="email@example.com" value="" autofocus="autofocus" required />
                <input type="password" name="pass" placeholder="Password" value="" required />
                <input type="submit" name="submitted" value="Entrar">
            </form>
        </div>
        <div id="reg_switch">
            <a href="#" 
                onclick="document.getElementById('registro').style.display='block';
                    document.getElementById('reg_switch').style.display='none';">
                Soy Nuevo, REGISTRAME 
            </a>
        </div>
        <div id="registro">
            <br/><hr><br/>
            <h3 style="text-align: center;">Registro</h3>
            <%@include file="Formularios/registro.jspf" %>
        </div>
    </div>
    <div id="vista_inicio">
        Aqui la vista
    </div>
</div>      
<%@include file="WEB-INF/jspf/footer.jspf" %>