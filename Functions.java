package System;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Functions {


    public static void clsCmd() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;//清屏

    public static String showTicket(User user) {
        int ticketnum = 0;
        Iterator<Ticket> iterator = user.getTicket().keySet().iterator();
        String output = "";
        Ticket ticket;
        while (iterator.hasNext()) {
            ticket = iterator.next();
            output += ticket.toString() + " " + user.getTicket().get(ticket) + "张" + "\n";
            int number = user.getTicket().get(ticket);
            ticketnum += number;
        }
        return "已购买车票:" + ticketnum + "张\n" + output;
    }

    synchronized public static void Writeobject(FileID fileID, Object object) {
        //       if (!fileID.isFile()) {
        //          fileID.setFile(true);
//            try {
//                FileWriter fileWriter = new FileWriter(fileID);
//                fileWriter.write("");
//                fileWriter.flush();
//                fileWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileID);
             ObjectOutStream objectOutStream = new ObjectOutStream(fileOutputStream)) {
            objectOutStream.writeObject(object);
            objectOutStream.writeObject(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //       Server.file2.setFile(false);
        //     }
    }


    synchronized public static Object Readobject(FileID fileID) {
        if (!fileID.isFile()) {
            fileID.setFile(true);
            try (FileInputStream fileInputStream = new FileInputStream(fileID);
                 ObjectInStream objectInStream = new ObjectInStream(fileInputStream)) {
                Object tempObj = objectInStream.readObject();
                if (null == objectInStream.readObject()) {
                    return tempObj;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Server.file2.setFile(false);
        }
        return null;
    }

    synchronized public static User getUser(String account, String password) {
        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getAccount().equals(account) && Users.get(i).getPassword().equals(password)) {
                return Users.get(i);
            }
        }
        return null;

    }

    public static String curTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Functions ttt =new Functions();
        Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }
    public static void show1() {
        board();
        System.out.println("|                         欢迎来到电子火车购票系统                         |");
        System.out.println("|                          (*请登录后进行购票*)                           |");
        System.out.println("|                              1:登录账号                                |");
        System.out.println("|                              2:注册账号                                |");
        System.out.println("|                              3:找回密码                                |");
        System.out.println("|                              4:购买车票                                |");
        System.out.println("|                              5:退订车票                                |");
        System.out.println("|                              6:现有车票                                |");
        System.out.println("|                              7:咨询客服                                |");
        System.out.println("|                              8:返回界面                                |");
        System.out.println("|                              9:退出登录                                |");
        System.out.println("|                             10:退出系统                                |");
        System.out.println("|                        请键入命令前的标号进行选择                        |");
        board();
    }
    public static void writeFile(FileWriter writer,String s) throws IOException {
        writer.write(s+"  "+Functions.curTime()+"\n");
        writer.flush();
    }
    static public boolean checkAccount(String account) {
        for (int i = 0; i < Users.users.size(); i++) {
            if (account.equals(Users.get(i).getAccount())) {
                return false;
            }
        }
        return true;
    }

    static public void board() {
        System.out.println("-------------------------------------------------------------------------");

    }

    static public String line = null;

    synchronized static public void writeSocket(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
        bw.flush();
    }

    synchronized static public String readSocket(BufferedReader br) throws IOException {
        String a="";
        String temp=null;
        while ((temp=br.readLine()).length()!=0){
            a=a+temp+"\n";
        }
        return a;
    }


}
