package ie.tcd.scss.moduleReview.service;

import ie.tcd.scss.moduleReview.Repo.ReviewRepository;
import ie.tcd.scss.moduleReview.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(String moduleCode, String moduleName, String reviewParagraph, double workload, double difficulty, double usefulness) {
        Review review = new Review();
        review.setModuleCode(moduleCode);
        review.setModuleName(moduleName);
        review.setReviewParagraph(reviewParagraph);
        review.setWorkload(workload);
        review.setDifficulty(difficulty);
        review.setUsefulness(usefulness);
        review.setOverallRating((workload + difficulty + usefulness)/ 3);
        return reviewRepository.save(review);
    }

	

    // Method to delete a review by ID
    public void deleteReviewById(Long reviewId) {
        // Check if the review exists
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new IllegalArgumentException("Review not found with id: " + reviewId);
        }
    }
}
