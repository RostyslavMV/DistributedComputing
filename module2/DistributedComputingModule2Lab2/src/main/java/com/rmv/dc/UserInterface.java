package com.rmv.dc;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UserInterface extends JFrame{
    private static Way currentWay = null;
    private static Station currentStation = null;

    private static boolean editMode = false;
    private static boolean wayMode = true;

    private static final JButton btnAddWay = new JButton("Add Way");
    private static final JButton btnAddStation = new JButton("Add Station");
    private static final JButton btnEdit= new JButton("Edit Data");
    private static final JButton btnCancel= new JButton("Cancel");
    private static final JButton btnSave= new JButton("Save");
    private static final JButton btnDelete= new JButton("Delete");

    private static final Box menuPanel = Box.createVerticalBox();
    private static final Box actionPanel = Box.createVerticalBox();
    private static final Box comboPanel = Box.createVerticalBox();
    private static final Box stationPanel = Box.createVerticalBox();
    private static final Box wayPanel = Box.createVerticalBox();

    private static final JComboBox comboWay = new JComboBox();
    private static final JComboBox comboStation = new JComboBox();

    private static final JTextField tfWayName = new JTextField(30);
    private static final JTextField tfStationName = new JTextField(30);
    private static final JTextField tfStationWayName = new JTextField(30);
    private static final JTextField tfStationDuration = new JTextField(30);

    private static JFrame frame;

    UserInterface(){
        super("Railway system");
        frame = this;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                DBConnection.closeConnection();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();

        // Menu
        menuPanel.add(btnAddWay);
        btnAddWay.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                wayMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                wayPanel.setVisible(true);
                stationPanel.setVisible(false);
                actionPanel.setVisible(true);
                //setContentPane(box);
                pack();
            }
        });
        menuPanel.add(btnAddStation);
        btnAddStation.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                wayMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                wayPanel.setVisible(false);
                stationPanel.setVisible(true);
                actionPanel.setVisible(true);
                //setContentPane(box);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                wayPanel.setVisible(false);
                stationPanel.setVisible(false);
                actionPanel.setVisible(true);
                //setContentPane(box);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Way:"));
        comboPanel.add(comboWay);
        comboWay.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String name = (String) comboWay.getSelectedItem();
                currentWay = WayDAO.findByName((String) comboWay.getSelectedItem());
                wayMode = true;
                wayPanel.setVisible(true);
                stationPanel.setVisible(false);
                fillWayFields();
                //setContentPane(box);
                pack();
            }
        });
        comboPanel.add(new JLabel("Station:"));
        comboPanel.add(comboStation);
        comboStation.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String name = (String) comboStation.getSelectedItem();
                currentStation = StationDAO.findByName((String) comboStation.getSelectedItem());
                wayMode = false;
                wayPanel.setVisible(false);
                stationPanel.setVisible(true);
                fillStationFields();
                //setContentPane(box);
                pack();
            }
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        // Station Fields
        stationPanel.add(new JLabel("Name:"));
        stationPanel.add(tfStationName);
        stationPanel.add(new JLabel("Way Name:"));
        stationPanel.add(tfStationWayName);
        stationPanel.add(new JLabel("Duration:"));
        stationPanel.add(tfStationDuration);
        stationPanel.setVisible(false);

        // Way Fields
        wayPanel.add(new JLabel("Name:"));
        wayPanel.add(tfWayName);
        wayPanel.setVisible(false);

        // Action Bar
        actionPanel.add(btnSave);
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                save();
            }
        });
        actionPanel.add(btnDelete);
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                delete();
            }
        });
        actionPanel.add(btnCancel);
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                wayPanel.setVisible(false);
                stationPanel.setVisible(false);
                actionPanel.setVisible(false);
                //setContentPane(box);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(wayPanel);
        box.add(stationPanel);
        box.add(actionPanel);
        setContentPane(box);
        pack();
    }

    private static void save() {
        if(editMode) {
            if(wayMode) {
                currentWay.setName(tfWayName.getText());
                if (!WayDAO.update(currentWay)) {
                    JOptionPane.showMessageDialog(null, "Error: something went wrong!");
                }
            } else {
                currentStation.setName(tfStationName.getText());
                Way cnt = WayDAO.findByName(tfStationWayName.getText());
                if(cnt == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such Way!");
                    return;
                }
                currentStation.setWayId(cnt.getId());
                currentStation.setDuration(Integer.parseInt(tfStationDuration.getText()));
                if (!StationDAO.update(currentStation)) {
                    JOptionPane.showMessageDialog(null, "Error: something went wrong!");
                }
            }
        } else {
            if (wayMode) {
                var way = new Way();
                way.setName(tfWayName.getText());
                if(WayDAO.insert(way)) {
                    comboWay.addItem(way.getName());
                }
            } else {
                var station = new Station();
                station.setName(tfStationName.getText());
                Way cnt = WayDAO.findByName(tfStationWayName.getText());
                if(cnt == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such Way!");
                    return;
                }
                station.setWayId(cnt.getId());
                station.setDuration(Integer.parseInt(tfStationDuration.getText()));
                if(StationDAO.insert(station)) {
                    comboStation.addItem(station.getName());
                }
            }
        }
    }

    private static void delete() {
        if(editMode) {
            if(wayMode) {
                var list = StationDAO.findByWayId(currentWay.getId());
                for(Station c: list) {
                    comboStation.removeItem(c.getName());
                    StationDAO.delete(c);
                }
                WayDAO.delete(currentWay);
                comboWay.removeItem(currentWay.getName());

            } else {
                StationDAO.delete(currentStation);
                comboStation.removeItem(currentStation.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboWay.removeAllItems();
        comboStation.removeAllItems();
        var ways = WayDAO.findAll();
        var stations = StationDAO.findAll();
        for(Way c: ways) {
            comboWay.addItem(c.getName());
        }
        for(Station c: stations) {
            comboStation.addItem(c.getName());
        }
    }

    private static void clearFields() {
        tfWayName.setText("");
        tfStationName.setText("");
        tfStationWayName.setText("");
        tfStationDuration.setText("");
        currentWay = null;
        currentStation = null;
    }

    private static void fillWayFields() {
        if (currentWay == null)
            return;
        tfWayName.setText(currentWay.getName());
    }
    private static void fillStationFields() {
        if(currentStation == null)
            return;
        Way cnt = WayDAO.findById(currentStation.getWayId());
        tfStationName.setText(currentStation.getName());
        tfStationWayName.setText(cnt.getName());
        tfStationDuration.setText(String.valueOf(currentStation.getDuration()));
    }

    public static void main(String[] args) {
        JFrame myWindow = new UserInterface();
        myWindow.setVisible(true);
    }
}
