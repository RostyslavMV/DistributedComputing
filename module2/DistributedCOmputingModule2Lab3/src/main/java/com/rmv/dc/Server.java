package com.rmv.dc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private ServerSocket server = null;
  private Socket sock = null;
  private PrintWriter out = null;
  private BufferedReader in = null;

  public void start(int port) throws IOException {
    server = new ServerSocket(port);
    while (true) {
      sock = server.accept();
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      out = new PrintWriter(sock.getOutputStream(), true);
      while (processQuery())
        ;
    }
  }

  private boolean processQuery() {
    int result = 0;
    String response = "";
    try {
      String query = in.readLine();
      if (query == null) return false;

      String[] fields = query.split("#");
      if (fields.length == 0) {
        return true;
      } else {
        var action = fields[0];
        Way way;
        Station station;

        System.out.println(action);

        switch (action) {
          case "WayFindById":
            var id = Integer.parseInt(fields[1]);
            way = WayDAO.findById(id);
            response = way.getName();
            System.out.println(response);
            out.println(response);
            break;
          case "StationFindByWayid":
            id = Integer.parseInt(fields[1]);
            var list = StationDAO.findByWayId(id);
            var str = new StringBuilder();
            for (Station c : list) {
              str.append(c.getId());
              str.append("#");
              str.append(c.getWayId());
              str.append("#");
              str.append(c.getName());
              str.append("#");
              str.append(c.getDuration());
              str.append("#");
            }
            response = str.toString();
            System.out.println(response);
            out.println(response);
            break;
          case "StationFindByName":
            var name = fields[1];
            station = StationDAO.findByName(name);
            response =
                station.getId().toString()
                    + "#"
                    + station.getWayId().toString()
                    + "#"
                    + station.getName()
                    + "#"
                    + station.getDuration().toString();
            System.out.println(response);
            out.println(response);
            break;
          case "WayFindByName":
            name = fields[1];
            way = WayDAO.findByName(name);
            response = way.getId().toString();
            System.out.println(response);
            out.println(response);
            break;
          case "stationUpdate":
            id = Integer.parseInt(fields[1]);
            var wayid = Integer.parseInt(fields[2]);
            name = fields[3];
            var size = Integer.parseInt(fields[4]);
            station = new Station(id, wayid, name, size);
            if (StationDAO.update(station)) response = "true";
            else response = "false";
            System.out.println(response);
            out.println(response);
            break;
          case "WayUpdate":
            id = Integer.parseInt(fields[1]);
            name = fields[2];
            way = new Way(id, name);
            if (WayDAO.update(way)) response = "true";
            else response = "false";
            System.out.println(response);
            out.println(response);
            break;
          case "StationInsert":
            wayid = Integer.parseInt(fields[1]);
            name = fields[2];
            size = Integer.parseInt(fields[3]);
            station = new Station((int) 0, wayid, name, size);
            if (StationDAO.insert(station)) response = "true";
            else response = "false";
            System.out.println(response);
            out.println(response);
            break;
          case "WayInsert":
            name = fields[1];
            way = new Way();
            way.setName(name);

            System.out.println(name);

            if (WayDAO.insert(way)) response = "true";
            else response = "false";
            System.out.println(response);
            out.println(response);
            break;
          case "StationDelete":
            id = Integer.parseInt(fields[1]);
            station = new Station();
            station.setId(id);
            if (StationDAO.delete(station)) response = "true";
            else response = "false";
            System.out.println(response);
            out.println(response);
            break;
          case "WayDelete":
            id = Integer.parseInt(fields[1]);
            way = new Way();
            way.setId(id);
            if (WayDAO.delete(way)) response = "true";
            else response = "false";
            System.out.println(response);
            out.println(response);
            break;
          case "StationAll":
            var list1 = StationDAO.findAll();
            str = new StringBuilder();
            for (Station c : list1) {
              str.append(c.getId());
              str.append("#");
              str.append(c.getWayId());
              str.append("#");
              str.append(c.getName());
              str.append("#");
              str.append(c.getDuration());
              str.append("#");
            }
            response = str.toString();
            System.out.println(response);
            out.println(response);
            break;
          case "WayAll":
            var list2 = WayDAO.findAll();
            str = new StringBuilder();
            for (Way c : list2) {
              str.append(c.getId());
              str.append("#");
              str.append(c.getName());
              str.append("#");
            }
            response = str.toString();
            System.out.println(response);
            out.println(response);
            break;
        }
      }

      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public static void main(String[] args) {
    try {
      Server srv = new Server();
      srv.start(12345);
    } catch (IOException e) {
      System.out.println("Error");
    }
  }
}
