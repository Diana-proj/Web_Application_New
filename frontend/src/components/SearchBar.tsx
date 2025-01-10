import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const SearchBar = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSearch = async () => {
    if (!searchQuery.trim()) {
      setError('Please enter a valid module name or code.');
      return;
    }

    setError(''); // Reset error state

    try {
      // First attempt: Search by module name
      let endpoint = `/db/module/${searchQuery}`;
      let response = await fetch(endpoint);

      if (response.ok) {
        const module = await response.json();
        if (module) {
          // Navigate to the module's page
          navigate(`/module/${module.moduleName}`);
          return; // Exit the function if the module is found
        }
      }

      // Second attempt: Search by module code
      endpoint = `/db/code/${searchQuery}`;
      response = await fetch(endpoint);

      if (response.ok) {
        const module = await response.json();
        if (module) {
          // Navigate to the module's page
          navigate(`/module/${module.moduleName}`);
          return; // Exit the function if the module is found
        }
      }

      // If neither endpoint returns a valid module, show an error
      setError('Module not found. Please check the name or code and try again.');
    } catch (err) {
      console.error('Error fetching module:', err);
      setError('An unexpected error occurred. Please try again later.');
    }
  };

  return (
    <div className="search-bar-container">
  <form className="search-form" onSubmit={handleSearch}>
    <input
      type="text"
      placeholder="Search module by name or code..."
      value={searchQuery}
      onChange={(e) => setSearchQuery(e.target.value)}
      className="search-input"
    />
    <button type="button" onClick={handleSearch} className="search-button">
      Search
    </button>
  </form>
  {error && <p className="error-message">{error}</p>}
</div>

  );
};

export default SearchBar;
