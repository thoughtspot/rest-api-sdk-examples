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
public const string v_BaseURL = "https://your-testcluster.thoughtspot.com";
public const string v_UserName = "testuser";
public const string v_Password = "testpassword";
```

Once these are variables are updated, you can build and execute the project