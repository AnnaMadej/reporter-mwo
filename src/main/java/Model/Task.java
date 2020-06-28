package Model;

import java.util.Date;

public class Task {

	private Date taskDate;
	private String projectName;
	private String taskName;
	private Double hours;

	public Task(Date taskDate, java.lang.String projectName, java.lang.String taskName, double hours) {
		this.taskDate = taskDate;
		this.projectName = projectName;
		this.taskName = taskName;
		this.hours = hours;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (hours == null) {
			if (other.hours != null)
				return false;
		} else if (!hours.equals(other.hours))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (taskDate == null) {
			if (other.taskDate != null)
				return false;
		} else if (!taskDate.equals(other.taskDate))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}

	public Double getHours() {
		return hours;
	}

	public java.lang.String getProjectName() {
		return projectName;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public java.lang.String getTaskName() {
		return taskName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hours == null) ? 0 : hours.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((taskDate == null) ? 0 : taskDate.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public void setProjectName(java.lang.String projectName) {
		this.projectName = projectName;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public void setTaskName(java.lang.String taskName) {
		this.taskName = taskName;
	}

	@Override
	public String toString() {
		return "Task [taskDate=" + taskDate + ", projectName=" + projectName + ", taskName=" + taskName + ", hours="
				+ hours + "]";
	}

}