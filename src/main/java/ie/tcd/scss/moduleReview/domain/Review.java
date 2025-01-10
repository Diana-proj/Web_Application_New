package ie.tcd.scss.moduleReview.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "Reviews")
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Review_Id")
    private Long reviewId;

    @Column(name = "Module_Code")
    private String moduleCode;

    @Column(name = "Module_Name")
    private String moduleName;

    @Column(name = "Module_Content")
    private String reviewParagraph;

    @Column(name = "Workload")
    private double workload;

    @Column(name = "Difficulty")
    private double difficulty;

    @Column(name = "Usefulness")
    private double usefulness;

    @Column(name = "Overall_Rating")
    private double overallRating;

    public Review() {
        // Default constructor
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

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

    public String getReviewParagraph() {
        return reviewParagraph;
    }

    public void setReviewParagraph(String reviewParagraph) {
        this.reviewParagraph = reviewParagraph;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {
        this.workload = workload;
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

    public Review(String moduleCode, String moduleName, String reviewParagraph, double workload, double difficulty, double usefulness) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.reviewParagraph = reviewParagraph;
        this.workload = workload;
        this.difficulty = difficulty;
        this.usefulness = usefulness;
        this.overallRating = ((6-workload) + (6-difficulty) + usefulness) / 3.0;
    }
    
}
