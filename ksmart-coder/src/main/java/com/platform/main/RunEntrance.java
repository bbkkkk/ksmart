package com.platform.main;

public class RunEntrance {
	
	public static void main(String[] args) {
		CodeMeachine.generateDaoFile("cms_file", "com.platform.main", "File","File.java");
		CodeMeachine.generateVoFile("cms_file", "com.platform.main", "File","FileVO.java");
	}
	
}
