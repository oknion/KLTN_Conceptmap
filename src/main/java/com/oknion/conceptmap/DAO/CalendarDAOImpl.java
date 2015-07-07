package com.oknion.conceptmap.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXStatus;
import com.oknion.conceptmap.Model.Event;

@Repository
public class CalendarDAOImpl implements CalendarDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Iterable<DHXEv> getEvents(String userID) {
		Session session = this.sessionFactory.getCurrentSession();
		// DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";
		System.out.println(session == null);
		List<DHXEv> evs = new ArrayList<DHXEv>();
		List<Event> events = new ArrayList<Event>();
		try {
			String hql = "FROM Event WHERE userid=:userId ";
			Query query = session.createQuery(hql);
			query.setParameter("userId", userID);
			events = query.list();

			System.out.println("get events " + events.size());
			for (Event event : events) {
				DHXEvent eventDHXE = new DHXEvent(event.getEvent_id(),
						event.getStart_date(), event.getEnd_date(),
						event.getEvent_name());
				evs.add(eventDHXE);
			}
			System.out.println("get events " + evs.size());

		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return evs;
	}

	@Override
	public int saveEvent(Event event, DHXStatus status) {
		Session session = this.sessionFactory.getCurrentSession();
		System.out.println("save event " + event.getEvent_name());
		try {

			if (status == DHXStatus.UPDATE)
				session.merge(event);
			else if (status == DHXStatus.DELETE)
				session.delete(event);
			else if (status == DHXStatus.INSERT)
				session.save(event);
			return event.getEvent_id();

		} catch (RuntimeException e) {

			e.printStackTrace();
			return -1;
		}

	}

	@Override
	public Iterable<DHXEv> getEvents() {
		Session session = this.sessionFactory.getCurrentSession();
		// DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";
		System.out.println(session == null);
		List<DHXEv> evs = new ArrayList<DHXEv>();
		List<Event> events = new ArrayList<Event>();
		try {
			String hql = "FROM Event";
			Query query = session.createQuery(hql);
			events = query.list();

			System.out.println("get events " + events.size());
			for (Event event : events) {
				DHXEvent eventDHXE = new DHXEvent(event.getEvent_id(),
						event.getStart_date(), event.getEnd_date(),
						event.getEvent_name());
				evs.add(eventDHXE);
			}
			System.out.println("get events " + evs.size());

		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return evs;
	}

}
