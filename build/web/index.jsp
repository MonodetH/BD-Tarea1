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
<div id="contenido" style="width: 100%; background-color: transparent;">
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
        <h2>
<%
    int domina = bd.domina();
    if(domina == 1){
        out.print("Los heroes mantenemos a salvo esta cuidad");
    }else if(domina == -1){
        out.print("Los villanos dominamos la cuidad");
    }else{
        out.print("Las batallas no han dado tregua alguna");
    }
%>
        </h2>
        <div id="topten">
            <hr/>
            <h2>TopTen</h2>
<%
    ResultSet rs = bd.topTen();
    while(rs.next()){
        out.println("<h4>"+rs.getString("pseudonimo")+" - "+rs.getString("batallasganadas")+"Victorias</h4>");
    }
%>
        </div>
    </div>
</div>      
<%@include file="WEB-INF/jspf/footer.jspf" %>