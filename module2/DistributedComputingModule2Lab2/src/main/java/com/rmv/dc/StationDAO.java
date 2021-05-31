package com.rmv.dc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {
    public static Station findById(int id) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM station "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            Station Station = null;
            if(rs.next()) {
                Station = new Station();
                Station.setId(rs.getInt(1));
                Station.setName(rs.getString(2));
                Station.setWayId(rs.getInt(3));
                Station.setDuration(rs.getInt(4));
            }
            st.close();
            return Station;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static Station findByName(String name) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM Station "
                            + "WHERE name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            Station Station = null;
            if(rs.next()) {
                Station = new Station();
                Station.setId(rs.getInt(1));
                Station.setName(rs.getString(3));
                Station.setWayId(rs.getInt(2));
                Station.setDuration(rs.getInt(4));
            }
            st.close();
            return Station;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static boolean update(Station Station) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "UPDATE Station "
                            + "SET name = ?, wayid = ?, duration = ? "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, Station.getName());
            st.setInt(2, Station.getWayId());
            st.setInt(3, Station.getDuration());
            st.setInt(4, Station.getId());
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

    public static boolean insert(Station Station) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO Station (name,wayid,duration) "
                            + "VALUES (?,?,?) "
                            + "RETURNING id";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, Station.getName());
            st.setInt(2, Station.getWayId());
            st.setInt(3, Station.getDuration());
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                Station.setId(rs.getInt(1));
            } else
                return false;
            st.close();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Station Station) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM Station "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, Station.getId());
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

    public static List<Station> findAll(){
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM Station";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Station> list = new ArrayList<>();
            while(rs.next()) {
                var Station = new Station();
                Station.setId(rs.getInt(1));
                Station.setName(rs.getString(3));
                Station.setWayId(rs.getInt(2));
                Station.setDuration(rs.getInt(4));
                list.add(Station);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static List<Station> findByWayId(Integer id){
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM Station "
                            + "WHERE wayid = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            List<Station> list = new ArrayList<>();
            while(rs.next()) {
                var Station = new Station();
                Station.setId(rs.getInt(1));
                Station.setName(rs.getString(2));
                Station.setWayId(rs.getInt(3));
                Station.setDuration(rs.getInt(4));
                list.add(Station);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
