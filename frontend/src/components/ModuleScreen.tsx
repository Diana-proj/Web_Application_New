import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./ModuleScreen.css";

interface ModuleDto {
  moduleCode: string;
  moduleName: string;
  moduleContent: string;
  moduleCoordinator: string;
  workload: number;
  difficulty: number;
  usefulness: number;
  overallRating: number;
}

interface Review {
  reviewParagraph: string;
}

const ModuleScreen = () => {
  const { name, code } = useParams<{ name?: string; code?: string }>(); // Extract the module name from the URL
  const [module, setModule] = useState<ModuleDto | null>(null);
  const [reviews, setReviews] = useState<Review[]>([]); // State for reviews
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [showReviewForm, setShowReviewForm] = useState(false);
  const [review, setReview] = useState({
    reviewParagraph: "",
    workload: 1,
    difficulty: 1,
    usefulness: 1,
  });

  useEffect(() => {
    const fetchModuleAndReviews = async () => {
      try {
        const endpoint = name ? `/db/module/${name}` : `/db/code/${code}`;
        const response = await fetch(endpoint);
        const data = await response.json();

        const ratingsEndpoint = `/db/module/${data.moduleCode}/ratings`;
        const ratingsResponse = await fetch(ratingsEndpoint);
        const ratingsData = await ratingsResponse.json();

        const reviewsEndpoint = `/db/reviews/${data.moduleCode}`;
        const reviewsResponse = await fetch(reviewsEndpoint);
        const reviewsData = await reviewsResponse.json();

        // Combine module and ratings data
        const updatedModule = {
          ...data,
          ...ratingsData,
        };

        setModule(updatedModule);
        setReviews(reviewsData);
      } catch (error) {
        setError(true);
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    fetchModuleAndReviews();
  }, [name, code]);

  const handleReviewChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setReview({ ...review, [name]: value });
  };

  const handleStarClick = (field: string, value: number) => {
    setReview((prev) => ({ ...prev, [field]: value }));
  };

  const renderStars = (field: keyof typeof review, value: number) => {
    return Array.from({ length: 5 }, (_, index) => (
      <span
        key={index + 1}
        className={`star ${index + 1 <= value ? "filled" : ""}`}
        onClick={() => handleStarClick(field, index + 1)}
      >
        ★
      </span>
    ));
  };

  const submitReview = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch(`/db/reviews`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ...review,
          moduleCode: module?.moduleCode,
          moduleName: module?.moduleName,
        }),
      });
      if (response.ok) {
        alert("Review submitted successfully!");
        setShowReviewForm(false);
        window.location.reload();
      } else {
        alert("Failed to submit review");
      }
    } catch (error) {
      console.error("Error submitting review:", error);
    }
  };

  if (loading) {
    return <p>Loading module details...</p>;
  }

  if (error || !module) {
    return <p>Module not found or there was an error fetching the details.</p>;
  }

  return (
    <div className="module-container">
      <h1 className="module-title">
        {module.moduleName} ({module.moduleCode})
      </h1>
      <div className="description-section">
        <h2>Description</h2>
        <p>{module.moduleContent}</p>
      </div>
      <div className="overall-rating">
        Overall Rating: {module.overallRating.toFixed(2) || "No rating yet"}
      </div>

      <div className="rating-section">
        <div className="rating-box">
          <h3>Difficulty</h3>
          <p>{module.difficulty.toFixed(2)}</p>
        </div>
        <div className="rating-box">
          <h3>Usefulness</h3>
          <p>{module.usefulness.toFixed(2)}</p>
        </div>
        <div className="rating-box">
          <h3>Workload</h3>
          <p>{module.workload.toFixed(2)}</p>
        </div>
      </div>

      <button
        className="review-btn"
        onClick={() => setShowReviewForm(true)}
        style={{ marginTop: "20px" }}
      >
        Leave Review
      </button>

      {showReviewForm && (
        <div className="review-popup">
          <form onSubmit={submitReview}>
            <textarea
              name="reviewParagraph"
              value={review.reviewParagraph}
              onChange={handleReviewChange}
              placeholder="Write your review..."
              required
            />
            <div className="parameter-section">
              <label>
                Workload:
                <div className="stars-container">
                  {renderStars("workload", review.workload)}
                </div>
              </label>
              <label>
                Difficulty:
                <div className="stars-container">
                  {renderStars("difficulty", review.difficulty)}
                </div>
              </label>
              <label>
                Usefulness:
                <div className="stars-container">
                  {renderStars("usefulness", review.usefulness)}
                </div>
              </label>
            </div>
            <div className="form-actions">
              <button type="submit" className="submit-btn">
                Submit
              </button>
              <button
                type="button"
                onClick={() => setShowReviewForm(false)}
                className="cancel-btn"
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="reviews-section">
        <h2>Anonymous Reviews</h2>
        {reviews.length > 0 ? (
          <ul className="reviews-list">
            {reviews.map((review, index) => (
              <li key={index} className="review-item">
                <p>{review.reviewParagraph}</p>
              </li>
            ))}
          </ul>
        ) : (
          <p>No reviews yet. Be the first to leave a review!</p>
        )}
      </div>

      <a
        href="https://cdn.botpress.cloud/webchat/v2.2/shareable.html?configUrl=https://files.bpcontent.cloud/2024/11/17/00/20241117002559-IP6BKWJ0.json"
        target="_blank"
        rel="noopener noreferrer"
        className="chatbox-link"
      >
        <img
          src="https://i.pinimg.com/736x/46/35/0c/46350c6d3f597e1b2162093f87dde119.jpg"
          alt="Chatbox link icon"
        />
      </a>
    </div>
  );
};

export default ModuleScreen;
