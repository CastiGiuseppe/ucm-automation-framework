package be.ucm.cas.nasca.web.test.leveemajorations;

import be.ucm.cas.nasca.web.test.support.DateUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public abstract class LeveeMajorationAbstract extends LeveeMajorationsTestBase {

    private String periode;

    private String periodeMoins3Ans;

    void doLaunchLm() {
        Calendar calJour = Calendar.getInstance();
        calJour.setTime(DateUtils.getMinusYearsMachine(1));
        setPeriode(String.valueOf(calJour.get(Calendar.YEAR)) + "/1");
        calJour.setTime(DateUtils.getMinusYearsMachine(3));
        setPeriodeMoins3Ans(String.valueOf(calJour.get(Calendar.YEAR)));
    }

    void verificationDocument(String resultat, String typeAffilie, String typeLeveeMajoration, String nissorbce,
                              Map<String, List<String>> periodesEtMontantsMajorationsLM, String typeLMFlux,
                              String modificationLeveeMajoration, String validationLeveeMajoration, String periode,
                              String periodeMoins3Ans) {
        if ("REM".equals(resultat)) {
            verificationRemEtDocument(typeAffilie, typeLeveeMajoration, nissorbce);
        } else {
            Map<String, List<String>> periodesEtMontantsMajorationsDecisionLM = verificationSuspensions(
                    typeAffilie, resultat, nissorbce,
                    periodesEtMontantsMajorationsLM);

            receptionFluxInasti(nissorbce, typeAffilie, typeLMFlux,
                    periodesEtMontantsMajorationsDecisionLM,
                    typeLeveeMajoration, modificationLeveeMajoration,
                    validationLeveeMajoration, resultat);

            verificationOperationEtDocumentSuiteDecision(typeAffilie, resultat, periode, periodeMoins3Ans, nissorbce);
        }
    }

    private void setPeriode(String periode) {
        this.periode = periode;
    }

    String getPeriode() {
        return periode;
    }

    private void setPeriodeMoins3Ans(String periodeMoins3Ans) {
        this.periodeMoins3Ans = periodeMoins3Ans;
    }

    String getPeriodeMoins3Ans() {
        return periodeMoins3Ans;
    }
}