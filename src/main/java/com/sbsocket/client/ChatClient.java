package com.sbsocket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.UUID;


public class ChatClient {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8111;
        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);
        // 建立连接后获得输出流
        DataOutputStream outputStream = new DataOutputStream( socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream( socket.getInputStream());
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        outputStream.write(uuid.getBytes());
        //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
 
        InputStream inputStreams = new FileInputStream("d:/Recovery.txt");
        byte[] bytes = new byte[inputStreams.available()];
        inputStreams.read(bytes);
        DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
       while (true){
           byte[] bytess = new byte[1024];
            inputStream1.read(bytess);
            String info = new String(bytess, "utf-8");
           System.out.println("xxxxxxxxxxx"+info);
       }
    }

}