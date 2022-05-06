/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaces_bdd;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author tiago145
 */
public class tela_principal extends javax.swing.JFrame {

    private String usuario;
    private String url;
    private String senha;
    private String banco_selecionado;
    /**
     * Creates new form tela_principal
     * @param usr
     * @param pss
     * @param urls
     */
    public tela_principal(String usr, String pss, String urls) {
        initComponents();
        usuario = usr;
        senha = pss;
        url = urls;
        banco_selecionado = "";
        //System.out.println("Recebi:" + url + " " + usuario + " " + senha + " " + nome_db);
        
        carrega_arvore();
    }
    
    private void carrega_arvore()
    {
        try {
            //Faz a conexao e rebece os metadados
            Connection con = DriverManager.getConnection(url, usuario, senha);
            DatabaseMetaData meta = con.getMetaData();
            
            DefaultTreeModel modelo;
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Databases");
            
            //Tabelas e schemas
            ResultSet tabelas = meta.getTables(null, null, null, new String[]{"TABLE"});
            //Views
            ResultSet views = meta.getTables(null, null, null, new String[]{"VIEW"});
            
            //Variaveis para os nodes db
            String nome_anterior = "";
            String nome_atual;
            DefaultMutableTreeNode db_node = new DefaultMutableTreeNode("erro");
            DefaultMutableTreeNode tabela_node = new DefaultMutableTreeNode("Tabelas");
            
            DefaultMutableTreeNode view_node = new DefaultMutableTreeNode("Views");
            String view_db_nome = "";
            
            //Pega dados do primeiro db
            if(tabelas.next())
            {
                nome_anterior = tabelas.getString(1);
                db_node = new DefaultMutableTreeNode(nome_anterior);
                tabelas.previous();
            }
            
            if(views.next())
                view_db_nome = views.getString(1);
            
            while(tabelas.next())
            {
                nome_atual = tabelas.getString(1);
                
                //Se mudou de db
                if(!(nome_anterior.equals(nome_atual)))
                {
                    //Adiciona o node view e tabelas no db
                    db_node.add(tabela_node);
                    tabela_node = new DefaultMutableTreeNode("Tabelas");
                    db_node.add(view_node);
                    view_node = new DefaultMutableTreeNode("Views");
                    //Adiciona na raiz e arruma as variaveis de controle
                    raiz.add(db_node);
                    db_node = new DefaultMutableTreeNode(nome_atual);
                    nome_anterior = nome_atual;
                }
                
                //Adiciona nome da tabela ao node
                DefaultMutableTreeNode tabela_nome = new DefaultMutableTreeNode(tabelas.getString("TABLE_NAME"));
                
                //Colunas
                ResultSet colunas = meta.getColumns(null, null, tabelas.getString("TABLE_NAME"), null);
                DefaultMutableTreeNode coluna_node = new DefaultMutableTreeNode("Colunas");
                
                //Adiciona nomes das colunas
                while(colunas.next())
                {
                    String nome_coluna = colunas.getString("COLUMN_NAME");
                    
                    nome_coluna += " - " + colunas.getString("TYPE_NAME");
                    nome_coluna += "(" + colunas.getString("COLUMN_SIZE") + ")";
                    coluna_node.add(new DefaultMutableTreeNode(nome_coluna));
                }
                tabela_nome.add(coluna_node);
                
                //Chaves primarias
                ResultSet chaves = meta.getPrimaryKeys(null, null, tabelas.getString("TABLE_NAME"));
                DefaultMutableTreeNode pk_node = new DefaultMutableTreeNode("Primary_Keys");
                
                //Adiciona nomes das chaves primarias
                while(chaves.next())
                {
                    pk_node.add(new DefaultMutableTreeNode(chaves.getString("COLUMN_NAME")));
                }
                tabela_nome.add(pk_node);
                
                tabela_node.add(tabela_nome);
                
                //Para todas as views do db atual
                while(view_db_nome.equals(nome_atual))
                {
                    //Adiciona nome da view ao node
                    DefaultMutableTreeNode view_nome = new DefaultMutableTreeNode(views.getString("TABLE_NAME"));

                    //Colunas
                    ResultSet colunas_view = meta.getColumns(null, null, views.getString("TABLE_NAME"), null);
                    DefaultMutableTreeNode coluna_view_node = new DefaultMutableTreeNode("Colunas");

                    //Adiciona nomes das colunas
                    while(colunas_view.next())
                    {
                        String nome_coluna = colunas_view.getString("COLUMN_NAME");

                        nome_coluna += " - " + colunas_view.getString("TYPE_NAME");
                        nome_coluna += "(" + colunas_view.getString("COLUMN_SIZE") + ")";
                        coluna_view_node.add(new DefaultMutableTreeNode(nome_coluna));
                    }
                    view_nome.add(coluna_node);

                    //Chaves primarias
                    ResultSet chaves_view = meta.getPrimaryKeys(null, null, views.getString("TABLE_NAME"));
                    DefaultMutableTreeNode pk_view_node = new DefaultMutableTreeNode("Primary_Keys");

                    //Adiciona nomes das chaves primarias
                    while(chaves_view.next())
                    {
                        pk_view_node.add(new DefaultMutableTreeNode(chaves_view.getString("COLUMN_NAME")));
                    }
                    view_nome.add(pk_view_node);

                    view_node.add(view_nome);
                    
                    db_node.add(view_node);
                    
                    if(views.next())
                        view_db_nome = views.getString(1);
                    else
                        view_db_nome = "";
                }
            }
            
            modelo = (DefaultTreeModel)arvore.getModel();
            modelo.setRoot(raiz);
            arvore.setModel(modelo);
            
        } catch (SQLException ex) {
            System.out.println("Erro db: " + ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        texto_tela_inicial = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arvore = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela_query = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        area_query = new javax.swing.JTextArea();
        botao_exportar = new javax.swing.JButton();
        botao_executar = new javax.swing.JButton();
        botao_limpar = new javax.swing.JButton();
        botao_voltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        texto_tela_inicial.setBackground(new java.awt.Color(255, 255, 51));
        texto_tela_inicial.setFont(new java.awt.Font("Liberation Sans", 0, 40)); // NOI18N
        texto_tela_inicial.setForeground(new java.awt.Color(51, 51, 51));
        texto_tela_inicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        texto_tela_inicial.setText("Banco de dados - ");

        arvore.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Databases");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tabelas");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("\"tabela\"");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Colunas");
        javax.swing.tree.DefaultMutableTreeNode treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("\"nomes\"");
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Primary_keys");
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("\"nomes\"");
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Views");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("\"view\"");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Colunas");
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("\"nomes\"");
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Primary_keys");
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("\"nomes\"");
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        arvore.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        arvore.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                arvoreAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        arvore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arvoreMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(arvore);

        tabela_query.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        tabela_query.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabela_query);

