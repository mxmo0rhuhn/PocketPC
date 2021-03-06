/*
 * Copyright (c) 2012 - Reto Hablützel, Max Schrimpf, Désirée Sacher
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.powerpc.view.impl;

import ch.zhaw.powerpc.controller.Assembler;
import ch.zhaw.powerpc.controller.InputWriter;
import ch.zhaw.powerpc.controller.ProgramStarter;
import ch.zhaw.powerpc.model.ControlUnit;
import ch.zhaw.powerpc.model.MainMemory;
import ch.zhaw.powerpc.model.instructions.Instruction;
import ch.zhaw.powerpc.model.instructions.InvalidInstructionException;
import ch.zhaw.powerpc.view.Formatter;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

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
    private JTextField resultDec;
    private JTextField register3Dec;

    private JTextField anzBefehle;

    private String lastPath;

    private DefaultTableModel dataTable;
    private DefaultTableModel instructionTableModel;
    private DefaultTableModel currentInstructionTableModel;

    private final Formatter binFormat = new BinaryFormatter();

    private final ProgramStarter programStarter;
    private ControlUnit controllUnit;

    private JTable instructionTable;
    private JTable currentInstructionTable;

    // künstliche Redundanz zum Befehlszähler um GUI nicht immer neu aufbauen zu müssen.
    private int currentInstruction;

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
            e.printStackTrace();
        }

        this.setTitle("SSH EmOlator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(createMenu());

        buildFrame();
        newCPU(new ControlUnit(new MainMemory()));
        // Da hier noch keine Daten vorhanden sind, ist die Clock abgelaufen
        controllUnit.getClock().setStopped(true);

        this.setLocation(100, 0);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(100, 100));
        this.setResizable(true);
        this.pack();
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
        panel.setLayout(new GridLayout(1, 6));
        JButton nxt = new JButton("Nächster Schritt");
        nxt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!EvilGUI.this.controllUnit.getClock().isStopped()) {
                    EvilGUI.this.controllUnit.getClock().step();
                }
            }

        });

        JButton auto = new JButton("Langsam");
        auto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!EvilGUI.this.controllUnit.getClock().isStopped()) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            EvilGUI.this.controllUnit.getClock().startSlowMode();
                        }
                    }).start();
                }
            }
        });

        JButton fast = new JButton("Schnell");
        fast.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!EvilGUI.this.controllUnit.getClock().isStopped()) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            EvilGUI.this.controllUnit.getClock().startFastMode();
                        }
                    }).start();
                }
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
        akkuPanel.setLayout(new GridLayout(1, 2));
        akkuPanel.add(new JLabel("Akkumulator"));
        akkumulatorDec = new JTextField();
        akkumulatorDec.setEnabled(false);
        akkuPanel.add(akkumulatorDec);
        westInnerPanel.add(akkuPanel);

        akkumulatorBin = new JTextField();
        akkumulatorBin.setEnabled(false);
        westInnerPanel.add(akkumulatorBin);

        JPanel reg1Panel = new JPanel();
        reg1Panel.setLayout(new GridLayout(1, 2));
        reg1Panel.add(new JLabel("Register 1"));
        register1Dec = new JTextField();
        register1Dec.setEnabled(false);
        reg1Panel.add(register1Dec);
        westInnerPanel.add(reg1Panel);

        register1Bin = new JTextField();
        register1Bin.setEnabled(false);
        westInnerPanel.add(register1Bin);

        JPanel reg2Panel = new JPanel();
        reg2Panel.setLayout(new GridLayout(1, 2));
        reg2Panel.add(new JLabel("Register 2"));
        register2Dec = new JTextField();
        register2Dec.setEnabled(false);
        reg2Panel.add(register2Dec);
        westInnerPanel.add(reg2Panel);

        register2Bin = new JTextField();
        register2Bin.setEnabled(false);
        westInnerPanel.add(register2Bin);

        JPanel reg3Panel = new JPanel();
        reg3Panel.setLayout(new GridLayout(1, 2));
        reg3Panel.add(new JLabel("Register 3"));
        register3Dec = new JTextField();
        register3Dec.setEnabled(false);
        reg3Panel.add(register3Dec);
        westInnerPanel.add(reg3Panel);

        register3Bin = new JTextField();
        register3Bin.setEnabled(false);
        westInnerPanel.add(register3Bin);

        JPanel carryPanel = new JPanel();
        carryPanel.setLayout(new GridLayout(1, 2));
        carryPanel.add(new JLabel("Carry Bit"));
        carryBit = new JCheckBox();
        carryBit.setEnabled(false);
        carryPanel.add(carryBit);
        westInnerPanel.add(carryPanel);

        westInnerPanel.setPreferredSize(new Dimension(200, 300));

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(1, 2));
        resultPanel.add(new JLabel("Ergebnis"));
        resultDec = new JTextField();
        resultDec.setEnabled(false);
        resultPanel.add(resultDec);
        westInnerPanel.add(resultPanel);

        westPanel.add(westInnerPanel);
        return westPanel;
    }

    private JPanel createCenterPanel() {

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.add(buildInstructions());
        centerPanel.add(createDataTable());
        validate();

        return centerPanel;
    }

    private JScrollPane createInstructionsTable() {
        if (instructionTableModel == null) {
            instructionTableModel = new DefaultTableModel();
        }
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
        optionen.add(buildStoreItem());

        menuBar.add(optionen);

        // Nächster Release
        // menuBar.add(buildAnsichtMenue());

        return menuBar;
    }

    private JTabbedPane buildInstructions() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Derzeitige Befehle", createCurrentInstructionsTable());
        tabbedPane.addTab("Alle Befehle", createInstructionsTable());

        return tabbedPane;
    }

    private JMenuItem buildLoadItem() {
        JMenuItem load = new JMenuItem("Datei laden");
        load.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = null;
                if (lastPath != null) {
                    chooser = new JFileChooser(lastPath);
                } else {
                    chooser = new JFileChooser();
                }
                int returnVal = chooser.showOpenDialog(EvilGUI.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        lastPath = chooser.getSelectedFile().getAbsolutePath();
                        newCPU(programStarter.generateControlUnitFromInput(lastPath));
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

    private JMenuItem buildStoreItem() {
        JMenuItem write = new JMenuItem("Datei schreiben");
        write.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showSaveDialog(EvilGUI.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        InputWriter.writeState(chooser.getSelectedFile().getAbsolutePath(), getData(), getInstructions());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Datei " + chooser.getSelectedFile().getName()
                                + " konnte nicht gespeichert werden", "Fehler", JOptionPane.ERROR_MESSAGE);
                    } catch (InvalidInstructionException e2) {
                        JOptionPane.showMessageDialog(null, "Folgende Befehle konnten nicht geparsed werden: \n" + e2.getMessage(),
                                "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });
        return write;
    }

    private Map<Integer, String> getData() {
        HashMap<Integer, String> formattedData = new HashMap<Integer, String>();

        for (int i = 0; i < dataTable.getRowCount(); i++) {
            formattedData.put((Integer) dataTable.getValueAt(i, 0), (String) dataTable.getValueAt(i, 1));
        }
        return formattedData;
    }

    private Map<Integer, String> getInstructions() throws InvalidInstructionException {
        HashMap<Integer, String> formattedInstructions = new HashMap<Integer, String>();
        Assembler testAssembler = new Assembler();
        boolean canBeAssemled = true;
        String errors = "";

        for (int i = 0; i < instructionTable.getRowCount(); i++) {
            try {
                testAssembler.assemble((String) instructionTable.getValueAt(i, 1));
                formattedInstructions.put((Integer) instructionTable.getValueAt(i, 0), (String) instructionTable.getValueAt(i, 1));
            } catch (InvalidInstructionException e) {
                canBeAssemled = false;
                errors += (String) instructionTable.getValueAt(i, 1) + "\n";
                // instructionTable.setb
            }
        }

        if (!canBeAssemled) {
            throw new InvalidInstructionException(errors);
        }

        return formattedInstructions;
    }

    private void removeAllCurrentInstructionTableEntrys() {
        // this.instructionListEntrys.clear();
        while (0 < this.currentInstructionTableModel.getRowCount()) {
            this.currentInstructionTableModel.removeRow(0);
        }
    }

    private void removeAllInstructionTableEntrys() {
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

    private JScrollPane createCurrentInstructionsTable() {
        if (currentInstructionTableModel == null) {
            currentInstructionTableModel = new DefaultTableModel();
        }
        // Spalten - Namen
        currentInstructionTableModel.addColumn("Zeile");
        currentInstructionTableModel.addColumn("Mnemonic");
        currentInstructionTableModel.addColumn("Binär");

        // Tabelle
        currentInstructionTable = new JTable(currentInstructionTableModel);
        currentInstructionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        currentInstructionTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        currentInstructionTable.getColumnModel().getColumn(1).setPreferredWidth(110);
        currentInstructionTable.getColumnModel().getColumn(2).setPreferredWidth(145);

        currentInstructionTable.setEnabled(false);
        currentInstructionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Tabelle scrollbar machen
        JScrollPane scrolly = new JScrollPane(currentInstructionTable);
        scrolly.setPreferredSize(new Dimension(305, 500));

        return scrolly;
    }

    private void displayCurrentInstructions() {

        if (this.controllUnit.getProgramCounter() - 2 == currentInstruction) {
            // Muss nur den neusten Befehl holen
            if (this.controllUnit.getProgramCounter() > 110) {
                // letzten Befehl löschen
                currentInstructionTableModel.removeRow(0);
            }

            try {
                int curInstruction = this.controllUnit.getProgramCounter() + 20;
                Object[] data = {curInstruction, this.controllUnit.getMemory().getInstructions().get(curInstruction).toString(),
                        binFormat.formatNumber(this.controllUnit.getMemory().getInstructions().get(curInstruction).getBinary(), 16)};
                currentInstructionTableModel.addRow(data);
            } catch (Exception e) {
                // oops die gabs wohl nimmer...
            }

        } else {
            // Derzeitige Befehle neu aufbauen
            removeAllCurrentInstructionTableEntrys();

            Map<Integer, Instruction> curInstructions = this.controllUnit.getMemory().getInstructions();

            ArrayList<Integer> sortetInstructionKeys = new ArrayList<Integer>(curInstructions.keySet());
            Collections.sort(sortetInstructionKeys);

            for (Integer curInstruction : sortetInstructionKeys) {
                if (curInstruction >= this.controllUnit.getProgramCounter() - 10
                        && curInstruction <= this.controllUnit.getProgramCounter() + 20) {
                    try {
                        Object[] data = {curInstruction, curInstructions.get(curInstruction).toString(),
                                binFormat.formatNumber(curInstructions.get(curInstruction).getBinary(), 16)};
                        currentInstructionTableModel.addRow(data);
                    } catch (Exception e) {
                        // war wohl nix ...
                    }
                }
            }
        }
        currentInstruction = this.controllUnit.getProgramCounter();
        int next = 0;
        // Logik was markiert werden muss
        if (currentInstruction == 100) {
            next = 0;
        } else if (currentInstruction <= 110) {
            next = (currentInstruction - 100) / 2;
        } else {
            next = 5;
        }
        currentInstructionTable.getSelectionModel().setSelectionInterval(next, next);
    }

    private void displayInstructions() {
        removeAllInstructionTableEntrys();

        Map<Integer, Instruction> curInstructions = this.controllUnit.getMemory().getInstructions();

        ArrayList<Integer> sortetInstructionKeys = new ArrayList<Integer>(curInstructions.keySet());
        Collections.sort(sortetInstructionKeys);

        for (Integer curInstruction : sortetInstructionKeys) {
            Object[] data = {curInstruction, curInstructions.get(curInstruction).toString(),
                    binFormat.formatNumber(curInstructions.get(curInstruction).getBinary(), 16)};
            instructionTableModel.addRow(data);
        }
    }

    private void displayData() {
        removeAllDataTableEntrys();

        Map<Integer, Short> curDatas = this.controllUnit.getMemory().getData();

        ArrayList<Integer> sortetDataKeys = new ArrayList<Integer>(this.controllUnit.getMemory().getData().keySet());
        Collections.sort(sortetDataKeys);

        for (Integer curDataRow : sortetDataKeys) {
            Object[] data = {curDataRow, curDatas.get(curDataRow).toString(), binFormat.formatNumber(curDatas.get(curDataRow), 16)};
            dataTable.addRow(data);
        }
    }

    private void displayRegisters() {
        akkumulatorBin.setText(binFormat.formatNumber(controllUnit.getRegisters()[0].read(), 16));
        register1Bin.setText(binFormat.formatNumber(controllUnit.getRegisters()[1].read(), 16));
        register2Bin.setText(binFormat.formatNumber(controllUnit.getRegisters()[2].read(), 16));
        register3Bin.setText(binFormat.formatNumber(controllUnit.getRegisters()[3].read(), 16));

        akkumulatorDec.setText("" + controllUnit.getRegisters()[0].read());
        register1Dec.setText("" + controllUnit.getRegisters()[1].read());
        register2Dec.setText("" + controllUnit.getRegisters()[2].read());
        register3Dec.setText("" + controllUnit.getRegisters()[3].read());

        anzBefehle.setText("" + controllUnit.getClock().getStepCounter());

        carryBit.setEnabled(controllUnit.getAlu().isCarryFlag());
    }

    @Override
    public void update(Observable o, Object arg) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                displayData();
                displayRegisters();
                displayCurrentInstructions();
                updateResult();

            }

        });
    }

    private void updateResult() {
        if (this.controllUnit.getMemory().readData(506) == 0) {
            resultDec.setText("" + this.controllUnit.getMemory().readData(504));
        } else {
            String myLongNumber = "" + binFormat.formatNumber(this.controllUnit.getMemory().readData(506), 16)
                    + binFormat.formatNumber(this.controllUnit.getMemory().readData(504), 16);
            if (myLongNumber.charAt(0) == ('1')) {
                resultDec.setText("" + ((~Integer.parseInt(myLongNumber.substring(1), 2)) + 1));
            } else {
                resultDec.setText("" + Integer.parseInt(myLongNumber, 2));
            }
        }
    }

    private void newCPU(ControlUnit cpu) {
        this.controllUnit = cpu;
        cpu.getClock().addObserver(this);
        currentInstruction = 0;
        displayCurrentInstructions();
        displayInstructions();
        displayData();
        displayRegisters();
    }
}
