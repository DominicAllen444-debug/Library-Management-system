import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class M6Dashboard {
    static final int totalBooks=250,availableBooks=180,issuedBooks=70,totalMembers=120,overdueBooks=8,fineCollected=2450;
    public static void main(String[] a){SwingUtilities.invokeLater(M6Dashboard::window);}
    static void window(){
        JFrame f=new JFrame("Library Dashboard");f.setSize(1200,700);f.setLocationRelativeTo(null);f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel root=new JPanel(new BorderLayout(10,10));root.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JLabel title=new JLabel("Library Management Dashboard",SwingConstants.CENTER);title.setFont(new Font("Arial",Font.BOLD,22));title.setForeground(new Color(31,78,121));root.add(title,BorderLayout.NORTH);
        JPanel cards=new JPanel(new GridLayout(1,6,10,10));String[][] data={{"Total Books","250"},{"Available","180"},{"Issued","70"},{"Members","120"},{"Overdue","8"},{"Fine (₹)","2450"}};
        for(String[] d:data){JPanel c=new JPanel(new GridLayout(2,1));c.setBorder(BorderFactory.createLineBorder(Color.GRAY));JLabel n=new JLabel(d[0],SwingConstants.CENTER),v=new JLabel(d[1],SwingConstants.CENTER);n.setFont(new Font("Arial",Font.BOLD,12));v.setFont(new Font("Arial",Font.BOLD,18));c.add(n);c.add(v);cards.add(c);}root.add(cards,BorderLayout.CENTER);
        JPanel bottom=new JPanel(new BorderLayout());
        String[] cols={"Time","Activity"};DefaultTableModel m=new DefaultTableModel(cols,0);String[][] acts={{"09:00","Book Issued"},{"09:30","New Member Added"},{"10:00","Book Returned"},{"10:45","Fine Calculated"},{"11:30","Book Issued"},{"12:00","Book Returned"},{"12:45","Member Updated"},{"01:15","Book Added"},{"02:00","Fine Paid"},{"03:10","Book Reserved"}};
        for(String[] x:acts)m.addRow(x);bottom.add(new JScrollPane(new JTable(m)),BorderLayout.CENTER);
        JPanel buttons=new JPanel();JButton refresh=new JButton("Refresh"),logout=new JButton("Logout");buttons.add(refresh);buttons.add(logout);refresh.addActionListener(e->JOptionPane.showMessageDialog(f,"Dashboard Updated."));logout.addActionListener(e->f.dispose());bottom.add(buttons,BorderLayout.SOUTH);
        root.add(bottom,BorderLayout.SOUTH);f.add(root);f.setVisible(true);
    }
}