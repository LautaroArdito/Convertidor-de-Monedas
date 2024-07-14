import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaApi {
    private static final String KEY ="5a8946e60d446695549ce25c";

    public Moneda buscarMoneda(String monedaOrigen,String monedaDestino,int cant){
        URI direc = URI.create("https://v6.exchangerate-api.com/v6/"
                +KEY+"/pair/"+monedaOrigen+"/"+monedaDestino+"/"+cant);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direc)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(),Moneda.class);
        } catch (Exception e) {
            throw new RuntimeException("No se encontro esa moneda.");
        }
    }
}
