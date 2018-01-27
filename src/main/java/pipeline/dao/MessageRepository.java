package pipeline.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pipeline.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
