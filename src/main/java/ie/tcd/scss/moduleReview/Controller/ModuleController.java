package ie.tcd.scss.moduleReview.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.scss.moduleReview.Repo.ModuleRepository;
import ie.tcd.scss.moduleReview.Repo.ReviewRepository;
import ie.tcd.scss.moduleReview.domain.Module;
import ie.tcd.scss.moduleReview.domain.Review;
import ie.tcd.scss.moduleReview.dto.ModuleDto;
import ie.tcd.scss.moduleReview.dto.ReviewDto;
import ie.tcd.scss.moduleReview.service.ModuleService;
import ie.tcd.scss.moduleReview.service.ReviewService;


@RestController
@RequestMapping("/db") // Allow requests from React frontend
public class ModuleController {
    private final ModuleService moduleService;
    private final ModuleRepository moduleRepository;
    private final ReviewRepository ReviewRepository;
    private final ReviewService reviewService;

    @Autowired
    public ModuleController(ModuleRepository moduleRepository, ModuleService moduleService, ReviewRepository reviewRepository, ReviewService reviewService) {
        this.moduleRepository = moduleRepository;
        this.moduleService = moduleService;
        this.ReviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }


    @GetMapping
    public List<Module> getModulesFromDatabase() {
        return moduleRepository.findAll();
        
    }


    @GetMapping("/module/{name}")
    public Optional<Module> getModuleByName(@PathVariable String name) {
        return moduleRepository.findByModuleName(name);
    }

    @GetMapping("/code/{code}")
    public Optional<Module> getModuleByCode(@PathVariable String code) {
        return moduleRepository.findByModuleCode(code);
    }

    // Update module details by module code
    @PutMapping("/module/{code}")
    public ResponseEntity<String> changeDetails(@PathVariable String code, @RequestBody ModuleDto moduleDto ) {
        moduleService.deleteModule(code);
        moduleService.createModule(moduleDto.getModuleCode(), moduleDto.getModuleName(), moduleDto.getModuleContent(), moduleDto.getModuleCoordinator(), moduleDto.getWorkload(), moduleDto.getDifficulty(), moduleDto.getUsefulness());
        return ResponseEntity.ok(code + "details updated.");

    }

    // Update module details by module name
    @PutMapping("/module/{name}")
    public ResponseEntity<String> changeDetailsByName(@PathVariable String name, @RequestBody ModuleDto moduleDto ) {
        moduleService.deleteModuleByName(name);
        moduleService.createModule(moduleDto.getModuleCode(), moduleDto.getModuleName(), moduleDto.getModuleContent(), moduleDto.getModuleCoordinator(), moduleDto.getWorkload(), moduleDto.getDifficulty(), moduleDto.getUsefulness());
        return ResponseEntity.ok(name + "details updated.");

    }

    // Create a new module
    @PostMapping("/module")
    public ResponseEntity<Module> postModule(@RequestBody ModuleDto moduleDto) {
        Module module = moduleService.createModule(moduleDto.getModuleCode(), moduleDto.getModuleName(), moduleDto.getModuleContent(), moduleDto.getModuleCoordinator(), moduleDto.getWorkload(), moduleDto.getDifficulty(), moduleDto.getUsefulness());
        return ResponseEntity.status(HttpStatus.CREATED).body(module);
    }

    // Delete a module by code
    @DeleteMapping("/module/{code}")
    public ResponseEntity<String> deleteModule(@PathVariable String code) {
        moduleService.deleteModule(code);
        return ResponseEntity.ok(code + " deleted.");
    }

    // Delete a module by name
    @DeleteMapping("/module/{name}")
    public ResponseEntity<String> deleteModuleByName(@PathVariable String name) {
        moduleService.deleteModuleByName(name);
        return ResponseEntity.ok(name + " deleted.");
    }

    //Review Endpoints
    @GetMapping("/reviews")
    public List<Review> getAllReviewsFromDatabase() {
        return ReviewRepository.findAll();
    }

    @GetMapping("/reviews/{moduleCode}")
    public List <Review> getAllReviews(@PathVariable String moduleCode) {
        return ReviewRepository.findByModuleCode(moduleCode);
    }

    @GetMapping("/reviews/reviewId/{reviewId}")
    public Review getReviewById(@PathVariable Long reviewId) {
        return ReviewRepository.findByReviewId(reviewId);
    }

    @PostMapping("/reviews")
    public Review createReview(@RequestBody ReviewDto reviewRequest) {
        System.out.println("Received review request: " + reviewRequest);
        return reviewService.addReview(
                // reviewRequest.getReviewId(),
                reviewRequest.getModuleCode(),
                reviewRequest.getModuleName(),
                reviewRequest.getReviewParagraph(),
                reviewRequest.getWorkload(),
                reviewRequest.getDifficulty(),
                reviewRequest.getUsefulness()
        );
    }

    // DELETE endpoint to remove a review by reviewId
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReviewById(reviewId);
            return ResponseEntity.ok("Review deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

        @GetMapping("/module/{moduleCode}/ratings")
    public HashMap<String, Double> getAverageRatings(@PathVariable String moduleCode) {
    List<Review> reviews = ReviewRepository.findByModuleCode(moduleCode);

    // Calculate averages
    double avgWorkload = reviews.stream().mapToDouble(Review::getWorkload).average().orElse(0);
    double avgDifficulty = reviews.stream().mapToDouble(Review::getDifficulty).average().orElse(0);
    double avgUsefulness = reviews.stream().mapToDouble(Review::getUsefulness).average().orElse(0);
    double overall = (avgWorkload + avgDifficulty + avgUsefulness) / 3;

    // Combine averages into a response
    HashMap<String, Double> averages = new HashMap<>();
    averages.put("workload", Math.round(avgWorkload * 100.0) / 100.0);
    averages.put("difficulty", Math.round(avgDifficulty * 100.0) / 100.0);
    averages.put("usefulness", Math.round(avgUsefulness * 100.0) / 100.0);
    averages.put("overallRating", Math.round(overall * 100.0) / 100.0);

    return averages;
    }

}
