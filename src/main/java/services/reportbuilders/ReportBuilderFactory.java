package services.reportbuilders;

public class ReportBuilderFactory {
    public ReportBuilder getReportBuilder(String number) {

        if (number == null) {
            return null;
        }
        switch (number) {
            case "1":
                return new Report1Builder();
            case "2":
                return new Report2Builder();

            case "3":
                return new Report3Builder();

            case "4":
                return new Report4Builder();

            case "5":
                return new Report5Builder();

            default:
                return null;
        }

    }

}
