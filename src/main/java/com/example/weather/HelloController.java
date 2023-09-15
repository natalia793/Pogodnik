package com.example.weather;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;


public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text info;

    @FXML
    private Text info_feel;

    @FXML
    private Text info_max;

    @FXML
    private Text info_min;

    @FXML
    private Text info_pressure;

    @FXML
    private Text info_temp;

    @FXML
    private Text weather;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            // Получаем данные из текстового поля
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) { // Если данные не пустые
                // Получаем данные о погоде с сайта openweathermap
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&lang=ru&appid=47e63ad11aecd316d0d7edd7b2d44fa9");

                if (!output.isEmpty()) { // Нет ошибки и такой город есть
                    JSONObject obj = new JSONObject(output);

                    // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                    info_temp.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
                    info_feel.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
                    info_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
                    info_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
                    info_pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }
    // Обработка URL адреса и получение данных с него
    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Такой город был не найден!");
        }
        return content.toString();
    }

    }

