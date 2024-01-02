package org.hachimi.EduCat.service;

import org.hachimi.EduCat.Entity.User;
import org.hachimi.EduCat.Exceptions.ServerException;
import org.hachimi.EduCat.Exceptions.UserNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DataService {

    private String databaseUrl;
    private String databasePassword;

    private String databaseUserName;

    Connection connection = null;
    public DataService( @Value("${database.url}") String databaseUrl,
                        @Value("${database.username}") String databaseUserName,
                        @Value("${database.password}") String databasePassword) {

        this.databaseUrl = databaseUrl;
        this.databaseUserName = databaseUserName;
        this.databasePassword = databasePassword;
        try{
            connection = dataBaseConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Bean
    public Connection dataBaseConnection() throws SQLException{
        return DriverManager.getConnection(databaseUrl, databaseUserName, databasePassword);
    }

    public JSONObject insertUser(User user) throws ServerException {
        JSONObject ret = new JSONObject();
        try{
            String sql = "INSERT INTO `utilisateur` (`IdUser`, `Nom`, `Prenom`, `Email`, `Classe` , `MotDePasse`)" +
                    " VALUES (NULL, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getForename());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getClasse());
            pstmt.setString(5, user.getPasseword());
            int rs = pstmt.executeUpdate();
            if (rs <= 0) ret.put("error", "Error when access to DataBase");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new ServerException();
        }

        return ret;
    }

    public JSONObject getUser(String user_mail, String user_password) throws ServerException, UserNotFoundException{
        JSONObject ret = new JSONObject();
        try{
            String sql = "SELECT * FROM utilisateur WHERE Email = ? AND MotDePasse = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user_mail);
            pstmt.setString(2, user_password);
            ResultSet resultSet = pstmt.executeQuery();
            int rows_count = 0;
            if(resultSet.next()){
                rows_count ++ ;
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    ret.put(columnName, resultSet.getString(columnName));
                }
            }
            if(rows_count <= 0 ) throw new UserNotFoundException();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new ServerException();
        }

        return ret;
    }

    public JSONArray getTableData(String table) {
        JSONArray jsonArray =  new JSONArray();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    json.put(columnName, resultSet.getString(columnName));
                }
                jsonArray.put(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return jsonArray;
    }

}
