package com.thoughtspot.UserAndGroupCurdSample;

import java.util.ArrayList;
import java.util.List;

import localhost.RESTAPISDKClient;
import localhost.models.GroupResponse;
import localhost.models.TspublicRestV2UserCreateRequest;
import localhost.models.UserResponse;

/**
 * Test User and Group CRUD sample.
 */
public final class StartHere {

    //Provide the host and credentials for login
    /**
     * Base URL to be connected.
     */
    private static final String BASEURL = "https://your-testcluster.thoughtspot.com";
    /**
     * Username of the user account.
     */
    private static final String USERNAME = "testuser";
    /**
     * Password of the user.
     */
    private static final String PASSWORD = "testpassword";

    /**
     * Constructor.
     */
    private StartHere() {
    }

    /**
     * User and Group CRUD operation execution starts here.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        try {
            ControllerBase controller = new ControllerBase();
            RESTAPISDKClient client = controller.getClient(BASEURL, USERNAME, PASSWORD);

            //List the active users in the cluster
            UserMethods userMethods = new UserMethods();
            List<String> names = userMethods.searchActiveUsers(client);
            System.out.println("List of users: \n ");

            for (String name : names) {
                System.out.println(String.format("%s \n\n", name));
            }

            //Create new users
            List<TspublicRestV2UserCreateRequest> userCreateBody = new ArrayList<>();

            TspublicRestV2UserCreateRequest tspublicRestV2UserCreateRequest =
                    new TspublicRestV2UserCreateRequest();

            tspublicRestV2UserCreateRequest.setName("SampleUser1");
            tspublicRestV2UserCreateRequest.setDisplayName("Sample User1");
            tspublicRestV2UserCreateRequest.setPassword("Welcome123");
            tspublicRestV2UserCreateRequest.setMail("sampleuser1@thoughtspot.com");

            userCreateBody.add(tspublicRestV2UserCreateRequest);

            tspublicRestV2UserCreateRequest = new TspublicRestV2UserCreateRequest();

            tspublicRestV2UserCreateRequest.setName("SampleUser2");
            tspublicRestV2UserCreateRequest.setDisplayName("Sample User2");
            tspublicRestV2UserCreateRequest.setPassword("Welcome123");
            tspublicRestV2UserCreateRequest.setMail("sampleuser2@thoughtspot.com");

            userCreateBody.add(tspublicRestV2UserCreateRequest);

            List<UserResponse> userResult = userMethods.createNewUser(client, userCreateBody);

            for (UserResponse userResponse : userResult) {
                System.out.println(String.format("User detail: %s \n\n", userResponse.toString()));
            }

            //Create a group if it does not exist
            GroupMethods groupMethods = new GroupMethods();

            String groupName = "Sample Group";

            GroupResponse groupResult = groupMethods.createNewGroup(client, groupName);

            System.out.println(String.format("Group detail: %s \n\n", groupResult.toString()));

            //Add users to the group
            groupMethods.addUserToGroup(client, userCreateBody, groupName);

            System.out.println(String.format("Users added to the group %s", groupName));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
