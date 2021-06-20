package com.gatepass.main.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.gatepass.main.dto.profile.Profile;


@Repository
public interface ProfileRepository extends ElasticsearchRepository<Profile,Integer>{

}
