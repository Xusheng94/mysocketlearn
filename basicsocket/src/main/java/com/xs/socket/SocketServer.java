package com.xs.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description SocketServer
 * @Author xusheng
 * @Create 2018-04-19 11:21
 * @Version 1.0
 **/
public class SocketServer {

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.startAction();
    }

    public void startAction() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10086);  //端口号
            System.out.println("服务端服务启动监听：");
            //通过死循环开启长连接，开启线程去处理消息
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new MyRuns(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    class MyRuns implements Runnable {

        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;

        public MyRuns(Socket socket) {
            super();
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//读取客户端消息
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//向客户端写消息
                String lineString = "";

                while (!(lineString = reader.readLine()).equals("bye")) {
                    System.out.println("收到来自客户端的发送的消息是：" + lineString);
                    writer.write("服务器返回：" + lineString + "\n");
                    writer.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

    }
}
