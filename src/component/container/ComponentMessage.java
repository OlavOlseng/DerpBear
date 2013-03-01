package component.container;

import component.base.Component;

public class ComponentMessage {
	private Component sender;
	private Message message;
	public ComponentMessage(Component sender,Message message){
		this.sender = sender;
		this.message = message;
	}
	enum Message{
		
	}
	public Component getSender() {
		return sender;
	}
	
	public Message getMessage() {
		return message;
	}
	
	
	
	
}
