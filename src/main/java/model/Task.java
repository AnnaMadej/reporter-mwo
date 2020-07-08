package model;

import java.util.Date;

public class Task {

    private Date taskDate;
    private String projectName;
    private String taskName;
    private Double hours;

    public Task(Date taskDate, java.lang.String projectName, java.lang.String taskName,
            double hours) {
        this.taskDate = taskDate;
        this.projectName = projectName;
        this.taskName = taskName;
        this.hours = hours;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        if (this.hours == null) {
            if (other.hours != null) {
                return false;
            }
        } else if (!this.hours.equals(other.hours)) {
            return false;
        }
        if (this.projectName == null) {
            if (other.projectName != null) {
                return false;
            }
        } else if (!this.projectName.equals(other.projectName)) {
            return false;
        }
        if (this.taskDate == null) {
            if (other.taskDate != null) {
                return false;
            }
        } else if (!this.taskDate.equals(other.taskDate)) {
            return false;
        }
        if (this.taskName == null) {
            if (other.taskName != null) {
                return false;
            }
        } else if (!this.taskName.equals(other.taskName)) {
            return false;
        }
        return true;
    }

    public Double getHours() {
        return this.hours;
    }

    public java.lang.String getProjectName() {
        return this.projectName;
    }

    public Date getTaskDate() {
        return this.taskDate;
    }

    public java.lang.String getTaskName() {
        return this.taskName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.hours == null ? 0 : this.hours.hashCode());
        result = prime * result + (this.projectName == null ? 0 : this.projectName.hashCode());
        result = prime * result + (this.taskDate == null ? 0 : this.taskDate.hashCode());
        result = prime * result + (this.taskName == null ? 0 : this.taskName.hashCode());
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
        return "Task [taskDate=" + this.taskDate + ", projectName=" + this.projectName
                + ", taskName=" + this.taskName + ", hours=" + this.hours + "]";
    }

}