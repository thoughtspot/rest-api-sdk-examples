## Data and report export sample

This sample project is build based on following use case
 - Lists all the liveboards for a user
 - Export the liveboards and associated objects as tml
 - Write the exported tml into separate file for each object
 - Import the tmls present in specified folder

## Setup

Install the RestAPI SDK

```
pip install thoughtspot-rest-api-sdk
```

Open the project in PyCharm and update the following in StartHere.cs

Thoughtspot cluster URL and credentials

```
 v_BaseURL = "https://your-testcluster.thoughtspot.com";
 v_UserName = "testuser";
 v_Password = "testpassword";
```

Path to save the file

```
string filePath = "/tmp/test";
```

Once these are variables are updated, you can build and execute the project
