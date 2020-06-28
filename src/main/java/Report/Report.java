package Report;

import java.util.ArrayList;
import java.util.List;

public class Report {

	protected String title = "";
	protected List<String> columnNames = new ArrayList<String>();
	protected List<List<String>> rows = new ArrayList<List<String>>();

	public List<String> getColumnNames() {
		return columnNames;
	}

	public List<List<String>> getRows() {
		return rows;
	}

	public String getTitle() {
		return title;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
