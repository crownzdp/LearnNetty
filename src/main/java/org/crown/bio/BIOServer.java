package org.crown.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    ExecutorService executorService = Executors.newCachedThreadPool();

    public void start() {
        try {
            final ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("server start");
            while (true) {
                // 阻塞点
                final Socket socket = serverSocket.accept();
                System.out.println("a client access");
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        byte[] bytes = new byte[1024];
                        try {
                            while (true) {
                                // 阻塞点
                                int read = socket.getInputStream().read(bytes);
                                if (read != -1) {
                                    System.out.println(new String(bytes, 0, read));
                                } else {
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
