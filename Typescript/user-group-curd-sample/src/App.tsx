import React, { useEffect } from "react";
import logo from "./logo.svg";
import "./App.css";
import User from "./components/user-component";
import Group from "./components/group-component";
import Home from "./components/Home";
import { BrowserRouter, Link, Route, Routes } from "react-router-dom";
function App() {
  return (
    <div className="App">
      <strong> REST API SDK USER AND GROUP EXAMPLE</strong>
      <BrowserRouter>
        <div className="sidebar">
        <Link to="/"></Link>
          <Link to="/user">Users</Link>
          <Link to="/group" className="paddingLeft">
            Groups
          </Link>
        </div>
        <br />
        <br />
        <Routes>
        <Route path="/" element={<Home />} />
          <Route path="/user" element={<User />} />
          <Route path="/group" element={<Group />} />
        </Routes>
      </BrowserRouter>

    </div>
  );
}

export default App;
