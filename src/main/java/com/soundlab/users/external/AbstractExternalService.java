package com.soundlab.users.external;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.soundlab.users.exceptions.ExternalServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public abstract class AbstractExternalService<T> {
    private static final Logger LOG = LogManager.getLogger(AbstractExternalService.class);
    protected T api;
    private Callable<String> tokenGatherer;
    protected volatile String token;

    protected AbstractExternalService(String baseUrl) {
        this.api = getRetrofitClient(baseUrl).create(getApiType());
    }

    protected AbstractExternalService(String baseUrl, Callable<String> tokenGatherer, int tokenRenewalInSeconds) {
        this.api = getRetrofitClient(baseUrl).create(getApiType());
        this.tokenGatherer = tokenGatherer;
        initializeTokenGathering(tokenRenewalInSeconds);
    }

    protected <K> K call(Call<K> call) {
        try {
            return call.execute().body();
        } catch (IOException e) {
            throw new ExternalServiceException(getApiType().getSimpleName(), e.getMessage());
        }
    }

    private void initializeTokenGathering(int renewalInSeconds) {
        try {
            if(token != null) return;

            token = tokenGatherer.call();
            Thread thread = new Thread(() -> {
                while(true) {
                    LOG.info("'{}' token acquired.", getApiType().getSimpleName());
                    try {
                        Thread.sleep(renewalInSeconds*1000);
                        token = tokenGatherer.call();
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
            });

            thread.setDaemon(true);
            thread.start();

        } catch (Exception e) {
            throw new ExternalServiceException(getApiType().getSimpleName(), e.getMessage());
        }
    }

    private static Retrofit getRetrofitClient(String baseUrl) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(getJacksonConverterFactory())
            .build();
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build();
    }

    private static JacksonConverterFactory getJacksonConverterFactory() {
        return JacksonConverterFactory.create(
            new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        );
    }

    @SuppressWarnings("unchecked")
    private Class<T> getApiType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    }


}
