package System;

import java.io.*;
import java.net.Socket;
import java.util.regex.Pattern;

public class ResponseServer implements Runnable {
    private Socket socket = null;

    public ResponseServer(Socket socket) {
        this.socket = socket;
    }
    private User user = null;

    @Override
    public void run() {
        BufferedReader rd = null;
        BufferedReader rdsocket = null;
        BufferedWriter wtsocket = null;
        FileWriter fileWriter = null;
        try {
            rd = new BufferedReader(new InputStreamReader(System.in));
            rdsocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            wtsocket = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            fileWriter=new FileWriter(Server.file1,true);
            System.out.println(socket + "客户端已连接!");
            Functions.writeFile(fileWriter,socket + "客户端已连接!");
            String accept = null;
            while (true) {
                if (rdsocket.ready()) {
                    accept = rdsocket.readLine();
                    if (accept != null) {
                        switch (accept) {
                            case "1":
                                String account = rdsocket.readLine();
                                String password = rdsocket.readLine();
                                user = Functions.getUser(account, password);
                                if (user == null) {
                                    System.out.println("服务器查询不到" + account + "账号!");
                                    Functions.writeSocket(wtsocket, "登陆失败!");
                                    Functions.writeFile(fileWriter,socket+ "客户端登陆失败!");
                                } else {
                                    System.out.println(socket + "客户端已经登录成功!");
                                    Functions.writeFile(fileWriter,socket + "客户端登陆成功!");
                                    Functions.writeSocket(wtsocket, "登陆成功!");
                                }
                                accept = null;
                                break;
                            case "2":
                                String a = "([a-zA-Z0-9]{6,})";
                                String b = "([0-9xX]{18})";
                                String c = "([1]?[0-9]{1,2})";
                                String d = "([男女])";
                                Pattern pattern2 = Pattern.compile(a);
                                Pattern pattern3 = Pattern.compile(b);
                                Pattern pattern4 = Pattern.compile(c);
                                Pattern pattern5 = Pattern.compile(d);
                                String Name = rdsocket.readLine();
                                String IDCard = rdsocket.readLine();
                                String Age = rdsocket.readLine();
                                String Sex = rdsocket.readLine();
                                account = rdsocket.readLine();
                                password = rdsocket.readLine();
                                if (pattern2.matcher(account).matches() == true && pattern2.matcher(password).matches() == true && Functions.checkAccount(account) && pattern3.matcher(IDCard).matches() == true && pattern4.matcher(Age).matches() && pattern5.matcher(Sex).matches()) {
                                    Functions.writeSocket(wtsocket, "注册成功!");
                                    User.setID(User.getID() + 1);
                                    Functions.Writeobject(Server.file4, User.getID());
                                    User bar = new User(Name, IDCard, Sex, Age, account, password);
                                    System.out.println("新用户" + bar.getNum() + "号注册成功!");
                                    Functions.writeFile(fileWriter,"新用户" + bar.getNum() + "号注册成功!");
                                    Users.addUser(bar);
                                    Functions.Writeobject(Server.file2, Users.users);
                                } else {
                                    Functions.writeSocket(wtsocket, "注册失败!");
                                    Functions.writeFile(fileWriter,socket+"客户端企图注册已存在的账号!");
                                    System.out.println(socket+"客户端企图注册已存在的账号!");
                                }
                                accept = null;
                                break;
                            case "3":
                                Name = rdsocket.readLine();

                                IDCard = rdsocket.readLine();
                                String tt = null;
                                User tempUser=null;
                                for (int i = 0; i < Users.size(); i++) {
                                    if (Users.get(i).getIDCard().equals(IDCard) && Users.get(i).getAccount().equals(Name)) {

                                        tt = Users.get(i).getPassword();
                                        tempUser=Users.get(i);
                                    }
                                }
                                if (tt == null) {
                                    Functions.writeFile(fileWriter,"用户"+tempUser.getNum()+"号找回密码失败!");
                                    System.out.println("用户"+tempUser.getNum()+"号找回密码失败!");
                                    Functions.writeSocket(wtsocket, "找回失败!");
                                } else {
                                    Functions.writeFile(fileWriter,"用户"+tempUser.getNum()+"号找回密码成功!");
                                    System.out.println("用户"+tempUser.getNum()+"号找回密码成功!");
                                    Functions.writeSocket(wtsocket, tt);
                                }
                                accept = null;
                                break;
                            case "4":
                                String will = rdsocket.readLine();
                                if (will.equalsIgnoreCase("y")) {
                                    String Train = rdsocket.readLine();
                                    if (this.user.buyTicket(Train) == false) {
                                        Functions.writeSocket(wtsocket, "购票失败!");
                                        System.out.println("用户" + this.user.getNum() + "号购买车次" + Train+"失败!");
                                        Functions.writeFile(fileWriter,"用户" + this.user.getNum() + "号购买车次" + Train+"失败!");

                                    } else {
                                        Functions.writeSocket(wtsocket, "购票成功!");
                                        Functions.writeFile(fileWriter,"用户" + this.user.getNum() + "号购买车票成功!");

                                        System.out.println("用户" + this.user.getNum() + "号购买车票成功!");
                                    }
                                }
                                accept = null;
                                break;
                            case "5":
                                String Train;
                                Functions.writeSocket(wtsocket, Functions.showTicket(this.user));
                                will = rdsocket.readLine();
                                Train = rdsocket.readLine();
                                if (will.equalsIgnoreCase("y")) {
                                    int number = Integer.parseInt(rdsocket.readLine());
                                    System.out.println(111);
                                    if (this.user.refundTicket(Train, number)) {
                                        System.out.println("用户" + this.user.getNum() + "号退订车次" + Train + "次--" + number + "张!");
                                        Functions.writeFile(fileWriter,"用户" + this.user.getNum() + "号退订车次" + Train + "次--" + number + "张!");

                                        Functions.writeSocket(wtsocket, "退票成功!");
                                    } else {
                                        Functions.writeSocket(wtsocket, "退票失败!");
                                        Functions.writeFile(fileWriter,"用户" + this.user.getNum() + "号退订车票失败!");

                                        System.out.println("用户" + this.user.getNum() + "号退订车票失败!");
                                    }
                                }
                                accept = null;
                                break;
                            case "6":
                                Functions.writeSocket(wtsocket, Functions.showTicket(this.user));
                                accept = null;
                                break;
                            case "7":
                                synchronized (this) {
                                    String problem = rdsocket.readLine();
                                    System.out.println(socket + "客户端"+problem+"正在等待客服的响应~~~");
                                    System.out.println("请响应客户端:("+socket+")");
                                    String solve = rd.readLine();
                                    Functions.writeFile(fileWriter,socket+"客户端:"+problem+",请求服务器端响应中~~~");
                                    Functions.writeSocket(wtsocket, solve);
                                    accept = null;
                                    break;
                                }
                            case "8":
                                accept = null;
                                break;
                            case "9":
                                System.out.println("客户" + this.user.getNum() + "号已经下线!");
                                Functions.writeFile(fileWriter,"客户" + this.user.getNum() + "号已经下线!");

                                this.user = null;
                                break;
                            case "10":
                                System.out.println(socket + "客户端已断开连接!");
                                Functions.writeFile(fileWriter,socket + "客户端已断开连接!");


                                Thread.currentThread().interrupt();
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                rd.close();
                fileWriter.close();
                rdsocket.close();
                wtsocket.close();
                socket.close();
                if (!Thread.currentThread().isInterrupted()){
                    System.out.println(socket + "客户端已断开连接!");
                    Functions.writeFile(fileWriter,socket + "客户端已断开连接!");
                    Thread.currentThread().interrupt();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
