package com.akshit.sale.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.akshit.sale.model.InfoData;

	@Controller
	public class SiteUIController {

		@Value("${app.baseUrl}")
		private String baseUrl;
		// WEBSITE PAGES
		@RequestMapping(value = "")
		public String index() {
			return "index.html";
		}
		@RequestMapping(value = "/ui/home")
		public ModelAndView home() {
			return mav("home.html");
		}
		@RequestMapping(value = "/ui/product")
		public ModelAndView product() {
			return mav("product.html");
		}
		@RequestMapping(value = "/ui/orderHistory")
		public ModelAndView orderHistory() {
			return mav("orderHistory.html");
		}
		@RequestMapping(value = "/ui/inventory")
		public ModelAndView inventory() {
			return mav("inventory.html");
		}
		@RequestMapping(value = "/ui/report")
		public ModelAndView report() {
			return mav("report.html");
		}
		@RequestMapping(value = "/ui/brand")
		public ModelAndView brand() {
			return mav("brand.html");
		}
		private ModelAndView mav(String page) {
			ModelAndView mav = new ModelAndView(page);
			mav.addObject("info", new InfoData());
			mav.addObject("baseUrl", baseUrl);
			return mav;
		}
		

}
