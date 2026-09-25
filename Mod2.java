import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class Mod2BookCatalog {
    static class Book {
        String id,title,author,genre,publisher,year,status;
        Book(String... v){id=v[0];title=v[1];author=v[2];genre=v[3];publisher=v[4];year=v[5];status=v[6];}
    }
    static final java.util.List<Book> books=new ArrayList<>();
    static DefaultTableModel model; static JTable table; static JTextField search; static JComboBox<String> filter;

    public static void main(String[] args){
        if(books.isEmpty()){
            books.add(new Book("B001","Python Programming","John Smith","Programming","Tech Publications","2022","Available"));
            books.add(new Book("B002","Database Systems","Elmasri","Database","Pearson","2021","Available"));
            books.add(new Book("B003","Operating Systems","Silberschatz","Computer Science","McGraw Hill","2020","Issued"));
            books.add(new Book("B004","Computer Networks","Forouzan","Networking","McGraw Hill","2019","Available"));
            books.add(new Book("B005","Artificial Intelligence","Russell","AI","Pearson","2023","Available"));
            books.add(new Book("B006","Software Engineering","Pressman","Software","McGraw Hill","2022","Available"));
        }
        SwingUtilities.invokeLater(Mod2BookCatalog::window);
    }
    static void window(){
        JFrame f=new JFrame("Book Catalog System Interface");f.setSize(1100,600);f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel p=new JPanel(new BorderLayout(10,10));p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JLabel h=new JLabel("Library Book Catalog",SwingConstants.CENTER);h.setFont(new Font("Arial",Font.BOLD,22));p.add(h,BorderLayout.NORTH);

        JPanel top=new JPanel();search=new JTextField(30);filter=new JComboBox<>(new String[]{"Title","Author","Genre"});
        JButton s=new JButton("Search"),reset=new JButton("Reset"),details=new JButton("View Details");
        top.add(search);top.add(filter);top.add(s);top.add(reset);top.add(details);p.add(top,BorderLayout.CENTER);

        String[] cols={"Book ID","Title","Author","Genre","Publisher","Year","Status"};
        model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(model);p.remove(top);
        JPanel center=new JPanel(new BorderLayout());center.add(top,BorderLayout.NORTH);center.add(new JScrollPane(table),BorderLayout.CENTER);p.add(center,BorderLayout.CENTER);

        s.addActionListener(e->searchBooks());reset.addActionListener(e->reset());details.addActionListener(e->details());
        load(books);f.add(p);f.setVisible(true);
    }
    static void load(java.util.List<Book> list){
        model.setRowCount(0);for(Book b:list)model.addRow(new Object[]{b.id,b.title,b.author,b.genre,b.publisher,b.year,b.status});
    }
    static void searchBooks(){
        String k=search.getText().toLowerCase(),o=(String)filter.getSelectedItem();java.util.List<Book> r=new ArrayList<>();
        for(Book b:books){String v=o.equals("Title")?b.title:o.equals("Author")?b.author:b.genre;if(v.toLowerCase().contains(k))r.add(b);}load(r);
    }
    static void reset(){search.setText("");filter.setSelectedIndex(0);load(books);}
    static void details(){
        int row=table.getSelectedRow();if(row<0){JOptionPane.showMessageDialog(table,"Please select a book.","Warning",JOptionPane.WARNING_MESSAGE);return;}
        String id=model.getValueAt(table.convertRowIndexToModel(row),0).toString();
        for(Book b:books)if(b.id.equals(id)){
            JOptionPane.showMessageDialog(table,"Book ID : "+b.id+"\nTitle : "+b.title+"\nAuthor : "+b.author+"\nGenre : "+b.genre+
                    "\nPublisher : "+b.publisher+"\nYear : "+b.year+"\nStatus : "+b.status,"Book Details",JOptionPane.INFORMATION_MESSAGE);break;
        }
    }
}