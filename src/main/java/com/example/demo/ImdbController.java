package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ImdbController {

	Map<String, List<String>> movieMap;
	Map<String, List<String>> lastNameMovieMap;
	Map<String, List<String>> yearMap;
	
	@PostConstruct
	public void init() {
		movieMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		lastNameMovieMap =  new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		yearMap = new HashMap<>();
		buildMovieMap();
	}
	
	
	private void buildMovieMap() {
		try {
			for(int page = 0; page < 20; page ++) {
				UriComponents uri = UriComponentsBuilder
	            .fromHttpUrl("https://www.imdb.com/search/title/?groups=top_1000&view=simple&sort=user_rating,desc&start={page}&ref_=adv_nxt")
	            .buildAndExpand(page*50 + 1);
			    String urlString = uri.toUriString();
			    Document doc = Jsoup.connect(urlString).get();
			  
			    //Each link has the cast and the movie information
			    //Iterate over the cast and with cast as the key map them to the movie of that particular link
			    //if the cast is already present add the movie to the array list corresponding to that particular cast
			   Elements links = doc.select("span[title]");
			   for(Element link : links) {
				   String[] cast = link.attr("title").split(",");
				   String movie = link.select("a").html();
				   for(String c: cast) {
					   String str = c.replace("(dir.)","");
					   str = str.trim();
					   String[] nameSplit = str.split(" ");
					   String lastName =  nameSplit[nameSplit.length - 1];
					   if(movieMap.containsKey(str)) {
						   movieMap.get(str).add(movie+"-"+link.child(1).text());
					   } else {
						   movieMap.put(str, new ArrayList<>());
						   movieMap.get(str).add(movie+"-"+link.child(1).text());
					   }
					   
					   if(lastNameMovieMap.containsKey(lastName)) {
						   lastNameMovieMap.get(lastName).add(movie+"-"+link.child(1).text());
					   } else {
						   lastNameMovieMap.put(lastName, new ArrayList<>());
						   lastNameMovieMap.get(lastName).add(movie+"-"+link.child(1).text());
					   }
				   }
				   
				   StringBuffer buf = new StringBuffer();
				   String str = link.child(1).text();
				   for(char c: str.toCharArray()) {
					   
					   if(Character.isDigit(c)) {
						   buf.append(c);
					   }
				   }
				   String year = buf.toString();
				   if(yearMap.containsKey(year)) {
					   yearMap.get(year).add(movie);
				   } else {
					   yearMap.put(year, new ArrayList<>());
					   yearMap.get(year).add(movie);
				   }
			   }
			}

		} catch(Exception e) {
			System.out.println("Unable to parse web page!!");
		}
	}



	@GetMapping(value = "/search/{name}")
	public List<String> getMoviesByDirectors(@PathVariable("name") String name) {
		
		if(movieMap.containsKey(name)) {
			return movieMap.get(name);
		} else if(lastNameMovieMap.containsKey(name)) {
			return lastNameMovieMap.get(name);
		} else {
			return new ArrayList<>();
		}
		

	}
	
	@GetMapping(value = "common/{name1}/{name2}")
	public List<String> getCommonMovies(@PathVariable("name1") String name1, @PathVariable("name2") String name2) {
		
		List<String> moviesName1;
		if(movieMap.containsKey(name1)) {
			moviesName1 = movieMap.get(name1);
		} else {
			moviesName1 = lastNameMovieMap.get(name1);
		}
		List<String> common = new ArrayList<>();
		
		Set<String> dirMovieSet  = new HashSet<>(moviesName1);
		
		if(movieMap.containsKey(name2)) {
			for(String s : movieMap.get(name2)) {
				
				if(dirMovieSet.contains(s)) {
					common.add(s);
				}
				
			}
		} else if(lastNameMovieMap.containsKey(name2)) {
			for(String s : lastNameMovieMap.get(name2)) {
				
				if(dirMovieSet.contains(s)) {
					common.add(s);
				}
				
			}
		}
	
		
		return common; 
	}
	
	@GetMapping(value = "/year/{year}")
	public List<String> getMoviesByYear(@PathVariable("year") String year) {
		
		if(yearMap.containsKey(year)) {
			return yearMap.get(year);
		}
		
		return new ArrayList<>(); 

	}

	
	

}
