package com.fei.sever;


import com.fei.paintUI.shape.Shape;
import com.fei.paintUI.util.JacksonUtil;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer {
    private List<Socket> sockets = new ArrayList<Socket>() ;    //类集的应用
    public ChatServer() throws IOException  {
        ServerSocket ss = new ServerSocket(8888) ;
        System.out.println("Server is listening the port : 8888") ;

        while(true)  {
            Socket socket = ss.accept() ;
            sockets.add(socket) ;
            String ip = socket.getInetAddress().getHostAddress() ;
            System.out.println("新用户进入！ip是"+ip) ;
            Thread thread = new Thread(new ServerRunner(sockets,socket)) ;
            thread.start();
        }
    }

    public static void main(String args[])  {
        try {
            new ChatServer() ;
        } catch(Exception e)  {
            e.printStackTrace();
        }
    }

}

class ServerRunner implements Runnable  {
    private List<Socket> sockets ;
    private Socket currentSocket ;   //当前socket

    public ServerRunner (List<Socket> sockets,Socket currentSocket)  {
        this.sockets = sockets ;
        this.currentSocket = currentSocket ;
    }

    public void run()  {
        String ip = currentSocket.getInetAddress().getHostAddress() ;
        BufferedReader br = null ;
        try  {
            br = new BufferedReader(new InputStreamReader(currentSocket.getInputStream())) ;
            String str = null ;
            while((str = br.readLine()) != null)  {


                for(Socket temp : sockets)  {
                    if(temp!=currentSocket){
                        PrintWriter pw = new PrintWriter(new OutputStreamWriter(temp.getOutputStream())) ;
                        pw.println(str) ;
                        pw.flush();
                    }

                }
            }
        }catch(IOException e)  {
            e.printStackTrace();
        }
    }
}

/*

class showUI implements  Runnable{

    @Override
    public void run() {
        CheckingUI pui = new CheckingUI();
        pui.initUI();
    }
}*/
