package be.ucm.cas.nasca.web.test.support;

import be.ucm.cas.nasca.web.test.support.dao.NascaDao;

public class DaoFunctionality {

    private static NascaDao nascaDao;

    public static final String BEAN_NAME_FOR_NASCA_DAO = "nascaDao";

    public static void cleanAllDataByIdentite(String identity) {
        getNascaDao().cleanUpDB(identity);
    }

    public static void cleanAllFluxByIdentite(String identity) {
        getNascaDao().prepareFluxInInjection(identity);
    }

    public static void cleanWsInastiExchange() {
        getNascaDao().cleanInastiHisto();
    }

    public static NascaDao getNascaDao() {
        return nascaDao;
    }

    public void setNascaDao(NascaDao nascaDao) {
        DaoFunctionality.nascaDao = nascaDao;
    }
}
