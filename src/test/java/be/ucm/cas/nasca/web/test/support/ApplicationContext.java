package be.ucm.cas.nasca.web.test.support;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ApplicationContext {

    private static FileSystemXmlApplicationContext appCtx;

    public ApplicationContext() {
        super();
        if (getAppCtx() == null)
            setAppCtx(new FileSystemXmlApplicationContext(TestData.SPRING_CONTEXT));
    }

    public static FileSystemXmlApplicationContext getAppCtx() {
        return appCtx;
    }

    private void setAppCtx(FileSystemXmlApplicationContext fileSystemXmlApplicationContext) {
        ApplicationContext.appCtx = fileSystemXmlApplicationContext;
    }
}