package com.rmv.dc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
  private Socket sock = null;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private static final String split = "#";

  Client(String ip, int port) throws IOException {
    // establish connection
    sock = new Socket(ip, port);
    // get input/output streams
    in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    out = new PrintWriter(sock.getOutputStream(), true);
  }

  public Way wayFindById(Integer id) {
    var query = "WayFindById" + split + id.toString();
    out.println(query);
    String response = "";
    try {
      response = in.readLine();
      return new Way(id, response);
    } catch (IOException e) {

      e.printStackTrace();
    }
    return null;
  }

  public Station stationFindByName(String name) {
    var query = "StationFindByName" + split + name;
    out.println(query);
    String response = "";
    try {
      response = in.readLine();
      String[] fields = response.split(split);
      var id = Integer.parseInt(fields[0]);
      var wayId = Integer.parseInt(fields[1]);
      var size = Integer.parseInt(fields[3]);
      return new Station(id, wayId, name, size);
    } catch (IOException e) {

      e.printStackTrace();
    }
    return null;
  }

  public Way wayFindByName(String name) {
    var query = "WayFindByName" + split + name;
    out.println(query);
    try {
      var response = Integer.parseInt(in.readLine());
      return new Way(response, name);
    } catch (IOException e) {

      e.printStackTrace();
    }
    return null;
  }

  public boolean stationUpdate(Station station) {
    var query =
        "StationUpdate"
            + split
            + station.getId().toString()
            + "#"
            + station.getWayId().toString()
            + "#"
            + station.getName()
            + "#"
            + station.getDuration().toString();
    out.println(query);
    try {
      var response = in.readLine();
      if ("true".equals(response)) return true;
      else return false;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return false;
  }

  public boolean wayUpdate(Way way) {
    var query = "WayUpdate" + split + way.getId().toString() + "#" + way.getName();
    out.println(query);
    try {
      var response = in.readLine();
      if ("true".equals(response)) return true;
      else return false;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return false;
  }

  public boolean stationInsert(Station station) {
    var query =
        "StationInsert"
            + "#"
            + station.getWayId().toString()
            + "#"
            + station.getName()
            + "#"
            + station.getDuration().toString();
    out.println(query);
    try {
      var response = in.readLine();
      if ("true".equals(response)) return true;
      else return false;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return false;
  }

  public boolean wayInsert(Way way) {
    var query = "WayInsert" + "#" + way.getName();
    out.println(query);
    try {
      var response = in.readLine();
      if ("true".equals(response)) return true;
      else return false;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return false;
  }

  public boolean wayDelete(Way way) {
    var query = "WayDelete" + split + way.getId().toString();
    out.println(query);
    try {
      var response = in.readLine();
      if ("true".equals(response)) return true;
      else return false;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return false;
  }

  public boolean stationDelete(Station station) {
    var query = "StationDelete" + split + station.getId().toString();
    out.println(query);
    try {
      var response = in.readLine();
      if ("true".equals(response)) return true;
      else return false;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return false;
  }

  public List<Way> wayAll() {
    var query = "WayAll";
    out.println(query);
    var list = new ArrayList<Way>();
    try {
      var response = in.readLine();
      String[] fields = response.split(split);
      for (int i = 0; i < fields.length; i += 2) {
        var id = Integer.parseInt(fields[i]);
        var name = fields[i + 1];
        list.add(new Way(id, name));
      }
      return list;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return null;
  }

  public List<Station> stationAll() {
    var query = "StationAll";
    out.println(query);
    var list = new ArrayList<Station>();
    try {
      var response = in.readLine();
      String[] fields = response.split(split);
      for (int i = 0; i < fields.length; i += 4) {
        var id = Integer.parseInt(fields[i]);
        var wayId = Integer.parseInt(fields[i + 1]);
        var name = fields[i + 2];
        var size = Integer.parseInt(fields[i + 3]);
        list.add(new Station(id, wayId, name, size));
      }
      return list;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return null;
  }

  public List<Station> stationFindByWayId(Integer idc) {
    var query = "StationFindByWayId" + split + idc.toString();
    out.println(query);
    var list = new ArrayList<Station>();
    try {
      var response = in.readLine();
      String[] fields = response.split(split);
      for (int i = 0; i < fields.length; i += 4) {
        var id = Integer.parseInt(fields[i]);
        var wayId = Integer.parseInt(fields[i + 1]);
        var name = fields[i + 2];
        var size = Integer.parseInt(fields[i + 3]);
        list.add(new Station(id, wayId, name, size));
      }
      return list;
    } catch (IOException e) {

      e.printStackTrace();
    }
    return null;
  }

  public void disconnect() {
    try {
      sock.close();
    } catch (IOException e) {

      e.printStackTrace();
    }
  }

  //    public static void main(String[] args) throws IOException {
  //        //(new Client()).test("localhost",12345);
  //    }
}
