package club.kuzyayo.stars.common.configuration;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class HttpClientFactoryConfig {

    private static final int DEFAULT_CONNECT_TIMEOUT = 2 * 60 * 1000; //2 minutes
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = 0; //infinitely
    private static final int MAX_CONNECTIONS_COUNT = 400;
    private static final int MAX_PER_ROUTE = 200;

    @Bean
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        connectionManager.setMaxTotal(MAX_CONNECTIONS_COUNT);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();

        return HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config).build();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient());
        factory.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
        factory.setReadTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS);
        return factory;
    }

}
