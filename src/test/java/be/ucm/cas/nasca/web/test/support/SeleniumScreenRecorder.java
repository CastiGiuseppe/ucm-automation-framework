package be.ucm.cas.nasca.web.test.support;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;

class SeleniumScreenRecorder extends ScreenRecorder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private String name;

    public SeleniumScreenRecorder(GraphicsConfiguration cfg,
                                  Rectangle captureArea, Format fileFormat, Format screenFormat,
                                  Format mouseFormat, Format audioFormat, File movieFolder,
                                  String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            if (movieFolder.mkdirs()) {
                LOGGER.info("Répertoire Movie créé");
            }
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\\" + movieFolder + "\\" + "is not a directory.");
        }

        return new File(movieFolder, name + "." + Registry.getInstance().getExtension(fileFormat));
    }
}