import {
  Client,
  DEFAULT_CONFIGURATION,
  TspublicRestV2UserAddgroupRequest,
  TspublicRestV2UserCreateRequest,
  UserController,
} from "@thoughtspot/rest-api-sdk";
import React, { useEffect } from "react";
import { ControllerBase } from "./controller-base";
import "../App.css";

function User() {
  let client = new Client(DEFAULT_CONFIGURATION);
  useEffect(() => {
    getToken();
  }, []);
  const getToken = async () => {
    let token: string | undefined = await ControllerBase();
    if (token) {
      DEFAULT_CONFIGURATION.accessToken = token;
      client = new Client(DEFAULT_CONFIGURATION);
    }
  };

  const getUsers = async () => {
    const userController = new UserController(client);
    const res = await userController.getUser("tsadmin");
    alert(res.result.name + " " + " Details of the user");
  };
  const createUser = async () => {
    const userController = new UserController(client);
    const body: TspublicRestV2UserCreateRequest = {
      name: "SampleUser",
      password: "P2wd_1234",
      displayName: "Sample User",
      mail: "sampleuser2@thoughtspot.com",
    };
    const res = await userController.createUser(body);
    alert(res.result.name + "Details of the user created");
  };
  const addUserToGroup = async () => {
    const userController = new UserController(client);
    const body: TspublicRestV2UserAddgroupRequest = {
      name: "SampleUser",
      groups: [
        {
          name: "Demo Retail Group",
        },
      ],
    };
    const res = await userController.addUserToGroups(body);
    alert("Successfully assigned groups to the user");
  };

  const deleteUser = async () => {
    const userController = new UserController(client);
    const res = await userController.deleteUser("SampleUser");
    alert("User successfully deleted");
  };
  return (
    <div className="right">
      <p> Fetch User With Default Values</p> <br></br>
      <button className="btn info" onClick={getUsers}>
        GetUser
      </button>
      <p> Create User With Default Values</p> <br></br>
      <button className="btn info" onClick={createUser}>
        CreateUser
      </button>
      <p> Add Users to Group With Default Values</p> <br></br>
      <button className="btn info" onClick={addUserToGroup}>
        AddUserToGroup
      </button>
      <p> Delete User With Default Values</p> <br></br>
      <button className="btn info" onClick={deleteUser}>
        Delete User
      </button>
    </div>
  );
}

export default User;
