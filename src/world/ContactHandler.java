package world;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactHandler implements ContactListener{
	private ArrayList<Contact> contacts;
	
	public ContactHandler() {
		this.contacts = new ArrayList<Contact>();
	}
	
	public ArrayList<Contact> getContacts() {
		return contacts;
	}
	
	@Override
	public void beginContact(Contact arg0) {
		contacts.add(arg0);
	}

	@Override
	public void endContact(Contact arg0) {
		contacts.remove(arg0);
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}
}
