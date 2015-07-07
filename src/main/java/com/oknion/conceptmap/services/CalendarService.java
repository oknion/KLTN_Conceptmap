package com.oknion.conceptmap.services;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXStatus;
import com.oknion.conceptmap.Model.Event;

public interface CalendarService {

	public Iterable<DHXEv> getEvents(String userID);

	public int saveEvent(Event event, DHXStatus status);

	public Iterable<DHXEv> getEvents();
}
