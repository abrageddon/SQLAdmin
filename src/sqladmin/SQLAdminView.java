/*
 * SQLAdminView.java
 */
package sqladmin;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;

/**
 * The application's main frame.
 */
public class SQLAdminView extends FrameView {

    public SQLAdminView(SingleFrameApplication app) {
        super(app);

        users = new ArrayList<String>();
        databases = new ArrayList<String>();

        initComponents();


    }

    private void connect() {
        String password = PasswordField.getText();
        String username = UserField.getText();
        String server = ServerField.getText();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + ":3306/mysql", username, password);
            mainPanel.setVisible(false);
            getDatabases();
            updateUsers();
            setComponent(UserListPanel);
            UserListPanel.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, ex);
        }
    }
    
    private void getDatabases() {
        try {
        Statement getDatabases = connection.createStatement();
        ResultSet dbSet = getDatabases.executeQuery("show databases");
        while (dbSet.next()) {
            databases.add(dbSet.getString(1));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getUserListValue() {
        if (UserListjList.getSelectedIndex() != -1) {
            return UserListjList.getSelectedValue().toString();
        }
        return "";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        ConnectButton = new javax.swing.JButton();
        ServerField = new javax.swing.JTextField();
        UserField = new javax.swing.JTextField();
        PasswordField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        UserListPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        EditUserButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        UserListjList = new javax.swing.JList();
        AddUserButton = new javax.swing.JButton();
        DeleteUserButton = new javax.swing.JButton();
        AddUserPanel = new javax.swing.JPanel();
        SubmitAddUser = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        AddUserName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        AddUserHost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        AddUserPass = new javax.swing.JPasswordField();
        AddUserCancel = new javax.swing.JButton();
        DBListPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        dbListPane = new javax.swing.JScrollPane();
        databaseList = new javax.swing.JList();
        SelectDB = new javax.swing.JButton();
        backToUsers = new javax.swing.JButton();
        DBPanel = new javax.swing.JPanel();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sqladmin.SQLAdminApp.class).getContext().getResourceMap(SQLAdminView.class);
        ConnectButton.setText(resourceMap.getString("ConnectButton.text")); // NOI18N
        ConnectButton.setName("ConnectButton"); // NOI18N
        ConnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectButtonActionPerformed(evt);
            }
        });

        ServerField.setText(resourceMap.getString("ServerField.text")); // NOI18N
        ServerField.setName("ServerField"); // NOI18N

        UserField.setText(resourceMap.getString("UserField.text")); // NOI18N
        UserField.setName("UserField"); // NOI18N

        PasswordField.setText(resourceMap.getString("PasswordField.text")); // NOI18N
        PasswordField.setName("PasswordField"); // NOI18N
        PasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldKeyPressed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(ServerField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(UserField, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(PasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(ConnectButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ServerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ConnectButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(sqladmin.SQLAdminApp.class).getContext().getActionMap(SQLAdminView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 360, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel))
                .addGap(3, 3, 3))
        );

        UserListPanel.setName("UserListPanel"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        EditUserButton.setText(resourceMap.getString("EditUserButton.text")); // NOI18N
        EditUserButton.setName("EditUserButton"); // NOI18N
        EditUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditUserButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        UserListjList.setModel(new javax.swing.AbstractListModel() {
            ArrayList<String> strings = users;
            public int getSize() { return strings.size(); }
            public Object getElementAt(int i) { return strings.get(i); }
        });
        UserListjList.setName("UserListjList"); // NOI18N
        jScrollPane1.setViewportView(UserListjList);

        AddUserButton.setText(resourceMap.getString("AddUserButton.text")); // NOI18N
        AddUserButton.setName("AddUserButton"); // NOI18N
        AddUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUserButtonActionPerformed(evt);
            }
        });

        DeleteUserButton.setText(resourceMap.getString("DeleteUserButton.text")); // NOI18N
        DeleteUserButton.setName("DeleteUserButton"); // NOI18N
        DeleteUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UserListPanelLayout = new javax.swing.GroupLayout(UserListPanel);
        UserListPanel.setLayout(UserListPanelLayout);
        UserListPanelLayout.setHorizontalGroup(
            UserListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UserListPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(181, 181, 181))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserListPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserListPanelLayout.createSequentialGroup()
                        .addComponent(AddUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(EditUserButton))))
        );
        UserListPanelLayout.setVerticalGroup(
            UserListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditUserButton)
                    .addComponent(DeleteUserButton)
                    .addComponent(AddUserButton))
                .addContainerGap())
        );

        AddUserPanel.setName("AddUserPanel"); // NOI18N

        SubmitAddUser.setText(resourceMap.getString("SubmitAddUser.text")); // NOI18N
        SubmitAddUser.setName("SubmitAddUser"); // NOI18N
        SubmitAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitAddUserActionPerformed(evt);
            }
        });

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        AddUserName.setText(resourceMap.getString("AddUserName.text")); // NOI18N
        AddUserName.setName("AddUserName"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        AddUserHost.setText(resourceMap.getString("AddUserHost.text")); // NOI18N
        AddUserHost.setName("AddUserHost"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        AddUserPass.setText(resourceMap.getString("AddUserPass.text")); // NOI18N
        AddUserPass.setName("AddUserPass"); // NOI18N

        AddUserCancel.setText(resourceMap.getString("AddUserCancel.text")); // NOI18N
        AddUserCancel.setName("AddUserCancel"); // NOI18N
        AddUserCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUserCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddUserPanelLayout = new javax.swing.GroupLayout(AddUserPanel);
        AddUserPanel.setLayout(AddUserPanelLayout);
        AddUserPanelLayout.setHorizontalGroup(
            AddUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddUserPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddUserPass, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddUserPanelLayout.createSequentialGroup()
                        .addComponent(AddUserCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE)
                        .addComponent(SubmitAddUser))
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(AddUserHost, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addComponent(AddUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );
        AddUserPanelLayout.setVerticalGroup(
            AddUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddUserPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddUserHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddUserPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(AddUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SubmitAddUser)
                    .addComponent(AddUserCancel))
                .addContainerGap())
        );

        DBListPanel.setName("DBListPanel"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        dbListPane.setName("dbListPane"); // NOI18N

        databaseList.setModel(new javax.swing.AbstractListModel() {
            ArrayList<String> strings = databases;
            public int getSize() { return strings.size(); }
            public Object getElementAt(int i) { return strings.get(i); }
        });
        databaseList.setName("databaseList"); // NOI18N
        dbListPane.setViewportView(databaseList);

        SelectDB.setText(resourceMap.getString("SelectDB.text")); // NOI18N
        SelectDB.setName("SelectDB"); // NOI18N

        backToUsers.setText(resourceMap.getString("backToUsers.text")); // NOI18N
        backToUsers.setName("backToUsers"); // NOI18N
        backToUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToUsersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DBListPanelLayout = new javax.swing.GroupLayout(DBListPanel);
        DBListPanel.setLayout(DBListPanelLayout);
        DBListPanelLayout.setHorizontalGroup(
            DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DBListPanelLayout.createSequentialGroup()
                        .addComponent(backToUsers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(SelectDB))
                    .addComponent(dbListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(jLabel8))
                .addContainerGap())
        );
        DBListPanelLayout.setVerticalGroup(
            DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dbListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectDB)
                    .addComponent(backToUsers))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DBPanel.setName("DBPanel"); // NOI18N

        javax.swing.GroupLayout DBPanelLayout = new javax.swing.GroupLayout(DBPanel);
        DBPanel.setLayout(DBPanelLayout);
        DBPanelLayout.setHorizontalGroup(
            DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        DBPanelLayout.setVerticalGroup(
            DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void ConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectButtonActionPerformed
        connect();
    }

    private void updateUsers() {
        try {
            Statement getUsers = connection.createStatement();
            ResultSet userSet = getUsers.executeQuery("SELECT DISTINCT User FROM `user`");
            ArrayList<String> userList = new ArrayList<String>();
            while (userSet.next()) {
                userList.add(userSet.getString("User"));
            }
            users = userList;
        } catch (SQLException e) {
            System.err.println(e);
        }
        UserListjList.setModel(new javax.swing.AbstractListModel() {

            ArrayList<String> strings = users;

            public int getSize() {
                return strings.size();
            }

            public Object getElementAt(int i) {
                return strings.get(i);
            }
        });
    }//GEN-LAST:event_ConnectButtonActionPerformed

    private void PasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            connect();
        }
    }//GEN-LAST:event_PasswordFieldKeyPressed

    private void EditUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditUserButtonActionPerformed
        editUser = getUserListValue();
        //TODO Edit User
        System.out.println("Edit: " + editUser);
        
        // Switch to database panel
        UserListPanel.setVisible(false);
        statusMessageLabel.setText("Editing User: "+editUser);
        setComponent(DBListPanel);
        DBListPanel.setVisible(true);

    }//GEN-LAST:event_EditUserButtonActionPerformed

    private void AddUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUserButtonActionPerformed
        
        AddUserName.setText("");
        AddUserHost.setText("%");
        AddUserPass.setText("");

        //Swap to add user form
        UserListPanel.setVisible(false);
        setComponent(AddUserPanel);
        AddUserPanel.setVisible(true);

    }//GEN-LAST:event_AddUserButtonActionPerformed

    private void DeleteUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteUserButtonActionPerformed
        editUser = getUserListValue();
        //TODO Delete user; not root
        if (!editUser.equals("root") && !editUser.isEmpty()) {
            int canDel = JOptionPane.showConfirmDialog(UserListPanel, "Delete User: " + editUser + "?", "Delete " + editUser, JOptionPane.YES_NO_OPTION);
            if (canDel == JOptionPane.YES_OPTION) {
                try {
                    Statement update = connection.createStatement();

                    //Check for username in database already
                    int ret = update.executeUpdate("DROP USER '" + editUser + "'");
                    if (ret == 0) {
                        JOptionPane.showMessageDialog(UserListPanel, "User Deleted.");
                    } else {
                        JOptionPane.showMessageDialog(UserListPanel, "User Not Deleted.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(UserListPanel, ex);
                }
            }
        } else if (editUser.equals("root")) {
            JOptionPane.showMessageDialog(UserListPanel, "Can't delete root account!");
        }
        //Refresh user list
        updateUsers();
    }//GEN-LAST:event_DeleteUserButtonActionPerformed

    private void SubmitAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitAddUserActionPerformed
        // TODO add your handling code here:
        String username = AddUserName.getText();
        String host = AddUserHost.getText();
        String pass = AddUserPass.getText();

        boolean added = true;

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(AddUserPanel, "Username is blank.");
            added = false;
        }
        if (host.isEmpty()) {
            JOptionPane.showMessageDialog(AddUserPanel, "Host is blank.");
            added = false;
        }
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(AddUserPanel, "Password is blank.");
            added = false;
        }
        if (added) {
            try {
                Statement update = connection.createStatement();

                //Check for username in database already
                int ret = update.executeUpdate("CREATE USER '" + username + "'@'" + host + "' IDENTIFIED BY '" + pass + "'");
                if (ret == 0) {
                    JOptionPane.showMessageDialog(AddUserPanel, "User Added.");
                    added = true;
                } else {
                    JOptionPane.showMessageDialog(AddUserPanel, "User Not Added.");
                    added = false;
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(AddUserPanel, ex);
                added = false;
            }
        }

        if (added) {
            //Refresh user list
            updateUsers();

            //Return to user list
            AddUserPanel.setVisible(false);
            setComponent(UserListPanel);
            UserListPanel.setVisible(true);
        }
    }//GEN-LAST:event_SubmitAddUserActionPerformed

    private void AddUserCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUserCancelActionPerformed

        //Refresh user list
        updateUsers();

        //Return to user list
        AddUserPanel.setVisible(false);
        setComponent(UserListPanel);
        UserListPanel.setVisible(true);
    }//GEN-LAST:event_AddUserCancelActionPerformed

