package ie.tcd.scss.moduleReview.service;

import ie.tcd.scss.moduleReview.Repo.ModuleRepository;
import ie.tcd.scss.moduleReview.domain.Module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling business logic related to modules
 */

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository=moduleRepository;
    }

    public Module createModule (String moduleCode, String moduleName, String moduleContent, String moduleCoordinator, double workload, double difficulty, double usefulness) {
        return moduleRepository.save(new Module(moduleCode, moduleName, moduleContent, moduleCoordinator, workload, difficulty, usefulness));
    }

    public void deleteModule (String code) {
        if (moduleRepository.existsById(code)) {
            moduleRepository.deleteById(code);
        }
    }

    public void deleteModuleByName (String name) {
        if (moduleRepository.existsByModuleName(name)) {
            moduleRepository.deleteByModuleName(name);
        }
    }

}
