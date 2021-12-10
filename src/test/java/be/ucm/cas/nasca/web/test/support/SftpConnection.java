package be.ucm.cas.nasca.web.test.support;

import com.jcraft.jsch.*;

import java.io.*;

public class SftpConnection {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static String sFtpHost;
    private static String sFtpUser;
    private static String sFtpPwd;
    private static String sFtpServerDir;

    private static final int SFTPPORT = 22;
    public static final String BEAN_NAME = "SftpConnection";

    private static Session session = null;
    private static ChannelSftp channelSftp = null;

    public static void createSession(String host, String user, String pass) {
        JSch jsch = new JSch();

        try {
            session = jsch.getSession(user, host, SFTPPORT);
            session.setPassword(pass);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (Exception e) {
            LOGGER.error("Unable to connect to FTP server", e);
        }
        setSession(session);
        setChannelSftp(channelSftp);
    }

    private void printConsoleResult(Channel channel) throws IOException, JSchException {
        ChannelExec ce = (ChannelExec) channel;
        ce.connect();

        InputStream input = ce.getInputStream();
        Reader reader = new InputStreamReader(input);
        BufferedReader buffered = new BufferedReader(reader);

        while (true) {
            String line = buffered.readLine();
            if (line == null) {
                break;
            } else {
                LOGGER.info(line);
            }
        }

        waitTillCloses(ce);
        ce.disconnect();
        destroySession(getChannelSftp(), getSession());
    }

    private void printResult(Channel channel) throws JSchException {
        ChannelExec ce = (ChannelExec) channel;
        ce.connect();

        waitTillCloses(ce);
        ce.disconnect();
        destroySession(getChannelSftp(), getSession());
    }

    private void waitTillCloses(ChannelExec ce) {
        while (true) {
            if (ce.isClosed()) {
                LOGGER.info("exit-status: " + ce.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void runCommandWithResult(String cmd) {
        try {
            Channel channel = getSession().openChannel("exec");
            ((ChannelExec) channel).setCommand(cmd);
            channel.setInputStream(null);
            channel.connect();
            printConsoleResult(channel);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public String runCommandGetResult(String cmd) {
        InputStream in = null;
        try {
            Channel channel = getSession().openChannel("exec");
            ((ChannelExec) channel).setCommand(cmd);
            channel.setInputStream(null);
            in = channel.getInputStream();
            channel.connect();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return getStringFromInputStream(in);

    }

    public void runCommand(String cmd) throws JSchException {
        Channel channel = getSession().openChannel("exec");
        ((ChannelExec) channel).setCommand(cmd);
        channel.setInputStream(null);
        channel.connect();
        printResult(channel);

    }

    public Session getSession() {
        return session;
    }

    private static void setSession(Session session) {
        SftpConnection.session = session;
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    private static void setChannelSftp(ChannelSftp channelSftp) {
        SftpConnection.channelSftp = channelSftp;
    }

    public void destroySession(ChannelSftp c, Session session) {
        try {
            SeleniumUtils.waitForBatch();
            c.disconnect();
            session.disconnect();
        } catch (Exception exc) {
            LOGGER.error("Unable to disconnect from server. " + exc);
        }
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }

        return sb.toString();
    }

    private String getsFtpHost() {
        return sFtpHost;
    }

    public void setsFtpHost(String sFtpHost) {
        SftpConnection.sFtpHost = sFtpHost;
    }

    private String getsFtpUser() {
        return sFtpUser;
    }

    public void setsFtpUser(String sFtpUser) {
        SftpConnection.sFtpUser = sFtpUser;
    }

    private String getsFtpPwd() {
        return sFtpPwd;
    }

    public void setsFtpPwd(String sFtpPwd) {
        SftpConnection.sFtpPwd = sFtpPwd;
    }

    private String getsFtpServerDir() {
        return sFtpServerDir;
    }

    public void setsFtpServerDir(String sFtpServerDir) {
        SftpConnection.sFtpServerDir = sFtpServerDir;
    }
}