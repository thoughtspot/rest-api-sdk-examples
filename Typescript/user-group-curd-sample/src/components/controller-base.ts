import {
  GetTokenAccessLevelEnum,
  Client,
  DEFAULT_CONFIGURATION,
  SessionController,
} from "@thoughtspot/rest-api-sdk";

const TS_DEFAULT_CONFIG = {
  //Provide the host and credentials for login
  /**
   * Base URL to be connected.
   */
  BASE_URL: "https://your-testcluster.thoughtspot.com",
  /**
   * Username of the user account.
   */
  USERNAME: "testuser",
  /**
   * Password of the user.
   */
  PASSWORD: "testpassword",

  ACCEEPT_LANG: "*",
};
const ControllerBase = async () => {
  DEFAULT_CONFIGURATION.baseUrl = TS_DEFAULT_CONFIG.BASE_URL;
  DEFAULT_CONFIGURATION.acceptLanguage = TS_DEFAULT_CONFIG.ACCEEPT_LANG;
  let client = new Client(DEFAULT_CONFIGURATION);
  const sessionController = new SessionController(client);
  const tokenRes = await sessionController.getToken({
    userName: TS_DEFAULT_CONFIG.USERNAME,
    password: TS_DEFAULT_CONFIG.PASSWORD,
    accessLevel: GetTokenAccessLevelEnum.FULL,
  });
  return tokenRes.result.token?.toString();
};
export { ControllerBase };
