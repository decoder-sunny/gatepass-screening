package com.gatepass.main.service;

import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.Pagination;

public interface SearchService {
	
	Pagination getSearchResult(int org_id,Condition condition);

}
