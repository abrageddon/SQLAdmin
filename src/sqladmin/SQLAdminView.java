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
        columns = new ArrayList<String>();

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
            getFrame().setMinimumSize(UsersWindow);
            getFrame().setSize(UsersWindow);
            editServer = server;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Connect: " + ex.getMessage());
        }
    }

    private void getDatabases() {
        try {
            Statement getDatabases = connection.createStatement();
            ResultSet dbSet = getDatabases.executeQuery("show databases");
            databases.clear();
            while (dbSet.next()) {
                databases.add(dbSet.getString(1));
            }
            databaseList.setModel(new javax.swing.AbstractListModel() {

                ArrayList<String> strings = databases;

                public int getSize() {
                    return strings.size();
                }

                public Object getElementAt(int i) {
                    return strings.get(i);
                }
            });
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
        editHost = DbHostCombobox.getSelectedItem().toString();
        try {
            Statement updateDBPrivs = connection.createStatement();
            ResultSet DBPrivs = updateDBPrivs.executeQuery("SELECT * from mysql.db WHERE user='" + cleanSQL(editUser) + "' AND host='" + cleanSQL(editHost) + "' AND db='" + cleanSQL(editDatabase) + "';");

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

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(UserListPanel, "getDBPrivs: " + ex.getMessage());
        }
    }

    private void getTables() {
        try {
            Statement getTables = connection.createStatement();
            ResultSet tableSet = getTables.executeQuery("SHOW TABLES IN " + cleanSQL(editDatabase));
            tables.clear();
            while (tableSet.next()) {
                tables.add(tableSet.getString(1));
            }
//            if (tables.isEmpty()) {
//                tables.add("No tables.");
//            }
            DBTablesList.setModel(new javax.swing.AbstractListModel() {

                ArrayList<String> strings = tables;

                public int getSize() {
                    return strings.size();
                }

                public Object getElementAt(int i) {
                    return strings.get(i);
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(UserListPanel, "getTables: " + ex.getMessage());
        }
    }

    private void getColumns() {
        try {
            Statement getTables = connection.createStatement();
            ResultSet tableSet = getTables.executeQuery("SHOW COLUMNS FROM " + cleanSQL(editDatabase) + "." + cleanSQL(editTable));
            columns.clear();
            while (tableSet.next()) {
                columns.add(tableSet.getString(1));
            }
//            if (columns.isEmpty()) {
//                columns.add("No columns.");
//            }
            ColumnNameList.setModel(new javax.swing.AbstractListModel() {

                ArrayList<String> strings = columns;

                public int getSize() {
                    return strings.size();
                }

                public Object getElementAt(int i) {
                    return strings.get(i);
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(UserListPanel, "getColumns: " + ex.getMessage());
        }
    }

    private String getUserListValue() {
        if (UserListjList.getSelectedIndex() != -1) {
            return UserListjList.getSelectedValue().toString();
        }
        return "";
    }

    private String getTableListValue() {
        if (DBTablesList.getSelectedIndex() != -1) {
            return DBTablesList.getSelectedValue().toString();
        }
        return "";
    }
	
	private String getColumnListValue() {
		if (ColumnNameList.getSelectedIndex() != -1) {
			return ColumnNameList.getSelectedValue().toString();
		} else {
			return "";
		}
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
        RenameUserButton = new javax.swing.JButton();
        AddUserPanel = new javax.swing.JPanel();
        SubmitAddUser = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        AddUserName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        AddUserHost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        AddUserPass = new javax.swing.JPasswordField();
        AddUserCancel = new javax.swing.JButton();
        GlobalPanel = new javax.swing.JPanel();
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
        ShowGrantsButton = new javax.swing.JButton();
        QueriesPerHour = new javax.swing.JTextField();
        ConnectionsPerHour = new javax.swing.JTextField();
        UpdatesPerHour = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        UserConnections = new javax.swing.JTextField();
        DBPanel = new javax.swing.JPanel();
        BackToGlobalButton = new javax.swing.JButton();
        DBPanelTitle = new javax.swing.JLabel();
        ApplyDBPrivButton = new javax.swing.JButton();
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
        DbHostCombobox = new javax.swing.JComboBox();
        TablePanel = new javax.swing.JPanel();
        TablePanelLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ColumnNameList = new javax.swing.JList();
        TableSelectCheckbox = new javax.swing.JCheckBox();
        TableInsertCheckbox = new javax.swing.JCheckBox();
        TableUpdateCheckbox = new javax.swing.JCheckBox();
        TableDeleteCheckbox = new javax.swing.JCheckBox();
        TableCreateCheckbox = new javax.swing.JCheckBox();
        TableHostCombobox = new javax.swing.JComboBox();
        TableDropCheckbox = new javax.swing.JCheckBox();
        TableGrantCheckbox = new javax.swing.JCheckBox();
        TableReferencesCheckbox = new javax.swing.JCheckBox();
        TableIndexCheckbox = new javax.swing.JCheckBox();
        TableAlterCheckbox = new javax.swing.JCheckBox();
        TableCreateViewCheckbox = new javax.swing.JCheckBox();
        TableShowViewCheckbox = new javax.swing.JCheckBox();
        TableTriggerCheckbox = new javax.swing.JCheckBox();
        EditColumnButton = new javax.swing.JButton();
        BackToDatabaseButton = new javax.swing.JButton();
        ApplyTablePrivsButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        ColumnPanel = new javax.swing.JPanel();
        ColPanelLabel = new javax.swing.JLabel();
        ColSelect = new javax.swing.JCheckBox();
        ColInsert = new javax.swing.JCheckBox();
        ColUpdate = new javax.swing.JCheckBox();
        ColReferences = new javax.swing.JCheckBox();
        BackToTables = new javax.swing.JButton();
        UpdateColPrivs = new javax.swing.JButton();

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
                    .addComponent(ServerField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(UserField, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(PasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
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

        UserListPanel.setMinimumSize(new java.awt.Dimension(403, 265));
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

        RenameUserButton.setText(resourceMap.getString("RenameUserButton.text")); // NOI18N
        RenameUserButton.setName("RenameUserButton"); // NOI18N
        RenameUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RenameUserButtonActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AddUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(DeleteUserButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                    .addGroup(UserListPanelLayout.createSequentialGroup()
                        .addComponent(RenameUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                        .addComponent(EditUserButton)))
                .addContainerGap())
        );
        UserListPanelLayout.setVerticalGroup(
            UserListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(AddUserButton)
                    .addComponent(DeleteUserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RenameUserButton)
                    .addComponent(EditUserButton))
                .addContainerGap())
        );

        AddUserPanel.setMinimumSize(new java.awt.Dimension(353, 271));
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
                    .addComponent(AddUserPass, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddUserPanelLayout.createSequentialGroup()
                        .addComponent(AddUserCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                        .addComponent(SubmitAddUser))
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(AddUserHost, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                    .addComponent(AddUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(AddUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SubmitAddUser)
                    .addComponent(AddUserCancel))
                .addContainerGap())
        );

        GlobalPanel.setMinimumSize(new java.awt.Dimension(759, 415));
        GlobalPanel.setName("GlobalPanel"); // NOI18N

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

        ShowGrantsButton.setText(resourceMap.getString("ShowGrantsButton.text")); // NOI18N
        ShowGrantsButton.setName("ShowGrantsButton"); // NOI18N
        ShowGrantsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowGrantsButtonActionPerformed(evt);
            }
        });

        QueriesPerHour.setText(resourceMap.getString("QueriesPerHour.text")); // NOI18N
        QueriesPerHour.setName("QueriesPerHour"); // NOI18N

        ConnectionsPerHour.setText(resourceMap.getString("ConnectionsPerHour.text")); // NOI18N
        ConnectionsPerHour.setName("ConnectionsPerHour"); // NOI18N

        UpdatesPerHour.setText(resourceMap.getString("UpdatesPerHour.text")); // NOI18N
        UpdatesPerHour.setName("UpdatesPerHour"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        UserConnections.setText(resourceMap.getString("UserConnections.text")); // NOI18N
        UserConnections.setName("UserConnections"); // NOI18N

        javax.swing.GroupLayout GlobalPanelLayout = new javax.swing.GroupLayout(GlobalPanel);
        GlobalPanel.setLayout(GlobalPanelLayout);
        GlobalPanelLayout.setHorizontalGroup(
            GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GlobalPanelLayout.createSequentialGroup()
                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GlobalPanelLayout.createSequentialGroup()
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GlobalSelectCheckbox)
                                    .addComponent(GlobalInsertCheckbox)
                                    .addComponent(GlobalDeleteCheckbox)
                                    .addComponent(GlobalCreateCheckbox)
                                    .addComponent(GlobalDropCheckbox)
                                    .addComponent(GlobalFileCheckbox))
                                .addGap(57, 57, 57)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GlobalReplSlaveCheckbox)
                                    .addComponent(GlobalGrantCheckbox)
                                    .addComponent(GlobalSuperCheckbox)
                                    .addComponent(GlobalShowDbCheckbox)
                                    .addComponent(GlobalAlterCheckbox)
                                    .addComponent(GlobalReferencesCheckbox)
                                    .addComponent(GlobalIndexCheckbox)
                                    .addComponent(GlobalLockTablesCheckbox)
                                    .addComponent(GlobalExecuteCheckbox)
                                    .addComponent(GlobalCreateTmpTableCheckbox)))
                            .addComponent(GlobalUpdateCheckbox)))
                    .addComponent(backToUsers)
                    .addComponent(GlobalPrivilegeLabel)
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GlobalReloadCheckbox))
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GlobalShutdownCheckbox))
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GlobalProcessCheckbox))
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(HostComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddHostButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RemoveHostButton))
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ShowGrantsButton)
                        .addGap(18, 18, 18)
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(QueriesPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(UpdatesPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ConnectionsPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(UserConnections, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalReplClientCheckbox)
                            .addComponent(GlobalCreateViewCheckbox)
                            .addComponent(GlobalShowViewCheckbox)
                            .addComponent(GlobalCreateRoutineCheckbox)
                            .addComponent(GlobalAlterRoutineCheckbox)
                            .addComponent(GlobalCreateUserCheckbox)
                            .addComponent(GlobalEventCheckbox)
                            .addComponent(GlobalTriggerCheckbox)
                            .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(GlobalPrivilegeSubmitButton)
                                .addComponent(ChangePasswordButton)))
                        .addGap(18, 18, 18)
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SelectDB, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dbListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jLabel8)))
        );
        GlobalPanelLayout.setVerticalGroup(
            GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GlobalPanelLayout.createSequentialGroup()
                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GlobalPrivilegeLabel)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GlobalPanelLayout.createSequentialGroup()
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HostComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddHostButton)
                            .addComponent(RemoveHostButton)
                            .addComponent(ChangePasswordButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(GlobalPanelLayout.createSequentialGroup()
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalSelectCheckbox)
                                    .addComponent(GlobalGrantCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalInsertCheckbox)
                                    .addComponent(GlobalReferencesCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalUpdateCheckbox)
                                    .addComponent(GlobalIndexCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalDeleteCheckbox)
                                    .addComponent(GlobalAlterCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalCreateCheckbox)
                                    .addComponent(GlobalShowDbCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalDropCheckbox)
                                    .addComponent(GlobalSuperCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalReloadCheckbox)
                                    .addComponent(GlobalCreateTmpTableCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GlobalShutdownCheckbox)
                                    .addComponent(GlobalLockTablesCheckbox)))
                            .addGroup(GlobalPanelLayout.createSequentialGroup()
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
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GlobalProcessCheckbox)
                            .addComponent(GlobalExecuteCheckbox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GlobalReplSlaveCheckbox)
                            .addComponent(GlobalFileCheckbox))
                        .addGap(6, 6, 6)
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GlobalPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(ShowGrantsButton))
                            .addGroup(GlobalPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(QueriesPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(UpdatesPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(ConnectionsPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(UserConnections, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(dbListPane, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GlobalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backToUsers)
                    .addComponent(SelectDB)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GlobalPanelLayout.createSequentialGroup()
                .addContainerGap(431, Short.MAX_VALUE)
                .addComponent(GlobalPrivilegeSubmitButton)
                .addContainerGap())
        );

        DBPanel.setMinimumSize(new java.awt.Dimension(590, 441));
        DBPanel.setName("DBPanel"); // NOI18N
        DBPanel.setPreferredSize(new java.awt.Dimension(535, 397));

        BackToGlobalButton.setText(resourceMap.getString("BackToGlobalButton.text")); // NOI18N
        BackToGlobalButton.setName("BackToGlobalButton"); // NOI18N
        BackToGlobalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToGlobalButtonActionPerformed(evt);
            }
        });

        DBPanelTitle.setText(resourceMap.getString("DBPanelTitle.text")); // NOI18N
        DBPanelTitle.setName("DBPanelTitle"); // NOI18N

        ApplyDBPrivButton.setText(resourceMap.getString("ApplyDBPrivButton.text")); // NOI18N
        ApplyDBPrivButton.setName("ApplyDBPrivButton"); // NOI18N
        ApplyDBPrivButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyDBPrivButtonActionPerformed(evt);
            }
        });

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
        EditTablePrivs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditTablePrivsActionPerformed(evt);
            }
        });

        DbHostCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        DbHostCombobox.setName("DbHostCombobox"); // NOI18N
        DbHostCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DbHostComboboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DBPanelLayout = new javax.swing.GroupLayout(DBPanel);
        DBPanel.setLayout(DBPanelLayout);
        DBPanelLayout.setHorizontalGroup(
            DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBPanelLayout.createSequentialGroup()
                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DBPanelTitle)
                            .addComponent(DbHostCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DBIndex)
                            .addGroup(DBPanelLayout.createSequentialGroup()
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DBSelect)
                                    .addComponent(DBInsert)
                                    .addComponent(DBDelete)
                                    .addComponent(DBUpdate)
                                    .addComponent(DBGrant)
                                    .addComponent(DBLockTables)
                                    .addComponent(DBReferences)
                                    .addComponent(DBCreate)
                                    .addComponent(DBAlter))
                                .addGap(43, 43, 43)
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DBTrigger)
                                    .addComponent(DBEvent)
                                    .addComponent(DBCreateView)
                                    .addComponent(DBExecute)
                                    .addComponent(DBAlterRoutine)
                                    .addComponent(DBCreateRoutine)
                                    .addComponent(DBShowView)
                                    .addComponent(DBCreateTempTables)
                                    .addComponent(DBDrop)
                                    .addComponent(ApplyDBPrivButton))))
                        .addGap(18, 18, 18)
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DBTableListLabel)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addComponent(BackToGlobalButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                        .addComponent(EditTablePrivs)))
                .addContainerGap())
        );
        DBPanelLayout.setVerticalGroup(
            DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBPanelLayout.createSequentialGroup()
                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DBPanelLayout.createSequentialGroup()
                                .addComponent(DBPanelTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DbHostCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DBPanelLayout.createSequentialGroup()
                                        .addComponent(DBInsert)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBDelete)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBUpdate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBGrant))
                                    .addGroup(DBPanelLayout.createSequentialGroup()
                                        .addComponent(DBCreateTempTables)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBShowView)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBCreateRoutine)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBAlterRoutine)))
                                .addGap(23, 23, 23)
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DBPanelLayout.createSequentialGroup()
                                        .addComponent(DBCreateView)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBEvent)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBTrigger)
                                        .addGap(35, 35, 35)
                                        .addComponent(ApplyDBPrivButton))
                                    .addGroup(DBPanelLayout.createSequentialGroup()
                                        .addComponent(DBReferences)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBCreate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBAlter)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DBIndex))))
                            .addGroup(DBPanelLayout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(DBSelect)
                                    .addComponent(DBDrop))
                                .addGap(92, 92, 92)
                                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DBLockTables)
                                    .addComponent(DBExecute))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE))
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(DBTableListLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addGroup(DBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BackToGlobalButton)
                    .addGroup(DBPanelLayout.createSequentialGroup()
                        .addComponent(EditTablePrivs)
                        .addContainerGap())))
        );

        TablePanel.setMinimumSize(new java.awt.Dimension(516, 347));
        TablePanel.setName("TablePanel"); // NOI18N

        TablePanelLabel.setText(resourceMap.getString("TablePanelLabel.text")); // NOI18N
        TablePanelLabel.setName("TablePanelLabel"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        ColumnNameList.setModel(new javax.swing.AbstractListModel() {
            ArrayList<String> strings = columns;
            public int getSize() { return strings.size(); }
            public Object getElementAt(int i) { return strings.get(i); }
        });
        ColumnNameList.setName("ColumnNameList"); // NOI18N
        jScrollPane3.setViewportView(ColumnNameList);

        TableSelectCheckbox.setText(resourceMap.getString("TableSelectCheckbox.text")); // NOI18N
        TableSelectCheckbox.setName("TableSelectCheckbox"); // NOI18N

        TableInsertCheckbox.setText(resourceMap.getString("TableInsertCheckbox.text")); // NOI18N
        TableInsertCheckbox.setName("TableInsertCheckbox"); // NOI18N

        TableUpdateCheckbox.setText(resourceMap.getString("TableUpdateCheckbox.text")); // NOI18N
        TableUpdateCheckbox.setName("TableUpdateCheckbox"); // NOI18N

        TableDeleteCheckbox.setText(resourceMap.getString("TableDeleteCheckbox.text")); // NOI18N
        TableDeleteCheckbox.setName("TableDeleteCheckbox"); // NOI18N

        TableCreateCheckbox.setText(resourceMap.getString("TableCreateCheckbox.text")); // NOI18N
        TableCreateCheckbox.setName("TableCreateCheckbox"); // NOI18N

        TableHostCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"NULL"}));
        TableHostCombobox.setName("TableHostCombobox"); // NOI18N
        TableHostCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TableHostComboboxActionPerformed(evt);
            }
        });

        TableDropCheckbox.setText(resourceMap.getString("TableDropCheckbox.text")); // NOI18N
        TableDropCheckbox.setName("TableDropCheckbox"); // NOI18N

        TableGrantCheckbox.setText(resourceMap.getString("TableGrantCheckbox.text")); // NOI18N
        TableGrantCheckbox.setName("TableGrantCheckbox"); // NOI18N

        TableReferencesCheckbox.setText(resourceMap.getString("TableReferencesCheckbox.text")); // NOI18N
        TableReferencesCheckbox.setName("TableReferencesCheckbox"); // NOI18N

        TableIndexCheckbox.setText(resourceMap.getString("TableIndexCheckbox.text")); // NOI18N
        TableIndexCheckbox.setName("TableIndexCheckbox"); // NOI18N

        TableAlterCheckbox.setText(resourceMap.getString("TableAlterCheckbox.text")); // NOI18N
        TableAlterCheckbox.setName("TableAlterCheckbox"); // NOI18N

        TableCreateViewCheckbox.setText(resourceMap.getString("TableCreateViewCheckbox.text")); // NOI18N
        TableCreateViewCheckbox.setName("TableCreateViewCheckbox"); // NOI18N

        TableShowViewCheckbox.setText(resourceMap.getString("TableShowViewCheckbox.text")); // NOI18N
        TableShowViewCheckbox.setName("TableShowViewCheckbox"); // NOI18N

        TableTriggerCheckbox.setText(resourceMap.getString("TableTriggerCheckbox.text")); // NOI18N
        TableTriggerCheckbox.setName("TableTriggerCheckbox"); // NOI18N

        EditColumnButton.setText(resourceMap.getString("EditColumnButton.text")); // NOI18N
        EditColumnButton.setName("EditColumnButton"); // NOI18N
        EditColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditColumnButtonActionPerformed(evt);
            }
        });

        BackToDatabaseButton.setText(resourceMap.getString("BackToDatabaseButton.text")); // NOI18N
        BackToDatabaseButton.setName("BackToDatabaseButton"); // NOI18N
        BackToDatabaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDatabaseButtonActionPerformed(evt);
            }
        });

        ApplyTablePrivsButton.setText(resourceMap.getString("ApplyTablePrivsButton.text")); // NOI18N
        ApplyTablePrivsButton.setName("ApplyTablePrivsButton"); // NOI18N
        ApplyTablePrivsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyTablePrivsButtonActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        javax.swing.GroupLayout TablePanelLayout = new javax.swing.GroupLayout(TablePanel);
        TablePanel.setLayout(TablePanelLayout);
        TablePanelLayout.setHorizontalGroup(
            TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablePanelLayout.createSequentialGroup()
                .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TablePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(TablePanelLayout.createSequentialGroup()
                                .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TableHostCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TableSelectCheckbox)
                                    .addComponent(TableInsertCheckbox)
                                    .addComponent(TableUpdateCheckbox)
                                    .addComponent(TableDeleteCheckbox)
                                    .addComponent(TableCreateCheckbox)
                                    .addComponent(TableDropCheckbox))
                                .addGap(79, 79, 79)
                                .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TableGrantCheckbox)
                                    .addComponent(TableReferencesCheckbox)
                                    .addComponent(TableIndexCheckbox)
                                    .addComponent(TableAlterCheckbox)
                                    .addComponent(TableCreateViewCheckbox)
                                    .addComponent(TableShowViewCheckbox)
                                    .addComponent(TableTriggerCheckbox)))
                            .addGroup(TablePanelLayout.createSequentialGroup()
                                .addComponent(ApplyTablePrivsButton)
                                .addGap(18, 18, 18)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                    .addGroup(TablePanelLayout.createSequentialGroup()
                        .addComponent(BackToDatabaseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                        .addComponent(EditColumnButton))
                    .addGroup(TablePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(TablePanelLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                        .addComponent(jLabel11)))
                .addContainerGap())
        );
        TablePanelLayout.setVerticalGroup(
            TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TablePanelLabel)
                    .addComponent(jLabel11))
                .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, TablePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(TablePanelLayout.createSequentialGroup()
                                .addComponent(TableHostCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TableSelectCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableInsertCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableUpdateCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableDeleteCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableCreateCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableDropCheckbox))
                            .addGroup(TablePanelLayout.createSequentialGroup()
                                .addComponent(TableGrantCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableReferencesCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableIndexCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableAlterCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableCreateViewCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableShowViewCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TableTriggerCheckbox)))
                        .addGap(18, 18, 18)
                        .addComponent(ApplyTablePrivsButton))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BackToDatabaseButton)
                    .addGroup(TablePanelLayout.createSequentialGroup()
                        .addComponent(EditColumnButton)
                        .addContainerGap())))
        );

        ColumnPanel.setName("ColumnPanel"); // NOI18N

        ColPanelLabel.setText(resourceMap.getString("ColPanelLabel.text")); // NOI18N
        ColPanelLabel.setName("ColPanelLabel"); // NOI18N

        ColSelect.setText(resourceMap.getString("ColSelect.text")); // NOI18N
        ColSelect.setName("ColSelect"); // NOI18N

        ColInsert.setText(resourceMap.getString("ColInsert.text")); // NOI18N
        ColInsert.setName("ColInsert"); // NOI18N

        ColUpdate.setText(resourceMap.getString("ColUpdate.text")); // NOI18N
        ColUpdate.setName("ColUpdate"); // NOI18N

        ColReferences.setText(resourceMap.getString("ColReferences.text")); // NOI18N
        ColReferences.setName("ColReferences"); // NOI18N

        BackToTables.setText(resourceMap.getString("BackToTables.text")); // NOI18N
        BackToTables.setName("BackToTables"); // NOI18N
        BackToTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToTablesActionPerformed(evt);
            }
        });

        UpdateColPrivs.setText(resourceMap.getString("UpdateColPrivs.text")); // NOI18N
        UpdateColPrivs.setName("UpdateColPrivs"); // NOI18N
        UpdateColPrivs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateColPrivsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ColumnPanelLayout = new javax.swing.GroupLayout(ColumnPanel);
        ColumnPanel.setLayout(ColumnPanelLayout);
        ColumnPanelLayout.setHorizontalGroup(
            ColumnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColumnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ColumnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ColumnPanelLayout.createSequentialGroup()
                        .addComponent(ColInsert)
                        .addGap(18, 18, 18)
                        .addComponent(ColReferences))
                    .addComponent(ColPanelLabel)
                    .addGroup(ColumnPanelLayout.createSequentialGroup()
                        .addComponent(ColSelect)
                        .addGap(18, 18, 18)
                        .addComponent(ColUpdate))
                    .addGroup(ColumnPanelLayout.createSequentialGroup()
                        .addComponent(BackToTables)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(UpdateColPrivs)))
                .addContainerGap())
        );
        ColumnPanelLayout.setVerticalGroup(
            ColumnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColumnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ColPanelLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ColumnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ColumnPanelLayout.createSequentialGroup()
                        .addGroup(ColumnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ColSelect)
                            .addComponent(ColUpdate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ColumnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ColInsert)
                            .addComponent(ColReferences))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BackToTables))
                    .addComponent(UpdateColPrivs))
                .addContainerGap())
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
            ResultSet userSet = getUsers.executeQuery("SELECT DISTINCT User FROM mysql.`user` ORDER BY User");
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
        if (!editUser.isEmpty()) {
            if (!editUser.equalsIgnoreCase("root")) {
                // Switch to database panel
                UserListPanel.setVisible(false);
                setComponent(GlobalPanel);
                getFrame().setMinimumSize(GlobalWindow);
                getFrame().setSize(GlobalWindow);
                GlobalPanel.setVisible(true);
                updateUsersHosts();
                updateGlobalPrivileges();
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Let's not mess up root's privileges.");
            }
        }
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
        if (!editUser.equalsIgnoreCase("root") && !editUser.isEmpty()) {
            int canDel = JOptionPane.showConfirmDialog(UserListPanel, "Delete User: " + editUser + "?", "Delete " + editUser, JOptionPane.YES_NO_OPTION);
            if (canDel == JOptionPane.YES_OPTION) {
                try {
                    //Check for username in database already
                    int ret = 0;
                    String allUserHosts = "";
                    for (String host : hosts) {
                        if (!allUserHosts.isEmpty()) {
                            allUserHosts += ",";
                        }
                        allUserHosts += " '" + cleanSQL(editUser) + "'@'" + cleanSQL(host) + "' ";
                    }

                    Statement update = connection.createStatement();
                    ret += update.executeUpdate("DROP USER " + allUserHosts);
                    if (ret == 0) {
                        JOptionPane.showMessageDialog(UserListPanel, "User Deleted.");
                    } else {
                        JOptionPane.showMessageDialog(UserListPanel, "User Not Deleted.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(UserListPanel, "DeleteUserButtonActionPerformed: " + ex.getMessage());
                }
            }
        } else if (editUser.equalsIgnoreCase("root")) {
            JOptionPane.showMessageDialog(UserListPanel, "Can't delete root account!");
        }
        //Refresh user list
        updateUsers();
    }//GEN-LAST:event_DeleteUserButtonActionPerformed

    private void SubmitAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitAddUserActionPerformed
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
            ResultSet checkName = query.executeQuery("SELECT DISTINCT User FROM mysql.`user` WHERE User = '" + cleanSQL(username) + "'");
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
    GlobalPanel.setVisible(false);
    setComponent(UserListPanel);

    getFrame().setMinimumSize(UsersWindow);
    getFrame().setSize(UsersWindow);
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
        ResultSet checkName = query.executeQuery("SELECT DISTINCT Host FROM mysql.`user` WHERE User = '" + cleanSQL(editUser) + "' AND Host ='" + cleanSQL(hostname.getText()) + "'");
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
    updateGlobalPrivileges();
}//GEN-LAST:event_AddHostButtonActionPerformed

