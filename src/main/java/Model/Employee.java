package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Employee implements Cloneable {

	private List<Task> taskList = new ArrayList<Task>();
	private SortedSet<String> projects = new TreeSet<String>();
	private String name;
	private String surname;

	public Employee() {

	}

	public Employee(java.lang.String name, java.lang.String surname) {
		this.name = name;
		this.surname = surname;
	}

	public void addTask(Task task) {
		this.taskList.add(task);
		this.projects.add(task.getProjectName());
	}

	public void addTasks(List<Task> tasks) {
		this.taskList.addAll(tasks);
		for (Task task : tasks) {
			this.projects.add(task.getProjectName());
		}
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {

			Employee employee = new Employee(this.getName(), this.getSurname());
			employee.setTaskList(this.getTaskList());
			return employee;
		}
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
		Employee other = (Employee) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.surname == null) {
			if (other.surname != null) {
				return false;
			}
		} else if (!this.surname.equals(other.surname)) {
			return false;
		}
		return true;
	}

	public Map<String, Double> getHoursByProject(int i) {
		Map<String, Double> hours = new TreeMap<String, Double>();
		for (Task task : this.getTaskList()) {
			String projectName = task.getProjectName();
			if (hours.containsKey(projectName)) {
				hours.put(projectName, hours.get(projectName) + task.getHours());
			} else {
				hours.put(projectName, task.getHours());
			}
		}
		return hours;
	}

	public String getName() {
		return this.name;
	}

	public String getNameAndSurname() {
		return this.getName() + " " + this.getSurname();
	}

	public Double getProjectHours(String project) {
		Double sum = 0.0;

		for (Task task : this.taskList) {
			if (task.getProjectName().equals(project)) {
				sum += task.getHours();
			}
		}

		return sum;
	}

	public Set<String> getProjects() {
		return this.projects;
	}

	public String getSurname() {
		return this.surname;
	}

	public List<Task> getTaskList() {
		return this.taskList;
	}

	public double getTotalHours() {
		double sum = 0;
		for (Task task : this.taskList) {
			sum += task.getHours();
		}
		return sum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.name == null ? 0 : this.name.hashCode());
		result = prime * result + (this.surname == null ? 0 : this.surname.hashCode());
		return result;
	}

	public void removeTask(Task task) {
		this.taskList.remove(task);
		boolean removeProjectName = true;
		for (Task tsk : this.taskList) {
			if (tsk.getProjectName().equals(task.getProjectName())) {
				removeProjectName = false;
			}
		}
		if (removeProjectName) {
			this.projects.remove(task.getProjectName());
		}
	}

	public void setTaskList(List<Task> taskList) {
		this.projects = new TreeSet<String>();
		this.taskList = taskList;
		for (Task task : taskList) {
			this.projects.add(task.getProjectName());
		}

	}

	@Override
	public String toString() {
		return "Employee [taskList=" + this.taskList + ", name=" + this.name + ", surname=" + this.surname + "]";
	}

}
