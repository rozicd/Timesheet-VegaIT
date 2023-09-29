import axios from "axios";
import config from "../configuration/conf";

const API_BASE_URL = config.apiBaseUrl
const API_COUNTRY_URL = API_BASE_URL+"/api/country";
const jwt = localStorage.getItem('accessToken')

async function getCountries(){
    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = axios.get(API_COUNTRY_URL+"/all",{
        headers
    },{
        withCredentials:true,
    });
    return response
}

export {getCountries}