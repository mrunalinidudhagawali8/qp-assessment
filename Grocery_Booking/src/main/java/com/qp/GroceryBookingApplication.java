package com.qp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.qp.entities.GroceryItem;
import com.qp.repository.GroceryItemRepository;

@SpringBootApplication
public class GroceryBookingApplication implements ApplicationRunner {

	public static void main(String[] args) {

		SpringApplication.run(GroceryBookingApplication.class, args);
	}
	

	@Autowired
	private GroceryItemRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (repository.count() == 0) {
			GroceryItem item1 = new GroceryItem();
			item1.setName("Cadbury");
			item1.setDescription("awesome chocolate");
			item1.setPrice(10.00);
			item1.setStockQty(5);
			GroceryItem item2 = new GroceryItem();
			item2.setName("Pepsi");
			item2.setDescription("a second ranked Soda");
			item2.setPrice(25.00);
			item2.setStockQty(5);
			GroceryItem item3 = new GroceryItem();
			item3.setName("Coco-cola");
			item3.setDescription("first ranked Soda");
			item3.setPrice(10.00);
			item3.setStockQty(5);

			
			
			repository.saveAll(List.of(item1, item2, item3));
		}
	}
}
