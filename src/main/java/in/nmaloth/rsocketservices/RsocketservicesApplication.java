package in.nmaloth.rsocketservices;

import com.sun.security.auth.login.ConfigFile;
import in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.x.async.modeled.JacksonModelSerializer;

import org.apache.zookeeper.ClientCnxnSocketNIO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.nativex.hint.*;

import java.nio.channels.SocketChannel;
import java.security.Security;
import java.util.concurrent.CountDownLatch;


@TypeHint(typeNames = "sun.security.provider.ConfigFile", access = AccessBits.ALL)
@TypeHint(
		typeNames =  "org.apache.zookeeper.ClientCnxnSocketNIO",
		access = AccessBits.ALL
)
@NativeHint(options = "--enable-all-security-services")
@TypeHint(typeNames = "in.nmaloth.rsocketservices.model.proto.ServerRegistrationOuterClass.ServerRegistration",access = AccessBits.ALL)
@SpringBootApplication
@Slf4j
public class RsocketservicesApplication {

	public static void main(String[] args) throws InterruptedException {

		ConfigurableApplicationContext ctx = SpringApplication.run(RsocketservicesApplication.class, args);


		CountDownLatch countDownLatch = ctx.getBean(CountDownLatch.class);

		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			log.info("Shutting down Locator");
		}));

		countDownLatch.await();


	}

}


@Configuration
class ConfigDev {

	@Bean
	public CountDownLatch getLatch(){
		return new CountDownLatch(1);
	}

}

