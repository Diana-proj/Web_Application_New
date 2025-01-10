import axios from "axios";
import { useQuery } from "react-query";
import { Module } from "../types/types";


// Define the function to fetch data
const fetchModules = async (): Promise<Module[]> => {
    const { data } = await axios.get("http://localhost:8080/db"); // Fetches from the backend
    console.log("Fetched data:", data); // Debugging log
    return data; // Return the module data
};

// Export a hook that uses React Query to fetch the data
export const useModules = () => {
    return useQuery<Module[], Error>("modules", fetchModules); // React Query key is 'modules'
};


