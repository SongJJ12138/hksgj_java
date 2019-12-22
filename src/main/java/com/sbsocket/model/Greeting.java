package com.sbsocket.model;

public class Greeting {

    private String content;

    public void setContent(String content) {
		this.content = content;
	}

	public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}

