package com.viniciusps2.bank.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {
	
	private static final long serialVersionUID = -7822359402690014234L;

	public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }


}
