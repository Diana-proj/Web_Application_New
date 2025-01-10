package ie.tcd.scss.moduleReview.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ie.tcd.scss.moduleReview.domain.Review;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByModuleCode(String moduleCode);
    Review findByReviewId(Long reviewId);
}
