import React from "react";
import './TeamMembers.css'
import { useState, useEffect } from "react";
import { getUsers, updateUser } from "../../services/UserService";
import { Logout } from "../../services/AuthService";
import CreateMemberDialog from "../dialogs/createMember/CreateMemberDialog";



const initialUser={
    name: '',
    surname: '',
    email: '',
    userType: '',
    status: '',
  }

function TeamMembers(){
    const [expandedUser, setExpandedUser] = useState(null);

    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(4);
    const [userPaginated, setUserPaginated] = useState({ "items": [], "currentPage": 0, "totalItems": 0, "totalPages": 0 });
    const [isCreateMemberDialogOpen, setIsCreateMemberDialogOpen] = useState(false); 
    const [useForUpdate, setUser] = useState(initialUser)

    function populateUsers(params){
        getUsers(params).then((response)=>{
            setUserPaginated(response.data)
            setPageSize(4)
        }).catch((error)=>{
            if(error.response.status === 401){
                Logout()
            }
            else{
                if (error.response && error.response.status === 401) {
                    window.alert("Session token expired!")
                    Logout()
                  }
                window.alert("Error:", error)
            }
        })
    }

    const toggleMemberExpansion = (user) => {
        if (expandedUser === user['id']) {
          setExpandedUser(null);
        } else {
          setExpandedUser(user['id']);
          setUser(user)
          
        }
      };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUser({ ...useForUpdate, [name]: value });
    };
    
    const openCreateMemberDialog = () => {
        setIsCreateMemberDialogOpen(true);
      };
    
    
      const closeCreateMemberDialog = () => {
        setIsCreateMemberDialogOpen(false);
      };
    function handleUpdate(){
        updateUser(useForUpdate).then(()=>{
            window.alert("Succes")
        }).catch((error)=>{
            if (error.response && error.response.status === 401) {
                window.alert("Session token expired!")
                Logout()
              }
            window.alert("error")
        })
    }
    useEffect(() => {
        const params = {
          "page": page,
          "pageSize": pageSize,
        }
        populateUsers(params);
      }, [page, pageSize, isCreateMemberDialogOpen]);

    return(
        <div className="container">
            <div className="create-container">
                <button className="create-project-button" onClick={openCreateMemberDialog} >Create new member</button>
            </div>
            <div className="members-container">
                {userPaginated['items'].map((user)=>(
                    <div key={user['id']} className="user-item" >
                        <div 
                        className={`users ${expandedUser === user['id'] ? 'expanded' : ''}`}
                        onClick={() => toggleMemberExpansion(user)}
                        >
                            <label className="user-name">{user['name']}</label>
                            <label className="delete-button">X</label>
                        </div>
                        {expandedUser === user['id'] && (
                            <div className="expandend-user">
                                <div className="expanded-content">
                                    <div className="label-field-container">
                                        <label>Name:</label>
                                        <input type="text" name="name" onChange={handleInputChange} value={useForUpdate.name} />
                                    </div>
                                    <div className="label-field-container">
                                        <label>Surname:</label>
                                        <input type="text" name="surname" onChange={handleInputChange} value={useForUpdate.surname} />
                                    </div>
                                    <div className="label-field-container">
                                        <label>Email:</label>
                                        <input type="text" name="email" onChange={handleInputChange} value={useForUpdate.email} />
                                    </div>
                                    <div className="label-field-container">
                                        
                                        <label>User Type:</label>
                                        <div className="radio">
                                            <label>
                                            <input
                                                type="radio"
                                                name="userType"
                                                value="ADMIN"
                                                checked={useForUpdate.userType === 'ADMIN'}
                                                onChange={handleInputChange}
                                            />
                                            Admin
                                            </label>
                                            <label>
                                            <input
                                                type="radio"
                                                name="userType"
                                                value="WORKER"
                                                checked={useForUpdate.userType === 'WORKER'}
                                                onChange={handleInputChange}
                                            />
                                            Worker
                                            </label>
                                        </div>
                                    </div>
                                    <div className="label-field-container">
                                        
                                        <label>User Type:</label>
                                        <div className="radio">
                                            <label>
                                            <input
                                                type="radio"
                                                name="status"
                                                value="ACTIVE"
                                                checked={useForUpdate.status === 'ACTIVE'}
                                                onChange={handleInputChange}
                                            />
                                            Active
                                            </label>
                                            <label>
                                            <input
                                                type="radio"
                                                name="status"
                                                value="INACTIVE"
                                                checked={useForUpdate.status === 'INACTIVE'}
                                                onChange={handleInputChange}
                                            />
                                            Inactive
                                            </label>
                                        </div>
                                    </div>
                                    <div className="buttons-container">
                                        <button className="update-button" onClick={handleUpdate}>Update</button>
                                        <button className="delete-user-button" >Delete</button>
                                    </div>

                                </div>
                            </div>
                        )}
                    </div>
                ))}
            </div>
            <div className="pagination-controls">
                <button className={`pagination-button ${page === 1 ? 'disabled' : ''}`} onClick={() => setPage(page - 1)} disabled={page === 1}>Previous</button>
                <label>| {page} |</label>
                <button className={`pagination-button ${page === userPaginated.totalPages ? 'disabled' : ''}`} onClick={() => setPage(page + 1)} disabled={page === userPaginated.totalPages}>Next</button>
            </div>
            <CreateMemberDialog
                isOpen={isCreateMemberDialogOpen}
                onClose={closeCreateMemberDialog}
                onSave={(userData) => {
                console.log("User data:", userData);
                closeCreateMemberDialog(); 
                }}
            />
        </div>
    )
}

export default TeamMembers;