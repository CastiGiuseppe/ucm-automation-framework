package be.ucm.cas.nasca.web.test.initialisationfolder;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Date;

public class InitialisationFolder extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    @Test
    public void VerificationFolder() {
        initTest("VerificationFolder", Thread.currentThread().getStackTrace()[1].getFileName());

        doVerificationFolder();

        finishTestExecution();
    }

    private static void doVerificationFolder() {
        Date toDay = new Date();
        String dateFormatee = DateUtils.doFormat(TestData.DATE_NOFORMAT, toDay);
        String pathName = "N:\\CAS\\selenium\\" + dateFormatee;

        int nbOfDoc = 1;
        File fichier = new File(pathName);

        boolean nouveauFichierCree = false;

        while (!nouveauFichierCree) {
            String nouveauNom = pathName + "." + "bak" + nbOfDoc + ".xlsx";

            File nouveauFichier = new File(nouveauNom);

            if (!nouveauFichier.exists()) {
                if (fichier.renameTo(nouveauFichier)) {
                    LOGGER.info("Renommage fichier ok");
                }

                nouveauFichierCree = true;
            }
            nbOfDoc++;
        }
    }
}
