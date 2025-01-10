import './App.css'
import ModuleList from "./components/ModuleList";
import SearchBar from './components/SearchBar';

import {Link} from "react-router-dom";
import { QueryClient, QueryClientProvider } from 'react-query';
const queryClient = new QueryClient();
function App() 
{
  return (
  <QueryClientProvider client={queryClient}>
    <div className = "App">
      <header className = "App-header">
        <p className = "logo"><img src = "src/TCDModuLens.PNG" alt = "logo" /></p>
        <h1 className = "Title">Rate My Module Tcd</h1>
        <p className= "Description1">The best website to find your Trinity module!</p>
        <SearchBar /> 
        <p className= "Description2">To test what one of our modules look like click the link below!</p>
        <Link to="/Module/Introduction to Computing I" className="custom-button">
            Explore Modules
          </Link>
        <ModuleList /> 
      </header>
    </div>
  </QueryClientProvider>
  );
}

export default App
