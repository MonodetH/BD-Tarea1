
<%@page import="com.sun.crypto.provider.RSACipher"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Clases.BD"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%  HttpSession sesion= request.getSession(false);
    if(sesion.getAttribute("nick") == null){
        response.sendRedirect("/Tarea1/index.jsp");
    }
    BD bd = new BD();
    bd.conectar();
    
    ResultSet rs = null;
    ResultSet round = bd.getUltimoRound(request.getParameter("id").toString());
    ResultSet batalla = bd.getBatalla(request.getParameter("id").toString());
    round.next();
    batalla.next();
    boolean esVillano = bd.isVillano(sesion.getAttribute("user_id").toString());
    boolean esTurno = false;
    int numRound = round.getInt("numeroRound");
    int estado = 0; // 0 = esperar, 1 = atacar, 2= defender
    String id_guerrero = null;
    if(esVillano){
        id_guerrero = batalla.getString("id_villano");
        if(batalla.getInt("id_villano") == round.getInt("id_turno")){esTurno = true;}
    }else{
        id_guerrero = batalla.getString("id_heroe");
        if(batalla.getInt("id_heroe") == round.getInt("id_turno")){esTurno = true;}
    }
    if(esVillano){
        if(numRound%2 == 1 && esTurno){estado = 1;}
               else if(numRound%2 == 0 && esTurno){estado = 2;}
    }else{
        if(numRound%2 == 0 && esTurno){estado = 1;}
               else if(numRound%2 == 1 && esTurno){estado = 2;}
    }
%>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<%@include file="/WEB-INF/jspf/sidebar.jspf" %>
<div id="contenido">
    <div id="estadoBatalla">

        <div id="estadoRound">
            <h2>Round <% out.print(numRound); %></h2>
            <p>Estado: 
<%
    if(estado == 0){out.print("Esperando al oponente<br/>");}
        else if(estado == 1){out.print("Atacar<br/>");}
        else if(estado == 2){out.print("Defender<br/>");}
%>
            </p>
        </div>
    </div>
    <div id="vs">
        <div class="avatar">
<%
    rs = bd.rawQuery("select * from guerrero where id_guerrero="+batalla.getString("id_heroe"));
    rs.next();
    out.println("<img src='"+ rs.getString("url_foto") +"'/>");
    out.println("<h3>"+round.getString("ptsvida_heroe")+" PtsVida</h3>");
%>
        </div>
        <div id="versus"><h2>VS</h2></div>
        <div class="avatar">
<%
    rs = bd.rawQuery("select * from guerrero where id_guerrero="+batalla.getString("id_villano"));
    rs.next();
    out.println("<img src='"+ rs.getString("url_foto") +"'/>");
    out.println("<h3>"+round.getString("ptsvida_villano")+" PtsVida</h3>");
%>
        </div>
    </div>
    <div class="formulario">
<%
    if(estado == 1){
%>
        <%@include file="/Formularios/atacar.jspf" %>
<%    
    }else if(estado == 2){
%>
        <%@include file="/Formularios/defender.jspf" %>
<%  } %>
    </div>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>