/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inserirautores;

import java.sql.*;

/**
 *
 * @author tiago145
 */
public class InserirAutores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/livrosdb";
        String user = "root";
        String password = "";
        
        try{
        //Cria a connection
        Connection con;
        con = DriverManager.getConnection(url, user, password);
        
        Statement ler = con.createStatement();
        //Prepara o statement para inserir os autores
        PreparedStatement inserir_autor = con.prepareStatement("INSERT INTO autores (nome, titulo) "
                +                                               "VALUES (?,?)");
        
        //Executa a leitura dos livros
        String query = "SELECT titulo, autor FROM livros";
        ResultSet rs = ler.executeQuery(query);
        
        //Para cada livro na tabela livros
        while(rs.next())
        {
            //Coloca o titulo no prepared statement
            inserir_autor.setString(2, rs.getString("titulo"));
            String autores = rs.getString("autor");
            
            //Separa cada autor da virgula
            for(int i = 0; i < autores.length(); i++)
            {
                String autor_nome = "";
                while(i < autores.length() && autores.charAt(i) != ',')
                {
                    autor_nome += autores.charAt(i);
                    i++;
                }
                i++;
                //Insere o autor no prepared statement e executa o update
                inserir_autor.setString(1, autor_nome);
                inserir_autor.executeUpdate();
                //System.out.println("Livro: " + rs.getString("titulo") + " Autor: " + autor_nome);
            }
        }
        
        con.close();
        } catch(SQLException e1){
            System.out.println("Erro criar conexao: " + e1);
        }
    }
    
}
