package com.prapps.jmx;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class JmxFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private Properties properties;
	
	private JScrollPane mainPanel;
	private JScrollPane consoleScrollPanel;
	private JPanel southPanel;
	private JPanel buttonPanel;
	private JTable table;
	private JButton btnGenerate;
	private JButton btnBrowse;
	private JTextField txtTargetFolder;
	private JLabel lblTargetFolder;
	private JLabel lblHeader;
	private JPanel headerPanel;
	private JPanel targetFolderPanel;
	private JTextArea console;
	private JLabel lblConsole;
	private TableModel tableModel;
	
	public JmxFrame() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("config.ini"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		initComponents();
	}
	
	public void initComponents() {
		setTitle("Jmx Util");
		setPreferredSize(new Dimension(800, 600));
		setSize(new Dimension(800, 600));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		String[][] data = new String[properties.entrySet().size()][2];
		int ctr = 0;
		for (Entry<Object, Object> entry : properties.entrySet()) {
			data[ctr++] = new String[]{(String) entry.getKey(), (String) entry.getValue()};
		}
		
		
		lblHeader = new JLabel("Configuration Parameters");
		lblTargetFolder = new JLabel("Select Target Folder");
		btnGenerate = new JButton("Generate >>");
		btnBrowse = new JButton("Browse");
		lblConsole = new JLabel("Console");
		console = new JTextArea(5, 20);
		txtTargetFolder = new JTextField();
		tableModel = new MyTableModel(data, new String[]{"property", "value"});
		table = new JTable(tableModel);
		headerPanel = new JPanel();
		mainPanel = new JScrollPane(table);
		southPanel = new JPanel();
		buttonPanel = new JPanel();
		targetFolderPanel = new JPanel();
		consoleScrollPanel = new JScrollPane(console);
		
		setLayout(new BorderLayout(20, 20));
		buttonPanel.setLayout(new BorderLayout());
		targetFolderPanel.setLayout(new BoxLayout(targetFolderPanel, BoxLayout.X_AXIS));
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
		
		southPanel.setBorder(new EmptyBorder(5, 50, 20, 50));
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		targetFolderPanel.setBorder(new EmptyBorder(20, 2, 20, 2));
		
		headerPanel.add(lblHeader);
		targetFolderPanel.add(lblTargetFolder);
		targetFolderPanel.add(txtTargetFolder);
		targetFolderPanel.add(btnBrowse);
		buttonPanel.add(btnGenerate, BorderLayout.EAST);
		southPanel.add(targetFolderPanel);
		southPanel.add(buttonPanel);
		southPanel.add(lblConsole);
		southPanel.add(consoleScrollPanel);
		add(headerPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGenerateAction(e);
			}
		});
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBrowseAction(e);
			}
		});
	}
	
	private void btnGenerateAction(ActionEvent e) {
		 table.getCellEditor().stopCellEditing();
		Map<String, String> context = new HashMap<String, String>();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			context.put((String)tableModel.getValueAt(i, 0), (String)tableModel.getValueAt(i, 1));
		}
		System.out.println(context);
	}
	
	private void btnBrowseAction(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	      txtTargetFolder.setText(chooser.getSelectedFile().getAbsolutePath());
	    }
	}
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
		      public void run() {
		         new JmxFrame();
		      }
		});
	}
	
	class MyTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		public MyTableModel(Object[][] tableData, Object[] colNames) {
			super(tableData, colNames);
		}

	   public boolean isCellEditable(int row, int column) {
		   if (column % 2 == 1) {
			   return true;
		   }
	      return false;
	   }
	}
}
