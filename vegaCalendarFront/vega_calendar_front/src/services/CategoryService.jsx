import axios from "axios";
import config from "../configuration/conf";


const API_CATEGORY_URL = config.apiBaseUrl+"/api/category"
const jwt = localStorage.getItem("accessToken")

async function getAllCategories(){

    const response = await axios.get(API_CATEGORY_URL+'/all', {
        headers:{
            'Authorization': 'Bearer '+jwt
        }
    },{
        withCredentials: true,
    });
    return response;
    
}
export {getAllCategories}