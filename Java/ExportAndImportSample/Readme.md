## Data and report export sample

This sample project is build based on following use case
 - Lists all the liveboards for a user
 - Export the liveboards and associated objects as tml
 - Write the exported tml into separate file for each object
 - Import the tmls present in specified folder

## Setup

Open the project in Visual Studio and update the following in StartHere.cs

Thoughtspot cluster URL and credentials

```
private static final String BASEURL = "https://your-testcluster.thoughtspot.com";
private static final String USERNAME = "testuser";
private static final String PASSWORD = "testpassword";
```

Path to save the file

```
String filePath = "/tmp/test";
```

Once these are variables are updated, you can build and execute the project