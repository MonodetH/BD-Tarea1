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
<%
    ResultSet rs = bd.rawQuery("select * from maestro where id_maestro = '"+session.getAttribute("user_id") +"'");
    rs.next();
%>
    
    
    <div id="datos_maestro">
        <h1> <% out.println( rs.getString("PSEUDONIMO")); %></h1>
        <%
            if(rs.getInt("TIPO") == 0){out.println("<h3>Villano</h3>");}
            else{out.println("<h3>Heroe</h3>");}
        %>
    </div>
        <div id="W-L">
            
            <h1><span><% out.print( rs.getInt("BATALLASGANADAS")); %></span> - 
                <span><% out.print( rs.getInt("BATALLASPERDIDAS")); %></span></h1>
        </div>
<%
    rs = bd.rawQuery("select id_guerrero, nombre, url_foto from guerrero where id_maestro = '"+session.getAttribute("user_id") +"'");
%>
    
<hr>
<div id="guerreros">
<% 
while(rs.next()){ 
    out.println("<div class='guerrero'>");
    out.println("<div class='foto'><img src='"+ rs.getString("url_foto") +"' /></div>");
    out.println("<div class='nombre_g'>"+rs.getString("NOMBRE")+"</div>");
    out.println("</div>");
} 
%>    
    </div>
    <hr>
    <div id="contrincantes">
<%
    rs = bd.getContrincantes(session.getAttribute("user_id").toString());
    while(rs.next()){
        out.println("<div class='nombre_contrincante'>"+ rs.getString("PSEUDONIMO") +"</div>");
    }
    bd.desconectar();
%>                     
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>