package test.Waluty;

import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

//RestFull Web Service aplikacja klienta
//Biblioteka Jersey i Jackson

public class App {
	public static void main(String[] args) {
		String waluta;
		String data;
		
		System.out.println("wpisz walute: usd lub eur");
		Scanner in1 = new Scanner(System.in);
		waluta = in1.next();
	
		System.out.println("Wpisz date kursu w formacie YYYY-MM-DD");
		Scanner in2 = new Scanner(System.in);
		data = in2.next();

		try {

			Client klient = Client.create();
			
			WebResource webZrodlo = klient.resource("http://api.nbp.pl/api/exchangerates/rates/c/" + waluta + "/" + data + "/");

			ClientResponse webOdpowiedz = webZrodlo.accept("application/json").get(ClientResponse.class);

			if (webOdpowiedz.getStatus() != 200) {

				throw new RuntimeException("Bląd HTTP ERROR" + webOdpowiedz.getStatus());

			}

			String pobrany = webOdpowiedz.getEntity(String.class);

			//System.out.println(pobrany);
			
			//Mapowanie
			ObjectMapper mapper = new ObjectMapper();
			Kursy k = mapper.readValue(pobrany, Kursy.class);

			
			System.out.println("Waluta  " + k.getCurrency()+"  "+ k.getCode() + " Kurs  " + k.getRates().get(0).getAsk());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
