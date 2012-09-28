<%@page import="java.sql.ResultSet"%>
<%@page import="Clases.BD"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%  HttpSession sesion= request.getSession(false);
    if(sesion.getAttribute("nick") == null){
        response.sendRedirect("/Tarea1/index.jsp");
    }
    BD bd = new BD();
    bd.conectar();
    ResultSet rs1 = null;
    ResultSet rs2 = null;
%>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<%@include file="/WEB-INF/jspf/sidebar.jspf" %>
<div id="contenido">
<% rs1 = bd.getGuerreros(sesion.getAttribute("user_id").toString()); %>
<div id="guerrerosPlus">
<% 
while(rs1.next()){ 
    out.println("<div class='guerreroPlus'>");
    out.println("<div class='foto'><img src='"+ rs1.getString("url_foto") +"' /></div>");
    out.println("<div class='datos_gp'><h2>"+rs1.getString("NOMBRE")+"</h2><br/><h3>"+rs1.getString("ptsvida")+" Puntos de Vida</h3></div>");
    rs2 = bd.getHabilidades(rs1.getString("id_guerrero").toString());
    out.println("<div class='habilidades'>");
    while(rs2.next()){
        String tipo = null;
        if(rs2.getString("tipo").compareTo("1") == 0){tipo = "Ataque";}else{tipo = "Defensa";}
        out.println(rs2.getString("nombre")+" - "+tipo+" - "+rs2.getString("ptsfuerza")+" Puntos de Fuerza<br/>");
    }
    out.println("</div>");
    out.println("</div>");
} 
%>    
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
