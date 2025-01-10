import './index.css'
import App from './App.tsx'
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import ReactDOM from 'react-dom/client'
import "./components/ModuleScreen.tsx";
import ModuleScreen from './components/ModuleScreen.tsx';
import React from 'react'


const router = createBrowserRouter([
  {
      path: '/',
      element: <App />,
  },
  {
      path: '/module',
      element: <ModuleScreen/>,
  },
  {
    path: '/module/:name',
      element: <ModuleScreen/>,
  },
  {
    path: '/code/:code', // New route for fetching by code
    element: <ModuleScreen />,
}
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
<React.StrictMode>
    <RouterProvider router={router} />
</React.StrictMode>,
)

