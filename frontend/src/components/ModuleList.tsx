import React from "react";
import { useModules } from "../hooks/useModules";
import { Link } from "react-router-dom";
import "./ModuleList.css"; // Import CSS for styling

const ModuleList: React.FC = () => {
    const { data: modules, isLoading, error } = useModules();
    console.log("Modules:", modules); // Debugging log
    console.log("Is Loading:", isLoading); // Debugging log
    console.log("Error:", error); // Debugging log
    if (isLoading) return <p>Loading...</p>;
    if (error) return <p>Error: {error.message}</p>;

    return (
        <div className="ModuleListContainer">
            <h1>Module List</h1>
            <ul className="ModuleList">
                {modules?.map((module) => (
                    <li key={module.moduleCode}>
                        <h2>{module.moduleName}</h2>
                        <p><strong>Code:</strong> {module.moduleCode}</p>
                        <p><strong>Coordinator:</strong> {module.moduleCoordinator}</p>
                        <p><strong>Workload:</strong> {module.workload}</p>
                        <p><strong>Difficulty:</strong> {module.difficulty}</p>
                        <p><strong>Usefulness:</strong> {module.usefulness}</p>
                        <p><strong>Overall Rating:</strong> {module.overallRating}</p>
                        <Link to={`/code/${module.moduleCode}`} className="view-details-button">
                            View Details
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ModuleList;
