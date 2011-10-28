/*
 * SQLAdminView.java
 */
package sqladmin;

import java.awt.Dimension;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
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
        tables = new ArrayList<String>();

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
            editServer = server;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Connect: " + ex.getMessage());
        }
    }

    private void getDatabases() {
        try {
            Statement getDatabases = connection.createStatement();
            ResultSet dbSet = getDatabases.executeQuery("show databases");
            while (dbSet.next()) {
                databases.add(dbSet.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "getDatabases: " + ex.getMessage());
        }
    }

    private String getDBListValue() {
        if (databaseList.getSelectedIndex() != -1) {
            return databaseList.getSelectedValue().toString();
        } else {
            return "";
        }
    }

	private void getDBPrivs() {
		try {
			Statement updateDBPrivs = connection.createStatement();
			ResultSet DBPrivs = updateDBPrivs.executeQuery("SELECT * from mysql.db WHERE user='" + editUser + "' AND db='" + editDatabase + "';");
			
			if (DBPrivs.next()) {
				DBSelect.setSelected(DBPrivs.getBoolean("Select_priv"));
				DBInsert.setSelected(DBPrivs.getBoolean("Insert_priv"));
				DBDelete.setSelected(DBPrivs.getBoolean("Delete_priv"));
				DBUpdate.setSelected(DBPrivs.getBoolean("Update_priv"));
				DBCreate.setSelected(DBPrivs.getBoolean("Create_priv"));
				DBDrop.setSelected(DBPrivs.getBoolean("Drop_priv"));
				DBGrant.setSelected(DBPrivs.getBoolean("Grant_priv"));
				DBIndex.setSelected(DBPrivs.getBoolean("Index_priv"));
				DBAlter.setSelected(DBPrivs.getBoolean("Alter_priv"));
				DBCreateTempTables.setSelected(DBPrivs.getBoolean("Create_tmp_table_priv"));
				DBShowView.setSelected(DBPrivs.getBoolean("Show_view_priv"));
				DBCreateRoutine.setSelected(DBPrivs.getBoolean("Create_routine_priv"));
				DBAlterRoutine.setSelected(DBPrivs.getBoolean("Alter_routine_priv"));
				DBExecute.setSelected(DBPrivs.getBoolean("Execute_priv"));
				DBCreateView.setSelected(DBPrivs.getBoolean("Create_view_priv"));
				DBEvent.setSelected(DBPrivs.getBoolean("Event_priv"));
				DBTrigger.setSelected(DBPrivs.getBoolean("Trigger_priv"));
				DBLockTables.setSelected(DBPrivs.getBoolean("Lock_tables_priv"));
				DBReferences.setSelected(DBPrivs.getBoolean("References_priv"));
			} else {
				DBSelect.setSelected(false);
				DBInsert.setSelected(false);
				DBDelete.setSelected(false);
				DBUpdate.setSelected(false);
				DBCreate.setSelected(false);
				DBDrop.setSelected(false);
				DBGrant.setSelected(false);
				DBIndex.setSelected(false);
				DBAlter.setSelected(false);
				DBCreateTempTables.setSelected(false);
				DBShowView.setSelected(false);
				DBCreateRoutine.setSelected(false);
				DBAlterRoutine.setSelected(false);
				DBExecute.setSelected(false);
				DBCreateView.setSelected(false);
				DBEvent.setSelected(false);
				DBTrigger.setSelected(false);
				DBLockTables.setSelected(false);
				DBReferences.setSelected(false);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
			
	private void getTables() {
		try {
			Statement useDB = connection.createStatement();
			useDB.execute("use " + editDatabase + ";");
			useDB.close();
			Statement getTables = connection.createStatement();
			ResultSet tableSet = getTables.executeQuery("show tables;");
			tables.clear();
			while (tableSet.next()) {
				tables.add(tableSet.getString(1));
			}
			if (tables.size() == 0) {
				tables.add("No tables.");
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
        GlobalPrivilegeLabel = new javax.swing.JLabel();
        GlobalCreateCheckbox = new javax.swing.JCheckBox();
        GlobalPrivilegeSubmitButton = new javax.swing.JButton();
        GlobalSelectCheckbox = new javax.swing.JCheckBox();
        GlobalInsertCheckbox = new javax.swing.JCheckBox();
        GlobalUpdateCheckbox = new javax.swing.JCheckBox();
        GlobalDeleteCheckbox = new javax.swing.JCheckBox();
        GlobalDropCheckbox = new javax.swing.JCheckBox();
        HostComboBox = new javax.swing.JComboBox();
        GlobalReloadCheckbox = new javax.swing.JCheckBox();
        GlobalShutdownCheckbox = new javax.swing.JCheckBox();
        GlobalProcessCheckbox = new javax.swing.JCheckBox();
        GlobalFileCheckbox = new javax.swing.JCheckBox();
        GlobalGrantCheckbox = new javax.swing.JCheckBox();
        GlobalReferencesCheckbox = new javax.swing.JCheckBox();
        GlobalIndexCheckbox = new javax.swing.JCheckBox();
        GlobalAlterCheckbox = new javax.swing.JCheckBox();
        GlobalShowDbCheckbox = new javax.swing.JCheckBox();
        GlobalSuperCheckbox = new javax.swing.JCheckBox();
        GlobalCreateTmpTableCheckbox = new javax.swing.JCheckBox();
        GlobalLockTablesCheckbox = new javax.swing.JCheckBox();
        GlobalExecuteCheckbox = new javax.swing.JCheckBox();
        GlobalReplSlaveCheckbox = new javax.swing.JCheckBox();
        GlobalReplClientCheckbox = new javax.swing.JCheckBox();
        GlobalCreateViewCheckbox = new javax.swing.JCheckBox();
        GlobalShowViewCheckbox = new javax.swing.JCheckBox();
        GlobalCreateRoutineCheckbox = new javax.swing.JCheckBox();
        GlobalAlterRoutineCheckbox = new javax.swing.JCheckBox();
        GlobalCreateUserCheckbox = new javax.swing.JCheckBox();
        GlobalEventCheckbox = new javax.swing.JCheckBox();
        GlobalTriggerCheckbox = new javax.swing.JCheckBox();
        AddHostButton = new javax.swing.JButton();
        RemoveHostButton = new javax.swing.JButton();
        ChangePasswordButton = new javax.swing.JButton();
        AddHostPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        AddHostNameField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        AddHostPassField = new javax.swing.JPasswordField();
        DBPanel = new javax.swing.JPanel();
        BackToDBs = new javax.swing.JButton();
        DBPanelTitle = new javax.swing.JLabel();
        DBUpdatePriv = new javax.swing.JButton();
        DBSelect = new javax.swing.JCheckBox();
        DBInsert = new javax.swing.JCheckBox();
        DBDelete = new javax.swing.JCheckBox();
        DBUpdate = new javax.swing.JCheckBox();
        DBCreate = new javax.swing.JCheckBox();
        DBDrop = new javax.swing.JCheckBox();
        DBGrant = new javax.swing.JCheckBox();
        DBIndex = new javax.swing.JCheckBox();
        DBAlter = new javax.swing.JCheckBox();
        DBCreateTempTables = new javax.swing.JCheckBox();
        DBShowView = new javax.swing.JCheckBox();
        DBCreateRoutine = new javax.swing.JCheckBox();
        DBAlterRoutine = new javax.swing.JCheckBox();
        DBExecute = new javax.swing.JCheckBox();
        DBCreateView = new javax.swing.JCheckBox();
        DBEvent = new javax.swing.JCheckBox();
        DBTrigger = new javax.swing.JCheckBox();
        DBLockTables = new javax.swing.JCheckBox();
        DBReferences = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        DBTablesList = new javax.swing.JList();
        DBTableListLabel = new javax.swing.JLabel();
        EditTablePrivs = new javax.swing.JButton();

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
                    .addComponent(ServerField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(UserField, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(PasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserListPanelLayout.createSequentialGroup()
                        .addComponent(AddUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
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

        SelectDB.setAction(actionMap.get("SelectDBActionPerformed")); // NOI18N
        SelectDB.setText(resourceMap.getString("SelectDB.text")); // NOI18N
        SelectDB.setName("SelectDB"); // NOI18N
        SelectDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectDBActionPerformed(evt);
            }
        });

        backToUsers.setText(resourceMap.getString("backToUsers.text")); // NOI18N
        backToUsers.setName("backToUsers"); // NOI18N
        backToUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToUsersActionPerformed(evt);
            }
        });

        GlobalPrivilegeLabel.setText(resourceMap.getString("GlobalPrivilegeLabel.text")); // NOI18N
        GlobalPrivilegeLabel.setName("GlobalPrivilegeLabel"); // NOI18N

        GlobalCreateCheckbox.setText(resourceMap.getString("GlobalCreateCheckbox.text")); // NOI18N
        GlobalCreateCheckbox.setName("GlobalCreateCheckbox"); // NOI18N

        GlobalPrivilegeSubmitButton.setText(resourceMap.getString("GlobalPrivilegeSubmitButton.text")); // NOI18N
        GlobalPrivilegeSubmitButton.setName("GlobalPrivilegeSubmitButton"); // NOI18N
        GlobalPrivilegeSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GlobalPrivilegeSubmitButtonActionPerformed(evt);
            }
        });

        GlobalSelectCheckbox.setText(resourceMap.getString("GlobalSelectCheckbox.text")); // NOI18N
        GlobalSelectCheckbox.setName("GlobalSelectCheckbox"); // NOI18N

        GlobalInsertCheckbox.setText(resourceMap.getString("GlobalInsertCheckbox.text")); // NOI18N
        GlobalInsertCheckbox.setName("GlobalInsertCheckbox"); // NOI18N

        GlobalUpdateCheckbox.setText(resourceMap.getString("GlobalUpdateCheckbox.text")); // NOI18N
        GlobalUpdateCheckbox.setName("GlobalUpdateCheckbox"); // NOI18N

        GlobalDeleteCheckbox.setText(resourceMap.getString("GlobalDeleteCheckbox.text")); // NOI18N
        GlobalDeleteCheckbox.setName("GlobalDeleteCheckbox"); // NOI18N

        GlobalDropCheckbox.setText(resourceMap.getString("GlobalDropCheckbox.text")); // NOI18N
        GlobalDropCheckbox.setName("GlobalDropCheckbox"); // NOI18N

        HostComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        HostComboBox.setName("HostComboBox"); // NOI18N
        HostComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HostComboBoxActionPerformed(evt);
            }
        });

        GlobalReloadCheckbox.setText(resourceMap.getString("GlobalReloadCheckbox.text")); // NOI18N
        GlobalReloadCheckbox.setName("GlobalReloadCheckbox"); // NOI18N

        GlobalShutdownCheckbox.setText(resourceMap.getString("GlobalShutdownCheckbox.text")); // NOI18N
        GlobalShutdownCheckbox.setName("GlobalShutdownCheckbox"); // NOI18N

        GlobalProcessCheckbox.setText(resourceMap.getString("GlobalProcessCheckbox.text")); // NOI18N
        GlobalProcessCheckbox.setName("GlobalProcessCheckbox"); // NOI18N

        GlobalFileCheckbox.setText(resourceMap.getString("GlobalFileCheckbox.text")); // NOI18N
        GlobalFileCheckbox.setName("GlobalFileCheckbox"); // NOI18N

        GlobalGrantCheckbox.setText(resourceMap.getString("GlobalGrantCheckbox.text")); // NOI18N
        GlobalGrantCheckbox.setName("GlobalGrantCheckbox"); // NOI18N

        GlobalReferencesCheckbox.setText(resourceMap.getString("GlobalReferencesCheckbox.text")); // NOI18N
        GlobalReferencesCheckbox.setName("GlobalReferencesCheckbox"); // NOI18N

        GlobalIndexCheckbox.setText(resourceMap.getString("GlobalIndexCheckbox.text")); // NOI18N
        GlobalIndexCheckbox.setName("GlobalIndexCheckbox"); // NOI18N

        GlobalAlterCheckbox.setText(resourceMap.getString("GlobalAlterCheckbox.text")); // NOI18N
        GlobalAlterCheckbox.setName("GlobalAlterCheckbox"); // NOI18N

        GlobalShowDbCheckbox.setText(resourceMap.getString("GlobalShowDbCheckbox.text")); // NOI18N
        GlobalShowDbCheckbox.setName("GlobalShowDbCheckbox"); // NOI18N

        GlobalSuperCheckbox.setText(resourceMap.getString("GlobalSuperCheckbox.text")); // NOI18N
        GlobalSuperCheckbox.setName("GlobalSuperCheckbox"); // NOI18N

        GlobalCreateTmpTableCheckbox.setText(resourceMap.getString("GlobalCreateTmpTableCheckbox.text")); // NOI18N
        GlobalCreateTmpTableCheckbox.setName("GlobalCreateTmpTableCheckbox"); // NOI18N

        GlobalLockTablesCheckbox.setText(resourceMap.getString("GlobalLockTablesCheckbox.text")); // NOI18N
        GlobalLockTablesCheckbox.setName("GlobalLockTablesCheckbox"); // NOI18N

        GlobalExecuteCheckbox.setText(resourceMap.getString("GlobalExecuteCheckbox.text")); // NOI18N
        GlobalExecuteCheckbox.setName("GlobalExecuteCheckbox"); // NOI18N

        GlobalReplSlaveCheckbox.setText(resourceMap.getString("GlobalReplSlaveCheckbox.text")); // NOI18N
        GlobalReplSlaveCheckbox.setName("GlobalReplSlaveCheckbox"); // NOI18N

        GlobalReplClientCheckbox.setText(resourceMap.getString("GlobalReplClientCheckbox.text")); // NOI18N
        GlobalReplClientCheckbox.setName("GlobalReplClientCheckbox"); // NOI18N

        GlobalCreateViewCheckbox.setText(resourceMap.getString("GlobalCreateViewCheckbox.text")); // NOI18N
        GlobalCreateViewCheckbox.setName("GlobalCreateViewCheckbox"); // NOI18N

        GlobalShowViewCheckbox.setText(resourceMap.getString("GlobalShowViewCheckbox.text")); // NOI18N
        GlobalShowViewCheckbox.setName("GlobalShowViewCheckbox"); // NOI18N

        GlobalCreateRoutineCheckbox.setText(resourceMap.getString("GlobalCreateRoutineCheckbox.text")); // NOI18N
        GlobalCreateRoutineCheckbox.setName("GlobalCreateRoutineCheckbox"); // NOI18N

        GlobalAlterRoutineCheckbox.setText(resourceMap.getString("GlobalAlterRoutineCheckbox.text")); // NOI18N
        GlobalAlterRoutineCheckbox.setName("GlobalAlterRoutineCheckbox"); // NOI18N

        GlobalCreateUserCheckbox.setText(resourceMap.getString("GlobalCreateUserCheckbox.text")); // NOI18N
        GlobalCreateUserCheckbox.setName("GlobalCreateUserCheckbox"); // NOI18N

        GlobalEventCheckbox.setText(resourceMap.getString("GlobalEventCheckbox.text")); // NOI18N
        GlobalEventCheckbox.setName("GlobalEventCheckbox"); // NOI18N

        GlobalTriggerCheckbox.setText(resourceMap.getString("GlobalTriggerCheckbox.text")); // NOI18N
        GlobalTriggerCheckbox.setName("GlobalTriggerCheckbox"); // NOI18N

        AddHostButton.setText(resourceMap.getString("AddHostButton.text")); // NOI18N
        AddHostButton.setName("AddHostButton"); // NOI18N
        AddHostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddHostButtonActionPerformed(evt);
            }
        });

        RemoveHostButton.setText(resourceMap.getString("RemoveHostButton.text")); // NOI18N
        RemoveHostButton.setName("RemoveHostButton"); // NOI18N
        RemoveHostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveHostButtonActionPerformed(evt);
            }
        });

        ChangePasswordButton.setText(resourceMap.getString("ChangePasswordButton.text")); // NOI18N
        ChangePasswordButton.setName("ChangePasswordButton"); // NOI18N
        ChangePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePasswordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DBListPanelLayout = new javax.swing.GroupLayout(DBListPanel);
        DBListPanel.setLayout(DBListPanelLayout);
        DBListPanelLayout.setHorizontalGroup(
            DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBListPanelLayout.createSequentialGroup()
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DBListPanelLayout.createSequentialGroup()
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GlobalSelectCheckbox)
                                    .addComponent(GlobalInsertCheckbox)
                                    .addComponent(GlobalDeleteCheckbox)
                                    .addComponent(GlobalCreateCheckbox)
                                    .addComponent(GlobalDropCheckbox))
                                .addGap(57, 57, 57)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GlobalGrantCheckbox)
                                    .addComponent(GlobalSuperCheckbox)
                                    .addComponent(GlobalShowDbCheckbox)
                                    .addComponent(GlobalAlterCheckbox)
                                    .addComponent(GlobalReferencesCheckbox)
                                    .addComponent(GlobalIndexCheckbox)
                                    .addComponent(GlobalCreateTmpTableCheckbox)
                                    .addComponent(GlobalLockTablesCheckbox)
                                    .addComponent(GlobalExecuteCheckbox)
                                    .addComponent(GlobalReplSlaveCheckbox)))
                            .addComponent(GlobalUpdateCheckbox)))
                    .addComponent(GlobalPrivilegeSubmitButton)
                    .addComponent(backToUsers)
                    .addComponent(GlobalPrivilegeLabel)
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GlobalReloadCheckbox))
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GlobalShutdownCheckbox))
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GlobalProcessCheckbox))
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GlobalFileCheckbox))
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(HostComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddHostButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RemoveHostButton)))
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                        .addComponent(ChangePasswordButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(GlobalReplClientCheckbox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalCreateViewCheckbox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalShowViewCheckbox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalCreateRoutineCheckbox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalAlterRoutineCheckbox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalCreateUserCheckbox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalEventCheckbox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalTriggerCheckbox, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SelectDB)
                    .addComponent(jLabel8)
                    .addComponent(dbListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        DBListPanelLayout.setVerticalGroup(
            DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBListPanelLayout.createSequentialGroup()
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GlobalPrivilegeLabel)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DBListPanelLayout.createSequentialGroup()
                        .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HostComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddHostButton)
                            .addComponent(RemoveHostButton)
                            .addComponent(ChangePasswordButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DBListPanelLayout.createSequentialGroup()
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalSelectCheckbox)
                                    .addComponent(GlobalGrantCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalInsertCheckbox)
                                    .addComponent(GlobalReferencesCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalUpdateCheckbox)
                                    .addComponent(GlobalIndexCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalDeleteCheckbox)
                                    .addComponent(GlobalAlterCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalCreateCheckbox)
                                    .addComponent(GlobalShowDbCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalDropCheckbox)
                                    .addComponent(GlobalSuperCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalReloadCheckbox)
                                    .addComponent(GlobalCreateTmpTableCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalShutdownCheckbox)
                                    .addComponent(GlobalLockTablesCheckbox)))
                            .addGroup(DBListPanelLayout.createSequentialGroup()
                                .addComponent(GlobalReplClientCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GlobalCreateViewCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GlobalShowViewCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GlobalCreateRoutineCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GlobalAlterRoutineCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GlobalCreateUserCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GlobalEventCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GlobalTriggerCheckbox)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GlobalProcessCheckbox)
                            .addComponent(GlobalExecuteCheckbox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GlobalFileCheckbox)
                            .addComponent(GlobalReplSlaveCheckbox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addComponent(GlobalPrivilegeSubmitButton))
                    .addComponent(dbListPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DBListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backToUsers)
                    .addComponent(SelectDB)))
        );

        AddHostPanel.setName("AddHostPanel"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        AddHostNameField.setText(resourceMap.getString("AddHostNameField.text")); // NOI18N
        AddHostNameField.setName("AddHostNameField"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        AddHostPassField.setText(resourceMap.getString("AddHostPassField.text")); // NOI18N
        AddHostPassField.setName("AddHostPassField"); // NOI18N

        javax.swing.GroupLayout AddHostPanelLayout = new javax.swing.GroupLayout(AddHostPanel);
        AddHostPanel.setLayout(AddHostPanelLayout);
        AddHostPanelLayout.setHorizontalGroup(
            AddHostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddHostPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddHostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddHostNameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addGroup(AddHostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddHostPassField)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        AddHostPanelLayout.setVerticalGroup(
            AddHostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddHostPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddHostNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddHostPassField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        DBPanel.setMaximumSize(new java.awt.Dimension(767, 767));
        DBPanel.setName("DBPanel"); // NOI18N
        DBPanel.setPreferredSize(new java.awt.Dimension(535, 497));

        BackToDBs.setText(resourceMap.getString("BackToDBs.text")); // NOI18N
        BackToDBs.setName("BackToDBs"); // NOI18N
        BackToDBs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDBsActionPerformed(evt);
            }
        });

        DBPanelTitle.setText(resourceMap.getString("DBPanelTitle.text")); // NOI18N
        DBPanelTitle.setName("DBPanelTitle"); // NOI18N

        DBUpdatePriv.setText(resourceMap.getString("DBUpdatePriv.text")); // NOI18N
        DBUpdatePriv.setName("DBUpdatePriv"); // NOI18N

        DBSelect.setText(resourceMap.getString("DBSelect.text")); // NOI18N
        DBSelect.setName("DBSelect"); // NOI18N

        DBInsert.setText(resourceMap.getString("DBInsert.text")); // NOI18N
        DBInsert.setName("DBInsert"); // NOI18N

        DBDelete.setText(resourceMap.getString("DBDelete.text")); // NOI18N
        DBDelete.setName("DBDelete"); // NOI18N

        DBUpdate.setText(resourceMap.getString("DBUpdate.text")); // NOI18N
        DBUpdate.setName("DBUpdate"); // NOI18N

        DBCreate.setText(resourceMap.getString("DBCreate.text")); // NOI18N
        DBCreate.setName("DBCreate"); // NOI18N

        DBDrop.setText(resourceMap.getString("DBDrop.text")); // NOI18N
        DBDrop.setName("DBDrop"); // NOI18N

        DBGrant.setText(resourceMap.getString("DBGrant.text")); // NOI18N
        DBGrant.setName("DBGrant"); // NOI18N

        DBIndex.setText(resourceMap.getString("DBIndex.text")); // NOI18N
        DBIndex.setName("DBIndex"); // NOI18N

        DBAlter.setText(resourceMap.getString("DBAlter.text")); // NOI18N
        DBAlter.setName("DBAlter"); // NOI18N

        DBCreateTempTables.setText(resourceMap.getString("DBCreateTempTables.text")); // NOI18N
        DBCreateTempTables.setName("DBCreateTempTables"); // NOI18N

        DBShowView.setText(resourceMap.getString("DBShowView.text")); // NOI18N
        DBShowView.setName("DBShowView"); // NOI18N

        DBCreateRoutine.setText(resourceMap.getString("DBCreateRoutine.text")); // NOI18N
        DBCreateRoutine.setName("DBCreateRoutine"); // NOI18N

        DBAlterRoutine.setText(resourceMap.getString("DBAlterRoutine.text")); // NOI18N
        DBAlterRoutine.setName("DBAlterRoutine"); // NOI18N

        DBExecute.setText(resourceMap.getString("DBExecute.text")); // NOI18N
        DBExecute.setName("DBExecute"); // NOI18N

        DBCreateView.setText(resourceMap.getString("DBCreateView.text")); // NOI18N
        DBCreateView.setName("DBCreateView"); // NOI18N

        DBEvent.setText(resourceMap.getString("DBEvent.text")); // NOI18N
        DBEvent.setName("DBEvent"); // NOI18N

        DBTrigger.setText(resourceMap.getString("DBTrigger.text")); // NOI18N
        DBTrigger.setName("DBTrigger"); // NOI18N

        DBLockTables.setText(resourceMap.getString("DBLockTables.text")); // NOI18N
        DBLockTables.setName("DBLockTables"); // NOI18N

        DBReferences.setText(resourceMap.getString("DBReferences.text")); // NOI18N
        DBReferences.setName("DBReferences"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        DBTablesList.setModel(new javax.swing.AbstractListModel() {
            ArrayList<String> strings = tables;
            public int getSize() { return strings.size(); }
            public Object getElementAt(int i) { return strings.get(i); }
        });
        DBTablesList.setName("DBTablesList"); // NOI18N
        jScrollPane2.setViewportView(DBTablesList);

        DBTableListLabel.setText(resourceMap.getString("DBTableListLabel.text")); // NOI18N
        DBTableListLabel.setName("DBTableListLabel"); // NOI18N

        EditTablePrivs.setText(resourceMap.getString("EditTablePrivs.text")); // NOI18N
        EditTablePrivs.setName("EditTablePrivs"); // NOI18N

        javax.swing.GroupLayout DBPanelLayout = new javax.swing.GroupLayout(DBPanel);
        DBPanel.setLayout(DBPanelLayout);
        DBPanelLayout.setHorizontalGroup(
            DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DBPanelTitle)
                            .addGroup(DBPanelLayout.createSequentialGroup()
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DBGrant)
                                    .addComponent(DBDelete)
                                    .addComponent(DBUpdate)
                                    .addComponent(DBInsert)
                                    .addComponent(DBSelect)
                                    .addComponent(DBLockTables)
                                    .addComponent(DBReferences))
                                .addGap(1, 1, 1)
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DBCreateTempTables)
                                    .addComponent(DBDrop)
                                    .addComponent(DBIndex)
                                    .addComponent(DBAlter)
                                    .addComponent(DBCreate)
                                    .addComponent(DBShowView)
                                    .addComponent(DBCreateRoutine)
                                    .addComponent(DBAlterRoutine)
                                    .addComponent(DBExecute)
                                    .addComponent(DBCreateView)
                                    .addComponent(DBEvent)
                                    .addComponent(DBTrigger))))
                        .addGap(44, 44, 44)
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DBTableListLabel)
                            .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(EditTablePrivs)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addComponent(BackToDBs)
                        .addGap(18, 18, 18)
                        .addComponent(DBUpdatePriv)))
                .addContainerGap(1937, Short.MAX_VALUE))
        );
        DBPanelLayout.setVerticalGroup(
            DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DBPanelTitle)
                    .addComponent(DBTableListLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DBSelect)
                            .addComponent(DBCreate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DBInsert)
                            .addComponent(DBAlter))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DBDelete)
                            .addComponent(DBIndex))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DBUpdate)
                            .addComponent(DBDrop))
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DBPanelLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(DBGrant)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBLockTables)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBReferences))
                            .addGroup(DBPanelLayout.createSequentialGroup()
                                .addComponent(DBCreateTempTables)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBShowView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBCreateRoutine)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBAlterRoutine)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBExecute)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBCreateView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBEvent)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DBTrigger)))
                        .addGap(27, 27, 27)
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BackToDBs)
                            .addComponent(DBUpdatePriv)))
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EditTablePrivs)))
                .addGap(121, 121, 121))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void ConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectButtonActionPerformed
        connect();
    }

    private void updateUsers() {
        try {
            Statement getUsers = connection.createStatement();
            ResultSet userSet = getUsers.executeQuery("SELECT DISTINCT User FROM `user` ORDER BY User");
            ArrayList<String> userList = new ArrayList<String>();
            while (userSet.next()) {
                userList.add(userSet.getString("User"));
            }
            users = userList;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "updateUsers: " + ex.getMessage());
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

        // Switch to database panel
        UserListPanel.setVisible(false);
//        statusMessageLabel.setText("Editing User: "+editUser);
        setComponent(DBListPanel);
        getFrame().setMinimumSize(new Dimension(800, 450));
        getFrame().setSize(new Dimension(800, 450));
        updateUsersHosts();
        updateGlobalPrivileges();
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
        updateUsersHosts();
        if (!editUser.equals("root") && !editUser.isEmpty()) {
            int canDel = JOptionPane.showConfirmDialog(UserListPanel, "Delete User: " + editUser + "?", "Delete " + editUser, JOptionPane.YES_NO_OPTION);
            if (canDel == JOptionPane.YES_OPTION) {
                try {
                    Statement update;
                    //Check for username in database already
                    int ret = 0;
                    for (String host : hosts) {
                        update = connection.createStatement();
                        ret += update.executeUpdate("DROP USER '" + cleanSQL(editUser) + "'@'" + cleanSQL(host) + "'");
                    }
                    if (ret == 0) {
                        JOptionPane.showMessageDialog(UserListPanel, "User Deleted.");
                    } else {
                        JOptionPane.showMessageDialog(UserListPanel, "User Not Deleted.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(UserListPanel, "DeleteUserButtonActionPerformed: " + ex.getMessage());
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
        if (added && host.isEmpty()) {
            JOptionPane.showMessageDialog(AddUserPanel, "Host is blank.");
            added = false;
        }
        if (added && pass.isEmpty()) {
            JOptionPane.showMessageDialog(AddUserPanel, "Password is blank.");
            added = false;
        }
        try {
            Statement query = connection.createStatement();
            ResultSet checkName = query.executeQuery("SELECT DISTINCT User FROM `user` WHERE User = '" + cleanSQL(username) + "'");
            if (checkName.next()) {
                JOptionPane.showMessageDialog(AddUserPanel, "Username \"" + username + "\" already exists.");
                added = false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(AddUserPanel, "SubmitAddUserActionPerformed: " + ex.getMessage());
            added = false;
        }
        if (added) {
            try {
                Statement update = connection.createStatement();

                //Check for username in database already
                int ret = update.executeUpdate("CREATE USER '" + cleanSQL(username) + "'@'" + cleanSQL(host) + "' IDENTIFIED BY '" + cleanSQL(pass) + "'");
                if (ret == 0) {
                    JOptionPane.showMessageDialog(AddUserPanel, "User Added.");
                    added = true;
                } else {
                    JOptionPane.showMessageDialog(AddUserPanel, "User Not Added.");
                    added = false;
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(AddUserPanel, "SubmitAddUserActionPerformed: " + ex);
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
    DBListPanel.setVisible(false);
    setComponent(UserListPanel);
    UserListPanel.setVisible(true);
}//GEN-LAST:event_backToUsersActionPerformed

private void HostComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HostComboBoxActionPerformed
    updateGlobalPrivileges();
}//GEN-LAST:event_HostComboBoxActionPerformed

private void AddHostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddHostButtonActionPerformed


    JTextField hostname = new JTextField();
    JPasswordField pass = new JPasswordField();
    final JComponent[] inputs = new JComponent[]{
        new JLabel("Enter Hostname:"),
        hostname,
        new JLabel("Enter Password:"),
        pass
    };
    int cancel = 0;
    Boolean added = false;
    while (cancel != JOptionPane.CANCEL_OPTION && cancel != JOptionPane.CLOSED_OPTION && !added) {
        added = true;
        cancel = JOptionPane.showConfirmDialog(null, inputs, "Add Host", JOptionPane.OK_CANCEL_OPTION);

        if (cancel != JOptionPane.OK_OPTION) {//Cancel input
            added = false;
            break;
        }

        if (added && (hostname.getText() == null || hostname.getText().isEmpty())) {
            JOptionPane.showMessageDialog(AddUserPanel, "Hostname Empty.");
            added = false;
        }
        if (added && (pass.getText() == null || pass.getText().isEmpty())) {
            JOptionPane.showMessageDialog(AddUserPanel, "Password Empty.");
            added = false;
        }

    }
    try {
        Statement query = connection.createStatement();
        ResultSet checkName = query.executeQuery("SELECT DISTINCT Host FROM `user` WHERE User = '" + cleanSQL(editUser) + "' AND Host ='" + cleanSQL(hostname.getText()) + "'");
        if (checkName.next()) {
            JOptionPane.showMessageDialog(AddUserPanel, editUser + " already has host \"" + hostname.getText() + "\".");
            added = false;
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(AddUserPanel, "SubmitAddUserActionPerformed: " + ex.getMessage());
        added = false;
    }
    if (added) {
        try {
            Statement update = connection.createStatement();
//            int ret = 0;
            int ret = update.executeUpdate("CREATE USER '" + cleanSQL(editUser) + "'@'" + cleanSQL(hostname.getText()) + "' IDENTIFIED BY '" + cleanSQL(pass.getText()) + "'");
            if (ret == 0) {
                JOptionPane.showMessageDialog(AddUserPanel, "Host Added.");
                updateUsersHosts();
                updateGlobalPrivileges();
                added = true;
            } else {
                JOptionPane.showMessageDialog(AddUserPanel, "Host Not Added.");
                added = false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(AddUserPanel, "AddHostButtonActionPerformed: " + ex.getMessage());
            added = false;
        }
    }
}//GEN-LAST:event_AddHostButtonActionPerformed

private void RemoveHostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveHostButtonActionPerformed
    editHost = HostComboBox.getSelectedItem().toString();
    if (!editUser.isEmpty() && !editHost.isEmpty()) {
        int canDel = JOptionPane.showConfirmDialog(UserListPanel, "Delete Host: " + editHost + "?", "Delete " + editHost, JOptionPane.YES_NO_OPTION);
        if (canDel == JOptionPane.YES_OPTION) {
            try {
                Statement query = connection.createStatement();
                ResultSet hostQuery = query.executeQuery("SELECT DISTINCT Host FROM `user` WHERE User = '" + cleanSQL(editUser) + "'");
                hostQuery.last();
                if (hostQuery.getRow() > 1) {
                    Statement update = connection.createStatement();
                    int ret = 0;
                    ret += update.executeUpdate("DROP USER '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
                    if (ret == 0) {
                        JOptionPane.showMessageDialog(UserListPanel, "Host Deleted.");
                    } else {
                        JOptionPane.showMessageDialog(UserListPanel, "Host Not Deleted.");
                    }
                } else {
                    JOptionPane.showMessageDialog(UserListPanel, "Only 1 host left for this user.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(UserListPanel, "RemoveHostButtonActionPerformed: " + ex.getMessage());
            }
        }
    }
    //Refresh user list
    updateUsersHosts();
}//GEN-LAST:event_RemoveHostButtonActionPerformed

private void ChangePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangePasswordButtonActionPerformed
    editHost = HostComboBox.getSelectedItem().toString();
    if (!editUser.isEmpty() && !editHost.isEmpty()) {
        JPasswordField pass = new JPasswordField();
        final JComponent[] inputs = new JComponent[]{
            new JLabel("Enter Password:"),
            pass
        };
        int cancel = JOptionPane.showConfirmDialog(null, inputs, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (cancel == JOptionPane.YES_OPTION) {
            if (pass.getText() != null && !pass.getText().isEmpty()) {
                try {



                    Statement update = connection.createStatement();
                    int ret = 0;
                    ret += update.executeUpdate("SET PASSWORD FOR '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "' = PASSWORD('" + cleanSQL(pass.getText()) + "')");
                    if (ret == 0) {
                        JOptionPane.showMessageDialog(UserListPanel, "Password Updated.");
                    } else {
                        JOptionPane.showMessageDialog(UserListPanel, "Password Not Updated.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(UserListPanel, "ChangePasswordButtonActionPerformed: " + ex.getMessage());
                }
            }
        }
    }
}//GEN-LAST:event_ChangePasswordButtonActionPerformed

private void GlobalPrivilegeSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GlobalPrivilegeSubmitButtonActionPerformed
    editHost = HostComboBox.getSelectedItem().toString();
    String grants = "";
    String revokes = "";

    if (GlobalSelectCheckbox.isSelected()) {
        grants += " SELECT ";
    } else {
        revokes += " SELECT ";
    }

    if (GlobalInsertCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " INSERT ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " INSERT ";
    }

    if (GlobalUpdateCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " UPDATE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " UPDATE ";
    }

    if (GlobalDeleteCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " DELETE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " DELETE ";
    }

    if (GlobalCreateCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " CREATE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " CREATE ";
    }

    if (GlobalDropCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " DROP ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " DROP ";
    }

    if (GlobalReloadCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " RELOAD ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " RELOAD ";
    }

    if (GlobalShutdownCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " SHUTDOWN ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " SHUTDOWN ";
    }

    if (GlobalProcessCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " PROCESS ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " PROCESS ";
    }

    if (GlobalFileCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " FILE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " FILE ";
    }

    if (GlobalGrantCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " GRANT OPTION ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " GRANT OPTION ";
    }

    if (GlobalReferencesCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " REFERENCES ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " REFERENCES ";
    }

    if (GlobalIndexCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " INDEX ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " INDEX ";
    }

    if (GlobalAlterCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " ALTER ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " ALTER ";
    }

    if (GlobalShowDbCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " SHOW DATABASES ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " SHOW DATABASES ";
    }

    if (GlobalSuperCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " SUPER ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " SUPER ";
    }

    if (GlobalCreateTmpTableCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " CREATE TEMPORARY TABLES ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " CREATE TEMPORARY TABLES ";
    }

    if (GlobalLockTablesCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " LOCK TABLES ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " LOCK TABLES ";
    }

    if (GlobalExecuteCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " EXECUTE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " EXECUTE ";
    }

    if (GlobalReplSlaveCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " REPLICATION SLAVE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " REPLICATION SLAVE ";
    }

    if (GlobalReplClientCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " REPLICATION CLIENT ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " REPLICATION CLIENT ";
    }

    if (GlobalCreateViewCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " CREATE VIEW ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " CREATE VIEW ";
    }

    if (GlobalShowViewCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " SHOW VIEW ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " SHOW VIEW ";
    }

    if (GlobalCreateRoutineCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " CREATE ROUTINE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " CREATE ROUTINE ";
    }

    if (GlobalAlterRoutineCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " ALTER ROUTINE ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " ALTER ROUTINE ";
    }

    if (GlobalCreateUserCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " CREATE USER ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " CREATE USER ";
    }

    if (GlobalEventCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " EVENT ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " EVENT ";
    }

    if (GlobalTriggerCheckbox.isSelected()) {
        if (!grants.isEmpty()) {
            grants += ",";
        }
        grants += " TRIGGER ";
    } else {
        if (!revokes.isEmpty()) {
            revokes += ",";
        }
        revokes += " TRIGGER ";
    }

//    System.out.println("GRANT " + grants + " ON *.* TO '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
//    System.out.println("REVOKE " + revokes + " ON *.* FROM '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");

    try {
        Statement update;
        int ret = 0;
        if (!grants.isEmpty()) {
            update = connection.createStatement();
            ret += update.executeUpdate("GRANT " + grants + " ON *.* TO '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
        }
        if (!revokes.isEmpty()) {
            update = connection.createStatement();
            ret += update.executeUpdate("REVOKE " + revokes + " ON *.* FROM '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
        }
        if (ret == 0) {
            JOptionPane.showMessageDialog(UserListPanel, "Privileges Updated.");
        } else {
            JOptionPane.showMessageDialog(UserListPanel, "Privileges Not Updated.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(UserListPanel, "GlobalPrivilegeSubmitButtonActionPerformed: " + ex.getMessage());
    }

}//GEN-LAST:event_GlobalPrivilegeSubmitButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddHostButton;
    private javax.swing.JTextField AddHostNameField;
    private javax.swing.JPanel AddHostPanel;
    private javax.swing.JPasswordField AddHostPassField;
    private javax.swing.JButton AddUserButton;
    private javax.swing.JButton AddUserCancel;
    private javax.swing.JTextField AddUserHost;
    private javax.swing.JTextField AddUserName;
    private javax.swing.JPanel AddUserPanel;
    private javax.swing.JPasswordField AddUserPass;
    private javax.swing.JButton ChangePasswordButton;
    private javax.swing.JButton BackToDBs;
    private javax.swing.JButton ConnectButton;
    private javax.swing.JCheckBox DBAlter;
    private javax.swing.JCheckBox DBAlterRoutine;
    private javax.swing.JCheckBox DBCreate;
    private javax.swing.JCheckBox DBCreateRoutine;
    private javax.swing.JCheckBox DBCreateTempTables;
    private javax.swing.JCheckBox DBCreateView;
    private javax.swing.JCheckBox DBDelete;
    private javax.swing.JCheckBox DBDrop;
    private javax.swing.JCheckBox DBEvent;
    private javax.swing.JCheckBox DBExecute;
    private javax.swing.JCheckBox DBGrant;
    private javax.swing.JCheckBox DBIndex;
    private javax.swing.JCheckBox DBInsert;
    private javax.swing.JPanel DBListPanel;
    private javax.swing.JCheckBox DBLockTables;
    private javax.swing.JPanel DBPanel;
    private javax.swing.JLabel DBPanelTitle;
    private javax.swing.JCheckBox DBReferences;
    private javax.swing.JCheckBox DBSelect;
    private javax.swing.JCheckBox DBShowView;
    private javax.swing.JLabel DBTableListLabel;
    private javax.swing.JList DBTablesList;
    private javax.swing.JCheckBox DBTrigger;
    private javax.swing.JCheckBox DBUpdate;
    private javax.swing.JButton DBUpdatePriv;
    private javax.swing.JButton DeleteUserButton;
    private javax.swing.JButton EditTablePrivs;
    private javax.swing.JButton EditUserButton;
    private javax.swing.JCheckBox GlobalAlterCheckbox;
    private javax.swing.JCheckBox GlobalAlterRoutineCheckbox;
    private javax.swing.JCheckBox GlobalCreateCheckbox;
    private javax.swing.JCheckBox GlobalCreateRoutineCheckbox;
    private javax.swing.JCheckBox GlobalCreateTmpTableCheckbox;
    private javax.swing.JCheckBox GlobalCreateUserCheckbox;
    private javax.swing.JCheckBox GlobalCreateViewCheckbox;
    private javax.swing.JCheckBox GlobalDeleteCheckbox;
    private javax.swing.JCheckBox GlobalDropCheckbox;
    private javax.swing.JCheckBox GlobalEventCheckbox;
    private javax.swing.JCheckBox GlobalExecuteCheckbox;
    private javax.swing.JCheckBox GlobalFileCheckbox;
    private javax.swing.JCheckBox GlobalGrantCheckbox;
    private javax.swing.JCheckBox GlobalIndexCheckbox;
    private javax.swing.JCheckBox GlobalInsertCheckbox;
    private javax.swing.JCheckBox GlobalLockTablesCheckbox;
    private javax.swing.JLabel GlobalPrivilegeLabel;
    private javax.swing.JButton GlobalPrivilegeSubmitButton;
    private javax.swing.JCheckBox GlobalProcessCheckbox;
    private javax.swing.JCheckBox GlobalReferencesCheckbox;
    private javax.swing.JCheckBox GlobalReloadCheckbox;
    private javax.swing.JCheckBox GlobalReplClientCheckbox;
    private javax.swing.JCheckBox GlobalReplSlaveCheckbox;
    private javax.swing.JCheckBox GlobalSelectCheckbox;
    private javax.swing.JCheckBox GlobalShowDbCheckbox;
    private javax.swing.JCheckBox GlobalShowViewCheckbox;
    private javax.swing.JCheckBox GlobalShutdownCheckbox;
    private javax.swing.JCheckBox GlobalSuperCheckbox;
    private javax.swing.JCheckBox GlobalTriggerCheckbox;
    private javax.swing.JCheckBox GlobalUpdateCheckbox;
    private javax.swing.JComboBox HostComboBox;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JButton RemoveHostButton;
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables
    private Connection connection;
    private String editServer;
    private ArrayList<String> users;
    private String editUser;
    private ArrayList<String> hosts;
    private String editHost;
    private ArrayList<String> databases;
    private String editDatabase;
    private ArrayList<String> tables;

    private void updateGlobalPrivileges() {
        try {

            editHost = (String) HostComboBox.getSelectedItem();

            GlobalPrivilegeLabel.setText("Global Privileges for " + editUser);
            Statement globPriv = connection.createStatement();
            ResultSet privs = globPriv.executeQuery("SELECT * FROM mysql.`user` WHERE User = '" + cleanSQL(editUser) + "' AND Host = '" + cleanSQL(editHost) + "'");
            privs.next();

            GlobalSelectCheckbox.setSelected(privs.getBoolean("Select_priv"));
            GlobalInsertCheckbox.setSelected(privs.getBoolean("Insert_priv"));
            GlobalUpdateCheckbox.setSelected(privs.getBoolean("Update_priv"));
            GlobalDeleteCheckbox.setSelected(privs.getBoolean("Delete_priv"));
            GlobalCreateCheckbox.setSelected(privs.getBoolean("Create_priv"));
            GlobalDropCheckbox.setSelected(privs.getBoolean("Drop_priv"));
            GlobalReloadCheckbox.setSelected(privs.getBoolean("Reload_priv"));
            GlobalShutdownCheckbox.setSelected(privs.getBoolean("Shutdown_priv"));
            GlobalProcessCheckbox.setSelected(privs.getBoolean("Process_priv"));
            GlobalFileCheckbox.setSelected(privs.getBoolean("File_priv"));
            GlobalGrantCheckbox.setSelected(privs.getBoolean("Grant_priv"));
            GlobalReferencesCheckbox.setSelected(privs.getBoolean("References_priv"));
            GlobalIndexCheckbox.setSelected(privs.getBoolean("Index_priv"));
            GlobalAlterCheckbox.setSelected(privs.getBoolean("Alter_priv"));
            GlobalShowDbCheckbox.setSelected(privs.getBoolean("Show_db_priv"));
            GlobalSuperCheckbox.setSelected(privs.getBoolean("Super_priv"));
            GlobalCreateTmpTableCheckbox.setSelected(privs.getBoolean("Create_tmp_table_priv"));
            GlobalLockTablesCheckbox.setSelected(privs.getBoolean("Lock_Tables_priv"));
            GlobalExecuteCheckbox.setSelected(privs.getBoolean("Execute_priv"));
            GlobalReplSlaveCheckbox.setSelected(privs.getBoolean("Repl_slave_priv"));
            GlobalReplClientCheckbox.setSelected(privs.getBoolean("Repl_client_priv"));
            GlobalCreateViewCheckbox.setSelected(privs.getBoolean("Create_view_priv"));
            GlobalShowViewCheckbox.setSelected(privs.getBoolean("Show_view_priv"));
            GlobalCreateRoutineCheckbox.setSelected(privs.getBoolean("Create_routine_priv"));
            GlobalAlterRoutineCheckbox.setSelected(privs.getBoolean("Alter_routine_priv"));
            GlobalCreateUserCheckbox.setSelected(privs.getBoolean("Create_user_priv"));
            GlobalEventCheckbox.setSelected(privs.getBoolean("Event_priv"));
            GlobalTriggerCheckbox.setSelected(privs.getBoolean("Trigger_priv"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(UserListPanel, "updateGlobalPrivileges: " + ex.getMessage());
        }
    }

    public static String cleanSQL(String arg) {
        String rtn = arg.replace("\\", "\\\\");
        return rtn.replace("'", "''");
    }

    private void updateUsersHosts() {
        try {
            Statement userHosts = connection.createStatement();
            ResultSet hostList = userHosts.executeQuery("SELECT Host FROM mysql.`user` WHERE User = '" + cleanSQL(editUser) + "' ORDER BY Host");
            ArrayList<String> currHosts = new ArrayList<String>();
            while (hostList.next()) {
                currHosts.add(hostList.getString("Host"));
            }
            hosts = currHosts;
            HostComboBox.setModel(new javax.swing.DefaultComboBoxModel(hosts.toArray()));
            HostComboBox.setSelectedIndex(0);
            editHost = (String) HostComboBox.getSelectedItem();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(DBListPanel, "UpdateUsersHosts: " + ex.getMessage());
        }
    }

	private void SelectDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectDBActionPerformed

		// TODO add your handling code here:
		editDatabase = getDBListValue();
		DBPanelTitle.setText(editUser + "'s Privileges on " + editDatabase);
		DBTableListLabel.setText(editDatabase + "'s Tables");
		getTables();
		getDBPrivs();

		DBListPanel.setVisible(false);
		setComponent(DBPanel);
		DBPanel.setVisible(true);
	}//GEN-LAST:event_SelectDBActionPerformed

	private void BackToDBsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDBsActionPerformed
            // TODO add your handling code here:
            editDatabase = "";

            DBPanel.setVisible(false);
            setComponent(DBListPanel);

            getFrame().setMinimumSize(new Dimension(800, 450));
            getFrame().setSize(new Dimension(800, 450));
            DBListPanel.setVisible(true);
	}//GEN-LAST:event_BackToDBsActionPerformed
}
