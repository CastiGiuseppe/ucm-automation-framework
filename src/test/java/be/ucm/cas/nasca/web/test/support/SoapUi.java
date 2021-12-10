package be.ucm.cas.nasca.web.test.support;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

import java.net.ProxySelector;

public class SoapUi {

    private static SoapUITestCaseRunner soapRunner = new SoapUITestCaseRunner();

    private static WsdlProject wsdlProject;

    private static WsdlProject getWsdlProject() {
        return wsdlProject;
    }

    private static void setWsdlProject(WsdlProject wsdlProject) {
        SoapUi.wsdlProject = wsdlProject;
    }

    public SoapUITestCaseRunner getSoapRunner() {
        return soapRunner;
    }

    public void setSoapRunner(SoapUITestCaseRunner soapRunner) {
        SoapUi.soapRunner = soapRunner;
    }

    public static Status sendSoapRequest(String soapProject, String testCaseToRun) throws Exception {
        WsdlProject project = new WsdlProject(soapProject);
        setWsdlProject(project);

        TestSuite testSuite = getWsdlProject().getTestSuiteByName("TestSuite");
        TestCase testCase = testSuite.getTestCaseByName(testCaseToRun);

        // create empty properties and run synchronously
        TestRunner runner = testCase.run(new PropertiesMap(), false);
        //Workaround for soap and selenium waiting for solving issue on webdriver or soap api
        ProxySelector.setDefault(TestBase.getProxy());
        return runner.getStatus();
    }
}