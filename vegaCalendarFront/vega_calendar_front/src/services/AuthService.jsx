import axios from "axios";
import jwt from 'jwt-decode';
import config from "../configuration/conf";

const API_BASE_URL = config.apiBaseUrl
const API_USER_URL = API_BASE_URL+"/api/user";
const token = localStorage.getItem("accessToken");

async function SignIn(email, password) {
    const response = await axios.post(`${API_USER_URL}/generateToken`, {
      email,
      password,
    }, {
      withCredentials: true
    });
  
    return response;
  }

function checkToken(){
    
    if(token === null){
      return null;
    }
    const decode = jwt(token)
    const currentDate = new Date();
    const tokenExpiration = new Date(decode['exp']*1000)
    if(currentDate<tokenExpiration){
      
      return decode;
    }
    else{
      return null;
    }
  }

function Logout(){
  localStorage.removeItem("accessToken")
  window.location.reload();
}

function getRole(){
  return jwt(token)['role']

}

  export {SignIn, checkToken, Logout, getRole}

