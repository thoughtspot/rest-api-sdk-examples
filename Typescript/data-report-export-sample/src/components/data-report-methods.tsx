import {
  Client,
  DataController,
  DEFAULT_CONFIGURATION,
  LiveboardDataFormatTypeEnum,
  MetadataController,
  PdfOptionsInputOrientationEnum,
  ReportController,
  LiveboardReportTypeEnum,
  SearchObjectHeaderTypeEnum,
} from "@thoughtspot/rest-api-sdk";
import { useEffect, useState } from "react";
import "../App.css";
import { GridView } from "./gridview";
import { ControllerBase } from "./controller-base";
const DataAndReports = () => {
  const [liveBoards, setLiveBoards] = useState([]);
  const selectedLiveBoards: Array<any> = [];
  let client = new Client(DEFAULT_CONFIGURATION);

  /**
   * Get liveboard data.
   */
  const getLiveboards = async () => {
    const metadataController = new MetadataController(client);
    const liveBoards: any = await metadataController.searchObjectHeader({
      type: SearchObjectHeaderTypeEnum.LIVEBOARD,
    });
    setLiveBoards(liveBoards.result.headers);
  };

  /**
   * Get AccessToken.
   */
  const getAccessToken = async () => {
    let token: string | undefined = await ControllerBase();
    if (token) {
      DEFAULT_CONFIGURATION.accessToken = token;
      client = new Client(DEFAULT_CONFIGURATION);
    }
    getLiveboards();
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  /**
   *  return Selected Liveboard name.
   */
  const getLiveBoardName = (): string => {
    const liveboards: any = liveBoards.filter(
      (val: any) => val.id === selectedLiveBoards[0]
    );
    if (liveboards.length === 0) return "Liveboard";
    return liveboards[0].name;
  };

  /**
   *  Download Liveboard Data with JSON format.
   */
  const exportLiveboard = async () => {
    const dataController = new DataController(client);
    const exportLiveBoardData: any = await dataController.liveboardData({
      id: selectedLiveBoards[0].toString(),
      formatType: LiveboardDataFormatTypeEnum.FULL,
    });
    const fileName = getLiveBoardName();
    const element = document.createElement("a");
    element.setAttribute(
      "href",
      "data:text/plain;charset=utf-8, " +
        encodeURIComponent(JSON.stringify(exportLiveBoardData.result))
    );
    element.setAttribute("download", `${fileName}.json`);
    document.body.appendChild(element);
    element.click();

    document.body.removeChild(element);
  };

  /**
   *  Download Liveboard Data with PDF format.
   */

  const downloadLiveboardReport = async () => {
    const reportController = new ReportController(client);
    let ids:[String] = selectedLiveBoards[0].toString()
    const exportLiveBoardData: any = await reportController.liveboardReport({
      id: [selectedLiveBoards[0]].toString(),
      type: LiveboardReportTypeEnum.PDF,
      pdfOptions: {
        orientation: PdfOptionsInputOrientationEnum.LANDSCAPE,
      },
    });
    const fileName = getLiveBoardName();

    //Convert the Byte Data to BLOB object.
    let blob = new Blob([exportLiveBoardData.result], {
      type: "application/octetstream",
    });

    let url = window.URL || window.webkitURL;
    let link = url.createObjectURL(blob);
    var element = document.createElement("a");
    element.setAttribute("download", fileName);
    element.setAttribute("href", link);
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  };

  const pushSelectedLiveboard = (event: any) => {
    selectedLiveBoards.splice(0, selectedLiveBoards.length);
    if (event.target.checked) selectedLiveBoards.push(event.target.value);
  };

  return (
    <div>
      <div className="headDownload">
        LIVEBOARDS
        <button onClick={exportLiveboard} className="runBtn">
          Download JSON <i className="fa fa-download"></i>
        </button>
        <button onClick={downloadLiveboardReport} className="dataBtn">
          Download PDF <i className="fa fa-download"></i>
        </button>
      </div>
      <div className="content">
        {liveBoards.length === 0 ? (
          "loading..."
        ) : (
          <GridView
            liveboards={liveBoards}
            pushSelectedLiveboard={pushSelectedLiveboard}
          ></GridView>
        )}
      </div>
    </div>
  );
};

export default DataAndReports;
