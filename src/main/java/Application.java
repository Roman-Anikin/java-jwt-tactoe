import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.getConnector().setPort(8080);
            tomcat.getHost().setAppBase(".");
            tomcat.addWebapp("/", ".");
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}