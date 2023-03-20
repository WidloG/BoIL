package com.example.widlowicherekboil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SpringBootApplication
public class WidloWicherekBoilApplication extends JFrame implements ActionListener {

    JButton CPM;
    JButton Posrednik;


    //Choosing method
    public void initUI(){

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

        CPM = new JButton("Metoda CPM");
        CPM.setPreferredSize(new Dimension(300,170));
        CPM.addActionListener(this);
        this.add(CPM);
        Posrednik = new JButton("Metoda Posrednika");
        Posrednik.setPreferredSize(new Dimension(300,170));
        Posrednik.addActionListener(this);
        this.add(Posrednik);

    }

    //Action performed
    @Override
    public void actionPerformed(ActionEvent e) {

        //======================CPM======================
        if(CPM.equals(e.getSource())){
            System.out.println("Wybrano CPM");
            dispose();
            EventQueue.invokeLater(() -> {
                WidloWicherekBoilApplication cpmFrame = new WidloWicherekBoilApplication();
                cpmFrame.setSize(500,500);
                cpmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                cpmFrame.setVisible(true);
                cpmFrame.setLocationRelativeTo(null);

              //  JLabel
            });
        }



        //======================POSREDNIK======================
        else if(Posrednik.equals(e.getSource())){
            System.out.println("Metoda Posrednika nie jest dostepna");
        }
    }

    public static void main(String[] args) {

        //Main Frame

        WidloWicherekBoilApplication frame = new WidloWicherekBoilApplication();
        frame.initUI();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        SpringApplication.run(WidloWicherekBoilApplication.class, args);
    }

}
