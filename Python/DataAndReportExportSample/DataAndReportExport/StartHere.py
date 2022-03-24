#! /usr/bin/env python3
# pylint: disable= no-name-in-module, W1514

import os

from DataAndReportExportSample.DataAndReportExport.ControllerBase \
    import ControllerBase
from DataAndReportExportSample.DataAndReportExport.DataAndReportMethods\
    import DataAndReportExport


def ResultToFile(result, filePath, liveboardId):
    suffix = '.dat'
    fileName = liveboardId + suffix

    path = os.path.join(filePath, fileName)

    print(path)

    # Write the object to file
    with open(path, 'w') as fp:
        fp.write(result+"")
        fp.close()


class StartHere:
    def __init__(self):
        # Provide the host and credentials for login
        self.v_BaseURL = "https://your-testcluster.thoughtspot.com"
        self.v_UserName = "testuser"
        self.v_Password = "testpassword"

    def DataExport(self):
        client = ControllerBase.getClient(
            self, self.v_BaseURL, self.v_UserName, self.v_Password)
        p_Id = 'bea79810-145f-4ad0-a02c-4177a6e7d861'
        viz_Id = '76fc7ab5-5a9b-4eee-80a6-b8328d4d0afc'

        # Export TML for the list of object ids received from above inputs
        result = DataAndReportExport.GetLiveboardData(
            self, client, p_Id, viz_Id)

        # Write the TMLs into file on disk(Existing directory)
        filePath = 'tmp/exporttest'
        ResultToFile(result, filePath, p_Id)


if __name__ == '__main__':
    s = StartHere()
    s.DataExport()
