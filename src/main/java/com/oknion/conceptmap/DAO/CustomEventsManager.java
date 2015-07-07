package com.oknion.conceptmap.DAO;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;
import com.oknion.conceptmap.Model.Event;
import com.oknion.conceptmap.services.CalendarService;

public class CustomEventsManager extends DHXEventsManager {

	CalendarService calendarService;

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public UserDetails getCurrentUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

	public CustomEventsManager(HttpServletRequest request) {
		super(request);

	}

	@Override
	public Iterable<DHXEv> getEvents() {

		return this.calendarService.getEvents(getCurrentUserDetails()
				.getUsername());
	}

	@Override
	public DHXStatus saveEvent(DHXEv event, DHXStatus status) {
		Event eventDB = new Event();
		eventDB.setEvent_id(event.getId());
		eventDB.setEvent_name(event.getText());
		eventDB.setStart_date(event.getStart_date());
		eventDB.setEnd_date(event.getEnd_date());
		eventDB.setUserId(getCurrentUserDetails().getUsername());

		System.out.println(event.getId());
		System.out.println(event.getText());
		System.out.println(event.getStart_date());
		System.out.println(event.getEnd_date());
		event.setId(this.calendarService.saveEvent(eventDB, status));

		return status;
	}

	@Override
	public DHXEv createEvent(String id, DHXStatus status) {
		return new DHXEvent();

	}
}
