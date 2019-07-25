import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;

public class FileDisplayer extends JFrame implements ItemListener {
	private JPanel radioButtonPanel;
	private JPanel panel;
	private Font font;
	private JRadioButton button1;
	private JRadioButton button2;
	private JComboBox comboBox;
	private JPanel comboPanel;
	private File f = null;
	private ArrayList<String> data_arr;
	private Set<String> hash2 = new HashSet<String>();
	private Set<String> hash4 = new HashSet<String>();
	private ArrayList<String> data_value = new ArrayList<>();
	private ArrayList<String> data_time = new ArrayList<>();
	private Double[][] IP_arr = new Double[51][656];
	private Double[][] HO_arr = new Double[51][656];
	private ArrayList<String> data_HO = new ArrayList<>();
	private ArrayList<String> data_IP = new ArrayList<>();
	private ArrayList<String> unique_ip;
	private ArrayList<String> unique_ho;
	private int[] IP_num = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private int[] HO_num = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0, 0,0,0,0,0 }; 

	public FileDisplayer() {

		super("Flow volume viewer");
		setSize(1000, 500);
		setLayout(null);
		font = new Font("Sans-serif", Font.PLAIN, 20);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupMenu();
		setupRadioButtons();

		panel = new Draw();

		add(panel, BorderLayout.CENTER);
		setupComboBox();
		setVisible(true);

	}

	private void setupMenu() {

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		fileMenu.setFont(font);
		menuBar.add(fileMenu);

		JMenuItem fileMenuOpen = new JMenuItem("Open trace file");
		fileMenuOpen.setFont(font);
		fileMenu.add(fileMenuOpen);

		fileMenuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser(".");
				int retVal = fileChooser.showOpenDialog(FileDisplayer.this);

				// System.out.println(this.getClass().getName());
				if (retVal == JFileChooser.APPROVE_OPTION) {
					f = fileChooser.getSelectedFile();
					
					File ff = new File(f.getAbsolutePath());
					Scanner input = null;

					try {
						input = new Scanner(ff);
					//	input = new Scanner(Paths.get(f.getName()));
						
					} catch (IOException ioExc) {

						return;
					}

					while (input.hasNext()) {

						String line = input.nextLine();
						// System.out.println(line);
						String[] data = line.split("\\s");

						String two = data[2]; // IP
						String four = data[4]; // Host
						if (two.length() != 0) {
							String one = data[1];
							String seven = data[7];
							data_time.add(one);
							data_IP.add(two);
							hash2.add(two);
							hash4.add(four);
							data_HO.add(four);
							data_value.add(seven);

						}
					}
					if (button1.isSelected()) {
						data_arr = new ArrayList(hash2);
						unique_ho = new ArrayList(hash4);
						unique_ip = new ArrayList(hash2);
					}
					if (button2.isSelected()) {
						data_arr = new ArrayList(hash4);
						unique_ho = new ArrayList(hash4);
						unique_ip = new ArrayList(hash2);
					}

					Collections.sort(data_arr, new Sort());
					Collections.sort(unique_ho, new Sort());
					Collections.sort(unique_ip, new Sort());
					comboBox.removeAllItems();
					for (int j = 0; j < data_arr.size(); j++) {
						comboBox.addItem((String) data_arr.get(j));
					}
					
					comboBox.setVisible(true);

					for (int i = 0; i < 51; i++) {
						for (int j = 0; j < 656; j++) {
							IP_arr[i][j] = 0.0;
							HO_arr[i][j] = 0.0;
						}
					}

					// // // sort the data in 2D array

					for (int i = 0; i < data_value.size(); i++) {
						for(int j = 0; j < unique_ip.size(); j++) {
							if (data_IP.get(i).equals(unique_ip.get(j))) {
								double l = Double.parseDouble(data_time.get(i));
								int k = (int) l;
								IP_arr[j][k] = IP_arr[j][k] + Double.parseDouble(data_value.get(i)); 
								if (IP_arr[j][k] > IP_arr[j][IP_num[j]]) {
									IP_num[j] = k;
								}
				
							}
						}

					}
					

					for (int i = 0; i < data_value.size(); i++) {
						for(int j = 0; j < unique_ho.size(); j++) {
							if (data_HO.get(i).equals(unique_ho.get(j))) {
								double l = Double.parseDouble(data_time.get(i));
								int k = (int) l;
								HO_arr[j][k] = HO_arr[j][k] + Double.parseDouble(data_value.get(i)); 
								if (HO_arr[j][k] > HO_arr[j][HO_num[j]]) {
									HO_num[j] = k;
								}
							}
						}
					} // end the for value loop					
				}					
			}
		});

		JMenuItem fileMenuExit = new JMenuItem("Quit");
		fileMenuExit.setFont(font);
		fileMenu.add(fileMenuExit);

		fileMenuExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	private void setupComboBox() {
		comboBox = new JComboBox();
		comboBox.addItemListener(this);
		// comboBox.addActionListener(this);
		comboPanel = new JPanel();
		comboPanel.add(comboBox);

		comboPanel.setSize(400, 50);
		comboPanel.setLocation(300, 15);
		comboBox.setVisible(false);
		add(comboPanel);
	}

	private void setupRadioButtons() {
		radioButtonPanel = new JPanel();
		radioButtonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.WEST;
		button1 = new JRadioButton("Source hosts");
		button2 = new JRadioButton("Destination host");
		ButtonGroup radioButtons = new ButtonGroup();
		radioButtons.add(button1);
		radioButtons.add(button2);
		radioButtonPanel.add(button1, c);
		radioButtonPanel.add(button2, c);
		radioButtonPanel.setSize(200, 100);
		radioButtonPanel.setLocation(0, 0);
		button1.setSelected(true);
		button1.setFont(font);
		button2.setFont(font);
		setVisible(true);
		add(radioButtonPanel);
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (f != null) {
					comboBox.removeAllItems();
					data_arr = new ArrayList(hash2);
					Collections.sort(data_arr, new Sort());

					for (int j = 0; j < data_arr.size(); j++) {
						comboBox.addItem((String) data_arr.get(j));
					}
				}
			}
		});
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (f != null) {
					comboBox.removeAllItems();
					data_arr = new ArrayList(hash4);
					Collections.sort(data_arr, new Sort());
					for (int j = 0; j < data_arr.size(); j++) {
						comboBox.addItem((String) data_arr.get(j));
					}
				}
			}
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			remove(panel);

		//	System.out.println("hello world");

			int index = comboBox.getSelectedIndex();
			if (button1.isSelected()) {
				panel = new Draw(index, IP_arr, IP_num);
	//			System.out.println("b1");
			} else {
				panel = new Draw(index, HO_arr, HO_num);
	//			System.out.println("b2");
			}
			add(panel);
			repaint();
		}
	}

}