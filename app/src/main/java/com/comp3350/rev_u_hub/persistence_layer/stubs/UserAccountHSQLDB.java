package com.comp3350.rev_u_hub.persistence_layer.stubs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;

public class UserAccountHSQLDB implements UserPersistence{


    private final String dbPath;
    private final Connection c;

    public UserAccountHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
        try{
            c = connection();
        }catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private UserObject fromResultSet(final ResultSet rs) throws SQLException {
        final String userName = rs.getString("userName");
        final String passWord = rs.getString("passWord");
        return new UserObject(userName, passWord);
    }

    public List<UserObject> getUserSequential() {
        final List<UserObject> users = new ArrayList<>();

        try{
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                final UserObject user = fromResultSet(rs);
                users.add(user);
            }
            rs.close();
            st.close();

            return users;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    public List<UserObject> searchUser(String userName){

        final List<UserObject> users = new ArrayList<>();

        try{
            final PreparedStatement st = c.prepareStatement("SELECT * FROM users WHERE userName = ?" );
            st.setString(1, userName);
            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final UserObject user = fromResultSet(rs);
                users.add(user);
            }
            rs.close();
            st.close();

            return users;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    public UserObject addNewUser(UserObject newUser) {
        try{
            final PreparedStatement st = c.prepareStatement("INSERT INTO users VALUES(?, ?)");
            st.setString(1, newUser.getUserName());
            st.setString(2, newUser.getPassWord());

            st.executeUpdate();

            return newUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public UserObject updatePassWord(UserObject currentUser) {
        try{
            final PreparedStatement st = c.prepareStatement("UPDATE users SET passWord = ? WHERE userName = ?");
            st.setString(1, currentUser.getPassWord());
            st.setString(2, currentUser.getUserName());

            st.executeUpdate();

            return currentUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void deleteUser(UserObject currentUser) {
        try{
            final PreparedStatement st = c.prepareStatement("DELETE FROM users WHERE userName = ?");
            st.setString(1, currentUser.getUserName());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
