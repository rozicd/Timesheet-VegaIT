import React, { useState, useEffect } from "react";
import './Client.css'
import { getClients, updateClient, deleteClient } from "../../services/ClientService";
import CreateClientDialog from "../dialogs/createClient/CreateClientDialog"; // Import the custom dialog component
import { getCountries } from "../../services/CountryService";
import { Logout } from "../../services/AuthService";

const initialClient = {
  clientId:"",
  name: "",
  address: "",
  city: "",
  zip: "",
  countryId: "", 
};

function Clients() {
  const [clientSearch, setClientSearch] = useState("");
  const [pressedButton, setPressedButton] = useState(null);
  const [pressedValue, setPressedValue] = useState('');
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(4);
  const [clientPaginated, setClientPaginated] = useState({ "items": [], "currentPage": 0, "totalItems": 0, "totalPages": 0 });
  const [isCreateClientDialogOpen, setIsCreateClientDialogOpen] = useState(false); 
  const [expandedClient, setExpandedClient] = useState(null);
  const [countryList, setCountryList] = useState([]);
  const [loadingCountries, setLoadingCountries] = useState(true);
  const [clientForUpdate, setClient] = useState(initialClient);

  const generateAlphabet = () => {
    const alphabet = [];
    for (let i = 65; i <= 90; i++) {
      alphabet.push(String.fromCharCode(i));
    }
    return alphabet;
  };

  const handleButtonClick = (letter) => {
    // Toggle the pressed button
    if (pressedButton === letter) {
      setPressedButton(null); // Unselect if the same button is clicked again
      setPressedValue('');
    } else {
      setPressedButton(letter);
      setPressedValue(letter);
    }
  };

  async function populateClients(params) {
    try {
      const response = await getClients(params);
      setPageSize(4);
      setClientPaginated(response.data);
    } catch (error) {
      if (error.response && error.response.status === 401) {
        window.alert("Session token expired!")
        Logout()
      }
      
    }
  }

  useEffect(() => {
    const params = {
      "page": page,
      "pageSize": pageSize,
      "letter": pressedValue,
      "search": clientSearch
    }
    populateClients(params);
  }, [page, pageSize, pressedValue, clientSearch,isCreateClientDialogOpen]);


  const openCreateClientDialog = () => {
    setIsCreateClientDialogOpen(true);
  };


  const closeCreateClientDialog = () => {
    setIsCreateClientDialogOpen(false);
  };

  const toggleClientExpansion = (client) => {
    if (expandedClient === client['clientId']) {
      setExpandedClient(null);
      return;
    }

    setExpandedClient(client['clientId']);
    setClient(client)
    getCountries()
    .then((response) => {

      setCountryList(response.data);
      setLoadingCountries(false);
    })
    .catch((error) => {
      if (error.response && error.response.status === 401) {
        window.alert("Session token expired!")
        Logout()
      }
      console.error("Error fetching countries:", error);
      setLoadingCountries(false);
    });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setClient({ ...clientForUpdate, [name]: value });
  };

  function handleUpdate(){
    updateClient(clientForUpdate).then(()=>{
    }).catch((error)=>{
      if (error.response && error.response.status === 401) {
        window.alert("Session token expired!")
        Logout()
      }
    else{
        console.error("Error updating client:", error)
    }
    })
  }
  function handleDelete(){
    deleteClient(clientForUpdate).then(()=>{
      console.log("successfull")
    }).catch((error)=>{
      if (error.response && error.response.status === 401) {
        window.alert("Session token expired!")
        Logout()
      }
    else{
        console.error("Error deleting client:", error)
    }
    })
  }


  return (
    <div className="container">
      <div className="create-search-container">
        <button className="create-project-button" onClick={openCreateClientDialog}>Create new client</button>
        <input
          type="text"
          className="search-field"
          value={clientSearch}
          onChange={(e) => setClientSearch(e.target.value)} />
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
      <div className="clients-container">
        {clientPaginated['items'].map((client) => (
          <div key={client['clientId']} className="client-item">
            <div
              className={`clients ${expandedClient === client['clientId'] ? 'expanded' : ''}`}
              onClick={() => toggleClientExpansion(client)} // Keep this as a function reference
            >

              <label className="client-name">{client['name']}</label>
              <label className="delete-button">X</label>
            </div>
            {expandedClient === client['clientId'] && (
              <div className="expanded-client">
                <div className="expanded-content">
                  <div className="label-field-container">
                    <label>Client name:</label>
                    <input type="text" name="name" onChange={handleInputChange} value={clientForUpdate.name} />
                  </div>
                  <div className="label-field-container">
                    <label>Address:</label>
                    <input type="text" name="address" onChange={handleInputChange} value={clientForUpdate.address} />
                  </div>
                  <div className="label-field-container">
                    <label>City:</label>
                    <input type="text" name="city" onChange={handleInputChange} value={clientForUpdate.city} />
                  </div>
                  <div className="label-field-container">
                    <label>Zip/Postal code:</label>
                    <input type="text" name="zip" onChange={handleInputChange} value={clientForUpdate.zip} />
                  </div>
                  <div className="label-field-container">
                      <label>Country:</label>
                      {loadingCountries ? (
                        <p>Loading countries...</p>
                      ) : (
                        <select
                          name="countryId"
                          defaultValue={clientForUpdate.countryId}
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
                  
                  </div>
                  <div className="buttons-container">
                    <button className="update-button" onClick={handleUpdate}>Update</button>
                    <button className="delete-client-button" onClick={handleDelete}>Delete</button>
                  </div>
                
              </div>
            )}
          </div>
        ))}
      </div>
      <div className="pagination-controls">
        <button className={`pagination-button ${page === 1 ? 'disabled' : ''}`} onClick={() => setPage(page - 1)} disabled={page === 1}>Previous</button>
        <label>| {page} |</label>
        <button className={`pagination-button ${page === clientPaginated.totalPages ? 'disabled' : ''}`} onClick={() => setPage(page + 1)} disabled={page === clientPaginated.totalPages}>Next</button>
      </div>

      <CreateClientDialog
        isOpen={isCreateClientDialogOpen}
        onClose={closeCreateClientDialog}
        onSave={(clientData) => {
          console.log("Client data:", clientData);
          closeCreateClientDialog(); 
        }}
      />
    </div>
  );
}

export default Clients;
