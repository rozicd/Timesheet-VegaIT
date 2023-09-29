import axios from "axios";
import config from "../configuration/conf";

const API_CLIENT_URL = config.apiBaseUrl+"/api/client";
const jwt = localStorage.getItem("accessToken")

async function getClients(params){
    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = axios.get(API_CLIENT_URL+'/all',{
        params,
        headers
    },{
        withCredentials:true,
    });
    return response;
}

async function getAllClients(){
    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = axios.get(API_CLIENT_URL+'/list-all',{
        headers
    },{
        withCredentials:true,
    });
    return response;
}

async function saveClient(client){
    console.log(jwt)
    const headers = {
        'Authorization': 'Bearer '+jwt
    }
    const response = axios.post(API_CLIENT_URL,client,{
        headers,
        withCredentials:true,
    });
    return response
}

async function updateClient(client){

    const headers = {
        'Authorization': 'Bearer ' + jwt
    }
    const response = axios.put(API_CLIENT_URL+'/'+client['clientId'],client,{
        headers,
        withCredentials:true,
    });
    return response
}

async function deleteClient(client){
    const headers = {
        'Authorization': 'Bearer ' + jwt
    }
    const response = axios.put(API_CLIENT_URL+'/delete/'+client['clientId'],{},{
        headers,
        withCredentials:true,
    });
    return response
}

export {getClients, saveClient, updateClient, deleteClient, getAllClients}