package com.broccoli.hacca.pageinfo;

public class CommentInfo {

	private String professorName;
	private String comment;
	private String studentName;
	private String professorLoginId;
	private String studentLoginId;
	
	public String getProfessorLoginId() {
		return professorLoginId;
	}
	public void setProfessorLoginId(String professorLoginId) {
		this.professorLoginId = professorLoginId;
	}
	public String getStudentLoginId() {
		return studentLoginId;
	}
	public void setStudentLoginId(String studentLoginId) {
		this.studentLoginId = studentLoginId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
