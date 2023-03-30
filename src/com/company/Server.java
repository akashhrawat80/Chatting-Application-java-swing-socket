package com.company;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
public class Server implements ActionListener {
    //we want that as soon as i run the class immediately a frame should
    // open thats why all main coding is done in the constructor.
    //All the decoration should be made Global....
    JPanel p1;//just like a div tag
    JTextField jt;
    JButton jb;
    JPanel ja;
    Timer t;
    static ServerSocket sk;
    static Socket so;
    static DataInputStream Di;
    static DataOutputStream Do;
    boolean type;
    static Box algn=Box.createVerticalBox();
    static JFrame f=new JFrame();
    Server() {

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);   //to change color only of the panel
        p1.setBounds(0, 0, 450, 70);
        f.add(p1);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/company/chatting/1 (1).png"));
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

        JLabel l3 = new JLabel("anshu");
        l3.setBounds(120, 5, 100, 40);
        l3.setForeground(Color.WHITE);
        l3.setFont(new Font("SERIF", Font.ITALIC, 25));
        p1.add(l3);

        JLabel l10 = new JLabel("Active Now");
        l10.setBounds(125, 35, 100, 30);
        l10.setForeground(Color.lightGray);
        p1.add(l10);

        t=new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!type) l10.setText("Active Now");
            }
        });
        t.setInitialDelay(2000);
        //............................................................

        ja = new JPanel();
        ja.setFont(new Font("BOLD", Font.ITALIC, 18));

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
        f.add(jt);
        jt.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                l10.setText("typing...");
                t.stop();
                type=true;
            }
            @Override
            public void keyReleased(KeyEvent e1){
                type=false;
                if(!t.isRunning())
                    t.start();
            }
        });

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
        f.setLocation(180, 20);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        Server s = new Server();
        String messageInp ="";
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            sk = new ServerSocket(6001);//binding application on a particular port
            so = sk.accept();//through socket we send and receive message//so is representing client
            Di = new DataInputStream(so.getInputStream());//getting message present in the stream by opening
                                                        // stream for getting data
            Do = new DataOutputStream(so.getOutputStream());//open stream for writing data
            while(!messageInp.equals("#stop")) {
                messageInp = (String) Di.readUTF();
                JPanel p= Message(messageInp);
                JPanel l=new JPanel(new BorderLayout());
                l.add(p,BorderLayout.LINE_START);
                algn.add(l);
                f.validate();
            }
            Di.close();
            sk.close();
            so.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent p) {
        try {
                String str = jt.getText();
                PrintWriter pw=new PrintWriter("chat.txt");
                pw.println("Server:"+str+"\n");
                JPanel f1=Message(str);
                ja.setLayout(new BorderLayout());
                JPanel x=new JPanel(new BorderLayout());
                f1.setBackground(Color.pink);
                x.add(f1,BorderLayout.LINE_END);
                algn.add(x);
                algn.add(Box.createVerticalStrut(10));
                ja.add(algn,BorderLayout.PAGE_START);
                jt.setText("");
                Do.writeUTF(str);//to send data we use utf(unicode text format) to send 16 bit data through byte stream
                Do.flush();
        }catch(Exception e){
            JOptionPane.showMessageDialog(f,e.getMessage());
        }
    }
    public static JPanel Message(String str){
        JPanel p1=new JPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
        JLabel l=new JLabel("<html><p style=\"width:150px\">"+str+"</p></html>");
        l.setFont(new Font("Tahoma",Font.ITALIC,20));
        l.setBackground(Color.pink);
        l.setOpaque(true);
        l.setBorder(new EmptyBorder(15,15,15,60));
        Calendar c=Calendar.getInstance();
        SimpleDateFormat f=new SimpleDateFormat("hh:mm");
        JLabel l2=new JLabel();
        l2.setText(f.format(c.getTime()));
        p1.add(l);
        p1.add(l2);
        return p1;
    }
}