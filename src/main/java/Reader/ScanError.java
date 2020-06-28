package Reader;

public class ScanError {
	private String filename = "";
	private String project = "";
	private Integer row = null;
	private String cell = "";
	private String description = "";

	public ScanError(String filename, String project, int row, String description) {
		super();
		this.filename = filename;
		this.project = project;
		this.row = row;
		this.description = description;
	}

	public ScanError(String filename, String project, int row, String cell, String description) {
		super();
		this.filename = filename;
		this.project = project;
		this.row = row;
		this.cell = cell;
		this.description = description;
	}

	public ScanError(String path, String sheetName, String description) {
		this.filename = path;
		this.project = sheetName;
		this.description = description;
	}

	public ScanError(String filename, String project, String cell, String description) {
		super();
		this.filename = filename;
		this.project = project;
		this.cell = cell;
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScanError other = (ScanError) obj;
		if (cell == null) {
			if (other.cell != null)
				return false;
		} else if (!cell.equals(other.cell))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}

	public String getCell() {
		return cell;
	}

	public String getDescription() {
		return description;
	}

	public String getFilename() {
		return filename;
	}

	public String getProject() {
		return project;
	}

	public Integer getRow() {
		return row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cell == null) ? 0 : cell.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((row == null) ? 0 : row.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ScanError [filename=" + filename + ", project=" + project + ", row=" + row + ", cell=" + cell
				+ ", description=" + description + "]" + "\n";
	}

}