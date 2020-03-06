package client.impl;

import client.exception.ActxApiError;
import client.exception.ActxApiErrorCode;
import client.exception.ActxApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class ActxApiServiceGenerator {
    private static final Retrofit.Builder builder;
    private static Retrofit retrofit;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        builder = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper));
    }

    private ActxApiServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        retrofit = builder.baseUrl(baseUrl).build();
        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                ActxApiError apiError = getActxApiError(response);
                throw new ActxApiException(apiError.getDetailedMessage(), ActxApiErrorCode.get(apiError.getEosErrorCode()));
            }
        } catch (IOException e) {
            throw new ActxApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    private static ActxApiError getActxApiError(Response<?> response) throws IOException {
        return (ActxApiError) retrofit.responseBodyConverter(ActxApiError.class, new Annotation[0])
                .convert(response.errorBody());
    }
}
