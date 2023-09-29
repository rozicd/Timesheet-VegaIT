import React from "react";
import "./Project.css"
import { useState, useEffect } from "react";
import { getProjects, updateProject } from "../../services/ProjectService";
import { Logout } from "../../services/AuthService";
import { getAllClients } from "../../services/ClientService";
import { getAllUsers } from "../../services/UserService";
import CreateProjectDialog from "../dialogs/createProject/CreateProjectDialog";
import { getRole } from "../../services/AuthService";

const initialProject = {
    projectId:"",
    name:"",
    description: "",
    clientId: "",
    teamLeadId: "",
    teamMembersIDs: [],
    status: "", 
  };
function Projects(){
  const [isCreateProjectDialogOpen, setIsCreateProjectDialogOpen] = useState(false); 

    const[projectSearch, setProjectSerach] = useState("")
    const [pressedButton, setPressedButton] = useState(null);
    const [pressedValue, setPressedValue] = useState('');
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(4);
    const [projectPaginated, setProjectPaginated] = useState({ "items": [], "currentPage": 0, "totalItems": 0, "totalPages": 0 });
    const [expandedProject, setExpandedProject] = useState(null);
    const [projectForUpdate, setProject] = useState(initialProject)
    const [clients, setClients] = useState([])
    const [users, setUsers] = useState([])
    const [isAdmin, setIsAdmin] = useState(false)

    const generateAlphabet = () => {
        const alphabet = [];
        for (let i = 65; i <= 90; i++) {
          alphabet.push(String.fromCharCode(i));
        }
        return alphabet;
      };
    function checkRole(){
        const role = getRole();
        if(role === "ADMIN"){
          setIsAdmin(true);
        }
        else{
          setIsAdmin(false);
        }
    }
    
      const fetchUserAndClientData = async () => {
        try {
          const usersResponse = await getAllUsers();
          setUsers(usersResponse.data);
    
          const clientsResponse = await getAllClients();
          setClients(clientsResponse.data);
        } catch (error) {
          if (error.response && error.response.status === 401) {
            Logout();
          }
        }
      };

      
      const openCreateProjectDialog = () => {
        setIsCreateProjectDialogOpen(true);
      };

      const handleButtonClick = (letter) => {
        if (pressedButton === letter) {
          setPressedButton(null); 
          setPressedValue('');
        } else {
          setPressedButton(letter);
          setPressedValue(letter);
        }
      };

      async function populateProjects(params) {
        try {
          setPageSize(4)
          const response = await getProjects(params);
          setProjectPaginated(response.data);
        } catch (error) {
          if (error.response && error.response.status === 401) {
            Logout();
          }
          else{
            window.alert(error)
          }
          
          
        }
      }
      const closeCreateProjectDialog = () => {
        setIsCreateProjectDialogOpen(false);
      };

      const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProject({ ...projectForUpdate, [name]: value });
        console.log(projectForUpdate)
      };

      useEffect(() => {
        checkRole()
        const params = {
          "page": page,
          "pageSize": pageSize,
          "letter": pressedValue,
          "search": projectSearch
        }
        populateProjects(params);
      }, [page, pageSize, pressedValue, projectSearch]);
    
      const toggleProjectExpansion = (project) => {
        if (expandedProject === project['projectId']) {
          setExpandedProject(null);
        } else {
            setExpandedProject(project['projectId']);
            const projectModel = {
                projectId:project.projectId,
                name:project.name,
                description: project.description,
                clientId: project.clientId,
                teamLeadId: project.teamLeadId,
                teamMembersIDs: [],
                status: project.status,
            }
            setProject(projectModel)
            fetchUserAndClientData();
          
        }
      };

    function handleUpdate(){
        updateProject(projectForUpdate).then(()=>{
            window.alert("Succes")
        }).catch((error)=>{
          if (error.response && error.response.status === 401) {
            window.alert("Session token expired!")
            Logout()
          }
            else{
                console.error("Error updating project:", error)
            }
        })
    }
    return(
        <div className="container">
            <div className="create-search-container">
                {isAdmin && <button className="create-project-button" onClick={openCreateProjectDialog}>Create new project</button>}
                <input
                type="text"
                className="search-field"
                value={projectSearch}
                onChange={(e) => setProjectSerach(e.target.value)} />
            </div>
            <div className="alphabet-button-container">
                {generateAlphabet().map((letter, index) => (
                <button
                    key={index}
                    className={`alphabet-button ${pressedButton === letter ? 'pressed' : ''}`}
                    onClick={() => handleButtonClick(letter)}
                >
                    {letter}
                </button>
                ))}
            </div>
            <div className="project-container">
                {projectPaginated['items'].map((project) => (
                <div key={project.projectId} className="project-item">
                    <div
                    className={`projects ${expandedProject === project['projectId'] ? 'expanded' : ''}`} 
                    onClick={() => toggleProjectExpansion(project)} 
                    >
                    <label className="project-name">{project['name']}</label>
                    <label className="delete-button">X</label>
                    </div>
                    {expandedProject === project['projectId'] && (
                        <div className="expanded-project">
                            <div className="expanded-content">
                            <div className="label-field-container">
                                <label>Name:</label>
                                <input type="text" name="name" onChange={handleInputChange} value={projectForUpdate.name} />
                            </div>
                            <div className="label-field-container">
                                <label>Description:</label>
                                <input type="text" name="description" onChange={handleInputChange} value={projectForUpdate.description} />
                            </div>
                            <div className="label-field-container">
                            <label>Customer:</label>
                                <select
                                name="clientId"
                                value={projectForUpdate.clientId}
                                onChange={handleInputChange}
                                >
                                <option value="">Select a customer</option>
                                {clients.map((client) => (
                                    <option key={client['clientId']} value={client['clientId']}>
                                    {client.name}
                                    </option>
                                ))}
                                </select>
                            </div>
                            <div className="label-field-container">
                            <label>Lead:</label>
                                <select
                                name="teamLeadId"
                                value={projectForUpdate.teamLeadId}
                                onChange={handleInputChange}
                                >
                                <option value="">Select a lead</option>
                                {users.map((user) => (
                                    <option key={user['id']} value={user['id']}>
                                    {user.name}
                                    </option>
                                ))}
                                </select>
                            </div>
                            <div className="label-field-container">
                            <div className="label-field-container">
                                <label>Project Status:</label>
                                <div className="status-labels-container">
                                    <label className="status-label">
                                    <input
                                        type="radio"
                                        name="status"
                                        value="ACTIVE"
                                        checked={projectForUpdate.status === "ACTIVE"}
                                        onChange={handleInputChange}
                                    />
                                    Active
                                    </label>
                                    <label className="status-label">
                                    <input
                                        type="radio"
                                        name="status"
                                        value="INACTIVE"
                                        checked={projectForUpdate.status === "INACTIVE"}
                                        onChange={handleInputChange}
                                    />
                                    Inactive
                                    </label>
                                    <label className="status-label">
                                    <input
                                        type="radio"
                                        name="status"
                                        value="ARCHIVE"
                                        checked={projectForUpdate.status === "ARCHIVE"}
                                        onChange={handleInputChange}
                                    />
                                    Archive
                                    </label>
                                </div>
                                </div>
                            </div>
                            </div>
                            <div className="buttons-container">
                                <button className="update-button" onClick={handleUpdate}>Update</button>
                                <button className="delete-project-button">Delete</button>
                            </div>
                            
                        </div>
                        )}
                </div>
                ))}
            </div>
            <div className="pagination-controls">
                <button className={`pagination-button ${page === 1 ? 'disabled' : ''}`} onClick={() => setPage(page - 1)} disabled={page === 1}>Previous</button>
                <label>| {page} |</label>
                <button className={`pagination-button ${page === projectPaginated.totalPages ? 'disabled' : ''}`} onClick={() => setPage(page + 1)} disabled={page === projectPaginated.totalPages}>Next</button>
            </div>
            <CreateProjectDialog
              isOpen={isCreateProjectDialogOpen}
              onClose={closeCreateProjectDialog}
              onSave={(project) => {
                console.log("project data:", project);
                closeCreateProjectDialog(); 
                }}
              />
        </div>
    )
}

export default Projects;