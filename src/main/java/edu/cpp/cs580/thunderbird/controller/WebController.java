package edu.cpp.cs580.thunderbird.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.cpp.cs580.thunderbird.data.provider.CppManager;
import edu.cpp.cs580.thunderbird.data.provider.UserManager;
import edu.cpp.cs580.thunderbird.tools.GetCppClasses;
import edu.cpp.cs580.thunderbird.tools.GoogleOAuth;

@RestController
public class WebController {
	
	@Autowired private UserManager userManager;
	@Autowired CppManager cppManager;

    GoogleOAuth OAuth; // May come up with better method

    /**
     *  Nanwarin Testing
     */
    @RequestMapping(value = "/Nanwarin")
    String helloNan(){
    	cppManager.ListClasses();
        return "Hello it's me";
    }

    /**
     * Assignment 3 part 3
     * @return
     */
    @RequestMapping(value = "/Wiehsing", method = RequestMethod.GET)
    String wiehsingtest(){

        return "Wie Hsing Li testing";
    }

    /**
     * Assignment 3 - Diana
     * Basic static calendar
     */
    @RequestMapping(value = "/Diana-A3", method = RequestMethod.GET)
    ModelAndView DianaAssignment3() {
        ModelAndView modelAndView = new ModelAndView("BasicCalendar.html");
        return modelAndView;
    }
    
    /**
     * Assignment 4 - Diana
     * Display International Student Club Events from intStdEvents.csv
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/Diana-A4", method = RequestMethod.GET)
    String DianaAssignment4() throws IOException {
        File file = new File("src/main/resources/static/data/intStdEvents.csv");
        List lines = FileUtils.readLines(file, "UTF-8");
        String eventList = "";
        for (Object event : lines) {
            eventList += event + "<br/>";
        }
        return eventList;
    }

    /*
     * Assignment #3
     * Author: Kushal Patel
     */
    @RequestMapping(value = "/Kushal", method = RequestMethod.GET)
    String KushalAssignment3() {
    	return "CS580 Assignment #3";
    }
    
    /**
     * After user login with Google Account
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    String getHome(@RequestParam("code") String code) throws IOException{
        userManager.updateUser(OAuth.getUserInfoJson(code));
        return "Welcome " + userManager.getUserName();
    }

    /**
     * Assignment#4 by Nanwarin, 
     * imported OAuth2 Library for logging in with Google Account
     * Require User to authorize account with Google before they can use calendar
     * Will come up with better method later
     * @throws URISyntaxException
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Object> loginGoogle() throws URISyntaxException
    {
        OAuth = new GoogleOAuth();
        String url = OAuth.buildLoginUrl();
        URI loginURI = new URI(url);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(loginURI);
        
        return new ResponseEntity<Object>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    //Temporary Usage, Just want to try it out
    @RequestMapping("/getCppSchedule")
    String getCppClassesSchedule() throws Exception{
        try {
            GetCppClasses cppClsss = new GetCppClasses(cppManager);
        } catch (IOException e) {
            System.out.println("Error -- getCppClsses");
        }

        return "complete";
    }
}
