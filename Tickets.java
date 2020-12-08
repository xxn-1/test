package System;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

class Ticket implements Serializable, Comparable {
    private String Train;
    private String From = null;
    private String To = null;
    private Date Fromdate;
    private Date Todate;
    private static final long serialVersionUID = 1L;

    public String getTrain() {
        return Train;
    }

    public void setTrain(String train) {
        Train = train;
    }

    public Ticket(String train, String from, String to, String Fromtime, String Totime) {
        Train = train;
        From = from;
        To = to;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        Date newDate1 = null;
        try {
            newDate = dateFormat.parse(Fromtime);
            newDate1 = dateFormat1.parse(Totime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.Fromdate = newDate;
        this.Todate = newDate1;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public Date getFromdate() {
        return Fromdate;
    }

    public void setFromdate(Date fromdate) {
        Fromdate = fromdate;
    }

    public Date getTodate() {
        return Todate;
    }

    public void setTodate(Date todate) {
        Todate = todate;
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Train + "次列车:出发地:" + From + "-->目的地:" + To + "(" + df.format(Fromdate) + "-->" + df1.format(Todate) + ")";
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (this == o) {
            return 0;
        }
        Ticket t = null;
        if (o instanceof Ticket) {
            t = (Ticket) o;
        }
        if (!this.getFrom().equals(t.getFrom())) {
            return this.From.compareTo(t.getFrom());
        } else {
            return this.To.compareTo(t.getTo());
        }

    }
}

public class Tickets {
    static TreeMap<Ticket, Integer> tickets = new TreeMap<>();

    static public void AddTickets(Ticket ticket, int number) {
        tickets.put(ticket, number);
    }


    static Ticket ticket1 = new Ticket("K112", "北京", "上海", "2020-12-08 14:12:00", "2020-12-09 15:24:00");
    static Ticket ticket2 = new Ticket("M234", "杭州", "天津", "2020-12-10 15:13:00", "2020-12-11 18:34:00");
    static Ticket ticket3 = new Ticket("K608", "海南", "重启", "2020-12-10 10:24:00", "2020-12-12 18:04:00");
    static Ticket ticket4 = new Ticket("G234", "四川", "武汉", "2020-12-06 15:42:00", "2020-12-07 18:47:00");
    static Ticket ticket5 = new Ticket("F3943", "上海", "云南", "2020-12-07 15:58:00", "2020-12-08 20:02:00");

    static {Tickets.tickets = (TreeMap<Ticket, Integer>) Functions.Readobject(Server.file3);
    }

    static public void supplementTicket(Ticket t, int num) {
        Tickets.tickets.put(t, Tickets.tickets.get(t) + num);
        Functions.Writeobject(Server.file3, Tickets.tickets);
    }

    static public void put(Ticket te, Integer i) {
        Tickets.tickets.put(te, i);
    }

    static public Integer get(Ticket te) {
        return Tickets.tickets.get(te);
    }

    static public TreeMap<Ticket, Integer> gettickets() {
        tickets = (TreeMap<Ticket, Integer>) Functions.Readobject(Server.file3);
        return tickets;
    }

    static public void showTicket() {
        Iterator<Ticket> iterator = tickets.keySet().iterator();
        Functions.board();
        while (iterator.hasNext()) {
            Ticket ticket = iterator.next();
            int number = tickets.get(ticket);
            System.out.println(ticket.toString() + "  余" + number + "张");
        }
        Functions.board();
    }

    public static void main(String[] args) {
//        tickets.put(Tickets.ticket1,200);
//        tickets.put(Tickets.ticket2,200);
//        tickets.put(Tickets.ticket3,200);
//        tickets.put(Tickets.ticket4,200);
//        tickets.put(Tickets.ticket5,200);
//        Functions.Writeobject(Server.file3, Tickets.tickets);
        supplementTicket(Tickets.ticket3,20);
        showTicket();
    }
}
