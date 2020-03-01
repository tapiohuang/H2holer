package org.hycode.h2holer.server;



import org.hycode.h2holer.server.services.H2holerBootStrap;
import org.hycode.h2holer.server.utils.ServerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class H2holerServerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(H2holerServerApplication.class, args);
        ServerUtil.setApplicationContext(applicationContext);

        H2holerBootStrap h2holerBootStrap = new H2holerBootStrap();
        h2holerBootStrap.initializer();
    }

}
