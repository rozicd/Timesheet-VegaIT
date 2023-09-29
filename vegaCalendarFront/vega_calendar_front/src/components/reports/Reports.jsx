import React from "react";
import './Report.css'
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useState, useEffect } from "react";
import { getReport } from "../../services/ActivityService";
import { Logout } from "../../services/AuthService";
import { getAllUsers } from "../../services/UserService";
import { getAllClients } from "../../services/ClientService";
import { getAllProjects } from "../../services/ProjectService";
import { getAllCategories } from "../../services/CategoryService";

const initialParams={
    teamMemberId: "",
    clientId: "",
    categoryId: "",
    projectId: "",
    startDate: "",
    endDate: ""

}
function Reports(){
    const [params, setParams] = useState(initialParams)
    const [report, setReport] = useState({activityResponseDTOS:[], totalHours:0})
    const [users, setUsers] = useState([]);
    const [clients, setClients] = useState([]);
    const [projects, setProjects] = useState([]);
    const [categories, setCategories] = useState([]);


    const StyledTableCell = styled(TableCell)(({ theme }) => ({
        [`&.${tableCellClasses.head}`]: {
          backgroundColor: "#f67d34",
          color: theme.palette.common.white,
        },
        [`&.${tableCellClasses.body}`]: {
          fontSize: 14,
        },
      }));
      
      const StyledTableRow = styled(TableRow)(({ theme }) => ({
        '&:nth-of-type(odd)': {
          backgroundColor: theme.palette.action.hover,
        },
        // hide last border
        '&:last-child td, &:last-child th': {
          border: 0,
        },
      }));
    function handleReset(){
        setParams(initialParams);
        document.querySelectorAll('select').forEach((select) => {
            select.value = "";
        });
        document.querySelectorAll('input[type="date"]').forEach((input) => {
            input.value = "";
        });
    }
      
    function handleSearch(){
        getReport(params).then((response)=>{
            setReport(response.data)
        }).catch((error)=>{
            if (error.response && error.response.status === 401) {
                window.alert("Session token expired!")
                Logout()
              }
            else{
                window.alert("error")
            }
        })
    }
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setParams({ ...params, [name]: value });
        
      };
    
    useEffect(() => {
        getAllUsers().then(response => {
            setUsers(response.data);
        }).catch(error => {
            if (error.response && error.response.status === 401) {
                window.alert("Session token expired!")
                Logout()
              }
            console.error("Error fetching users:", error);
        });

        getAllClients().then(response => {
            setClients(response.data);
        }).catch(error => {
            if (error.response && error.response.status === 401) {
                window.alert("Session token expired!")
                Logout()
              }
            console.error("Error fetching clients:", error);
        });

        getAllProjects().then(response => {
            setProjects(response.data);
        }).catch(error => {
            if (error.response && error.response.status === 401) {
                window.alert("Session token expired!")
                Logout()
              }
            console.error("Error fetching projects:", error);
        });

        getAllCategories().then(response => {
            setCategories(response.data);
        }).catch(error => {
            if (error.response && error.response.status === 401) {
                window.alert("Session token expired!")
                Logout()
              }
            console.error("Error fetching categories:", error);
        });
    }, []);
    
    return(
        <div className="container">
            <div className="filter-container">
                <div className="filters">
                    <div className="label-field-container">
                        <label>
                            Team member:
                        </label>
                        <select className="filter-select" name="teamMemberId"  onChange={handleInputChange}>
                            <option value={""}>All</option>
                            {users.map(user => (
                                <option key={user.id} value={user.id}>{user.name+" "+user.surname}</option>
                            ))}
                        </select>
                    </div>
                    <div className="label-field-container">
                        <label>
                            Client:
                        </label>
                        <select className="filter-select" name="clientId"  onChange={handleInputChange}>
                            <option value={""}>All</option>
                            {clients.map(client => (
                                <option key={client.clientId} value={client.clientId}>{client.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="label-field-container">
                        <label>
                            Project:
                        </label>
                        <select className="filter-select" name="projectId"  onChange={handleInputChange}>
                            <option value={""}>All</option>
                            {projects.map(project => (
                                <option key={project.projectId} value={project.projectId}>{project.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="label-field-container">
                        <label>
                            Category:
                        </label>
                        <select className="filter-select" name="categoryId"  onChange={handleInputChange}> 
                            <option value={""}>All</option>
                            {categories.map(category => (
                                <option key={category.id} value={category.id}>{category.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="label-field-container">
                        <label >Start date:</label>
                        <input
                            type="date"
                            className="date-picker"
                            name="startDate"
                            value={""}
                            onChange={handleInputChange}
                            />

                    </div>
                    <div className="label-field-container">
                        <label >End date:</label>
                        <input
                            onChange={handleInputChange}
                            type="date"
                        
                            className="date-picker"
                            value={""}
                            name = "endDate"
                            />
                    </div>
                    
                </div>
                <div className="button-container">
                    <button className="search-button" onClick={handleSearch}>Search</button>
                    <button className="reset-button" onClick={handleReset}>Reset</button>
                </div>

            </div>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 700 }} aria-label="customized table">
                    <TableHead>
                    <TableRow>
                        <StyledTableCell>Date</StyledTableCell>
                        <StyledTableCell align="right">Team Member</StyledTableCell>
                        <StyledTableCell align="right">Projects</StyledTableCell>
                        <StyledTableCell align="right">Categories</StyledTableCell>
                        <StyledTableCell align="right">Description</StyledTableCell>
                        <StyledTableCell align="right">Time</StyledTableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {report['activityResponseDTOS'].map((activity) => (
                        <StyledTableRow key={activity.activitysId}>
                        <StyledTableCell component="th" scope="row">
                            {activity.date}
                        </StyledTableCell>
                        <StyledTableCell align="right">{activity.user.name + " " + activity.user.surname}</StyledTableCell>
                        <StyledTableCell align="right">{activity.project.name}</StyledTableCell>
                        <StyledTableCell align="right">{activity.category.name}</StyledTableCell>
                        <StyledTableCell align="right">{activity.description}</StyledTableCell>
                        <StyledTableCell align="right">{activity.time + activity.overtime}</StyledTableCell>

                        </StyledTableRow>
                    ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <div className="report-total">
                <label>Report total: </label>
                <label id="total-hours">{report.totalHours}</label>
            </div>
        </div>
    )
}

export default Reports;