        area_query.setColumns(20);
        area_query.setFont(new java.awt.Font("Liberation Sans", 0, 30)); // NOI18N
        area_query.setRows(5);
        area_query.setText("Digite sua query:");
        jScrollPane3.setViewportView(area_query);

        botao_exportar.setFont(new java.awt.Font("Liberation Sans", 0, 30)); // NOI18N
        botao_exportar.setText("Exportar CSV");
        botao_exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_exportarActionPerformed(evt);
            }
        });

        botao_executar.setFont(new java.awt.Font("Liberation Sans", 0, 30)); // NOI18N
        botao_executar.setText("Executar query");
        botao_executar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_executarActionPerformed(evt);
            }
        });

        botao_limpar.setFont(new java.awt.Font("Liberation Sans", 0, 30)); // NOI18N
        botao_limpar.setText("Limpar");
        botao_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_limparActionPerformed(evt);
            }
        });

        botao_voltar.setFont(new java.awt.Font("Liberation Sans", 0, 30)); // NOI18N
        botao_voltar.setText("Voltar");
        botao_voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_voltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(texto_tela_inicial)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botao_exportar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botao_voltar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(botao_executar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(botao_limpar)
                                        .addGap(322, 322, 322))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(texto_tela_inicial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botao_executar)
                            .addComponent(botao_limpar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botao_exportar)
                    .addComponent(botao_voltar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arvoreAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_arvoreAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_arvoreAncestorAdded

    private void arvoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arvoreMouseClicked
        // TODO add your handling code here:
        DefaultMutableTreeNode aux = (DefaultMutableTreeNode)arvore.getSelectionPath().getLastPathComponent();
        String db_nome = aux.getUserObject().toString();
        
        if(!(db_nome.equals("Databases")))
        {
            //System.out.println(db_nome);
            aux = (DefaultMutableTreeNode)arvore.getSelectionPath().getPathComponent(1);
            db_nome = aux.getUserObject().toString();
            texto_tela_inicial.setText("Banco de dados - " + db_nome);
            banco_selecionado = db_nome;
        }
    }//GEN-LAST:event_arvoreMouseClicked

    private void botao_executarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_executarActionPerformed
        
        String query = area_query.getText();
        
        try {
            Connection con = DriverManager.getConnection(url + banco_selecionado, usuario, senha);
            Statement stmt = con.createStatement();
            
            
            DefaultTableModel modelo = new DefaultTableModel();
            
            tabela_query.setModel(modelo);
            
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            //Cria as colunas da tabela
            modelo.addColumn("#");
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                modelo.addColumn(rsmd.getColumnName(i));
            }
            
            int count = 1;
            while(rs.next() && count < 1001)
            {
                Object[] linha = new Object[rsmd.getColumnCount()+1];
                
                linha[0] = count;
                for(int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    linha[i] = rs.getString(i);
                }
                
                modelo.addRow(linha);
                count++;
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro db: " + ex);
        }
        
    }//GEN-LAST:event_botao_executarActionPerformed

    private void botao_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_limparActionPerformed
        area_query.setText("");
    }//GEN-LAST:event_botao_limparActionPerformed

    private void botao_voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_voltarActionPerformed
        // TODO add your handling code here:
        tela_inicial inicial = new tela_inicial();
        inicial.show();
        dispose();
    }//GEN-LAST:event_botao_voltarActionPerformed

    private void botao_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_exportarActionPerformed

        Connection con;
        try {
            con = DriverManager.getConnection(url + banco_selecionado, usuario, senha);
            Statement stmt = con.createStatement();
            
            String query = area_query.getText();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            String arq_nome = "./" + rsmd.getTableName(1) + ".csv";
            try {
                FileWriter arq = new FileWriter(arq_nome);
                
                String linha = "";
                //Preenche a linha com o nome dos campos
                for(int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    linha += rsmd.getColumnName(i) + ";";
                }
                arq.write(linha + "\n");
                
                //Percorre o result set
                while(rs.next())
                {
                    linha = "";

                    for(int i = 1; i <= rsmd.getColumnCount(); i++)
                    {
                        //System.out.println(rsmd.getColumnType(i));
                        if(rsmd.getColumnType(i) >= 2 && rsmd.getColumnType(i) <= 8)
                            linha += rs.getString(i);
                        else
                            linha += "\"" + rs.getString(i) + "\";";
                    }
                    arq.write(linha + "\n");
                }
                arq.close();

            } catch (IOException ex) {
                System.out.println("Erro IO:" + ex);
            }
        } catch (SQLException ex) {
            System.out.println("Erro db:" + ex);
        }
        
    }//GEN-LAST:event_botao_exportarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area_query;
    private javax.swing.JTree arvore;
    private javax.swing.JButton botao_executar;
    private javax.swing.JButton botao_exportar;
    private javax.swing.JButton botao_limpar;
    private javax.swing.JButton botao_voltar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tabela_query;
    private javax.swing.JLabel texto_tela_inicial;
    // End of variables declaration//GEN-END:variables
}
