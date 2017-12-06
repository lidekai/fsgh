package com.kington.fshg.webapp.security;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class MySessionContext {
	private static MySessionContext instance;
	private HashMap<String, HttpSession> mymap;

	private MySessionContext() {
		mymap = new HashMap<String, HttpSession>();
	}

	public static MySessionContext getInstance() {
		if (instance == null) {
			instance = new MySessionContext();
		}
		return instance;
	}

	public synchronized void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
			System.out.println("++++++add session:"+session.getId());
		}
	}

	public synchronized void DelSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
			System.out.println("------remove session:"+session.getId());
		}
	}

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null || mymap.get(session_id) == null)
			return null;
		return (HttpSession) mymap.get(session_id);
	}

}
