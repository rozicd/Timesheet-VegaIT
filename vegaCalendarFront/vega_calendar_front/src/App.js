import React, {useState, useEffect} from 'react';
import {BrowserRouter as Router, Routes, Route,Navigate} from 'react-router-dom';
import Home from './components/home/Home'
import Login from './components/login/Login'
import { checkToken } from './services/AuthService';
import Navbar from './components/navbar/Navbar';
import TimeSheet from './components/timeSheet/TimeSheet';
import Clients from './components/clients/Clients'
import Projects from './components/projects/Projects'
import Categories from './components/categories/Categories'
import TeamMembers from './components/teammembers/TeamMembers'
import Reports from './components/reports/Reports'
import Daily from './components/daily/Daily';
import './App.css';


function App() {
  const [isAuthenticated, setAuthenticated] = useState(false);
  const [activeButton, setActiveButton] = useState("TimeSheet");
  const [userId, setUserId] = useState("")

  function validateToken(){
    const token = checkToken();
    if(token != null){
      setUserId(token["id"])
      setAuthenticated(true)
    }
    else{
      setAuthenticated(false)
    }
  }

  const handleButtonClick = (buttonName) => {
    setActiveButton(buttonName);
  };

  useEffect(()=>{
    validateToken();
  }, [])

  return (
    <Router>
      <Routes>
          <Route
            path="/"
            element={
              isAuthenticated ? (
                <>
                  <Navbar activeButton={activeButton} handleButtonClick={handleButtonClick} loggedUserId={userId} /> 
                  <Home activatedButton={activeButton} />

                </>
              ) : (
                <Navigate to="/login" />
              )
            }
          >
            <Route path='/timesheet' element={<TimeSheet/>}/>
            <Route path='/clients' element={<Clients/>}/>
            <Route path='/projects' element={<Projects/>}/>
            <Route path='/categories' element={<Categories/>}/>
            <Route path='/teammembers' element={<TeamMembers/>}/>
            <Route path='/reports' element={<Reports/>}/>
            <Route path='/daily/:data' element={<Daily/>}/>
          </Route>
        
        <Route path="/login" element={!isAuthenticated ? <Login /> : <Navigate to="/timesheet"/>} />

        </Routes>

    </Router>

  );
}

export default App;


