package com.oknion.conceptmap.services;

import org.springframework.transaction.annotation.Transactional;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXStatus;
import com.oknion.conceptmap.DAO.CalendarDAO;
import com.oknion.conceptmap.Model.Event;

@Transactional
public class CalendarServiceImpl implements CalendarService {

	private CalendarDAO calendarDAO;

	public CalendarDAO getCalendarDAO() {
		return calendarDAO;
	}

	public void setCalendarDAO(CalendarDAO calendarDAO) {
		this.calendarDAO = calendarDAO;
	}

	@Override
	public Iterable<DHXEv> getEvents(String userID) {

		return this.calendarDAO.getEvents(userID);
	}

	@Override
	public int saveEvent(Event event, DHXStatus status) {

		return this.calendarDAO.saveEvent(event, status);
	}

	@Override
	public Iterable<DHXEv> getEvents() {
		// TODO Auto-generated method stub
		return this.calendarDAO.getEvents();
	}

}
