import java.time.temporal.ChronoUnit;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Mod3IssueReturn {
    static final DateTimeFormatter F=DateTimeFormatter.ofPattern("dd-MM-yyyy");
    static class Record{String member,book,issue,due,returned="-",status="Issued";int fine=0;
        Record(String m,String b,String i,String d){member=m;book=b;issue=i;due=d;}}
    static final String[] MEMBERS={"Rahul","Priya","Arun","Sneha","Karthik","Anitha"};
    static final String[] BOOKS={"Python Programming","Database Systems","Operating Systems","Computer Networks","Artificial Intelligence","Software Engineering","Machine Learning"};
    static final java.util.List<Record> records=new ArrayList<>();
    static JComboBox<String> member,book;static JTextField issue,due,returned,search;static DefaultTableModel model;static JLabel stats;

    public static void main(String[] a){SwingUtilities.invokeLater(Mod3IssueReturn::window);}
    static void window(){
        JFrame f=new JFrame("Book Issue & Return");f.setSize(1200,700);f.setLocationRelativeTo(null);f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel root=new JPanel(new BorderLayout(10,10));root.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
        JLabel h=new JLabel("Book Issue & Return",SwingConstants.CENTER);h.setFont(new Font("Arial",Font.BOLD,22));root.add(h,BorderLayout.NORTH);

        JPanel left=new JPanel();left.setBorder(BorderFactory.createTitledBorder("Issue / Return Book"));left.setLayout(new GridLayout(0,1,5,5));
        member=new JComboBox<>(MEMBERS);book=new JComboBox<>(BOOKS);issue=new JTextField();due=new JTextField();returned=new JTextField();
        LocalDate now=LocalDate.now();issue.setText(now.format(F));due.setText(now.plusDays(14).format(F));
        left.add(new JLabel("Member Name"));left.add(member);left.add(new JLabel("Book Name"));left.add(book);
        left.add(new JLabel("Issue Date"));left.add(issue);left.add(new JLabel("Due Date"));left.add(due);left.add(new JLabel("Return Date"));left.add(returned);
        JButton ib=new JButton("Issue Book"),rb=new JButton("Return Book"),cb=new JButton("Clear");left.add(ib);left.add(rb);left.add(cb);

        JPanel right=new JPanel(new BorderLayout(5,5));JPanel top=new JPanel();search=new JTextField(25);JButton sb=new JButton("Search"),reset=new JButton("Reset");
        top.add(new JLabel("Search :"));top.add(search);top.add(sb);top.add(reset);right.add(top,BorderLayout.NORTH);
        String[] cols={"Member","Book","Issue Date","Due Date","Return Date","Fine","Status"};
        model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
        right.add(new JScrollPane(new JTable(model)),BorderLayout.CENTER);stats=new JLabel("",SwingConstants.CENTER);right.add(stats,BorderLayout.SOUTH);
        root.add(left,BorderLayout.WEST);root.add(right,BorderLayout.CENTER);f.add(root);

        ib.addActionListener(e->issueBook());rb.addActionListener(e->returnBook());cb.addActionListener(e->clear());
        sb.addActionListener(e->searchBook());reset.addActionListener(e->{search.setText("");load();});load();f.setVisible(true);
    }
    static void load(){model.setRowCount(0);for(Record r:records)model.addRow(new Object[]{r.member,r.book,r.issue,r.due,r.returned,"₹"+r.fine,r.status});updateStats();}
    static void updateStats(){int i=0,r=0,f=0;for(Record x:records){if(x.status.equals("Issued"))i++;else r++;f+=x.fine;}stats.setText("Total Books : "+records.size()+"     Issued : "+i+"     Returned : "+r+"     Fine Collected : ₹"+f);}
    static LocalDate date(String s){return LocalDate.parse(s,F);}
    static void issueBook(){
        String m=member.getSelectedItem().toString(),b=book.getSelectedItem().toString();
        try{date(issue.getText().trim());date(due.getText().trim());}catch(Exception e){error("Invalid date format.\nUse DD-MM-YYYY.");return;}
        for(Record x:records)if(x.book.equals(b)&&x.status.equals("Issued")){warn("This book is already issued.");return;}
        records.add(new Record(m,b,issue.getText().trim(),due.getText().trim()));load();clear();JOptionPane.showMessageDialog(null,"Book Issued Successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
    }
    static void returnBook(){
        String m=member.getSelectedItem().toString(),b=book.getSelectedItem().toString(),rd=returned.getText().trim();
        if(rd.isEmpty()){error("Please enter Return Date.");return;}LocalDate r;
        try{r=date(rd);}catch(Exception e){error("Return Date must be in DD-MM-YYYY format.");return;}
        for(Record x:records)if(x.member.equals(m)&&x.book.equals(b)&&x.status.equals("Issued")){
            int late=(int)Math.max(0,ChronoUnit.DAYS.between(date(x.due),r));x.fine=late*5;x.returned=rd;x.status="Returned";load();
            JOptionPane.showMessageDialog(null,"Book Returned Successfully!\n\nLate Days : "+late+"\nFine : ₹"+x.fine,"Success",JOptionPane.INFORMATION_MESSAGE);clear();return;
        }warn("No issued record found.");
    }
    static void searchBook(){String k=search.getText().toLowerCase();model.setRowCount(0);for(Record x:records)if(x.member.toLowerCase().contains(k)||x.book.toLowerCase().contains(k)||x.status.toLowerCase().contains(k))model.addRow(new Object[]{x.member,x.book,x.issue,x.due,x.returned,"₹"+x.fine,x.status});}
    static void clear(){member.setSelectedIndex(0);book.setSelectedIndex(0);issue.setText(LocalDate.now().format(F));due.setText(LocalDate.now().plusDays(14).format(F));returned.setText("");}
    static void error(String s){JOptionPane.showMessageDialog(null,s,"Error",JOptionPane.ERROR_MESSAGE);}
    static void warn(String s){JOptionPane.showMessageDialog(null,s,"Warning",JOptionPane.WARNING_MESSAGE);}
}