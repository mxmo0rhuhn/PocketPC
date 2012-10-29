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
import javax.swing.table.DefaultTableModel;

import ch.zhaw.powerpc.controller.ProgramStarter;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.instructions.Instruction;
import ch.zhaw.powerpc.view.Formatter;

public class EvilGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private JTextField akkumulatorBin;
	private JTextField register1Bin;
	private JTextField register2Bin;
	private JTextField register3Bin;
	private JCheckBox carryBit;
	
	private JTextField akkumulatorDec;
	private JTextField register1Dec;
	private JTextField register2Dec;
	private JTextField register3Dec;

	private JTextField anzBefehle;
	
	private DefaultTableModel dataTable;
	private DefaultTableModel instructionTableModel;

	private final Formatter binFormat = new BinaryFormatter();

	private final ProgramStarter programStarter;
	private ControlUnit controllUnit;

	private JTable instructionTable;

	public EvilGUI(ProgramStarter programStarter) {
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
		panel.setLayout(new GridLayout(1,6));
		JButton nxt = new JButton("Nächster Schritt");
		nxt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EvilGUI.this.controllUnit.getClock().step();
			}

		});

		JButton auto = new JButton("Langsam");
		auto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						EvilGUI.this.controllUnit.getClock().startSlowMode();
					}
				}).start();
			}
		});

		JButton fast = new JButton("Schnell");
		fast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EvilGUI.this.controllUnit.getClock().step();
				new Thread(new Runnable() {

					@Override
					public void run() {
						EvilGUI.this.controllUnit.getClock().startFastMode();
					}
				}).start();
			}

		});

		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EvilGUI.this.controllUnit.getClock().setPaused(true);
			}

		});
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EvilGUI.this.controllUnit.getClock().setStopped(true);
			}
		});

		anzBefehle = new JTextField();
		anzBefehle.setEnabled(false);
		
		panel.add(anzBefehle);
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

		westInnerPanel.setLayout(new GridLayout(10, 1));
		
		JPanel akkuPanel = new JPanel();
		akkuPanel.setLayout(new GridLayout(1,2));
		akkuPanel.add(new JLabel("Akkumulator"));
		akkumulatorDec = new JTextField();
		akkumulatorDec.setEnabled(false);
		akkuPanel.add(akkumulatorDec);
		westInnerPanel.add(akkuPanel);
		
		akkumulatorBin = new JTextField();
		akkumulatorBin.setEnabled(false);
		westInnerPanel.add(akkumulatorBin);
		
		JPanel reg1Panel = new JPanel();
		reg1Panel.setLayout(new GridLayout(1,2));
		reg1Panel.add(new JLabel("Register 1"));
		register1Dec = new JTextField();
		register1Dec.setEnabled(false);
		reg1Panel.add(register1Dec);
		westInnerPanel.add(reg1Panel);

		register1Bin = new JTextField();
		register1Bin.setEnabled(false);
		westInnerPanel.add(register1Bin);
		
		JPanel reg2Panel = new JPanel();
		reg2Panel.setLayout(new GridLayout(1,2));
		reg2Panel.add(new JLabel("Register 2"));
		register2Dec = new JTextField();
		register2Dec.setEnabled(false);
		reg2Panel.add(register2Dec);
		westInnerPanel.add(reg2Panel);
		
		register2Bin = new JTextField();
		register2Bin.setEnabled(false);
		westInnerPanel.add(register2Bin);
		
		JPanel reg3Panel = new JPanel();
		reg3Panel.setLayout(new GridLayout(1,2));
		reg3Panel.add(new JLabel("Register 3"));
		register3Dec = new JTextField();
		register3Dec.setEnabled(false);
		reg3Panel.add(register3Dec);
		westInnerPanel.add(reg3Panel);

		register3Bin = new JTextField();
		register3Bin.setEnabled(false);
		westInnerPanel.add(register3Bin);
		
		JPanel carryPanel = new JPanel();
		carryPanel.setLayout(new GridLayout(1,2));
		carryPanel.add(new JLabel("Carry Bit"));
		carryBit = new JCheckBox();
		carryBit.setEnabled(false);
		carryPanel.add(carryBit);
		westInnerPanel.add(carryPanel);

		westInnerPanel.setPreferredSize(new Dimension(200, 300));

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
		instructionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		instructionTable.getColumnModel().getColumn(0).setPreferredWidth(45);
		instructionTable.getColumnModel().getColumn(1).setPreferredWidth(110);
		instructionTable.getColumnModel().getColumn(2).setPreferredWidth(145);

		// Tabelle scrollbar machen
		JScrollPane scrolly = new JScrollPane(instructionTable);
		scrolly.setPreferredSize(new Dimension(305, 500));

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
		tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tab.getColumnModel().getColumn(0).setPreferredWidth(45);
		tab.getColumnModel().getColumn(1).setPreferredWidth(110);
		tab.getColumnModel().getColumn(2).setPreferredWidth(145);
		
		// Tabelle scrollbar machen
		JScrollPane scrolly = new JScrollPane(tab);
		scrolly.setPreferredSize(new Dimension(305, 500));

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
				int returnVal = chooser.showOpenDialog(EvilGUI.this);
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
			Object[] data = {curInstruction, curInstructions.get(curInstruction).toString(), binFormat.formatNumber(curInstructions.get(curInstruction).getBinary(), 16)};
			instructionTableModel.addRow(data);
		}
	}

	private void displayData() {
		removeAllDataTableEntrys();
		
		Map<Integer, Short> curDatas = this.controllUnit.getMemory().getData();
		
		ArrayList<Integer> sortetDataKeys = new ArrayList<Integer>(this.controllUnit.getMemory().getData().keySet());
		Collections.sort(sortetDataKeys);
		
		for (Integer curDataRow : sortetDataKeys) {
			Object[] data = {curDataRow, curDatas.get(curDataRow).toString(), binFormat.formatNumber(curDatas.get(curDataRow),16)};
			dataTable.addRow(data);
		}
	}
	
	private void displayRegisters() {
		akkumulatorBin.setText(binFormat.formatNumber(controllUnit.getRegisters()[0].read(),16));
		register1Bin.setText(binFormat.formatNumber(controllUnit.getRegisters()[1].read(),16));
		register2Bin.setText(binFormat.formatNumber(controllUnit.getRegisters()[2].read(),16));
		register3Bin.setText(binFormat.formatNumber(controllUnit.getRegisters()[3].read(),16));
		
		akkumulatorDec.setText("" + controllUnit.getRegisters()[0].read());
		register1Dec.setText("" + controllUnit.getRegisters()[1].read());
		register2Dec.setText("" + controllUnit.getRegisters()[2].read());
		register3Dec.setText("" + controllUnit.getRegisters()[3].read());
		
		anzBefehle.setText("" + controllUnit.getClock().getStepCounter());
		
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
