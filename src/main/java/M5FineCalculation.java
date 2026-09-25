import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class M5FineCalculation {
    static final DateTimeFormatter F=DateTimeFormatter.ofPattern("dd-MM-yyyy");
    static JTextField book,member,due,returned;static JLabel late,fine;
    public static void main(String[] a){SwingUtilities.invokeLater(M5FineCalculation::window);}
    static void window(){
        JFrame f=new JFrame("Fine Calculation 123");f.setSize(700,600);f.setLocationRelativeTo(null);f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel p=new JPanel(new GridBagLayout());p.setBorder(BorderFactory.createTitledBorder("Fine Details"));GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(8,8,8,8);g.fill=GridBagConstraints.HORIZONTAL;
        book=new JTextField(25);member=new JTextField(25);due=new JTextField(25);returned=new JTextField(25);
        String[] labels={"Book ID","Member ID","Due Date (DD-MM-YYYY)","Return Date (DD-MM-YYYY)"};JTextField[] fs={book,member,due,returned};
        for(int i=0;i<4;i++){g.gridx=0;g.gridy=i;p.add(new JLabel(labels[i]),g);g.gridx=1;p.add(fs[i],g);}
        JButton calc=new JButton("Calculate Fine"),clear=new JButton("Clear");JPanel bp=new JPanel();bp.add(calc);bp.add(clear);g.gridx=0;g.gridy=4;g.gridwidth=2;p.add(bp,g);
        late=new JLabel("Late Days : 0");fine=new JLabel("Fine Amount : ₹0");g.gridy=5;p.add(late,g);g.gridy=6;p.add(fine,g);
        calc.addActionListener(e->calculate());clear.addActionListener(e->clear());f.add(p);f.setVisible(true);
    }
    static void calculate(){
        if(book.getText().trim().isEmpty()||member.getText().trim().isEmpty()||due.getText().trim().isEmpty()||returned.getText().trim().isEmpty()){error("Please fill all fields.");return;}
        try{long d=Duration.between(LocalDate.parse(due.getText().trim(),F).atStartOfDay(),LocalDate.parse(returned.getText().trim(),F).atStartOfDay()).toDays();int days=(int)Math.max(0,d);late.setText("Late Days : "+days);fine.setText("Fine Amount : ₹"+(days*10));}
        catch(Exception e){error("Date format should be DD-MM-YYYY.");}
    }
    static void clear(){book.setText("");member.setText("");due.setText("");returned.setText("");late.setText("Late Days : 0");fine.setText("Fine Amount : ₹0");}
    static void error(String s){JOptionPane.showMessageDialog(null,s,"Error",JOptionPane.ERROR_MESSAGE);}
}