package org.raspberry.nexus.repository;

import org.raspberry.framework.core.repository.GenericRepository;
import org.raspberry.nexus.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends GenericRepository<Project, Long> {

}
