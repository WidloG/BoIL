package com.example.widlowicherekboil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

@SpringBootApplication
public class WidloWicherekBoilApplication extends JFrame implements ActionListener {



    JButton CPM;
    JButton Posrednik;
    public static int maxTime;

    //Choosing method
    public void initUI(){

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

        CPM = new JButton("Metoda CPM");
        CPM.setPreferredSize(new Dimension(300,170));
        //CPM.addActionListener(this);
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
                cpmFrame.setLayout(null);

                String result = JOptionPane.showInputDialog(null,"Wpisz ilosc czynnosci: ");
                int num = Integer.parseInt(result);

                Border border = BorderFactory.createLineBorder(Color.black, 1);

                // headers

                JLabel czynnosc = new JLabel("<html><center>Czynnosc <br></br> [Wpisz wielka literę]</center></html>");
                czynnosc.setBounds(0, 0, 200, 50);
                czynnosc.setBorder(border);
                czynnosc.setHorizontalAlignment(JLabel.CENTER);
                cpmFrame.add(czynnosc);

                JLabel czas = new JLabel("<html><center>Czas <br></br> [Wpisz liczbę całkowitą]</center></html>");
                czas.setBounds(200, 0, 100, 50);
                czas.setBorder(border);
                czas.setHorizontalAlignment(JLabel.CENTER);
                cpmFrame.add(czas);

                JLabel poprzednik = new JLabel("<html><center>Poprzednicy <br></br> [Oddziel przecinkiem]</center></html>");
                poprzednik.setBounds(300, 0, 200, 50);
                poprzednik.setBorder(border);
                poprzednik.setHorizontalAlignment(JLabel.CENTER);
                cpmFrame.add(poprzednik);

                int startY = 50;
                int i = 0;
                while (i < num){
                    JTextField czynnosc1 = new JTextField("");
                    czynnosc1.setBounds(0, startY, 200, 20);
                    czynnosc1.setBorder(border);
                    czynnosc1.setHorizontalAlignment(JLabel.CENTER);
                    cpmFrame.add(czynnosc1);

                    JTextField czas1 = new JTextField("", 1);
                    czas1.setBounds(200, startY, 100, 20);
                    czas1.setBorder(border);
                    czas1.setHorizontalAlignment(JLabel.CENTER);
                    cpmFrame.add(czas1);

                    JTextField poprzednik1 = new JTextField("", 1);
                    poprzednik1.setBounds(300, startY, 200, 20);
                    poprzednik1.setBorder(border);
                    poprzednik1.setHorizontalAlignment(JLabel.CENTER);
                    cpmFrame.add(poprzednik1);
                    i++;
                    startY +=25;

                    HashSet<Task> allTasks = new HashSet<Task>();


                    // trace do tego cierpliwosc, here
                    czynnosc1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Task A = new Task(czynnosc1.getText(), Integer.parseInt(czas1.getText()), poprzednik1.getText());
                            System.out.println(czynnosc1.getText());
                        }
                    });
                    //System.out.println(czynnosc1.getText());
                }



                //text field


                cpmFrame.setSize(516, 500);
                cpmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                cpmFrame.setVisible(true);
                cpmFrame.setLocationRelativeTo(null);


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
        System.out.println();

        HashSet<Task> allTasks = new HashSet<Task>();

        Task A = new Task("A", 5);
        Task B = new Task("B", 3, A);
        Task C = new Task("C", 4);
        Task D = new Task("D", 6, A);
        Task E = new Task("E", 4, C);
        Task F = new Task("F", 3, B, C, D);
        allTasks.add(A);
        allTasks.add(B);
        allTasks.add(C);
        allTasks.add(D);
        allTasks.add(E);
        allTasks.add(F);
        Task[] result = criticalPath(allTasks);
        print(result);
    }

    // Logic behind

    public static class Task {
        public int time;
        public int criticalTime;
        public String name;
        public int earlyStart;
        public int earlyFinish;
        public int latestStart;
        public int latestFinish;
        public HashSet<Task> dependencies = new HashSet<Task>();

        public Task(String name, int time, Task... dependencies) {
            this.name = name;
            this.time = time;
            this.dependencies.addAll(Arrays.asList(dependencies));
            this.earlyFinish = -1;
        }

        public void setLatest() {
            latestStart = maxTime - criticalTime;
            latestFinish = latestStart + time;
        }
    }

    public static Task[] criticalPath(Set<Task> tasks) {
        HashSet<Task> completed = new HashSet<Task>();
        HashSet<Task> remaining = new HashSet<Task>(tasks);

        while (!remaining.isEmpty()) {
            boolean progress = false;

            for (Iterator<Task> it = remaining.iterator(); it.hasNext();) {
                Task task = it.next();
                if (completed.containsAll(task.dependencies)) {
                    int critical = 0;
                    for (Task t : task.dependencies) {
                        if (t.criticalTime > critical) {
                            critical = t.criticalTime;
                        }
                    }
                    task.criticalTime = critical + task.time;
                    completed.add(task);
                    it.remove();
                    progress = true;
                }
            }
            if (!progress)
                throw new RuntimeException("Cyclic dependency, algorithm stopped!");
        }

        maxTime(tasks);
        HashSet<Task> initialNodes = initials(tasks);
        calculateEarly(initialNodes);

        Task[] ret = completed.toArray(new Task[0]);
        Arrays.sort(ret, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.name.compareTo(o2.name);
            }
        });

        return ret;
    }

    public static void calculateEarly(HashSet<Task> initials) {
        for (Task initial : initials) {
            initial.earlyStart = 0;
            initial.earlyFinish = initial.time;
            setEarly(initial);
        }
    }

    public static void setEarly(Task initial) {
        int completionTime = initial.earlyFinish;
        for (Task t : initial.dependencies) {
            if (completionTime >= t.earlyStart) {
                t.earlyStart = completionTime;
                t.earlyFinish = completionTime + t.time;
            }
            setEarly(t);
        }
    }

    public static HashSet<Task> initials(Set<Task> tasks) {
        HashSet<Task> remaining = new HashSet<Task>(tasks);
        for (Task t : tasks) {
            for (Task td : t.dependencies) {
                remaining.remove(td);
            }
        }

        System.out.print("Initial nodes: ");
        for (Task t : remaining)
            System.out.print(t.name + " ");
        System.out.print("\n\n");
        return remaining;
    }

    public static void maxTime(Set<Task> tasks) {
        int max = -1;
        for (Task t : tasks) {
            if (t.criticalTime > max)
                max = t.criticalTime;
        }
        maxTime = max;
        System.out.println("Critical path length (time): " + maxTime);
        for (Task t : tasks) {
            t.setLatest();
        }
    }

    public static void print(Task[] tasks) {
        System.out.println( "Task" +  "\tES" + "\tEF" + "\tLS" + "\tLF" + "\tSlack" + "\tCritical?");
        for (Task t : tasks) {
            String criticalCond = t.earlyStart == t.latestStart ? "Yes" : "No";
            System.out.println(t.name + "\t\t" + t.earlyStart + "\t" + t.earlyFinish + "\t" + t.latestStart + "\t" + t.latestFinish + "\t" + (t.latestStart - t.earlyStart) + "\t\t" + criticalCond);
        }
    }

}
