package com.thoughtspot.UserAndGroupCurdSample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import localhost.RESTAPISDKClient;
import localhost.controllers.GroupController;
import localhost.exceptions.ApiException;
import localhost.models.*;

/**
 * Group CRUD operations.
 */
public class GroupMethods {
    /**
     * Create a new group by name.
     *
     * @param client client.
     * @param name name.
     * @return group response.
     * @throws IOException .
     * @throws ApiException .
     */
    public GroupResponse createNewGroup(RESTAPISDKClient client,
                                        String name) throws IOException, ApiException {
        GroupController groupController = client.getGroupController();

        int status = 0;
        GroupResponse result = null;

        try {
            result = groupController.getGroup(name, "");
            status = 1;
        } catch (Exception e) {
        } finally {
            if (status == 0) {
                TspublicRestV2GroupCreateRequest tspublicRestV2GroupCreateRequest =
                        new TspublicRestV2GroupCreateRequest();

                tspublicRestV2GroupCreateRequest.setName(name);
                tspublicRestV2GroupCreateRequest.setDisplayName(name);
                tspublicRestV2GroupCreateRequest.setVisibility(CreateGroupVisibilityEnum.DEFAULT);
                List<CreateGroupPrivilegesEnum> privilegeEnum = new ArrayList<>();
                privilegeEnum.add(CreateGroupPrivilegesEnum.DEVELOPER);
                privilegeEnum.add(CreateGroupPrivilegesEnum.DATAMANAGEMENT);
                privilegeEnum.add(CreateGroupPrivilegesEnum.EXPERIMENTALFEATUREPRIVILEGE);
                tspublicRestV2GroupCreateRequest.setPrivileges(privilegeEnum);
                tspublicRestV2GroupCreateRequest.setType(CreateGroupTypeEnum.LOCAL_GROUP);

                result = groupController.createGroup(tspublicRestV2GroupCreateRequest);
            }
        }
        return result;
    }

    /**
     * Add user to a group.
     *
     * @param client client.
     * @param userNames userNames.
     * @param groupname groupname.
     * @return returns true if added else returns false.
     * @throws IOException .
     * @throws ApiException .
     */
    public Boolean addUserToGroup(RESTAPISDKClient client,
                                  List<TspublicRestV2UserCreateRequest> userNames,
                                  String groupname) throws IOException, ApiException {

        GroupController groupController = client.getGroupController();

        TspublicRestV2GroupAdduserRequest tspublicRestV2GroupAdduserRequest =
                new TspublicRestV2GroupAdduserRequest();
        tspublicRestV2GroupAdduserRequest.setName(groupname);
        List<UserNameAndIDInput> userList = new ArrayList<>();

        for (TspublicRestV2UserCreateRequest tspublicRestV2UserCreateRequest : userNames) {
            UserNameAndIDInput userNameAndIDInput = new UserNameAndIDInput();
            userNameAndIDInput.setName(tspublicRestV2UserCreateRequest.getName());
            userList.add(userNameAndIDInput);
        }
        tspublicRestV2GroupAdduserRequest.setUsers(userList);

        Boolean result = groupController.addUsersToGroup(tspublicRestV2GroupAdduserRequest);

        return result;
    }

    /**
     *
     * @param client RESTAPISDKClient
     * @param group group name and id
     * @return returns true when group deleted successfully.
     * @throws IOException .
     * @throws ApiException .
     */
    public Boolean deleteGroup(
            RESTAPISDKClient client, UserNameAndID group)
            throws IOException, ApiException {

        GroupController groupController = client.getGroupController();
        return groupController.deleteGroup(group.getName(), null);
    }
}
