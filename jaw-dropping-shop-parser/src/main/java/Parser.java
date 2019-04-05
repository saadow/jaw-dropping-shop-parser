
import java.beans.JavaBean;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import dto.Product;

public class Parser {
	public static void main(String[] args) throws IOException {
		System.out.println(getProductsInfo(getProductLinks("adidas")));
	}

	public static List<String> getProductLinks(String keywords) {
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
				listOfLinks.add(baseURL + link.attr("href"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return listOfLinks;
	}

	public static List<Product> getProductsInfo(List<String> productLinks) throws IOException {
		Document doc;
		List<Product> productList = new ArrayList<Product>();
		doc = Jsoup.connect(productLinks.get(1)).get();
		Elements json = doc.body().getElementsByAttributeValue("type", "application/ld+json");
		Gson g = new Gson();
		Product prod = g.fromJson(json.get(1).data(), Product.class);
		Elements price = doc.body().getElementsByAttributeValue("data-test-id", "ProductPrices");
		prod.setPrice(price.text().split(" ")[1]);
		Elements articleId = doc.body().getElementsByAttributeValue("class", "_articleNumber_1474d");
		prod.setArticleID(articleId.text().split(" ")[1]);
		Elements color = doc.body().getElementsByAttributeValue("data-test-id", "VariantColor");
		prod.setColor(color.text());
		productList.add(prod);
		return productList;
	}
}
