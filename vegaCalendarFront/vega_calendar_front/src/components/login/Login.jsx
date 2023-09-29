import { useState } from 'react';
import React from 'react';
import '../login/Login.css'
import logo from '../../assets/img/logo-large.png'
import { SignIn } from '../../services/AuthService';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  
  const handleLogin = async (event) => {
    event.preventDefault();
    try{
      const response = await SignIn(email, password)
      localStorage.setItem("accessToken", response.data['accessToken'])
      window.location.reload();
      
    }catch(error){
      if (error.response && error.response.status === 400) {
        window.alert("Wrong email or password!")
      }
      console.log(error.response)
    }
    
  };
  return (
    <div className='content'>
      <div className='logo-wrapper'>
        <a href='/login'>
          <img src={logo} alt='logo'></img>
        </a>
      </div>
      <div className='login-content-wrapper'>
        <div className='login-content'>
          <h1>LOGIN</h1>
          <form onSubmit={handleLogin}>
            <input
                    type="text"
                    placeholder="Email"
                    className="input-fields"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
            <input
                    type="password"
                    placeholder="Password"
                    className="input-fields"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
            <label>
              <input
                type="checkbox"
              />
              Rember me
            </label>
            <div className='buttons-container'>
              <a href='/login' className='forgot-password'>Forgot password</a>
              <button className='login-button' type='submit'>Login</button>
              
            </div>
          </form>
        </div>
      </div>
    </div>
)}
export default Login;