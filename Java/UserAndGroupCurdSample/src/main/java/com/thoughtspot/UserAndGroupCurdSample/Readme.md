## User and group CRUD operations sample

This sample project is build based on following use case
 - List all the active users in the cluster
 - Create new users
 - Create new group
 - Add the users to the group

## Setup

Open the project in Visual Studio and update the following in StartHere.cs

Thoughtspot cluster URL and credentials

```
private static final String BASEURL = "https://your-testcluster.thoughtspot.com";
private static final String USERNAME = "testuser";
private static final String PASSWORD = "testpassword";
```

Once these are variables are updated, you can build and execute the project