private void RemoveHostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveHostButtonActionPerformed
    editHost = HostComboBox.getSelectedItem().toString();
    if (!editUser.isEmpty() && !editHost.isEmpty()) {

        try {
            Statement query = connection.createStatement();
            ResultSet hostQuery = query.executeQuery("SELECT DISTINCT Host FROM mysql.`user` WHERE User = '" + cleanSQL(editUser) + "'");
            hostQuery.last();
            if (hostQuery.getRow() > 1) {
                int canDel = JOptionPane.showConfirmDialog(UserListPanel, "Delete Host: " + editHost + "?", "Delete " + editHost, JOptionPane.YES_NO_OPTION);
                if (canDel == JOptionPane.YES_OPTION) {
                    Statement update = connection.createStatement();
                    int ret = 0;
                    ret += update.executeUpdate("DROP USER '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
                    if (ret == 0) {
                        JOptionPane.showMessageDialog(UserListPanel, "Host Deleted.");
                        updateUsersHosts();
                        updateGlobalPrivileges();
                    } else {
                        JOptionPane.showMessageDialog(UserListPanel, "Host Not Deleted.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(UserListPanel, "Only host left for this user.\nDelete User Instead.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(UserListPanel, "RemoveHostButtonActionPerformed: " + ex.getMessage());

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
	
//	String QueriesPerHourAmt = QueriesPerHour.getText();
//	String ConnectionsPerHourAmt = ConnectionsPerHour.getText();
//	String UpdatesPerHourAmt = UpdatesPerHour.getText();
//	String UserConnectionsAmt = UserConnections.getText();
//	String postfix = "";
//	if (!QueriesPerHourAmt.isEmpty()) {
//		postfix += " MAX_QUERIES_PER_HOUR " + QueriesPerHourAmt;
//	}
//	if (!ConnectionsPerHourAmt.isEmpty()) {
//		postfix += " MAX_CONNECTIONS_PER_HOUR " + ConnectionsPerHourAmt;
//	}
//	if (!UpdatesPerHourAmt.isEmpty()) {
//		postfix += " MAX_UPDATES_PER_HOUR " + UpdatesPerHourAmt;
//	}
//	if (!UserConnectionsAmt.isEmpty()) {
//		postfix += " MAX_USER_CONNECTIONS " + UserConnectionsAmt;
//	}
//	if (!postfix.isEmpty()) {
//		postfix = " WITH" + postfix;
//	}
	
//    System.out.println("GRANT " + grants + " ON *.* TO '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'" + postfix);
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
		
		if (!QueriesPerHour.getText().isEmpty()) {
			update = connection.createStatement();
			ret += update.executeUpdate("UPDATE mysql.user SET max_questions='"+QueriesPerHour.getText()+"' WHERE user='"+editUser+"'");
		}
		if (!ConnectionsPerHour.getText().isEmpty()) {
			update = connection.createStatement();
			ret += update.executeUpdate("UPDATE mysql.user SET max_connections='"+ConnectionsPerHour.getText()+"' WHERE user='"+editUser+"'");
		}
		if (!UpdatesPerHour.getText().isEmpty()) {
			update = connection.createStatement();
			ret += update.executeUpdate("UPDATE mysql.user SET max_updates='"+UpdatesPerHour.getText()+"' WHERE user='"+editUser+"'");
		}
		if (!UserConnections.getText().isEmpty()) {
			update = connection.createStatement();
			ret += update.executeUpdate("UPDATE mysql.user SET max_user_connections='"+UserConnections.getText()+"' WHERE user='"+editUser+"'");
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
    private javax.swing.JButton AddUserButton;
    private javax.swing.JButton AddUserCancel;
    private javax.swing.JTextField AddUserHost;
    private javax.swing.JTextField AddUserName;
    private javax.swing.JPanel AddUserPanel;
    private javax.swing.JPasswordField AddUserPass;
    private javax.swing.JButton ApplyDBPrivButton;
    private javax.swing.JButton ApplyTablePrivsButton;
    private javax.swing.JButton BackToDatabaseButton;
    private javax.swing.JButton BackToGlobalButton;
    private javax.swing.JButton BackToTables;
    private javax.swing.JButton ChangePasswordButton;
    private javax.swing.JCheckBox ColInsert;
    private javax.swing.JLabel ColPanelLabel;
    private javax.swing.JCheckBox ColReferences;
    private javax.swing.JCheckBox ColSelect;
    private javax.swing.JCheckBox ColUpdate;
    private javax.swing.JList ColumnNameList;
    private javax.swing.JPanel ColumnPanel;
    private javax.swing.JButton ConnectButton;
    private javax.swing.JTextField ConnectionsPerHour;
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
    private javax.swing.JComboBox DbHostCombobox;
    private javax.swing.JButton DeleteUserButton;
    private javax.swing.JButton EditColumnButton;
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
    private javax.swing.JPanel GlobalPanel;
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
    private javax.swing.JTextField QueriesPerHour;
    private javax.swing.JButton RemoveHostButton;
    private javax.swing.JButton RenameUserButton;
    private javax.swing.JButton SelectDB;
    private javax.swing.JTextField ServerField;
    private javax.swing.JButton ShowGrantsButton;
    private javax.swing.JButton SubmitAddUser;
    private javax.swing.JCheckBox TableAlterCheckbox;
    private javax.swing.JCheckBox TableCreateCheckbox;
    private javax.swing.JCheckBox TableCreateViewCheckbox;
    private javax.swing.JCheckBox TableDeleteCheckbox;
    private javax.swing.JCheckBox TableDropCheckbox;
    private javax.swing.JCheckBox TableGrantCheckbox;
    private javax.swing.JComboBox TableHostCombobox;
    private javax.swing.JCheckBox TableIndexCheckbox;
    private javax.swing.JCheckBox TableInsertCheckbox;
    private javax.swing.JPanel TablePanel;
    private javax.swing.JLabel TablePanelLabel;
    private javax.swing.JCheckBox TableReferencesCheckbox;
    private javax.swing.JCheckBox TableSelectCheckbox;
    private javax.swing.JCheckBox TableShowViewCheckbox;
    private javax.swing.JCheckBox TableTriggerCheckbox;
    private javax.swing.JCheckBox TableUpdateCheckbox;
    private javax.swing.JButton UpdateColPrivs;
    private javax.swing.JTextField UpdatesPerHour;
    private javax.swing.JTextField UserConnections;
    private javax.swing.JTextField UserField;
    private javax.swing.JPanel UserListPanel;
    private javax.swing.JList UserListjList;
    private javax.swing.JButton backToUsers;
    private javax.swing.JList databaseList;
    private javax.swing.JScrollPane dbListPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane3;
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
    private String editTable;
    private ArrayList<String> columns;
	private String editColumn;
    private Dimension UsersWindow = new Dimension(403, 265);
    private Dimension GlobalWindow = new Dimension(759, 415);
    private Dimension DbWindow = new Dimension(590, 441);
    private Dimension TableWindow = new Dimension(516, 347);
    private Dimension ColumnWindow = new Dimension(516, 347);

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
			QueriesPerHour.setText(privs.getString("max_questions"));
			ConnectionsPerHour.setText(privs.getString("max_connections"));
			UpdatesPerHour.setText(privs.getString("max_updates"));
			UserConnections.setText(privs.getString("max_user_connections"));


            ShowGrantsButton.setText("Show Grants (" + editUser + "@" + editHost + ")");

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
            if (hosts == null) {
                hosts = new ArrayList<String>();
            } else {
                hosts.clear();
            }
            Statement userHosts = connection.createStatement();
            ResultSet hostList = userHosts.executeQuery("SELECT Host FROM mysql.`user` WHERE User = '" + cleanSQL(editUser) + "' ORDER BY Host");

            while (hostList.next()) {
                hosts.add(hostList.getString("Host"));
            }

            HostComboBox.setModel(new javax.swing.DefaultComboBoxModel(hosts.toArray()));
            if (GlobalPanel.isVisible()) {
                editHost = (String) HostComboBox.getSelectedItem();
                ShowGrantsButton.setText("Show Grants (" + editUser + "@" + editHost + ")");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(GlobalPanel, "UpdateUsersHosts: " + ex.getMessage());
        }
    }

    private void updateDbHosts() {
        updateUsersHosts();

        DbHostCombobox.setModel(new javax.swing.DefaultComboBoxModel(hosts.toArray()));


        if (DBPanel.isVisible()) {
            editHost = (String) DbHostCombobox.getSelectedItem();
        }
    }

    private void updateTableHosts() {
        TableHostCombobox.setSelectedIndex(0);

        updateUsersHosts();
        TableHostCombobox.setModel(new javax.swing.DefaultComboBoxModel(hosts.toArray()));

        if (TablePanel.isVisible()) {
            editHost = (String) TableHostCombobox.getSelectedItem();
        }
    }

	private void SelectDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectDBActionPerformed
            editDatabase = getDBListValue();


            if (editDatabase.equals("")) {
                JOptionPane.showMessageDialog(GlobalPanel, "You must select a database to edit.");


            } else {
                DBPanelTitle.setText("Privileges for " + editUser + " on " + editDatabase);
                DBTableListLabel.setText(editDatabase + "'s Tables");
                getTables();

                GlobalPanel.setVisible(false);
                setComponent(
                        DBPanel);
                DBPanel.setVisible(true);

                getFrame().setMinimumSize(DbWindow);
                getFrame().setSize(DbWindow);

                updateDbHosts();
                getDBPrivs();


            }
	}//GEN-LAST:event_SelectDBActionPerformed

	private void BackToGlobalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToGlobalButtonActionPerformed
            editDatabase = "";

            DBPanel.setVisible(false);
            setComponent(GlobalPanel);

            getFrame().setMinimumSize(GlobalWindow);
            getFrame().setSize(GlobalWindow);
            GlobalPanel.setVisible(true);
            updateGlobalPrivileges();
	}//GEN-LAST:event_BackToGlobalButtonActionPerformed
        private void DbHostComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DbHostComboboxActionPerformed
            getDBPrivs();
        }//GEN-LAST:event_DbHostComboboxActionPerformed

        private void ApplyDBPrivButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyDBPrivButtonActionPerformed
            editHost = DbHostCombobox.getSelectedItem().toString();
            String grants = "";
            String revokes = "";
            if (DBSelect.isSelected()) {
                grants += " SELECT ";
            } else {
                revokes += " SELECT ";
            }

            if (DBInsert.isSelected()) {
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

            if (DBDelete.isSelected()) {
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

            if (DBUpdate.isSelected()) {
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

            if (DBCreate.isSelected()) {
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

            if (DBDrop.isSelected()) {
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

            if (DBGrant.isSelected()) {
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

            if (DBIndex.isSelected()) {
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

            if (DBAlter.isSelected()) {
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

            if (DBCreateTempTables.isSelected()) {
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

            if (DBShowView.isSelected()) {
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

            if (DBCreateRoutine.isSelected()) {
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

            if (DBAlterRoutine.isSelected()) {
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

            if (DBExecute.isSelected()) {
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

            if (DBCreateView.isSelected()) {
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

            if (DBEvent.isSelected()) {
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

            if (DBTrigger.isSelected()) {
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

            if (DBLockTables.isSelected()) {
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

            if (DBReferences.isSelected()) {
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

            try {
                Statement update;
                int ret = 0;


                if (!grants.isEmpty()) {
                    update = connection.createStatement();
                    ret += update.executeUpdate("GRANT " + grants + " ON " + cleanSQL(editDatabase) + ".* TO '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
                }
                if (!revokes.isEmpty()) {
                    update = connection.createStatement();
                    ret += update.executeUpdate("REVOKE " + revokes + " ON " + cleanSQL(editDatabase) + ".* FROM '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
                }
                if (ret == 0) {
                    JOptionPane.showMessageDialog(UserListPanel, "Privileges Updated.");
                } else {
                    JOptionPane.showMessageDialog(UserListPanel, "Privileges Not Updated.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(UserListPanel, "DBUpdatePrivActionPerformed: " + ex.getMessage());
            }
        }//GEN-LAST:event_ApplyDBPrivButtonActionPerformed

        private void RenameUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RenameUserButtonActionPerformed
            editUser = UserListjList.getSelectedValue().toString();
            updateUsersHosts();


            if (!editUser.isEmpty()) {
                if (!editUser.equalsIgnoreCase("root")) {
                    JTextField username = new JTextField();
                    final JComponent[] inputs = new JComponent[]{
                        new JLabel("Enter New Username:"),
                        username
                    };
                    int cancel = JOptionPane.showConfirmDialog(null, inputs, "Change Password", JOptionPane.OK_CANCEL_OPTION);
                    if (cancel == JOptionPane.YES_OPTION) {
                        if (username.getText() != null && !username.getText().isEmpty()) {
                            try {
                                Statement query = connection.createStatement();
                                ResultSet checkName = query.executeQuery("SELECT DISTINCT User FROM mysql.`user` WHERE User = '" + cleanSQL(username.getText()) + "'");
                                if (checkName.next()) {
                                    JOptionPane.showMessageDialog(AddUserPanel, "Username \"" + username.getText() + "\" already exists.");
                                } else {
                                    int ret = 0;

                                    String allUserHosts = "";
                                    for (String host : hosts) {
                                        if (!allUserHosts.isEmpty()) {
                                            allUserHosts += ",";
                                        }
                                        allUserHosts += " '" + cleanSQL(editUser) + "'@'" + cleanSQL(host) + "' TO '" + cleanSQL(username.getText()) + "'@'" + cleanSQL(host) + "' ";
                                    }

                                    Statement update = connection.createStatement();
                                    ret += update.executeUpdate("RENAME USER " + allUserHosts);

                                    if (ret == 0) {
                                        JOptionPane.showMessageDialog(UserListPanel, "Username Updated.");
                                    } else {
                                        JOptionPane.showMessageDialog(UserListPanel, "Username Not Updated.");
                                    }
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(UserListPanel, "RenameUserButtonActionPerformed: " + ex.getMessage());
                            }
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Let's not mess up root's name.");
                }
            }
            updateUsers();
        }//GEN-LAST:event_RenameUserButtonActionPerformed
        private void EditTablePrivsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditTablePrivsActionPerformed
            editTable = getTableListValue();
            if (!editTable.isEmpty()) {
                DBPanel.setVisible(false);
                setComponent(
                        TablePanel);
                getFrame().setMinimumSize(TableWindow);
                getFrame().setSize(TableWindow);
                TablePanel.setVisible(true);
                TablePanelLabel.setText("Privileges for " + editUser + " on " + editDatabase + "." + editTable);
                updateTableHosts();
                updateTablePrivs();
                getColumns();
            }
        }//GEN-LAST:event_EditTablePrivsActionPerformed

        private void TableHostComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TableHostComboboxActionPerformed
            updateTablePrivs();
        }//GEN-LAST:event_TableHostComboboxActionPerformed

        private void ApplyTablePrivsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyTablePrivsButtonActionPerformed
            editHost = (String) TableHostCombobox.getSelectedItem();
            String grants = "";
            String revokes = "";

            if (TableSelectCheckbox.isSelected()) {
                grants += " SELECT ";
            } else {
                revokes += " SELECT ";
            }

            if (TableInsertCheckbox.isSelected()) {
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

            if (TableUpdateCheckbox.isSelected()) {
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

            if (TableDeleteCheckbox.isSelected()) {
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

            if (TableCreateCheckbox.isSelected()) {
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

            if (TableDropCheckbox.isSelected()) {
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

            if (TableGrantCheckbox.isSelected()) {
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

            if (TableReferencesCheckbox.isSelected()) {
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

            if (TableIndexCheckbox.isSelected()) {
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

            if (TableAlterCheckbox.isSelected()) {
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

            if (TableCreateViewCheckbox.isSelected()) {
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

            if (TableShowViewCheckbox.isSelected()) {
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

            if (TableTriggerCheckbox.isSelected()) {
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



            try {
                Statement update;
                int ret = 0;
                if (!grants.isEmpty()) {
                    update = connection.createStatement();
                    ret += update.executeUpdate("GRANT " + grants + " ON " + cleanSQL(editDatabase) + "." + editTable + " TO '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
                }
                if (!revokes.isEmpty()) {
                    update = connection.createStatement();
                    ret += update.executeUpdate("REVOKE " + revokes + " ON " + cleanSQL(editDatabase) + "." + editTable + " FROM '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");
                }
                if (ret == 0) {
                    JOptionPane.showMessageDialog(UserListPanel, "Privileges Updated.");
                } else {
                    JOptionPane.showMessageDialog(UserListPanel, "Privileges Not Updated.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(UserListPanel, "ApplyTablePrivsButtonActionPerformed: " + ex.getMessage());
            }
        }//GEN-LAST:event_ApplyTablePrivsButtonActionPerformed

        private void BackToDatabaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDatabaseButtonActionPerformed
            getTables();

            TablePanel.setVisible(false);
            setComponent(DBPanel);
            DBPanel.setVisible(true);

            getFrame().setMinimumSize(DbWindow);
            getFrame().setSize(DbWindow);

            updateDbHosts();
            getDBPrivs();
        }//GEN-LAST:event_BackToDatabaseButtonActionPerformed

        private void ShowGrantsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowGrantsButtonActionPerformed
            editHost = (String) HostComboBox.getSelectedItem();
            if (editHost != null && !editHost.isEmpty()) {
                try {
                    String grantsMsg = "";
                    Statement grantsQ = connection.createStatement();
                    ResultSet showGrants = grantsQ.executeQuery("SHOW GRANTS FOR '" + cleanSQL(editUser) + "'@'" + cleanSQL(editHost) + "'");

                    while (showGrants.next()) {
                        if (!grantsMsg.isEmpty()) {
                            grantsMsg += "\n";
                        }
                        grantsMsg += showGrants.getString(1);
                    }

                    JOptionPane.showMessageDialog(UserListPanel, grantsMsg);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(UserListPanel, "ShowGrantsButtonActionPerformed: " + ex.getMessage());
                }
            }
        }//GEN-LAST:event_ShowGrantsButtonActionPerformed

	private void EditColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditColumnButtonActionPerformed
		editColumn = getColumnListValue();
		if (!editColumn.isEmpty()) {
			TablePanel.setVisible(false);
			setComponent(ColumnPanel);
			ColumnPanel.setVisible(true);
			ColPanelLabel.setText(editUser +"'s Privileges on " + editDatabase +"."+editTable+"."+editColumn);
			getColPrivs();
		}
	}//GEN-LAST:event_EditColumnButtonActionPerformed

	private void BackToTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToTablesActionPerformed
		ColumnPanel.setVisible(false);
		setComponent(TablePanel);
		TablePanel.setVisible(true);
		editColumn = "";
	}//GEN-LAST:event_BackToTablesActionPerformed

	private void UpdateColPrivsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateColPrivsActionPerformed
		ArrayList<String> revokes = new ArrayList<String>();
		ArrayList<String> grants = new ArrayList<String>();
		
		if (ColSelect.isSelected()) {
			grants.add("SELECT");
		} else {
			revokes.add("SELECT");
		}
		
		if (ColInsert.isSelected()) {
			grants.add("INSERT");
		} else {
			revokes.add("INSERT");
		}
		
		if (ColUpdate.isSelected()) {
			grants.add("UPDATE");
		} else {
			revokes.add("UPDATE");
		}
		
		if (ColReferences.isSelected()) {
			grants.add("REFERENCES");
		} else {
			revokes.add("REFERENCES");
		}
		
		try {
			int ret = 0;
			Statement revoke = connection.createStatement();
			Statement grant = connection.createStatement();
			String revokeString = "";
			String grantString = "";
			
			for (String r : revokes) {
				revokeString += r + " (" + editColumn + "), ";
			}
			for (String g : grants) {
				grantString += g + " (" + editColumn + "), ";
			}
			
			if (!revokeString.isEmpty()) {
				revokeString = revokeString.substring(0, revokeString.length() - 2);
				ret += revoke.executeUpdate("REVOKE " + revokeString + " ON " + editDatabase + "." + editTable + " FROM " + editUser + "@" + editHost);
			}
			if (!grantString.isEmpty()) {
				grantString = grantString.substring(0, grantString.length() - 2);
				ret += grant.executeUpdate("GRANT " + grantString + " ON " + editDatabase + "." + editTable + " TO " + editUser + "@" + editHost);
			}
			if (ret == 0) {
                    JOptionPane.showMessageDialog(UserListPanel, "Privileges Updated.");
                } else {
                    JOptionPane.showMessageDialog(UserListPanel, "Privileges Not Updated.");
                }
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(UserListPanel, "updateColPrivs: " + e.getMessage());
		}
	}//GEN-LAST:event_UpdateColPrivsActionPerformed

    private void updateTablePrivs() {
        editHost = (String) TableHostCombobox.getSelectedItem();

        TableSelectCheckbox.setSelected(false);
        TableInsertCheckbox.setSelected(false);
        TableUpdateCheckbox.setSelected(false);
        TableDeleteCheckbox.setSelected(false);
        TableCreateCheckbox.setSelected(false);
        TableDropCheckbox.setSelected(false);
        TableGrantCheckbox.setSelected(false);
        TableReferencesCheckbox.setSelected(false);
        TableIndexCheckbox.setSelected(false);
        TableAlterCheckbox.setSelected(false);
        TableCreateViewCheckbox.setSelected(false);
        TableShowViewCheckbox.setSelected(false);
        TableTriggerCheckbox.setSelected(false);

        if (editHost != null && !editHost.isEmpty()) {
            try {
                Statement updateTablePrivs = connection.createStatement();
                ResultSet TablePrivs = updateTablePrivs.executeQuery("SELECT * from mysql.tables_priv WHERE user='" + cleanSQL(editUser) + "' AND host='" + cleanSQL(editHost) + "' AND db='" + editDatabase + "' AND Table_name='" + editTable + "';");

                if (TablePrivs.next()) {
                    for (String token : TablePrivs.getString("Table_priv").split(",")) {
                        if (token.equalsIgnoreCase("Select")) {
                            TableSelectCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Insert")) {
                            TableInsertCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Update")) {
                            TableUpdateCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Delete")) {
                            TableDeleteCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Create")) {
                            TableCreateCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Drop")) {
                            TableDropCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Grant")) {
                            TableGrantCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Reference")) {
                            TableReferencesCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Index")) {
                            TableIndexCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Alter")) {
                            TableAlterCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Create View")) {
                            TableCreateViewCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Show View")) {
                            TableShowViewCheckbox.setSelected(true);
                        }
                        if (token.equalsIgnoreCase("Trigger")) {
                            TableTriggerCheckbox.setSelected(true);
                        }
                    }
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(UserListPanel, "updateTablePrivs: " + ex.getMessage());
            }
        }
    }

	private void getColPrivs() {
		try {
			Statement colPrivsSt = connection.createStatement();
			ResultSet colPrivs = colPrivsSt.executeQuery("SELECT * FROM mysql.columns_priv WHERE Host='"+editHost+"' AND Db='"+ editDatabase +"' AND User='" +editUser+"' AND Table_name='"+editTable+"' AND Column_name='" +editColumn+"';");
			
			ColSelect.setSelected(false);
			ColInsert.setSelected(false);
			ColUpdate.setSelected(false);
			ColReferences.setSelected(false);
			
			if (colPrivs.next()) {
				for (String priv : colPrivs.getString("Column_priv").split(",")) {
					if (priv.equalsIgnoreCase("Select")) {
						ColSelect.setSelected(true);
					}
					if (priv.equalsIgnoreCase("Insert")) {
						ColInsert.setSelected(true);
					}
					if (priv.equalsIgnoreCase("Update")) {
						ColUpdate.setSelected(true);
					}
					if (priv.equalsIgnoreCase("References")) {
						ColReferences.setSelected(true);
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(UserListPanel, "updateColPrivs: " + e.getMessage());
		}
	}

}