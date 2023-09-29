import axios from "axios";
import config from "../configuration/conf";

const API_ACTIVITY_URL = config.apiBaseUrl+"/api/activity";
const jwt = localStorage.getItem("accessToken")

async function getForCalendar(startDate, endDate){
    const params = {
        "startDate": startDate,
        "endDate": endDate
    }
    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = axios.get(API_ACTIVITY_URL+"/per-day", {
        params,
        headers
    }, {
        withCredentials: true
    });
    return response;
}

async function getReport(params){
    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = axios.get(API_ACTIVITY_URL+"/all", {
        params,
        headers
    }, {
        withCredentials: true
    });
    return response;
}

export {getForCalendar, getReport}