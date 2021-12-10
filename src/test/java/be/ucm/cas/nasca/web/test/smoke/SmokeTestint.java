package be.ucm.cas.nasca.web.test.smoke;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestBase;
import org.testng.annotations.Test;

public class SmokeTestint extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.SMOKE;
    }

    @Test
    public void smokeTest() {
        if (homeMenuPage.isErreurHttp404()) {
            RunBatch.restartTomcat();

            loginNasca();
            if (homeMenuPage.isErreurHttp404()) {
                RunBatch.restartTomcat();
            }
        }
    }
}
