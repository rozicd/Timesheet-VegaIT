import axios from "axios";
import config from "../configuration/conf";

const API_PROJECT_URL = config.apiBaseUrl+"/api/project";
const jwt = localStorage.getItem("accessToken")

async function getProjects(params){
    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = axios.get(API_PROJECT_URL+'/all',{
        params,
        headers
    },{
        withCredentials:true,
    });
    return response;
}

async function updateProject(project){

    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = await axios.put(API_PROJECT_URL+'/'+project['projectId'],project,{
        headers,
        withCredentials:true,
    });
    return response
}

async function saveProject(project){

    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = await axios.post(API_PROJECT_URL,project,{
        headers,
        withCredentials:true,
    });
    return response
}

async function getAllProjects(){
    const response = await axios.get(API_PROJECT_URL+'/list-all', {
        headers:{
            'Authorization': 'Bearer '+jwt
        }
    },{
        withCredentials: true,
    });
    return response;
}


export {getProjects, updateProject, saveProject, getAllProjects}