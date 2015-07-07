package com.oknion.conceptmap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dhtmlx.planner.DHXPlanner;
import com.dhtmlx.planner.DHXSkin;
import com.dhtmlx.planner.data.DHXDataFormat;
import com.oknion.conceptmap.DAO.CustomEventsManager;
import com.oknion.conceptmap.services.CalendarService;

@Controller
public class CalendarController {

	private CalendarService calendarService;

	public CalendarService getCalendarService() {
		return calendarService;
	}

	@Autowired
	@Qualifier(value = "calendarService")
	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	@RequestMapping("/myplanner")
	public ModelAndView planner(HttpServletRequest request) throws Exception {

		DHXPlanner p = new DHXPlanner("./codebase/", DHXSkin.TERRACE);
		p.setInitialDate(2013, 1, 7);

		// p.config.setScrollHour(2);
		p.setWidth(900);
		p.load("events", DHXDataFormat.JSON);
		p.data.dataprocessor.setURL("events");

		ModelAndView mnv = new ModelAndView("article");
		mnv.addObject("body", p.render());
		return mnv;
	}

	@RequestMapping("/myplanner1")
	public String planner1(HttpServletRequest request) throws Exception {

		return "article1";
	}

	@RequestMapping("/events")
	@ResponseBody
	public String events(HttpServletRequest request) {

		CustomEventsManager evs = new CustomEventsManager(request);
		evs.setCalendarService(calendarService);
		return evs.run();

	}

}
