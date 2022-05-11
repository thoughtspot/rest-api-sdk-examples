import {
  Client,
  DEFAULT_CONFIGURATION,
  GroupController,
  TspublicRestV2GroupCreateRequest,
  TspublicRestV2GroupUpdateRequest,
  TspublicRestV2UserCreateRequest,
  UserController,
} from "@thoughtspot/rest-api-sdk";
import React, { useEffect } from "react";
import { ControllerBase } from "./controller-base";
import "../App.css";

function Group() {
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

  const getGroups = async () => {
    const groupController = new GroupController(client);
    const res  = await groupController?.getGroup('Sample Group');
    alert(res.result.name + " " + " Details fetched Successfully");
  };
  const createGroup = async () => {
    const groupController = new GroupController(client);
    const body: TspublicRestV2GroupCreateRequest = {
      name: "Sample Group",
      displayName: "Sample Group",
    };
    const res = await groupController.createGroup(body);
    alert(res.result.name + "Details of the group created");
  };
  const updateGroup = async () => {
    const groupController = new GroupController(client);
    const body: TspublicRestV2GroupUpdateRequest = {
      displayName: "Sample Group Update",
    };
    const res = await groupController.updateGroup(body);
    alert( "Group successfully updated");
  };
  const deleteGroup = async () => {
    const groupController = new GroupController(client);
    const res = await groupController.deleteGroup('Sample Group');
    alert( "Group successfully deleted");
  };

  return (
    <div>
        <p> Fetch Group With Default Values</p> <br></br>
      <button className="btn info" onClick={getGroups}>Get Group</button>
      <p> Create Group With Default Values</p> <br></br>
      <button className="btn info" onClick={createGroup}>Create Group</button>
      <p> Update Group With Default Values</p> <br></br>
      <button className="btn info" onClick={updateGroup}>Update Group</button>
      <p> Delete Group With Default Values</p> <br></br>
      <button className="btn info" onClick={deleteGroup}>Delete Group</button>
    </div>
  );
}

export default Group;
