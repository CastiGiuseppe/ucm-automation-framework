package be.ucm.cas.openerp.web.test.support;

public class OpenErpUtils {

    private static OpenErpUtils openerputils;

    private static String webUrl;

    public static final String BEAN_NAME = "openErp";
    private static String user;

    private static String pwd;

    private static String serverurl;
    private static String serveruser;
    private static String serverpass;

    private static OpenErpUtils oerp;

    public String getWebUrl() {
        return webUrl;
    }

    public static void setWebUrl(String url) {
        webUrl = url;
    }

    public String getUser() {
        return user;
    }

    public static void setUser(String user) {
        OpenErpUtils.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        OpenErpUtils.pwd = pwd;
    }


    public OpenErpUtils getOpenerputils() {
        return openerputils;
    }

    public static void setOpenerputils(OpenErpUtils openerputils) {
        OpenErpUtils.openerputils = openerputils;
    }

    /**
     * @return the serveruser
     */
    public static String getServeruser() {
        return serveruser;
    }

    /**
     * @param serveruser the serveruser to set
     */
    public static void setServeruser(String serveruser) {
        OpenErpUtils.serveruser = serveruser;
    }

    /**
     * @return the serverpass
     */
    public static String getServerpass() {
        return serverpass;
    }

    /**
     * @param serverpass the serverpass to set
     */
    public static void setServerpass(String serverpass) {
        OpenErpUtils.serverpass = serverpass;
    }

    /**
     * @return the serverurl
     */
    public static String getServerurl() {
        return serverurl;
    }

    /**
     * @param serverurl the serverurl to set
     */
    public static void setServerurl(String serverurl) {
        OpenErpUtils.serverurl = serverurl;
    }

    /**
     * @return the oerp
     */
    public static OpenErpUtils getOerp() {
        return oerp;
    }

    /**
     * @param oerp the oerp to set
     */
    public static void setOerp(OpenErpUtils oerp) {
        OpenErpUtils.oerp = oerp;
    }


}
