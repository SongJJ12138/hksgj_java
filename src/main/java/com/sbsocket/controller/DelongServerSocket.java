
package com.sbsocket.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbsocket.handle.SocketHandler;
import com.sbsocket.model.Greeting;

import lombok.Data;

@Data
@Component
@PropertySource("classpath:socket.properties")
@RestController
public class DelongServerSocket {
@Value("${socket.port}")
private Integer port;
private boolean started;
private ServerSocket ss;

public static ConcurrentHashMap<String, ClientSocket> clientsMap = new ConcurrentHashMap<>();
private ExecutorService executorService = Executors.newCachedThreadPool();
public static String name1 ="xxx";
public String name;

public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public static void main(String[] args) {

new DelongServerSocket().start();
}
@GetMapping("/send")
public void greetingTo(@RequestParam("name") String name) throws IOException {
name1=name;
this.name=name;
System.out.print("信息1===="+this.name);
System.out.print("信息2========"+name1);
}


public void start() {
start(null);
}

public void start(String name){
ServerSocket serverSocket=null;
try {
    serverSocket=new ServerSocket(8111);  //端口号
    //LOGGER.info("服务端服务启动监听：");
    //通过死循环开启长连接，开启线程去处理消息
    while(true){
        Socket socket=serverSocket.accept();
        new Thread(new MyRuns(socket)).start();
    }
} catch (Exception e) {
    e.printStackTrace();
} finally {
    try {
        if (serverSocket!=null) {
            serverSocket.close();
        }
    } catch (Exception e2) {
        e2.printStackTrace();
    }
}
}

class MyRuns implements Runnable{

Socket socket;
BufferedReader reader;
BufferedWriter writer;

public MyRuns(Socket socket) {
    super();
    this.socket = socket;
}

public void run() {
    try {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//读取客户端消息
        writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//向客户端写消息
        String lineString="";

        while(!(lineString=reader.readLine()).equals("bye")){
            //LOGGER.info("收到来自客户端的发送的消息是：" + lineString);
            System.out.print("gggggggg"+DelongServerSocket.name1);
            writer.write(getName()+"\n");
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

}

