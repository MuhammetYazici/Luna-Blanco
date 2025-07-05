package utilities;
import com.microsoft.playwright.*;
import io.cucumber.java.Scenario;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PW{
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    private static final String TRACE_DIR = "src/test/java/utilities/traceViewer/";

    public static Page getPage() {
        if (page == null) {
            playwright = Playwright.create();

            String browserName = ConfigReader.getProperty("browser");
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);

            switch (browserName) {
                case "firefox":
                    browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    break;
                case "chromium":
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    break;
                case "edge":
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    launchOptions.setChannel("msedge");
                    break;
                case "chrome":
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                    launchOptions.setChannel("chrome");
                    break;
                default:
                    String message = "Browser name : " + browserName + " Geçersiz olarak ayrıştırıldı.";
                    message += " Lütfen desteklenen tarayıcılardan birini belirtin [Firefox, Edge, Chrome, Chromium]";
                    throw new IllegalArgumentException(message);
            }

            context = browser.newContext();

            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));

            page = context.newPage();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();
            page.setViewportSize(width,height);
        }
        return page;
    }

    public static void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String tracePath = getTraceFilePath(scenario);
                context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));
            } else {
                context.tracing().stop();
                cleanupOldTraces();
            }
        } catch (Exception e) {
            System.err.println("Trace işlemi sırasında hata oluştu: " + e.getMessage());
        } finally {
            cleanUp();
        }
    }

    private static String getTraceFilePath(Scenario scenario) {
        String methodName = scenario.getName().replaceAll(" ", "_");
        String date = new SimpleDateFormat("_HH_mm_ss_ddMMyyyy").format(new Date());
        return TRACE_DIR + methodName + date + "-trace.zip";
    }

    private static void cleanUp() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();

        context = null;
        browser = null;
        playwright = null;
        page = null;
    }

    private static void cleanupOldTraces() {
        final long EXPIRATION_TIME = 86400000;
        File dir = new File(TRACE_DIR);
        File[] files = dir.listFiles();
        if (files != null) {
            long now = System.currentTimeMillis();
            for (File file : files) {
                if (now - file.lastModified() > EXPIRATION_TIME) {
                    if (!file.delete()) {
                        System.err.println("Trace dosyası silinemedi: " + file.getPath());
                    }
                }
            }
        }
    }
}
