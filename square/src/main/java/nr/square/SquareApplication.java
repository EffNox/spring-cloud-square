package nr.square;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.square.retrofit.EnableRetrofitClients;
import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import org.springframework.context.annotation.Bean;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@SpringBootApplication
@EnableRetrofitClients
public class SquareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquareApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(GreetingsClient gc) {
		return ev -> {
			for (int i = 0; i < 5; i++) {
				var call = gc.greet("EffNox");
				var body = call.execute().body();
				System.out.println("rs:" + body);
			}
		};
	}

	@Bean
	@LoadBalanced
	OkHttpClient.Builder okHttpClientBuilder() {
		return new OkHttpClient.Builder();
	}
}

@RetrofitClient("ms-greetings")
interface GreetingsClient {

	@GET("/hello/{name}")
	Call<String> greet(@Path("name") String name);

}
