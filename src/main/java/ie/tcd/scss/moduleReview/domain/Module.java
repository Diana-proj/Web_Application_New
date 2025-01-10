package ie.tcd.scss.moduleReview.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Modules")
public class Module {

    @Id
    @Column(name = "Module_Code")
    private String moduleCode;

    @Column(name = "Module_Name")
    private String moduleName;

    @Column(name = "Module_Content")
    private String moduleContent;

    @Column(name = "Module_Coordinator")
    private String moduleCoordinator;

    @Column(name = "Workload")
    private double workload;

    @Column(name = "Difficulty")
    private double difficulty;

    @Column(name = "Usefulness")
    private double usefulness;

    @Column(name = "Overall_Rating")
    private double overallRating;

    public Module() {

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
    

    // Parameterized Constructor
    public Module(String moduleCode, String moduleName, String moduleContent, String moduleCoordinator, double workload, double difficulty, double usefulness) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleContent = moduleContent;
        this.moduleCoordinator = moduleCoordinator;
        this.workload = workload;
        this.difficulty = difficulty;
        this.usefulness = usefulness;
        this.overallRating = ((6-workload) + (6-difficulty) + usefulness) / 3.0; // Assuming all ratings are between 1 and 5
    }
    
}
