package be.ucm.cas.nasca.web.test.support;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

final class SeleniumTestNGDataHelper {

    public SeleniumTestNGDataHelper() {
    }

    public static Iterator<Object[]> toObjectArrayIterator(List<List<String>> data) {
        List<Object[]> testData = data.stream().map(List::toArray).collect(Collectors.toList());
        return testData.iterator();
    }
}