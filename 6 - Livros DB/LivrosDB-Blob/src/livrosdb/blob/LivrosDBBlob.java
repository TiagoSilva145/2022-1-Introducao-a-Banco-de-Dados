/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package livrosdb.blob;

import java.sql.*;
import java.io.*;

/**
 *
 * @author tiago145
 */
public class LivrosDBBlob {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String url = "jdbc:mysql://localhost/livrosdb";
        String user = "root";
        String password = "";
        
        //Cria o campo blob
        try{
        Connection con;
        con = DriverManager.getConnection(url, user, password);
        
        Statement update = con.createStatement();
        
        String query = "ALTER TABLE livros "
                + "     add column"
                + "     capa mediumblob";
        update.executeUpdate(query);
        
        con.close();
        } catch(SQLException e1){
            System.out.println("Erro criar conexao: " + e1);
        }
        
        //Adiciona as capas no campo capa
        try{
        Connection con;
        con = DriverManager.getConnection(url, user, password);
        
        Statement ler = con.createStatement();
        PreparedStatement inserirCapa = con.prepareStatement("UPDATE livros "
                + "                                           SET capa = ? "
                + "                                           WHERE livro_id = ? ");
        
        String query = "SELECT livro_id FROM livros";
        ResultSet rs = ler.executeQuery(query);
        
        while(rs.next())
        {
            System.out.println(rs.getInt("livro_id"));
            
            //Ler a capa a ser inserida
            String local = "/home/tiago145/Tudo/Desktop/UTFPR - 5 Semestre/Introdução a Banco de dados/6 - Livros DB/livros-db/";
            FileInputStream arq = new FileInputStream(local+rs.getString(1)+".jpg");
            
            //Preencher o byte array output stream
            int bytelido = arq.read();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while(bytelido != -1)
            {
                buffer.write(bytelido);
                bytelido = arq.read();
            }
            
            //Arrumar os campos do prepared statement
            inserirCapa.setInt(2, rs.getInt(1));
            inserirCapa.setBytes(1, buffer.toByteArray());
            //Executar o comando
            inserirCapa.executeUpdate();
            
        }
        
        con.close();
        } catch(SQLException e1){
            System.out.println("Erro criar conexao: " + e1);
        } catch (IOException e2){
            System.out.println("Erro arquivo: " + e2);
        }
        
        
        System.out.println("Deu certo o/");
    }
    
}
