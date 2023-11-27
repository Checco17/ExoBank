package it.exobank.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class HandleBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Integer switchNumb = 1;

	public Integer getSwitchNumb() {
		return switchNumb;
	}

	public void setSwitchNumb(Integer switchNumb) {
		this.switchNumb = switchNumb;
	}
	
	

}
