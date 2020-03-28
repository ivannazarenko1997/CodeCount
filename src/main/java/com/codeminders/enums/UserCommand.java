package com.codeminders.enums;

public enum UserCommand {
	LS("ls"), PWD("pwd"), CD("cd"), VI("vi"), ROOT("/"),
	UP(".."), EXIT("exit"), NOT_EXIST("not_exist"), STING_PATH("string_path");
 
    private String type;

	UserCommand(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
