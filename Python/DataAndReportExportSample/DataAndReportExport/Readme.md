## Data and report export sample

This sample project is build based on following use case
 - Export the data for a Liveboard in JSON format and save to a file

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

Liveboard Id and Viz Id for which data needs to be downloaded

```
string objectId = "33248a57-cc70-4e39-9199-fb5092283381";
string vizId = "730496d6-6903-4601-937e-2c691821af3c";
```

Path to save the file

```
string filePath = "/tmp/exporttest";
```

Once these are variables are updated, you can build and execute the project
