package System;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    static boolean isLog = false;

    public void writeSocket(String s) {

    }

    public static void main(String[] args) {
        Socket client = null;
        BufferedReader rd = null;
        BufferedReader rdsocket = null;
        BufferedWriter wtsocket = null;
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        InputStream in;
        try {

            client = new Socket("localhost", 3300);
            rd = new BufferedReader(new InputStreamReader(System.in));
            rdsocket = new BufferedReader(new InputStreamReader(client.getInputStream()));
            wtsocket = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            mark:
            while (true) {
                try {
                    Functions.show1();
                    System.out.println("请输入指令:");
                    Functions.line = rd.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                String temp = null;
                switch (Functions.line) {
                    case "1":
                        if (!Client.isLog) {
                            Functions.board();
                            Functions.writeSocket(wtsocket, "1");
                            System.out.println("请输入您的用户名:");
                            temp = rd.readLine();
                            Functions.writeSocket(wtsocket, temp);
                            System.out.println("请输入您的密码:");
                            temp = rd.readLine();
                            Functions.writeSocket(wtsocket, temp);
                            if (!rdsocket.readLine().equals("登陆成功!")) {
                                System.out.println("账号或密码错误,请重新进行登录!");
                            } else {
                                Client.isLog = true;
                                System.out.println("登录成功!");
                            }
                        } else {
                            Functions.board();
                            System.out.println("您已经处于登陆状态中,想要切换账号,请先退出当前帐号后重试!");
                        }
                        break;
                    case "2":
                        Functions.writeSocket(wtsocket, "2");
                        Functions.board();
                        System.out.println("请输入您的姓名:");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        System.out.println("请输入您的身份证号:(18位)");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        System.out.println("请输入您的年龄(0~200):");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        System.out.println("请输入您的性别(男|女)");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        System.out.println("请输入您的注册用户名:(长度6位及以上,只允许数字和字母)");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        System.out.println("请输入您的注册密码:(长度6位及以上,只允许数字和字母)");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        if (!rdsocket.readLine().equals("注册成功!")) {
                            System.out.println("注册失败!格式错误或注册账号已存在,请重新注册!");
                        } else {
                            System.out.println("注册成功!");
                        }
                        break;
                    case "3":
                        Functions.writeSocket(wtsocket, "3");
                        Functions.board();
                        System.out.println("请输入您要查询的账号名");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        System.out.println("请输入您的身份证");
                        temp = rd.readLine();
                        Functions.writeSocket(wtsocket, temp);
                        String pp = rdsocket.readLine();
                        if (!pp.equals("找回失败!")) {
                            System.out.println("找回成功!您的密码:" + pp);
                        } else {
                            System.out.println("找回失败!账号不存在或身份证错误!");
                        }
                        break;
                    case "4":
                        if (Client.isLog) {
                            Functions.writeSocket(wtsocket, "4");
                            Functions.board();
                            Tickets.showTicket();
                            System.out.println("确定购票吗?[Y/N]");
                            temp = rd.readLine();
                            while (!temp.equalsIgnoreCase("y") && !temp.equalsIgnoreCase("n")) {
                                System.out.println("格式错误,请重新输入");
                                temp = rd.readLine();
                            }
                            Functions.writeSocket(wtsocket, temp);
                            if (temp.equalsIgnoreCase("y")) {

                                System.out.println("请准确输入要购买列车的车次(如:k111,不得出现中文字符):");
                                temp = rd.readLine();
                                Functions.writeSocket(wtsocket, temp);
                                if (!rdsocket.readLine().equals("购票成功!")) {
                                    System.out.println("车次不存在或输入格式错误,请重试");
                                } else {
                                    System.out.println("购票成功!");
                                }
                            }
                        } else {
                            Functions.board();
                            System.out.println("用户还未登录,请登陆后购票!");


                        }
                        break;
                    case "5":
                        if (Client.isLog) {
                            Functions.writeSocket(wtsocket, "5");
                            Functions.board();
                            System.out.println("你已经购买的车票:");
                            System.out.println(Functions.readSocket(rdsocket));
                            System.out.println("确定退票吗?[Y/N]");
                            temp = rd.readLine();
                            while (!temp.equalsIgnoreCase("y") && !temp.equalsIgnoreCase("n")) {
                                System.out.println("格式错误,请重新输入");
                                temp = rd.readLine();
                            }
                            Functions.writeSocket(wtsocket, temp);
                            if (temp.equalsIgnoreCase("y")) {
                                System.out.println("请准确输入将要退票列车的车次(如:k111次):");
                                temp = rd.readLine();
                                Functions.writeSocket(wtsocket, temp);
                                System.out.println("请准确输入将要退票列车车次的张数(不得大于拥有的张数):");
                                temp = rd.readLine();
                                Functions.writeSocket(wtsocket, temp);
                                if (!rdsocket.readLine().equals("退票成功!")) {
                                    System.out.println("车票不存在或输入格式错误,请重试");
                                } else {
                                    System.out.println("退票成功!");
                                }


                            }
                        } else {
                            Functions.board();
                            System.out.println("用户还未登录,请登陆后退票!");
                        }
                        break;
                    case "6":
                        if (Client.isLog) {
                            Functions.writeSocket(wtsocket, "6");
                            Functions.board();
                            System.out.println("你已经购买的车票:");
                            System.out.println(Functions.readSocket(rdsocket));
                        } else {
                            System.out.println("用户尚未登录,请登录后查询!");
                        }
                        break;
                    case "7":
                        if (Client.isLog) {
                            Functions.writeSocket(wtsocket, "7");
                            Functions.board();
                            System.out.println("请输入你要咨询的问题,本功能尚未完善,请将问题总结后一次发送");
                            String problem = rd.readLine();
                            Functions.writeSocket(wtsocket, problem);
                            System.out.println("正在响应客服的回应,请等待");
                            String solve = rdsocket.readLine();
                            System.out.println("客服:" + solve);
                        } else {
                            System.out.println("用户还未登录,请登陆后咨询!");
                        }
                        break;
                    case "8":
                        Functions.show1();
                        break;
                    case "9":
                        Functions.board();
                        if (Client.isLog) {
                            Functions.writeSocket(wtsocket, "9");
                            Client.isLog = false;
                            System.out.println("登录已退出!");
                        } else {
                            System.out.println("用户还未登录!");
                        }
                        break;
                    case "10":
                        Functions.writeSocket(wtsocket, "10");

                        System.exit(0);
                        break;
                    default:
                        System.out.println("命令格式错误,请重新输入");
                        break;
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
                rdsocket.close();
                wtsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
