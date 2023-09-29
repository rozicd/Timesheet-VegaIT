import axios from "axios";
import config from "../configuration/conf";

const API_BASE_URL = config.apiBaseUrl
const API_USER_URL = API_BASE_URL+"/api/user";
const jwt = localStorage.getItem("accessToken")

async function getUser(id){
    const response = await axios.get(API_USER_URL+'/'+id, {
        headers:{
            'Authorization': 'Bearer '+jwt
        }
    },{
        withCredentials: true,
    });
    return response;
}

async function getAllUsers(){
    const response = await axios.get(API_USER_URL+'/list-all', {
        headers:{
            'Authorization': 'Bearer '+jwt
        }
    },{
        withCredentials: true,
    });
    return response;
}

async function getUsers(params){
    const response = await axios.get(API_USER_URL+'/all', {
        params,
        headers:{
            'Authorization': 'Bearer '+jwt
        }
    },{
        withCredentials: true,
    });
    return response;
}

async function saveUser(user){
    const response = await axios.post(API_USER_URL, user, {
        headers:{
            'Authorization': 'Bearer '+jwt
        },
        withCredentials: true,
    });
    return response
}

async function updateUser(user){
    const response = await axios.put(API_USER_URL+"/"+user['id'], user, {
        headers:{
            'Authorization': 'Bearer '+jwt
        },
        withCredentials: true,
    });
    return response
}

export{getUser, getAllUsers, getUsers, saveUser, updateUser}