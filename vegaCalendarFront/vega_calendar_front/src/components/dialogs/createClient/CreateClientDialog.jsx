import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import { getCountries } from "../../../services/CountryService";
import "./CreateClientDialog.css"
import { saveClient } from "../../../services/ClientService";
import { Logout } from "../../../services/AuthService";

const initialClient = {
  name: "",
  address: "",
  city: "",
  zip: "",
  countryId: "", 
};

// eslint-disable-next-line react/prop-types
function CreateClientDialog({ isOpen, onClose, onSave }) {
  const [client, setClient] = useState(initialClient);
  const [countryList, setCountryList] = useState([]);
  const [loadingCountries, setLoadingCountries] = useState(true);

  useEffect(() => {

    if (isOpen) {
      getCountries()
        .then((response) => {

          setCountryList(response.data);
          setLoadingCountries(false);
        })
        .catch((error) => {
          console.error("Error fetching countries:", error);
          setLoadingCountries(false);
        });
    }
  }, [isOpen]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setClient({ ...client, [name]: value });
  };

  async function handleSave() {
    try {
      const respone = saveClient(client)
      console.log(respone);
    } catch (error) {
      if(error.response && error.respone.status){
        window.alert("Session token expired")
        Logout();
      }
    }
    onSave(client);

    setClient(initialClient);
    onClose();
  }

  return (
    <Modal
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
      <h2>Create Project</h2>
      <hr></hr>
      <form className="create-project-form">
        <div className="label-field">
          <label>Name:</label>
          <input
            type="text"
            name="name"
            value={client.name}
            onChange={handleInputChange}
          />
        </div>
        <div className="label-field">
          <label>Address:</label>
          <input
            type="text"
            name="address"
            value={client.address}
            onChange={handleInputChange}
          />
        </div>
        <div className="label-field">
          <label>City:</label>
          <input
            type="text"
            name="city"
            value={client.city}
            onChange={handleInputChange}
          />
        </div>
        <div className="label-field">
          <label>ZIP:</label>
          <input
            type="text"
            name="zip"
            value={client.zip}
            onChange={handleInputChange}
          />
        </div>
        <div className="label-field">
          <label>Country:</label>
          {loadingCountries ? (
            <p>Loading countries...</p>
          ) : (
            <select
              name="countryId"
              value={client.countryId}
              onChange={handleInputChange}
            >
              <option value="">Select a country</option>
              {countryList.map((country) => (
                <option key={country.id} value={country.id}>
                  {country.name}
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
    </Modal>
  );
}

export default CreateClientDialog;
