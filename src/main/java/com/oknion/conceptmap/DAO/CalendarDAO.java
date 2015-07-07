package com.oknion.conceptmap.DAO;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXStatus;
import com.oknion.conceptmap.Model.Event;

public interface CalendarDAO {

	public Iterable<DHXEv> getEvents(String userID);

	public Iterable<DHXEv> getEvents();

	public int saveEvent(Event event, DHXStatus status);
}
