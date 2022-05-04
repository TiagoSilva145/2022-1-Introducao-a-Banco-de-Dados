/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package introducao;

import java.sql.*;

/**
 *
 * @author leandro
 */
public class AcessoMySQL {
    
    public static void main(String[] args) throws Exception {
        
        //Class.forName("com.mysql.jdbc.Driver");
        
/*        String url = "jdbc:mysql://localhost/university";
        String user = "root";
        String password = "1234";*/
        String url = "jdbc:postgresql://localhost/university";
        String user = "postgres";
        String password = "1234";
        Connection con = DriverManager.getConnection(url, user, password);
        DatabaseMetaData dbmt = con.getMetaData();
        
        //Statement stmt = con.createStatement();
        ResultSet rs1 = dbmt.getTables(null,null,null, new String[] { "TABLE" } );
        while (rs1.next()) {
            System.out.println(rs1.getString(3));
        }
        
        /*ResultSet rs = stmt.executeQuery("select * from instructor");
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }*/
        //rs.close();
        //stmt.close();
        con.close();
        
    }
    
}





















