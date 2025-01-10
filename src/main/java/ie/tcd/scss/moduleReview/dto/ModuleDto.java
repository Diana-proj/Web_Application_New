package ie.tcd.scss.moduleReview.dto;

public class ModuleDto {

    private String moduleCode;
    private String moduleName;
    private String moduleContent;
    private String moduleCoordinator;
    private double workload;
    private double difficulty;
    private double usefulness;
    private double overallRating;

    // Default Constructor
    public ModuleDto() {
    }

    // Parameterized Constructor
    public ModuleDto(String moduleCode, String moduleName, String moduleContent, String moduleCoordinator, double workload, double difficulty, double usefulness, double overallRating) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleContent = moduleContent;
        this.moduleCoordinator = moduleCoordinator;
        this.workload = workload;
        this.difficulty = difficulty;
        this.usefulness = usefulness;
        this.overallRating = overallRating;
    }

    // Getters and Setters
    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(String moduleContent) {
        this.moduleContent = moduleContent;
    }

    public String getModuleCoordinator() {
        return moduleCoordinator;
    }

    public void setModuleCoordinator(String moduleCoordinator) {
        this.moduleCoordinator = moduleCoordinator;
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {
        this.workload = workload;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getUsefulness() {
        return usefulness;
    }

    public void setUsefulness(double usefulness) {
        this.usefulness = usefulness;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    @Override
    public String toString() {
        return "ModuleDTO{" +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", moduleContent='" + moduleContent + '\'' +
                ", moduleCoordinator='" + moduleCoordinator + '\'' +
                ", workload=" + workload +
                ", difficulty=" + difficulty +
                ", usefulness=" + usefulness +
                ", overallRating=" + overallRating +
                '}';
    }
}
