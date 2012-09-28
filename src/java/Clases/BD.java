package Clases;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
  * Clase para acceder a una BD ORACLE
*/
public class BD {

    private static final String DRIVER ="oracle.jdbc.driver.OracleDriver";
    private static final String CONNECTION ="jdbc:oracle:thin:@";
    String serverName = "127.0.0.1";
    String host = "8080";
    String port = "1521";//El puerto por defecto para ORACLE es el 1521
    String sid= "XE";
    String user = "ADMIN";
    String password ="admin";
    Connection connection = null;
    
    public BD (){
        try{
        //Cargando el driver JDBC
        Class.forName( DRIVER ).newInstance();          
        } catch (Exception e){
            System.out.println("Error cargando el driver JDBC");
            throw new IllegalArgumentException("QOracle: Error cargando el driver JDBC");
        }
    }
    /**
     * Metodo constructor. 
	 * Asigna los valores de usuario, password, host, puerto y nombre de la bd, 
	 * para que posteriormente pueda hacerse la conexion
     *     
     * @param host Maquina en la que esta la BD
     * @param port Puerto en el que escucha el servidor ORACLE
     * @param sid Nombre de la BD a la que se desea conectar
     * @param user Nombre de usuario a emplear para conectarse a la BD
     * @param password Password para conectarse a la BD, con el nombre de usuario especificado
    */
    public BD (String host, String port, String sid, String user, String password) {
      this.host = host;
      this.port = port;
      this.sid = sid;
      this.user = user;
      this.password = password;
    
      try{
          //Cargando el driver JDBC
          Class.forName( DRIVER ).newInstance();          
      } catch (Exception e){
        System.out.println("Error cargando el driver JDBC");
        throw new IllegalArgumentException("QOracle: Error cargando el driver JDBC");
      }
    }
    
    /**
     * Metodo constructor. 
     * Asigna los valores de usuario, password, host y nombre de la bd, 
     * para que posteriormente pueda hacerse la conexion
     *
    * @param host Maquina en la que esta la BD
    * @param dataBase Nombre de la BD a la que se desea conectar
    * @param user Nombre de usuario a emplear para conectarse a la BD
    * @param password Password para conectarse a la BD, con el nombre de usuario especificado
    */
    public BD (String host, String dataBase, String user,
			String password) {
      this.host = host;
      this.port = null;
      this.sid = dataBase;
      this.user = user;
      this.password = password;
    
      try{
          //Cargando el driver JDBC
          Class.forName( DRIVER ).newInstance();          
      } catch (Exception e){
        System.out.println("Error cargando el driver JDBC");
        throw new IllegalArgumentException("QOracle: Error cargando el driver JDBC");
      }
    }

    /**
     *  Metodo para establecer la conexion JDBC con la BD
     *  <p>
     *  @exception Lanza una excepcion en caso de que se produzca algun error
    */
    public void conectar() {
        try {
            Class.forName(DRIVER);
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            connection = DriverManager.getConnection(url, "ADMIN", "admin");
        } catch (ClassNotFoundException e) {
            System.out.println("NO SE ENCONTRO DRIVER");
        } catch (SQLException e) {
            System.out.println("NO SE PUDO CONECTAR A LA BASE DE DATOS");
        }
    }
    
