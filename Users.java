package System;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

class User implements Serializable {
    volatile private static int ID;
    private static final long serialVersionUID = 1L;

    static {
        ID = (int) Functions.Readobject(Server.file4);
    }


    private int Num;
    private String Name = null;
    private String IDCard = null;
    private String Sex = null;
    private String Age = null;
    private String Account = null;
    private String Password = null;
    private TreeMap<Ticket, Integer> ticket = new TreeMap<>();

    public User(String name, String IDCard, String sex, String age, String account, String password) {
        Num = ID;
        Name = name;
        this.IDCard = IDCard;
        Sex = sex;
        Age = age;
        Account = account;
        Password = password;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        User.ID = ID;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public TreeMap<Ticket, Integer> getTicket() {
        return ticket;
    }

    public void setTicket(TreeMap<Ticket, Integer> ticket) {
        this.ticket = ticket;
    }

    synchronized public boolean buyTicket(String train) {
        Iterator<Ticket> iterator = Tickets.tickets.keySet().iterator();
        Ticket temp=null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            if(temp.getTrain().equalsIgnoreCase(train)){
                break;
            }
        }
        if (temp != null && Tickets.get(temp) != 0) {

            if (this.ticket.containsKey(temp)) {
                int number = this.ticket.get(temp);
                this.ticket.remove(temp);
                this.ticket.put(temp, number + 1);
            } else {
                this.ticket.put(temp, 1);
            }
            User user = null;
            for (int i = 0; i < Users.size(); i++) {
                user = Users.users.get(i);
                if (user.Password.equals(this.Password) && user.Account.equals(this.Account)) {
                    Users.remove(i);
                }
            }
           Tickets.put(temp, Tickets.get(temp) - 1);
            Users.addUser(this);

            Functions.Writeobject(Server.file3, Tickets.tickets);
            Functions.Writeobject(Server.file2, Users.users);
            return true;
        }
        return false;
    }

    public boolean refundTicket(String Train, int num) {
        Iterator<Ticket> iterator = ticket.keySet().iterator();
        while (iterator.hasNext()) {
            Ticket temp = iterator.next();
            String train = temp.getTrain();
            if (train.equalsIgnoreCase(Train)) {
                int number = ticket.get(temp);
                if (num > number) {
                    return false;
                } else {
                    int newNumbers = number - num;
                    if (newNumbers == 0) {
                        ticket.remove(temp);
                    } else {
                        ticket.put(temp, newNumbers);
                    }
                    Iterator<Ticket> iter = Tickets.tickets.keySet().iterator();
                    while (iter.hasNext()) {
                        Ticket temp1 = iter.next();
                        if (temp1.getTrain().equalsIgnoreCase(Train)) {
                            Tickets.put(temp1,Tickets.get(temp1)+num);
                        }
                    }
                    Functions.Writeobject(Server.file3, Tickets.tickets);
                    Functions.Writeobject(Server.file2, Users.users);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "用户ID:" + Num + "  用户姓名:" + Name + "  身份证号:" + IDCard + "  性别:" + Sex + "  年龄:" + Age;
    }

}

public class Users {
    volatile static ArrayList<User> users=new ArrayList<>();

    static {
        Users.users = (ArrayList<User>) Functions.Readobject(Server.file2);
    }

    public static void main(String[] args) {
 //       User temp = new User("jjj", "142724200106200289", "男", "19", "789", "xuqwe");
//          User temp1 = Functions.getUser("213", "xuqwe");
//        temp1.refundTicket("k112次",1);
      //  temp1.buyTicket(Tickets.ticket1);
 //       Functions.Writeobject(Server.file2,Users.users);
        showUsers();
        Tickets.showTicket();


    }

    static public void addUser(User user) {
        users.add(user);
    }

    static public void showUsers() {
        for (int i = 0; i < Users.size(); i++) {
            System.out.println(Users.get(i));
            System.out.println(Functions.showTicket(Users.get(i)));
        }
    }

    static public User get(int i) {
        return users.get(i);
    }

    static public void remove(int i) {
        users.remove(i);
    }

    static public int size() {
        return users.size();
    }
}