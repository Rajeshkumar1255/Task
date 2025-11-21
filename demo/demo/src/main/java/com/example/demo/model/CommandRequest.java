package com.example.demo.model;

public class CommandRequest {
	
	private String commands; 
	public CommandRequest() {
	}

	public CommandRequest(String commands) {
		this.commands = commands;
	}

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
	}
}