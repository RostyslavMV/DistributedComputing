package com.rmv.dc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WayDAO {
    public static Way findById(int id) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM way "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            Way way = null;
            if(rs.next()) {
                way = new Way();
                way.setId(rs.getInt(2));
                way.setName(rs.getString(1));
            }
            st.close();
            return way;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static Way findByName(String name) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM way "
                            + "WHERE name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            Way way = null;
            if(rs.next()) {
                way = new Way();
                way.setId(rs.getInt(2));
                way.setName(rs.getString(1));
            }
            st.close();
            return way;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static boolean update(Way way) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "UPDATE way "
                            + "SET name = ? "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, way.getName());
            st.setInt(2, way.getId());
            var result = st.executeUpdate();
            st.close();
            if(result>0)
                return true;
            else
                return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean insert(Way way) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO way (name) "
                            + "VALUES (?) "
                            + "RETURNING id";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, way.getName());
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                way.setId(rs.getInt(1));
            } else
                return false;
            st.close();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Way way) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM way "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, way.getId());
            var result = st.executeUpdate();
            st.close();
            if(result>0)
                return true;
            else
                return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static List<Way> findAll(){
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM way";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Way> list = new ArrayList<>();
            while(rs.next()) {
                var way = new Way();
                way.setId(rs.getInt(2));
                way.setName(rs.getString(1));
                list.add(way);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
