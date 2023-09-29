import React from "react";
import { Link, useParams } from "react-router-dom";
import './Daily.css'


function Daily(){
    const { data } = useParams();
    const receivedData = JSON.parse(data);
    console.log(receivedData)
    return(
        <div className="container">
            <div className="week-container">
                <button className="navigation-button"> Previous week</button>
                <label>February 04 - February 10, 2013 (week 6)</label>
                <button className="navigation-button">Next week</button>
            </div>
            <div className="day-buttons-container">
                <button className="day-button">
                    <label>Feb 06</label>
                    <label className="day-label">Tuesday</label>
                </button>
                <button className="day-button">
                    <label>Feb 06</label>
                    <label className="day-label">Wednesday</label>
                </button>
                <button className="day-button">
                    <label>Feb 06</label>
                    <label className="day-label">Thursday</label>
                </button>
                <button className="day-button">
                    <label>Feb 06</label>
                    <label className="day-label">Friday</label>
                </button>
                <button className="day-button">
                    <label>Feb 06</label>
                    <label className="day-label">Saturday</label>
                </button>
                <button className="day-button">
                    <label>Feb 06</label>
                    <label className="day-label">Sunday</label>
                </button>
            </div>
            <div className="activity-form-container">
                <div className="label-field-container">
                    <label>Client</label>
                    <select className="client-select">
                        <option>client 1</option>
                        <option>client 2</option>
                        <option>client 3</option>
                    </select>
                </div>
                <div className="label-field-container">
                    <label>Project</label>
                    <select className="project-select">
                        <option>project 1</option>
                        <option>project 2</option>
                        <option>project 3</option>
                    </select>
                </div>
                <div className="label-field-container">
                    <label>Category</label>
                    <select className="category-select">
                        <option>Category 1</option>
                        <option>Category 2</option>
                        <option>Category 3</option>
                    </select>
                </div>
                <div className="label-field-container">
                    <label>Description</label>
                    <input type="text" className="description-field">
                    </input>
                </div>
                <div className="label-field-container">
                    <label>Time</label>
                    <input type="text" className="time-field">
                    </input>
                </div>
                <div className="label-field-container">
                    <label>overtime</label>
                    <input type="text" className="time-field">
                    </input>
                </div>
                
            </div>
            <button className="add"> + </button>
            <div className="footer-container">
                <Link to="/timesheet" className="navigation-button">Back to monthly view</Link>
                <div className="label-container">
                    <label>Total hours:</label>
                    <label className="hours">10</label>
                </div>

            </div>
        </div>
    )
}

export default Daily;