import React from "react";
import Modal from "react-modal";
import { useState } from "react";
import { saveUser } from "../../../services/UserService";
import { Logout } from "../../../services/AuthService";



const initialUser={
    name: '',
    surname: '',
    email: '',
    userType: 'WORKER',
    password: '',
    status: 'ACTIVE',
  }

// eslint-disable-next-line react/prop-types
function CreateMemberDialog({isOpen, onClose, onSave}){
    const [user, setUser] = useState(initialUser);

      const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUser({ ...user, [name]: value });
      };
    
      const handleSubmit = (e) => {
        e.preventDefault();
        saveUser(user).then(()=>{
            window.alert("Success")
        })
        .catch((error)=>{
            if(error.response.status){
                Logout()
            }
            else{
                window.alert("Error:", error)
            }
        })
        console.log(user);
        onSave(user);

        setUser(initialUser)
        onClose();
      };

    return(<Modal
        isOpen={isOpen}
        onRequestClose={onClose}
        contentLabel="Create Project"
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
        <div>
      <h1>User Form</h1>
      <form onSubmit={handleSubmit} className="create-member-form">
        <div>
        <div className="label-field">
          <label>Name:</label>
          <input
            type="text"
            name="name"
            value={user.name}
            onChange={handleInputChange}
          />
          </div>
        </div>
        <div>
        <div className="label-field">
          <label>Surname:</label>
          <input
            type="text"
            name="surname"
            value={user.surname}
            onChange={handleInputChange}
          />
          </div>
          <div className="label-field">
          <label>Password:</label>
          <input
            type="password"
            name="password"
            value={user.password}
            onChange={handleInputChange}
          />
          </div>
        </div>
        <div>
        <div className="label-field">
          <label>Email:</label>
          <input
            type="text"
            name="email"
            value={user.email}
            onChange={handleInputChange}
          />
          </div>
        </div>
        <div>
            
          <label>User Type:</label>
          <label>
            <input
              type="radio"
              name="userType"
              value="ADMIN"
              checked={user.userType === 'ADMIN'}
              onChange={handleInputChange}
            />
            Admin
          </label>
          <label>
            <input
              type="radio"
              name="userType"
              value="WORKER"
              checked={user.userType === 'WORKER'}
              onChange={handleInputChange}
            />
            Worker
          </label>
        </div>
        <div>
        
        </div>
        <div>
          <label>Status:</label>
          <label>
            <input
              type="radio"
              name="status"
              value="ACTIVE"
              checked={user.status === 'ACTIVE'}
              onChange={handleInputChange}
            />
            Active
          </label>
          <label>
            <input
              type="radio"
              name="status"
              value="INACTIVE"
              checked={user.status === 'INACTIVE'}
              onChange={handleInputChange}
            />
            Inactive
          </label>
        </div>
        <div className="button-container">
            <button type="submit" className="save-button">Submit</button>
            <button type="button" className="cancel-button" onClick={onClose}>Cancel </button>
        </div>
      </form>
    </div>
      </Modal>)
}

export default CreateMemberDialog