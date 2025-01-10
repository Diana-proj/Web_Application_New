package ie.tcd.scss.moduleReview.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ie.tcd.scss.moduleReview.domain.Module;


@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {
    Optional<Module> findByModuleName(String moduleName);
    Optional<Module> findByModuleCode(String moduleCode);
    void deleteByModuleName(String moduleName);
    boolean existsByModuleName(String moduleName);
}
