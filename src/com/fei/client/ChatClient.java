package com.fei.client;

import com.fei.paintUI.PainterUI;
import com.fei.paintUI.util.ClientUtils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;


public class ChatClient extends JFrame {
    private Socket socket ;                  //负责和服务器通信
    private JTextArea sendArea ;        //消息编辑区域
    private JTextArea contentArea ;   //群聊消息显示框
    private String name ;                   //当前用户名称



    public ChatClient(Socket socket, String name)  {
        this.socket = socket ;
        this.name  = name ;
        this.init()   ;       //初始化聊天客户端
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        /*接下来启动单独线程，专门从服务器中读取数据
         *
         */
        ClientThread clientThread  = new ClientThread(socket,contentArea) ;
        clientThread.start();

        //启动一个绘画线程
        PaintThread paintThread = new PaintThread();
        paintThread.start();

        //再启动一个线程用来不停地检查msgList(看看ui是伐画了新的东西)
        MsgThread msgThread = new MsgThread(socket);
        msgThread.start();
    }

    public void init( )  {
        this.setTitle("我的聊天室");
        this.setSize(300,400);
        int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() ;
        int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() ;

        this.setLocation( (x-this.getWidth() )/2, ( y-this.getHeight() )/2 );
        this.setResizable(false);      //不允许用户改变大小

        contentArea = new JTextArea() ;
        contentArea.setLineWrap(true);  //换行方法
        JScrollPane logPanel  = new JScrollPane(contentArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) ;

        sendArea = new JTextArea() ;
        sendArea.setLineWrap(true);    //控制每行显示长度最大不超过界面长度
        JScrollPane sendPanel  = new JScrollPane(sendArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) ;

        //创建一个分隔窗格
        JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,logPanel,sendPanel) ;
        splitpane.setDividerLocation(250);
        this.add(splitpane,BorderLayout.CENTER) ;

        //按钮面板
        JPanel bPanel  = new JPanel() ;
        bPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)) ;
        this.add(bPanel,BorderLayout.SOUTH) ;

        JLabel namelabel = new JLabel("昵称： "+this.name+"  ") ;
        bPanel.add(namelabel) ;

        JButton closeButton = new JButton("关闭") ;
        closeButton.addActionListener( new ActionListener( )  {
            public void actionPerformed(ActionEvent e)  {

            }
        });
        bPanel.add(closeButton) ;

        JButton sendButton = new JButton("发送") ;

        sendButton.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String str = sendArea.getText() ;
                SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss") ;
                String time  = formater.format(new Date() ) ;
                String sendStr = name+" "+time+" 说: "+str ;
                PrintWriter out = null ;
                try  {
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream() ) ) ;
                    out.println(sendStr) ;
                    out.flush();
                }catch(Exception e1)  {
                    e1.printStackTrace();
                }
                sendArea.setText("");
            }
        });

        bPanel.add(sendButton) ;
    }

}

//画图程序的thread
class PaintThread extends Thread  {

    public void run()  {
        PainterUI pui = new PainterUI();
        pui.initUI();
    }
}

class MsgThread extends Thread  {

    private Socket socket ;

    public MsgThread(Socket socket)  {
        this.socket = socket ;
    }

    public void run()  {
        //不停地循环检查是否有新的消息
        while(true){
            if(ClientUtils.getJsonMsg().size()!=0){
                //取出第一个元素
                String msg = ClientUtils.getJsonMsg().remove(0);
                PrintWriter out = null ;
                try  {
                    //将msg发送给服务器
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())) ;
                    out.println(msg) ;
                    out.flush();
                }catch(Exception e1)  {
                    e1.printStackTrace();
                }

            }else{  //如果没有内容就睡一会
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


//客户端与服务器端通信的线程类
class ClientThread extends Thread  {
    private Socket socket ;
    private JTextArea contentArea ;

    public ClientThread(Socket socket, JTextArea  conteArea)  {
        this.socket = socket ;
        this.contentArea = conteArea ;
    }

    public void run()  {
        BufferedReader br = null ;
        try  {
            //从socket读取数据
            br = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            String str = null ;
            while( (str = br.readLine()) != null)  {
                System.out.println(str) ;
                //contentArea.append(str);
                //contentArea.append("\n");
                ClientUtils.putShape(str);
            }
        } catch(IOException e)  {
            e.printStackTrace();
        } finally  {
            if(br != null)  {
                try  {
                    br.close () ;
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }}

