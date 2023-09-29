import React from "react";
import { Outlet } from "react-router-dom";
import "./Home.css"


// eslint-disable-next-line react/prop-types
function Home({activatedButton}){
    return(
        <div className="container">
            <div className="main-container">
                <h2>{activatedButton}</h2>
                <hr/>
                <div className="main-content">
                    <Outlet/>
                </div>
            </div>
        </div>
    )
}

export default Home;