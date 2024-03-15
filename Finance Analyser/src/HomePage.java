import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePage extends JFrame implements ActionListener {

    JFrame Home;
    JPanel TopBar;
    JPanel Window;
    JLabel amount, totalBal;
    JLabel greetings;
    RoundedButtons Add;
    double money = 0.00;
    double moneyAdded, moneySpent = 0.00;
    String moneyAddedReason, moneySpentReason;
    String firstname, username = "";

    JLabel receivedAmount;
    JLabel spentAmount;

    JPanel Menu;
    JButton menuHome, menuTransaction;
    JPanel windowTransaction;
    JButton logout;
    LocalDate currentDate = LocalDate.now();
    String currentMonth = currentDate.getMonth().toString();

    JLabel incomeLabel;
    JTextField incomeTextField;
    JButton incomeButton;

    JLabel expenseLabel;
    JTextField expenseTextField;
    JButton expenseButton;
    JTable transactionTable;
    DefaultTableModel model;
    JScrollPane transaction;

    String url = "jdbc:mysql://localhost:3306/wmw"; // 'wmw' is the database name
    String userName = "root";
    String password = "tiger";
    Connection connection;

 
    void addPieChartToWindow() {

        // Create a pie chart dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Received", Double.parseDouble(receivedAmount.getText().substring(1)));
        dataset.setValue("Spent", Double.parseDouble(spentAmount.getText().substring(1)));
    
        // Create the pie chart
        JFreeChart chart = ChartFactory.createPieChart("Income vs Expense", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Received", new Color(50, 205, 50)); // Green color for received
        plot.setSectionPaint("Spent", new Color(178, 34, 34)); // Red color for spent
        plot.setBackgroundPaint(new Color(240, 240, 240));

        // Create a chart panel and add the chart to it
        ChartPanel chartPanel = new ChartPanel(chart);
    
        // Set the preferred size of the chart panel
        chartPanel.setPreferredSize(new Dimension(650, 400)); // Adjust dimensions as needed
    
        // Add the chart panel to the Window panel
        GridBagConstraints winConstraints = new GridBagConstraints();
        winConstraints.gridx = 0;
        winConstraints.gridy = 1; // Adjust the row to position the chart
        winConstraints.gridwidth = 2;
        winConstraints.anchor = GridBagConstraints.CENTER;
        Window.add(chartPanel, winConstraints);
    }
    
        
    


    void getTotalBalance() {
        try {
            String query = "SELECT * FROM transactions WHERE username = ? ORDER BY transaction_id  DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                money = resultSet.getDouble("updatedBal");
                // Update amount label with retrieved balance
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    HomePage(String fname, String uname) {
        // HomePage(){
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        try {

            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        firstname = fname;
        username = uname;
        Home = new JFrame("Watch my Wealth");
        ImageIcon logo = new ImageIcon(
                "C:\\Users\\krish\\OneDrive\\Desktop\\New folder (2)\\FA\\Finance Analyser\\src\\LogoIcon.png");
        Home.setIconImage(logo.getImage());
        Home.setLayout(new BorderLayout());

        Home.setSize(950, 600);
        Home.setResizable(false);
        Home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu = new JPanel();
        Menu.setBackground(new Color(32, 47, 90)); // Dark blue color for menu
        Menu.setPreferredSize(new Dimension(250, Home.getHeight()));
        Menu.setLayout(new BoxLayout(Menu, BoxLayout.Y_AXIS));

        TopBar = new JPanel(new GridBagLayout());
        TopBar.setBackground(new Color(21, 36, 69)); // Darker blue color for top bar
        TopBar.setPreferredSize(new Dimension(700, 90));

        Window = new JPanel(new GridBagLayout());
        Window.setBackground(new Color(240, 240, 240)); // Light grayish color for window
        Window.setPreferredSize(new Dimension(700, 510));

        // Labels
        getTotalBalance();
        amount = new JLabel("$ " + money);
        amount.setForeground(new Color(50, 205, 50)); // Shades of green for amount
        amount.setFont(new Font("Arial", Font.BOLD, 20));

        totalBal = new JLabel("Account Balance:");
        totalBal.setForeground(new Color(70, 130, 180)); // Shades of blue for total balance
        totalBal.setFont(new Font("Arial", Font.PLAIN, 10));

        String welcome = ExtraFunction.generateGreeting(firstname);
        greetings = new JLabel(welcome);
        greetings.setForeground(new Color(255, 255, 255)); // White color for greetings
        greetings.setFont(new Font("Tahoma", Font.PLAIN, 19)); // Changed font to Tahoma

        // Buttons
        Add = new RoundedButtons("+ Add Money", new Color(30, 144, 255), Color.white);
        Add.addActionListener(this);

        GridBagConstraints tp = new GridBagConstraints();
        tp.anchor = GridBagConstraints.LINE_END;
        tp.gridx = 0;
        tp.gridy = 0;
        tp.insets = new Insets(20, 800, 0, 20);
        TopBar.add(amount, tp);

        tp.insets = new Insets(0, 800, 30, 20);
        TopBar.add(totalBal, tp);

        tp.insets = new Insets(7, 0, 0, 140);
        tp.ipady = 7;
        TopBar.add(Add, tp);

        tp.insets = new Insets(0, 0, 0, 720);
        TopBar.add(greetings, tp);

        /////////////////////////// Menu//////////////////////
        menuHome = new JButton("Home ");
        menuHome.addActionListener(this);
        menuHome.setFont(new Font("Tahoma", Font.BOLD, 13)); // Changed font to Tahoma
        menuHome.setForeground(new Color(192, 192, 192)); // Lighter shade of gray for menu items
        menuHome.setBackground(null);
        menuHome.setBorder(null);
        menuHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuHome.setBorder(BorderFactory.createEmptyBorder(20, 80, 10, 80));

        menuTransaction = new JButton("Transactions");
        menuTransaction.addActionListener(this);
        menuTransaction.setFont(new Font("Tahoma", Font.BOLD, 13)); // Changed font to Tahoma
        menuTransaction.setForeground(new Color(192, 192, 192)); // Lighter shade of gray for menu items
        menuTransaction.setBackground(null);
        menuTransaction.setBorder(null);
        menuTransaction.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTransaction.setBorder(BorderFactory.createEmptyBorder(35, 80, 10, 80));

        logout = new JButton("Logout ");
        logout.addActionListener(this);
        logout.setFont(new Font("Tahoma", Font.BOLD, 13)); // Changed font to Tahoma
        logout.setForeground(new Color(192, 192, 192)); // Lighter shade of gray for menu items
        logout.setBackground(null);
        logout.setBorder(null);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setBorder(BorderFactory.createEmptyBorder(320, 80, 10, 80));

        ///////////////////////////// Window/////////////////////////////

        JLabel receivedText = new JLabel("Total Received");
        JLabel spentText = new JLabel("Total Spent");
        JLabel month = new JLabel("Current Month : "+currentMonth);
        receivedAmount = new JLabel("$" + 0);
        totalReceived();
        spentAmount = new JLabel("$" + 0);
        totalSpent();

        receivedText.setFont(new Font("Tahoma", Font.BOLD, 18)); 
        receivedText.setForeground(new Color(34, 139, 34)); // Green color for total received

        spentText.setFont(new Font("Tahoma", Font.BOLD, 18)); 
        spentText.setForeground(new Color(139, 0, 0)); 

        month.setFont(new Font("Tahoma", Font.BOLD, 15)); 

        month.setForeground(new Color(64, 64, 64)); 

        receivedAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
        receivedAmount.setForeground(new Color(50, 205, 50));

        spentAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
        spentAmount.setForeground(new Color(178, 34, 34));

        GridBagConstraints win = new GridBagConstraints();
        win.anchor = GridBagConstraints.CENTER;
        win.insets = new Insets(0, 80, 30, 10);
        win.gridx = 0;
        win.gridy = 0;
        Window.add(month, win);
        win.anchor = GridBagConstraints.WEST;
        win.insets = new Insets(0, 0, 380, 350);
        win.gridx = 0;
        win.gridy = 1;
        Window.add(receivedText, win);
        win.insets = new Insets(0, 0, 380, 0);
        win.gridx = 1;
        win.gridy = 1;
        Window.add(spentText, win);

        win.insets = new Insets(0, 22, 290, 375);
        win.gridx = 0;
        win.gridy = 1;
        Window.add(receivedAmount, win);
        win.insets = new Insets(0, 22, 290, 0);
        win.gridx = 1;
        win.gridy = 1;
        Window.add(spentAmount, win);

        addPieChartToWindow();

        //////////////// windowTransaction/////////////////////
        windowTransaction = new JPanel(new GridBagLayout());
        windowTransaction.setVisible(false);
        windowTransaction.setPreferredSize(new Dimension(700, 510));
        windowTransaction.setBackground(new Color(240, 240, 240));

        model = new DefaultTableModel();
        // model.addColumn("Sr No");
        model.addColumn("Amount");
        model.addColumn("Transaction Type");
        model.addColumn("Transaction Reason");
        model.addColumn("Updated Balance");
        model.addColumn("Transaction Time");

        transactionTable = new JTable(model);

        // Add transactionTable to the JScrollPane
        transaction = new JScrollPane(transactionTable);
        transaction.setPreferredSize(new Dimension(600, 200));
        transaction.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        transaction.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        transaction.setBackground(new Color(200, 200, 200));

        fetch();

        // Income Section
        incomeLabel = new JLabel("Income");
        incomeTextField = new JTextField(20);
        incomeButton = new JButton("Add");
        incomeButton.addActionListener(this);

        // Expense Section
        expenseLabel = new JLabel("Expense");
        expenseTextField = new JTextField(20);
        expenseButton = new JButton("Add");
        expenseButton.addActionListener(this);

        GridBagConstraints wtConstraints = new GridBagConstraints();
        wtConstraints.gridx = 0;
        wtConstraints.gridy = 0;
        wtConstraints.anchor = GridBagConstraints.WEST;
        wtConstraints.insets = new Insets(10, 15, 10, 10); // Reduce top and bottom insets
        windowTransaction.add(incomeLabel, wtConstraints);

        wtConstraints.gridx = 1;
        wtConstraints.gridwidth = 2; // Increase grid width to cover both text field and button
        windowTransaction.add(incomeTextField, wtConstraints);

        wtConstraints.gridx = 3;
        wtConstraints.gridwidth = 1; // Reset grid width
        windowTransaction.add(incomeButton, wtConstraints);

        wtConstraints.gridy = 1; // Move to the next row
        wtConstraints.gridx = 0;
        windowTransaction.add(expenseLabel, wtConstraints);

        wtConstraints.gridx = 1;
        wtConstraints.gridwidth = 2; // Increase grid width to cover both text field and button
        windowTransaction.add(expenseTextField, wtConstraints);

        wtConstraints.gridx = 3;
        wtConstraints.gridwidth = 1; // Reset grid width
        windowTransaction.add(expenseButton, wtConstraints);

        // Adjust GridBagConstraints for the scroll panel
        GridBagConstraints scrollConstraints = new GridBagConstraints();
        scrollConstraints.gridx = 0;
        scrollConstraints.gridy = 2; // Move to the next row
        scrollConstraints.gridwidth = 6; // Span across all columns
        scrollConstraints.gridheight = 1; // Increase vertical coverage
        scrollConstraints.fill = GridBagConstraints.BOTH; // Allow both horizontal and vertical stretching
        scrollConstraints.weightx = 1.0; // Add weight to allow horizontal stretching
        scrollConstraints.weighty = 1.0; // Add weight to allow vertical stretching
        scrollConstraints.insets = new Insets(10, 15, 10, 15); // Adjust margins
        windowTransaction.add(transaction, scrollConstraints);

        windowTransaction.setVisible(true);

        Menu.add(menuHome);
        Menu.add(menuTransaction);
        Menu.add(logout);

        // Add windowTransaction to Home frame before other components
        Home.add(windowTransaction, BorderLayout.CENTER);
        Home.add(Window, BorderLayout.CENTER);
        Home.add(Menu, BorderLayout.WEST);
        Home.add(TopBar, BorderLayout.NORTH);

        Home.setVisible(true);

    }// END OF CONSTRUCTOR

    void totalReceived() {
        String query = "SELECT SUM(amount) AS total_received FROM transactions WHERE username = ? AND transaction_type = ? AND MONTH(transaction_time)=?";
        int monthNo = currentDate.getMonthValue();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, "credited");
            ps.setInt(3, monthNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double totalReceivedAmount = rs.getDouble("total_received");
                receivedAmount.setText("$" + totalReceivedAmount);
                System.out.println("Total received amount: $" + totalReceivedAmount);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void totalSpent() {
        String query = "SELECT SUM(amount) AS total_received FROM transactions WHERE username = ? AND transaction_type = ? AND MONTH(transaction_time)=?";
        int monthNo = currentDate.getMonthValue();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, "debited");
            ps.setInt(3, monthNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double totalSpentAmount = rs.getDouble("total_received");
                spentAmount.setText("$" + totalSpentAmount);
                System.out.println("Total amount spent: $" + totalSpentAmount);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void spendMoney() {
        String am = expenseTextField.getText();
        moneySpent = Double.parseDouble(am);
        try {
            money -= moneySpent; // Update the 'money' variable with the added amount

            String query1 = "INSERT INTO transactions(username, amount, updatedBal, transaction_type, transaction_reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement q1 = connection.prepareStatement(query1);
            q1.setString(1, username);
            q1.setDouble(2, moneySpent);
            q1.setDouble(3, money); // Use the updated 'money' variable
            q1.setString(4, "debited");
            q1.setString(5, moneySpentReason);
            q1.executeUpdate(); // Use executeUpdate() for INSERT queries

            // Update the amount label to reflect the new balance
            amount.setText("$" + money);

            addPieChartToWindow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fetch();

    }

    void addMoney() {
        double amountAdded = moneyAdded;
        try {
            money += amountAdded; // Update the 'money' variable with the added amount

            String query1 = "INSERT INTO transactions(username, amount, updatedBal, transaction_type, transaction_reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement q1 = connection.prepareStatement(query1);
            q1.setString(1, username);
            q1.setDouble(2, amountAdded);
            q1.setDouble(3, money); // Use the updated 'money' variable
            q1.setString(4, "credited");
            q1.setString(5, moneyAddedReason);
            q1.executeUpdate(); // Use executeUpdate() for INSERT queries

            // Update the amount label to reflect the new balance
            amount.setText("$" + money);
            fetch();

            addPieChartToWindow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void fetch() {
        try {
            String query = "Select amount, transaction_type, transaction_reason, updatedBal,transaction_time from transactions where username = ? ;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            model.setRowCount(0);
            ResultSet resultSet = ps.executeQuery();
          
            while (resultSet.next()) {

                String amount = String.valueOf(resultSet.getDouble("amount"));
                String transactionType = resultSet.getString("transaction_type");
                String transactionReason = resultSet.getString("transaction_reason");
                String updatedBal = String.valueOf(resultSet.getDouble("updatedBal"));
                String transactionTime = resultSet.getString("transaction_time");

                model.addRow(new Object[] { amount, transactionType, transactionReason, updatedBal, transactionTime });

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Add) {
            String moneyAddedString = JOptionPane.showInputDialog(Window, "Enter Amount to be added", "Adding Money...",
                    3);
            moneyAddedReason = JOptionPane.showInputDialog(Window, "Comments/Received from/sent by ", "Reason", 3);
            moneyAdded = Double.parseDouble(moneyAddedString);

            addMoney();
            amount.setText("$" + money);
            receivedAmount.setText("$" + money);

            JOptionPane.showMessageDialog(this, "Money Added Successfully");

        } else if (e.getSource() == menuHome) {
            // Show the default Window panel
            Home.getContentPane().removeAll(); // Remove all components
            Home.add(Window, BorderLayout.CENTER);
            Home.add(Menu, BorderLayout.WEST);
            Home.add(TopBar, BorderLayout.NORTH);
            Home.revalidate(); // Refresh the frame
            Home.repaint();
        } else if (e.getSource() == menuTransaction) {
            // Show the windowTransaction panel
            Home.getContentPane().removeAll(); // Remove all components
            Home.add(windowTransaction, BorderLayout.CENTER);
            Home.add(Menu, BorderLayout.WEST);
            Home.add(TopBar, BorderLayout.NORTH);
            Home.revalidate(); // Refresh the frame
            Home.repaint();

            fetch();
        } else if (e.getSource() == logout) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                Home.dispose();
                Home.setVisible(false);

                Account loginFrame = new Account();
                loginFrame.setVisible(true);
            }
        }

        else if(e.getSource() == incomeButton) {
            String r = incomeTextField.getText();
            Double rec = Double.parseDouble(r);
            moneyAdded = rec;
            // Create a dropdown menu for income categories
            String[] incomeCategories = {"Salary","Interest","Pension","Bonus", "Gift", "Other"};
            JComboBox<String> incomeCategoryDropdown = new JComboBox<>(incomeCategories);
            incomeCategoryDropdown.setSelectedIndex(0);
            
int option = JOptionPane.showConfirmDialog(Window, new Object[]{"Select Category:", incomeCategoryDropdown}, "Select Income Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

if (option == JOptionPane.OK_OPTION) {
    String selectedCategory = incomeCategoryDropdown.getSelectedItem().toString();
    if (selectedCategory.equals("Other")) {
        moneyAddedReason = JOptionPane.showInputDialog(Window, "Enter Other Category:", "Other", JOptionPane.PLAIN_MESSAGE);
    } else {
        moneyAddedReason = selectedCategory;
    }
              
                
                addMoney();
                amount.setText("$" + money);
                receivedAmount.setText("$" + money);
                incomeTextField.setText("");
            }
        } else if (e.getSource() == expenseButton) {
            moneySpentReason = expenseTextField.getText();
            
            String[] expenseCategories = {"Food", "Transportation", "Utilities", "Entertainment", "Shopping", "Insurance","Rent","Healthcare","Other"};
            JComboBox<String> expenseCategoryDropdown = new JComboBox<>(expenseCategories);
            expenseCategoryDropdown.setSelectedIndex(0);
            
            int option = JOptionPane.showConfirmDialog(Window, new Object[]{"Select Category:", expenseCategoryDropdown}, "Select Expense Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
        String selectedCategory = expenseCategoryDropdown.getSelectedItem().toString();

                if(selectedCategory.equals("Others")){

         moneyAddedReason = JOptionPane.showInputDialog(Window, "Enter Other Category:", "Other", JOptionPane.PLAIN_MESSAGE);

                } else {
                    moneyAddedReason = selectedCategory;
                }

                }
                spendMoney();
                amount.setText("$" + money);
                receivedAmount.setText("$" + money);
                expenseTextField.setText("");
            }
        }
    

    

    public static void main(String[] args) {
        new HomePage("demo", "demo123");
    }
}
