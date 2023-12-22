package org.rurulala.server_admin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Network_Request {
    String url;
    String Method;
    HashMap<String, String> Data = new HashMap<String, String>();
    String response;
    int statue;
    Network_Request(String url, String Method, HashMap<String, String> Data, String response, int statue){
        this.url = url;
        this.Method = Method;
        this.Data = Data;
        this.response = response;
        this.statue = statue;
    }
    public void sendRequestJson() {
        try {
            // 요청을 보낼 URL 설정
            URL apiUrl = new URL(this.url);

            // HttpURLConnection 객체 생성
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // 요청 방식 설정 (Method)
            if(!(this.Method == "")){
                connection.setRequestMethod(this.Method);
            }else {
                this.response = "0(check Method)";
                this.statue = 0;
            }
            // 요청 헤더 설정
            connection.setRequestProperty("Content-Type", "application/json");

            // 요청 데이터를 전송
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(HashMapToJson(this.Data).getBytes());
            outputStream.flush();
            outputStream.close();

            // 응답 코드 확인
            this.statue = connection.getResponseCode();
            System.out.println("Response Code: " + this.statue);

            // 응답 데이터 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 응답 데이터 출력
            System.out.println("Response Data: " + response.toString());

            // 연결 종료
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String HashMapToJson(HashMap<String, String > hashMap) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            jsonBuilder.append("\"").append(key).append("\":").append(value).append(",");
        }

        if (!hashMap.isEmpty()) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // 마지막 쉼표 삭제
        }

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}

