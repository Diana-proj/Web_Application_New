package ie.tcd.scss.moduleReview.dto;

import lombok.Getter;

@Getter
public class ReviewDto {

    private String reviewId;
    private String moduleCode;
    private String moduleName;
    private String reviewParagraph;
    private double workload;
    private double difficulty;
    private double usefulness;

    // Default Constructor
    public ReviewDto() {
    }

    public ReviewDto( String moduleCode, String moduleName, String moduleContent, double workload, double difficulty, double usefulness ) {
        // this.reviewId = reviewId;
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.reviewParagraph = moduleContent;
        this.workload = workload;
        this.difficulty = difficulty;
        this.usefulness = usefulness;;
    }

    // Getters and Setters

    // public void setReviewId(String reviewId) {
    //     this.reviewId = reviewId;
    // }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setReviewParagraph(String reviewParagraph) {
        this.reviewParagraph = reviewParagraph;
    }

    public void setWorkload(double workload) {
        this.workload = workload;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public void setUsefulness(double usefulness) {
        this.usefulness = usefulness;
    }

    @Override
    public String toString() {
        return "ModuleDTO{" +
                // "reviewId='" + reviewId + '\'' +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", reviewParagraph='" + reviewParagraph + '\'' +
                ", workload=" + workload +
                ", difficulty=" + difficulty +
                ", usefulness=" + usefulness +
                ", overallRating=" + 7+
                '}';
    }
}

