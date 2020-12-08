/*
 * @Descripttion:
 * @version:
 * @Author: WPX
 * @Date: 2020-12-04 16:45:54
 * @LastEditors: WPX
 * @LastEditTime: 2020-12-05 00:25:32
 */
package System;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static FileID file1 = new FileID("src//System//Log.dat");
    static FileID file2 = new FileID("src//System//Users.dat");
    static FileID file3 = new FileID("src//System//Tickets.dat");
    static FileID file4 = new FileID("src//System//Usernum.dat");
    static ArrayList<Socket> sockets = new ArrayList<>();


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3300)) {
            System.out.println("服务器已开启,正等待连接~~~");
            int nums = sockets.size();
            while (true) {
                try {
                    Socket socket = null;
                    socket = serverSocket.accept();
                    if (socket != null) {
                        sockets.add(socket);
                        ResponseServer temp = new ResponseServer(socket);
                        new Thread(temp).start();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("服务器已关闭");
        }
    }
}
