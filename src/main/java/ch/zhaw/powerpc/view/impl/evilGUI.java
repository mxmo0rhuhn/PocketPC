package ch.zhaw.powerpc.view.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import ch.zhaw.powerpc.controller.ProgramStarter;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.instructions.Instruction;

public class evilGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private JTextField akkumulator;
	private JTextField register1;
	private JTextField register2;
	private JTextField register3;
	private JCheckBox carryBit;

	private DefaultTableModel dataTable;
	private DefaultTableModel instructionTableModel;

	private List<String> dataListEntrys;
	private List<String> instructionListEntrys;

	private final ProgramStarter programStarter;
	private ControlUnit controllUnit;

	private JTable instructionTable;

	public evilGUI(ProgramStarter programStarter) {
		this.programStarter = programStarter;

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		dataListEntrys = new ArrayList<String>();
		instructionListEntrys = new ArrayList<String>();

		this.setTitle("Super duper ssh Prozessor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(createMenu());

		buildFrame();
		newCPU(new ControlUnit(new MainMemory()));
		// Da hier noch keine Daten vorhanden sind, ist die Clock abgelaufen
		controllUnit.getClock().setStopped(true);

		this.setLocation(100, 0);
		this.setVisible(true);
		this.setResizable(false);
		pack();
		this.setLocationRelativeTo(null);
	}

	private void buildFrame() {
		setLayout(new BorderLayout());
		add(createEastPanel(), BorderLayout.EAST);
		add(createWestPanel(), BorderLayout.WEST);
		add(createCenterPanel(), BorderLayout.CENTER);
		add(createSouthPanel(), BorderLayout.SOUTH);
	}

	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JButton nxt = new JButton("Nächster Schritt");
		nxt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evilGUI.this.controllUnit.getClock().step();
			}

		});

		JButton auto = new JButton("Langsam");
		auto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						evilGUI.this.controllUnit.getClock().startSlowMode();
					}
				}).start();
			}
		});

		JButton fast = new JButton("Schnell");
		fast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evilGUI.this.controllUnit.getClock().step();
				new Thread(new Runnable() {

					@Override
					public void run() {
						evilGUI.this.controllUnit.getClock().startFastMode();
					}
				}).start();
			}

		});

		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evilGUI.this.controllUnit.getClock().setPaused(true);
			}

		});
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evilGUI.this.controllUnit.getClock().setStopped(true);
			}

		});

		panel.add(nxt);
		panel.add(auto);
		panel.add(fast);
		panel.add(pause);
		panel.add(stop);
		return panel;
	}

	private JPanel createEastPanel() {
		return new JPanel();
	}

	private JPanel createWestPanel() {
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new FlowLayout());
		JPanel westInnerPanel = new JPanel();

		westInnerPanel.setLayout(new GridLayout(5, 2));
		westInnerPanel.add(new JLabel("Akkumulator"));
		akkumulator = new JTextField();
		akkumulator.setColumns(16);
		akkumulator.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		westInnerPanel.add(akkumulator);

		westInnerPanel.add(new JLabel("Register 1"));
		register1 = new JTextField();
		register1.setColumns(16);
		westInnerPanel.add(register1);

		westInnerPanel.add(new JLabel("Register 2"));
		register2 = new JTextField();
		register2.setColumns(16);
		westInnerPanel.add(register2);

		westInnerPanel.add(new JLabel("Register 3"));
		register3 = new JTextField();
		register3.setColumns(16);
		westInnerPanel.add(register3);

		westInnerPanel.add(new JLabel("Carry Bit"));
		carryBit = new JCheckBox();
		westInnerPanel.add(carryBit);

		westInnerPanel.setPreferredSize(new Dimension(250, 150));

		westPanel.add(westInnerPanel);
		return westPanel;
	}

	private JPanel createCenterPanel() {

		JPanel centerPanel = new JPanel();

		centerPanel.setLayout(new GridLayout(1, 2));
		centerPanel.add(createInstructionsTable());
		centerPanel.add(createDataTable());

		return centerPanel;
	}

	private JScrollPane createInstructionsTable() {
		instructionTableModel = new DefaultTableModel();

		// Spalten - Namen
		instructionTableModel.addColumn("Zeile");
		instructionTableModel.addColumn("Mnemonic");
		instructionTableModel.addColumn("Binär");

		// Tabelle
		instructionTable = new JTable(instructionTableModel);

		// Tabelle scrollbar machen
		JScrollPane scrolly = new JScrollPane(instructionTable);
		scrolly.setPreferredSize(new Dimension(300, 500));

		return scrolly;
	}

	private JScrollPane createDataTable() {
		dataTable = new DefaultTableModel();

		// Spalten - Namen
		dataTable.addColumn("Zeile");
		dataTable.addColumn("Dezimal");
		dataTable.addColumn("Binär");

		// Tabelle
		JTable tab = new JTable(dataTable);

		// Tabelle scrollbar machen
		JScrollPane scrolly = new JScrollPane(tab);
		scrolly.setPreferredSize(new Dimension(300, 500));

		return scrolly;
	}

	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu optionen = new JMenu("Optionen");

		optionen.add(buildLoadItem());

		menuBar.add(optionen);
		return menuBar;
	}

	private JMenuItem buildLoadItem() {
		JMenuItem load = new JMenuItem("Datei laden");
		load.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(evilGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						newCPU(programStarter.generateControlUnitFromInput(chooser.getSelectedFile().getAbsolutePath()));
						// } catch (ClassNotFoundException e1) {
						// JOptionPane.showMessageDialog(null, "Datei " + chooser.getSelectedFile().getName()
						// + " konnte nicht ausgelesen werden", "Fehler", JOptionPane.ERROR_MESSAGE);

					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Datei " + chooser.getSelectedFile().getName()
								+ " konnte nicht geöffnet werden", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		return load;
	}

	private void removeAllinstructionTableEntrys() {
		// this.instructionListEntrys.clear();
		while (0 < this.instructionTableModel.getRowCount()) {
			this.instructionTableModel.removeRow(0);
		}
	}

	private void removeAllDataTableEntrys() {
		// this.dataListEntrys.clear();
		while (0 < this.dataTable.getRowCount()) {
			this.dataTable.removeRow(0);
		}
	}

	private void displayInstructions() {
		removeAllinstructionTableEntrys();
		
		Map<Integer, Instruction> curInstructions = this.controllUnit.getMemory().getInstructions();
		
		ArrayList<Integer> sortetInstructionKeys = new ArrayList<Integer>(curInstructions.keySet());
		Collections.sort(sortetInstructionKeys);
		
		for (Integer curInstruction : sortetInstructionKeys) {
			Object[] data = {curInstruction, curInstructions.get(curInstruction).toString(), curInstructions.get(curInstruction).getBinary()};
			instructionTableModel.addRow(data);
		}
	}

	private void displayData() {
		removeAllDataTableEntrys();
		
		Map<Integer, Short> curDatas = this.controllUnit.getMemory().getData();
		
		ArrayList<Integer> sortetDataKeys = new ArrayList<Integer>(this.controllUnit.getMemory().getData().keySet());
		Collections.sort(sortetDataKeys);
		
		for (Integer curDataRow : sortetDataKeys) {
			Object[] data = {curDataRow, curDatas.get(curDataRow).toString(), curDatas.get(curDataRow).byteValue()};
			dataTable.addRow(data);
		}
	}
	
	private void displayRegisters() {
		akkumulator.setText("" + controllUnit.getRegisters()[0].read());
		register1.setText("" + controllUnit.getRegisters()[1].read());
		register2.setText("" + controllUnit.getRegisters()[2].read());
		register3.setText("" + controllUnit.getRegisters()[3].read());
		
		carryBit.setEnabled(controllUnit.getAlu().isCarryFlag());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		displayData();
		displayRegisters();
//TODO
		instructionTable.getSelectionModel().setAnchorSelectionIndex(0);
	}

	private void newCPU(ControlUnit cpu) {
		this.controllUnit = cpu;
		cpu.getClock().addObserver(this);
		displayInstructions();
		displayData();
		displayRegisters();
	}
}
