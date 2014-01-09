package com.example.yes;

public class Task {
	int id;
	int priority;
	String dueDate;
	String taskName;
	boolean done;

	public Task() {
	}

	public Task(int priority, String dueDate, String taskName) {
		this.priority = priority;
		this.dueDate = dueDate;
		this.taskName = taskName;

	}

	@Override
	public String toString() {
		return "Task [id" + id + " priority" + priority + " date" + dueDate
				+ " task name" + taskName + "]";
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getId() {
		return this.id;
	}

	public int getPriority() {
		return this.priority;
	}

	public String getDate() {
		return this.dueDate;
	}

	public String getTaskName() {
		return this.taskName;
	}

}
