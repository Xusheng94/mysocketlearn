package com.xs.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @Description SocketClient
 * @Author xusheng
 * @Create 2018-04-19 11:23
 * @Version 1.0
 **/
public class SocketClient {
    public static void main(String[] args) {
        SocketClient client=new SocketClient();
        client.startAction();
    }

    void readSocketInfo(BufferedReader reader){
        new Thread(new MyRuns(reader)).start();
    }

    class MyRuns implements Runnable{

        BufferedReader reader;

        public MyRuns(BufferedReader reader) {
            super();
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                String lineString="";
                while( (lineString = reader.readLine())!=null ){
                    System.out.println(lineString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void startAction(){
        Socket socket=null;
        BufferedReader reader=null;
        BufferedWriter writer=null;
        BufferedReader reader2=null;
        try {
            socket=new Socket("127.0.0.1", 10086);
            reader = new BufferedReader(new InputStreamReader(System.in));
            reader2=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            readSocketInfo(reader2);
            String lineString="";
            while(!(lineString=reader.readLine()).equals("exit")){
                writer.write(lineString+"\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader!=null) {
                    reader.close();
                }
                if (writer!=null) {
                    writer.close();
                }
                if (socket!=null) {
                    socket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
