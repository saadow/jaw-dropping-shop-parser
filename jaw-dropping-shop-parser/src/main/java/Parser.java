

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import dto.Product;

public class Parser {
	public static void main(String[] args) throws IOException {
		getProductsInfo(getProductLinks());
	}

	public static List<String> getProductLinks() {
		Document doc;
		// String keywords = args[0];
		String baseURL = "https://www.aboutyou.de";
		String searchURL = "https://www.aboutyou.de/about/brand/adidas-originals?category=20201&is_s=typein&is_h=manual&term=adidas";
		List<String> listOfLinks = new ArrayList<String>();
		try {

			doc = Jsoup.connect(searchURL).get();
			Elements links = doc.body().getElementsByAttributeValue("data-test-id", "ProductTileDefault")
					.select("a[href]");

			for (Element link : links) {
				//System.out.println(baseURL + link.attr("href"));
				listOfLinks.add(baseURL + link.attr("href"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return listOfLinks;
	}
	
	public static List<Product> getProductsInfo(List<String> productLinks) throws IOException{
		Document doc;
		List<Product> productList = new ArrayList<Product>();
		doc = Jsoup.connect(productLinks.get(1)).get();
		Elements links = doc.body().getElementsByAttributeValue("type", "application/ld+json");
//		for (String link : productLinks) {
//			for (Element l : links) {
//				System.out.println(l);
//			}
//		}
		System.out.println(links.get(1));
		return productList;
	}
}