private void backToUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToUsersActionPerformed
// TODO add your handling code here:
    DBListPanel.setVisible(false);
    setComponent(UserListPanel);
    UserListPanel.setVisible(true);
}//GEN-LAST:event_backToUsersActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddUserButton;
    private javax.swing.JButton AddUserCancel;
    private javax.swing.JTextField AddUserHost;
    private javax.swing.JTextField AddUserName;
    private javax.swing.JPanel AddUserPanel;
    private javax.swing.JPasswordField AddUserPass;
    private javax.swing.JButton ConnectButton;
    private javax.swing.JPanel DBListPanel;
    private javax.swing.JPanel DBPanel;
    private javax.swing.JButton DeleteUserButton;
    private javax.swing.JButton EditUserButton;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JButton SelectDB;
    private javax.swing.JTextField ServerField;
    private javax.swing.JButton SubmitAddUser;
    private javax.swing.JTextField UserField;
    private javax.swing.JPanel UserListPanel;
    private javax.swing.JList UserListjList;
    private javax.swing.JButton backToUsers;
    private javax.swing.JList databaseList;
    private javax.swing.JScrollPane dbListPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private Connection connection;
    private ArrayList<String> users;
    private String editUser;
    private ArrayList<String> databases;
    private String editDatabase;
}
