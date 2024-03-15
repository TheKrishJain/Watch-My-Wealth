import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account extends JFrame implements ActionListener {
    public static JFrame signupframe, loginframe;
    RoundedButtons loginPage, signupPage;
    RoundedButtons login, signUp;
    JPanel panelL, panelR;
    JPanel panelLL, panelRR;

    JTextField userfield;

    JPasswordField passfield;

    JPasswordField passfieldd, passcheckfieldd;
    JTextField userfieldd;
    JTextField fullNameField;
  

    JLabel statusLabel = new JLabel();

    String url = "jdbc:mysql://localhost:3306/wmw"; // Assuming 'wmw' is the database name
    String userName = "root";
    String password = "tiger";
    Connection connection;
    PreparedStatement preparedStatement;

    Account() {
        try {
            // Dynamically load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Handle class not found exception
            e.printStackTrace();
        }
        try {
            // Establishing the database connection
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        signupframe = new JFrame("Watch my Wealth");
        ImageIcon logo = new ImageIcon(
                "C:\\Users\\krish\\OneDrive\\Desktop\\New folder (2)\\FA\\Finance Analyser\\src\\LogoIcon.png");

         
        signupframe.setIconImage(logo.getImage());
        signupframe.setLayout(new GridBagLayout());
        signupframe.setSize(600, 450);
        signupframe.setResizable(true);
        signupframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginframe = new JFrame("Watch my Wealth");
        loginframe.setIconImage(logo.getImage());
        loginframe.setLayout(new GridBagLayout());
        loginframe.setSize(600, 450);
        loginframe.setResizable(true);
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernamee = new JLabel("Choose a Username");
        JLabel passwordd = new JLabel("Password");
        JLabel passwordCheckk = new JLabel("Re-Enter Password");

        userfieldd = new JTextField(17);
        passfieldd = new JPasswordField(17); // Using JPasswordField for password input
        passcheckfieldd = new JPasswordField(17); // Using JPasswordField for password input
        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameField = new JTextField(17);

        loginPage = new RoundedButtons("Already have an Account?", Color.white, new Color(30, 144, 255)); // Renaming
                                                                                                    

        signupPage = new RoundedButtons("Create Account", new Color(30, 144, 255), Color.WHITE); // Renaming SignUp to
                                                                                                

        signupPage.addActionListener(this);
        loginPage.addActionListener(this);

        panelLL = new JPanel();
        panelLL.setBackground(new Color(29, 59, 137));// Setting background color
        panelLL.setPreferredSize(new Dimension(200, 450)); // Setting preferred size
     

     
    
        ImageIcon fulllogo = new ImageIcon("C:\\Users\\krish\\OneDrive\\Desktop\\FA\\Finance Analyser\\src\\full_logo.png");
        Image scaledFullLogoImage = fulllogo.getImage().getScaledInstance(169, 370, Image.SCALE_SMOOTH);
        JLabel bgimage = new JLabel(new ImageIcon(scaledFullLogoImage));
        
        // Add bgimage to panelLL using GridBagConstraints
        GridBagConstraints gcLL = new GridBagConstraints();
        gcLL.anchor=GridBagConstraints.CENTER;
        gcLL.gridx = 0;
        gcLL.gridy = 0;
        gcLL.weightx = 1;
        gcLL.weighty = 1;
        gcLL.fill = GridBagConstraints.BOTH;
        panelLL.add(bgimage, gcLL);


        panelRR = new JPanel(new GridBagLayout());
        GridBagConstraints gcR = new GridBagConstraints();
        JLabel signUpName = new JLabel("Create Account");

        // loginName.setSize(20, 20); // This line is unnecessary

        panelRR.setBackground(Color.WHITE);// Setting background color
        panelRR.setPreferredSize(new Dimension(400, 450));

        gcR.insets = new Insets(5, 5, 5, 5);
        gcR.gridx = 0;
        gcR.gridy = 1;
        gcR.gridwidth = 2;
        gcR.ipady = 5;

        panelRR.add(fullNameLabel, gcR);

        gcR.gridx = 2;
        gcR.gridy = 1;
        gcR.gridwidth = 2;
        gcR.gridheight = 1;
        gcR.ipady = 5;
        panelRR.add(fullNameField, gcR);

        gcR.gridx = 0;
        gcR.gridy = 2;
        gcR.gridwidth = 2;
        gcR.ipady = 5;
        panelRR.add(usernamee, gcR);

        gcR.gridx = 2;
        gcR.gridy = 2;
        gcR.gridwidth = 2;
        gcR.ipady = 5;
        panelRR.add(userfieldd, gcR);

        gcR.gridx = 0;
        gcR.gridy = 0; // Update this value to move "Login" label above

        gcR.gridwidth = 4; // span it across all columns
        gcR.ipady = 5; // adding padding
        panelRR.add(signUpName, gcR);

        gcR.gridx = 0;
        gcR.gridy = 3;
        gcR.gridwidth = 2;
        gcR.ipady = 5;
        panelRR.add(passwordd, gcR);

        gcR.gridx = 2;
        gcR.gridy = 3;
        gcR.gridwidth = 2;

        gcR.ipady = 5;
        panelRR.add(passfieldd, gcR);

        gcR.gridx = 0;
        gcR.gridy = 5;
        gcR.gridwidth = 2;
        gcR.ipady = 5;
        panelRR.add(passwordCheckk, gcR);

        gcR.gridx = 2;
        gcR.gridy = 5;
        gcR.gridwidth = 2;

        gcR.ipady = 5;
        panelRR.add(passcheckfieldd, gcR);

        gcR.insets = new Insets(20, 30, 10, 0);

        gcR.gridx = 0;
        gcR.gridy = 7;
        gcR.gridwidth = 2;
        gcR.gridheight = 1;
        gcR.ipady = 5;

        panelRR.add(loginPage, gcR);

        gcR.gridx = 2;
        gcR.gridy = 7;
        gcR.gridwidth = 2;
        gcR.gridheight = 1;
        gcR.ipady = 5;
        panelRR.add(signupPage, gcR);
        gcR.gridx = 1;
        gcR.gridy = 10;
        gcR.gridwidth = 4;
        gcR.gridheight = 1;
        gcR.ipady = 40;
        statusLabel.setForeground(Color.GREEN);
        panelRR.add(statusLabel, gcR);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 2;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.WEST;
        signupframe.add(panelLL, gc);

        gc.gridx = 1;
        gc.weightx = 3;

        signupframe.add(panelRR, gc);

        JLabel username = new JLabel(" Username");
        JLabel password = new JLabel("Password");
        userfield = new JTextField(17);

        passfield = new JPasswordField(17); // Using JPasswordField for password input

        login = new RoundedButtons("Log In", new Color(30, 144, 255), Color.white); // Renaming Login to login

        signUp = new RoundedButtons("Sign Up", new Color(30, 144, 255), Color.WHITE); // Renaming SignUp to signUp

        login.addActionListener(this);
        signUp.addActionListener(this);
///////
        panelL = new JPanel();
        panelL.setBackground(new Color(29, 59, 137));// Setting background color
        panelL.setPreferredSize(new Dimension(200, 450)); // Setting preferred size

        Image scaledFullLogoImageLogin = fulllogo.getImage().getScaledInstance(160, 400, Image.SCALE_SMOOTH);
        JLabel bgimageLogin = new JLabel(new ImageIcon(scaledFullLogoImageLogin));

        GridBagConstraints gcL = new GridBagConstraints();
        gcLL.gridx = 0;
        gcLL.gridy = 0;
        gcLL.weightx = 1;
        gcLL.weighty = 1;
        gcLL.fill = GridBagConstraints.BOTH;
        panelL.add(bgimageLogin, gcL);


        panelR = new JPanel(new GridBagLayout());
        GridBagConstraints gR = new GridBagConstraints();
        JLabel loginName = new JLabel("LogIn");

        // loginName.setSize(20, 20); // This line is unnecessary

        panelR.setBackground(Color.WHITE);// Setting background color
        panelR.setPreferredSize(new Dimension(400, 450));

        gR.insets = new Insets(5, 5, 5, 5);
        gR.gridx = 0;
        gR.gridy = 0; // Update this value to move "Login" label above

        gR.gridwidth = 4; // span it across all columns
        gR.ipady = 5; // adding padding
        panelR.add(loginName, gR);

        gR.gridx = 0;
        gR.gridy = 1;
        gR.gridwidth = 2;
        gR.ipady = 5;

        panelR.add(username, gR);

        gR.gridx = 2;
        gR.gridy = 1;
        gR.gridwidth = 2;
        gR.gridheight = 1;
        gR.ipady = 5;
        panelR.add(userfield, gR);

        gR.gridx = 0;
        gR.gridy = 3;
        gR.gridwidth = 2;
        gR.ipady = 5;
        panelR.add(password, gR);

        gR.gridx = 2;
        gR.gridy = 3;
        gR.gridwidth = 2;

        gR.ipady = 5;
        panelR.add(passfield, gR);

        gR.insets = new Insets(20, 30, 10, 0);

        gR.gridx = 0;
        gR.gridy = 6;
        gR.gridwidth = 2;
        gR.gridheight = 1;
        gR.ipady = 5;

        panelR.add(login, gR);

        gR.gridx = 2;
        gR.gridy = 6;
        gR.gridwidth = 2;
        gR.gridheight = 1;
        gR.ipady = 5;

        panelR.add(signUp, gR);

        GridBagConstraints gcc = new GridBagConstraints();
        gcc.gridx = 0;
        gcc.gridy = 0;
        gcc.weightx = 2;
        gcc.weighty = 1;
        gcc.fill = GridBagConstraints.BOTH;
        gcc.anchor = GridBagConstraints.WEST;
        loginframe.add(panelL, gcc);

        gcc.gridx = 1;
        gcc.weightx = 3;

        loginframe.add(panelR, gcc);

        loginframe.setVisible(false);

        signupframe.setVisible(true);
        signupframe.setResizable(false);
loginframe.setResizable(false);


    }

    void verifyLogin() {

        String uname = userfield.getText();
        char[] pass = passfield.getPassword();
        String password = new String(pass);
        try {
            String query1 = "SELECT * FROM userinfo WHERE username = ?"; // Select only by username
            PreparedStatement q1 = connection.prepareStatement(query1);
            q1.setString(1, uname);

            ResultSet resultSet = q1.executeQuery();
            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");

                if (dbPassword.equals(password)) {
                    String fname = resultSet.getString("full_name");

                    new HomePage(fname, uname);

                } else {
                    // Wrong password
                    JOptionPane.showMessageDialog(this, "Wrong password.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                // User does not exist
                JOptionPane.showMessageDialog(this, "Please create an account first.", "Alert",
                        JOptionPane.INFORMATION_MESSAGE);
            }
     
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void addToDatabase() {
        String name;
        String uname;
        char[] pass;
        name = fullNameField.getText();
        uname = userfieldd.getText();
        pass = passfieldd.getPassword();
        String passString = new String(pass);

        try {
            String query1 = "Insert into userinfo(full_name,username,password) values(?,?,?)";

            PreparedStatement queryone = connection.prepareStatement(query1);
            queryone.setString(1, name);
            queryone.setString(2, uname);
            queryone.setString(3, passString);
            queryone.executeUpdate();

            String query2 = "Insert into transactions(username,transaction_reason) values(?,?)";
            PreparedStatement q2 = connection.prepareStatement(query2);
            q2.setString(1, uname);
            q2.setString(2, "Account Created");

            q2.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println(e);
        }
    }

    int checkSignupp() {
        if (!new String(passfieldd.getPassword()).equals(new String(passcheckfieldd.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return 2;
        } else if (userfieldd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username.");
            return 2;
        } else {
            try {
                String uname = userfieldd.getText();
                // Check if the username already exists in the database
                String checkQuery = "SELECT COUNT(*) FROM userinfo WHERE username = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setString(1, uname);
                ResultSet resultSet = checkStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count > 0) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return 2; // Username already exists, return 2 indicating failure
                } else {
                    return 1; // Username does not exist, return 1 indicating success
                }
            } catch (SQLException e) {
                System.out.println(e);
                return 2; // Exception occurred, return 2 indicating failure
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginPage) {

            loginframe.setVisible(true);
            signupframe.setVisible(false);

        } else if (e.getSource() == signUp) {
            loginframe.setVisible(false);
            signupframe.setVisible(true);
        }

        else if (e.getSource() == signupPage) {
            int result = checkSignupp();
            if (result == 1) {
                addToDatabase();
                statusLabel.setText("Account created successfully.");
            }
        }

        else if (e.getSource() == login) {
            verifyLogin();
            System.out.println("Logged In");
        }

        else {
            System.out.println("Create an account");
        }
    }

    public static void main(String[] args) {
        new Account();

    }
}
