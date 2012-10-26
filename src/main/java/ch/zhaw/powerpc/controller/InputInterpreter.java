//package ch.zhaw.powerpc.controller;
//
//import ch.zhaw.powerpc.model.MainMemory;
//
//public class InputInterpreter {
//
//	public InputInterpreter() {
//
//	}
//
//	public MainMemory generateMainMemory(String[] stringInput) {
//		int cnt = 100;
//		int[] initialMemory = new int[530]; // TODO: check for defined Memory
//		for (String instruction : stringInput) {
//			if (instruction.contains("=")) {
//				// Split in 2 teile
//				// 1. Teil Memory Wert
//				fillMemory(initialMemory, instruction.split("="));
//			} else {
//				addToMemory(initialMemory, instruction, cnt);
//				cnt++;
//			}
//		}
//		return new MainMemory(initialMemory);
//	}
//
//	private void addToMemory(int[] initialMemory, String instruction, int cnt) {
//		initialMemory[cnt] = Integer.parseInt(instruction, 2);
//	}
//
//	private void fillMemory(int[] initialMemory, String[] split) {
//		int address = Integer.parseInt(split[0]);
//		int value = Integer.parseInt(split[1], 2);
//
//		initialMemory[address] = value;
//
//	}
//
// }
