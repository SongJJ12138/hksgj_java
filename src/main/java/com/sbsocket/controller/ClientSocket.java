package com.sbsocket.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


@Data
@Slf4j
public class ClientSocket implements Runnable {
 
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String key;
    private String message;
    
 
    public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(DataInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(DataOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
     * 注册socket到map里
     *
     * @param socket
     * @return
     */
    public static ClientSocket register(Socket socket) {
        ClientSocket client = new ClientSocket();
        try {
            client.setSocket(socket);
            client.setInputStream(new DataInputStream(socket.getInputStream()));
            client.setOutputStream(new DataOutputStream(socket.getOutputStream()));
            return client;
        } catch (IOException e) {
            client.logout();
        }
        return null;
    }
 
    /**
     * 发送数据
     *
     * @param str
     */
    public void send(String str) {
        try {
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            logout();
        }
    }
 
    /**
     * 接收数据
     *
     * @return
     * @throws IOException
     */
    public String receive()  {
        try {
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            String info = new String(bytes, "utf-8");
            System.out.println("cccccccccccccccccccc"+info);
            return info;
        } catch (IOException e) {
            logout();
        }
        return null;
    }
 
    /**
     * 登出操作, 关闭各种流
     */
    public void logout() {
        if (DelongServerSocket.clientsMap.containsKey(key)) {
            DelongServerSocket.clientsMap.remove(key);
        }
 
        System.out.println(DelongServerSocket.clientsMap);
        try {
            socket.shutdownOutput();
            socket.shutdownInput();
        } catch (IOException e) {
           // throw new SocketDisconnectedException("关闭输入输出异常", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
               // throw new SocketDisconnectedException("关闭socket异常", e);
            }
        }
    }
 
    /**
     * 发送数据包, 判断数据连接状态
     *
     * @return
     */
    public boolean isSocketClosed() {
        try {
            socket.sendUrgentData(1);
            return false;
        } catch (IOException e) {
            return true;
        }
    }
 
    @Override
    public void run() {
        // 每过5秒连接一次客户端
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isSocketClosed()) {
                System.out.println("关闭");
                logout();
                break;
            }
        }
 
    }
 
    @Override
    public String toString() {
        return "Client{" +
                "socket=" + socket +
                ", inputStream=" + inputStream +
                ", outputStream=" + outputStream +
                ", key='" + key + '\'' +
                '}';
    }
 
 
}