package pipeline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pipeline.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
