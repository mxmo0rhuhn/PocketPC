package ch.zhaw.powerpc.controller;

import org.junit.Assert;
import org.junit.Test;

import ch.zhaw.powerpc.controller.ProgramStarter;

public class ProgramStarterTest{
	
	public void readFile(){
		ProgramStarter test = new ProgramStarter();
		Assert.assertTrue(test.stringMemory2MainMemory(true)); // Umwandlung 
	}
	
}