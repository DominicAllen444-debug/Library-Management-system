import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Mod1Login {
    static final Map<String,String[]> USERS = new HashMap<>();
    static JFrame loginFrame;
    static JTextField username;
    static JPasswordField password;
    static JComboBox<String> role;

    public static void main(String[] args) {
        USERS.put("admin", new String[]{"admin123","Admin"});
        USERS.put("librarian", new String[]{"lib123","Librarian"});
        USERS.put("member", new String[]{"member123","Member"});
        SwingUtilities.invokeLater(Mod1Login::showLogin);
    }

    static void showLogin() {
        loginFrame = new JFrame("Library Login");
        loginFrame.setSize(500,550);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20,35,20,35));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8,8,8,8);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Library Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setForeground(new Color(31,78,121));
        g.gridx=0;g.gridy=0;g.gridwidth=2;p.add(title,g);

        username=new JTextField(25);
        password=new JPasswordField(25);
        role=new JComboBox<>(new String[]{"Admin","Librarian","Member"});

        g.gridwidth=1;g.gridy++;
        p.add(new JLabel("Username"),g); g.gridx=1;p.add(username,g);
        g.gridx=0;g.gridy++;p.add(new JLabel("Password"),g);g.gridx=1;p.add(password,g);
        g.gridx=0;g.gridy++;p.add(new JLabel("Role"),g);g.gridx=1;p.add(role,g);

        JButton login=new JButton("Login");
        login.addActionListener(e->doLogin());
        g.gridx=0;g.gridy++;g.gridwidth=2;p.add(login,g);

        JTextArea demo=new JTextArea("Demo Credentials\n\nAdmin      : admin / admin123\nLibrarian  : librarian / lib123\nMember     : member / member123");
        demo.setEditable(false);
        g.gridy++;p.add(demo,g);

        loginFrame.add(p);
        loginFrame.setVisible(true);
    }

    static void doLogin() {
        String u=username.getText().trim().toLowerCase();
        String pw=new String(password.getPassword()).trim();
        String r=(String)role.getSelectedItem();

        if(u.isEmpty()||pw.isEmpty()){error("All fields are required.");return;}
        if(!USERS.containsKey(u)){error("Invalid Username");return;}
        if(!USERS.get(u)[0].equals(pw)){error("Incorrect Password");return;}
        if(!USERS.get(u)[1].equals(r)){error("Selected Role is Incorrect");return;}

        JOptionPane.showMessageDialog(loginFrame,"Welcome "+r,"Success",JOptionPane.INFORMATION_MESSAGE);
        loginFrame.setVisible(false);
        dashboard(r);
    }

    static void dashboard(String r) {
        JFrame f=new JFrame("Library Management System");
        f.setSize(700,450); f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
        JLabel h=new JLabel("Library Management System");
        h.setFont(new Font("Arial",Font.BOLD,22)); h.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(h);
        JLabel logged=new JLabel("Logged in as : "+r);
        logged.setAlignmentX(Component.CENTER_ALIGNMENT); p.add(logged);
        p.add(Box.createVerticalStrut(20));

        String[][] mods={{"Book Catalog","Mod2BookCatalog"},{"Book Issue & Return","Mod3IssueReturn"},
                         {"Member Management","Mod4MemberManagement"},{"Fine Calculation","M5FineCalculation"},
                         {"Dashboard / Reports","M6Dashboard"}};
        for(String[] m:mods){
            JButton b=new JButton(m[0]); b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(280,38));
            b.addActionListener(e->launch(m[1]));
            p.add(b);p.add(Box.createVerticalStrut(7));
        }
        JButton logout=new JButton("Logout");
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.addActionListener(e->{f.dispose();loginFrame.setVisible(true);});
        p.add(logout);
        f.add(p);f.setVisible(true);
    }

    static void launch(String name){
        try{Class.forName(name).getMethod("main",String[].class).invoke(null,(Object)new String[]{});}
        catch(Exception e){error("Could not open "+name+".java\n"+e.getMessage());}
    }
    static void error(String s){JOptionPane.showMessageDialog(loginFrame,s,"Error",JOptionPane.ERROR_MESSAGE);}
}