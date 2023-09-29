import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import { getAllClients } from "../../../services/ClientService";
import { getAllUsers } from "../../../services/UserService";
import { saveProject } from "../../../services/ProjectService";
import { Logout } from "../../../services/AuthService";


const initialProject = {
  name: "",
  description: "",
  clientId: "", 
  teamLeadId: "", 
  teamMembersIDs: [],
};
  
// eslint-disable-next-line react/prop-types
function CreateProjectDialog({ isOpen, onClose, onSave }){
    const [project, setProject] = useState(initialProject)
    const [userList, setUserList] = useState([])
    const [loadingUsers, setLoadingUsers] = useState(true)
    const [clientList, setClientList] = useState([])
    const [loadingClients, setLoadingClients] = useState(true)

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProject({ ...project, [name]: value });
      };

    useEffect(() => {

        if (isOpen) {
          getAllClients()
            .then((response) => {
    
              setClientList(response.data);
              setLoadingClients(false);
            })
            .catch((error) => {
              console.error("Error fetching clients:", error);
              setLoadingClients(false);
            });

            getAllUsers().then((response) =>{
                setUserList(response.data)
                setLoadingUsers(false)
            })
            .catch((error) => {
                console.error("Error fetching users:", error);
                setLoadingUsers(false);
              });
        }
      }, [isOpen]);

      async function handleSave() {
        console.log(project)
        saveProject(project).then(()=>{
            window.alert("Success")
        }).catch((error)=>{
            if(error.response.status===401){
                Logout()
            }
            else{
                window.alert("Error:", error.response.message)
            }
        })

        onSave(project);
    
        setProject(initialProject);
        onClose();
      }

    return(<Modal
        isOpen={isOpen}
        onRequestClose={onClose}
        contentLabel="Create Client"
        style={{
          content: {
            top: '40%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)',
          },
        }}
      >
        <h2>Create project</h2>
        <hr></hr>
        <form className="create-client-form">
          <div className="label-field">
            <label>Name:</label>
            <input
              type="text"
              name="name"
              value={project.name}
              onChange={handleInputChange}
            />
          </div>
          <div className="label-field">
            <label>Description:</label>
            <input
              type="text"
              name="description"
              value={project.description}
              onChange={handleInputChange}
            />
          </div>

          <div className="label-field">
            <label>Customer:</label>
            {loadingClients ? (
              <p>Loading countries...</p>
            ) : (
              <select
                name="clientId"
                value={project.clientId}
                onChange={handleInputChange}
              >
                <option value="">Select a client</option>
                {clientList.map((client) => (
                  <option key={client.clientId} value={client.clientId}>
                    {client.name}
                  </option>
                ))}
              </select>
            )}
          </div>

          <div className="label-field">
            <label>Lead:</label>
            {loadingUsers ? (
              <p>Loading countries...</p>
            ) : (
              <select
                name="teamLeadId"
                value={project.teamLeadId}
                onChange={handleInputChange}
              >
                <option value="">Select a lead</option>
                {userList.map((user) => (
                  <option key={user.id} value={user.id}>
                    {user.name + " " + user.surname}
                  </option>
                ))}
              </select>
            )}
          </div>

          <div className="button-container">
            <button type="button" className="save-button" onClick={handleSave}>
              Save
            </button>
            <button type="button" className="cancel-button" onClick={onClose}>
              Cancel
            </button>
          </div>
          
        </form>
      </Modal>)
}

export default CreateProjectDialog;