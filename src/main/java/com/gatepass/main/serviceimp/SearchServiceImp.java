package com.gatepass.main.serviceimp;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.gatepass.main.dto.profile.Profile;
import com.gatepass.main.miscellaneous.Condition;
import com.gatepass.main.miscellaneous.CustomException;
import com.gatepass.main.miscellaneous.Pagination;
import com.gatepass.main.service.SearchService;

@Service
public class SearchServiceImp implements SearchService{
	
	 @Autowired ElasticsearchRestTemplate elasticsearchTemplate;

	@SuppressWarnings("deprecation")
	@Override
	public Pagination getSearchResult(int org_id, Condition condition) {
		try {
			
			QueryBuilder shoulds =  QueryBuilders.boolQuery()
					.should(new WildcardQueryBuilder("name", "*"+condition.getText()+"*"))
					.should(new WildcardQueryBuilder("email", "*"+condition.getText()+"*"))
					.should(new WildcardQueryBuilder("details", "*"+condition.getText()+"*"));
			
			QueryBuilder query =  QueryBuilders.boolQuery()
							.must(QueryBuilders.termQuery("orgId", org_id))
							.must(shoulds);
   
			NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
				.withQuery(query)
				.withPageable(PageRequest.of(
						(condition.getIndex()-1),condition.getPagesize()))
						.build();
			Page<Profile> result = elasticsearchTemplate.queryForPage(nativeSearchQuery, Profile.class, IndexCoordinates.of("profile"));
			return mapCandidates(result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Failed to fetch result");
		}
	}
	
	public Pagination mapCandidates(Page<Profile> profile) {
		Pagination pagination=new Pagination();
		pagination.setProfile(profile.getContent());
		pagination.setLength(Integer.valueOf(String.valueOf(profile.getTotalElements())));
		return pagination;
	}

}
