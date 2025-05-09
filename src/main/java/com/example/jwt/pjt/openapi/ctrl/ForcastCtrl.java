package com.example.jwt.pjt.openapi.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.pjt.openapi.domain.ForcastRequestDTO;
import com.example.jwt.pjt.openapi.domain.ForcastResponseDTO;
import com.example.jwt.pjt.openapi.service.ForcastService;

import jakarta.validation.Valid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/forcast")
public class ForcastCtrl {
    @Autowired
    private ForcastService forcastService;
    @Value("${openApi.servicekey}")
    private String serviceKey;
    @Value("${openApi.callbackurl}")
    private String callBackUrl;
    @Value("${openApi.dataType}")
    private String dataType;
    @PostMapping("/getData")
    public ResponseEntity<List<ForcastResponseDTO>> getData(@RequestBody ForcastRequestDTO param) {
        System.out.println("ForcastCtrl.getData() called");
        System.out.println("serviceKey : " + serviceKey);
        System.out.println("callBackUrl : " + callBackUrl);
        System.out.println("dataType : " + dataType);
        System.out.println("param : " + param);
        String requestURL = callBackUrl + "?serviceKey=" + serviceKey + "&beach_num=" + param.getBeach_num() + "&base_date=" + param.getBase_date() + "&base_time=" + param.getBase_time();
        System.out.println("requestURL : " + requestURL);
        // 콜백 URL에 요청 파라미터 보내는 것
        HttpURLConnection http   = null ; 
        InputStream       stream = null ; 
        String            result = null ;

        List<ForcastRequestDTO>  list  = null ;
        try {
            URL url = new URL(requestURL);
            http = (HttpURLConnection)url.openConnection();
            System.out.println("http connection = " + http ); 
            int code = http.getResponseCode() ; 
            System.out.println("http response code  = " + code );  
            if( code == 200 ) {
                stream = http.getInputStream() ; 
                result = readString(stream) ;
                System.out.println("result = " + result); 
		
                // 서비스 구현 
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return null;
    }
    
    public String readString(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String input = null  ;
        StringBuilder result = new StringBuilder();
        while( (input = br.readLine() ) != null ) {
            result.append(input+"\n\r");
        }
        br.close();   
        return result.toString() ; 
    }
}
