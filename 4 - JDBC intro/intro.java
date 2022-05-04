
import java.sql.*;

/**
 * intro
 */
public class intro {

    public static void main(String[] args) {

        try 
        {
            //carregar driver do mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
        } 
        catch (ClassNotFoundException e1) 
        {
            System.out.println("Driver nao disponivel");
            System.exit(0);
        }

        try 
        {
            String url = "jdbc:mysql://localhost/University";
            String user = "root";
            String password = "S3rv3rSenh@";

            //iniciar conexao com o servidor do banco de dados
            Connection con = DriverManager.getConnection(url, user, password);

            //Criar statement
            Statement stat = con.createStatement();

            //Executar query
            String query = "SELECT * FROM instructor";
            ResultSet rs = stat.executeQuery(query);

            //Imprimir a query
            while(rs.next())
                System.out.println(rs.getString("name"));

            //Fechar a conexao
            con.close();
        }
        catch (SQLException e2)
        {
            System.out.println("Erro em banco de dados " + e2);
        }

        System.out.println("Deu certo o/");
    }
}