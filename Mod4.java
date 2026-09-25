import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class Mod4MemberManagement {
    static class Member{String id,name,dept,phone,email,membership,status;Member(String...v){id=v[0];name=v[1];dept=v[2];phone=v[3];email=v[4];membership=v[5];status=v[6];}}
    static final java.util.List<Member> members=new ArrayList<>();
    static JTextField id,name,dept,phone,email,search;static JComboBox<String> membership,status;static DefaultTableModel model;static JTable table;

    public static void main(String[] a){
        if(members.isEmpty()){members.add(new Member("M001","Rahul Sharma","CSE","9876543210","rahul@gmail.com","Student","Active"));
            members.add(new Member("M002","Priya Kumar","IT","9876501234","priya@gmail.com","Faculty","Active"));
            members.add(new Member("M003","Arun","ECE","9988776655","arun@gmail.com","Student","Inactive"));}
        SwingUtilities.invokeLater(Mod4MemberManagement::window);
    }
    static void window(){
        JFrame f=new JFrame("Member Management");f.setSize(1200,650);f.setLocationRelativeTo(null);f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel root=new JPanel(new BorderLayout(10,10));root.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JLabel h=new JLabel("Library Member Management",SwingConstants.CENTER);h.setFont(new Font("Arial",Font.BOLD,22));root.add(h,BorderLayout.NORTH);
        JPanel form=new JPanel(new GridLayout(0,2,7,7));form.setBorder(BorderFactory.createTitledBorder("Member Details"));
        id=new JTextField();name=new JTextField();dept=new JTextField();phone=new JTextField();email=new JTextField();membership=new JComboBox<>(new String[]{"Student","Faculty","Staff"});status=new JComboBox<>(new String[]{"Active","Inactive"});
        form.add(new JLabel("Member ID"));form.add(id);form.add(new JLabel("Name"));form.add(name);form.add(new JLabel("Department"));form.add(dept);form.add(new JLabel("Phone"));form.add(phone);form.add(new JLabel("Email"));form.add(email);form.add(new JLabel("Membership"));form.add(membership);form.add(new JLabel("Status"));form.add(status);
        JButton add=new JButton("Add"),update=new JButton("Update"),del=new JButton("Delete"),clear=new JButton("Clear");form.add(add);form.add(update);form.add(del);form.add(clear);
        JPanel right=new JPanel(new BorderLayout(5,5));JPanel top=new JPanel();search=new JTextField(25);JButton sb=new JButton("Search");top.add(new JLabel("Search Member"));top.add(search);top.add(sb);right.add(top,BorderLayout.NORTH);
        String[] cols={"ID","Name","Department","Phone","Email","Membership","Status"};model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};table=new JTable(model);right.add(new JScrollPane(table),BorderLayout.CENTER);
        root.add(form,BorderLayout.WEST);root.add(right,BorderLayout.CENTER);f.add(root);
        add.addActionListener(e->addMember());update.addActionListener(e->updateMember());del.addActionListener(e->deleteMember());clear.addActionListener(e->clear());sb.addActionListener(e->searchMember());
        table.getSelectionModel().addListSelectionListener(e->select());load(members);f.setVisible(true);
    }
    static void load(java.util.List<Member> list){model.setRowCount(0);for(Member m:list)model.addRow(new Object[]{m.id,m.name,m.dept,m.phone,m.email,m.membership,m.status});}
    static void clear(){id.setText("");name.setText("");dept.setText("");phone.setText("");email.setText("");membership.setSelectedIndex(0);status.setSelectedIndex(0);table.clearSelection();}
    static void addMember(){if(empty()){error("All fields are required");return;}for(Member m:members)if(m.id.equals(id.getText().trim())){error("Member ID already exists");return;}members.add(current());load(members);clear();info("Member Added");}
    static void select(){int r=table.getSelectedRow();if(r<0)return;id.setText(model.getValueAt(r,0).toString());name.setText(model.getValueAt(r,1).toString());dept.setText(model.getValueAt(r,2).toString());phone.setText(model.getValueAt(r,3).toString());email.setText(model.getValueAt(r,4).toString());membership.setSelectedItem(model.getValueAt(r,5));status.setSelectedItem(model.getValueAt(r,6));}
    static void updateMember(){int r=table.getSelectedRow();if(r<0){warn("Select a member");return;}members.set(r,current());load(members);clear();info("Member Updated");}
    static void deleteMember(){int r=table.getSelectedRow();if(r<0){warn("Select member");return;}members.remove(r);load(members);clear();info("Member Deleted");}
    static void searchMember(){String k=search.getText().toLowerCase();java.util.List<Member> r=new ArrayList<>();for(Member m:members)if(m.id.toLowerCase().contains(k)||m.name.toLowerCase().contains(k)||m.dept.toLowerCase().contains(k))r.add(m);load(r);}
    static Member current(){return new Member(id.getText().trim(),name.getText().trim(),dept.getText().trim(),phone.getText().trim(),email.getText().trim(),membership.getSelectedItem().toString(),status.getSelectedItem().toString());}
    static boolean empty(){return id.getText().trim().isEmpty()||name.getText().trim().isEmpty()||dept.getText().trim().isEmpty()||phone.getText().trim().isEmpty()||email.getText().trim().isEmpty();}
    static void error(String s){JOptionPane.showMessageDialog(null,s,"Error",JOptionPane.ERROR_MESSAGE);}static void warn(String s){JOptionPane.showMessageDialog(null,s,"Warning",JOptionPane.WARNING_MESSAGE);}static void info(String s){JOptionPane.showMessageDialog(null,s,"Success",JOptionPane.INFORMATION_MESSAGE);}
}