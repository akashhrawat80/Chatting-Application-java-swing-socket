package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.plaf.ScrollBarUI;
public class Client implements ActionListener,KeyListener{

    JPanel p1;
    JTextField jt;
    JButton jb;
    JLabel l10;
    static JFrame f=new JFrame();
    boolean type;
    Timer t;
    static JPanel ja;
    static Socket so;
    static DataInputStream Dii;
    static DataOutputStream Doo;
    static Box algn=Box.createVerticalBox();
    private Client() {
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);//new Color(7, 94, 84)
        p1.setBounds(0, 0, 450, 70);
        f.add(p1);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/company/chatting/2.png"));
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l = new JLabel(i6);//these labels are placed in the frame via a layout bydefault gridbag
        l.setBounds(40, 5, 60, 60);//to create label setBounds(locationX,locationY,Length,Breadth)
        p1.add(l);//to
//........................................................................
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("com/company/chatting/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l4 = new JLabel(i9);//these labels are placed in the frame via a layout bydefault gridbag
        l4.setBounds(315, 17, 30, 30);//to create label setBounds(locationX,locationY,Length,Breadth)
        p1.add(l4);//to

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("com/company/chatting/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel l5 = new JLabel(i12);//these labels are placed in the frame via a layout bydefault gridbag
        l5.setBounds(365, 17, 30, 30);//to create label setBounds(locationX,locationY,Length,Breadth)
        p1.add(l5);//to

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("com/company/chatting/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 30, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel l6 = new JLabel(i15);//these labels are placed in the frame via a layout bydefault gridbag
        l6.setBounds(410, 17, 15, 30);//to create label setBounds(locationX,locationY,Length,Breadth)
        p1.add(l6);

//....................................................................................................
        ImageIcon i = new ImageIcon(ClassLoader.getSystemResource("com/company/chatting/3.png"));
        Image i2 = i.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 17, 30, 30);
        p1.add(l1);
        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        JLabel l13 = new JLabel("Akash");
        l13.setBounds(120, 5, 100, 40);
        l13.setForeground(Color.WHITE);
        l13.setFont(new Font("SERIF", Font.ITALIC, 25));
        p1.add(l13);

        l10 = new JLabel("Active Now");
        l10.setBounds(125, 35, 100, 30);
        l10.setForeground(Color.lightGray);
        p1.add(l10);

        t=new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!type){
                    l10.setText("Active Now");
                }
            }
        });
        t.setInitialDelay(2000);

        ja = new JPanel();
        ja.setFont(new Font("BOLD",Font.ITALIC,18));

        JScrollPane pane=new JScrollPane(ja);
        pane.setBounds(5, 75, 440, 575);
        pane.setBorder(BorderFactory.createEmptyBorder());

        ScrollBarUI ui=new BasicScrollBarUI(){
            protected JButton createDecreaseButton(int orientation){
                JButton button=super.createDecreaseButton(orientation);
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                this.thumbColor=Color.BLACK;
                return button;
            }
            protected JButton createIncreaseButton(int orientation){
                JButton button=super.createIncreaseButton(orientation);
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                return button;
            }
        };
        pane.getVerticalScrollBar().setUI(ui);
        f.add(pane);
        jt = new JTextField();
        jt.setBounds(5, 655, 350, 35);
        jt.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        jt.setForeground(Color.BLACK);
        jt.addKeyListener(this);
        f.add(jt);

        jb = new JButton("Send");
        jb.setBounds(370, 655, 70, 35);
        jb.setBackground(Color.BLACK);
        jb.setForeground(Color.WHITE);
        jb.addActionListener(this);
        f.add(jb);

        f.getContentPane().setBackground(Color.BLACK);//we cant send image directly to the frame we have put it in a label
        //here j and i are called components
        f.setLayout(null);
        f.setSize(450, 700);//by default setLocation is 0,0 i.e., top left location on the screen
        f.setLocation(900, 20);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public static void main(String[] args){

        Client c=new Client();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            InetAddress ia;
            ia =InetAddress.getLocalHost();
            so = new Socket(ia, 6001);//port at which server will listen.//this is represneting server
            InputStream in_str=so.getInputStream();
            Dii = new DataInputStream(in_str);
            OutputStream out_str=so.getOutputStream();
            Doo = new DataOutputStream(out_str);

            String msg = "";
            while (!msg.equals("#stop")) {
                msg = (String) Dii.readUTF();
                JPanel p= Message(msg);
                JPanel l=new JPanel(new BorderLayout());
                l.add(p,BorderLayout.LINE_START);
                algn.add(l);
                f.validate();
            }
        }catch(Exception e){JOptionPane.showMessageDialog(f,e.getMessage());}
    }

    @Override
    public void actionPerformed(ActionEvent p) {
        try {
            String str = jt.getText();
            FileWriter pw=new FileWriter("chat.txt",true);
            pw.write("Client:"+str+"\n");
            JPanel msg=Message(str);
            JPanel n=new JPanel();

            n.setLayout(new BorderLayout());
            n.add(msg,BorderLayout.LINE_END);
            ja.setLayout(new BorderLayout());
            algn.add(n);
            algn.add(Box.createVerticalStrut(10));
            ja.add(algn, BorderLayout.PAGE_START);
            jt.setText("");
            Doo.writeUTF(str);
            Doo.flush();
        }catch(Exception e){ JOptionPane.showMessageDialog(f,e.getMessage());}
    }
    @Override
    public void keyPressed(KeyEvent kp){
        l10.setText("typing...");
        t.stop();
        type=true;
    }

    @Override
    public void keyTyped(KeyEvent kt){}
    @Override
    public void keyReleased(KeyEvent kr){
        type=false;
        if(!t.isRunning())
            t.start();
    }
    public static JPanel Message(String str){
        JPanel j=new JPanel();
        j.setLayout(new BoxLayout(j,BoxLayout.Y_AXIS));
        JLabel l=new JLabel("<html><p style=\"width:150\">"+str+"</p></html>");
        l.setFont(new Font("Tahoma",Font.ITALIC,20));
        l.setBackground(Color.pink);
        l.setOpaque(true);
        l.setBorder(new EmptyBorder(15,15,15,60));
        Calendar c=Calendar.getInstance();
        SimpleDateFormat s=new SimpleDateFormat();
        JLabel time=new JLabel();
        time.setText(s.format(c.getTime()));
        j.add(l);
        j.add(time);
        return j;
    }
}
