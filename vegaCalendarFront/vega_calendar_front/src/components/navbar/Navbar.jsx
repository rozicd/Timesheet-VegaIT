import React from "react";
import "./Navbar.css"
import logo from "../../assets/img/logo.png"
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getUser } from "../../services/UserService";
import { Logout, getRole } from "../../services/AuthService";

// eslint-disable-next-line react/prop-types
function Navbar({ activeButton, handleButtonClick, loggedUserId }){
    const[usersName, setUsersName] = useState("")
    const[isAdmin, setIsAdmin] = useState(false)

    async function setLoggedUser() {
        try{
            const response = await getUser(loggedUserId)
            const loggedUser = response.data
            setUsersName(loggedUser['name'] + " " + loggedUser['surname'])
            
        }catch(error){
            if (error.response && error.response.status === 401) {
                window.alert("Session token expired!")
                Logout()
              }
            console.log(error);
        }
    }
    function handleLogout(){
        Logout()
    }
    function checkRole() {
        const role = getRole()
        if(role==='ADMIN'){
            
            setIsAdmin(true);
        }
        else{
            setIsAdmin(false);
        }
    }

    useEffect(()=>{
        setLoggedUser();
        checkRole()
        // console.log(loggedUserId);
      }, [loggedUserId])
    

    return(
        <div >
            <div className="top-bar"></div>
            <div className="navbar-container">
                <div className="wrapper">
                    <div className="logo">
                        <a href="/">
                            <img src={logo} alt="logo"></img>
                        </a>
                    </div>
                    <div className="menu">
                        <div className="user">
                            <label className="logout-label" onClick={handleLogout}>Logout </label>
                            | 
                            <label className="user-name"> {usersName}</label>
                        </div>
                        <nav className="nav-menu">
                                <Link to="/timesheet" className={activeButton === "TimeSheet" ? "active" : ""} onClick={() => handleButtonClick("TimeSheet")}>TimeSheet</Link>
                                {isAdmin && <Link to="/clients" className={activeButton === "Clients" ? "active" : ""} onClick={() => handleButtonClick("Clients")}>Clients</Link>}
                                <Link to="/projects" className={activeButton === "Projects" ? "active" : ""} onClick={() => handleButtonClick("Projects")}>Projects</Link>
                                {isAdmin && <Link to="/categories" className={activeButton === "Categories" ? "active" : ""} onClick={() => handleButtonClick("Categories")}>Categories</Link>}
                                {isAdmin && <Link to="/teammembers" className={activeButton === "TeamMembers" ? "active" : ""}onClick={() => handleButtonClick("TeamMembers")}>TeamMembers</Link>}
                                {isAdmin && <Link to="/reports" className={activeButton === "Reports" ? "active" : ""} onClick={() => handleButtonClick("Reports")}>Reports</Link>}
                        </nav>
                    </div>
                </div>
                </div>
        </div>
    );
}

export default Navbar;