    /**
     *  Metodo para cerrar la conexion JDBC con la BD
    */
    public void desconectar() {
        try{
            if(connection != null){
                connection.close();
                connection = null;
            }
        } catch (SQLException sqlE){
            connection = null;
        }
    }
    public ResultSet rawQuery(String sql) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public boolean existeNickname(String nickname) throws SQLException {
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery ("SELECT PSEUDONIMO FROM maestro WHERE PSEUDONIMO ='"+nickname+"'");
        
        return rs.next();
    }
    public boolean existeEmail(String email) throws SQLException {
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery ("SELECT EMAIL FROM maestro WHERE EMAIL ='"+email+"'");
        
        return rs.next();
    }
    public ResultSet login(String email,String contrasena) throws SQLException{
        String sql = "SELECT id_maestro, pseudonimo FROM maestro WHERE email='"+email+"' AND password='"+contrasena+"'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public String regUser(String email, String pass, String nick, int tipo) throws SQLException{
        
        if(existeNickname(nick)){return "Pseudonimo ya existe";}
        if(existeEmail(email)){return "Email ya existe";}
        ResultSet rs=null;
        try {
            
            Statement stmt = connection.createStatement();
            int inserted =stmt.executeUpdate("insert into maestro values(1,'"+email+"','"+pass+"','"+nick+"',"+tipo+",0,0,0)");
            connection.commit();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Ok";
    }
    public int numGuerreros(String id_maestro) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery ("SELECT COUNT(*) AS conteo FROM guerrero WHERE id_maestro="+id_maestro);
        rs.next();
        return rs.getInt("conteo");
    }
    public boolean isVillano(String id_maestro) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery ("SELECT tipo FROM maestro WHERE id_maestro="+id_maestro);
        rs.next();
        int tipo = rs.getInt("tipo");
        if(tipo == 0){return true;}
        return false;
    }
    public String regGuerrero(String id_maestro,String ptsvida,String nombre,String url_foto){
        ResultSet rs=null;
        try {
            
            Statement stmt = connection.createStatement();
            int inserted =stmt.executeUpdate("insert into guerrero(id_maestro,nombre,ptsvida,url_foto) values("+id_maestro+",'"+nombre+"',"+ptsvida+",'"+url_foto+"')");
            connection.commit();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Ok";
    }
    public String regHabilidad(String id_guerrero, String nombre, String ptsfuerza,String tipo){
        ResultSet rs=null;
        try {
            
            Statement stmt = connection.createStatement();
            int inserted =stmt.executeUpdate("insert into habilidad (id_guerrero,nombre,ptsfuerza,tipo) values("+id_guerrero+",'"+nombre+"',"+ptsfuerza+","+tipo+")");
            connection.commit();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Ok";
    }
    
    public int atacar(boolean esVillano, String id_round, String id_habilidad){
        ResultSet rs=null;
        int inserted = 0;
        try {
            
            Statement stmt = connection.createStatement();
            if(esVillano){
                inserted =stmt.executeUpdate("update round set hab_villano="+id_habilidad+" where id_round="+id_round);
            }else{
                inserted =stmt.executeUpdate("update round set hab_heroe="+id_habilidad+" where id_round="+id_round);
            }
            connection.commit();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inserted;
    }
    
    public int nuevaBatalla(String nombre, String id_villano, String id_heroe) throws SQLException{
        Statement stmt = connection.createStatement();
        int inserted =stmt.executeUpdate("insert into batalla (id_villano,id_heroe,nombrebatalla) values("+id_villano+","+id_heroe+",'"+nombre+"')");
        connection.commit();
        
        int id_bat = 0;
        
        if(inserted == 1){
            ResultSet rs = stmt.executeQuery("select id_batalla from batalla where id_villano="+id_villano+" and id_heroe="+id_heroe+" and id_ganador is null");
            if(rs.next()){id_bat = rs.getInt("id_batalla");}
        }
        stmt.close();
        return id_bat;
    }
    public String nuevoRound(String id_batalla,String id_turno,String hab_heroe,String hab_villano,String numeroRound,String ptsvida_heroe,String ptsvida_villano) throws SQLException{
        Statement stmt = connection.createStatement();
        int inserted =stmt.executeUpdate("insert into round (id_batalla,id_turno,hab_heroe,hab_villano,numeroRound,ptsvida_heroe,ptsvida_villano) values("+id_batalla+","+id_turno+","+hab_heroe+","+hab_villano+","+numeroRound+","+ptsvida_heroe+","+ptsvida_villano+")");
        connection.commit();
        stmt.close();
        
        if(inserted == 1){return "Ok";}
        return "Error al crear Round";
    }
    public String nuevoRound(String id_batalla){
        try {
            ResultSet ultimo = getUltimoRound(id_batalla);
            ultimo.next();
            String id_turno = ultimo.getString("id_turno");
            String Snull = null;
            String numeroRound = String.valueOf(ultimo.getInt("numeroround")+1);
            String ptsvida_heroe = ultimo.getString("ptsvida_heroe");
            String ptsvida_villano = ultimo.getString("ptsvida_villano");
            
            Statement stmt = connection.createStatement();
            int inserted =stmt.executeUpdate("insert into round (id_batalla,id_turno,hab_heroe,hab_villano,numeroRound,ptsvida_heroe,ptsvida_villano) values("+id_batalla+","+id_turno+","+Snull+","+Snull+","+numeroRound+","+ptsvida_heroe+","+ptsvida_villano+")");
            connection.commit();
            stmt.close();
            
            if(inserted == 1){return "Ok";}
            
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error al crear Round";
    }
    
    public ResultSet getContrincantes(String id_maestro) throws SQLException{
        Statement s=connection.createStatement();
        ResultSet rs;

        rs=s.executeQuery("select tipo from maestro where id_maestro="+id_maestro);
        rs.next();
        String tipo = rs.getString("tipo");
        //villano
        if(tipo.compareTo("0")==0){
            rs=s.executeQuery("select pseudonimo from (select id_maestro from(select id_heroe from(select id_guerrero from guerrero where guerrero.id_maestro="+id_maestro+") s,batalla b where s.id_guerrero=b.id_villano) s1, guerrero g where s1.id_heroe=g.id_guerrero ) s2, maestro m where s2.id_maestro=m.id_maestro group by pseudonimo");
            
            return rs;
        }
        //heroe
        else{
            rs=s.executeQuery("select pseudonimo from (select id_maestro from(select id_villano from(select id_guerrero from guerrero where guerrero.id_maestro="+id_maestro+") s,batalla b where s.id_guerrero=b.id_heroe) s1, guerrero g where s1.id_villano=g.id_guerrero ) s2, maestro m where s2.id_maestro=m.id_maestro group by pseudonimo");//cambiar select
          
            return rs;
        }
    }
    public ResultSet getGuerreros(String id_maestro) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery ("select * from guerrero where id_maestro='"+id_maestro+"' and ptsvida>0 and ptsvida<11");
        
        return rs;
    }
    public ResultSet getHabilidades(String id_guerrero) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery ("select * from habilidad where id_guerrero='"+id_guerrero+"'");
        
        return rs;
    } 
    public String getVida(String id_guerrero) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery ("select ptsvida from guerrero where id_guerrero='"+id_guerrero+"'");
        rs.next();
        return rs.getString("ptsvida");
    }
    public ResultSet batallasActuales(String user_id) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = null;
        if(isVillano(user_id)){
            rs = s.executeQuery ("select b.id_batalla id_batalla, b.nombreBatalla nombreBatalla from batalla b inner join guerrero g on g.id_guerrero = b.id_villano where b.id_ganador is null and g.id_maestro = "+user_id);
        }else{
            rs = s.executeQuery ("select b.id_batalla id_batalla, b.nombreBatalla nombreBatalla from batalla b inner join guerrero g on g.id_guerrero = b.id_heroe where b.id_ganador is null and g.id_maestro = "+user_id);
        }
        return rs;
    }
    public ResultSet getEnemigos(boolean esVillano) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = null;
        if(esVillano){
            rs = s.executeQuery("select g.id_guerrero id, m.pseudonimo maestro, g.nombre guerrero from guerrero g inner join maestro m on g.id_maestro = m.id_maestro where m.tipo = 1 and g.ptsvida > 0");
        }else{
            rs = s.executeQuery("select g.id_guerrero id, m.pseudonimo maestro, g.nombre guerrero from guerrero g inner join maestro m on g.id_maestro = m.id_maestro where m.tipo = 0 and g.ptsvida > 0");
        }
        return rs;
    }
    public ResultSet getUltimoRound(String id_batalla){
        ResultSet rs = null;
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("select * from round where id_batalla="+id_batalla+" order by numeroRound desc");
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public ResultSet getBatalla(String id){
        ResultSet rs = null;
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("select * from batalla where id_batalla="+id);
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public boolean ganoVillano(String id_bat){
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("select * from batalla where id_batalla="+id_bat);
            rs.next();
            if(rs.getInt("id_villano") == rs.getInt("id_ganador")){return true;}
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean sinBatalla(String guerrero) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("select * from batalla where (id_heroe='"+guerrero+"' or id_villano='"+guerrero+"') and id_ganador is null");
        boolean sin = true;
        if(rs.next()){sin = false;}
        return sin;
    }
    public int domina(){
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("select * from view_domina order by tipo");
            rs.next();
            int vill, her;
            vill = rs.getInt("bg");
            rs.next();
            her = rs.getInt("bg");
            if(vill > her){return -1;}
            else if(vill < her){return 1;}
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public ResultSet topTen(){
        ResultSet rs = null;
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("select * from view_topten");
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public ResultSet todosAtaques(String id_maestro){
        ResultSet rs = null;
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("select h.id_habilidad id, h.nombre h_nombre, g.nombre g_nombre from guerrero g inner join habilidad h on g.id_guerrero = h.id_guerrero where h.ptsfuerza < 10 and g.ptsvida > 0 and h.tipo = 1 and g.id_maestro="+id_maestro);
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public ResultSet todasDefensas(String id_maestro){
        ResultSet rs = null;
        try {
            Statement s = connection.createStatement();
            rs = s.executeQuery("select h.id_habilidad id, h.nombre h_nombre, g.nombre g_nombre from guerrero g inner join habilidad h on g.id_guerrero = h.id_guerrero where h.ptsfuerza < 10 and g.ptsvida > 0 and h.tipo = 2 and g.id_maestro="+id_maestro);
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public void masVida(String id_guerrero){
        try {
            Statement s = connection.createStatement();
            
            ResultSet rs = s.executeQuery("select ptsvida from guerrero where id_guerrero="+id_guerrero);
            rs.next();
            int pts = rs.getInt("ptsvida");
            
            int inserted =s.executeUpdate("update guerrero set ptsvida="+String.valueOf(pts+1)+" where id_guerrero="+id_guerrero);
            connection.commit();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void masPtsFuerza(String id_hab){
        try {
            Statement s = connection.createStatement();
            
            ResultSet rs = s.executeQuery("select ptsfuerza from habilidad where id_habilidad="+id_hab);
            rs.next();
            int pts = rs.getInt("ptsfuerza");
            
            int inserted =s.executeUpdate("update habilidad set ptsfuerza="+String.valueOf(pts+1)+" where id_habilidad="+id_hab);
            connection.commit();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void masPremio(String id_maestro){
        try {
            Statement s = connection.createStatement();
            
            ResultSet rs = s.executeQuery("select premiosrestantes from maestro where id_maestro="+id_maestro);
            rs.next();
            int pts = rs.getInt("premiosrestantes");
            
            int inserted =s.executeUpdate("update maestro set premiosrestantes="+String.valueOf(pts+1)+" where id_maestro="+id_maestro);
            connection.commit();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void menosPremio(String id_maestro){
        try {
            Statement s = connection.createStatement();
            
            ResultSet rs = s.executeQuery("select premiosrestantes from maestro where id_maestro="+id_maestro);
            rs.next();
            int pts = rs.getInt("premiosrestantes");
            
            int inserted =s.executeUpdate("update maestro set premiosrestantes="+String.valueOf(pts-1)+" where id_maestro="+id_maestro);
            connection.commit();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}