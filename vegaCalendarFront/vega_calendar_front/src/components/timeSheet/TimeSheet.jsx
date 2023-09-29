import React, { useState, useEffect } from "react";
import "./TimeSheet.css";
import { getForCalendar } from "../../services/ActivityService";
import { Logout } from "../../services/AuthService";
import { useNavigate } from "react-router-dom";
function TimeSheet() {
  const currentDate = new Date();
  const [currentMonth, setCurrentMonth] = useState(currentDate.getMonth() + 1); // Months are 0-indexed, so add 1
  const [currentYear, setCurrentYear] = useState(currentDate.getFullYear());
  const [activities, setActivities] = useState([])
  const [totalHours, setTotalhours] = useState(0)

  const handlePreviousMonth = () => {
    if (currentMonth === 1) {
      setCurrentMonth(12);
      setCurrentYear(currentYear - 1);
    } else {
      setCurrentMonth(currentMonth - 1);
    }
  };

  const handleNextMonth = () => {
    if (currentMonth === 12) {
      setCurrentMonth(1);
      setCurrentYear(currentYear + 1);
    } else {
      setCurrentMonth(currentMonth + 1);
    }
  };

  async function populateCalendar(date){
    try {
        const response = await getForCalendar(date[0], date[1])
        setActivities(response.data['hoursPerDay'])
        setTotalhours(response.data['totalHours'])
        
    } catch (error) {
        if(error.response.status === 401){
          Logout()
        }
    }
  }

  const calculateWeeks = () =>{
    return Math.ceil(activities.length/7);
  }

  const getFirstAndLastDatesOfMonth = () => {
    const firstDate = new Date(currentYear, currentMonth - 1, 1);
    const lastDate = new Date(currentYear, currentMonth, 0);
    return { firstDate, lastDate };
  };


  const getSpecialDates = () => {
    const { firstDate, lastDate } = getFirstAndLastDatesOfMonth();
    const specialDates = [];
  
    const formatDate = (date) => {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // Add 1 to month because it's zero-indexed
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    };
  
    if (firstDate.getDay() === 1) {
      specialDates.push(formatDate(firstDate));
    } else {
      const earlierDate = new Date(firstDate);
      const daysToMonday = (firstDate.getDay() - 1 + 7) % 7;
      earlierDate.setDate(firstDate.getDate() - daysToMonday);
      specialDates.push(formatDate(earlierDate));
    }
  
    if (lastDate.getDay() === 5) {
      specialDates.push(formatDate(lastDate));
    } else {
      const laterDate = new Date(lastDate);
      const daysToFriday = (7 - lastDate.getDay() + 7) % 7;
      laterDate.setDate(lastDate.getDate() + daysToFriday+1);
      specialDates.push(formatDate(laterDate));
    }
  
    return specialDates;
  };
  useEffect(()=>{
    const date = getSpecialDates();
    populateCalendar(date)
    // console.log(activities)
    
  }, [currentYear, currentMonth])

  const numWeeks = calculateWeeks();
  const navigate = useNavigate();
function handleHour(weekIndex, dayIndex, activity){
    const data = {
      week: weekIndex,
      dayIndex: dayIndex,
      activity: activity
    }
    const string = JSON.stringify(data)
    navigate(`/daily/${string}`);
}


  return (
    <div>
      <div className="date-container">
        <div className="buttons-container">
          <div className="btn-wrapper">
            <button className="btn" onClick={handlePreviousMonth}>
              {"<"} previous month
            </button>
          </div>
          <div className="btn-wrapper">
            <label>
              {new Date(currentYear, currentMonth - 1, 1).toLocaleDateString(
                "en-US",
                { month: "long", year: "numeric" }
              )}
            </label>
          </div>
          <div className="btn-wrapper">
            <button className="btn" onClick={handleNextMonth}>
              next month {">"}
            </button>
          </div>
        </div>
        <hr id="date-hr" />
      </div>
      <div>
      {activities.length > 0 ? (
        <table className="month-table">
          <tbody>
              <tr>
                <th>Monday</th>
                <th>Tuesday</th>
                <th>Wednesday</th>
                <th>Thursday</th>
                <th>Friday</th>
                <th>Saturday</th>
                <th>Sunday</th>
              </tr>
              {[...Array(numWeeks)].map((_, weekIndex) => (
                <tr key={weekIndex}>
                  {[...Array(7)].map((_, dayIndex) => {
                    const current = activities[weekIndex * 7 + dayIndex];
                    const currentDate = new Date();
                    const month = currentDate.getMonth() + 1

                    const isCurrentMonth = current && current.date.split('-')[1] === String(month).padStart(2, '0');
                    return (
                      <td
                        key={dayIndex}
                        disabled={!isCurrentMonth}
                      >
                        <div className={`table-cells ${!isCurrentMonth ? 'disabled-cell' : ''}`}>
                          <p>{current?.date.split('-')[2]}.</p>
                          <div className={`hour-per-day ${current?.dailyHours > 0 ? (current?.dailyHours < 7.5 ? 'red-bg' : 'green-bg') : ''}`}>
                            <label onClick={()=>handleHour(weekIndex, dayIndex, current.date)} className={`hour-button ${!isCurrentMonth ? 'disabled-button' : ''}`}>Hours: {current?.dailyHours}</label>
                          </div>
                        </div>
                      </td>
                    );
                  })}
                </tr>
              ))}
            </tbody>
        </table>
      ) : (
        <p>Loading...</p>
      )}
      </div>
      <div className="total-hours-container">
        <p id="hours">{totalHours}</p>
        <label>Total hours: </label>
    
      </div>

    </div>
  );
}

export default TimeSheet;
