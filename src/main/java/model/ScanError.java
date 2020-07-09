package model;

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

    public ScanError(String filename, String project, int row, String cell,
            String description) {
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ScanError other = (ScanError) obj;
        if (this.cell == null) {
            if (other.cell != null) {
                return false;
            }
        } else if (!this.cell.equals(other.cell)) {
            return false;
        }
        if (this.description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!this.description.equals(other.description)) {
            return false;
        }
        if (this.filename == null) {
            if (other.filename != null) {
                return false;
            }
        } else if (!this.filename.equals(other.filename)) {
            return false;
        }
        if (this.project == null) {
            if (other.project != null) {
                return false;
            }
        } else if (!this.project.equals(other.project)) {
            return false;
        }
        if (this.row == null) {
            if (other.row != null) {
                return false;
            }
        } else if (!this.row.equals(other.row)) {
            return false;
        }
        return true;
    }

    public String getCell() {
        return this.cell;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getProject() {
        return this.project;
    }

    public Integer getRow() {
        return this.row;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.cell == null ? 0 : this.cell.hashCode());
        result = prime * result + (this.description == null ? 0 : this.description.hashCode());
        result = prime * result + (this.filename == null ? 0 : this.filename.hashCode());
        result = prime * result + (this.project == null ? 0 : this.project.hashCode());
        result = prime * result + (this.row == null ? 0 : this.row.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ScanError [filename=" + this.filename + ", project=" + this.project + ", row="
                + this.row + ", cell=" + this.cell + ", description=" + this.description + "]"
                + "\n";
    }

}